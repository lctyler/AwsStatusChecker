package checker;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CheckerStats
{
		@Setter
		private Date lastChecked; 
	
		private AtomicInteger postsSent = new AtomicInteger();
		private AtomicInteger statusItemsRead = new AtomicInteger();
		private AtomicInteger itemsFiltered = new AtomicInteger();
		private AtomicInteger postsFailed = new AtomicInteger();
		
}
