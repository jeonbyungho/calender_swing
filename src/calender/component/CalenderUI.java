package calender.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.util.Calendar;

import calender.db.QueryExecution;



@SuppressWarnings("serial")
public class CalenderUI extends CalenderFrame{

//#───────싱글톤
	private static CalenderUI _current;
	
	public static CalenderUI createInstance() {
		if (_current == null) _current = new CalenderUI();
		
		return _current;
	}
	
	public static CalenderUI refer() {
		return _current;
	}
//#───────멤버 변수
	
	// 날짜
	private int day;
	private int month;
	private int year;
	
	private Calendar calender;
	
	private ActionListener clickDayAction;
	private ActionListener clickDeleteAction;

//#───────생성자
	
	private CalenderUI() {
		// 달력 참조 변수 할당
		this.calender = Calendar.getInstance();
		// 날짜 정보 오늘 날짜로 초기화
		this.day 	= 	this.calender.get(Calendar.DATE);
		this.month 	= 	this.calender.get(Calendar.MONTH);
		this.year 	= 	this.calender.get(Calendar.YEAR);
		
		//
		dayTaskActive(this.year, this.month, this.day);
		
		// 액션 리스너 - 일자별 버튼
		this.clickDayAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton jb = (JButton) e.getSource();
				int d = Integer.parseInt(jb.getText());
				dayTaskActive(d);
			}
		};
		
		// 액션 리스너 - 일정 삭제
		this.clickDeleteAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TButton tb = (TButton) e.getSource();
				delTask(tb.getID());
			}
		};
		
		// 일자별 버튼
		for(JButton dayb : this.dayButton_Array) 
			dayb.addActionListener(this.clickDayAction);
		
		// 다음 달로 넘기기
		super.nextMonth_Button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					nextShowMonth();
				}
			});
		
		// 이전 달로 넘기기
		super.prevMonth_Button.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					prevShowMonth();
				}
			});
		
		// 일정 추가 버튼
		super.addTask_Button.addActionListener( new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e) {
					addSamedayTask();
				}
			});
		
		// 날짜 조회 버튼
		super.searchDate_Button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				betweenDaysTaskActive();
			}
		});
		
		showMonth();
	}
	
//#───────getter setter
	
	public int 	getDay() 			{ return day;}
	public void setDay(int day) 	{ this.day = day;}
	
	public int 	getMonth() 			{ return month;}
	public void setMonth(int month) { this.month = month;}
	
	public int 	getYear() 			{ return year;}
	public void setYear(int year) 	{ this.year = year;}

//#───────메서드 : 달력 표시 변경
	
	/** 달력 날짜 바꾸기 */
	public void showMonth() {
		
		int month = this.calender.get(Calendar.MONTH);
		this.calender.set(Calendar.DATE, 1);
		
		System.out.printf(
				"%4d년 %2d월\n",
				this.calender.get(Calendar.YEAR),
				month + 1);
		
		super.month_Label.setText( String.format("%s월", month + 1) );
		
		//_일자 버튼 패널
		this.calender.set(Calendar.DATE, 1);
		int startDay = this.calender.
							get(Calendar.DAY_OF_WEEK);
		int totalDays = this.calender.
							getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 첫 주의 비어 있는 일자
		int count = 1;
		for(; count < startDay; count++) {
			JButton dayb = super.dayButton_Array[count-1];
			dayb.setText("");
			dayb.setEnabled(false);
		}
		
		// 일자별로 숫자값 기입
		for(int nday = 1; nday <= totalDays; nday++, count++) {
			JButton dayb = super.dayButton_Array[count-1];
			dayb.setText(String.valueOf(nday));
			dayb.setEnabled(true);
		}
		
		// 마지막 주 비어 있는 일자
		for(; count <= super.dayButton_Array.length; count++) {
			JButton dayb = super.dayButton_Array[count-1];
			dayb.setText("");
			dayb.setEnabled(false);
		}
	}
	
	/** 달력 날짜 바꾸기 */
	public void showMonth(int year, int month) {
		
		this.calender.set( Calendar.MONTH, 	month);
		this.calender.set( Calendar.YEAR, 	year);
		showMonth();
	}
	
	/** 다음 달로 넘기기 */
	public void nextShowMonth() {
		if( ++	this.month 	> 11 ) {
				this.year	++;
				this.month 	= 0;
		} showMonth(this.year, this.month);
	}
	
	/** 이전 달로 넘기기 */
	public void prevShowMonth() {
		if( --	this.month 	< 0) {
				this.year	--;
				this.month 	= 11;
		} showMonth(this.year, this.month);
	}

