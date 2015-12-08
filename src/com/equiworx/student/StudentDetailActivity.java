package com.equiworx.student;

import com.equiworx.tutorhelper.R;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class StudentDetailActivity extends Activity{
	private ImageView back;
	private TextView name,email,fees,address,contact,notes,parentame,studentid,edit,lbl_notes;
	private LinearLayout lay_call,lay_email,button_history,lay_fees,enable_layout;
	private SharedPreferences tutorPrefs;
	
protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);	
			setContentView(R.layout.activity_studentdetail);
			
			initializelayout();
			fetchStudentDetails();
			onclickListenser();
}

	private void initializelayout() {
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		back=(ImageView)findViewById(R.id.back);
		edit=(TextView)findViewById(R.id.edit);
		name=(TextView)findViewById(R.id.name);
		email=(TextView)findViewById(R.id.email);
		address=(TextView)findViewById(R.id.address);
		notes=(TextView)findViewById(R.id.notes);
		contact=(TextView)findViewById(R.id.contact);
		fees=(TextView)findViewById(R.id.fees);
		parentame=(TextView)findViewById(R.id.parentname);
		studentid=(TextView)findViewById(R.id.studentid);
		lbl_notes=(TextView)findViewById(R.id.lbl_notes);
		lay_call=(LinearLayout)findViewById(R.id.lay_contact);
		lay_email=(LinearLayout)findViewById(R.id.lay_email);
		lay_fees=(LinearLayout)findViewById(R.id.lay_fees);
		enable_layout=(LinearLayout)findViewById(R.id.enable_layout);
		button_history=(LinearLayout)findViewById(R.id.layout_history);
		if(tutorPrefs.getString("mode", "").equalsIgnoreCase("tutor"))
		{
			button_history.setVisibility(View.GONE);
		}
		else{
			button_history.setVisibility(View.VISIBLE);
			lbl_notes.setVisibility(View.GONE);
			notes.setVisibility(View.GONE);
			lay_fees.setVisibility(View.GONE);
		}
		
	}
	private void fetchStudentDetails() {
		
		try{
		String str=getIntent().getStringExtra("email");
		SpannableString content = new SpannableString(str);
		content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
		email.setText(content);
		
		address.setText(""+getIntent().getStringExtra("address"));
		
		String str1=getIntent().getStringExtra("phone");
		SpannableString content1 = new SpannableString(str1);
		content1.setSpan(new UnderlineSpan(), 0, str1.length(), 0);
		contact.setText(content1);
		if (getIntent().getStringExtra("isActiveInMonth").equalsIgnoreCase("0")) {
			enable_layout.setVisibility(View.VISIBLE);
			name.setTextColor(Color.parseColor("#000000"));
			name.setText(""+getIntent().getStringExtra("name"));
			studentid.setText("Student Id :"+getIntent().getStringExtra("studentid"));
			studentid.setTextColor(Color.parseColor("#000000"));
		}else{
			name.setText(""+getIntent().getStringExtra("name"));
			
			name.setTextColor(Color.parseColor("#54c5d5"));
			
			enable_layout.setVisibility(View.GONE);
			studentid.setText("Student Id :"+getIntent().getStringExtra("studentid"));
			studentid.setTextColor(Color.parseColor("#54c5d5"));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//contact.setText(""+getIntent().getStringExtra("phone"));
		fees.setText("$ "+getIntent().getStringExtra("fees"));
		notes.setText(getIntent().getStringExtra("notes"));
		parentame.setText(""+getIntent().getStringExtra("parentid"));
		
		
	}

	private void onclickListenser() {
	back.setOnClickListener(new View.OnClickListener() {
	public void onClick(View v) {
		finish();
			
		}
	});
	lay_call.setOnClickListener(new View.OnClickListener() {
	public void onClick(View v) {
		String uri = "tel:" + contact.getText().toString().trim();
		 Intent intent = new Intent(Intent.ACTION_CALL);
		 intent.setData(Uri.parse(uri));
		 startActivity(intent);
			
		}
	});	
	lay_email.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
		            "mailto",email.getText().toString().trim(), null));
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
				
			}
		});	
	edit.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
	
		Intent intent=new Intent(StudentDetailActivity.this,EditStudentActivity.class);
		intent.putExtra("name",getIntent().getStringExtra("name") );
		intent.putExtra("email",getIntent().getStringExtra("email") );
		intent.putExtra("contactno", getIntent().getStringExtra("phone"));
		intent.putExtra("fees",getIntent().getStringExtra("fees") );
		intent.putExtra("address",getIntent().getStringExtra("address") );
		intent.putExtra("gender",getIntent().getStringExtra("gender") );
		intent.putExtra("notes", getIntent().getStringExtra("notes"));
		intent.putExtra("parentid", getIntent().getStringExtra("parentid"));
		intent.putExtra("studentid", getIntent().getStringExtra("studentid"));
		intent.putExtra("check", "Parent");
		startActivity(intent);
		finish();
				
			}
		});
	button_history.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		Intent intent=new Intent(StudentDetailActivity.this,StudentHistoryActivity.class);
		intent.putExtra("studentid", getIntent().getStringExtra("studentid"));
		startActivity(intent);			
		}
	});
	}

}