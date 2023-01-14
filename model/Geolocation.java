package model;

/**
 * An object of class <code>Geolocation</code> represents the coordinates of
 * a specific point on Earth.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public class Geolocation {
	
	private double latitude, longitude;
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It builds a new object of class <code>Geolocation</code> with the specified
	 * latitude and longitude.
	 * @param lat the latitude of the point.
	 * @param lon the longitude of the point.
	 */
	public Geolocation(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
	}
	
	//------------------------------------------------------------------------------------
	
	protected double toRadians(double x){
		return x * Math.PI / 180;
	}
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It calculates the distance between a point on Earth (the one that executes the
	 * method) and another point (given by parameter).
	 * @param g the point (latitude and longitude).
	 * @return the distance between the two points, expressed in km.
	 */
	public double calculateDistance(Geolocation g){
		//latitude of point 1 and point 2 expressed in radians
		double x = this.toRadians(this.latitude);
		double z = g.toRadians(g.latitude);
		//longitude of point 1 and point 2 expressed in radians
		double y = this.toRadians(this.longitude);
		double w = g.toRadians(g.longitude);
		
		double deltaLongitude = Math.abs(y - w);
		
		double centralAngle = Math.acos((Math.sin(x) * Math.sin(z)) + (Math.cos(y) * Math.cos(w) * Math.cos(deltaLongitude)));
		
		return centralAngle * 6373.044737; //expressed in km
	}
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It returns the latitude of the coordinates.
	 * @return the latitude of the coordinates.
	 */
	public double getLatitude(){
		return this.latitude;
	}
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It returns the longitude of the coordinates.
	 * @return the longitude of the coordinates.
	 */
	public double getlongitude(){
		return this.longitude;
	}
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It returns the string which represents the latitude
	 * and the longitude of the point.
	 * @return the string which contains the latitude and longitude.
	 */
	public String toString(){
		return this.getLatitude() + " " + this.getlongitude();
	}
	
	//------------------------------------------------------------------------------------
	
	/**
	 * It returns the string which is the decimal degrees representation
	 * of the coordinates. For example, "Latitude: 93° N Longitude: 75° E".
	 * @return the string which represents latitude and longitude expressed in decimal degrees.
	 */
	public String toDDString(){
		char nordSud = 'N', estOvest = 'E';
		
		if(this.latitude < 0)
			nordSud = 'S';
		if(this.longitude < 0)
			estOvest = 'O';
		
		//expressed in decimal degrees (DD)
		return "Latitude: " + this.getLatitude() + "° " + nordSud + " Longitude: " + this.getlongitude() + "° " + estOvest;
	}
	
}