package model;

/**
 * An object of enum <code>City</code> represents a city.
 * @author Alessandro Giovannacci
 * @version 1.0
 */

public enum City {
	
	MILANO("Milano"), ROMA("Roma"), TORINO("Torino"), VENEZIA("Venezia");
	
	private String name;
	private int districtsNumber;
	private String[] districts;
	
	//----------------------------------------------------------------------------------------------------------
	
	private City(String name){
		this.name = name;
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns a string of characters that 
	 * @return string that represents the city.
	 */
	public String toString(){
		return this.name;
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the number of districts that the specific City has got.
	 * @return the number of districts of the object that executes the method.
	 */
	public int getDistrictsNumber(){
		return this.districtsNumber;
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	private void setDistrictsNumber(){
		this.districtsNumber = this.districts.length;
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	/**
	 * It sets various districts for a specific city. Each city
	 * has got a fixed number of districts.
	 */
	public void setDistricts(){
		switch(this){
		
			case MILANO:
				districts = new String[]{"Brera", "Porta Venezia", "Lorenteggio", "Gratosoglio"};
				break;
			
			case ROMA:
				districts = new String[]{"Ardeatino", "Don Bosco", "Europa"};
				break;
				
			case TORINO:
				districts = new String[]{"Aurora", "Cavoretto", "Cenisia"};
				break;
				
			case VENEZIA:
				districts = new String[]{"San Marco", "Santa Croce", "Giudecca"};
				break;
			
			default:
				districts = new String[]{""};
				break;
		}
		setDistrictsNumber();
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	/**
	 * It return an array of strings which represents the various district of the city.
	 * @return an array of strings containing the districts of the object that executes the method.
	 */
	public String[] getDistricts(){
		return districts;
	}
	
	
	
}