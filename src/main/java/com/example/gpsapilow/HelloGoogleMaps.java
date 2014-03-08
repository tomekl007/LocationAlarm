package com.example.gpsapilow;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
//import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnLongClickListener;


public class HelloGoogleMaps extends MapActivity {
	public static final String PREFS_NAME = "MyPrefsFile";
	public String lng;
	public String lat;
	public String lngPoint;
	public String latPoint;
	DestinationRadiusOverlay dro;
	 HelloItemizedOverlay itemizedoverlay;
	 List<Overlay> mapOverlays;
	 OverlayItem overlayitemDest;
	boolean destSet = false;
	private String fromHistory="false";
	Button submit;
	boolean noTouch=true;
	
	
	 GestureDetector mGestureDetector ; 
	
	//Inside every MapActivity, the isRouteDisplayed() method is required, so override this method: 
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HelloGoogleMaps","onCreate");
        setContentView(R.layout.activity_hello_google_maps);
       
        
       final MapView mapView = (MapView) findViewById(R.id.mapview);
       
        
    
       
       mapOverlays = mapView.getOverlays();
 
      Drawable drawable = this.getResources().getDrawable(R.drawable.homemarker);
       
  
        itemizedoverlay = new HelloItemizedOverlay(drawable, this);
              
        
        //Now create a GeoPoint that defines the map coordinates for the first overlay item, 
        //and pass it to a new {@code OverlayItem}:

        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
 
