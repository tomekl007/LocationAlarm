package com.example.gpsapilow;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SavePoint extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	DatabaseHelper databaseHelper;
	String TAG = SavePoint.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_point);
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_save_point, menu);
        return true;
    }
    
    @TargetApi(9)
	public void saveToDatabase(View view){
     Log.d(TAG,"saveToDatabase");
    	String lng= LoadPreferences("lng");
     	String lat = LoadPreferences("lat");
     	//double destLng = Double.parseDouble(lng);
     	//double destLngt = Double.parseDouble(lat);
     	EditText editText = (EditText)findViewById(R.id.nazwaRekordu);
     	String pointName = editText.getText().toString();
     	
     	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_LONG;
        String textWithouotName = "Nie podales nazwy";
        Toast toastWithoutName = Toast.makeText(context, textWithouotName, duration);
        
        String textWithoutPoints = "Nie zaznaczyles punktu";
        Toast toastWithoutPoints = Toast.makeText(context, textWithoutPoints, duration);
     	
     	if(!pointName.isEmpty() ){
     		if(!(lng=="empty" | lat=="empty")){
     		databaseHelper.saveRecord(lat, lng, pointName);
     		  Intent intent = new Intent(this, HelloGoogleMaps.class);
     		  startActivity(intent);
     		}else{
     			toastWithoutPoints.show();
     			Intent intent = new Intent(this, HelloGoogleMaps.class);
       		    startActivity(intent);
     		}
     	}else{
     		toastWithoutName.show();
     	}
	   
   
	
    }
    
    private String LoadPreferences(String key){
    	   
        String defaultString = "empty";
        String location ="";
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        location =  sharedPreferences.getString( key, defaultString );
        System.out.println("loadRestore key = " + location);
        
        return location;
       
   
       }
}
