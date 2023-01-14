package view;

import java.util.Scanner;
import model.UserCSVFileManager;
import model.City;
import model.Geolocation;
import java.util.InputMismatchException;
import model.User;

final class EditUserMenu extends RegistrationMenu {
	
	public EditUserMenu(){
		
	}
	
	//-------------------------------------------------------------------------------------------------------------
	
	protected void showEditMenu(User user){
		Scanner in = new Scanner(System.in);
		byte choice = 0;
		boolean badInput, cityOrDistrictEdited = false, userIDEdited = false, invalidUserID = false;;
		EditUserMenu e = new EditUserMenu();
		String[] fieldsVector = new String[UserCSVFileManager.getFieldsNumber()];
		City newCity = null, oldCity = null;
		String newDistrict = null, oldDistrict = null;
		String newUserID = null;
		
		User oldUser = new User(user);
		
		do{
			try{
				invalidUserID = false;
				badInput = false;
				EditUserMenu.showOptions();
				choice = in.nextByte();
				in.nextLine();
				
				
				switch(choice){	
					case 1:
						System.out.println();
						System.out.println("You have chosen to edit your city/district");
						oldCity = user.getCity();
						newCity = e.setCity(in);
						newDistrict = e.setDistrict(in, newCity);
						if(newCity.toString().equals(user.getCity().toString()) &&
								newDistrict.toString().equals(user.getDistrict().toString())){
							System.out.println("Error. You have chosen the same city and district");
							continue;
						}
						user.setCity(newCity);
						user.setDistrict(newDistrict);
						cityOrDistrictEdited = true;
						
					case 2:
						System.out.println();
						//two different messages
						if(!cityOrDistrictEdited){
							System.out.println("You have chosen to edit your coordinates."); //if city not edited
						}
						else{
							System.out.println("You have to edit coordinates."); //if city edited previously
						}
						Geolocation newGeoloc = e.setGeoloc(in);
						user.setCoordinates(newGeoloc);
						fieldsVector[UserCSVFileManager.get_User_Geoloc_Index()] = newGeoloc.toString();
						break;
						
					case 3:
						System.out.println();
						System.out.println("You have chosen to edit your userID.");
						newUserID = e.setUserID(in);
						user.setUserID(newUserID);
						fieldsVector[UserCSVFileManager.get_User_ID_Index()] = newUserID;
						userIDEdited = true;
						break;
						
					case 4:
						System.out.println();
						System.out.println("You have chosen to edit your email.");
						String newEmail = e.setEmail(in);
						user.setEmail(newEmail);
						fieldsVector[UserCSVFileManager.get_User_Email_Index()] = newEmail;
						break;
						
					case 5:
						System.out.println();
						System.out.println("You have chosen to edit your password.");
						String newPsw = e.setPassword(in);
						user.setPassword(newPsw);
						fieldsVector[UserCSVFileManager.get_User_Password_Index()] = newPsw;
						break;
						
					case 6:
						System.out.println();
						System.out.println("Closing Edit menu...");
						System.out.println();
						break;						
						
					default:
						System.out.println();
						System.out.println("Invalid choice. Repeat.");
						System.out.println();
						
				}
				
			}catch(InputMismatchException ex){
				badInput = true;
				System.out.println();
				System.out.println("You entered a letter. Repeat choice.");
				System.out.println();
				in.nextLine();
			}
			
			if(!badInput && choice != 6){
					System.out.println();
					System.out.println("Do you want to edit another field?");
					System.out.println();
					System.out.println("1- Yes");
					System.out.println();
					System.out.println("2- No");
					System.out.println();
					System.out.print("Select an operation: ");
					choice = in.nextByte();
					
					while(choice != 1 && choice != 2){
						System.out.println("Invalid choice. Repeat.");
						System.out.println();
						System.out.print("Select an operation: ");
						choice = in.nextByte();
					}
					
					if(choice == 1){
						continue;
					}
					else{ 
						UserCSVFileManager csvFile = new UserCSVFileManager();
						
						if(cityOrDistrictEdited){
							if(userIDEdited){
								if(!csvFile.isUserIDValid(newCity, newDistrict, newUserID)){
									System.out.println();
									System.out.println("User " + newUserID + " already exists in " + newCity + "." + newDistrict);
									invalidUserID = true;
								}
							}
							
							else{
								if(!csvFile.isUserIDValid(newCity, newDistrict, user.getUserID())){
									System.out.println();
									System.out.println("User " + user.getUserID() + " already exists in " + newCity + "." + newDistrict);
									invalidUserID = true;
								}
							}
							
							if(!invalidUserID){
								csvFile.saveUserDataToCSVFile(user);
								csvFile.deleteUserDataFromCSVFile(oldUser);
							}
							else{
								user.setCity(oldCity);
								user.setDistrict(oldDistrict);
							}
						}
						
						else{
							if(userIDEdited){
								if(!csvFile.isUserIDValid(user.getCity(), user.getDistrict(), user.getUserID())){
									System.out.println();
									System.out.println("User " + newUserID + " already exists in " + user.getCity() + "." + user.getDistrict());
									invalidUserID = true;
								}
								csvFile.editUserData(oldUser, fieldsVector);
							}
							else{
								csvFile.editUserData(user, fieldsVector);
							}
						}
						
						if(!invalidUserID){
							System.out.println();
							System.out.println("Profile has been succesfully edited.");
							System.out.println();
							new LoggedUserMenu(user).showMenu();
						}
						else{
							System.out.println("Edit process has been interrupted.");
						}
						
					} 
					
			}
			
			
		}while(choice != 6);
		
		new LoggedUserMenu(user).showMenu();
		
	}
	
	//-----------------------------------------------------------------------------------------------------------------
	
	protected static void showOptions(){
		System.out.println();
		System.out.println("Edit your profile.");
		System.out.println();
		System.out.println("1- City/district");
		System.out.println();
		System.out.println("2- Coordinates");
		System.out.println();
		System.out.println("3- UserID");
		System.out.println();
		System.out.println("4- Email");
		System.out.println();
		System.out.println("5- Password");
		System.out.println();
		System.out.println("6- Exit from Edit menu");
		System.out.println();
		System.out.print("Select an operation: ");
	}
	
	
	
}