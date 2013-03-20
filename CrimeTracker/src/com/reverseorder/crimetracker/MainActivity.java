package com.reverseorder.crimetracker;

import java.util.ArrayList;
import java.util.List;

import com.reverseorder.crimetracker.R;
import com.reverseorder.crimetracker.structures.RowMeta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.toString();
	private static int counter = 0;
	private static EventRowHandler eventRowHandler = new EventRowHandler();
	private List<RowMeta> rows = new ArrayList<RowMeta>();
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        	
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
        TableLayout eventsTable = (TableLayout) findViewById(R.id.events_table);

    	RowMeta rowMeta = new RowMeta("New Event: " + counter, false);
        
    	TableRow row = new TableRow(this);
    	
    	row.setPadding(10, 5, 5, 10);
    	row.setClickable(true);
    	row.setOnClickListener(eventRowHandler);
    	row.setMinimumHeight(100);
    	row.setTag(rowMeta);
    	
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
