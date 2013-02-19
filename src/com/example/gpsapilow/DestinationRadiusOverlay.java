package com.example.gpsapilow;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class DestinationRadiusOverlay extends Overlay{
	
	 private GeoPoint sourcePoint;
	    private float radius;

	    public DestinationRadiusOverlay() {
	        super();
	    }

	    public void setSource(GeoPoint geoPoint, float radius) {
	        sourcePoint = geoPoint;
	        this.radius = radius;
	    }

	    @Override
	    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	        super.draw(canvas, mapView, false);
	        Projection projection = mapView.getProjection();
	        Point center = new Point();

	        int radiusInPixels = (int) (projection.metersToEquatorPixels(radius));
	        projection.toPixels(sourcePoint, center);

	        Paint radiusPaint = new Paint();
	        radiusPaint.setAntiAlias(true);
	        radiusPaint.setStrokeWidth(2.0f);
	        radiusPaint.setColor(0xff2F92F5);
	        radiusPaint.setStyle(Paint.Style.STROKE);

	        canvas.drawCircle(center.x, center.y, radiusInPixels, radiusPaint);

	        radiusPaint.setColor(0x182F92F5);
	        radiusPaint.setStyle(Paint.Style.FILL);
	        canvas.drawCircle(center.x, center.y, radiusInPixels, radiusPaint);

	    }

}
