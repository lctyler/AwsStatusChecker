package slack;

import common.Config;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SlackConfig extends Config
{
	
	public SlackConfig()
	{
		// null for jackson
	}
	
	
	
	public SlackConfig(String url, String channel, String userName, int socketReadTimeout, int connectionTimeout)
	{
		super();
		this.url = url;
		this.channel = channel;
		this.userName = userName;
		this.socketReadTimeout = socketReadTimeout;
		this.connectionTimeout = connectionTimeout;
	}



	private String url;
	private String channel;
	private String userName;
	private int socketReadTimeout;
	private int connectionTimeout;
}
