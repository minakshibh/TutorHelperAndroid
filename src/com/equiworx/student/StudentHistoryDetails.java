package com.equiworx.student;

import com.equiworx.tutorhelper.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentHistoryDetails extends Activity {
	
	private TextView tv_title,tv_name,tv_description,tv_stime,tv_duration,tv_sdate,tv_edate,tv_recuring;
	private TextView 	sun ,mon ,tue ,wed ,thur,fri ,sat ;
	private ImageView back;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_studenthistorydetails);
		
		intializelayout();
		setOnClickListners();
		getLessonDetail();
		
	}
	private void intializelayout() {
		
		tv_title=(TextView)findViewById(R.id.title);
		tv_title.setText("Lesson Details");
		back=(ImageView)findViewById(R.id.back);
		tv_name=(TextView)findViewById(R.id.textView1);
		tv_description=(TextView)findViewById(R.id.textView2);
		tv_stime=(TextView)findViewById(R.id.textView3);
		//tv_etime=(TextView)findViewById(R.id.textView4);
		tv_duration=(TextView)findViewById(R.id.textView5);
		
		tv_sdate=(TextView)findViewById(R.id.textView6);
		tv_edate=(TextView)findViewById(R.id.textView7);
		tv_recuring=(TextView)findViewById(R.id.textView8);
		//listView=(ListView)findViewById(R.id.listView);
	
		sun = (TextView)findViewById(R.id.sun);
		mon = (TextView)findViewById(R.id.mon);
		tue = (TextView)findViewById(R.id.tue);
		wed = (TextView)findViewById(R.id.wed);
		thur = (TextView)findViewById(R.id.thur);
		fri = (TextView)findViewById(R.id.fri);
		sat = (TextView)findViewById(R.id.sat);
	}

	private void getLessonDetail() {
		tv_name.setText(""+ getIntent().getStringExtra("tname"));
		tv_description.setText(""+getIntent().getStringExtra("description"));
		tv_stime.setText(""+getIntent().getStringExtra("stime")+" - "+getIntent().getStringExtra("etime"));
		tv_duration.setText(getIntent().getStringExtra("duration"));
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
		
	

	private void setOnClickListners() {
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				}
			});
	}


	
}
