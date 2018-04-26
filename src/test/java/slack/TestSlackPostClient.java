package slack;

import org.junit.Test;

public class TestSlackPostClient {

	@Test
	public void testClient() throws Exception {
		
		SlackPost post = SlackPost.builder()
				.channel("#awsstatus")
				.icon_emoji(":grin:")
				.link_names("true")
				.text("@channel This is a test")
				.username("test")
				.build();
		
		SlackConfig config = SlackConfig.builder()
				.url("webhook goes here").build();
		
		
		SlackPostClient client = new SlackPostClient(config);
		
		client.sendSlackPost(post);
	}
	
	
}
