package slack;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SlackPost {

	public static final String X_EMOJI = ":x:";
	public static final String GREEN_CHECK_EMOJI = ":heavy_check_mark:";
	
	private String text;
	private String channel;
	private String link_names;
	private String username;
	private String icon_emoji;
	
}
