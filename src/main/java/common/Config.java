package common;

import checker.CheckerStats;

public abstract class Config
{
	protected static CheckerStats stats = new CheckerStats();
	
	public CheckerStats getStats()
	{
		return stats;
	}
	
	/**
	 * Lambda runs in containers, and it may run in the same container, so statics will be preserved between invocations. 
	 * This method allows us to preserve the static stats value across multiple instances of configs. It must be executed in a thread safe manner. 
	 */
	public void resetStats()
	{
		stats = new CheckerStats();
	}
}
