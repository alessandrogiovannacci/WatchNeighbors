package view;

import java.util.Date;
import java.util.Scanner;
import model.InformationFileManager;
import java.util.InputMismatchException;
import java.io.IOException;

final class WatchNeighbors {
	
	public WatchNeighbors(){
		showMainMenu();
	}
	
	private void showMainMenu(){
		Scanner in = new Scanner(System.in);
		byte choice = 0;
		
		do{
			
			try{
				
				WatchNeighbors.showOptions();
				choice = in.nextByte();
								
				switch(choice){
					case 1:
						new ReportsMenu().showMenu(null);
						break;
					
					case 2:
						new LoginMenu().showLoginMenu();
						break;
					
					case 3:
						new RegistrationMenu().showMenu();
						break;
						
					case 4:
						System.out.println();
						System.out.println("Closing application...");
						System.exit(0);
						break;
					
					default:
						System.out.println("Invalid choice. Repeat.");
						
				}
			
			}catch(InputMismatchException e){
				System.out.println();
				System.out.println("Invalid choice. Repeat.");
				System.out.println();
				in.nextLine();
			}
			
		}while(choice != 4);
		
	}
	
	//--------------------------------------------------------------------------------
	
	protected static void showOptions(){
		
		InformationFileManager infoFile = new InformationFileManager();
		if(!infoFile.exists(InformationFileManager.getInformationFileName()))
				infoFile.createFile(InformationFileManager.getInformationFileName());
		
		System.out.println();
		System.out.println(new Date().toString());
		
		infoFile.printInformationFile();
		infoFile.updateUserAndReportCounters();
		
		System.out.println();
		System.out.println();
		System.out.println("WATCHNEIGHBORS");
		System.out.println();
		System.out.println();
		System.out.println("1- Enter WatchNeighbors");
		System.out.println("2- Login");
		System.out.println("3- Registration");
		System.out.println("4- Exit from WatchNeighbors");
		System.out.println();
		System.out.print("Select an operation: ");
	}
	
	//--------------------------------------------------------------------------------
	
	public static void main(String[] args) throws IOException{
		new WatchNeighbors();
	}
	
	
}