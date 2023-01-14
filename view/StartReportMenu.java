package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;
import java.util.Hashtable;
import model.Geolocation;
import model.User;
import model.ReportReason;
import model.Report;
import model.ReportCSVFileManager;
import model.InformationFileManager;
import java.io.IOException;

final class StartReportMenu extends Menu {
	
	private Hashtable<Integer, String[]> map;
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	public StartReportMenu(){}
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	public StartReportMenu(Hashtable<Integer, String[]> map){
		this.map = map;
	}
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	protected void showStartReportMenu(User user){
		
		Scanner in = new Scanner(System.in);
		StartReportMenu s = new StartReportMenu();
		
		System.out.println();
		System.out.println("Start a report and share it with all users of the district");
		System.out.println();
		
		
		Geolocation geoloc = s.setGeoloc(in);
		Date date = new Date();
		ReportReason reportReason = s.setReportReason(in);
		
		Report report = new Report(geoloc, date, reportReason);
		
		ReportCSVFileManager reportCSVFile = new ReportCSVFileManager();
		reportCSVFile.saveReportDataToCSVFile(user, report);
		reportCSVFile.finalize();
		
		InformationFileManager infoFile = new InformationFileManager();
		infoFile.updateInformationFile(Report.getReportCounter(), (Report) report);
		infoFile.finalize();
		
		System.out.println();
		System.out.println("Report successfully created. Now users can watch it and eventually take control of it.");
		System.out.println();
	}
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	private Geolocation setGeoloc(Scanner in){
		double x = 0.0, y = 0.0;
		boolean error;
		System.out.println();
		System.out.println("- Coordinates (expressed in decimal degrees, for example 7,534433)");
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
			System.out.println("You entered a letter. Repeat choice.");
			System.out.println();
			in.nextLine();
		}
		}while(error);
		
		return new Geolocation(x,y);
	}
	
	/*--------------------------------------------------------------------------------------------------------*/
	
	private ReportReason setReportReason(Scanner in){
		System.out.println();
		System.out.println("Report reason");
		System.out.println();
		ReportReason[] reportReasons = ReportReason.values();
		byte choice;
		for(int i = 0; i < reportReasons.length; i++)
			System.out.println((i+1) + "- " + reportReasons[i].toString());
		do{
			System.out.println();
			System.out.print("- Choose the reason of the report: ");
			choice = in.nextByte();
			if(choice < 1 || choice > reportReasons.length)
				System.out.println("Invalid choice. Repeat");
		}while(choice < 1 || choice > reportReasons.length);
		return  reportReasons[choice-1];
	}
	
	
}