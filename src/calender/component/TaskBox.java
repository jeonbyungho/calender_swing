package calender.component;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
class TButton extends JButton{
	private final int id;
	public int getID() { return this.id;}
	public TButton(String str,int id) {
		super(str);
		this.id = id;
	}
}

@SuppressWarnings("serial")
public class TaskBox extends JPanel{
	
	private final int 	id;
	private String 		title;
	private String 		date;
	private String 		memo;
	
	private JLabel 		date_Label;
	private JLabel 		title_Label;
	private JLabel 		memo_Label;
	
	private Box 		label_layout;
	private Box 		button_layout;
	
	public JButton 		update_Button;
	public JButton 		del_Button;
	
	public int getID() { return id; }
	
	public TaskBox(	int id, String title, String date, String memo) {
		super(new BorderLayout());
		
		this.id 		= id;
		this.title 		= title;
		this.date 	= date;
		this.memo 		= memo;
		
		// 라벨
		this.date_Label 	= new JLabel();
		
		this.title_Label 	= new JLabel();
		this.title_Label.setHorizontalAlignment(JLabel.CENTER);
		this.title_Label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		
		this.memo_Label 	= new JLabel();
		
		// 버튼
		this.update_Button 	= new TButton("수정", this.id);
		this.update_Button.setName("update");
		
		this.del_Button 	= new TButton("🗑️", this.id);
		this.del_Button.setName("del");
		this.del_Button.setFont(new Font("", Font.BOLD, 22));
		// 라벨 수직 레이아웃
		this.label_layout 	= Box.createVerticalBox();
		
		this.label_layout.add(date_Label);
		this.label_layout.add(title_Label);
		this.label_layout.add(memo_Label);
		
		// 버튼 수직 레이아웃
		this.button_layout 	= Box.createVerticalBox();
		
//		this.button_layout.add(update_Button);
		this.button_layout.add(del_Button);
		
		// 패널에 요소 삽입
		this.add(label_layout, BorderLayout.CENTER);
		this.add(button_layout, BorderLayout.LINE_END);
		
		makeComponent();
	}
	
	/** 날짜 문자열 나누기
	 * @return { [0] 년도, [1] 월, [2] 일, [3] 시간, [4] 분 }*/
	private String[] dateSplit(String datetime) {
		String[] divDatetime 	= 	datetime.split(" ");
		String[] date 			= 	divDatetime[0].split("-");
		String[] time 			= 	divDatetime[1].split(":");
		
		return new String[] {date[0], date[1], date[2], time[0], time[1]};
	}
	
	/**화면 구성하기*/
	private void makeComponent() {
		
		// 날짜 라벨
		String[] startDate = dateSplit(this.date);
		this.date_Label.setText(" ● " + String.format("%s월 %s일", startDate[1], startDate[2]));
		
		// 제목 라벨
		this.title_Label.setText(this.title);
		
		// 메모 라벨
		if(this.memo == null)
			this.memo_Label.setEnabled(false);
		else {
			this.memo_Label.setEnabled(true);
			this.memo_Label.setText(this.memo);
		}
		
		
	}
	
}
