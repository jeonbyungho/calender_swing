package calender.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class DateDialog extends JDialog{
	
	public JTextField sYear;
	public JTextField sMonth;
	public JTextField sDay;
	
	public JTextField eYear;
	public JTextField eMonth;
	public JTextField eDay;
	
	private DateDialog(JFrame frame, int year, int month, int day) {
		super(frame);
		this.setLayout(new GridLayout(3, 2, 5, 3));
		
		this.sYear 	= new JTextField(String.valueOf(year));
		this.sMonth = new JTextField(String.valueOf(month + 1));
		this.sDay 	= new JTextField(String.valueOf(day));
		
		this.eYear 	= new JTextField(String.valueOf(year));
		this.eMonth = new JTextField(String.valueOf(month + 1));
		this.eDay 	= new JTextField(String.valueOf(day + 1));
		
		JLabel sy = new JLabel("년");
		JLabel sm = new JLabel("월");
		JLabel sd = new JLabel("일");
		
		JLabel gap = new JLabel("  ~  ");
		gap.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		JLabel ey = new JLabel("년");
		JLabel em = new JLabel("월");
		JLabel ed = new JLabel("일");
		
		// 버튼 액션 리스너
		JButton checkButton = new JButton("조회");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		
		// 화면 구성
		Box boxleft = Box.createHorizontalBox();
		Box boxright = Box.createHorizontalBox();
		
		boxleft.add(this.sYear);
		boxleft.add(sy);
		boxleft.add(this.sMonth);
		boxleft.add(sm);
		boxleft.add(this.sDay);	
		boxleft.add(sd);
		
		boxright.add(this.eYear);
		boxright.add(ey);
		boxright.add(this.eMonth);
		boxright.add(em);
		boxright.add(this.eDay);
		boxright.add(ed);
		
		Box box = Box.createHorizontalBox();
		
		box.add(boxleft);
		box.add(gap);
		box.add(boxright);
		
		this.add(box);
		this.add(checkButton);
		
		this.setTitle("일정 조회");
		this.setSize(600,100);
		this.setLocation(400, 150);
		
		this.setModal(true);
		this.setVisible(true);
	}
	
	/**시작 날짜 년[0] 월[1] 일[2] 끝날짜 년[3] 월[4] 일[5]*/
	public static int[] betweenDate(JFrame frame, int year, int month, int day) {
		DateDialog panel = new DateDialog(frame, year, month, day);
		
		int sy = Integer.parseInt(panel.sYear.getText());
		int sm = Integer.parseInt(panel.sMonth.getText());
		int sd = Integer.parseInt(panel.sDay.getText());
		
		int ey = Integer.parseInt(panel.eYear.getText());
		int em = Integer.parseInt(panel.eMonth.getText());
		int ed = Integer.parseInt(panel.eDay.getText());
		
		return new int[]{sy, sm, sd, ey, em, ed};
		
	}
}
