package model;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * The abstract class <code>CSVFileManager</code> contains both abstract and
 * non abstract method to manage comma separated value files.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public abstract class CSVFileManager extends FileManager {
	
	protected static String csvFileFormat;
	
	//separatore di default per file csv
	protected static final String SEPARATOR_S = ";";
	protected static final char SEPARATOR_C = ';';
	
	/**
	 * It creates file with the name specified by parameter. The name
	 * has to be intended of name and extension.
	 * @param s the name of the file that has to be created
	 * @return the instance of <code>File</code>
	 **/
	public File createFile(String s){
		File file = null;
		try{
			file = new File(s);
			file.createNewFile();
		}catch(IOException e){}
		return file;
	}
	
	//---------------------------------------------------------------------------------------------------------
	
	protected static void setCSVFileFormat(String format){
		csvFileFormat = new String(format);
	}
	
	//------------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the default separator (String) of csv file.
	 * @return the separator in String format
	 */
	public static String getSeparator_S(){
		return SEPARATOR_S;
	}
	
	//-------------------------------------------------------------------------------------------------------------
	
	/**
	 * It returns the default separator (char) of csv file.
	 * @return the separator in char format
	 */
	public static char getSeparator_C(){
		return SEPARATOR_C;
	}
	
	//----------------------------------------------------------------------------------------------------------------
	
	/**
	 * The implementation of the abstract method allows to write data onto
	 * the specific file given by parameter. Data are contained in a <code>LinkedList</code>
	 * @param l the list where data are contained
	 * @param f the file where data have to be written
	 */
	protected abstract void write(LinkedList<String[]> l, File f);
	

}