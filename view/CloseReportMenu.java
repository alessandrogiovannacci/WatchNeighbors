package view;

import model.ReportCSVFileManager;
import model.ReportStatus;
import model.User;
import java.util.Scanner;
import java.io.IOException;
import java.util.Hashtable;

final class CloseReportMenu extends TakeControlReportMenu{
	
	public CloseReportMenu(User user, Hashtable<Integer, String[]> map){
		super(user, map);
	}
	
	/*------------------------------------------------------------------------------------------------------------*/
	
	protected void showDeleteReportMenu(){
		Scanner in = new Scanner(System.in);
		int idNumber = 0;
		
		if(map == null){
			System.out.println();
			System.out.println("There are no reports to close.");
			new ReportsMenu().showMenu(user);
		}
		
		System.out.println();
		System.out.println("What report do you want to close?");
		System.out.println();
		System.out.print("Please enter report id: ");
		idNumber = in.nextInt();
		
		if(!map.containsKey(Integer.valueOf(idNumber))){
			System.out.println("There is no report with the specified id");
		}
		
		else{
			String[] tmp = new String[ReportCSVFileManager.getFieldsNumber()];
			tmp = map.remove(Integer.valueOf(idNumber));
			
			if(tmp[ReportCSVFileManager.getReport_TakenOver_Index()].equals("NO")){
				System.out.println();
				System.out.println("You can't close this report, first you have to take control of it.");
				new ReportsMenu().showMenu(user);
			}
			
			if(tmp[ReportCSVFileManager.getReport_ReportStatus_Index()].equals(ReportStatus.FALSE_ALARM.toString()) ||
			   tmp[ReportCSVFileManager.getReport_ReportStatus_Index()].equals(ReportStatus.POLICE_PRESENCE.toString()) ||
			   tmp[ReportCSVFileManager.getReport_ReportStatus_Index()].equals(ReportStatus.SUSPECTED_PERSON_LEFT.toString())){
				System.out.println();
				System.out.println("Report " + idNumber + " has been already closed.");
				
			}
			else{
				String closingReportStatus = setClosingReportStatus(in);
				
				if(tmp[ReportCSVFileManager.getReport_TakenOver_Index()].equals("YES") && 
						(tmp[ReportCSVFileManager.getReport_TakeOverBy_Index()].equals(user.getUserID()) 
								|| tmp[ReportCSVFileManager.getUserID_Index()].equals(user.getUserID()))){
					tmp[ReportCSVFileManager.getReport_ReportStatus_Index()] = closingReportStatus;
					map.put(Integer.valueOf(idNumber),tmp);
					System.out.println();
					System.out.println("Report " + idNumber + " has been successfully closed.");
					
					ReportCSVFileManager r = new ReportCSVFileManager();
					
					r.saveClosedReportToCSVFile(tmp,user);
					
					r.saveMapToCSVFile(user, map, Integer.valueOf(idNumber));
					
				}
				else{
					System.out.println("You cannot close report " + idNumber);
				}
			}
		}
	
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	
	private String setClosingReportStatus(Scanner in){
		System.out.println();
		System.out.println("1- " + ReportStatus.FALSE_ALARM.toString());
		System.out.println("2- " + ReportStatus.POLICE_PRESENCE.toString());
		System.out.println("3- " + ReportStatus.SUSPECTED_PERSON_LEFT.toString());
		
		System.out.print("Specify report status: ");
		byte choice = in.nextByte();
		
		while(choice < 1 || choice > 3){
			System.out.println("Invalid choice. Repeat.");
			System.out.print("Specify report status: ");
			choice = in.nextByte();
		}
		
		String closingReportStatus = null;
		if(choice == 1){
			closingReportStatus = ReportStatus.FALSE_ALARM.toString();
		}
		else{
			if(choice == 2){
				closingReportStatus = ReportStatus.POLICE_PRESENCE.toString();
			}
			else{
				closingReportStatus = ReportStatus.SUSPECTED_PERSON_LEFT.toString();
			}
		}
		
		return closingReportStatus;
	}
}