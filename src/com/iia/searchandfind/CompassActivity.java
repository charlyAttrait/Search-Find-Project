package com.iia.searchandfind;

import java.net.URL;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class CompassActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    
    // record the compass picture angle turned
    private float currentDegree = 0f;
    
    // device sensor manager
    private SensorManager mSensorManager;
 
    TextView tvHeading;
    TextView distanceTXT;
    
    Chronometer chrono;
    ThreadChronometer chronoThread;
    
    private LatLng myLocation = new LatLng(48.06322768007788, -0.8115197718143463);
    private LatLng toLocation = new LatLng(48.063320892459174, -0.8114419877529144);
    private float orientation;
    private float distance;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);

		// image representation of the compass
        image = (ImageView) findViewById(R.id.imageViewCompass);
 
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        // TextView shown distance of the Location
        distanceTXT = (TextView) findViewById(R.id.distance);
 
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
    	// angle degree to move
    	float degree;
    	
    	// calcul of the fixed angle point to the location
    	orientation = CalculDegree.ReturnDegree(myLocation, toLocation);
    	// calcul of the distance between the 2 locations
    	distance = CalculDegree.GetDistance(myLocation, toLocation);
    	// Indicate distance to the point
    	distanceTXT.setText("Distance : " + distance);
    	
    	// calcul the degree to rotate : angle around the z-axis rotated
    	// -  fixed angle point to the location
		degree = Math.round(event.values[0]) - orientation;

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        
        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF,
                0.5f,
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
    		// TODO Auto-generated method stub
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
