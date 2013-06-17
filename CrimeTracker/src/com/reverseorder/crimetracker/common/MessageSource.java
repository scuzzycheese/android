package com.reverseorder.crimetracker.common;

public enum MessageSource 
{
	Unknown(0),
	MainActivity(1),
	EventRowHandler(2),
	CrimeTrackerService(3);
	
	Integer value;
	
	private MessageSource(Integer value)
	{
		this.value = value;
	}
	
	public static MessageSource fromInt(Integer value)
	{
		for(MessageSource messageSource : values())
		{
			if(messageSource.value == value)
			{
				return messageSource;
			}
		}
		return values()[Unknown.toInt()];
	}
	
	public Integer toInt()
	{
		return value;
	}
}
