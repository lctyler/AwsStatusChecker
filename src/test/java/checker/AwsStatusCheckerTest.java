package checker;

import java.util.Date;

import org.junit.Test;

import reader.AwsStatusReaderConfig;
import reader.AwsStatusReaderConfig.ReaderType;
import slack.SlackConfig;
import util.ConfigLoader;

public class AwsStatusCheckerTest {
	
	@Test
	public void testCheckerAllPosts() throws Exception
	{
		SlackConfig config = SlackConfig.builder()
				.url("https://hooks.slack.com/services/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")
				.channel("#awsstatus")
				.connectionTimeout(5000)
				.socketReadTimeout(5000)
				.userName("AwsStatusBot").build();
		
		AwsStatusReaderConfig readerConfig = AwsStatusReaderConfig.builder()
				.sourceType(ReaderType.URL)
				.url("http://status.aws.amazon.com/rss/all.rss")
				.build();
		
		AwsStatusCheckerConfig checkerConfig = new AwsStatusCheckerConfig();
		
		checkerConfig.setSlackConfig(config);
		checkerConfig.setStatusReaderConfig(readerConfig);
		checkerConfig.setLastChecked(new Date(1800));
		AwsStatusChecker checker = new AwsStatusChecker(checkerConfig);
		
		checker.performCheck();
	}
	
	
	@Test
	public void testCheckerFilteredPosts() throws Exception
	{
		SlackConfig config = SlackConfig.builder()
				.url("https://hooks.slack.com/services/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")
				.channel("#awsstatus")
				.connectionTimeout(5000)
				.socketReadTimeout(5000)
				.userName("AwsStatusBot").build();
		
		AwsStatusReaderConfig readerConfig = AwsStatusReaderConfig.builder()
				.sourceType(ReaderType.URL)
				.url("http://status.aws.amazon.com/rss/all.rss")
				.build();
		
		AwsStatusCheckerConfig checkerConfig = new AwsStatusCheckerConfig();
		
		checkerConfig.setSlackConfig(config);
		checkerConfig.setStatusReaderConfig(readerConfig);
		checkerConfig.setLastChecked(new Date(0L));
		AwsStatusChecker checker = new AwsStatusChecker(checkerConfig);
		
		checker.performCheck();
	}
	
	@Test
	public void testCheckerFilteredPostsLoadFromConfig() throws Exception
	{
		AwsStatusCheckerConfig checkerConfig = ConfigLoader.loadStatusCheckerConfigFromFile("config.json");

		AwsStatusChecker checker = new AwsStatusChecker(checkerConfig);
		
		checker.performCheck();
	}
}
