package com.example.gpsapilow;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class TargetReached extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_reached);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_target_reached, menu);
        return true;
    }
    
    
    
    public void turnOffAlarm(View view){
 	   
    	
 	   System.out.println("getAlarm");
 	   
 	   
 	    Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         if(alert == null){
             // alert is null, using backup
             alert =
 RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
             if(alert == null){  // I can't see this ever being null
                                ///(as always have a default notification) but just incase
                 // alert backup is null, using 2nd backup
                 alert =
 RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
             }
         }

         Ringtone r =
 RingtoneManager.getRingtone(getApplicationContext(), alert);
         //vibrate();
        // r.play();
         System.out.println("r.stop");
        // r.stop();
         
         SavePreferences("alarmConfirmed", "true");
         SavePreferences("lng", "empty");
         SavePreferences("lat", "empty");
      
         Intent intent = new Intent(this, MainActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
         
         backgroundApp();
     
        
         }
        
        public void backgroundApp(){
       
        	this.moveTaskToBack(true);
        	
        }
        
     	
     

private void SavePreferences(String key, String value){
	   

     SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
     SharedPreferences.Editor editor = sharedPreferences.edit();
    
     editor.putString(key , value);
     
     
     editor.commit();
 
    }

}
