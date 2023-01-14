package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.io.FileReader;
import java.util.Hashtable;

/**
 * The class <code>ReportCSVFileManager</code> provides methods that can be
 * used to manage csv files of reports.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public final class ReportCSVFileManager extends CSVFileManager {
	
	private static final String REPORTFILEHEADERFORMAT = "REPORT I;USER ID;LAT/LONG USER;LAT/LONG REPORT;TIME;REPORT REASON;REPORT STATUS;TAKEN OVER;TAKEN OVER BY";
	
	//variabili usate per indirizzare correttamente il vettore che conterrà i dati della segnalazione presi dal file csv
	private static final int REPORT_ID_INDEX = 0;
	private static final int REPORT_USERID_INDEX = 1;
	private static final int REPORT_GEOLOCUSER_INDEX= 2;
	private static final int REPORT_GEOLOCREPORT_INDEX = 3;
	private static final int REPORT_TIME_INDEX = 4;
	private static final int REPORT_REPORTREASON_INDEX = 5;
	private static final int REPORT_REPORTSTATUS_INDEX = 6;
	private static final int REPORT_TAKENOVER_INDEX = 7;
	private static final int REPORT_TAKENOVERBY_INDEX = 8;
	
	private static final int FIELDS = 9;
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * The method allows to save report data on a specific file, in function of
	 * the city and the district of the user
	 * @param u the user associated with the report
	 * @param r the report whose data have to be stored
	 */
	public void saveReportDataToCSVFile(User u, Report r){
		File file = null;
		ReportCSVFileManager csvFile = new ReportCSVFileManager();
		String[] arrayAux = null;
		CSVWriter csvWriter = null;
		String s = "reports." + u.getCity()  + "." + u.getDistrict() + ".csv";
		
		if(!csvFile.exists(s)){
			file = csvFile.createFile(s);
			System.out.println();
			System.out.println(s + " was succesfully created.");
			try{
				csvWriter = new CSVWriter(new FileWriter(file), SEPARATOR_C);
			}catch(IOException e){}
			CSVFileManager.setCSVFileFormat(REPORTFILEHEADERFORMAT);
			arrayAux = csvFileFormat.split(SEPARATOR_S);
			csvWriter.writeNext(arrayAux);
		}
		
		else{
			file = new File(s);
			try{
				csvWriter = new CSVWriter(new FileWriter(file, true), SEPARATOR_C);
			}catch(IOException e){}
		}
			
		String temp = r.toCSVString(u);
		
		arrayAux = temp.split(SEPARATOR_S);
		csvWriter.writeNext(arrayAux);
		try{
			csvWriter.close();
		}catch(IOException e){}
		
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	/**
	 * It saves a report as closed, giving an array by parameter which
	 * contains the values that specify that a report has been closed.
	 * @param closedReport array which contains values that specify that a report has been closed
	 * @param u the user whose city and district are essential to get access to the file
	 */
	public void saveClosedReportToCSVFile(String[] closedReport, User u){
		File file = null;
		ReportCSVFileManager csvFile = new ReportCSVFileManager();
		String[] arrayAux = null;
		CSVWriter csvWriter = null;
		String s = "closed.reports." + u.getCity() + "." + u.getDistrict() + ".csv";
		
		if(!csvFile.exists(s)){
			file = csvFile.createFile(s);
			System.out.println();
			try{
				csvWriter = new CSVWriter(new FileWriter(file), SEPARATOR_C);
			}catch(IOException e){}
			CSVFileManager.setCSVFileFormat(REPORTFILEHEADERFORMAT);
			arrayAux = csvFileFormat.split(SEPARATOR_S);
			csvWriter.writeNext(arrayAux);
		}
		
		else{
			file = new File(s);
			try {
				csvWriter = new CSVWriter(new FileWriter(file, true), SEPARATOR_C);
			} catch (IOException e) {}
		}
		
		csvWriter.writeNext(closedReport);
		try {
			csvWriter.close();
		} catch (IOException e) {}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * It writes report data onto the csv file specified by parameter.
	 */
	protected void write(LinkedList<String[]> l, File f){
		String[] arrayAux = null;
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(new FileWriter(f), SEPARATOR_C);
		} catch (IOException e) {}
		CSVFileManager.setCSVFileFormat(REPORTFILEHEADERFORMAT);
		arrayAux = csvFileFormat.split(SEPARATOR_S);
		csvWriter.writeNext(arrayAux);
		String t = null;
		while(!l.isEmpty()){
			arrayAux = l.removeFirst();
			for(int i = 0; i < arrayAux.length; i++){
				t = t + arrayAux[i];
				if(i != (ReportCSVFileManager.FIELDS-1))
					t = t + SEPARATOR_S;
			}
			csvWriter.writeNext(arrayAux);
		}
		try {
			csvWriter.close();
		} catch (IOException e) {}
	}
	
	//---------------------------------------------------------------------------------------------------------------
	
	/**
	 * It saves data stored in a <code>Hashtable</code> onto the csv file.
	 * @param user  the user whose city and district are essential to get access to the file
	 * @param m the hashtable where data are stored
	 * @param closed if it is non null, it allows to remove report from hashtable
	 */
	public void saveMapToCSVFile(User user, Hashtable<Integer, String[]> m, Integer closed){
		String s = "reports." + user.getCity().toString() + "." + user.getDistrict().toString() + ".csv";
		File file = new File(s);
		LinkedList<String[]> l = new LinkedList<String[]>();
		
		if(closed != null)
			m.remove(closed);
		for(int i=Report.getReportCounter(); i>=1; i--){
			if(m.containsKey(i)){
				String[] t = m.get(Integer.valueOf(i));
				l.add(t);}
		}
		write(l, file);
	}

	//---------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param user if parameter is non null, it means that  the user is registered.
	 * @param city the city of the user
	 * @param district the district of the user
	 * @return the hashtable that contains reports
	 */
	public static Hashtable<Integer, String[]> createMap(User user, City city, String district){
		String s = "";
		if(user == null){
			s = "reports." + city.toString() + "." + district + ".csv";
		}
		else{
			s = "reports." + user.getCity() + "." + user.getDistrict() + ".csv"; 
		}
		
		UserCSVFileManager csvFile = new UserCSVFileManager();
		Hashtable<Integer,String[]> map = null;
		
		if(!csvFile.exists(s)){
			return null;
		}
		
		else{
			File file = new File(s);
			
			try{
				CSVReader reader = new CSVReader(new FileReader(file), UserCSVFileManager.getSeparator_C());
				String[] row;
				
				reader.readNext();
				
				map = new Hashtable<Integer, String[]>();
				
				while((row = reader.readNext()) != null){		
						map.put(Integer.valueOf(row[REPORT_ID_INDEX]), row);
				}
				
				reader.close();
				
				if(map.isEmpty()){
					return null;
				}
			
			}catch(IOException e){}
			
			return map;
			
		}
		
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * It shows the map which contains all reports for the specific district.
	 * @param map the data structure which contains reports data
	 */
	public static void showMap(Hashtable<Integer, String[]> map){
		Enumeration<String[]> e = map.elements();
		while(e.hasMoreElements()){
			System.out.println(Arrays.toString((String[])e.nextElement()));
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable FIELDS
	 * @return the value of the static variable FIELDS
	 */
	public static int getFieldsNumber(){
		return FIELDS;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_ID_INDEX
	 * @return the value of the static variable REPORT_ID_INDEX
	 */
	public static int getReport_ID_Index(){
		return REPORT_ID_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_USERID_INDEX
	 * @return the value of the static variable REPORT_USERID_INDEX
	 */
	public static int getUserID_Index(){
		return REPORT_USERID_INDEX;
	}
	
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_GEOLOCUSER_INDEX
	 * @return the value of the static variable REPORT_GEOLOCUSER_INDEX
	 */
	public static int getReport_GeolocUser_Index(){
		return REPORT_GEOLOCUSER_INDEX;
	}
	
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_GEOLOCREPORT_INDEX
	 * @return the value of the static variable REPORT_GEOLOCREPORT_INDEX
	 */
	public static int getReport_GeolocReport_Index(){
		return REPORT_GEOLOCREPORT_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_TIME_INDEX
	 * @return the value of the static variable REPORT_TIME_INDEX
	 */
	public static int getReport_Time_Index(){
		return REPORT_TIME_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_REPORTREASON_INDEX
	 * @return the value of the static variable REPORT_REPORTREASON_INDEX
	 */
	public static int getReport_ReportReason_Index(){
		return REPORT_REPORTREASON_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_REPORTSTATUS_INDEX
	 * @return the value of the static variable REPORT_REPORTSTATUS_INDEX
	 */
	public static int getReport_ReportStatus_Index(){
		return REPORT_REPORTSTATUS_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_TAKENOVER_INDEX
	 * @return the value of the static variable REPORT_TAKENOVER_INDEX
	 */
	public static int getReport_TakenOver_Index(){
		return REPORT_TAKENOVER_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable REPORT_TAKENOVERBY_INDEX
	 * @return the value of the static variable REPORT_TAKENOVERBY_INDEX
	 */
	public static int getReport_TakeOverBy_Index(){
		return REPORT_TAKENOVERBY_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns a string which represents the current format of the report header
	 * @return the current format of the report header
	 */
	public static String getReportFileHeaderFormat(){
		return REPORTFILEHEADERFORMAT;
	}

}