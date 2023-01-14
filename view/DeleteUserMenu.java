package view;

import java.util.Scanner;
import model.UserCSVFileManager;
import model.InformationFileManager;
import model.User;

final class DeleteUserMenu extends Menu {
	
	private User user;
	
	public DeleteUserMenu(User u){
		this.user = u;
	}
	
	//-------------------------------------------------------------------------------
	
	protected void showMenu(){
		Scanner in = new Scanner(System.in);
		byte ch = 0;
		
		DeleteUserMenu.showOptions();
		
		ch = in.nextByte();
		
		while(ch != 1 && ch != 2){
			System.out.println();
			System.out.println("Invalid choice. Repeat.");
			System.out.println();
			ch = in.nextByte();
		}
		
		if(ch == 1){
			UserCSVFileManager csvFile = new UserCSVFileManager();
			csvFile.deleteUserDataFromCSVFile(user);
			
			user.finalize();
			
			System.out.println();
			System.out.println("Profile has been successfully deleted.");
			
			InformationFileManager infoFile = new InformationFileManager();
			infoFile.updateInformationFile(User.getUserCounter(), (User) user);
			infoFile.finalize();
			
			new WatchNeighbors();
		}
		
		else{
			new LoggedUserMenu(user).showMenu();
		}
		
	}
	
	//-----------------------------------------------------------------------------
	
	protected static void showOptions(){
		System.out.println();
		System.out.println("You have chosen to delete your profile");
		System.out.println();
		System.out.println("In this case, all your information will be permanently deleted.");
		System.out.println();
		System.out.println("Are you sure you want to delete your profile?");
		System.out.println();
		System.out.println("1- Yes, delete my profile");
		System.out.println("2- Return to previous menu");
		System.out.println();
		System.out.print("Select an operation: ");
	}
	
}