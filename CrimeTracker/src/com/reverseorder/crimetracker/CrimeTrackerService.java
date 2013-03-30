package com.reverseorder.crimetracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class CrimeTrackerService extends Service 
{
	private static final String TAG = EventRowHandler.class.toString();

	  
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.w(TAG, "Starting Command...");
		
		try 
		{
			Thread.sleep(1000);
		} 
		catch(InterruptedException e)
		{
		}
		// If we get killed, after returning from here, restart
		return START_STICKY;
	}
  
	@Override
	public void onCreate() 
	{
		Log.w(TAG, "Starting CrimeTracker service...");
	}


	@Override
	public IBinder onBind(Intent intent) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() 
	{
		Log.w(TAG, "CrimeTracker Server finished.");
	}

}