        GeoPoint point2 = new GeoPoint(35410000, 139460000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Sekai, konichiwa!", "I'm in Japan!");
        
        itemizedoverlay.addOverlay(overlayitem2);
        
        
    
     
        
        
        mapOverlays.add(itemizedoverlay);
        
        
        submit = (Button) findViewById(R.id.googlemaps_select_location);
        
      
      //  mapView.setLongClickable(true); 
    /*    mapView.setOnLongClickListener(new OnLongClickListener(){

			@TargetApi(11)
			public boolean onLongClick(View v) {
				if (v == mapView) {
					System.out.println("onLognClick return true");
					Log.d("s", "long");
					return true;
					
				}
				System.out.println("onLognClick return false");
				return false;
			}
        });
      */  
        
        
       mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
    	
    	 
    	   

            @Override
            public void onLongPress(MotionEvent event) {
            	System.out.println("onLongPressed");
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                    Toast.makeText(getBaseContext(),                             
                        p.getLatitudeE6() / 1E6 + "," + 
                        p.getLongitudeE6() /1E6 ,                             
                        Toast.LENGTH_SHORT);//.show();//uncomment to show toast
                    
                 
        
                    lng = Double.toString(p.getLongitudeE6()/1E6);
                    lat = Double.toString(p.getLatitudeE6() / 1E6);
                lngPoint = Double.toString(p.getLongitudeE6() );
                latPoint = Double.toString(p.getLatitudeE6() );
                    
                   // SavePreferences();
                    int sourceLng = p.getLongitudeE6();
                    int sourceLat = p.getLatitudeE6();
                    //tutaj skonczylem mam tu zrobic okrag---------------------
                   // GeoPoint geo = new GeoPoint(lat,lng);
                    GeoPoint geoPoint = new GeoPoint(sourceLat,sourceLng);
                  //  dro.setSource(geoPoint, 4);
                   // itemizedoverlay.addOverlay(dro);
                    overlayitemDest = new OverlayItem(geoPoint, "Youre", "Destination");
                   

                    System.out.println("->before cleen map");
                    itemizedoverlay.cleanAllMap();
                    itemizedoverlay.addOverlay(overlayitemDest);
                    destSet = true;
                    
                SavePreferences();
                fromHistory="false";    
                noTouch=false;
                
                //enable savePoint button
                Button savePoint = (Button) findViewById(R.id.goToSavePoint);
              	savePoint.setEnabled(true);
                    
            }
        });
       
       mGestureDetector.setIsLongpressEnabled(true);
        	
       	 
        mapView.setOnTouchListener(new OnTouchListener(){
        	
            public boolean onTouch(View arg0, MotionEvent event) {
            	 return mGestureDetector.onTouchEvent(event);
//            	 
//            	System.out.println("onTouchEvent");
//                  if (event.getAction() == 0) { // && event.getAction() != 2 
//                	 System.out.println("in == 1");
//                	  return mGestureDetector.onTouchEvent(event);
//                  	//return true;
//                  
//                  
//                  }
//                  //else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//                    //  Log.d("TouchTest", "Touch up");
//                  //}
//                  
//                  
//                      return false;
                  }
          });
          
          mapView.setClickable(true);
          mapView.setFocusableInTouchMode(true);
          mapView.setEnabled(true);
         // mapView.setLongClickable(true);
        
        //-----
          Intent intent = getIntent();
          
          fromHistory  = intent.getStringExtra("fromHistory");
       //   lat = intent.getStringExtra("lat");
       //   SavePreferences();
          
          
          //disable save point if is empty
       
          	Button savePoint = (Button) findViewById(R.id.goToSavePoint);
          	savePoint.setEnabled(false);
         
      
        }
    @TargetApi(9)
	@Override
    public void onResume(){
    	super.onResume();
    	System.out.println("hellogooglemaps.onResume");
    	 
    	 String lngPoint = LoadPreferences("lng");
         String latPoint = LoadPreferences("lat");
         if(!(lngPoint.equals("empty") && latPoint.equals("empty"))  ){
        	 
        // = Integer.parseInt(lngPoint);
        // int sourceLat = Integer.parseInt(latPoint);
         int sourceLng = Integer.parseInt(lngPoint.replaceAll("\\.","") );
         int sourceLat = Integer.parseInt(latPoint.replaceAll("\\.","") );
         //tutaj skonczylem mam tu zrobic okrag---------------------
         GeoPoint geo = new GeoPoint(sourceLat,sourceLng);

         overlayitemDest = new OverlayItem(geo, "Youre", "Destination");

         itemizedoverlay.addOverlay(overlayitemDest);
         //scroll view to specyfic point
         MapView mapView = (MapView) findViewById(R.id.mapview);
         MapController mc=mapView.getController();
         mc.animateTo(geo);
         //mc.zoomIn();
         //mapView.setX( Float.parseFloat(lngPoint));
         //mapView.setY(Float.parseFloat(latPoint) );,
        
         
         }
         
         //disable save point if is empty
         if(lngPoint.isEmpty() || latPoint.isEmpty() 
        		 || lngPoint.contains("empty") || latPoint.contains("empty") ){
         	Button savePoint = (Button) findViewById(R.id.goToSavePoint);
         	savePoint.setEnabled(false);
          }
//    	
    }
    /**
     * save Coords to shared preferences
     * @param view
     */
    public void saveCoords(View view){
    	
    	
    	
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_LONG;
        String text = "Musisz zaznaczyc punkt";
        Toast toast = Toast.makeText(context, text, duration);
       
    	
    //	if(lng.contains("empty") || lat.contains("empty") || lngPoint.contains("empty") || latPoint.contains("empty") ){
        if(noTouch){
        	String lng = LoadPreferences("lng");
        	String lat = LoadPreferences("lat");
        	String latPoint=LoadPreferences("latPoint");
        	String lngPoint=LoadPreferences("lngPoint");
    //	if(lat==null || lng==null){
        	if(lng.contains("empty") || lat.contains("empty") || lngPoint.contains("empty") || latPoint.contains("empty") ){
    		 toast.show();
        	}
        	else{
        		  Intent intent = new Intent(this, SelectRadius.class);
        	    	
        	      startActivity(intent);
        	}
    	}else{
    	
    	System.out.println("tutaj zapisze wspolrzedne");
    	if(!(fromHistory.equals("true")) )
    	SavePreferences();
    	
        Intent intent = new Intent(this, SelectRadius.class);
    	
    	startActivity(intent);
    	}
    	
    }
    
    
    private void SavePreferences(){
  	   
       // String defaultString = "empty";
        //String loc ="";
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lng", lng);
        editor.putString("lat", lat);
        editor.putString("lngPoint", lngPoint);
        editor.putString("latPoint", latPoint);
        		System.out.println(lngPoint + "  "+ latPoint);
        editor.commit();
        
     
       }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hello_google_maps, menu);
        return true;
    }
    
    private String LoadPreferences(String key){
   	   
        String defaultString = "empty";
        String location ="";
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_WORLD_READABLE);
        location =  sharedPreferences.getString( key, defaultString );
        System.out.println("loadRestore key = " + location);
        	
        return location;
       
   
       }
    
    public void goToSavePoint(View view){
    	SavePreferences();

        Intent intent = new Intent(this, SavePoint.class);
    	
    	startActivity(intent);
    	
    }
    
    
    
    
}
