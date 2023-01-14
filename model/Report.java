package model;

import java.util.Date;

/**
 * An object of class <code>Report</code> represents a report.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public class Report {
	
	private static int reportCounter = 0;
	
	private int reportID;
	private Geolocation coordinates;
	private Date timeStamp;
	private ReportReason reason;
	private ReportStatus status;
	private String takenOver; 
	private String takenOverBy;
	
	//-------------------------------------------------------------------------------------
	
	
	/**
	 * It builds a new report with the specified parameters. The non-specified parameters are
	 * automatically set up. By default, when a report is created, it has not been taken over yet,
	 * and the name of the user who has taken over it is not specificated. Furthermore, the status
	 * of the report is automatically set, and also the reportID.
	 * @param g the coordinates (latitude and longitude) of the report.
	 * @param d the local time and date of the report.
	 * @param rr the reason of the report.
	 */
	public Report(Geolocation g, Date d, ReportReason rr){
		
		this.coordinates = g;
		this.timeStamp = d;
		this.reason = rr;
		this.status = ReportStatus.TOBECHECKED;
		this.reportID = ++reportCounter;
		this.takenOver = "NO";
		this.takenOverBy = "n/a";
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the userID of the person that has taken over the report. If the report
	 * has not been taken over yet, it returns the string "n/a".
	 * @return the string which represents the userID of the user, or "n/a" if not taken over.
	 */
	public String getTakenOverBy(){
		return takenOverBy;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the reportID of the report.
	 * @return the reportID of the report.
	 */
	public int getReportID(){
		return this.reportID;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the coordinates (latitude and longitude) of the report.
	 * @return the coordinates of the report.
	 */
	public Geolocation getCoordinates(){
		return this.coordinates;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the time and date of the report.
	 * @return local time and date of the report.
	 */
	public Date getTime(){
		return this.timeStamp;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the reason of the report.
	 * @return the reason of the report.
	 */
	public ReportReason getReportReason(){
		return this.reason;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the status of the report.
	 * @return the status of the report.
	 */
	public ReportStatus getReportStatus(){
		return this.status;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the counter of the reports.
	 * @return the value of the counter of the reports.
	 */
	public static int getReportCounter(){
		return reportCounter;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It returns the string "YES" if the report has been already taken over, "NO" otherwise.
	 * @return the string "YES" if taken over, "NO" if not taken over.
	 */
	public String getTakenOver(){
		return this.takenOver;
	}
	
	//-------------------------------------------------------------------------------------
	
	/**
	 * It sets the value of the counter of reports.
	 * @param n the value to give to the counter of the users.
	 * */
	public static void setReportsCounter(int n){
		reportCounter = n;
	}
	
	//-------------------------------------------------------------------------------------
	
	protected String toCSVString(User u){
		return this.reportID + ";" + u.getUserID() + ";" + u.getCoordinates() + ";" + this.coordinates + ";" +
				this.timeStamp + ";" + this.reason + ";" + this.status + ";" + this.takenOver + ";" + this.takenOverBy;
	}
	
}