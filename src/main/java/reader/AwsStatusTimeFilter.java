package reader;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import model.StatusItem;

public class AwsStatusTimeFilter
{
	
	/**
	 * Modifies the list, removes any items that are less than the time inputed. 
	 @param time date to filter by
	 * @param input list of status items to reduce 
	 */
	public void applyFilterToItemsLessThanOrEqual(List<StatusItem> input, Date time)
	{
		// the items from AWS are NOT in order! We need to filter them down. then sort. 
		Predicate<StatusItem> personPredicate = item -> item.getPublishedDate().compareTo(time) <= 0;
		input.removeIf(personPredicate);
		
		Collections.sort(input, this.new AwsStatusItemComparitor());
	}
	
	private class AwsStatusItemComparitor implements Comparator<StatusItem>
	{

		@Override
		public int compare(StatusItem o1, StatusItem o2)
		{
			long time1 = o1.getEpochTime();
			long time2 = o2.getEpochTime();
			
			return (int) (time1 - time2); 
		}
	}
}
