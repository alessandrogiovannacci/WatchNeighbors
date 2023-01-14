package view;

import java.util.Scanner;
import model.City;
import model.User;
import model.UserCSVFileManager;


final class LoginMenu extends RegistrationMenu {
	
	protected void showLoginMenu(){
		Scanner in = new Scanner(System.in);
		LoginMenu l = new LoginMenu();
		
		System.out.println();
		System.out.println("Insert your userID and enter Watchneighbors!");
		System.out.println();
		System.out.print("UserID: ");
		String userID = in.nextLine();
		System.out.println();
		City city = l.setCity(in);
		String district = l.setDistrict(in, city);
		System.out.println();
		System.out.print("Password: ");
		String psw = in.nextLine();
		System.out.println();
		
		UserCSVFileManager csvFile = new UserCSVFileManager();
		
		User user = csvFile.existsUser(city, district, userID, psw);
		
		if(user == null)
			System.out.println("Authentication failed.");
		else{
			System.out.println("Login succesfully completed.");
			new LoggedUserMenu(user).showMenu();
		}
		
	}
	
}