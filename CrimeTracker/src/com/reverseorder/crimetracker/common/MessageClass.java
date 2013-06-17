package com.reverseorder.crimetracker.common;

public enum MessageClass
{
	Unknown(0),
	Hello(1),
	GoodBye(2);
	
	Integer value;
	
	private MessageClass(Integer value) 
	{
		this.value = value;
	}
	
	public static MessageClass fromInt(Integer value)
	{
		for(MessageClass messageClass : values())
		{
			if(messageClass.value == value)
			{
				return messageClass;
			}
		}
		return values()[Unknown.toInt()];
	}
	
	public Integer toInt()
	{
		return value;
	}
}
