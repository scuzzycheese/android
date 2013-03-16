package com.reverseorder.crimetracker;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class EventRowHandler implements OnClickListener
{

	private static final String TAG = EventRowHandler.class.toString();
	
	@Override
	public void onClick(View v) {
		Log.w(TAG, "Row clicked: " + ((TextView)((TableRow)v).getChildAt(0)).getText());
		
	}

}
