package com.example.gpsapilow;




import android.os.CountDownTimer;
import java.util.Calendar;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;


public class Gps extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
    private TextView latituteField;
	private TextView longitudeField;
	LocationManager locationManager;
	LocationListener locationListener;
	Location currentLocation;
	String lng;
	String lat;
	RectArea ra;
	CountDownTimer timer;
	boolean timesOutFlag = false;
	boolean loadingFlag  = true;
	//final 
	String repeat ="false";
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	System.out.println("Gps.onCreate()");
       
    	
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.activity_gps);


		  
		  
      

        initProgressBar();
        String lng= LoadPreferences("lng");
     	String lat = LoadPreferences("lat");
     	double destLng;
     	double destLngt ;
     	try{
     	destLng = Double.parseDouble(lng);
     	destLngt = Double.parseDouble(lat);
     	}catch(NumberFormatException e){
     		destLng=0;
     		destLngt=0;
     	}
     	String r = LoadPreferences("radius");
     	int radius = Integer.parseInt(r);
     	
     	SavePreferences("alarmConfirmed", "false");
     	
        ra = new RectArea(destLng,destLngt,radius);
        
        
        //timer - count until last getting data from gps
        int oneSecond = 1000;
        String temp = LoadPreferences("time");
        int time = Integer.parseInt(temp) * 1000 * 60;
        System.out.println("int time : "  + time);
        timer = new CountDownTimer(time ,oneSecond){

			@Override
			public void onFinish() {
				System.out.println("i don't receive information from gps fixed time");
				String didUserConfirmed = LoadPreferences("alarmConfirmed");
				 if(didUserConfirmed.equals("false")){
				   timesOutFlag = true;
				   setAlarm(true);
				  
				 }
				
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				System.out.println("secondRemaining : " + millisUntilFinished / 1000);
				
			}
			
			
        	
        }.start();
        
   	// Acquire a reference to the system Location Manager
   	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
   	System.out.println("lm" + locationManager);
  
   	
   	

   	// Define a listener that responds to location updates
   	 locationListener = new LocationListener() {
   	    public void onLocationChanged(Location location) {
   	    	
   	    	//restart the timer
   	    	timer.cancel();
   	    	timer.start();
   	    	
   	    	System.out.println("onlocationChanged");
   	     if(location == null){
   	   	 System.out.println("location is null!");
   	     }
   	     //  Called when a new location is found by the network location provider.
   	     if(currentLocation == null)
   	    	 currentLocation = location;
   	     else{
   	    	
   	    	 if(isBetterLocation(location, currentLocation) ){
   	    		 System.out.println("isBetterLocation true");
   	    	 currentLocation = location;
   	    	 }
   	    		 
   	     }
   	    	currentLocation = location;
   	     
   	      makeUseOfNewLocation(currentLocation);
   	    }

   	    public void onStatusChanged(String provider, int status, Bundle extras) {}

   	    public void onProviderEnabled(String provider) {}

   	    public void onProviderDisabled(String provider) {}
   	  };

   	// Register the listener with the Location Manager to receive location updates
   	  System.out.println("before requestLocationUpdates");
   	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
   	
   	//uncoment on real device
   	
   	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
   	List<String> allProviders = locationManager.getAllProviders();
   	System.out.println(allProviders);
   	
   	
  
    }
    
    @Override
    public void onResume(){
    super.onResume();	
    System.out.println("GPS.onResume");	
    String didUserConfirmed = LoadPreferences("alarmConfirmed");
    
    if(didUserConfirmed.equals("true")){
    
    }
    
    }
    
    public void initProgressBar(){
    	final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
    	if(pb != null){
    
       final Intent intent = new Intent(this, MainActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	Thread bg  = new Thread(new Runnable() {
			int interval = 400;
			public void run() {
				for(int i = 0;i < 10; i++){
					pb.incrementProgressBy(1);
					try {
						Thread.currentThread().sleep(interval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			
				
			    startActivity(intent);
			}   
		});
    	bg.start();
    	
   
    }
  }
    public void makeUseOfNewLocation(Location location){
		   double lat =  location.getLatitude();
		   System.out.println("lat = " + lat);
		  
		   double lng = location.getLongitude();
		   System.out.println("lng = " + lng);
			  
		   
		    loadingFlag = false;
		    
		    String didUserConfirmed = LoadPreferences("alarmConfirmed");
		    
		    if(didUserConfirmed.equals("false")){
		    if(ra.containts(lng, lat)){
		    	setAlarm(true);
		    }
		    }

		    
		    
		  
	  }
    
    
    public boolean compareLong(double sourceLat, double sourceLng){
    	
    	//load destination coords
    	String lng= LoadPreferences("lng");
     	String lat = LoadPreferences("lat");
     	double destLng = Double.parseDouble(lng);
     	double destLat = Double.parseDouble(lat);
     	
     	
     	
     	//comparing sourceCoord(get from gps) with destCoord(given by user at start of application)
     	if( destLng - sourceLng <= 0.40){
     		
     		return true;
     	}
     	
    	
    	return false;
    	
    }
    
    
    //need to be checked on real devices
   //@TargetApi(9)
public void setAlarm(boolean b){
	   
	
	   System.out.println("setAlarm");
	   
	   
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

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
        //vibrate();
     //   if(b == true){
        r.play();
        int duration = Toast.LENGTH_LONG;
        
        if(timesOutFlag == true){
        Context context = getApplicationContext();
        String time = LoadPreferences("time");
        String text = "brak polaczenia przez " + time + " minute ";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        
        Intent intent = new Intent(this, SignalLost.class);
        startActivity(intent);
        
        }
        
        
        //in case that user dont press confirm
        
        Thread bg  = new Thread(new Runnable() {
			int interval = 1000;
			public void run() {
				for(int i = 0;i < 30; i++){ //30 seconds
					
					try {
						Thread.currentThread().sleep(interval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("stop");
				r.stop();
			}   
		});
        bg.start();
        
        //checking did user pressed confirm
        Thread bg2  = new Thread(new Runnable() {
			int interval = 1000;
			String didUserConfirmed = "false";
			public void run() {
				for(;;){ //30 seconds
					didUserConfirmed = LoadPreferences("alarmConfirmed");
					repeat = LoadPreferences("repeat");
					if(didUserConfirmed.equals("true") && !repeat.equals("true")){
						r.stop();
					}else if(didUserConfirmed.equals("true") && repeat.equals("true")){
						r.stop();
						Log.d("if repeat"," true"); 
						timer.start();
						SavePreferences("repeat","false");
						SavePreferences("didUserConfirmed","false");
					}
					try {
						Thread.currentThread().sleep(interval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//System.out.println("userConfirmed - stop");
				
			}   
		});
        bg2.start();
        
        
        
        
        if(timesOutFlag == false){
        //destination is reachead so tell it to user
        Log.d("gotoTargetReached", "gps");
    	
    	Intent intent = new Intent(this, TargetReached.class);
    	
    	startActivity(intent);
        }
    
    	
    }

private void vibrate() {
    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    if(v==null)
    	return ;

    long[] pattern = { 0, 500, 200 };

    v.vibrate(pattern, 0);
}
    
    
    
    
    
    
    
    
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /** Determines whether one Location reading is better than the current Location fix
      * @param location  The new Location that you want to evaluate
      * @param currentBestLocation  The current Location fix, to which you want to compare the new one
      */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
    	System.out.println("in is BetterLocation();");
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    
   
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
   
          System.out.println("gps.onDestory()");
    	
    	
    	
    }
    
    public void submit(View view){
    
    		
    		Intent intent = new Intent(this, HelloGoogleMaps.class);
    	    
    	    startActivity(intent);
    	moveTaskToBackground();
    	
    }
    
    public void moveTaskToBackground(){
    	//tutaj ustawiam gps i po tym wysylam aplikacje do background
    	boolean didMove = this.moveTaskToBack(true);
    	if(didMove){
    		System.out.println("task was moved to back");
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
    
    
    private void SavePreferences(String key, String value){
 	   
        // String defaultString = "empty";
         //String loc ="";
         SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
        
         editor.putString(key , value);
         
         
         editor.commit();
     
        }
   
    
    
	

}

