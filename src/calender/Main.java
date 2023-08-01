package calender;

import calender.component.CalenderUI;
import calender.db.QueryExecution;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hi");
		QueryExecution db = QueryExecution.getInstance();
		db.connectWithDB();
		
		db.createTable();
		
		CalenderUI.createInstance();
//		db.colseDB();
	}

}
