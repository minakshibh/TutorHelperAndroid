package com.equiworx.student;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Parent;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class AddStudent extends Activity implements AsyncResponseForTutorHelper {

	private SharedPreferences tutorPrefs;
	private TextView parentName, parentEmail, parentContact, parentAddress,
			addParent, sameAsParent;
	private EditText name, email, contactInfo, fees, existingParentId, notes;
	private Spinner parentSpinner;
	private Button search, add;
	private ArrayList<Parent> parentList;
	private TutorHelperParser parser;
	private LinearLayout parentForm, parentSelection;
	private RadioButton male, female;
	private TextView tv_title, txt_parentid;
	private ImageView back;
	private LinearLayout lay_fee;
	public static int idcheck = 0;
	private String trigger = "tutor";
	private String parentId = "0", tutorID, parentGender,
			studentGender = "Male";
	private int int_check = 0;
	private LinearLayout lay_parentid;
	private RelativeLayout back_layout;
	private String zipcode = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_student);

		zipcode = "+" + GetCountryZipCode();
		initializeLayout();
		setClickListeners();

		System.err.println("zipcode=" + zipcode);
	}

	private void initializeLayout() {
		// TODO Auto-generated method stub
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title = (TextView) findViewById(R.id.title);
		tv_title.setText("Add Student");
		back = (ImageView) findViewById(R.id.back);
		male = (RadioButton) findViewById(R.id.male);
		female = (RadioButton) findViewById(R.id.female);
		parentName = (TextView) findViewById(R.id.parentName);
		parentEmail = (TextView) findViewById(R.id.parentEmail);
		parentContact = (TextView) findViewById(R.id.parentContact);
		parentAddress = (TextView) findViewById(R.id.parentAddress);
		addParent = (TextView) findViewById(R.id.addParent);
		sameAsParent = (TextView) findViewById(R.id.sameAsParent);
		txt_parentid = (TextView) findViewById(R.id.parentid);
		notes = (EditText) findViewById(R.id.notes);
		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		contactInfo = (EditText) findViewById(R.id.contactInfo);
		contactInfo.setText(zipcode);
		fees = (EditText) findViewById(R.id.fees);
		existingParentId = (EditText) findViewById(R.id.existingParentID);
		lay_parentid = (LinearLayout) findViewById(R.id.lay_parentid);
		parentSpinner = (Spinner) findViewById(R.id.parentSpinner);
		lay_fee = (LinearLayout) findViewById(R.id.lay_fees);
		search = (Button) findViewById(R.id.search);
		add = (Button) findViewById(R.id.add);

		parentForm = (LinearLayout) findViewById(R.id.parentForm);
		parentSelection = (LinearLayout) findViewById(R.id.parentSelection);

		parentList = new ArrayList<Parent>();
		parser = new TutorHelperParser(AddStudent.this);

		trigger = getIntent().getStringExtra("trigger");
		if (trigger == null)
			trigger = "tutor";

		if (trigger.equals("tutor")) {
			tutorID = tutorPrefs.getString("tutorID", "");
			// parentId = tutorPrefs.getString("newparentID", "0");
			getEsixtingParents();
		} else {
			parentForm.setVisibility(View.GONE);
			parentSelection.setVisibility(View.GONE);
			sameAsParent.setVisibility(View.GONE);
			parentId = getIntent().getStringExtra("parentID");
			lay_fee.setVisibility(View.GONE);
		}

		
		back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void getEsixtingParents() {
		if (Util.isNetworkAvailable(AddStudent.this)) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorID));

			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					AddStudent.this, "fetch-parent", nameValuePairs, true,
					"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) AddStudent.this;
			mLogin.execute();
		} else {
			Util.alertMessage(AddStudent.this,
					"Please check your internet connection");
		}
	}

	private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (buttonView == male) {
				if (isChecked) {
					studentGender = "Male";
				} else
					studentGender = "Female";
			} else if (buttonView == female) {
				if (isChecked) {
					studentGender = "Female";
				} else
					studentGender = "Male";
			}
		}
	};

	private void setClickListeners() {
		addParent.setOnClickListener(listener);
		search.setOnClickListener(listener);
		add.setOnClickListener(listener);
		sameAsParent.setOnClickListener(listener);
		male.setOnCheckedChangeListener(checkListener);
		female.setOnCheckedChangeListener(checkListener);

		parentSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						Util.hideKeyboard(AddStudent.this);

						Parent parent = (Parent) parentSpinner
								.getSelectedItem();

						if (!parent.getParentId().equals("0")) {
							parentId = parent.getParentId();
							existingParentId.setText("");
							lay_parentid.setVisibility(View.VISIBLE);
							parentName.setText("" + parent.getName());
							txt_parentid.setText(": " + parent.getParentId());
							parentEmail.setText(": " + parent.getEmail());
							parentContact.setText(": "
									+ parent.getContactNumber());
							parentAddress.setText(": " + parent.getAddress());
							try {
								if (parent.getGender().equalsIgnoreCase("Male")) {
									male.setChecked(true);
									parentGender = "Male";
								} else {
									female.setChecked(true);
									parentGender = "Female";
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.err.println("spinnerrr  yeeeeeeeeeees");
						}
						if (int_check == 0) {
							if (position == 0) {
								parentId = "0";
								parentAddress.setText("");
								parentName.setText("Parent name");
								txt_parentid.setText("");
								parentEmail.setText("");
								parentContact.setText("");
								System.err.println("spinnerrrnooooooooo");
								lay_parentid.setVisibility(View.VISIBLE);
							}
						}

						int_check = 0;

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});
		existingParentId
				.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView arg0, int actionid,
							KeyEvent arg2) {
						if (actionid == EditorInfo.IME_ACTION_SEARCH) {

							if (existingParentId.getText().toString().trim()
									.equals(""))
								Util.alertMessage(AddStudent.this,
										"Please fill in Parent ID");
							else {
								Util.hideKeyboard(AddStudent.this);
								if (Util.isNetworkAvailable(AddStudent.this)) {
									ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
									nameValuePairs.add(new BasicNameValuePair(
											"parent_id", existingParentId
													.getText().toString()
													.trim()));

									AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
											AddStudent.this, "parent-info",
											nameValuePairs, true,
											"Please wait...");
									mLogin.delegate = (AsyncResponseForTutorHelper) AddStudent.this;
									mLogin.execute();
								} else {
									Util.alertMessage(AddStudent.this,
											"Please check your internet connection");
								}

								return true;
							}
							return false;
						}
						return false;
					}
				});
	}

	private View.OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			if (v == addParent) {
				Intent intent = new Intent(AddStudent.this, AddParent.class);
				startActivity(intent);
			} else if (v == search) {
				if (existingParentId.getText().toString().trim().equals(""))
					Util.alertMessage(AddStudent.this,
							"Please fill in Parent ID");
				else {
					Util.hideKeyboard(AddStudent.this);
					if (Util.isNetworkAvailable(AddStudent.this)) {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("parent_id",
								existingParentId.getText().toString().trim()));

						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								AddStudent.this, "parent-info", nameValuePairs,
								true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) AddStudent.this;
						mLogin.execute();
					} else {
						Util.alertMessage(AddStudent.this,
								"Please check your internet connection");
					}
				}
			} else if (v == add) {
				String check = "";
				if (trigger.equalsIgnoreCase("tutor")) {
					check = emptyFieldCheckTutor();
				} else {
					check = emptyFieldCheckParent();
				}
				if (check.equals("success")) {
					if (Util.isNetworkAvailable(AddStudent.this)) {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("name", name
								.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("email",
								email.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("parent_id",
								parentId));
						nameValuePairs.add(new BasicNameValuePair("phone",
								contactInfo.getText().toString().trim()));

						if (trigger.equals("tutor")) {
							nameValuePairs.add(new BasicNameValuePair(
									"parent_name", parentName.getText()
											.toString().trim()));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_email", parentEmail.getText()
											.toString().replace(": ", "")));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_contact", parentContact.getText()
											.toString().replace(": ", "")));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_address", parentAddress.getText()
											.toString().replace(": ", "")));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_gender", parentGender));
							nameValuePairs.add(new BasicNameValuePair(
									"address", parentAddress.getText()
											.toString().replace(": ", "")));
							nameValuePairs.add(new BasicNameValuePair(
									"creator", "Tutor"));
							nameValuePairs.add(new BasicNameValuePair(
									"creator_id", tutorID));

						} else {
							nameValuePairs.add(new BasicNameValuePair(
									"parent_name", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_email", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_contact", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_address", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"parent_gender", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"address", ""));
							nameValuePairs.add(new BasicNameValuePair(
									"creator", "Parent"));
							nameValuePairs.add(new BasicNameValuePair(
									"creator_id", parentId));

						}
						nameValuePairs.add(new BasicNameValuePair(
								"alternate_phone", ""));
						nameValuePairs.add(new BasicNameValuePair("trigger",
								"add"));
						nameValuePairs.add(new BasicNameValuePair("gender",
								studentGender));
						nameValuePairs.add(new BasicNameValuePair("fee", fees
								.getText().toString().trim()));
						nameValuePairs.add(new BasicNameValuePair("notes",
								notes.getText().toString()));

						Log.e("add stu", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								AddStudent.this, "student-register",
								nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) AddStudent.this;
						mLogin.execute();
					} else {
						Util.alertMessage(AddStudent.this,
								"Please check your internet connection");
					}
				} else
					Util.alertMessage(AddStudent.this, check);
			} else if (v == sameAsParent) {
				email.setText(parentEmail.getText().toString()
						.replace(": ", ""));
				contactInfo.setText(parentContact.getText().toString()
						.replace(": ", ""));
			}
		}
	};

	@Override
	public void processFinish(String output, String methodName) {
		// Util.alertMessage(AddStudent.this, output);
		if (methodName.equals("parent-info")) {
			Parent parent = parser.getParentInfo(output);

			if (parent != null) {
				int_check = 1;
				lay_parentid.setVisibility(View.VISIBLE);
				parentId = parent.getParentId();
				txt_parentid.setText(": " + parentId);
				parentName.setText("" + parent.getName());
				parentEmail.setText(": " + parent.getEmail());
				parentContact.setText(": " + parent.getContactNumber());
				parentAddress.setText(": " + parent.getAddress());
				try {
					String gendar = parent.getGender();
					if (gendar.equalsIgnoreCase("male")) {
						male.setChecked(true);
						parentGender = "male";
					} else {
						female.setChecked(true);
						parentGender = "female";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				ArrayAdapter<Parent> spinnerArrayAdapter = new ArrayAdapter<Parent>(
						this, android.R.layout.simple_spinner_item, parentList);
				spinnerArrayAdapter
						.setDropDownViewResource(R.layout.spinner_dropdown);
				// Step 3: Tell the spinner about our adapter
				System.err.println("sizeeeeee===" + parentList.size());

				parentSpinner.setAdapter(spinnerArrayAdapter);

			}
		} else if (methodName.equals("fetch-parent")) {
			parentList = parser.getParents(output);

			ArrayAdapter<Parent> spinnerArrayAdapter = new ArrayAdapter<Parent>(
					this, android.R.layout.simple_spinner_item, parentList);
			spinnerArrayAdapter
					.setDropDownViewResource(R.layout.spinner_dropdown);
			// Step 3: Tell the spinner about our adapter
			parentSpinner.setAdapter(spinnerArrayAdapter);
		} else if (methodName.equals("student-register")) {
			try {
				JSONObject jsonChildNode = new JSONObject(output);

				String result = jsonChildNode.getString("result").toString();
				String message = jsonChildNode.getString("message").toString();
				if (result.equals("0")) {
					idcheck = 0;
					AlertDialog.Builder alert = new AlertDialog.Builder(
							AddStudent.this);
					alert.setTitle("Congrats!!!");
					alert.setMessage("Your request has been sent. We will notify you as soon as the Parent will approve the same.");
					alert.setCancelable(false);
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									finish();
								}
							});
					alert.show();
				} else
					Util.alertMessage(AddStudent.this, message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected String emptyFieldCheckTutor() {
		String gettingEmail = email.getText().toString().trim();
		if (parentId.equals("0")) {
			return "Please select any parent";
		} else if (name.getText().toString().trim().equals("")) {
			return "Please enter student name";
		} else if (email.getText().toString().trim().equals("")) {
			return "Please enter student email";
		} else if (contactInfo.getText().toString().trim().equals("")) {
			return "Please enter student's contact info";
		} else if (!Util.isValidPhoneNumber(contactInfo.getText().toString())) {
			return "Please enter valid phone number";
		}
	
		else if (fees.getText().toString().trim().equals("")) {
			return "Please enter fee details";
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(gettingEmail)
				.matches() && !TextUtils.isEmpty(gettingEmail)) {
			return "Please enter a valid email address.";

		} else {
			return "success";
		}
	}

	protected String emptyFieldCheckParent() {
		String gettingEmail = email.getText().toString().trim();
		if (parentId.equals("0")) {
			return "Please select any parent";
		} else if (name.getText().toString().trim().equals("")) {
			return "Please enter student name";
		} else if (email.getText().toString().trim().equals("")) {
			return "Please enter student email";
		} else if (contactInfo.getText().toString().trim().equals("")) {
			return "Please enter student's contact info";
		} else if (!Util.isValidPhoneNumber(contactInfo.getText().toString())) {
			return "Please enter valid phone number";
		}
		
		else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(gettingEmail)
				.matches() && !TextUtils.isEmpty(gettingEmail)) {
			return "Please enter a valid email address.";

		} else {
			return "success";
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (idcheck == 1) {
			parentId = tutorPrefs.getString("newparentID", "0");
			// txt_parentid.setText(parentId);
			parentName.setText(" "
					+ tutorPrefs.getString("name", "Parent name"));
			parentEmail.setText(": " + tutorPrefs.getString("email", ""));
			parentContact.setText(": " + tutorPrefs.getString("contact", ""));
			parentAddress.setText(": " + tutorPrefs.getString("address", ""));
			parentGender = tutorPrefs.getString("gender", "male");
			lay_parentid.setVisibility(View.GONE);
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