package com.iia.searchandfind;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UtilLocationManager implements LocationListener {

	public Context context;
	
	private LocationManager myLocationManager;
	private String provider;
	
	public static LatLng myLocation;
	public static Location myRealLocation;
	
	private GoogleMap map;
	
	// Constructor of the class
	public UtilLocationManager(Context context, MapFragment mapFragment) {
		// get instance of the activity who's called
		this.context = context;
		
		if (mapFragment != null) {
			// get the mapFragment send by activity parent
			this.map = mapFragment.getMap();
			// set the button GetMyLocation() visible
			map.setMyLocationEnabled(true);
			// set the map type
	        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
			// Get the location manager
		    myLocationManager = (LocationManager) context.getSystemService(
		    		Context.LOCATION_SERVICE);
		    // Define the criteria how to select the better location provider
		    provider = myLocationManager.getBestProvider(new Criteria(), true);

		    // Get the last update location
		    Location location = myLocationManager.getLastKnownLocation(provider);
		    
		    if (location != null) {
		    	// set new coordinate of myLocation
		    	myLocation = new LatLng(location.getLatitude(), 
		    			location.getLongitude());
		    	myRealLocation = location;
				
		    	// set a marker on my position
				MarkerOptions options = new MarkerOptions();
				
			    options.position(myLocation);
			    options.title("MyLocation");
			    options.snippet("Hey! It's Me !");
			    options.icon(BitmapDescriptorFactory.
			    		fromResource(R.drawable.ic_launcher));
			    
			    // add the marker on the map
			    map.addMarker(options);

			    // Move the camera instantly to my current Location with a zoom of 15.
			    map.moveCamera(CameraUpdateFactory.
			    		newLatLngZoom(myLocation, 15));

			    // Zoom in, animating the camera.
			    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		    }

		    onResume();
		}
	}
	
	/**
	 * onPause : app in pause -> app remove updates of map location
	 */
	public void onPause() {
		myLocationManager.removeUpdates(this);
	}
	
	/**
	 * onResume : app resume -> app ask updates of the map location
	 */
	public void onResume() {
		myLocationManager.requestLocationUpdates(provider, 100, 0, this);
		if (myLocationManager.getLastKnownLocation(provider) != null) {
			myLocation = new LatLng(
					myLocationManager.getLastKnownLocation(provider).getLatitude(),
					myLocationManager.getLastKnownLocation(provider).getLongitude());
			myRealLocation = myLocationManager.getLastKnownLocation(provider);
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
	    	myLocation = new LatLng(location.getLatitude(), 
	    			location.getLongitude());
	    	myRealLocation = location;
			
			MarkerOptions options = new MarkerOptions();
			
		    options.position(myLocation);
		    options.title("MyLocation");
		    options.snippet("Hey! It's Me !");
		    options.icon(BitmapDescriptorFactory.
		    		fromResource(R.drawable.ic_launcher));
		    
		    map.addMarker(options);

		    // Move the camera instantly to my current Location with a zoom of 15.
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

		    // Zoom in, animating the camera.
		    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		    
			CircleOptions optionsCircle = new CircleOptions();
			optionsCircle.center(myLocation);
			map.addCircle(optionsCircle);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(context, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(context, "Enabled new provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

}
