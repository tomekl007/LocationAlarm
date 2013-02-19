package com.example.gpsapilow;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class HistoryAdapter extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public HistoryAdapter(Context context, Cursor c) {
		super(context, c);
		
	}
	

	@Override//to populate the view with data
	public void bindView(View view, Context arg1, Cursor cursor) {
		TextView nameText = (TextView) view.findViewById(R.id.name);
		nameText.setText(cursor.getString(cursor.getColumnIndex("name")));
		
		TextView latText = (TextView) view.findViewById(R.id.lat);
		latText.setText(cursor.getString(cursor.getColumnIndex("lat")));
	
		TextView lngText = (TextView) view.findViewById(R.id.lng);
		lngText.setText(cursor.getString(cursor.getColumnIndex("lng")));
		
		
	}

	@Override//to create the view
	public View newView(Context arg0, Cursor arg1, ViewGroup parent) {
		LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.activity_history, parent, false);
		
		return view;
	}
	

}
