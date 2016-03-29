package com.equiworx.lesson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.MyLesson;
import com.equiworx.model.StudentList;
import com.equiworx.tutor.CancellationActivity;
import com.equiworx.tutor.CancellationActivity.TuturRequestAdpter;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class MyLessonActivity extends Activity implements AsyncResponseForTutorHelper{

	private ArrayList<MyLesson> arraylist_mylesson=new ArrayList<MyLesson>();
	public static ArrayList<StudentList> arraylist_studentlist=new ArrayList<StudentList>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
 	private DateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");  
 	private DateFormat showtimeformatter = new SimpleDateFormat("HH:mm");  
 	private MyLesson mylesson;
	private MyLessonAdapter adapter;
	private ListView listView;
	private TutorHelperParser parser;
	private TextView tv_title;

	
	
	private String str_trigger="";
	private String str_parentid="",str_lessonid="0",str_reason="";
	private String str_tuterid="",SelectedDate=null,str_startdate,str_starttime;
	private int datecheck=0;
	private int timecheck=0;
	
	private SharedPreferences tutorPrefs;
	
	private RelativeLayout back_layout;
	
	public  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mylesson);
		
		intializelayout();
		
		setOnClickListners();
		getLessonDetail();
}

	private void getLessonDetail() {
		SelectedDate=getIntent().getStringExtra("date");
	
		if(SelectedDate!=null)
		{
			webLessonDetails();
			tv_title.setText("Lesson Details");
			}
		else
		{
			fetchlMyLessonList();
			tv_title.setText("My Lessons");
			}
	}

	private void intializelayout() {
		mylesson=new MyLesson();
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title=(TextView)findViewById(R.id.title);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		listView=(ListView)findViewById(R.id.listView_mylesson);
		parser=new TutorHelperParser(MyLessonActivity.this);
		
	}

	private void setOnClickListners() {
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
			System.err.println("listclick");
				mylesson= arraylist_mylesson.get(position);
				Intent intent=new Intent(MyLessonActivity.this,LessonDetailsActivity.class);
				intent.putExtra("tname", mylesson.getTutor_name());
				intent.putExtra("day", mylesson.getDays());	
				intent.putExtra("description", mylesson.getLessonDescription());
				intent.putExtra("stime", mylesson.getStartTime());
				intent.putExtra("etime", mylesson.getEndTime());
				intent.putExtra("duration", mylesson.getDuration());
				intent.putExtra("sdate", mylesson.getLessonDate());
				intent.putExtra("edate", mylesson.getLessonenddate());
				intent.putExtra("lid", mylesson.getLessonId());
				intent.putExtra("tid", mylesson.getLesson_tutor_id());
				intent.putExtra("recuring", mylesson.getIsRecurring());
				arraylist_studentlist.clear();
				System.err.println("students="+mylesson.getArray_studentlist().size());//setArray_studentlist
				for(int i=0;i<mylesson.getArray_studentlist().size();i++)
				{
					StudentList studentlist=new StudentList();
					studentlist.setName(mylesson.getArray_studentlist().get(i).getName());
					studentlist.setAddress(mylesson.getArray_studentlist().get(i).getAddress());
					studentlist.setContactInfo(mylesson.getArray_studentlist().get(i).getContactInfo());
					studentlist.setEmail(mylesson.getArray_studentlist().get(i).getEmail());
					studentlist.setNotes(mylesson.getArray_studentlist().get(i).getNotes());
					studentlist.setStudentfee(mylesson.getArray_studentlist().get(i).getStudentfee());
					Log.d("", "student fee"+mylesson.getArray_studentlist().get(i).getStudentfee());
					arraylist_studentlist.add(studentlist);
				}
				
				startActivity(intent);
					
				}
			});
	}
	public void webLessonDetails()
	{
		if (Util.isNetworkAvailable(MyLessonActivity.this)){
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("lesson_date", SelectedDate));
			nameValuePairs.add(new BasicNameValuePair("tutor_id",tutorPrefs.getString("tutorID", "")));
			//nameValuePairs.add(new BasicNameValuePair("parent_id",tutorPrefs.getString("tutorpass", "")));
			
			Log.e("get lesson detail", nameValuePairs.toString());///get-lesson-detail.php
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(MyLessonActivity.this, "get-lesson-detail", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyLessonActivity.this;
			mLogin.execute();
		}else {
				Util.alertMessage(MyLessonActivity.this,"Please check your internet connection");
				}
		}
	private void fetchlMyLessonList() {
		
		if(tutorPrefs.getString("mode", "").equals("parent"))
		{
			str_trigger="Parent";
			str_parentid=tutorPrefs.getString("parentID", "");
			str_tuterid="";//tutorPrefs.getString("tutorID","");
			}
		else if(tutorPrefs.getString("mode", "").equals("tutor"))
		{
			str_trigger="Tutor";
			str_parentid="";//tutorPrefs.getString("parentID", "");
			str_tuterid=tutorPrefs.getString("tutorID","");
		}
		if (Util.isNetworkAvailable(MyLessonActivity.this)){
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("parent_id", str_parentid));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", str_tuterid));
			nameValuePairs.add(new BasicNameValuePair("trigger", str_trigger));
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			Log.e("My lesson", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(MyLessonActivity.this, "fetch-my-lessons", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyLessonActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(MyLessonActivity.this,"Please check your internet connection");
		}
		
	}
	
	public class MyLessonAdapter extends BaseAdapter
	{			
		private Context context;
		private MyLesson myLessonobj;
		private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		public MyLessonAdapter(Context ctx)
		{
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist_mylesson.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist_mylesson.get(position);
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null){
			    convertView = inflater.inflate(R.layout.lesson_row, parent, false);
			}

			myLessonobj = arraylist_mylesson.get(position);
	
			TextView approve= (TextView)convertView.findViewById(R.id.imageView_connect);
			approve.setVisibility(View.GONE);
			TextView reject= (TextView)convertView.findViewById(R.id.imageView_reject);
			reject.setVisibility(View.GONE);
			
			
		TextView	name = (TextView)convertView.findViewById(R.id.name);
		TextView	description = (TextView)convertView.findViewById(R.id.description);
		TextView	fees = (TextView)convertView.findViewById(R.id.fees);
		TextView	sun = (TextView)convertView.findViewById(R.id.sun);
		TextView	mon = (TextView)convertView.findViewById(R.id.mon);
		TextView	tue = (TextView)convertView.findViewById(R.id.tue);
		TextView	wed = (TextView)convertView.findViewById(R.id.wed);
		TextView	thur = (TextView)convertView.findViewById(R.id.thur);
		TextView	fri = (TextView)convertView.findViewById(R.id.fri);
		TextView	sat = (TextView)convertView.findViewById(R.id.sat);
		//TextView	stime = (TextView)convertView.findViewById(R.id.stime);
		TextView	etime = (TextView)convertView.findViewById(R.id.etime);

//			
		name.setText(myLessonobj.getTutor_name()+" has send you lesson request for ");
		name.setVisibility(View.GONE);
		description.setText(": "+myLessonobj.getLessonDescription());
		String active=myLessonobj.getIsActive();
		if(active.equalsIgnoreCase("false"))
		{
			fees.setText("Not Approved Yet");
			fees.setTextColor(getResources().getColor(R.color.red));
			}
	String getdays=myLessonobj.getDays();
	System.err.println("day="+getdays);
	if(getdays!=null)
	{
			if(getdays.contains("Sunday"))
			{
				sun.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				sun.setTextColor(getResources().getColor(R.color.gray));
			}
			if(getdays.contains("Monday"))
			{
				mon.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				mon.setTextColor(getResources().getColor(R.color.gray));
			}
			if(getdays.contains("Tuesday"))
			{
				tue.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				tue.setTextColor(getResources().getColor(R.color.gray));
				}
			if(getdays.contains("Wednesday"))
			{
				wed.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				wed.setTextColor(getResources().getColor(R.color.gray));
			}
			if(getdays.contains("Thursday"))
			{
				thur.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				thur.setTextColor(getResources().getColor(R.color.gray));
			}
			if(getdays.contains("Saturday"))
			{
				sat.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				sat.setTextColor(getResources().getColor(R.color.gray));
			}
			if(getdays.contains("Friday"))
			{
				fri.setTextColor(getResources().getColor(R.color.appBlue));
				}
			else
			{
				fri.setTextColor(getResources().getColor(R.color.gray));
			}
	}
		//stime.setText(""+myLesson.getStartTime() +" - ");
	
	

	try {
		etime.setText(myLessonobj.getStartTime().subSequence(0, 5) +" - "+myLessonobj.getEndTime().substring(0, 5));
		
		} catch (Exception e) {
				e.printStackTrace();
		}
	
		TextView duration=(TextView)convertView.findViewById(R.id.duration);
		
		
		//duration.setText("Duration : "+myLessonobj.getDuration()+"hr");
		
		if (myLessonobj.getDuration().equalsIgnoreCase("0") ) {
			duration.setText(": "+myLessonobj.getDuration()+" hour");
			
		}else if (myLessonobj.getDuration().equalsIgnoreCase("1")) {
			
			duration.setText(": "+myLessonobj.getDuration()+" hour");
			
		}else{
			
			duration.setText(": "+myLessonobj.getDuration()+" hours");
			
		}
		TextView time=(TextView)convertView.findViewById(R.id.time);
		
		
		System.err.println("students="+myLessonobj.getArray_studentlist().size());//setArray_studentlist
		String studentname="";
		for(int i=0;i<myLessonobj.getArray_studentlist().size();i++)
		{
			
			if(i==0)
			{
				studentname=	""+myLessonobj.getArray_studentlist().get(i).getName();
				System.err.println("name="+myLessonobj.getArray_studentlist().get(i).getName());
			}
			else if(i>0)
			{
				studentname=studentname+","+myLessonobj.getArray_studentlist().get(i).getName();
				System.err.println("name="+myLessonobj.getArray_studentlist().get(i).getName());
			}
			//arraylist_studentlist.add(studentlist);
			}
		if(myLessonobj.getStudentno().equals("1"))
		{
			time.setText(": "+myLessonobj.getStudentno()+" Student");
		}
		else
		{
			time.setText(": "+myLessonobj.getStudentno()+" Students");
		}
		TextView studentnames=(TextView)convertView.findViewById(R.id.studentnames);
		studentnames.setText(": "+studentname);
		Button cancel=(Button)convertView.findViewById(R.id.cancel);
		System.err.println("date=="+myLessonobj.getLessonDate());
		if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent"))
		{
		
			if((myLessonobj.getLessonDate()!=null))  
		    {
		    	dateCompare(myLessonobj.getLessonDate());  
		    				    			
		    			}//date compare  
			if((myLessonobj.getStartTime()!=null))  
			{
				timeCompare(myLessonobj.getStartTime());  
				    			
			}//date	  
				
			if(datecheck==1)
			{
				cancel.setVisibility(View.VISIBLE);
				}
			else if(datecheck==2)
			{
				if(timecheck==1)
				{
					cancel.setVisibility(View.VISIBLE);
					}
			}
			else
			{
				cancel.setVisibility(View.GONE);
			}
		}
		else
		{
			cancel.setVisibility(View.GONE);
		}
		cancel.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			
			LayoutInflater li = LayoutInflater.from(MyLessonActivity.this);
			View promptsView = li.inflate(R.layout.dailog_layout, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyLessonActivity.this);

			alertDialogBuilder.setTitle("Enter Reason");
			//alertDialogBuilder.setMessage("Enter Reason");
			alertDialogBuilder.setView(promptsView);

			final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
			userInput.setHint("Enter reason");
			// set dialog message
			alertDialogBuilder.setCancelable(false)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
				    	str_reason= userInput.getText().toString();
				    	if(str_reason.equals(""))
				    	{
				    		Util.alertMessage(MyLessonActivity.this, "Please enter reason");
				    		}
				    	else
				    	{
				    		str_lessonid=arraylist_mylesson.get(position).getLessonId();//myLessonobj.getLessonId();
				    		tutorPrefs.getString("parentID", "");
				    		System.err.println("lessonId="+str_lessonid+"  "+tutorPrefs.getString("parentId", ""));
				    		cancallationLesson(str_lessonid);
					    	
					    	}
				    }
				  })
				  .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  })
				  ;
			
			// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
						// show it
						alertDialog.show();
				
			}
		});
		
			return convertView;
		}
	}
	
	private void cancallationLesson(String str_lessonid)
	{
		if (Util.isNetworkAvailable(MyLessonActivity.this)){
			
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("lesson_id", str_lessonid));
			nameValuePairs.add(new BasicNameValuePair("parent_id", tutorPrefs.getString("parentID", "")));
			nameValuePairs.add(new BasicNameValuePair("reason", str_reason));
		
			Log.e("cancel lesson", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(MyLessonActivity.this, "request-lesson-cancellation", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyLessonActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(MyLessonActivity.this,"Please check your internet connection");
		}
	}
public void processFinish(String output, String methodName) {
	if(methodName.equals("fetch-my-lessons")){
		
		arraylist_mylesson = parser.getMyLesson(output);
		
		if(arraylist_mylesson.size()==0)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(MyLessonActivity.this);
			alert.setTitle("Tutor Helper");
			alert.setMessage("no lesson added yet now");
			alert.setCancelable(false);
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
			adapter = new MyLessonAdapter(MyLessonActivity.this);
			listView.setAdapter(adapter);
		}
		
	}
	else if(methodName.equals("get-lesson-detail"))
	{
		arraylist_mylesson = parser.getMyLesson(output);
		adapter = new MyLessonAdapter(MyLessonActivity.this);
		listView.setAdapter(adapter);
		
			}
	else if(methodName.equals("request-lesson-cancellation"))
	{
		try {

			JSONObject jsonChildNode = new JSONObject(output);

		   String	result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			String message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(MyLessonActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setCancelable(false);
				alert.setMessage("Your request to cancel lesson has been submit successfully.");
				alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						finish();
					}
				});	
				alert.show();
			}
			else{
				Util.alertMessage(MyLessonActivity.this, message);	
				}
		}catch(Exception e)
		{
			e.printStackTrace();
			}
		}
			
	
	}
	
	
	public void dateCompare(String str_date)
	{
	 Date date = null;
		try {
			date = (Date)formatter.parse(str_date);
		
	
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		str_startdate = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	//check date condition 
		
           		
		  try{
			  Calendar c = Calendar.getInstance();
			  System.out.println("Current time => " + c.getTime());
			  String currentdate = formatter.format(c.getTime());
		      Date seldate = formatter.parse(str_startdate);
		      Date curdate = formatter.parse(currentdate);
		      System.err.println("cuurent date="+curdate);
		     System.err.println("selected date="+seldate);
		     if (curdate.before(seldate))
		      {
		          
		        datecheck=1;
		        System.out.println("date=="+datecheck);   
		      		}
		     else if(curdate.equals(seldate))
		     { 
		    	datecheck=2;
		    	 System.out.println("date==="+datecheck);
		    
		     }
		     else
		     {
		    	//Util.alertMessage(AddLessonActivity.this, "Please select Greater date from current date");  
		    	   datecheck=0;
		    	   System.out.println("date==="+datecheck);
		     	}

		    }catch (Exception e1){
		        e1.printStackTrace();
		    	}
			}
	public void timeCompare(String str_time)
	{
		 Date date = null;
			try {
				date = (Date)timeformatter.parse(str_time);
			
			
			timeformatter = new SimpleDateFormat("HH:mm:ss");
			str_starttime = timeformatter.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	 //check time  	 
	    	  try{
 			  Calendar c = Calendar.getInstance();
 			 

 			  String currentdate = timeformatter.format(c.getTime());
 			  
			     Date seldate = timeformatter.parse(str_starttime);
			     Date curdate = timeformatter.parse(currentdate);
			     System.out.println("Current time => " + curdate);
			     System.out.println("seldate time => " + seldate);
			     if (curdate.before(seldate))
			      {
			    	 timecheck=1;
			    	 System.out.println("time="+timecheck);   
			    	 
			      
			      }
			     else if(curdate.equals(seldate))
			     {
			    	 timecheck=0;
			    	 System.out.println("time="+timecheck);  
			     }
			     else
			     {
			    	 timecheck=2;
			    	 System.out.println("time="+timecheck);  
			     	}

			    }catch (Exception e1){
			        e1.printStackTrace();
			    }
	}
}