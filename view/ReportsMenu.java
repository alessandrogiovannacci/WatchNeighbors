package view;

import java.util.Scanner;
import model.City;
import model.ReportCSVFileManager;
import model.User;
import java.io.IOException;
import java.util.Hashtable;
import java.util.InputMismatchException;

final class ReportsMenu extends Menu {
	
	private Hashtable<Integer, String[]> map;
	
	public ReportsMenu(){}
	
	//----------------------------------------------------------------------------------------------------------
	
	public ReportsMenu(Hashtable<Integer, String[]> map){
		this.map = map;
	}
	
	//----------------------------------------------------------------------------------------------------------
	
	protected void showMenu(User user){
		
		Scanner in = new Scanner(System.in);
		byte choice = 0;
		City city = null;
		String district = null;
		
		if(user == null){
			do{
				try{
					System.out.println();
					System.out.println("Do you have an account? If you have it, please login.");
					System.out.println();
					System.out.println("1- Yes, I have an account");
					System.out.println("2- No, I don't have an account");
					System.out.print("Select an operation: ");
					choice = in.nextByte();
					
					
					while(choice < 1 || choice > 2){
						System.out.println("Invalid choice. Repeat.");
						System.out.println();
						System.out.print("Select an operation: ");
						choice = in.nextByte();
					}
				
				}catch(InputMismatchException e){
					System.out.println();
					System.out.println("Invalid choice. Repeat.");
					System.out.println();
					in.nextLine();
				}
			}while(choice != 1 && choice != 2);
			
			if(choice == 1){
				new LoginMenu().showLoginMenu();
			}
			
			else{
				System.out.println();
				System.out.println("Please select your city and district.");
				System.out.println();
				city = ReportsMenu.setCity(in);
				district = ReportsMenu.setDistrict(in, city);
			}
		
		}
		
		printMap(map, user, city, district);
		
		if(user == null){
			System.out.println();
			System.out.println("Press any key and then press enter to return to main menu");
			in.nextLine();
		}
		
		else{
			showReportsSubMenu(user, in);	
		}
		
	}
	
	//------------------------------------------------------------------------------------------
	
	protected static void showOptions(){
		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println();
		System.out.println("1- Start a report");
		System.out.println();
		System.out.println("2- Take control of a report");
		System.out.println();
		System.out.println("3- Close a report");
		System.out.println();
		System.out.println("4- Return to previous menu");
		System.out.println();
		System.out.print("Select an operation: ");
	}
	
	//-----------------------------------------------------------------------------------------
	
	private static City setCity(Scanner in){
		System.out.println();
		System.out.println("City");
		System.out.println();
		City[] cities = City.values();
		byte choice;
		for(int i=0; i<cities.length; i++)
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
	
	//------------------------------------------------------------------------------------------
	
	private static String setDistrict(Scanner in, City c){
		c.setDistricts();
		System.out.println();
		System.out.println("- District");
		System.out.println();
		String[] districts = c.getDistricts();
		byte choice;
		for(int i=0; i<districts.length; i++)
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
	
	//------------------------------------------------------------------------------------------
	
	private void printMap(Hashtable<Integer, String[]> m, User u, City c, String d){
		System.out.println();
		System.out.println("Here all active reports of the district can be watched");
		System.out.println();
		
		map = ReportCSVFileManager.createMap(u, c, d);
		
		if(map == null){
			System.out.println();
			System.out.println("There are no active reports to view in this district at the moment.");
		}
		else{
			ReportCSVFileManager.showMap(map);
		}
	}
	
	//--------------------------------------------------------------------------------------------
	
	private void showReportsSubMenu(User user, Scanner in){
		byte choice = 0;
		do{
			ReportsMenu.showOptions();
			choice = in.nextByte();
			switch(choice){
				case 1: 
					new StartReportMenu(map).showStartReportMenu(user);
					new ReportsMenu(map).showMenu(user);
					break;
				case 2:
					new TakeControlReportMenu(user, map).showMenu();
					new ReportsMenu(map).showMenu(user);
					break;
				case 3:
					new CloseReportMenu(user, map).showDeleteReportMenu();
					new ReportsMenu(null).showMenu(user);
					break;
				case 4:
					new LoggedUserMenu(user).showMenu();
				default:
					System.out.println("Invalid choice.");
					System.out.println();
					break;
			}
		}while(choice != 4);
	}
	
}