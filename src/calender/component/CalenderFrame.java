package calender.component;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class CalenderFrame extends JFrame{

//#───────멤버 변수
	
	// 캘린더 참조 변수
	protected JLabel 		month_Label;
	protected JButton 		nextMonth_Button;
	protected JButton 		prevMonth_Button;
	protected JButton[] 	dayButton_Array;
	
	// 일정란 참조 변수
	protected JLabel 		date_Label;
	protected JButton 		searchDate_Button;
	protected JTextField 	task_Field;
	protected JTextArea 	memo_Field;
	protected JButton 		addTask_Button;
	protected JPanel 		taskList_Panel;
	
//#───────getter
	
	public JLabel 		getMonth_Label() 		{ return month_Label; }
	public JButton 		getNextMonth_Button() 	{ return nextMonth_Button; }
	public JButton 		getPrevMonth_Button() 	{ return prevMonth_Button; }
	public JButton[] 	getDayButton_Array() 	{ return dayButton_Array; }
	
	public JLabel 		getDate_Label() 		{ return date_Label; }
	public JTextField 	getTask_Field() 		{ return task_Field; }
	public JButton 		getAddTask_Button() 	{ return addTask_Button; }

//#───────생성자
	
	protected CalenderFrame() {
		// 메인 - 상하 정렬 박스 레이아웃
		Box box = Box.createVerticalBox();
		this.add(box);
	
		// 메인 프레임 요소 삽입 - 캘린더는 상단, 일정 정보는 하단
		Component cal 	= calender_MainComponent();
		Component task 	= task_MainComponent();
		
		box.add(cal);
		box.add(task);
		
		// 프레임 정보 세팅
		this.setTitle("캘린더");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,600);
		this.setLocation(500, 150);
		this.setVisible(true);
		
	}

//#───────메서드
	
	/** 일정 정보란 구성함 */
	private JPanel task_MainComponent() {
		// 하단 전체 컴포넌트 패널
		JPanel panel = new JPanel(new BorderLayout());
		
		// 날짜 레이어
		JPanel dateBox = new JPanel(new FlowLayout());
		
		// 날짜 라벨
		JLabel 	date = new JLabel();
				date.setHorizontalAlignment(JLabel.LEFT);
				date.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		this.date_Label = date;
		
		// 날짜 검색 버튼
		JButton searchbutton = new JButton("🔍");
		searchbutton.setName("Search Date");
		this.searchDate_Button = searchbutton;
		
		dateBox.add(date);
		dateBox.add(searchbutton);
		
		// 입력란 - 좌우 정렬 박스 레이아웃
		JPanel subBox = new JPanel(new BorderLayout());
		
		// 일정 라벨
		JLabel 	task = new JLabel("일정");
				task.setHorizontalAlignment(JLabel.RIGHT);
				task.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		// 일정 입력란
		JTextField title_field = new JTextField();
		title_field.setPreferredSize(new Dimension(300, 40));
		this.task_Field = title_field;
		
		
		// 메모 레이어
		JPanel 	memoBox = new JPanel(new BorderLayout());
		
		// 메모 라벨
		JLabel 	memo = new JLabel("메모");
				memo.setHorizontalAlignment(JLabel.CENTER);
				memo.setFont(new Font("맑은 고딕", Font.BOLD, 17));
				
		// 메모란
		JTextArea  memo_field = new JTextArea ();
		memo_field.setPreferredSize(new Dimension(300, 40));
		this.memo_Field = memo_field;
		
		memoBox.add(memo, BorderLayout.NORTH);
		memoBox.add(memo_field, BorderLayout.CENTER);
		
		// 일정 추가 버튼
		JButton addt = new JButton("추가");
				addt.setName("Add Task");
		this.addTask_Button = addt;
		
		subBox.add(task, 		BorderLayout.WEST);
		subBox.add(title_field, BorderLayout.CENTER);
		subBox.add(addt, 		BorderLayout.EAST);
		subBox.add(memoBox, 	BorderLayout.SOUTH);
		
		// 스크롤 일정
		JPanel 	listBox = new JPanel();
		listBox.setLayout(new BoxLayout(listBox, BoxLayout.Y_AXIS) );
		this.taskList_Panel = listBox;
		JScrollPane scroll 		= new JScrollPane(listBox);
					scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					
		// 페널에 요소 삽입
		panel.add(dateBox, 	BorderLayout.NORTH);
		panel.add(scroll, 	BorderLayout.CENTER);
		panel.add(subBox, 	BorderLayout.SOUTH);
		
		return panel;
	}
	
	/** 캘린더를 구성함 */
	private Box calender_MainComponent() {
		// 상단 전체 컴포넌트 패널
		Box box = Box.createVerticalBox();
		
		// 캘린더 상단 - 좌우버튼 중앙라벨 보더 레이어
		JPanel 	top = new JPanel(new BorderLayout());
				top.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
		
		// 이번달 표시 라벨
		JLabel 	month = new JLabel();
				month.setHorizontalAlignment(SwingConstants.CENTER);
				month.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		this.month_Label = month;
		
		JButton prev = new JButton("⬅️");
				prev.setName("Previ Month");
		this.prevMonth_Button = prev;
		
		JButton next = new JButton("➡");
				next.setName("Next Month");
		this.nextMonth_Button = next;
		
		top.add(prev, 	BorderLayout.WEST);
		top.add(month, 	BorderLayout.CENTER);
		top.add(next, 	BorderLayout.EAST);
		
		// 박스 레이어에 요소 삽입
		box.add(top);
		box.add(dayButton_Panel());
		
		return box;
	}
	
	/** 일별 버튼 생성함 */
	private JPanel dayButton_Panel() {
		
		JPanel panel = new JPanel(new GridLayout(0, 7, 5, 5));
		
		// 일 ~ 토 라벨
		String[] 	dayOfweek = {"일","월", "화", "수", "목", "금", "토"};
		for (String str : dayOfweek) {
			
			JLabel 	day = new JLabel(str);
					day.setHorizontalAlignment(JLabel.CENTER);
					day.setFont(new Font("맑은 고딕", Font.BOLD, 18));
					
			panel.add(day);
		}
		
		// 일자별 버튼
		JButton[] arr = new JButton[42];
		for (int i = 0; i < arr.length; i++) {
			
			JButton day = new JButton(String.valueOf(i));
					day.setName("Day");
					
			arr[i] = day;
			panel.add(day);
		}
		this.dayButton_Array = arr;
		
		return panel;
	}
}
