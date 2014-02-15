package com.iia.searchandfind;

import com.google.android.gms.maps.model.LatLng;

public class CalculDegree {

	public static float returnDegree(LatLng from, LatLng to) {
		// Calcul angle in radians
		float angleRadian = (float) Math.atan2(
				from.latitude - to.latitude, 
				from.longitude - to.longitude);
		// Convert angle in degree
		float angleDegree = (float) (angleRadian * (180 / Math.PI));
		
		// return angle in degree
		return angleDegree;
	}
	
	public static float getDistance(LatLng from, LatLng to ) {
		float distance;
		distance = (float) distanceVolOiseauEntre2PointsAvecPrécision(
				Math.toRadians(from.latitude), 
				Math.toRadians(from.longitude), 
				Math.toRadians(to.latitude), 
				Math.toRadians(to.longitude));
		
		distance = (distance * 6366);
		return distance;
	}
	
	/**
	 * Distance entre 2 points GPS
	 * http://dotclear.placeoweb.com/post/Formule-de-calcul-entre-2-points-wgs84-pour-calculer-la-distance-qui-separe-ces-deux-points
	 * 
	 * La distance mesurée le long d'un arc de grand cercle entre deux points dont on connaît les coordonnées {lat1,lon1} et {lat2,lon2} est donnée par :
	 * La formule, mathématiquement équivalente, mais moins sujette aux erreurs d'arrondis pour les courtes distances est :	 * 
	 * d=2*asin(sqrt((sin((lat1-lat2)/2))^2 + cos(lat1)*cos(lat2)*(sin((lon1- lon2)/2))^2))
	 * Le tout * 6366 pour l'avoir en km
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distanceVolOiseauEntre2PointsAvecPrécision(double lat1, double lon1, double lat2, double lon2) {
		return 2 *	Math.asin(Math.sqrt(
						Math.pow((Math.sin((lat1 - lat2) / 2)),2) +
						Math.cos(lat1) * Math.cos(lat2) * (
						Math.pow(Math.sin(((lon1-lon2)/2)),2)))
						);
 
	}
}
