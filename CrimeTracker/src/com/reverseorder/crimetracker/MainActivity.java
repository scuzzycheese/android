package com.reverseorder.crimetracker;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.reverseorder.crimetracker.structures.RowMeta;

public class MainActivity extends Activity 
{

	private static final String TAG = MainActivity.class.toString();
	private static int counter = 0;
	private List<RowMeta> rows = new ArrayList<RowMeta>();
	Intent crimeTrackerServiceIntent = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crimeTrackerServiceIntent = new Intent(this, CrimeTrackerService.class);
        crimeTrackerServiceIntent.putExtra("TestDataKey", "TestDataValue");
        startService(crimeTrackerServiceIntent);
    }
    
    @Override
    protected void onStart()
    {
    	super.onStart();
        bindService(crimeTrackerServiceIntent, crimeTrackerServiceConnection, Context.BIND_AUTO_CREATE);
    }
    
    @Override
    protected void onStop()
    {
    	super.onStop();
    	if(boundToCrimeTrackerService)
    	{
    		unbindService(crimeTrackerServiceConnection);
    		boundToCrimeTrackerService = false;
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Log.w(TAG, "Item ID: " + String.valueOf(item.getItemId()));
    	switch (item.getItemId())
    	{
    		case R.id.action_create_event:
    			addRow();
    			return true;
    	}
    	return true;
    }

    private void addRow()
    {
    	Log.w(TAG, "Creating Event!!!");
    	
    	Resources res = getResources();
    	RowMeta rowMeta = new RowMeta("New Event: " + counter, false);
    	EventRowHandler eventRowHandler = new EventRowHandler(this, rowMeta);
    	
    	
    	int rowHeight = res.getInteger(R.integer.row_height);
    	
        TableLayout eventsTable = (TableLayout)findViewById(R.id.events_table);

    	
        
    	TableRow row = new TableRow(this);
    	
    	row.setPadding(10, 5, 5, 10);
    	row.setClickable(true);
    	row.setOnClickListener(eventRowHandler);
    	row.setMinimumHeight(rowHeight);
    	
    	TextView text = new TextView(this);
    	text.setText(rowMeta.getRowText());
    	row.addView(text);
    	eventsTable.addView(row);

    	rows.add(rowMeta);
    	counter ++;
    	
    	
    	
    	//This is a bit of a hack to add a spacer between rows
    	TableRow spacer = new TableRow(this);
    	spacer.setMinimumHeight(1);
    	spacer.setBackgroundColor(0XFF888888);
    	eventsTable.addView(spacer);
    	
    	//Testing out some messenger stuff!
    	Message msg = Message.obtain(null, 1, 0, 0);
    	msg.replyTo = crimeTrackerInputMessenger;
    	try 
    	{
			crimeTrackerOutputMessenger.send(msg);
		} 
    	catch(RemoteException e) 
    	{
    		Log.e(TAG, "Errro sending message to crimeTrackerSerice", e);
		}
    	
    }
    
    
    
    
    
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger crimeTrackerInputMessenger = new Messenger(new IncomingHandler());
    
    /** Messenger for communicating with the service. */
    Messenger crimeTrackerOutputMessenger = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean boundToCrimeTrackerService;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection crimeTrackerServiceConnection = new ServiceConnection() 
    {
    	@Override
        public void onServiceConnected(ComponentName className, IBinder service) 
        {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            crimeTrackerOutputMessenger = new Messenger(service);
            boundToCrimeTrackerService = true;
        }

    	@Override
        public void onServiceDisconnected(ComponentName className) 
        {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            crimeTrackerOutputMessenger = null;
            boundToCrimeTrackerService = false;
        }
    };
    
    
    /**
     * Handler of incoming messages from clients.
     */
    private static class IncomingHandler extends Handler 
    {
        @Override
        public void handleMessage(Message msg) 
        {
        	
            switch (msg.what) 
            {
                case 2:
                	Log.w(TAG, "Got Response from crimeTrackerService!");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    
}
