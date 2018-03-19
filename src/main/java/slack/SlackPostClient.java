package slack;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlackPostClient
{

	private final SlackConfig config;
	private final HttpClient client;

	public SlackPostClient(SlackConfig config)
	{
		this.config = config;
		
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(config.getConnectionTimeout());
		requestBuilder = requestBuilder.setConnectionRequestTimeout(config.getConnectionTimeout());
		requestBuilder = requestBuilder.setSocketTimeout(config.getSocketReadTimeout());
		
		
		this.client = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestBuilder.build())
				.build();
	}

	public void sendSlackPost(SlackPost slackPost) throws Exception
	{
		String payload = serializeSlackPost(slackPost);

		HttpPost post = new HttpPost(config.getUrl());
		StringEntity stringEnt = new StringEntity(payload);
		post.setEntity(stringEnt);

		HttpResponse resp = client.execute(post);
		
		if (resp.getStatusLine().getStatusCode() != 200)
		{
			throw new Exception("Error while sending slack post! " + resp.toString());
		}
		
		this.config.getStats().getPostsSent().incrementAndGet();
	}

	private String serializeSlackPost(SlackPost slackPost) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(slackPost);
	}

}
