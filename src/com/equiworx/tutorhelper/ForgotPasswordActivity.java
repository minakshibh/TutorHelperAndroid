package com.equiworx.tutorhelper;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.util.Util;

public class ForgotPasswordActivity extends Activity implements AsyncResponseForTutorHelper{
	
	private Button recoverPassowrd;
	private EditText userId;
	private String trigger;
	private ImageView imgview_back;
	private TextView tv_title;
	private RelativeLayout back_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_forgotpassword);
	
		initializeLayout();
		setClickListeners();
	}

	private void initializeLayout() {
		tv_title=(TextView)findViewById(R.id.title);
		tv_title.setText("Recover Password");
		imgview_back=(ImageView)findViewById(R.id.back);
		recoverPassowrd = (Button)findViewById(R.id.recover);
		userId = (EditText)findViewById(R.id.userId);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		trigger = getIntent().getStringExtra("trigger");
	}

	private void setClickListeners() {
		// TODO Auto-generated method stub
		recoverPassowrd.setOnClickListener(listener);
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

	private Boolean emptyFieldCheck(){
		if(userId.getText().toString().trim().equals("")){
			return false;
		}else
			return true;
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v == recoverPassowrd){
				if(emptyFieldCheck()){
					if (Util.isNetworkAvailable(ForgotPasswordActivity.this)){
						Util.hideKeyboard(ForgotPasswordActivity.this);
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("fp_e_u", userId.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("trigger", trigger));
						
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(ForgotPasswordActivity.this, "forgot-pass", nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) ForgotPasswordActivity.this;
						mLogin.execute();
						
										
					}else {
						Util.alertMessage(ForgotPasswordActivity.this,
								"Please check your internet connection");
					}
				}else
					Util.alertMessage(ForgotPasswordActivity.this, "Please enter the required fields");
			}
		}
	};

	@Override
	public void processFinish(String output, String methodName) {
//		Util.alertMessage(ForgotPasswordActivity.this, output);
		//TutorHelperParser parser = new TutorHelperParser(ForgotPasswordActivity.this);
		String result = null;//= parser.parseResponse(output);
		
		try {

			JSONObject jsonChildNode = new JSONObject(output);	
			
			result = jsonChildNode.getString("result").toString();
								
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result.equals("0"))
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(ForgotPasswordActivity.this);
			alert.setTitle("Congrats!!!");
			alert.setMessage("Your password has been sent to you via email.");
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alert.show();
		}
		else
		{
			Util.alertMessage(ForgotPasswordActivity.this, "Invalid email address");
			}
	}
}
