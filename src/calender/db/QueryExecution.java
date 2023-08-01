package calender.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecution {

//#───────전역 상수
	
	/** SQLite db 파일의 경로*/
	public static final String PARTDBFILE = "res/db.sqlite";
	public static final String TABLENAME = "task_table";

//#───────전역 참조 변수
	private static Connection 	_conn = null;
	private static Statement 	_statement = null;

//#───────싱글톤
	private 		QueryExecution() {}
	
	private static 	QueryExecution _current = null;
	
	/** 싱글톤 객체 반환및 생성*/
	public static 	QueryExecution getInstance() {
		if(_current == null) _current = new QueryExecution();
		return _current;
	}
	
//#───────메서드 : DB 연결과 닫기
	
	/** DB Connection */
	public boolean connectWithDB() {
		
		try {
			
			_conn 		= DriverManager.getConnection("jdbc:sqlite:" + PARTDBFILE);
			_statement 	= _conn.createStatement();
			
			System.out.printf("● SQLite와 연결되었습니다. file : %s \n", PARTDBFILE);
			return true;
			
		} catch (SQLException e) {
			System.err.printf("！ DB 연결 실패 %s \n-%s-\n" ,PARTDBFILE, e.getMessage());
			return false;
			
		}
		
	}
	
	/** DB Close*/
	public boolean colseDB() {
		
		if(_conn == null ) return false;
		
		try {
			
			_conn.close();
			_statement.close();
			
			System.out.printf("● DB를 닫았습니다.\n");
			return true;
			
		} catch (SQLException e) {
			System.err.printf("！ DB 닫기 실패 \n└─[%s]\n" ,PARTDBFILE, e.getMessage());
			return false;
			
		}
		
	}
	
//# ──────CREATE
	/** 테이블 생성*/
	public boolean createTable(String table, String columns) {
		
		String 	query = "CREATE TABLE %s \n\t(%s)";
				query = String.format(query, table, columns);
		
		try { _statement.execute(query);
			
			System.out.printf("Ｑ %s \n", query);
			return true;
			
		} catch (SQLException e) {
			System.err.printf("！테이블 생성 실패 \n%s \n-%s-\n" , query, e.getMessage());
			
		}
		return false;
	}

	/** 테이블 생성*/
	public boolean createTable() {
		String col = 
				"id INTEGER PRIMARY KEY,\n"
				+ "title TEXT NOT NULL,\n"
				+ "date TEXT NOT NULL,\n"
				+ "memo TEXT";
		return createTable(TABLENAME, col);
	}
	
//# ──────INSERT
	/** 당일 일정 추가*/
	public int insertDayTask(String title, String date, String memo) {
		String 	query = "INSERT INTO %s "
						+ "(title, date, memo) \n"
						+ "VALUES(\'%s\', \'%s\', \'%s\')";
				query = String.format(query, TABLENAME, title, date, memo);
				
		try { int check = _statement.executeUpdate(query);
		
			System.out.printf("Ｑ %s \n", query);
			return check;
			
		} catch (SQLException e) {
			System.err.printf("！일정 추가 실패 \n%s \n-%s-\n" , query, e.getMessage());
			
		}
		return -1;
	}
	
//# ──────SELECT
	/** 일정 조회 */
	private ResultSet selectExecute(String query) {
		
		try { ResultSet rs = _statement.executeQuery(query);
		
			System.out.printf("Ｑ %s \n", query);
			return rs;
			
		} catch (SQLException e) {
			System.err.printf("！조회 실패 \n%s \n-%s-\n" , query, e.getMessage());
			
		}
		return null;
	}
	
	/** 날짜와 날짜 사이 조회 일정 조회*/
	public ResultSet selectTask(String sdate, String edate) {
		String query = String.format(
				"SELECT * FROM %s\n\t"
				+ "WHERE DATE(date) >= DATE(\'%s\') \n\t"
				+ "AND \n\t"
				+ "DATE(date) <= DATE(\'%s\')\n\t"
				+ "ORDER BY DATE(date)", TABLENAME, sdate, edate);
		return selectExecute(query);
	}
	
	/** 하루 일정 조회*/
	public ResultSet selectTask(String date) {
		String query = String.format(
				"SELECT * FROM %s\n"
				+ "WHERE DATE(date) = DATE(\'%s\')", TABLENAME, date);
		return selectExecute(query);
	}
	
//# ──────DELETE
	/** 일정 삭제*/
	public int delTask(String table, int id) {
		String query = String.format(
				"DELETE FROM %s WHERE id = %d", table, id);
		
		try { int check = _statement.executeUpdate(query);
			System.out.printf("Ｑ %s \n", query);
			return check;
			
		} catch (SQLException e) {
			System.err.printf("！삭제 실패 \n%s \n-%s-\n" , query, e.getMessage());
			
		}
		return -1;
	}
	
	/** 일정 삭제*/
	public int delTask(int id) {
		return delTask(TABLENAME, id);
	}
}
