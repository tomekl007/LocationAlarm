package com.example.gpsapilow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class History extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	DatabaseHelper databaseHelper;
	HistoryAdapter historyAdapter;
	

	String TAG = History.class.getCanonicalName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView listView = (ListView )findViewById(R.id.history_list);
         Log.d(TAG, "onCreate");
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllRecors();
        
        if(cursor.moveToFirst()){
        	
        	do{
        	String lng = cursor.getString(1);
        	String lat = cursor.getString(2);
        	String name = cursor.getString(3);
        	Log.d(TAG," " + lng + " " + lat + " " + name);
        	}while(cursor.moveToNext());
        	
        }
        
      //  if(!cursor.isClosed()){
      //  	cursor.close();
     //   }
       
        historyAdapter = new HistoryAdapter(this, cursor);
        listView.setAdapter(historyAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Log.d(TAG,"onItemCLick");
			goToGoogleMaps(view);
				
			}
        	
        });
		
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history, menu);
        return true;
    }
    
    
    public void goToGoogleMaps(View view){
    	TextView textLng = (TextView)view.findViewById(R.id.lng);
        String lng = (String) textLng.getText();
        TextView textLat = (TextView)view.findViewById(R.id.lat);
        String lat = (String) textLat.getText();
        Log.d(TAG,"lat : " + lat + " lng " + lng);
        SavePreferences("lat", lat);
        SavePreferences("lng", lng);
    	
    	Intent intent = new Intent(this, HelloGoogleMaps.class);
    	intent.putExtra("fromHistory", "true");
    	//intent.putExtra("lat", lat);
    	startActivity(intent);
        
    	 
    	
    }
    
    private void SavePreferences(String key, String value){
   	   
        // String defaultString = "empty";
         //String loc ="";
         SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString(key,value);
         
         
   
         editor.commit();
         
      
        }
}
