package model;

/**
 * An object of class <code>User</code> represents a user.
 * @author Alessandro Giovannacci
 * @version 1.0
 */

public class User {
	
	private static long userCounter;
	
	private String nameSurname, district, userID, email, password;
	private City city;
	private Geolocation coordinates;
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It builds a new user with the specified parameters.
	 * @param nameSurname name and surname of the user.
	 * @param city city of the user.
	 * @param district district of the user.
	 * @param g coordinates of the user.
	 * @param id the userID chosen by the user.
	 * @param email email of the user.
	 * @param password password of the user.
	 * @param registrated <code>true</code> if the user is already registered, <code>false</code> otherwise.
	 */
	public User(String nameSurname, City city, String district, Geolocation g, String id, String email, String password, boolean registrated){
		this.nameSurname = nameSurname;
		this.city = city;
		this.district = district;
		this.coordinates = g;
		this.userID = id;
		this.email = email;
		this.password = password;
		
		if(!registrated)
			userCounter++;
	}
	
	//----------------------------------------------------------------------------------
	
	//copy builder
	/**
	 * It builds an object of class user from an existing object of class
	 * user given by parameter.
	 * @param original the existing user from which build the new user
	 */
	public User(User original){
		this.nameSurname = new String(original.nameSurname);
		this.city = original.getCity();
		this.district = new String(original.getDistrict());
		this.coordinates = new Geolocation(original.coordinates.getLatitude(),original.coordinates.getlongitude());
		this.userID = new String(original.getUserID());
		this.email = new String(original.getEmail());
		this.password = new String(original.getPassword());
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * Destroy the object that executes the method (by the garbage collector) and decrements
	 * the value of the users' counter.
	 */
	public void finalize(){
		userCounter--;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns a string representation of name and surname of the user.
	 * @return the string which represents name and surname.
	 */
	public String getNameSurname(){
		return this.nameSurname;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the city of the user.
	 * @return the city of the user.
	 */
	public City getCity(){
		return this.city;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the district of the user.
	 * @return the string which represents the district.
	 */
	public String getDistrict(){
		return this.district;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the coordinates of the user.
	 * @return coordinates (latitude and longitude) of the user.
	 */
	public Geolocation getCoordinates(){
		return this.coordinates;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the userID chosen by the user.
	 * @return the string  which represents userID.
	 */
	public String getUserID(){
		return this.userID;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the email of the user.
	 * @return the email of the user.
	 */
	public String getEmail(){
		return this.email;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns password of the user.
	 * @return password of the user.
	 */
	public String getPassword(){
		return this.password;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new city of the user.
	 * @param c the new city of the user.
	 */
	public void setCity(City c){
		this.city = c;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new district of the user.
	 * @param d the new district of the user.
	 */
	public void setDistrict(String d){
		this.district = d;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new coordinates of the user.
	 * @param g the new coordinates (latitude and longitude) of the user.
	 */
	public void setCoordinates(Geolocation g){
		this.coordinates = g;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new userID of the user.
	 * @param id the new userID of the user.
	 */
	public void setUserID(String id){
		this.userID = id;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new email of the user.
	 * @param e the new email of the user.
	 */
	public void setEmail(String e){
		this.email = e;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the new password of the user.
	 * @param p the new password of the user.
	 */
	public void setPassword(String p){
		this.password = p;
	}
	
	/**
	 * It returns a string representation of the user, in the format: Name and surname: "name and surname",
	 * City: "city", District: "district", Lat/lon: "lat/lon", UserID: "userID", Email: "email".
	 * @return the string representation of the user data.
	 */
	public String toString(){
		return "Name and surname: " + this.getNameSurname() +  ", City: " + this.getCity() +
				", District: " + this.getDistrict() + ", Lat/Lon: " + this.getCoordinates().toString() +
				", UserID: " + this.getUserID() + ", Email: " + this.getEmail();
	}
	
	//----------------------------------------------------------------------------------
	
	protected String toCSVString(){
		return this.getNameSurname() + ";"  + this.getCoordinates() +
				";" + this.getUserID() + ";" + this.getEmail() + ";" + this.getPassword();
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It returns the counter of the users.
	 * @return the counter of the users.
	 */
	public static long getUserCounter(){
		return userCounter;
	}
	
	//----------------------------------------------------------------------------------
	
	/**
	 * It sets the value of the counter of the users
	 * @param n the new value of the counter.
	 */
	public static void setUsersCounter(long n){
		userCounter = n;
	}
	
}