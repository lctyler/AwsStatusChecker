package reader;

import common.Config;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AwsStatusReaderConfig extends Config
{
	
	public AwsStatusReaderConfig()
	{}
	
	public AwsStatusReaderConfig(String url, ReaderType sourceType)
	{
		super();
		this.url = url;
		this.sourceType = sourceType;
	}

	private String url;

	private ReaderType sourceType;

	public enum ReaderType
	{
		URL, FILE
	}
}