//#───────메서드 : 일정 조회
	/**달력 일별 클릭 시*/
	public void dayTaskActive( int year, int month, int day) {
		// 날짜 설정
		this.year = year;
		this.month = month;
		this.day = day;
		
		// 날짜 표시 라벨 텍스트 재 설정
		super.date_Label.setText(String.format(
						"%5d년%3d월%3d일",
						year, month + 1, day));
		super.taskList_Panel.removeAll();
		// 쿼리 요청
		QueryExecution 	qe = QueryExecution.getInstance();
		
		// 쿼리 결과
		ResultSet 		rs = qe.selectTask(String.format(
							"%04d-%02d-%02d",
							year, month + 1, day));
		
		super.taskList_Panel.removeAll();
		super.taskList_Panel.repaint();
		
		outputTaskBox(rs);
		
		super.taskList_Panel.validate();
	}
	/**달력 일별 클릭 시*/
	public void dayTaskActive( int day) {
		this.dayTaskActive(this.year, this.month, day);
	}
	
	/**사이 날짜 조회*/
	public void betweenDaysTaskActive() {
		// 날짜 설정
		int[] date = DateDialog.betweenDate(this, this.year, this.month, this.day);
		
		// 날짜 표시 라벨 텍스트 재 설정
		super.date_Label.setText(String.format(
						"%5d년%3d월%3d일 ~ %5d년%3d월%3d일",
						date[0], date[1], date[2], date[3], date[4], date[5]));
		super.taskList_Panel.removeAll();
		// 쿼리 요청
		QueryExecution 	qe = QueryExecution.getInstance();
		
		// 쿼리 결과
		String sdate = String.format("%04d-%02d-%02d", date[0], date[1], date[2]);
		String edate = String.format("%04d-%02d-%02d", date[3], date[4], date[5]);
		ResultSet 		rs = qe.selectTask(sdate, edate);
		
		super.taskList_Panel.removeAll();
		super.taskList_Panel.repaint();
		
		outputTaskBox(rs);
		
		super.taskList_Panel.validate();
	}
//#───────메서드 : 조회 결과
	/**조회된 일정 스크롤바에 생성하기*/
	private void outputTaskBox(ResultSet rs) {
	
		try { ;
			while(rs.next()) {
				int 	id 			= rs.getInt		("id");
				String 	tilte 		= rs.getString	("title");
				String 	date 		= rs.getString	("date");
				String 	memo 		= rs.getString	("memo");
				
				TaskBox task = new TaskBox(	id, tilte, date, memo);
						task.del_Button.addActionListener( clickDeleteAction );
				super.taskList_Panel.add(task);
			}
			
		} catch (SQLException e) {
			System.out.println("Task 생성에 문제 발생");
			e.printStackTrace();
		} 
	}

//#───────메서드 : 일정 추가
	/** 빠르게 당일 일정 추가*/
	public void addSamedayTask() {
		
		String title 	= super.task_Field.getText().trim();
		String memo 	= super.memo_Field.getText().trim();
		
		if(title.equals("")) {
			JOptionPane.showMessageDialog(this, "일정을 반드시 입력 해야 합니다.");
			return;
		}
		
		QueryExecution qe = QueryExecution.getInstance();
		String date = String.format(
				"%04d-%02d-%02d 00:00",
				this.year, this.month + 1, this.day);
		
		qe.insertDayTask(title, date, memo);
		dayTaskActive(this.year, this.month, this.day);
		
		super.task_Field.setText("");
		super.memo_Field.setText("");
	}
	
//#───────메서드 : 일정 삭제
	/** 일정 삭제*/
	public void delTask( int id) {
		QueryExecution qe = QueryExecution.getInstance();
		qe.delTask(id);
		dayTaskActive(this.year, this.month, this.day);
	}
}
