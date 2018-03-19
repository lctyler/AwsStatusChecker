package model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatusItem 
{

	// raw data

	private String title;
	private Date publishedDate;
	private String guid;
	private String description;

	private boolean resolved;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusItem other = (StatusItem) obj;
		if (guid == null)
		{
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		return true;
	}
	
	public long getEpochTime()
	{
		return publishedDate.getTime();
	}

	
	@Override
	public String toString()
	{
		return "[" + publishedDate +  "] resolved=" + resolved + " " + title + "\n"
						+ description + "\n"
				 		+ "Link: "  + guid;
	}

	
	
}
