package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * The class <code>UserCSVFileManager</code> provides methods that can be
 * used to manage csv files of users.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public final class UserCSVFileManager extends CSVFileManager {
	
	private static final String USERFILEHEADERFORMAT = "NAME AND SURNAME;LAT/LONG;USERID;EMAIL;PASSWORD";
	
	//variabili usate per indirizzare correttamente il vettore che conterrà i dati dello user presi dal file csv
	private static final int USER_NAMESURNAME_INDEX = 0;
	private static final int USER_GEOLOC_INDEX = 1;
	private static final int USER_ID_INDEX= 2;
	private static final int USER_EMAIL_INDEX = 3;
	private static final int USER_PASSWORD_INDEX = 4;
	
	private static final int FIELDS = 5;
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * The method allows to save user data on a specific file, in function of
	 * the city and the district of the user. To know this information, an object of class 
	 * <code>User</code> is given by parameter.
	 * @param u the user whose data have to be saved.
	 */
	public void saveUserDataToCSVFile(User u){
		File file = null;
		UserCSVFileManager csvFile = new UserCSVFileManager();
		String[] arrayAux = null;
		CSVWriter csvWriter = null;
		String s = "users." + u.getCity()  + "." + u.getDistrict() + ".csv";
		
		if(!csvFile.exists(s)){
			file = csvFile.createFile(s);
			try{
				csvWriter = new CSVWriter(new FileWriter(file),SEPARATOR_C);
			}catch(IOException e){}
			CSVFileManager.setCSVFileFormat(USERFILEHEADERFORMAT);
			arrayAux = csvFileFormat.split(SEPARATOR_S);
			csvWriter.writeNext(arrayAux);
		}

		
		else{
			file = new File(s);
			try {
				csvWriter = new CSVWriter(new FileWriter(file, true), SEPARATOR_C);
			} catch (IOException e) {}
		}
	
		
		arrayAux = u.toCSVString().split(SEPARATOR_S);
		csvWriter.writeNext(arrayAux);
		try {
			csvWriter.close();
		} catch (IOException e) {}
		
	}
	
	//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * The method allows to delete data of a specific user given by parameter.
	 * @param u the user whose data have to be deleted
	 */
	public void deleteUserDataFromCSVFile(User u){
		String s = "users." + u.getCity() + "." + u.getDistrict() + ".csv";
		UserCSVFileManager csvFile = new UserCSVFileManager();
		LinkedList<String[]> list;
		
		if(!csvFile.exists(s))
			System.out.println("Error. File doesn't exist.");
		
		else{
			
			File file = new File(s);
			
			try{

				CSVReader reader = new CSVReader(new FileReader(file), SEPARATOR_C);
				String[] row;
				
				reader.readNext();
				
				list = new LinkedList<String[]>();
				
				while((row = reader.readNext()) != null){
					if(!row[USER_ID_INDEX].equals(u.getUserID()))
						list.add(row);
				}
				
				reader.close();
				
				if(list.size() == 0)
					file.delete();
				else{
					//riscrittura del nuovo file
					UserCSVFileManager userCSVFileManager = new UserCSVFileManager();
					userCSVFileManager.write(list, file);
				}
			}catch(IOException e){
				System.out.println("Application crashed due to bad I/O operations.");
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------
	
	/**
	 * It checks if the userID is valid. In order to get access to the user,
	 * the user id given by parameter is not enough: it is necessary to know
	 * the city and the district in order to open the correct file in which
	 * user data are saved.
	 * @param city the city of the user
	 * @param district the district of the user
	 * @param id the id of the user
	 * @return <code>true</code> if the userID is valid, <code>false</code> otherwise
	 */
	public boolean isUserIDValid(City city, String district, String id){
		String s = "users." + city + "." + district + ".csv";
		CSVReader reader;
		UserCSVFileManager csvFile = new UserCSVFileManager();
		
		try{
			if(!csvFile.exists(s))
				return true;
			
			else{
				File file = new File(s);
				
				reader = new CSVReader(new FileReader(file), SEPARATOR_C);
				String[] row;
				
				reader.readNext();
				
				while((row = reader.readNext()) != null){
					
					if(row[USER_ID_INDEX].equals(id)){
							reader.close();
							return false;
					}
					
				}
			}
			
			reader.close();
		}catch(IOException e){
			System.out.println("Application crashed due to bad I/O operations.");
		}
		return true;
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * It checks if a specific user is saved onto the csv file,
	 * and if so, returns an instance of the user.
	 * @param city the city of the user
	 * @param district the district of the user
	 * @param id the user ID
	 * @param psw the password of the user
	 * @return the instance of class <code>User</code>
	 */
	public User existsUser(City city, String district, String id, String psw){
		String s = "users." + city + "." + district + ".csv";
		
		UserCSVFileManager csvFile = new UserCSVFileManager();
		
		try{
			
			if(!csvFile.exists(s)){
				System.out.println("User " + id + " does not exist in " + city.toString() + "." + district);
				return null;
			}
			else{
				File file = new File(s);
				
				CSVReader reader = new CSVReader(new FileReader(file), SEPARATOR_C);
				String[] row;
				
				reader.readNext();
				
				while((row = reader.readNext()) != null){
					
					if(row[USER_ID_INDEX].equals(id) && row[USER_PASSWORD_INDEX].equals(psw)){
						
						//costruisco un oggetto di tipo Geolocation, a partire dalla stringa prelevata dal file csv
						StringTokenizer strTokenizer = new StringTokenizer(row[USER_GEOLOC_INDEX]," ");
						Double[] aux = new Double[2];
						int i = 0;
						while(strTokenizer.hasMoreTokens()){
							aux[i++] = Double.parseDouble(strTokenizer.nextToken());
						}
						Geolocation g = new Geolocation(aux[0], aux[1]);
						
						//utente trovato
						User u = new User(row[USER_NAMESURNAME_INDEX], city, district, g, row[USER_ID_INDEX], row[USER_EMAIL_INDEX], row[USER_PASSWORD_INDEX], true);
						reader.close();
						return u;
					}
				}
				
				reader.close();
			}
			
			
		}catch(IOException e){
			System.out.println("Application crashed due to bad I/O operations.");
		}
		
		return null;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * It allows to edit some fields of the user.
	 * The new values are saved in a String array given by parameter.
	 * @param u the user whose data have to be modified
	 * @param modifiedFields a <code>String</code> array where the new values
	 * are stored.
	 */
	public void editUserData(User u, String[] modifiedFields){
		File file = new File("users." + u.getCity() + "." + u.getDistrict() + ".csv");
		try{
			CSVReader reader = new CSVReader(new FileReader(file), SEPARATOR_C);
			String[] row;
			LinkedList<String[]> list = new LinkedList<String[]>();
			reader.readNext();
			
			while((row = reader.readNext()) != null){
				if(row[USER_ID_INDEX].equals(u.getUserID())){
					for(int i = 0; i < modifiedFields.length; i++){
						if(modifiedFields[i] != null)
							row[i] = modifiedFields[i];
					}
				}
				list.add(row);
			}
			
			//riscrittura del nuovo file
			UserCSVFileManager userCSVFileManager = new UserCSVFileManager();
			userCSVFileManager.write(list, file);
			reader.close();
		}catch(IOException e){}
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * It writes user data onto the csv file specified by parameter.
	 */
	protected void write(LinkedList<String[]> l, File f){
		String[] arrayAux = null;
		CSVWriter csvWriter = null;
		try{
			csvWriter = new CSVWriter(new FileWriter(f), SEPARATOR_C);
		}catch(IOException e){}
		CSVFileManager.setCSVFileFormat(USERFILEHEADERFORMAT);
		arrayAux = csvFileFormat.split(SEPARATOR_S);
		csvWriter.writeNext(arrayAux);
		String t = null;
		while(!l.isEmpty()){
			arrayAux = l.removeFirst();
			for(int i = 0; i < arrayAux.length; i++){
				t = t + arrayAux[i];
				if(i != (UserCSVFileManager.FIELDS-1))
					t = t + SEPARATOR_S;
			}
			csvWriter.writeNext(arrayAux);
		}
		
		try{
			csvWriter.close();
		}catch(IOException e){}
	}
	
	//----------------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable FIELDS.
	 * @return the value of the static variable FIELDS
	 */
	public static int getFieldsNumber(){
		return FIELDS;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable USER_NAMESURNAME_INDEX.
	 * @return the value of the static variable USER_NAMESURNAME_INDEX
	 */
	public static int getUser_NameSurname_Index(){
		return USER_NAMESURNAME_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable USER_GEOLOC_INDEX.
	 * @return the value of the static variable USER_GEOLOC_INDEX
	 */
	public static int get_User_Geoloc_Index(){
		return USER_GEOLOC_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable USER_ID_INDEX.
	 * @return the value of the static variable USER_ID_INDEX
	 */
	public static int get_User_ID_Index(){
		return USER_ID_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable USER_EMAIL_INDEX.
	 * @return the value of the static variable USER_EMAIL_INDEX
	 */
	public static int get_User_Email_Index(){
		return USER_EMAIL_INDEX;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the value of the static variable USER_PASSWORD_INDEX.
	 * @return the value of the static variable USER_PASSWORD_INDEX
	 */
	public static int get_User_Password_Index(){
		return USER_PASSWORD_INDEX;
	}

}