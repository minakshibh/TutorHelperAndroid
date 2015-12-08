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
import com.equiworx.tutor.CancellationActivity;
import com.equiworx.tutorhelper.R;

public class NotificationTutorActivity extends Activity {
	private String message;
	private TextView tv_message;
	private Button ok;
	LinearLayout tutornotification;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_notificationtutor);
		this.setFinishOnTouchOutside(false);
		initializelayout();
		onclickListenser();
	
	}

	private void initializelayout() {
		tutornotification=(LinearLayout)findViewById(R.id.tutornotification);
		tutornotification.setFocusable(isRestricted());
		
		message=getIntent().getStringExtra("message");
		System.err.println("message"+message);
		message=getIntent().getStringExtra("message");
		System.err.println("message"+message);
		tv_message=(TextView)findViewById(R.id.message);
		ok=(Button)findViewById(R.id.ok);
		tv_message.setText(message);

	}
	
	private void onclickListenser() {
		final String str=tv_message.getText().toString();
		
		
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(str.contains("requested a lesson"))	//lesson request
			{
				Intent i=new Intent(NotificationTutorActivity.this,LessonRequestActivity.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("Accepted the request for lesson"))	//accept lesson
			{
				//Mrs. test3 have Accepted the request for lesson on 

				Intent i=new Intent(NotificationTutorActivity.this,MyLessonActivity.class);
				startActivity(i);
				finish();
				}
			else if(str.contains("Rejected the request for lesson"))	//reject lesson
			{
				//Mrs. test3 have Rejected the request for lesson on 
			

				/*Intent i=new Intent(NotificationTutorActivity.this,MyLessonActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("connection request"))	//connection request
			{
				//amrik singh has sent you a connection request. Please check the mobile app in order to approve/reject the same.
				/*Intent i=new Intent(NotificationTutorActivity.this,ConnectionRequests.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("connection request has been approved"))	//connection approve //connection request has been approved
			{
				/*Intent i=new Intent(NotificationTutorActivity.this,ParentListActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("connection request rejected"))	//connection reject
			{
				/*Intent i=new Intent(NotificationTutorActivity.this,ParentListActivity.class);
				startActivity(i);*/
				finish();
				}
			else if(str.contains("have been added to your account"))	//student request
			{
			/*	Intent i=new Intent(NotificationTutorActivity.this,StudentRequestActivity.class);
				startActivity(i);*/
				//Tutor Request to parent== A new student named happy have been added to your account by nav. 
				//Please check the mobile app in order to approve/reject the same.
				finish();
				}
				
			else if(str.contains("have been Approved"))	//student request Approved
			{
				// Your request for student creation for the student named dfgd have been Approved by Mrs test3

				finish();
				} 
			else if(str.contains("have been Rejected"))	//student request Rejected
			{
				//Your request for student creation for the student named new student. have been Rejected by Mrs test3

				finish();
				}
			else if(str.contains("have cancelled the request for lesson on"))	
			{
				Intent i=new Intent(NotificationTutorActivity.this,CancellationActivity.class);
				startActivity(i);
				finish();
				}
			}
	
		});	
		
	}

	
}
