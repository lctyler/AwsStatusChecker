package util;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import checker.AwsStatusCheckerConfig;
import reader.AwsStatusReaderConfig;
import reader.AwsStatusReaderConfig.ReaderType;
import slack.SlackConfig;

public class ConfigGenerator
{
	
	public static void main(String[] args) throws JsonProcessingException
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
		checkerConfig.setLastChecked(new Date(1520015040L * 1000L));
		
		
		ObjectMapper mapper = new ObjectMapper();

		System.out.println(mapper.writeValueAsString((checkerConfig)));
		
	}
	
}
