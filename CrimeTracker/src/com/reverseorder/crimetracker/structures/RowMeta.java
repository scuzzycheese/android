package com.reverseorder.crimetracker.structures;

public class RowMeta 
{
	private String rowText;
	private boolean highlighted;
	
	public RowMeta(String rowText, boolean highlighted)
	{
		super();
		this.rowText = rowText;
		this.highlighted = highlighted;
	}
	
	public String getRowText() {
		return rowText;
	}
	public void setRowText(String rowText) {
		this.rowText = rowText;
	}
	public boolean isHighlighted() {
		return highlighted;
	}
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
