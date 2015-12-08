package com.equiworx.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.equiworx.lesson.LessonRequestActivity;
import com.equiworx.lesson.MyLessonActivity;
import com.equiworx.parent.ConnectionRequests;
import com.equiworx.parent.ParentDashBoard;
import com.equiworx.student.MyStudentActivity;
import com.equiworx.student.StudentRequestActivity;
import com.equiworx.tutor.TutorDashboard;
import com.equiworx.tutorhelper.R;


public class NotificationParentActivity extends Activity{
	private String message=null;
	private TextView tv_message;
	private Button ok;
    LinearLayout parentnotification;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_notificationparent);
		this.setFinishOnTouchOutside(false);
		
		
	message=getIntent().getStringExtra("message");
	System.err.println("message"+message);
	tv_message=(TextView)findViewById(R.id.message);
	ok=(Button)findViewById(R.id.ok);
	tv_message.setText(message);
	
	parentnotification=(LinearLayout)findViewById(R.id.parentnotification);
	parentnotification.setFocusable(isRestricted());

		final String str=tv_message.getText().toString();
		ok.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(str.contains("requested to give a lesson"))	//lesson request
			{
				Intent i=new Intent(NotificationParentActivity.this,LessonRequestActivity.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("Accepted the request for lesson"))	//accept lesson
			{
				
				Intent i=new Intent(NotificationParentActivity.this,ParentDashBoard.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("Rejected the request for lesson"))	//reject lesson
			{
				//Mr. test3 have Rejected the request for lesson on 
				/*Intent i=new Intent(NotificationParentActivity.this,MyLessonActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("connection request"))	//connection request
			{
				Intent i=new Intent(NotificationParentActivity.this,ConnectionRequests.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("connection request has been approved"))	//connection approve
			{
				/*Intent i=new Intent(NotificationParentActivity.this,ParentListActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("connection request Rejected"))	//connection reject
			{
				/*Intent i=new Intent(NotificationParentActivity.this,ParentListActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("have been added to your account"))	//student request
			    {
				Intent i=new Intent(NotificationParentActivity.this,StudentRequestActivity.class);
				startActivity(i);
				finish();
				}
				
			else if(str.contains("have been Approved"))	//student request Approved
			{
				Intent i=new Intent(NotificationParentActivity.this,MyStudentActivity.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("have been Rejected"))	//student request Rejected
			{
				/*Intent i=new Intent(NotificationParentActivity.this,MyStudentActivity.class);
				startActivity(i);*/
				finish();
				}
			
			else if(message.contains("lesson cancellation request has been approved"))	//lesson cancellation request for approved
			{
				//Your lesson cancellation request has been approved by Mr. amrik singh
				Intent i=new Intent(NotificationParentActivity.this,MyLessonActivity.class);
				startActivity(i);
				finish();
				}
			else if(message.contains("lesson cancellation request has been rejected"))	//lesson cancellation request for rejected
			{
				/*Intent i=new Intent(NotificationParentActivity.this,MyLessonActivity.class);
				startActivity(i);*/
				finish();
				//Your lesson cancellation request has been rejected by Mr. amrik singh
				}
				
		}
	});
			
	}	
}