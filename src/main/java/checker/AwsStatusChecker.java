package checker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import model.StatusItem;
import reader.AwsStatusReader;
import reader.AwsStatusTimeFilter;
import slack.SlackPost;
import slack.SlackPostClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AwsStatusChecker
{

	private final AwsStatusCheckerConfig config;
	private final Logger logger;

	public AwsStatusChecker(AwsStatusCheckerConfig config)
	{
		logger = LogManager.getLogger(getClass());
		this.config = config;
	}

	public CheckerStats performCheck() throws Exception
	{

		AwsStatusReader reader = new AwsStatusReader(config.getStatusReaderConfig());

		// Logic for Error codes.
		List<StatusItem> items = reader.loadStatus();

		new AwsStatusTimeFilter().applyFilterToItemsLessThanOrEqual(items, config.getLastChecked());

		if (!items.isEmpty())
		{
			ExecutorService executor = Executors.newFixedThreadPool(items.size());
			try
			{
				List<Future<Void>> tasks = new ArrayList<Future<Void>>(items.size());

				for (StatusItem item : items)
				{
					Callable<Void> task = () ->
					{
						try
						{

							SlackPost post = SlackPost.builder().channel(config.getSlackConfig().getChannel())
									.username(config.getSlackConfig().getUserName()).text(item.toString())
									.icon_emoji(item.isResolved() ? SlackPost.GREEN_CHECK_EMOJI : SlackPost.X_EMOJI).build();

							SlackPostClient client = new SlackPostClient(config.getSlackConfig());
							client.sendSlackPost(post);
						} catch (InterruptedException e)
						{
							throw new IllegalStateException("task interrupted", e);
						}
						return null;
					};

					tasks.add(executor.submit(task));

				}

				for (Future<Void> task : tasks)
				{
					try
					{
						task.get(5000, TimeUnit.SECONDS);
					} catch (Exception e)
					{
						logger.error("Error while posting to slack", e);
						config.getStats().getPostsFailed().incrementAndGet();
					}
				}
			} finally
			{
				executor.shutdown();
			}

		}
		return config.getStats();
	}
}
