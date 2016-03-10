package com.equiworx.parent;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Parent;
import com.equiworx.model.Tutor;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyProfileActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private SharedPreferences tutorPrefs;
	private EditText txt_name, txt_email, txt_password, txt_confirmPassword,
			txt_contactInfo, txt_altContactInfo, txt_address;
	private RadioButton male, female;
	private Tutor tutor;
	private Parent parent;
	private Button done;
	private RelativeLayout back;
	private TextView tv_edit;
	private LinearLayout ley_confirmPassword, lay_altContactInfo;
	private TextView textView_title;
	private String result = "", jsonmessge = "";
	private String gender = "", methodname = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprofile);

		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		setUI();
		disableAll();
		setOnClick();

		if (tutorPrefs.getString("mode", "").equalsIgnoreCase("parent")) {
			getParentDetail();
			lay_altContactInfo.setVisibility(View.GONE);

		} else {
			getTutorDetails();
			lay_altContactInfo.setVisibility(View.VISIBLE);
		}
	}

	private void setOnClick() {

		male.setOnClickListener(listener);
		female.setOnClickListener(listener);
		done.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String result = emptyFieldCheck();
				if (result.equals("success")) {

					if (Util.isNetworkAvailable(MyProfileActivity.this)) {
						Util.hideKeyboard(MyProfileActivity.this);
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

						if (tutorPrefs.getString("mode", "").equals("parent")) {
							nameValuePairs.add(new BasicNameValuePair("name",
									txt_name.getText().toString().trim()));
							nameValuePairs.add(new BasicNameValuePair("email",
									txt_email.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("pass",
									txt_password.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"contact_number", txt_contactInfo.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"alt_contact_number", txt_altContactInfo
											.getText().toString()));
							nameValuePairs
									.add(new BasicNameValuePair("address",
											txt_address.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair("gender",
									gender));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_id", tutorPrefs.getString(
											"parentID", "")));
							methodname = "edit-profile";
						} else {
							nameValuePairs.add(new BasicNameValuePair("name",
									txt_name.getText().toString().trim()));
							nameValuePairs.add(new BasicNameValuePair("email",
									txt_email.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"password", txt_password.getText()
											.toString()));
							nameValuePairs.add(new BasicNameValuePair("phone",
									txt_contactInfo.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"alternate_phone", txt_altContactInfo
											.getText().toString()));
							nameValuePairs
									.add(new BasicNameValuePair("address",
											txt_address.getText().toString()));
							nameValuePairs.add(new BasicNameValuePair(
									"trigger", "edit"));
							nameValuePairs.add(new BasicNameValuePair("gender",
									gender));
							nameValuePairs.add(new BasicNameValuePair(
									"tutor_id", tutorPrefs.getString("tutorID",
											"")));
							methodname = "register";
						}

						Log.e("edit register", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								MyProfileActivity.this, methodname,
								nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) MyProfileActivity.this;
						mLogin.execute();
					} else {
						Util.alertMessage(MyProfileActivity.this,
								"Please check your internet connection");
					}
				} else {
					Util.alertMessage(MyProfileActivity.this, result);
				}

			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		tv_edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.err.println("edit");
				enableAll();
				tv_edit.setVisibility(View.GONE);
			}
		});

	}

	private void enableAll() {

		txt_name.setEnabled(true);
		txt_email.setEnabled(true);
		txt_password.setEnabled(true);
		ley_confirmPassword.setVisibility(View.GONE);
		txt_contactInfo.setEnabled(true);
		txt_altContactInfo.setEnabled(true);
		txt_address.setEnabled(true);
		male.setEnabled(true);
		female.setEnabled(true);
		done.setVisibility(View.VISIBLE);
	}

	private void disableAll() {
		txt_name.setEnabled(false);
		txt_email.setEnabled(false);
		txt_password.setEnabled(false);
		ley_confirmPassword.setVisibility(View.GONE);
		txt_contactInfo.setEnabled(false);
		txt_altContactInfo.setEnabled(false);
		txt_address.setEnabled(false);
		male.setEnabled(false);
		female.setEnabled(false);
		done.setVisibility(View.GONE);

	}

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == male) {
				male.setChecked(true);
				female.setChecked(false);
				gender = "Male";
			} else if (v == female) {
				female.setChecked(true);
				male.setChecked(false);
				gender = "Female";
			}
		}
	};

	private String emptyFieldCheck() {
		String gettingEmail = txt_email.getText().toString();
		if (txt_name.getText().toString().trim().equals("")
				|| txt_email.getText().toString().trim().equals("")
				|| txt_password.getText().toString().trim().equals("")
				|| txt_contactInfo.getText().toString().trim().equals("")
				|| txt_address.getText().toString().trim().equals("")) {
			return "Please fill in required fields.";
		}/*
		 * else
		 * if(!txt_password.getText().toString().trim().equals(txt_confirmPassword
		 * .getText().toString().trim())){ return "Passwords do not match";
		 */
		// }
		else if (txt_contactInfo.getText().toString().length() < 10) {
			return "Please enter valid contact info";
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(gettingEmail)
				.matches() && !TextUtils.isEmpty(gettingEmail)) {
			return "Please enter a valid email.";
		} else
			return "success";
	}

	private void getParentDetail() {
		if (Util.isNetworkAvailable(MyProfileActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("username", tutorPrefs
					.getString("parentID", "")));
			nameValuePairs.add(new BasicNameValuePair("password", tutorPrefs
					.getString("parentpass", "")));

			Log.e("parent login", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MyProfileActivity.this, "parent-login", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyProfileActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(MyProfileActivity.this,
					"Please check your internet connection");
		}
	}

	private void getTutorDetails() {
		if (Util.isNetworkAvailable(MyProfileActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_pin", tutorPrefs
					.getString("tutorID", "")));
			nameValuePairs.add(new BasicNameValuePair("password", tutorPrefs
					.getString("tutorpass", "")));

			Log.e("tutor login", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MyProfileActivity.this, "login", nameValuePairs, true,
					"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyProfileActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(MyProfileActivity.this,
					"Please check your internet connection");
		}

	}

	private void setUI() {
		// TODO Auto-generated method stub
		tutor = new Tutor();
		parent = new Parent();
		ley_confirmPassword = (LinearLayout) findViewById(R.id.ley_confirmPassword);
		lay_altContactInfo = (LinearLayout) findViewById(R.id.lay_altContactInfo);
		txt_name = (EditText) findViewById(R.id.name);
		txt_email = (EditText) findViewById(R.id.email);
		tv_edit = (TextView) findViewById(R.id.edit);
		back = (RelativeLayout) findViewById(R.id.back_layout);
		txt_password = (EditText) findViewById(R.id.password);
		txt_confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		txt_contactInfo = (EditText) findViewById(R.id.contactInfo);
		txt_altContactInfo = (EditText) findViewById(R.id.altContactInfo);
		txt_address = (EditText) findViewById(R.id.address);
		male = (RadioButton) findViewById(R.id.male);
		female = (RadioButton) findViewById(R.id.female);
		done = (Button) findViewById(R.id.done);
		ley_confirmPassword.setVisibility(View.GONE);
		textView_title=(TextView)findViewById(R.id.textView_title);
		textView_title.setText(" My Profile");

	}

	private void setParent() {
		txt_name.setText(parent.getName());
		txt_email.setText(parent.getEmail());
		txt_password.setText(tutorPrefs.getString("parentpass", ""));
		txt_confirmPassword.setText(tutorPrefs.getString("parentpass", ""));
		txt_contactInfo.setText(parent.getContactNumber());

		txt_altContactInfo.setText(parent.getAltContactNumber());
		txt_address.setText(parent.getAddress());
		gender = parent.getGender();
		try {
			if (gender.equalsIgnoreCase("Male")) {
				male.setChecked(true);
				gender = "Male";
			} else if (gender.equalsIgnoreCase("Female")) {
				female.setChecked(true);
				gender = "Female";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setTutor() {

		txt_name.setText(tutor.getName());
		txt_email.setText(tutor.getEmail());
		txt_password.setText(tutorPrefs.getString("tutorpass", ""));
		txt_confirmPassword.setText(tutorPrefs.getString("tutorpass", ""));
		txt_contactInfo.setText(tutor.getContactNumber());

		txt_altContactInfo.setText(tutor.getAltContactNumber());
		txt_address.setText(tutor.getAddress());
		gender = tutor.getGender();
		try {
			if (gender.equalsIgnoreCase("Male")) {
				male.setChecked(true);
				gender = "Male";
			} else if (gender.equalsIgnoreCase("Female")) {
				female.setChecked(true);
				gender = "Female";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void processFinish(String output, String methodName) {
		TutorHelperParser parser = new TutorHelperParser(MyProfileActivity.this);
		if (methodName.equalsIgnoreCase("login"))// tutor login
		{
			tutor = parser.parseTutorDetails(output);
			setTutor();
		} else if (methodName.equalsIgnoreCase("parent-login"))// parent login
		{
			parent = parser.parseParentDetails(output);
			setParent();
		} else if (methodName.equalsIgnoreCase("edit-profile"))// edit profile
																// parent
		{
			parent = parser.parseParentEdit(output);
			try {
				JSONObject jsonChildNode = new JSONObject(output);
				result = jsonChildNode.getString("result");
				jsonmessge = jsonChildNode.getString("message");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result.equals("0")) {

				Editor ed = tutorPrefs.edit();
				ed.putString("parentpass", txt_password.getText().toString());
				ed.commit();
				System.err.println("parentpss="
						+ txt_password.getText().toString());

				setParent();
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MyProfileActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("Profile update successfully");
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								finish();
							}
						});
				alert.show();
			} else {
				Util.alertMessage(MyProfileActivity.this, jsonmessge);
			}

		} else if (methodName.equalsIgnoreCase("register"))// edit profile tutor
		{
			tutor = parser.parseTutorDetails(output);
			try {
				JSONObject jsonChildNode = new JSONObject(output);
				result = jsonChildNode.getString("result");
				jsonmessge = jsonChildNode.getString("message");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result.equals("0")) {

				Editor ed = tutorPrefs.edit();
				ed.putString("tutorpass", txt_password.getText().toString());
				ed.commit();

				setTutor();
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MyProfileActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("Profile update successfully");
				alert.setCancelable(false);
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								finish();
							}
						});
				alert.show();
			} else {
				Util.alertMessage(MyProfileActivity.this, jsonmessge);
			}

		}
	}

}
