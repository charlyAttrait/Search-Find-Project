/**
 * 
 */
package com.iia.searchandfind;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author Charly
 *
 */
public class LocationActivity extends MapActivity implements LocationListener {
	private MapView mapView;
	private MapController mc;

	private LocationManager lm;

	private double latitude;
	private double longitude;
	private double altitude;
	private float accuracy;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		mc = mapView.getController();
		mc.setZoom(17);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0,
					this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,
				this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	/**
	 * Method called where user location is update 
	 * @param location : new location
	 * Allowed to get informations (altitude, longitude, precision(in meters) )
	 */
	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		altitude = location.getAltitude();
		accuracy = location.getAccuracy();

		String msg = String.format(
				getResources().getString(R.string.new_location), latitude,
				longitude, altitude, accuracy);
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Method called where location source is disabled (GPS, 3G..etc). 
	 * @param provider : name of source disabled
	 */
	@Override
	public void onProviderDisabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Method called where location source is enabled (GPS, 3G..etc). 
	 * @param provider : name of source enabled
	 */
	@Override
	public void onProviderEnabled(String provider) {
		String msg = String.format(
				getResources().getString(R.string.provider_enabled), provider);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Method called where status of the source has changed 
	 * 3 Status enabled : OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE, AVAILABLE.
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String newStatus = "";
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			newStatus = "OUT_OF_SERVICE";
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			newStatus = "TEMPORARILY_UNAVAILABLE";
			break;
		case LocationProvider.AVAILABLE:
			newStatus = "AVAILABLE";
			break;
		}
		String msg = String.format(
				getResources().getString(R.string.provider_disabled), provider,
				newStatus);
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
