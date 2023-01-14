package view;

import model.User;
import java.util.Scanner;
import model.ReportCSVFileManager;
import model.ReportStatus;
import java.util.Hashtable;

class TakeControlReportMenu extends Menu {
	
	protected User user;
	protected Hashtable<Integer, String[]> map;
	
	public TakeControlReportMenu(User user, Hashtable<Integer, String[]> map){
		this.user = user;
		this.map = map;
	}
	
	public void showMenu(){
		Scanner in = new Scanner(System.in);
		int idNumber = 0;
		
		if(map == null){
			System.out.println();
			System.out.println("There are no reports to take control of.");
			new ReportsMenu().showMenu(user);
		}
		
		System.out.println();
		System.out.println("What report do you want to take control of?");
		System.out.println();
		System.out.print("Please enter report id: ");
		idNumber = in.nextInt();
		
		if(takenOver(map, idNumber)){
			new ReportCSVFileManager().saveMapToCSVFile(user, map, null);
		}
		
		
	}
	
	/*---------------------------------------------------------------------------------------------------------*/
	
	private boolean takenOver(Hashtable<Integer, String[]> map, int id){
		if(!map.containsKey(Integer.valueOf(id))){
			System.out.println("There is no report with the specified id");
			return false;
		}
		else{
			String[] tmp = new String[ReportCSVFileManager.getFieldsNumber()];
			tmp = map.remove(Integer.valueOf(id));
			
			if(tmp[ReportCSVFileManager.getReport_TakenOver_Index()].equals("YES")){
				System.out.println();
				System.out.println("Report number " + id + " has been already taken over");
				return false;
			}
			
			else{
				tmp[ReportCSVFileManager.getReport_ReportStatus_Index()] = ReportStatus.INCOURSEOFDETERMINATION.toString();
				tmp[ReportCSVFileManager.getReport_TakenOver_Index()] = "YES";
				tmp[ReportCSVFileManager.getReport_TakeOverBy_Index()] = this.user.getUserID();
				map.put(Integer.valueOf(id),tmp);
				System.out.println();
				System.out.println("You are currently taking over report " + id );
				
				return true;
			}
		
		}
	}
	
}