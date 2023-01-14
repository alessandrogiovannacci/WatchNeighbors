package view;

import java.util.Scanner;
import java.io.IOException;
import model.User;


final class LoggedUserMenu extends Menu {
	
	private User user;
	
	public LoggedUserMenu(User u){
		this.user = u;
	}
	
	//-----------------------------------------------------------------------------
	
	public void finalize(){
		
	}
	
	//-----------------------------------------------------------------------------
	
	protected void showMenu(){
		byte choice = 0;
		
		Scanner in = new Scanner(System.in);
		
		do{
			
				LoggedUserMenu.showOptions();
				
				choice = in.nextByte();
				
				switch(choice){
					case 1:
						new ReportsMenu().showMenu(user);
						break;
						
					case 2:
						EditUserMenu editMenu = new EditUserMenu();
						editMenu.showEditMenu(user);
						break;
						
					case 3:
						DeleteUserMenu deleteMenu = new DeleteUserMenu(user);
						deleteMenu.showMenu();
						break;
						
					case 4:
						System.out.println();
						System.out.println("Closing application...");
						System.exit(0);
						
					default:
						System.out.println();
						System.out.println("Invalid choice. Repeat.");
						System.out.println();
						
				}
			
			
		}while(choice != 4);
		
		
	}
	
	//-----------------------------------------------------------------------------
	
	protected static void showOptions(){
		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println();
		System.out.println("1- Show map of the district");
		System.out.println();
		System.out.println("2- Edit profile");
		System.out.println();
		System.out.println("3- Delete profile");
		System.out.println();
		System.out.println("4- Exit from WatchNeighbors");
		System.out.println();
		System.out.print("Select an operation: ");
	}
	

	
}