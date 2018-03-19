package checker;

import java.util.Date;

import common.Config;
import lombok.Getter;
import lombok.Setter;
import reader.AwsStatusReaderConfig;
import slack.SlackConfig;

@Getter
@Setter 
public class AwsStatusCheckerConfig extends Config
{
	
	public AwsStatusCheckerConfig()
	{
		
	}
	// State object. 
	private SlackConfig slackConfig;
	private AwsStatusReaderConfig statusReaderConfig;
	private Date lastChecked;
	private String bucketName; 
	private String region;
}
