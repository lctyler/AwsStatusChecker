package slack;

import org.junit.Test;

public class TestSlackPostClient {

	@Test
	public void testClient() throws Exception {
		
		SlackPost post = SlackPost.builder()
				.channel("#awsstatus")
				.icon_emoji(":grin:")
				.text("This is a test")
				.username("test")
				.build();
		
		SlackConfig config = SlackConfig.builder()
				.url("https://hooks.slack.com/services/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").build();
		
		
		SlackPostClient client = new SlackPostClient(config);
		
		client.sendSlackPost(post);
	}
	
	
}
