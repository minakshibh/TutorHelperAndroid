package com.equiworx.tutor;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.array;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.lesson.LessonDetailsActivity;
import com.equiworx.model.MyLesson;
import com.equiworx.model.StudentList;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class Payment_LessonsDetailsActivity extends Activity implements AsyncResponseForTutorHelper {
	
	public static ArrayList<MyLesson> arraylist_mylesson=new ArrayList<MyLesson>();
	public static ArrayList<StudentList> arraylist_studentlist=new ArrayList<StudentList>();
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
 	private DateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");  
 	private DateFormat showtimeformatter = new SimpleDateFormat("HH:mm");  
 	private MyLesson mylesson;
	private MyLessonAdapter adapter;
	private ListView listView;
	private TutorHelperParser parser;
	private TextView tv_title;
	private ImageView back;
	private  RelativeLayout back_layout;
	private String str_trigger="",getLyear="",getLmonth;
	private String str_parentid="",str_lessonid="0",str_reason="",getLessonIds;
	private String str_tuterid="",SelectedDate=null,str_startdate,str_starttime;
	private int datecheck=0;
	private int timecheck=0;
	
	private SharedPreferences tutorPrefs;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mylesson);
        intializelayout();
		setOnClickListners();
		webLessonDetails();
	}
	
	private void intializelayout() {
		mylesson=new MyLesson();
		getLessonIds=getIntent().getStringExtra("getLessonIds");
		//getLyear=getIntent().getStringExtra("getLyear");
		//getLmonth=getIntent().getStringExtra("getLmonth");
		Log.d("","getLessonIds"+getLessonIds);
		back=(ImageView)findViewById(R.id.back);
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title=(TextView)findViewById(R.id.title);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		listView=(ListView)findViewById(R.id.listView_mylesson);
		parser=new TutorHelperParser(Payment_LessonsDetailsActivity.this);
		tv_title.setText("Lessons");
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
				
				mylesson= arraylist_mylesson.get(position);
				Intent intent=new Intent(Payment_LessonsDetailsActivity.this,Payment_FulllessonDetialsActivity.class);
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
				
				System.err.println("students="+mylesson.getStudebtlist().size());//setArray_studentlist
				for(int i=0;i<mylesson.getStudebtlist().size();i++)
				{
					StudentList studentlist=new StudentList();
					studentlist.setStudentId(mylesson.getStudebtlist().get(i).getStudentId());
					studentlist.setName(mylesson.getStudebtlist().get(i).getName());
					studentlist.setAddress(mylesson.getStudebtlist().get(i).getAddress());
					studentlist.setContactInfo(mylesson.getStudebtlist().get(i).getContactInfo());
					studentlist.setEmail(mylesson.getStudebtlist().get(i).getEmail());
					//studentlist.setNotes(mylesson.getArray_studentlist().get(i).getNotes());
					studentlist.setStudentfee(mylesson.getStudebtlist().get(i).getStudentfee());
					//Log.d("", "student fee"+mylesson.getArray_studentlist().get(i).getStudentfee());
					arraylist_studentlist.add(studentlist);
					}
				startActivity(intent);
					
				}
			});
	}
	public void webLessonDetails()
	{
		if (Util.isNetworkAvailable(Payment_LessonsDetailsActivity.this)){
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("lesson_ids", getLessonIds));
			nameValuePairs.add(new BasicNameValuePair("tutor_id",tutorPrefs.getString("tutorID", "")));
			//nameValuePairs.add(new BasicNameValuePair("parent_id",tutorPrefs.getString("tutorpass", "")));
			
			Log.e("get lesson detail", nameValuePairs.toString());///get-lesson-detail.php
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(Payment_LessonsDetailsActivity.this, "get-lesson-detail-month", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) Payment_LessonsDetailsActivity.this;
			mLogin.execute();
		}else {
				Util.alertMessage(Payment_LessonsDetailsActivity.this,"Please check your internet connection");
				}
		}

	
	public class MyLessonAdapter extends BaseAdapter
	{			
		private Context context;
		private MyLesson myLessonobj;
		private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		private ArrayList<MyLesson> arrayLesson=new ArrayList<MyLesson>();
		public MyLessonAdapter(Context ctx, ArrayList<MyLesson> arraylist_mylesson)
		{
			context = ctx;
			arrayLesson=arraylist_mylesson;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arrayLesson.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrayLesson.get(position);
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null){
			    convertView = inflater.inflate(R.layout.activity_newpamentlessondetail, parent, false);
			}

			myLessonobj = arrayLesson.get(position);
		TextView	description = (TextView)convertView.findViewById(R.id.description);
		TextView	etime = (TextView)convertView.findViewById(R.id.etime);
		TextView	noOfStudents = (TextView)convertView.findViewById(R.id.no_of_stuent_text);
		TextView lession_date=(TextView)convertView.findViewById(R.id.lession_date);
		
		TextView	sun = (TextView)convertView.findViewById(R.id.sun);
		TextView	mon = (TextView)convertView.findViewById(R.id.mon);
		TextView	tue = (TextView)convertView.findViewById(R.id.tue);
		TextView	wed = (TextView)convertView.findViewById(R.id.wed);
		TextView	thur = (TextView)convertView.findViewById(R.id.thur);
		TextView	fri = (TextView)convertView.findViewById(R.id.fri);
		TextView	sat = (TextView)convertView.findViewById(R.id.sat);
		
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
		try{
			etime.setText(": "+myLessonobj.getStartTime().subSequence(0, 5)+" - "+myLessonobj.getEndTime().subSequence(0, 5));
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		description.setText(": "+myLessonobj.getLessonDescription());
		
		noOfStudents.setText(": "+myLessonobj.getStudentno());
	 
	
	   lession_date.setText(": "+myLessonobj.getLessonDate());
	

		TextView duration=(TextView)convertView.findViewById(R.id.duration);
         
		if (myLessonobj.getDuration().equalsIgnoreCase("0") ) {
			duration.setText(": "+myLessonobj.getDuration()+" hour");
		}else if (myLessonobj.getDuration().equalsIgnoreCase("1")) {
			duration.setText(": "+ myLessonobj.getDuration()+" hour");
		}else{
			duration.setText(": "+myLessonobj.getDuration()+" hours");
			
		}
		TextView studentnames_text=(TextView)convertView.findViewById(R.id.studentnames_text);
		
		System.err.println("students="+myLessonobj.getStudebtlist().size());//setArray_studentlist
		String studentname="";
		for(int i=0;i<myLessonobj.getStudebtlist().size();i++)
		{
			
			//StudentList studentlist=new StudentList();
		//	String name=mylesson.getStudebtlist().get(i).getName();
			
			/*if(i==0)
			{
				String name=mylesson.getStudebtlist().get(i).getName();
				studentnames_text.setText(name);
				}
			else
			{
				String name=mylesson.getStudebtlist().get(i).getName();
				String all=name+",";
				studentnames_text.setText(all);
				
			}*/
			if(i==0)
			{
				studentname=	""+myLessonobj.getStudebtlist().get(i).getName();
				System.err.println("name="+myLessonobj.getStudebtlist().get(i).getName());
			}
			else if(i>0)
			{
				studentname=studentname+","+myLessonobj.getStudebtlist().get(i).getName();
				System.err.println("name="+myLessonobj.getStudebtlist().get(i).getName());
			}
		}
		studentnames_text.setText(": "+studentname);
		
		//TextView time=(TextView)convertView.findViewById(R.id.time);
		

		
		//TextView studentnames=(TextView)convertView.findViewById(R.id.studentnames);
		
	
		
			return convertView;
		}
	}
	
	
public void processFinish(String output, String methodName) {
	//if(methodName.equals("get-lesson-detail-month")){
		
		arraylist_mylesson = parser.getLessonDetails(output);
		
		if(arraylist_mylesson.size()==0)
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(Payment_LessonsDetailsActivity.this);
			alert.setTitle("Tutor Helper");
			alert.setMessage("no lesson added yet now");
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
			adapter = new MyLessonAdapter(Payment_LessonsDetailsActivity.this,arraylist_mylesson);
			listView.setAdapter(adapter);
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
		    	/* if((myLessonobj.get!=null))  
	    			{
	    				dateCompare(myLessonobj.getLessonDate());  
	    			}//date
*/		     }
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
