package reader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.kms.model.UnsupportedOperationException;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import model.StatusItem;

public class AwsStatusReader
{

	private final AwsStatusReaderConfig config;

	public AwsStatusReader(AwsStatusReaderConfig config)
	{
		this.config = config;

	}

	public List<StatusItem> loadStatus() throws IllegalArgumentException, FeedException, IOException
	{
		String feedUrl = config.getUrl();

		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;

		switch (config.getSourceType())
		{
		case FILE:
			feed = input.build(new File(feedUrl));
			break;
		case URL:
			feed = input.build(new XmlReader(new URL(feedUrl)));
			break;
		default:
			throw new UnsupportedOperationException("Unknown source type" + config.getSourceType());
		}

		ArrayList<StatusItem> items = new ArrayList<StatusItem>(feed.getEntries().size());

		for (SyndEntry entry : feed.getEntries())
		{
			this.config.getStats().getStatusItemsRead().incrementAndGet();
			items.add(StatusItem.builder().title(entry.getTitle()).publishedDate(entry.getPublishedDate())
					.description(entry.getDescription().getValue()).guid(entry.getUri())
					.resolved(entry.getTitle() == null ? false : entry.getTitle().contains("[RESOLVED]"))
					.build());
		}

		return items;
	}

}
