package com.equiworx.tutorhelper;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Tutor;
import com.equiworx.tutor.TutorDashboard;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class RegisterActivity extends Activity implements AsyncResponseForTutorHelper{

	private EditText name, email, password, confirmPassword, contactNumber, alternateContactNumber,
	contactInfo_countryCode,altContactInfo_countryCode, address;
	private Button register;
	private RadioButton male, female;
	private String trigger, gender="Male", tutor_id="";
	private Tutor tutor;
	private ImageView imgview_back;
	private TextView tv_title;
	private RelativeLayout back_layout;
	private String zipcode="";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_register);
		
		zipcode="+"+GetCountryZipCode();
		initializeLayout();
		setClickListeners();
	}
	
	private void initializeLayout(){
		
		tv_title=(TextView)findViewById(R.id.title);
		tv_title.setText("Tutor Helper");
		imgview_back=(ImageView)findViewById(R.id.back);
		
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		name = (EditText)findViewById(R.id.name);
		email = (EditText)findViewById(R.id.email);
		//userName = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.password);
		confirmPassword = (EditText)findViewById(R.id.confirmPassword);
		contactInfo_countryCode= (EditText)findViewById(R.id.contactInfo_countryCode);
		contactInfo_countryCode.setText(zipcode);
		contactNumber = (EditText)findViewById(R.id.contactInfo);
		alternateContactNumber = (EditText)findViewById(R.id.altContactInfo);
		altContactInfo_countryCode= (EditText)findViewById(R.id.altContactInfo_countryCode);
		altContactInfo_countryCode.setText(zipcode);
		
		address = (EditText)findViewById(R.id.address);
		male = (RadioButton)findViewById(R.id.male);
		female = (RadioButton)findViewById(R.id.female);
		
		register = (Button)findViewById(R.id.register);
		
		trigger = getIntent().getStringExtra("trigger");
		
		male.setChecked(true);
		female.setChecked(false);
		
		if(trigger.equals("edit")){
			tutor = getIntent().getParcelableExtra("Tutor");
			
			name.setText(tutor.getName());
			email.setText(tutor.getEmail());
			//userName.setText(tutor.getTutorId());
			contactNumber.setText(tutor.getContactNumber());
			alternateContactNumber.setText(tutor.getAltContactNumber());
			address.setText(tutor.getAddress());
			tutor_id = tutor.getTutorId();
		}
	}

	private void setClickListeners(){
		register.setOnClickListener(listener);
		male.setOnClickListener(listener);
		female.setOnClickListener(listener);
		imgview_back.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			finish();
				
			}
		});
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
					
				}
			});
	}
	
	private String emptyFieldCheck(){
		String gettingEmail=email.getText().toString().trim();
		if(name.getText().toString().trim().equals("") || email.getText().toString().trim().equals("")
			|| password.getText().toString().trim().equals("") || confirmPassword.getText().toString().trim().equals("")
			|| contactNumber.getText().toString().trim().equals("") || address.getText().toString().trim().equals("")){
				return "Please fill in required fields.";
			}else if(!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
				return "Passwords do not match";
			}
			else if(!Util.isValidPhoneNumber(contactNumber.getText().toString()))
			{
				return "Please enter valid phone number";
			}
			/*else if((contactNumber.getText().toString().trim().length()<13)){
				return "Please enter contact number minimum 10 digits.";
			}*/
			else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(gettingEmail).matches() && !TextUtils.isEmpty(gettingEmail)) {
				return "Please enter a valid email address";
				}
				
			else
				return "success";
	}
	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v == register){
				String result = emptyFieldCheck();
				if(result.equals("success")){
					
					String strContactNumber=contactInfo_countryCode.getText().toString().trim()+contactNumber.getText().toString().trim();
					String alertNumber=altContactInfo_countryCode.getText().toString().trim()+alternateContactNumber.getText().toString().trim();
					
					if (Util.isNetworkAvailable(RegisterActivity.this)){
						
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("email",email.getText().toString().trim()));
						//nameValuePairs.add(new BasicNameValuePair("username", userName.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("password",password.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("phone", strContactNumber));
						nameValuePairs.add(new BasicNameValuePair("alternate_phone",alertNumber));
						nameValuePairs.add(new BasicNameValuePair("address", address.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("trigger",trigger));
						nameValuePairs.add(new BasicNameValuePair("gender",gender));
						nameValuePairs.add(new BasicNameValuePair("tutor_id",tutor_id));
						
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(RegisterActivity.this, "register", nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) RegisterActivity.this;
						mLogin.execute();
					}else {
						Util.alertMessage(RegisterActivity.this,
								"Please check your internet connection");
					}
				}else
					Util.alertMessage(RegisterActivity.this, result);
			}
			else if(v == male)
			{
				male.setChecked(true);
				female.setChecked(false);
				gender = "Male";
			}else if(v == female){
				female.setChecked(true);
				male.setChecked(false);
				gender = "Female";
			}
		}
	};
	
	@Override
	public void processFinish(String output, String methodName) {
		//Util.alertMessage(RegisterActivity.this, output);
		TutorHelperParser parser = new TutorHelperParser(RegisterActivity.this);
		tutor = parser.parseTutorDetails(output);
		
		if(tutor != null){
			AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
			alert.setTitle("Tutor Helper");
			alert.setCancelable(false);
			alert.setMessage("Congrats!!! Your account has been registered with us. your tutor Id is "+tutor.getTutorId());
			alert.setPositiveButton("Get Started!", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					for(int i = 0; i<HomeAcitivity.cacheActivities.size(); i++){
						HomeAcitivity.cacheActivities.get(i).finish();
					}
					
					SharedPreferences tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
					Editor editor = tutorPrefs.edit();
					editor.putString("tutorID", tutor.getTutorId());
					editor.putString("mode", "tutor");
					editor.putString("tutorname", tutor.getName());
					editor.putString("tutorpass", password.getText().toString());
					editor.commit();
					
					Intent intent = new Intent(RegisterActivity.this, TutorDashboard.class);
					startActivity(intent);
					finish();
				}
			});	
			alert.show();
		}
	}
	public String GetCountryZipCode() {
		String CountryID = "";
		String CountryZipCode = "";
		TelephonyManager manager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		// getNetworkCountryIso
		CountryID = manager.getNetworkCountryIso().toUpperCase();
		String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
		for (int i = 0; i < rl.length; i++) {
			String[] g = rl[i].split(",");
			if (g[1].trim().equals(CountryID.trim())) {
				CountryZipCode = g[0];
				break;
			}
		}
		return CountryZipCode;
	}
}
