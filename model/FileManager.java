package model;

import java.io.File;

/**
 * The abstract class <code>FileManager</code> is the base class for
 * all kinds of file manager needed. The specific subclasses
 * of <code>FileManager</code> are built in function of the specific
 * kind of file to manage.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public abstract class FileManager {
	
	/**
	 * Destroy the object that executes the method (by the garbage collector).
	 */
	public void finalize(){}
	
	/**
	 * An abstract method which, implemented by subclasses of <code>FileManager</code>,
	 * creates a specific file.
	 * @param s the specified file to create
	 * @return the instance of class <code>File</code> just created.
	 */
	public abstract File createFile(String s);
	
	/**
	 * It checks if a specific file exists in secondary memory.
	 * @param s the name of the file.
	 * @return <code>true</code> if the file exists, <code>false</code> otherwise.
	 */
	public boolean exists(String s){ 
		File file = new File(s);
		return file.exists();
	}
	
	
}