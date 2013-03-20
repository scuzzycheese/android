package com.reverseorder.crimetracker;

import com.reverseorder.crimetracker.structures.RowMeta;

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
		RowMeta rowMeta = (RowMeta)v.getTag();
		Log.w(TAG, "Row clicked: " + ((TextView)((TableRow)v).getChildAt(0)).getText());
		TableRow selectedRow = (TableRow)v;
		
		
		boolean highlightValue = rowMeta.isHighlighted();
		
		selectedRow.setBackgroundColor(highlightValue ? 0x00000000 : 0xFFC0C0FF);
		
		rowMeta.setHighlighted(!highlightValue);
	}

}
