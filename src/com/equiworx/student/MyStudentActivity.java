package com.equiworx.student;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.StudentList;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class MyStudentActivity extends Activity implements AsyncResponseForTutorHelper{
	
	private TutorHelperParser parser;
	private StudentAdapter adapter;
	private ListView listView;
	private String parentId,tutorId="";
	private SharedPreferences tutorPrefs;
	private RelativeLayout back;
	private StudentList studentlist;
	private TextView title;
	private ArrayList<StudentList> arraylist_student=new ArrayList<StudentList>();
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.my_studentlist);
		
		initializeLayout();
		setClickListeners();
		fetchStudentList();
	}
	private void setClickListeners() {
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			finish();
				
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				studentlist = arraylist_student.get(position);
				Intent i=new Intent(MyStudentActivity.this,StudentDetailActivity.class);
				i.putExtra("trigger", "parent");
				i.putExtra("name", studentlist.getName());
				i.putExtra("address", studentlist.getAddress());
				i.putExtra("email", studentlist.getEmail());
				i.putExtra("phone", studentlist.getContactInfo());
				i.putExtra("fees", studentlist.getFees());
				i.putExtra("notes", studentlist.getNotes());
				i.putExtra("gender", studentlist.getGender());
				i.putExtra("parentid", studentlist.getParentId());
				i.putExtra("studentid", studentlist.getStudentId());
				i.putExtra("isActiveInMonth", studentlist.getIsActiveInMonth());
				startActivity(i);
				
			}
		})	;
		
		
	}
	private void initializeLayout() {
		studentlist=new StudentList();
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser=new TutorHelperParser(MyStudentActivity.this);
		listView=(ListView)findViewById(R.id.listView);
		parentId =  tutorPrefs.getString("parentID", "0");
		tutorId  = "-1";//tutorPrefs.getString("tutorID", "0");
		back=(RelativeLayout)findViewById(R.id.back_layout);
		title = (TextView) findViewById(R.id.title);
		title.setText("My Students");
	}
	private void fetchStudentList() {
		if (Util.isNetworkAvailable(MyStudentActivity.this)){
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("parent_id", parentId));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorId));
			Log.e("student list", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(MyStudentActivity.this, "fetch-student", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyStudentActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(MyStudentActivity.this,
					"Please check your internet connection");
		}
	}
	public class StudentAdapter extends BaseAdapter
	{			
		private Context context;
		private TextView tv_name, tv_email,tv_note,tv_contactno,id,lbl_id,student_fees_text,outstandingBalnce;
		private ImageView img_edit;
		private StudentList studentlist;
		
		public StudentAdapter(Context ctx)
		{
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

		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null){
			    convertView = inflater.inflate(R.layout.studentlist_row, parent, false);
			}

		studentlist = arraylist_student.get(position);
		TextView	lbl_notes =(TextView)convertView.findViewById(R.id.lbl_notes);
		lbl_notes.setVisibility(View.GONE);
		TextView	lbl_address =(TextView)convertView.findViewById(R.id.lbl_address);
		lbl_address.setVisibility(View.VISIBLE);
			
			id = (TextView)convertView.findViewById(R.id.id);
			lbl_id= (TextView)convertView.findViewById(R.id.lbl_id);
			lbl_id.setVisibility(View.GONE);
			id.setVisibility(View.GONE);
			
			student_fees_text =(TextView)convertView.findViewById(R.id.student_fees_text);
			student_fees_text.setVisibility(View.VISIBLE);
			outstandingBalnce=(TextView)convertView.findViewById(R.id.outstandingBalnce);
			outstandingBalnce.setVisibility(View.VISIBLE);
			
			tv_name = (TextView)convertView.findViewById(R.id.studentname);
			tv_email = (TextView)convertView.findViewById(R.id.Email);
			tv_note = (TextView)convertView.findViewById(R.id.Notes);
			tv_contactno = (TextView)convertView.findViewById(R.id.contactno);
			img_edit= (ImageView)convertView.findViewById(R.id.editImg);
			
			id.setText(": "+studentlist.getStudentId());
			tv_contactno.setText(": "+studentlist.getContactInfo());
			tv_email.setText(": "+studentlist.getEmail());
			tv_note.setText(": "+studentlist.getAddress());
			tv_name.setText(studentlist.getName()+"("+studentlist.getStudentId()+")");
			if(studentlist.getFees()==null | studentlist.getFees().equalsIgnoreCase("null"))
			{
				
				outstandingBalnce.setText(": $"+"0");
				}
			else{
				outstandingBalnce.setText(": $"+studentlist.getFees());
			}
			
			img_edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				studentlist = arraylist_student.get(position);
				Intent i=new Intent(MyStudentActivity.this,EditStudentActivity.class);
				i.putExtra("trigger", "parent");
				i.putExtra("name", studentlist.getName());
				i.putExtra("address", studentlist.getAddress());
				i.putExtra("email", studentlist.getEmail());
				i.putExtra("contactno", studentlist.getContactInfo());
				i.putExtra("fees", studentlist.getFees());
				i.putExtra("notes", studentlist.getNotes());
				i.putExtra("gender", studentlist.getGender());
				i.putExtra("parentid", studentlist.getParentId());
				i.putExtra("studentid", studentlist.getStudentId());
								
				startActivity(i);
					
				}
			});
			return convertView;
		}
	}
	public void processFinish(String output, String methodName) {
		if(methodName.equals("fetch-student")){
			
			arraylist_student = parser.getStudentlistwithoutNote(output);
			if(arraylist_student.size()==0)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(MyStudentActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setCancelable(false);
				alert.setMessage("no Students added yet now");
				alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						finish();
					}
				});	
				alert.show();
			}
			else
			{
				adapter = new StudentAdapter(MyStudentActivity.this);
				listView.setAdapter(adapter);
			}
			
			
		}
		
	}

}
