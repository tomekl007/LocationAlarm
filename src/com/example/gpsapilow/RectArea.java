package com.example.gpsapilow;
import android.util.Log;

import com.example.gpsapilow.Point;



public class RectArea {
	double oneKm = 0.009;
	Point rt;
	Point lt;
	Point rb;
	Point lb;
	
	public RectArea(double lng, double lat, int radius){
		System.out.println("get lng, lat radius in Rect ares " + lng);
		double temp1 = lat+(0.5*radius*oneKm);
		double temp2 = lng+(0.5*radius*oneKm);
		rt = new Point(temp1,temp2);
		
		temp1 = lat+(0.5*radius*oneKm);
		temp2 = lng-(0.5*radius*oneKm);
		lt = new Point(temp1,temp2);
		

		temp1 = lat-(0.5*radius*oneKm);
		temp2 = lng+(0.5*radius*oneKm);
		rb = new Point(temp1,temp2);
		
		temp1 = lat-(0.5*radius*oneKm);  
		temp2 = lng-(0.5*radius*oneKm);
		lb = new Point(temp1,temp2);
		
		System.out.println(lt);
		System.out.println(rt);
		System.out.println(lb);
		System.out.println(rb);
		
		
	}
	

	public boolean containts(double lng, double lat){
		Log.d("rectA","get source " + lng + " " +  lat +  " to  " + this.toString());
		
		
		if(lng >= lt.lng && lng <= rt.lng && lat >= lb.lat && lat <= rt.lat){
		    System.out.println("containst return true");
		    Log.d("rectA","ret TRue");
		    return true;
			
		}
		
		System.out.println("containst return false");
		Log.d("rectA","ret false");
		return false;
		
		
	}

}
