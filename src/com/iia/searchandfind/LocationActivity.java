package com.iia.searchandfind;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends Activity implements LocationListener {

	public AppSQLiteOpenHelper dbHelper;
	public SQLiteDatabase db;
	
	private LocationManager myLocationManager;
	private String provider;
	
	public LatLng myLocation = new LatLng(48.06323018966061, -0.8115211129188538);
	
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize database Helper
		//dbHelper = new AppSQLiteOpenHelper(MainActivity.this, "MyDb", null, 1);
		
		// Get Db instance
		//db = dbHelper.getWritableDatabase();
		
		// ...
		initilizeMap();
		
		// Get the location manager
	    myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the location provider -> use
	    // default
	    provider = myLocationManager.getBestProvider(new Criteria(), true);

	    // Ask position update
	    myLocationManager.requestLocationUpdates(
                provider,
                10000,
                0, 
                this);

	    // Get the last update location
	    Location location = myLocationManager.getLastKnownLocation(provider);
	    
	    myLocation = new LatLng(location.getLatitude(), location.getLongitude());
		
		MarkerOptions options = new MarkerOptions();
		
	    options.position(myLocation);
	    options.title("MyLocation");
	    options.snippet("Coucou !");
	    options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
	    
	    map.addMarker(options);

	    // Move the camera instantly to my current Location with a zoom of 15.
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

	    // Zoom in, animating the camera.
	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	    
		CircleOptions optionsCircle = new CircleOptions();
		optionsCircle.center(myLocation);
		map.addCircle(optionsCircle);
	}
	
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.fragment_map)).getMap();
 
            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
    		map.setMyLocationEnabled(true);
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLocationManager.removeUpdates(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myLocationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		myLocation = new LatLng(location.getLatitude(), location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}
