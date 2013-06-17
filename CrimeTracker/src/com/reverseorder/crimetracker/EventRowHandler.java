package com.reverseorder.crimetracker;

import com.reverseorder.crimetracker.structures.RowMeta;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class EventRowHandler implements OnClickListener
{
	private static final String TAG = EventRowHandler.class.toString();
	private Activity callingActivity = null;
	private RowMeta rowMeta = null;
	private static Handler handler = new Handler(); 
	private TableRow selectedRow = null;
	
	
	EventRowHandler(Activity callingActivity, RowMeta rowMeta)
	{
		this.callingActivity = callingActivity;
		this.rowMeta = rowMeta;
	}
	
	
	@Override
	public void onClick(View v) 
	{
		Resources res = callingActivity.getResources();
		
		Log.w(TAG, "Row clicked: " + ((TextView)((TableRow)v).getChildAt(0)).getText());
		selectedRow = (TableRow)v;
		
		selectedRow.setBackgroundColor(res.getColor(R.color.row_highlighted));
		rowMeta.setHighlighted(true);
		
	    Intent intent = new Intent(callingActivity, EventDetailsActivity.class);
	    callingActivity.startActivity(intent);
	    
	    //Sneaky way to run highlight the row
	    handler.postDelayed(unHighlightRow, res.getInteger(R.integer.unhighlight_after_ms));
	}
	
	private Runnable unHighlightRow = new Runnable() 
	{
		@Override
		public void run()
		{	
			Resources res = callingActivity.getResources();
			selectedRow.setBackgroundColor(res.getColor(R.color.row_unhighlighted));
			rowMeta.setHighlighted(false);	    
		}
	};
}
