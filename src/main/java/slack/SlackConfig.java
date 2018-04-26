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
	private String url;
	private String channel;
	private NotificationLevel notificationLevel;
	private String userName;
	private int socketReadTimeout;
	private int connectionTimeout;
	
	public SlackConfig()
	{
		// null for jackson
	}
	
	public SlackConfig(String url, String channel,  NotificationLevel level,  String userName, int socketReadTimeout, int connectionTimeout)
	{
		super();
		this.url = url;
		this.channel = channel;
		this.userName = userName;
		this.socketReadTimeout = socketReadTimeout;
		this.connectionTimeout = connectionTimeout;
		this.notificationLevel = level;
	}
	
	
	// TODO ping a specific person? 
	public enum NotificationLevel
	{
		AT_CHANNEL("@channel"),
		AT_HERE("@here");
		
		private String text;
		private NotificationLevel(String text)
		{
			this.text = text;
		}
		
		public String getText()
		{
			return text;
		}
	}
}
