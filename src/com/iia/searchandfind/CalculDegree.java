package com.iia.searchandfind;

import com.google.android.gms.maps.model.LatLng;

public class CalculDegree {

	public static float ReturnDegree(LatLng from, LatLng to) {
		// Calcul angle in radians
		float angleRadian = (float) Math.atan2(
				from.latitude - to.latitude, 
				from.longitude - to.longitude);
		// Convert angle in degree
		float angleDegree = (float) (angleRadian * (180 / Math.PI));
		
		// return angle in degree
		return angleDegree;
	}
	
	public static float GetDistance(LatLng from, LatLng to ) {
		return (float) Math.sqrt(
				from.latitude*to.latitude + 
				from.longitude*to.longitude);
	}
}
