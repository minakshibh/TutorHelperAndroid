package com.equiworx.tutor;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.StudentList;
import com.equiworx.student.AddStudent;
import com.equiworx.student.EditStudentActivity;
import com.equiworx.student.StudentDetailActivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class ParentDetailActivity extends Activity implements
		AsyncResponseForTutorHelper {

	private SharedPreferences tutorPrefs;
	private ImageView add;
	private TextView txt_contactno, txt_email, txt_noofstudent, txt_balance,
			txt_address, txt_parentid;
	private ArrayList<StudentList> arraylist_student = new ArrayList<StudentList>();
	private StudentList studentList;
	private StudentAdapter adapter;
	private ListView listView;
	private String parentId, tutorId;
	private EditText txtNotes;
	private TextView tv_title;
	private TutorHelperParser parser;
	private LinearLayout layout_history;
	private Button button_update;
	private RelativeLayout back_layout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parentdetails);

		initializelayout();
		fetchStudentList();
		onclickListenser();
	}

	private void onclickListenser() {
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});
		layout_history.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParentDetailActivity.this,MakePayment_SelectParentActivity.class);
				intent.putExtra("parentId",tutorPrefs.getString("p_parentId", "0"));
				startActivity(intent);
			}
		});
		txt_contactno.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String uri = "tel:" + txt_contactno.getText().toString().trim();
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse(uri));
				startActivity(intent);
			}
		});
		txt_email.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", txt_email.getText().toString()
								.trim(), null));
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ParentDetailActivity.this,
						AddStudent.class);
				intent.putExtra("detailadd", "value");
				/*
				 * intent.putExtra("parentID",
				 * tutorPrefs.getString("p_parentId", "0"));
				 * intent.putExtra("pname", tutorPrefs.getString("p_name",
				 * "0")); intent.putExtra("pemail",
				 * tutorPrefs.getString("p_email", "0"));
				 * intent.putExtra("pcontact",
				 * tutorPrefs.getString("p_contactno", "0"));
				 * intent.putExtra("paddress", tutorPrefs.getString("p_address",
				 * "0")); intent.putExtra("pgender",
				 * tutorPrefs.getString("p_gender", "0"));
				 */
				startActivity(intent);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				studentList = arraylist_student.get(position);
				Intent intent = new Intent(ParentDetailActivity.this,
						StudentDetailActivity.class);
				intent.putExtra("name", studentList.getName());
				intent.putExtra("email", studentList.getEmail());
				intent.putExtra("address", studentList.getAddress());
				intent.putExtra("phone", studentList.getContactInfo());
				intent.putExtra("fees", studentList.getFees());
				intent.putExtra("notes", studentList.getNotes());
				intent.putExtra("gender", studentList.getGender());
				intent.putExtra("parentid", tutorPrefs.getString("p_name", null));
				intent.putExtra("studentid", studentList.getStudentId());
				intent.putExtra("isActiveInMonth", studentList.getIsActiveInMonth());
				startActivity(intent);

			}
		});
		txtNotes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtNotes.setCursorVisible(true);
			}
		});
		button_update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (txtNotes.getText().toString().equals("")) {
					Util.alertMessage(ParentDetailActivity.this,
							"Please enter notes");
				} else {
					if (Util.isNetworkAvailable(ParentDetailActivity.this)) {

						txtNotes.setCursorVisible(false);
						Util.hideKeyboard(ParentDetailActivity.this);
						
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("parent_id",
								tutorPrefs.getString("p_parentId", "")));
						nameValuePairs.add(new BasicNameValuePair("tutor_id",
								tutorPrefs.getString("tutorID", "")));
						nameValuePairs.add(new BasicNameValuePair("notes",
								txtNotes.getText().toString()));
						nameValuePairs.add(new BasicNameValuePair("sender",
								"Tutor"));
						Log.e("students list", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								ParentDetailActivity.this,
								"insert-parents-notes", nameValuePairs, true,
								"Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) ParentDetailActivity.this;
						mLogin.execute();
					} else {
						Util.alertMessage(ParentDetailActivity.this,
								"Please check your internet connection");
					}
				}
			}
		});
	}

	private void fetchStudentList() {
		if (Util.isNetworkAvailable(ParentDetailActivity.this)) {
			/*
			 * ParentId/TutorId Trigger -- Parent/Tutor
			 */// tutor_helper/fetch-parent.php//
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("parent_id", parentId));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorId));
			Log.e("students list", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					ParentDetailActivity.this, "fetch-student", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) ParentDetailActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(ParentDetailActivity.this,
					"Please check your internet connection");
		}

	}

	private void initializelayout() {

		studentList = new StudentList();
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title = (TextView) findViewById(R.id.title);

		tv_title.setText(tutorPrefs.getString("p_name", "0"));
		parser = new TutorHelperParser(ParentDetailActivity.this);
		listView = (ListView) findViewById(R.id.listView_student);
		txt_contactno = (TextView) findViewById(R.id.contactno);
		txt_email = (TextView) findViewById(R.id.email);
		txt_noofstudent = (TextView) findViewById(R.id.studentno);
		txt_balance = (TextView) findViewById(R.id.balance);
		txt_address = (TextView) findViewById(R.id.Address);
		txt_parentid = (TextView) findViewById(R.id.parentid);
		txtNotes = (EditText) findViewById(R.id.editText_notes);
		layout_history = (LinearLayout) findViewById(R.id.layout_history);
		button_update = (Button) findViewById(R.id.button_update);
		add = (ImageView) findViewById(R.id.add);
		String str = "" + tutorPrefs.getString("p_contactno", "0");
		SpannableString content = new SpannableString(str);
		content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
		txt_contactno.setText(content);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);

		String str_email = "" + tutorPrefs.getString("p_email", "0");
		SpannableString content1 = new SpannableString(str_email);
		content1.setSpan(new UnderlineSpan(), 0, str_email.length(), 0);
		txt_email.setText(content1);

		txt_noofstudent.setText(": "
				+ tutorPrefs.getString("p_noofstudent", "0"));
		txt_balance.setText(":$" + tutorPrefs.getString("p_balance", "0"));
		txt_address.setText(":" + tutorPrefs.getString("p_address", "0"));
		parentId = tutorPrefs.getString("p_parentId", "0");
		tutorId = tutorPrefs.getString("tutorID", "0");
		txt_parentid.setText("Parent Id : " + parentId);

		try{
			if (tutorPrefs.getString("p_notes", "null").equalsIgnoreCase("null")) {
				txtNotes.setText("");
			}
			else{
				txtNotes.setText(tutorPrefs.getString("p_notes", ""));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public class StudentAdapter extends BaseAdapter {
		private Context context;
		private TextView tv_name, tv_email, tv_note, tv_contactno;
		private ImageView img_edit;
		private StudentList studentlist;

		public StudentAdapter(Context ctx) {
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist_student.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist_student.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.studentlist_row,
						parent, false);
			}

			studentlist = arraylist_student.get(position);
			TextView lbl_notes = (TextView) convertView
					.findViewById(R.id.lbl_notes);
			lbl_notes.setVisibility(View.VISIBLE);
			TextView lbl_address = (TextView) convertView
					.findViewById(R.id.lbl_address);
			lbl_address.setVisibility(View.GONE);

			tv_name = (TextView) convertView.findViewById(R.id.studentname);
			tv_email = (TextView) convertView.findViewById(R.id.Email);
			tv_note = (TextView) convertView.findViewById(R.id.Notes);
			tv_contactno = (TextView) convertView.findViewById(R.id.contactno);
			img_edit = (ImageView) convertView.findViewById(R.id.editImg);
			tv_contactno.setText(": " + studentlist.getContactInfo());

			tv_name.setText(studentlist.getName());
			tv_email.setText(": " + studentlist.getEmail());
			try{
			if (studentlist.getNotes() == null | studentlist.getNotes().equalsIgnoreCase("null")) {
				tv_note.setText(": " );
				}
			else
			{
				tv_note.setText(": " + studentlist.getNotes());
				
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				}
			

			// System.err.println("adpter name="+studentlist.getName());
			img_edit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					studentlist = arraylist_student.get(position);
					Intent intent = new Intent(ParentDetailActivity.this,EditStudentActivity.class);
					intent.putExtra("trigger", "Tutor");
					intent.putExtra("name", studentlist.getName());
					intent.putExtra("email", studentlist.getEmail());
					intent.putExtra("contactno", studentlist.getContactInfo());
					intent.putExtra("fees", studentlist.getFees());
					intent.putExtra("notes", studentlist.getNotes());
					intent.putExtra("gender", studentlist.getGender());
					intent.putExtra("parentid", studentlist.getParentId());
					intent.putExtra("studentid", studentlist.getStudentId());
					startActivity(intent);

				}
			});
			return convertView;
		}
	}

	public void processFinish(String output, String methodName) {

		Log.e(methodName, output);

		if (methodName.equals("fetch-student")) {

			arraylist_student = parser.getStudentlist(output);
			adapter = new StudentAdapter(ParentDetailActivity.this);
			listView.setAdapter(adapter);
		} else if (methodName.equals("insert-parents-notes")) {

			try {

				JSONObject jsonChildNode = new JSONObject(output);

				String result = jsonChildNode.getString("result").toString();
				String message = jsonChildNode.getString("message").toString();
				if (result.equals("0")) {
					
					Editor editor=tutorPrefs.edit();
					editor.putString("p_notes", txtNotes.getText().toString());
					editor.commit();
					
					Util.alertMessage(ParentDetailActivity.this,
							"Your note updated successfully.");
				} else {
					Util.alertMessage(ParentDetailActivity.this, message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}