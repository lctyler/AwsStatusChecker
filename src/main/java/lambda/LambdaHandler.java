package lambda;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import checker.AwsStatusChecker;
import checker.AwsStatusCheckerConfig;
import checker.CheckerStats;
import util.ConfigLoader;
import util.S3StringReader;

public class LambdaHandler implements RequestHandler<AwsStatusCheckerConfig, CheckerStats>
{
	private final Logger logger;
	private static final String LAST_CHECKED = ".lastChecked";
	private static final String CONFIG = "config.json";

	public LambdaHandler()
	{
		logger = LogManager.getLogger(getClass());
	}

	@Override
	public CheckerStats handleRequest(AwsStatusCheckerConfig input, Context context)
	{
		try
		{
			input.resetStats();
			
			// if we are debugging, input will be passed in, else we will need to load. 
			String bucket = input.getBucketName();
			String region = input.getRegion();
			AmazonS3 s3 = null;
			if (bucket == null)
			{
				bucket = getEnvVal(context, "bucketName", true);
				region = getEnvVal(context, "region", true);
				
				s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
				String configJson = S3StringReader.getString(s3, bucket, CONFIG, true);
				input = ConfigLoader.loadStatusCheckerConfig(configJson);
				
			}
			else
			{
				s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();
			}
		
			// load when we last checked 
			String strDate = S3StringReader.getString(s3, bucket, LAST_CHECKED, false);
			
			Date dtLastChecked = null;
			if (strDate != null)
			{
				dtLastChecked = new Date(Long.parseLong(strDate));
			}
			else
			{
				dtLastChecked = new Date(0L);
			}
			
			input.setLastChecked(dtLastChecked);
			
			AwsStatusChecker checker = new AwsStatusChecker(input);

			CheckerStats stats = checker.performCheck();
			// what happens if we didn't succeed? Right now we just try again, we need to
			// disable the lambda and send an SOS
			stats.setLastChecked(new Date());

			s3.putObject(bucket, LAST_CHECKED, Long.toString(stats.getLastChecked().getTime()));

			return stats;
		} catch (Throwable t)
		{
			logger.error("Error while handling lambda", t);
			throw new RuntimeException(t);
		}
	}
	
	private String getEnvVal(Context context, String key, boolean required) throws Exception
	{
		String rtn = System.getenv(key);
		
		if (rtn == null && required)
		{
			throw new Exception("Missing required env var " + key);
		}
		
		return rtn;
	}
}
