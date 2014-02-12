package com.iia.searchandfind;

import com.google.android.gms.maps.model.LatLng;
import com.iia.activities.HomeActivity;
import com.iia.activities.ProfilActivity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends Activity implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    
    // record the compass picture angle turned
    private float currentDegree = 0f;
    
    // device sensor manager
    private SensorManager mSensorManager;
 
    TextView tvHeading;
    
    private LatLng myLocation;
    private LatLng toLocation;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);

        image = (ImageView) findViewById(R.id.imageViewCompass);
 
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
 
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        myLocation = new LatLng(48.06323305771986, -0.8115626871585846);
        toLocation = new LatLng(48.06301974415755, -0.8109001815319061);
        
        
        ImageView ivBack = (ImageView) this.findViewById(R.id.back);
        
        ivBack.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(CompassActivity.this,
            			HomeActivity.class);
            	
            	startActivity(intent);
				}
		});
        
        
	}

	@SuppressWarnings("deprecation")
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
        // get the angle around the z-axis rotated
        //float degree = Math.round(event.values[0]);
    	float degree = CalculDegree.ReturnDegree(myLocation, toLocation);
 
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
 
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
 
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}
