package com.equiworx.lesson;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.model.StudentList;
import com.equiworx.tutorhelper.R;

public class LessonDetailsActivity extends Activity {
	private TextView tv_title,tv_name,tv_description,tv_stime,tv_duration,
	tv_sdate,tv_edate,tv_recuring,note,lbl_student;
	private TextView 	sun ,mon ,tue ,wed ,thur,fri ,sat ;
	private RelativeLayout back_layout;
	private LinearLayout student_row,main_layout,lay_notes;
	private StudentList studentlist;
	
    public  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lesson_details1);
		
		intializelayout();
		setOnClickListners();
		getLessonDetail();
		if(getIntent().getStringExtra("check")==null)
		{
			getStudentsLayout();
			tv_title.setText("Lesson Details");
		
		}
		else
		{
			lay_notes.setVisibility(View.GONE);
			note.setVisibility(View.GONE);
			lbl_student.setVisibility(View.GONE);
			tv_title.setText("Student History");
		}
		
}
	private void intializelayout() {
		studentlist=new StudentList();
		
		tv_title=(TextView)findViewById(R.id.title);
	
		student_row=(LinearLayout)findViewById(R.id.student_row);
		main_layout=(LinearLayout)findViewById(R.id.main_layout);
		lay_notes=(LinearLayout)findViewById(R.id.lay_notes);
		tv_name=(TextView)findViewById(R.id.textView1);
		tv_description=(TextView)findViewById(R.id.textView2);
		tv_stime=(TextView)findViewById(R.id.textView3);
		note=(TextView)findViewById(R.id.note);
		tv_duration=(TextView)findViewById(R.id.textView5);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		tv_sdate=(TextView)findViewById(R.id.textView6);
		tv_edate=(TextView)findViewById(R.id.textView7);
		tv_recuring=(TextView)findViewById(R.id.textView8);
		lbl_student=(TextView)findViewById(R.id.lbl_student);
	
		sun = (TextView)findViewById(R.id.sun);
		mon = (TextView)findViewById(R.id.mon);
		tue = (TextView)findViewById(R.id.tue);
		wed = (TextView)findViewById(R.id.wed);
		thur = (TextView)findViewById(R.id.thur);
		fri = (TextView)findViewById(R.id.fri);
		sat = (TextView)findViewById(R.id.sat);
			
	}
	private void getStudentsLayout() {
				
		for(int i=0;i<MyLessonActivity.arraylist_studentlist.size();i++)
		{
			studentlist = MyLessonActivity.arraylist_studentlist.get(i);
			
		
			if(student_row == null){
				View convertView = getLayoutInflater().inflate(R.layout.studentlist_row, main_layout, false);
				TextView	lbl_notes =(TextView)convertView.findViewById(R.id.lbl_notes);
				lbl_notes.setVisibility(View.GONE);
				TextView outstandingBalnce =(TextView)convertView.findViewById(R.id.outstandingBalnce);
				outstandingBalnce.setVisibility(View.VISIBLE);
				lbl_notes.setText("Address");
				TextView	lbl_address =(TextView)convertView.findViewById(R.id.lbl_address);
				//lbl_address.setVisibility(View.GONE);
				
				TextView tv_name = (TextView)convertView.findViewById(R.id.studentname);
				TextView tv_email = (TextView)convertView.findViewById(R.id.Email);
				TextView tv_note = (TextView)convertView.findViewById(R.id.Notes);
				TextView tv_contactno = (TextView)convertView.findViewById(R.id.contactno);
				ImageView img_view=(ImageView)convertView.findViewById(R.id.editImg);
				img_view.setVisibility(View.GONE);
				tv_contactno.setText(": "+studentlist.getContactInfo());
				TextView student_fees_text=(TextView)convertView.findViewById(R.id.student_fees_text);
				tv_name.setText(studentlist.getName());
				tv_email.setText(": "+studentlist.getEmail());
				tv_note.setText(": "+studentlist.getAddress());
				student_fees_text.setVisibility(View.VISIBLE);
				student_fees_text.setText("Fees");
				outstandingBalnce.setText(": "+studentlist.getStudentfee());
				main_layout.addView(convertView);
			
			}
			
		}
		//addlessonview();
	}

	private void setOnClickListners() {
		back_layout.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			finish();
			}
		});
		//listView.setc);
		
	}

	private void getLessonDetail() {
		
		tv_name.setText(""+ getIntent().getStringExtra("tname"));
		tv_description.setText(""+getIntent().getStringExtra("description"));
		try{
			tv_stime.setText(""+getIntent().getStringExtra("stime").subSequence(0, 5)+" - "+getIntent().getStringExtra("etime").subSequence(0, 5));
			}
		catch(Exception e)
		{
			e.printStackTrace();
			}                 
		//Float hr=Float.parseFloat(getIntent().getStringExtra("duration")); //crash
		//Float hours=hr/60;
		if (getIntent().getStringExtra("duration").equalsIgnoreCase("0")) {
			tv_duration.setText(getIntent().getStringExtra("duration")+" hour");
		}else if (getIntent().getStringExtra("duration").equalsIgnoreCase("1")) {
			tv_duration.setText(getIntent().getStringExtra("duration")+" hour");
		}else{
			tv_duration.setText(getIntent().getStringExtra("duration")+" hours");
			
		}
		
		
		tv_sdate.setText(getIntent().getStringExtra("sdate"));
		tv_edate.setText(getIntent().getStringExtra("edate"));
		
		tv_recuring.setText(getIntent().getStringExtra("recuring"));
	
		getIntent().getStringExtra("tid");
		getIntent().getStringExtra("lid");
		
		
		String getdays=	getIntent().getStringExtra("day");	
		System.err.println("day="+getdays);
	
		if(getdays.contains("Sunday"))
		{
			sun.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			sun.setTextColor(getResources().getColor(R.color.gray));
		}
		if(getdays.contains("Monday"))
		{
			mon.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			mon.setTextColor(getResources().getColor(R.color.gray));
		}
		if(getdays.contains("Tuesday"))
		{
			tue.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			tue.setTextColor(getResources().getColor(R.color.gray));
			}
		if(getdays.contains("Wednesday"))
		{
			wed.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			wed.setTextColor(getResources().getColor(R.color.gray));
		}
		if(getdays.contains("Thursday"))
		{
			thur.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			thur.setTextColor(getResources().getColor(R.color.gray));
		}
		if(getdays.contains("Saturday"))
		{
			sat.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			sat.setTextColor(getResources().getColor(R.color.gray));
		}
		if(getdays.contains("Friday"))
		{
			fri.setTextColor(getResources().getColor(R.color.appBlue));
			}
		else
		{
			fri.setTextColor(getResources().getColor(R.color.gray));
		}
	}

}
