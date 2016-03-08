package com.equiworx.tutor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.DialogC;
import com.equiworx.model.Parent;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddCreditsActivity extends Activity implements
		AsyncResponseForTutorHelper, OnClickListener, OnItemSelectedListener {
	private EditText fees_exit, remarks;
	private Spinner select_parentId;
	private Button payment_button;
	private SharedPreferences tutorPrefs;
	private String payment, remark, encode_payment, en_remark;
	private String makepayment, parentIdd;
	private ArrayList<NameValuePair> nameValuePairs;
	private TutorHelperDatabaseHandler dbHandler;
	private List<Parent> arraylist_student;
	private RelativeLayout back_layout;
	private String[] stockArr;
	private TutorHelperParser parser;
	private TextView tv_title;
	private ArrayList<DialogC> dialog = new ArrayList<DialogC>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_paymenttutor);
		setUI();
		getParentId();
	}

	private void getParentId() {
		// TODO Auto-generated method stub
		arraylist_student = new ArrayList<Parent>();

		arraylist_student.addAll(dbHandler.getParentsDetails());
		Log.d("", "sizeee" + arraylist_student.size());
		stockArr = new String[arraylist_student.size()];
		// stockArr[0]= "Select Parent";
		for (int j = 0; j < arraylist_student.size(); j++) {
			stockArr[0] = "Select Parent";
			stockArr[j] = arraylist_student.get(j).getName();
		}

		/* Displaying Array elements */
		for (String k : stockArr) {
			System.out.println(k);
		}

		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stockArr);
		adapter_state
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		select_parentId.setAdapter(adapter_state);
		select_parentId.setOnItemSelectedListener(this);
		parentIdd = arraylist_student.get(0).getParentId();

	}

	private void setUI() {
		// TODO Auto-generated method stub
		parser = new TutorHelperParser(AddCreditsActivity.this);
		dbHandler = new TutorHelperDatabaseHandler(AddCreditsActivity.this);
		makepayment = getIntent().getStringExtra("AddCredit");
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		fees_exit = (EditText) findViewById(R.id.fees_edit);
		remarks = (EditText) findViewById(R.id.remarks_edit);
		payment_button = (Button) findViewById(R.id.pay_credits);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		payment_button.setOnClickListener(this);
		select_parentId = (Spinner) findViewById(R.id.osversions);
		tv_title = (TextView) findViewById(R.id.title);

		if (makepayment.equalsIgnoreCase("AddCredit")) {
			payment_button.setText("Credit");
			tv_title.setText("Add Credit");
		} else {
			payment_button.setText("Payments");
			tv_title.setText("Add Payments");
		}

		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Util.hideKeyboard(AddCreditsActivity.this);
				finish();
			}
		});
	}

	private void getTutorDetails(Boolean value) {

		if (Util.isNetworkAvailable(AddCreditsActivity.this)) {
			payment = fees_exit.getText().toString();
			remark = remarks.getText().toString();
			if (payment.equals("") || remark.equals("")
					|| select_parentId.getSelectedItemPosition() == 0) {

				Util.alertMessage(AddCreditsActivity.this,
						"Please fill in required fields.");
			} else {
				try {
					payment = fees_exit.getText().toString();
					remark = remarks.getText().toString();
					en_remark = URLEncoder.encode(remark, "utf-8");
					encode_payment = URLEncoder.encode(payment, "utf-8");
					Log.d("", "encode_payment" + encode_payment);
					Log.d("", "encode_payment" + encode_payment);
					Log.d("", "encode_payment" + encode_payment);
					System.out.print("fkhdsfpayment no" + encode_payment);
					if (makepayment.equalsIgnoreCase("AddCredit")) {
						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("tutor_id",
								tutorPrefs.getString("tutorID", "")));
						nameValuePairs.add(new BasicNameValuePair("parent_id",
								parentIdd));
						nameValuePairs.add(new BasicNameValuePair(
								"payment_mode", "AddCredit"));
						nameValuePairs.add(new BasicNameValuePair("amount",
								encode_payment));
						nameValuePairs.add(new BasicNameValuePair("remarks",
								en_remark));
					} else if (makepayment.equalsIgnoreCase("PayFees")) {

						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("tutor_id",
								tutorPrefs.getString("tutorID", "")));
						nameValuePairs.add(new BasicNameValuePair("parent_id",
								parentIdd));
						nameValuePairs.add(new BasicNameValuePair(
								"payment_mode", "PayFees"));
						nameValuePairs.add(new BasicNameValuePair("amount",
								encode_payment));
						nameValuePairs.add(new BasicNameValuePair("remarks",
								en_remark));
					}

					AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
							AddCreditsActivity.this, "pay-fee", nameValuePairs,
							value, "Please wait...");
					mLogin.delegate = (AsyncResponseForTutorHelper) AddCreditsActivity.this;
					mLogin.execute();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else

		{
			Util.alertMessage(AddCreditsActivity.this,
					"Please check your internet connection");
		}

	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		Log.d("", "xxxxxx output xxxxxxxxx" + output);
		if (methodName.equals("pay-fee")) {
			dialog = parser.getAddCreditResults(output);

			if (dialog.get(0).getSuccess().equalsIgnoreCase("success")) {
			
				finish();

			} else {
				Toast.makeText(AddCreditsActivity.this,
						"Credits is not successfull", Toast.LENGTH_LONG).show();

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pay_credits:
			Boolean Value = true;
			getTutorDetails(Value);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		parentIdd = arraylist_student.get(arg2).getParentId();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
