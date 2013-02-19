package com.example.gpsapilow;

import android.location.*;

import android.util.*;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final int PERIOD = 1*60*60*1000;
	
	
	public static final String PREFS_NAME = "MyPrefsFile";
	// SeekBar mSeekBar = null;
	// TextView valueOfSeekBar = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(this, Gps.class);
        PendingIntent pi= PendingIntent.getBroadcast(this, 0, i, 0);

        System.out.println("setReapiting");
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 1000, pi);
        SaveDefaultPreferences();
        
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	System.out.println("MainActivity.onCreteOptionsMenu");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public void onRestoreInstanceState(Bundle b){
    	System.out.println("MainActivity.onRestoreInstanceState");
  //  System.out.println("onRestoreInstanceState bundle szerokosc " + b.getString("szerokosc") );	
  //  Log.d("bundle value", b.getString("szerokosc") );
    	
    	
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	System.out.println("onResume");
    	String lng= LoadPreferences("lng");
     	String lat = LoadPreferences("lat");
     	System.out.println("loadPreferences loaded this : " + lng + lat);
    	
    	//TextView text = (TextView) findViewById(R.id.textView2);
    	//text.setText("");
     	TextView text = (TextView) findViewById(R.id.didDestIsSet);
     	if(lng != "empty" && lat != "empty"){
     		text.setText("Miejsce przeznaczenia jest ustawione");
     	}else{
     		text.setText("");
     	}
     		
    	
    
    }

    
    public void goToGoogleMaps(View view){
    	
    	Log.d("goToGoogleMaps", "mainActivity");
    	
    	Intent intent = new Intent(this, HelloGoogleMaps.class);
	
    
			
		    startActivity(intent);
    }
    
    public void goToNavigation(View view){
    	
    	Log.d("goToNavigation", "mainActivity");
    	
    	Intent intent = new Intent(this, Gps.class);
    	
    	startActivity(intent);
    	
    }
    
    
    
    private String LoadPreferences(String key){
 	   
        String defaultString = "empty";
        String location ="";
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        location =  sharedPreferences.getString( key, defaultString );
        System.out.println("loadRestore key = " + location);
        	
        return location;
       
 
       }
    
    public void moveTaskToBackground(View view){
    	//tutaj ustawiam gps i po tym wysylam aplikacje do background
    	boolean didMove = this.moveTaskToBack(true);
    	if(didMove){
    		System.out.println("task was moved to back");
    	}
    	
    }
    
    public void turnOff(View view){
    	System.out.println("turn off app");
    	SavePreferences("alarmConfirmed", "true");
    	SavePreferences("lat", "empty");
    	SavePreferences("lng", "empty");
    	
    	backgroundApp();
    	
    }
    
    
    public void backgroundApp(){
        
    	this.moveTaskToBack(true);
    	
    }
    
    public void goToHistory(View view){
      Log.d("goToHistory", "mainActivity");
    	
    	Intent intent = new Intent(this, History.class);
    	
    	startActivity(intent);
    }
    
    
    private void SaveDefaultPreferences(){
    	   
        
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //some default values
        editor.putString("radius", "1");
        editor.putString("time", "2");
     //   editor.putString("lng","1.0");
       // editor.putString("lat","1.0");
        
       // System.out.println("put radius : " + radius + ", and timeOf alarm" + withoutSignalTimeOfAlarm);
        editor.putString("alarmConfirmed", "false");
        
        editor.commit();
  
       }
    
    private void SavePreferences(String key, String value){
  	   
        // String defaultString = "empty";
         //String loc ="";
         SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
        
         editor.putString(key , value);
         
         
         editor.commit();
     
        }

    
}
