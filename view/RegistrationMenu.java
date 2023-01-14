package view;

import model.User;
import java.util.Scanner;
import model.City;
import model.Geolocation;
import model.UserCSVFileManager;
import model.InformationFileManager;
import java.io.Console;
import java.io.IOException;
import java.util.InputMismatchException;
import model.EmailValidator;


class RegistrationMenu extends Menu {
	
	protected void showMenu(){
		Scanner in = new Scanner(System.in);
		RegistrationMenu r = new RegistrationMenu();
		boolean emailValid;
		
		System.out.println();
		System.out.println("Join WatchNeighbors!");
		System.out.println();
		
		String nameSurname = r.setNameSurname(in);
		
		City city = r.setCity(in);
		
		String district = r.setDistrict(in, city);
		
		Geolocation geoloc = r.setGeoloc(in);
		
		String userID = r.setUserID(in);
		
		String email;
		do{
			email = r.setEmail(in);
			EmailValidator validator = new EmailValidator();
			emailValid = validator.isEmailValid(email);
			if(!emailValid){
				System.out.println();
				System.out.println("Email not valid. Enter a valid email address.");
				System.out.println();
			}
		}while(!emailValid);
		
		String psw = r.setPassword(in);
		
		User user = new User(nameSurname, city, district, geoloc, userID, email, psw, false);
		
		UserCSVFileManager csvFile = new UserCSVFileManager();
		
		if(!csvFile.isUserIDValid(city, district, userID)){
			System.out.println();
			System.out.println("User " + userID + " already exists in " + city + "." + district);
			System.out.println();
			System.out.println("Registration has failed");
			System.out.println();
		}
			
		else{
			csvFile.saveUserDataToCSVFile(user);
			csvFile.finalize();
			InformationFileManager infoFile = new InformationFileManager();
			infoFile.updateInformationFile(User.getUserCounter(), (User) user);
			infoFile.finalize();
			
			System.out.println();
			System.out.println("Registration succesfully completed!");
			
			new LoggedUserMenu(user).showMenu();
		}
		
		
	}
	
	//----------------------------------------------------------------------------------
	
	protected String setNameSurname(Scanner in){
		System.out.println();
		System.out.print("- Name and surname: ");
		String nameSurname = in.nextLine();
		return nameSurname;
	}
	
	//----------------------------------------------------------------------------------
	
	protected City setCity(Scanner in){
		System.out.println();
		System.out.println("City");
		System.out.println();
		City[] cities = City.values();
		byte choice;
		for(int i = 0; i < cities.length; i++)
			System.out.println((i+1) + "- " + cities[i].toString());
		do{
			System.out.println();
			System.out.print("- Select your city: ");
			choice = in.nextByte();
			if(choice < 1 || choice > cities.length)
				System.out.println("Invalid choice. Repeat");
		}while(choice < 1 || choice > cities.length);
		return  cities[choice-1];
	}
	
	//----------------------------------------------------------------------------------
	
	protected String setDistrict(Scanner in, City c){
		c.setDistricts();
		System.out.println();
		System.out.println("- District");
		System.out.println();
		String[] districts = c.getDistricts();
		byte choice;
		for(int i = 0; i < districts.length; i++)
			System.out.println((i+1) + "- " + districts[i].toString());
		do{
			System.out.println();
			System.out.print("Select your district: ");
			choice = in.nextByte();
			if(choice < 1 || choice > districts.length)
				System.out.println("Invalid choice. Repeat");
		}while(choice < 1 || choice > districts.length);
		in.nextLine();
		return districts[choice-1];
	}
	
	//----------------------------------------------------------------------------------
	
	protected Geolocation setGeoloc(Scanner in){
		double x = 0.0, y = 0.0;
		boolean error;
		System.out.println();
		System.out.println("- Coordinates (expressed in decimal degrees, for example \"7,534433\")");
		System.out.println();
		do{
		try{
			error = false;
			System.out.print("Latitude: ");
			x = in.nextDouble();
			System.out.println();
			System.out.print("Longitude: ");
			y = in.nextDouble();
			in.nextLine();
		}catch(InputMismatchException e){
			error = true;
			System.out.println("Invalid format. Repeat choice.");
			System.out.println();
			in.nextLine();
		}
		}while(error);
		
		return new Geolocation(x,y);
	}
	
	//----------------------------------------------------------------------------------
	
	protected String setUserID(Scanner in){
		System.out.println();
		System.out.print("- UserID: ");
		String uID = in.nextLine();
		return uID;
	}
	
	//----------------------------------------------------------------------------------
	
	protected String setEmail(Scanner in){
		System.out.println();
		System.out.print("- Email address: ");
		String email = in.nextLine();
		return email;
	}
	
	//----------------------------------------------------------------------------------
	
	
	protected String setPassword(Scanner in){
		/*
		System.out.println();
		System.out.print("- Password: ");
		String psw = in.nextLine();
		return psw;
		*/
		
		Console console = System.console();
		char[] psw = null;
		System.out.println();
		System.out.print("- Password (when digiting it, you can't see each character digited): ");
		psw = console.readPassword();
		String str = String.valueOf(psw);
		return str;
		
	}
	
	
}