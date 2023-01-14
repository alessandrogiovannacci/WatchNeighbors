package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * An object of class <code>InformationFileManager</code>
 * manages the creation of a specific file which contains
 * information about the number of users and reports, their
 * modification and visualization.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public final class InformationFileManager extends FileManager {
	
	private static String informationFileName = "information.txt";
	
	/**
	 * It creates a specific file named as the given string parameter.
	 * The string must include the name plus a dot and the extension.
	 * @param s the name of the file to create
	 * @return the instance of class <code>File</code> just created.
	 */
	public File createFile(String s){
		BufferedWriter bfrWriter = null;
		File file = null;
		try{
			file = new File(s);
			file.createNewFile();
			bfrWriter = new BufferedWriter(new FileWriter(file));
			bfrWriter.write("Registered users:0");
			bfrWriter.newLine();
			bfrWriter.write("Total reports:0");
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				bfrWriter.flush();
				bfrWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return file;
	}
	
	//--------------------------------------------------------------------------------------------
	
	/**
	 * It returns the name of the default information file name.
	 * @return the string representation of the default information file name.
	 */
	public static String getInformationFileName(){
		return informationFileName;
	}
	
	//-----------------------------------------------------------------------
	
	/**
	 * It sets the name of the information file.
	 * @param the name of the information file to set.
	 */
	private static void setInformationFileName(String name){
		informationFileName = name;
	}
	
	//-----------------------------------------------------------------------
	
	/**
	 * It prints the number of users and reports on the standard output.
	 */
	public void printInformationFile(){
		File file = new File(informationFileName);
		try{
			BufferedReader bfrReader = new BufferedReader(new FileReader(file));
			String t;
			while((t = bfrReader.readLine()) != null)				
				System.out.println(t);
			bfrReader.close();
		}catch(IOException e){}
	}
	
	//-----------------------------------------------------------------------
	
	/**
	 * It updates the number of users or reports, in function of the object given by parameter
	 * @param n the new number of users or reports
	 * @param o the object can be an instance of class User or class Report. This parameter is important
	 * because it establishes which information has to be updated
	 */
	public void updateInformationFile(long n, Object o){
		File file = new File(informationFileName);
		List<String> fileContent = null;
		try {
			fileContent = new ArrayList<>(Files.readAllLines(file.toPath()));
		} catch (IOException e) {}
		if(o instanceof User) 		//se l'oggetto è istanza di User, aggiorno num. utenti, altrimenti aggiorno segnalazioni
				fileContent.set(0, "Registered users:" + n);
		else
				fileContent.set(1, "Total reports:" + n);
			
		try {
			Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {}		
	}
	
	//-----------------------------------------------------------------------
	
	/**
	 * It updates the number of users or reports, and it saves
	 * the modification on the information file.
	 */
	public void updateUserAndReportCounters(){
		File file = new File(informationFileName);
		try{
			BufferedReader bfrReader = new BufferedReader(new FileReader(file));
			String t;
			StringTokenizer strTok;
			Integer i = null;
			while((t = bfrReader.readLine()) != null){
				strTok = new StringTokenizer(t, ":");
				while(strTok.hasMoreTokens()){
					strTok.nextToken();
					if(i == null){
						i = new Integer(strTok.nextToken());
						User.setUsersCounter(i);
					}
					
					else{
						i = new Integer(strTok.nextToken());
						Report.setReportsCounter(i);
					}
				}
			}
			bfrReader.close();
		}catch(IOException e){}
	}	
	
}