package com.iia.activities;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.iia.data.Classes.Item;
import com.iia.searchandfind.CalculDegree;
import com.iia.searchandfind.UtilLocationManager;
import com.iia.searchandfind.R;

public class CompassActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    
    public final static String BUNDLE_ITEM = "item";
    
    // record the compass picture angle turned
    private float currentDegree = 0f;
    private float orientation;
    private float distance;
    
    // device sensor manager
    private SensorManager mSensorManager;
 
    TextView tvHeading;
    TextView distanceTXT;
    
    Chronometer chrono;
    ThreadChronometer chronoThread;
    
    private LatLng myLocation;
    private LatLng toLocation;
 
    private UtilLocationManager utilLocationManager;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);

		// image representation of the compass
        image = (ImageView) findViewById(R.id.imageViewCompass);
 
        // TextView shown distance of the Location
        distanceTXT = (TextView) findViewById(R.id.distance);
 
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        // Get item to SearchANDFind
        Item item = (Item) this.getIntent().getExtras().getSerializable(BUNDLE_ITEM);
        
        utilLocationManager = new UtilLocationManager(CompassActivity.this,
				(MapFragment) getFragmentManager().
				findFragmentById(R.id.fragment_map));
        // get my current location
        myLocation = UtilLocationManager.myLocation;
        // define the destination point
        toLocation = new LatLng(item.getCoordLat(), item.getCoordLong());
        
        // Init chronometer and start the thread
        chrono = (Chronometer) findViewById(R.id.chronometer);
        chronoThread = new ThreadChronometer();
        chronoThread.execute();
	}

	@Override
    protected void onResume() {
        super.onResume();
        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        utilLocationManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
        utilLocationManager.onPause();
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
    	// update my current location
    	utilLocationManager.onResume();
    	myLocation = UtilLocationManager.myLocation;
    	
    	// angle degree to move
    	float degree;
    	
    	Location from = new Location("");
    	from.setLatitude(myLocation.latitude);
    	from.setLongitude(myLocation.longitude);
    	Location to = new Location("");
    	to.setLatitude(toLocation.latitude);
    	to.setLongitude(toLocation.longitude);
    	
    	// calcul of the fixed angle point to the location
    	//orientation = CalculDegree.returnDegree(myLocation, toLocation);
    	orientation = from.bearingTo(to);
    	// calcul of the distance between the 2 locations
    	//distance = CalculDegree.getDistance(myLocation, toLocation);
    	float[] result = new float[1];
    	Location.distanceBetween(myLocation.latitude, 
    			myLocation.longitude, 
    			toLocation.latitude, 
    			toLocation.longitude, 
    			result);
    	
    	distance = result[0];
    	
    	if (distance <= 1) {
			chrono.stop();
			Toast.makeText(getBaseContext(), "You're arrived !!!".toString(),
					Toast.LENGTH_SHORT).show();
		} else {

	    	// calcul the degree to rotate : angle around the z-axis rotated
	    	// -  fixed angle point to the location
			degree = Math.round(event.values[0]) - orientation;
			
	    	// Indicate distance to the point
	    	distanceTXT.setText("Distance : " + distance + " m (" + distance / 1000 + " km)");
	    	
	        // create a rotation animation (reverse turn degree degrees)
	        RotateAnimation ra = new RotateAnimation(
	                currentDegree,
	                -degree,
	                Animation.RELATIVE_TO_SELF, 0.5f,
	                Animation.RELATIVE_TO_SELF,
	                0.5f);
	 
	        // how long the animation will take place
	        ra.setDuration(210);
	 
	        // set the animation after the end of the reservation status
	        ra.setFillAfter(true);
	 
	        // Start the animation
	        image.startAnimation(ra);
	        currentDegree = -degree;
		}
    }
 
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
    
	private class ThreadChronometer extends AsyncTask<URL, Integer, Long> {

    	/* (non-Javadoc)
    	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
    	 */
    	@Override
    	protected Long doInBackground(URL... params) {
    		// update myLocation by myCurrentLocation
    		utilLocationManager.onResume();
    		Log.d("LOCATION", myLocation.toString());
            return null;
    	}

    	/* (non-Javadoc)
    	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
    	 */
    	@Override
    	protected void onPostExecute(Long result) {
    		
    	}

    	/* (non-Javadoc)
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			// Stop the chronometer
			chrono.stop();
		}

		/* (non-Javadoc)
    	 * @see android.os.AsyncTask#onPreExecute()
    	 */
    	@Override
    	protected void onPreExecute() {
    		// Define chronometer format
    		chrono.setBase(SystemClock.elapsedRealtime());
    		// Start the chronometer
            chrono.start();
    	}

    	/* (non-Javadoc)
    	 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
    	 */
    	@Override
    	protected void onProgressUpdate(Integer... values) {
    	}

    }
}
