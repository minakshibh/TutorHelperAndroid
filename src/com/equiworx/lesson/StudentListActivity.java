package com.equiworx.lesson;


import java.util.ArrayList;
import java.util.HashSet;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.StudentList;
import com.equiworx.student.AddStudent;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;



public class StudentListActivity extends Activity implements AsyncResponseForTutorHelper{
	private  ArrayList<StudentList> arraylist_student=new ArrayList<StudentList>();
	private MyCustomAdapter adapter;
	private ListView listView;
	private  Button  btnselect;
	private String str_parentid="",str_tuterid="";
	private SharedPreferences tutorPrefs;
	private TextView tv_title,tv_cancel;
	private TutorHelperParser parser;
	
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_studentlist);
	

	intializelayout();
	fetchlStudentList();
	setOnClickListners();
	
	}
	
	private void intializelayout() {
	
		AddLessonActivity.AddActivities.add(StudentListActivity.this);
		
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser=new TutorHelperParser(StudentListActivity.this);
		tv_title=(TextView)findViewById(R.id.title);
		tv_title.setText("Add Students");
		listView=(ListView)findViewById(R.id.listView);
		btnselect=(Button)findViewById(R.id.button_done);
		tv_cancel=(TextView)findViewById(R.id.textView_cancel);

	}

	private void setOnClickListners() {
		
	btnselect.setOnClickListener(new OnClickListener() {
	public void onClick(View v) {
		 String studemtID="";
		
		for (StudentList gdf : adapter.getBox()) {
			if (gdf.box) {
					studemtID = gdf.getStudentId();
					Log.d("&&&&&&&", "recieveridsssfirst==" + studemtID);
					AddLessonActivity.arraylist_addstu.add(studemtID);
					Log.d("&&&&&&&", "recieveridssssecong"+	AddLessonActivity.arraylist_addstu.toString());
			}
		}	
		
			 
		if(AddLessonActivity.arraylist_addstu.size()==0)
		{	
			Util.alertMessage(StudentListActivity.this, "Please select Students");
			
		}
		else
		{
			Intent intent = new Intent(StudentListActivity.this,AddLessonActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			}
		}
	});
	tv_cancel.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			AddLessonActivity.arraylist_addstu.clear();
			//arraylist_checkbox.clear();
			finish();
			}
		});
	
		
	}
	
	private void fetchlStudentList() {
		
		if(tutorPrefs.getString("mode", "").equals("parent"))
		{
			str_parentid=tutorPrefs.getString("parentID", "");
			str_tuterid="-1";
			}
		else if(tutorPrefs.getString("mode", "").equals("tutor"))
		{
			str_parentid="-1";
			str_tuterid=tutorPrefs.getString("tutorID","");
			}
		if (Util.isNetworkAvailable(StudentListActivity.this)){

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("parent_id", str_parentid));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", str_tuterid));
			Log.e("Student list", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(StudentListActivity.this, "fetch-student", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) StudentListActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(StudentListActivity.this,"Please check your internet connection");
		}
		
	}

	
	
	
	private class MyCustomAdapter extends BaseAdapter {
		ArrayList<StudentList> box1;
		  private ArrayList<StudentList> countryList;
		// private StudentList country;
		  LayoutInflater lInflater;
		  private StudentList gdf;
		  Activity activity;

		 
		  public MyCustomAdapter(StudentListActivity studentListActivity,
				ArrayList<StudentList> arraylist_student) {
			// TODO Auto-generated constructor stub
			  activity=studentListActivity;
			  countryList=arraylist_student;
			  lInflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  }
		  @Override
		  public View getView(final int position, View convertView, ViewGroup parent) {
		 
			  View view = convertView;
				if (view == null) {
					view = lInflater.inflate(R.layout.studentlist_row1, parent, false);
				} 
			  
		   TextView name = (TextView) view.findViewById(R.id.studentname);
		   TextView studentid = (TextView) view.findViewById(R.id.studentid);
		   TextView parentid = (TextView) view.findViewById(R.id.parentid);
		   CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox_student);
		    gdf = getGetdatafrom(position);
		   
		    checkbox.setOnCheckedChangeListener(myCheckChangList);
		    checkbox.setTag(position);
		    checkbox.setChecked(gdf.box);
		   
		   
		 //  StudentList country = countryList.get(position);
		   studentid.setText("Student Id : "+gdf.getStudentId());
		   name.setText(gdf.getName());
		   parentid.setText("Parent Id : "+gdf.getParentId());
		 
		
		   return view;
		 
		  }
		  StudentList getGetdatafrom(int position) {
				return ((StudentList) getItem(position));
			}

			ArrayList<StudentList> getBox() {
				box1 = new ArrayList<StudentList>();
				for (StudentList gdf : countryList) {
					if (gdf.box)
						box1.add(gdf);
				}
				return box1;
			}
		  OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					getGetdatafrom((Integer) buttonView.getTag()).box = isChecked;
				}
			};

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return countryList.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return countryList.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		 
		 }
	
	
	
  public void processFinish(String output, String methodName) {
	if(methodName.equals("fetch-student")){
				
		if(tutorPrefs.getString("mode", "").equals("parent"))
		{
			arraylist_student = parser.getStudentlistwithoutNote(output);
			}
		else if(tutorPrefs.getString("mode", "").equals("tutor"))
		{
			arraylist_student = parser.getStudentlist(output);
			
			}
		
		//arraylist_student.clear();
		if(arraylist_student.size()==0)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(StudentListActivity.this);
			alert.setTitle("Tutor Helper");
			alert.setMessage("No student have been added to your account yet. Continue to add a new student in order to add some lesson.");
			alert.setPositiveButton("Add new Student", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i=new Intent(StudentListActivity.this,AddStudent.class);
				startActivity(i);
				finish();
				}
			});
			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
				}
			});	
			alert.show();
		}
		adapter = new MyCustomAdapter(this,  arraylist_student);
		
		listView.setAdapter(adapter);
		
		
		
	}
	}
	
	static ArrayList<String> removeDuplicates(ArrayList<String> list) {

		// Store unique items in result.
		ArrayList<String>  sortedlist= new ArrayList<String>();

		// Record encountered Strings in HashSet.
		HashSet<String> set = new HashSet<String>();

		// Loop over argument list.
		for (String item : list) {

		    // If String is not in set, add it to the list and the set.
		    if (!set.contains(item)) {
		    	sortedlist.add(item);
		    	set.add(item);
		    }
		}
		return sortedlist;
	    }
	@Override
	public void onBackPressed() {
		/*Intent intent = new Intent(StudentListActivity.this,AddLessonActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);*/
	
	}

}

