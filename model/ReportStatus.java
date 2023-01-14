package model;

/**
 * An object of enum <code>ReportStatus</code> represents the status of
 * a report.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public enum ReportStatus {
	
	TOBECHECKED("To be checked"), INCOURSEOFDETERMINATION("In course of determination"), FALSE_ALARM("False alarm"),
	POLICE_PRESENCE("Police presence"), SUSPECTED_PERSON_LEFT("Suspected person has left");
	
	private String name;
	
	private ReportStatus(String name){
		this.name = name;
	}
	
	/**
	 * It returns the string representation of the status of report.
	 * @return the string that represents the status.
	 */
	public String toString(){
		return this.name;
	}
}