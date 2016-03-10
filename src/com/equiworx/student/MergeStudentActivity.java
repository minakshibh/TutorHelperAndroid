package com.equiworx.student;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.StudentList;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MergeStudentActivity extends Activity implements
		AsyncResponseForTutorHelper {

	private EditText first_edittext, sec_edittext;
	private RelativeLayout back;
	private Button merge_btn;

	private String first_id, second_id;
	private String first_check = "no", second_check = "no";
	private ArrayList<StudentList> arraylist_student = new ArrayList<StudentList>();
	private SharedPreferences tutorPrefs;
	private TutorHelperDatabaseHandler dbHandler;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_student_merge);
		setUI();
		implementation();
	}

	private void setUI() {
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		dbHandler = new TutorHelperDatabaseHandler(MergeStudentActivity.this);
		first_edittext = (EditText) findViewById(R.id.first_edittext);
		sec_edittext = (EditText) findViewById(R.id.sec_edittext);
		merge_btn = (Button) findViewById(R.id.merge_button);
		back = (RelativeLayout) findViewById(R.id.back_layout);
		title = (TextView) findViewById(R.id.title);
		title.setText("Merge Students");
	}

	private void implementation() {
		merge_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Util.hideKeyboard(MergeStudentActivity.this);
				arraylist_student = new ArrayList<StudentList>();
				arraylist_student.addAll(dbHandler.getStudentAll("all", ""));
				System.err.println("arraylist_student size="
						+ arraylist_student.size());
				first_id = first_edittext.getText().toString().trim();
				second_id = sec_edittext.getText().toString().trim();
				if (first_id.length() > 0) {
					if (second_id.length() > 0) {

						first_check = "no";
						second_check = "no";

						try {

							for (int i = 0; i < arraylist_student.size(); i++) {

								String studentid = arraylist_student.get(i)
										.getStudentId();
								Log.d("", "studentidstudentid=" + studentid);

								Log.d("", "first_idfirst_id" + first_id);
								if (first_id.equalsIgnoreCase(studentid)) {
									Log.d("", "first 's are existedddd111");
									first_check = "yes";
									break;
								} else {
									first_check = "no";
								}

							}

							for (int i = 0; i < arraylist_student.size(); i++) {

								String studentid = arraylist_student.get(i)
										.getStudentId();
								Log.d("", "studentidstudentid=" + studentid);

								Log.d("", "second_id" + second_id);
								if (second_id.equalsIgnoreCase(studentid)) {
									Log.d("", "second's are existedddd111");
									second_check = "yes";
									break;
								} else {
									second_check = "no";
								}

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if ((first_check.equalsIgnoreCase("yes"))) {
							if (second_check.equalsIgnoreCase("yes")) {

								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										MergeStudentActivity.this);
								alertDialogBuilder.setMessage("Are you sure, you want to merge Students ..?");
								alertDialogBuilder.setCancelable(false);
								alertDialogBuilder.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface arg0,
													int arg1) {

												mergeStudent();
											}
										});
								alertDialogBuilder.setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int which) {

											}
										});

								AlertDialog alertDialog = alertDialogBuilder
										.create();
								alertDialog.show();
							} else {
								Util.alertMessage(MergeStudentActivity.this,
										"Please enter valid Ids");
								;
							}
						} else {
							Util.alertMessage(MergeStudentActivity.this,
									"Please enter valid Ids");
							;
						}

					}

					else {
						Util.alertMessage(MergeStudentActivity.this,
								"Enter second student id");
						;
					}
				} else {
					Util.alertMessage(MergeStudentActivity.this,
							"Enter first student id");
				}

			}
		});
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});
	}

	private void mergeStudent() {
		if (Util.isNetworkAvailable(MergeStudentActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("first_id", first_id));
			nameValuePairs.add(new BasicNameValuePair("second_id", second_id));
			nameValuePairs.add(new BasicNameValuePair("trigger", "Student"));

			Log.e("merge-parent", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MergeStudentActivity.this, "merge-parent", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MergeStudentActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(MergeStudentActivity.this,
					"Please check your internet connection");
		}

	}

	private void fetchStudentList() {
		if (Util.isNetworkAvailable(MergeStudentActivity.this)) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("parent_id", tutorPrefs
					.getString("parentID", "")));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", "-1"));

			Log.e("student list", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MergeStudentActivity.this, "fetch-student", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MergeStudentActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(MergeStudentActivity.this,
					"Please check your internet connection");
		}
	}

	@Override
	public void processFinish(String output, String methodName) {
		TutorHelperParser parser = new TutorHelperParser(
				MergeStudentActivity.this);
		if (methodName.equals("merge-parent")) {
			fetchStudentList();
		} else if (methodName.equals("fetch-student")) {
			arraylist_student = parser.getStudentlistwithoutNote(output);
			dbHandler.deleteStudentDetails();
			dbHandler.updateStudentDetails(arraylist_student);
			Util.alertMessage(MergeStudentActivity.this, "Merge successful");
			finish();

		}

	}
}
