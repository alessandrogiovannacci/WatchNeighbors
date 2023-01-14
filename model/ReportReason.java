package model;

/**
 * An object of enum <code>ReportReason</code> represents
 * the reason of a report.
 * @author Alessandro Giovannnacci
 * @version 1.0
 *
 */
public enum ReportReason {
	
	ALARM("Alarm"), SNATCHING("Snatching in progress"), SUSPECTED_PERSON("Suspected person"), THEFT("Theft in progress");
	
	private String name;
	
	//----------------------------------------------------------------------
	
	private ReportReason(String name){
		this.name= name;
	}
	
	//----------------------------------------------------------------------
	
	/**
	 * It returns the string representation of the reason of report.
	 * @return the string that represents the reason.
	 */
	public String toString(){
		return this.name;
	}
	
}