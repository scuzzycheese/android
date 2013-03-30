package com.reverseorder.crimetracker;

import java.util.ArrayList;
import java.util.List;

import com.reverseorder.crimetracker.R;
import com.reverseorder.crimetracker.structures.RowMeta;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity 
{

	private static final String TAG = MainActivity.class.toString();
	private static int counter = 0;
	private List<RowMeta> rows = new ArrayList<RowMeta>();
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent mServiceIntent = new Intent(this, CrimeTrackerService.class);
        mServiceIntent.putExtra("TestDataKey", "TestDataValue");
        startService(mServiceIntent);
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
    }
}
