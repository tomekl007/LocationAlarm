package com.example.gpsapilow;

public class Point {
	
	double lat;
	double lng;
	
	public Point(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	
	@Override
	public String toString(){
		
		return "latitude(y) : " + lat + " longtitude (x) : " + lng;
	}
}
