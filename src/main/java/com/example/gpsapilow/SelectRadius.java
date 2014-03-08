package com.example.gpsapilow;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SelectRadius extends Activity {
	String withoutSignalTimeOfAlarm ;
    String radius;
	
	public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_radius);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_select_radius, menu);
        return true;
    }
    
    public void submit(View view){
    	EditText t1 = (EditText) findViewById(R.id.radiusText);
    	EditText t2 = (EditText) findViewById(R.id.timeWithoutSignalText);
    	radius = t1.getText().toString();
    	withoutSignalTimeOfAlarm =  t2.getText().toString();
    	
    	String decimalPattern = "([0-9]*)\\.([0-9]*)";  
    	 
    	boolean matchRadius = Pattern.matches(decimalPattern, radius);
    	boolean matchWithoutSig = Pattern.matches(decimalPattern,withoutSignalTimeOfAlarm);
    	//System.out.println(match); 
    	//if true then decimal else not
    	
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_LONG;
    	
        String text = "musisz wprowadzic wartosci calkowiete";
        Toast toast = Toast.makeText(context, text, duration);
        
        String text2 = "musisz wprowadzic wartosc";
        Toast toast2 = Toast.makeText(context, text2, duration);
    	
    	if(matchRadius || matchWithoutSig){
    		toast.show();
    		
    	}else if(radius.isEmpty() || withoutSignalTimeOfAlarm.isEmpty()){
    	toast2.show();
    	
    	}else{
    	
    	
    	SavePreferences();
    	
    	
       Intent intent = new Intent(this, Gps.class);
       startActivity(intent);

    	}
    	
    	
    }
    
    public void moveTaskToBackground(){
    	//tutaj ustawiam gps i po tym wysylam aplikacje do background
    	boolean didMove = this.moveTaskToBack(true);
    	if(didMove){
    		System.out.println("task was moved to back");
    	}
    	
    }
    
    private void SavePreferences(){
   	   
     
         SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putString("radius", radius);
         editor.putString("time", withoutSignalTimeOfAlarm);
         System.out.println("put radius : " + radius + ", and timeOf alarm" + withoutSignalTimeOfAlarm);
         editor.putString("alarmConfirmed", "false");
         
         editor.commit();
   
        }
}
