package com.reverseorder.crimetracker;

import com.reverseorder.crimetracker.common.MessageClass;
import com.reverseorder.crimetracker.common.MessageSource;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;


public class CrimeTrackerService extends Service 
{
	private static final String TAG = CrimeTrackerService.class.toString();
	private Message mainActivityMessanger = null;

	  
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
		return mMessenger.getBinder();
	}
	
	@Override
	public void onDestroy() 
	{
		Log.w(TAG, "CrimeTracker Server finished.");
	}

	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
    /**
     * Handler of incoming messages from clients.
     * I don't think this will ever leak because it's parent class (the service)
     * will never be discarded. (I could be wrong tho)
     */
    @SuppressLint("HandlerLeak") 
    private class IncomingHandler extends Handler 
    {
    	public IncomingHandler()
    	{
    		Log.w(TAG, "Instantiating IncomingHandler");
    	}
    	
        @Override
        public void handleMessage(Message msg) 
        {
            switch(MessageClass.fromInt(msg.what)) 
            {
                case Hello:
                {
                	Log.w(TAG, "Got Message from crimeTracker activity!");
                	try 
                	{
                		if(msg.arg1 == MessageSource.MainActivity.toInt())
                		{
                			mainActivityMessanger = msg;
                		}
                		Log.w(TAG, (String)msg.obj);
                		msg.replyTo.send(Message.obtain(null, MessageClass.Hello.toInt(), 0, 0));
                	}
                	catch(RemoteException e)
                	{
                		Log.e(TAG, "Error responding to crimeTracker activity.", e);
                	}
                    break;
                }
                case Unknown:
                default:
                {
                    super.handleMessage(msg);
                }
            }
        }
    }
	
}
