package com.equiworx.parent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.lesson.LessonRequestActivity;
import com.equiworx.lesson.MyLessonActivity;
import com.equiworx.lesson.Payment_Activity;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.model.Parent;
import com.equiworx.notification.NotificationParentActivity;
import com.equiworx.student.AddStudent;
import com.equiworx.student.MergeStudentActivity;
import com.equiworx.student.MyStudentActivity;
import com.equiworx.student.StudentRequestActivity;
import com.equiworx.tutor.MyConnectionActivity;
import com.equiworx.tutor.NewsFeedActivity;
import com.equiworx.tutorhelper.HomeAcitivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

/**
 * Customize the weekday gridview test6857/parent123)
 */

public class ParentDashBoard extends FragmentActivity implements
		AsyncResponseForTutorHelper {

	private CaldroidFragment caldroidFragment;
	private Calendar calendar;
	private Date date33;
	private TextView tv_title;
	private TutorHelperParser parser;
	// private Date selectdate1,selectdate2,selectdate3,selectdate4;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private String parentId;
	private ImageView menuIcon, back;
	private LinearLayout menuLayout, calendarlayout, can_request_layout,
			connection_request_alout, student_re_layout, lesson_request_layout,
			mystudent_layout, mylesson_layout, myprofile_layout,
			myconnections_layouts, parnetmerg_layout, merg_student_layout,
			credit_layout, logout_layout,addstudent_layout,invoice_layout,invoice_layoutall;
	private SharedPreferences tutorPrefs;
	private RelativeLayout parentdashboard;
	private LinearLayout newStudentLayout, payment_historyLayout,
			newsFeed_layout;
	private TextView connectionRequests, studentRequests, myStudent,
			lessonRequests, myLesson, parentid, name, myProfile, tuterList,
			logout, studentmerge, parentmerge, tutor_requests, txt_credit,addstudent,invoice_ext;
	private ArrayList<Lesson_Booked> array_lessonbooked=new ArrayList<Lesson_Booked>();
	private String str_date;
	private ArrayList<Parent> arraybasicDetail = new ArrayList<Parent>();
	private TutorHelperDatabaseHandler dbHandler;
	private boolean dashboardProgress=true;
	private TextView connectionRequests_count,studentRequests_count,can_request_count,lessonRequests_count;
	// SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.parent_dashboard);
		
		initializeLayout();
		menuLayoutGone();
		setClickListeners();
		getnotification();
	}

	private void initializeLayout() {
		dbHandler = new TutorHelperDatabaseHandler(ParentDashBoard.this);
		parser=new TutorHelperParser(ParentDashBoard.this);
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title = (TextView) findViewById(R.id.title);
		tv_title.setText("DashBoard");
		back = (ImageView) findViewById(R.id.back);
		back.setVisibility(View.INVISIBLE);
		parentdashboard = (RelativeLayout) findViewById(R.id.parentdashboard);
		newStudentLayout = (LinearLayout) findViewById(R.id.addStudentlayout);
		payment_historyLayout = (LinearLayout) findViewById(R.id.payment_historyLayout);
		calendarlayout = (LinearLayout) findViewById(R.id.calendar1);
		parentid = (TextView) findViewById(R.id.id);

		// menu layoutss
		connection_request_alout = (LinearLayout) findViewById(R.id.connection_request_alout);
		student_re_layout = (LinearLayout) findViewById(R.id.student_re_layout);
		lesson_request_layout = (LinearLayout) findViewById(R.id.lesson_request_layout);
		mystudent_layout = (LinearLayout) findViewById(R.id.mystudent_layout);
		mylesson_layout = (LinearLayout) findViewById(R.id.mylesson_layout);
		myprofile_layout = (LinearLayout) findViewById(R.id.myprofile_layout);
		myconnections_layouts = (LinearLayout) findViewById(R.id.myconnections_layouts);
		parnetmerg_layout = (LinearLayout) findViewById(R.id.parnetmerg_layout);
		merg_student_layout = (LinearLayout) findViewById(R.id.merg_student_layout);
		can_request_layout = (LinearLayout) findViewById(R.id.can_request_layout);
		newsFeed_layout = (LinearLayout) findViewById(R.id.newsFeed_layout);
		invoice_layoutall= (LinearLayout) findViewById(R.id.invoice_layoutall);
		invoice_layoutall.setVisibility(View.VISIBLE);
		invoice_layout = (LinearLayout)findViewById(R.id.invoice_layout);
		invoice_ext= (TextView) findViewById(R.id.invoice_ext);
		newsFeed_layout.setVisibility(View.GONE);
		can_request_layout.setVisibility(View.GONE);

		credit_layout = (LinearLayout) findViewById(R.id.credit_layout);
		credit_layout.setVisibility(View.VISIBLE);
		logout_layout = (LinearLayout) findViewById(R.id.logout_layout);
		addstudent_layout = (LinearLayout) findViewById(R.id.addstudent_layout);
		name = (TextView) findViewById(R.id.name);
		parentid.setText(tutorPrefs.getString("parentID", ""));
		name.setText(tutorPrefs.getString("parentname", ""));
		// newLessonLayout = (LinearLayout)findViewById(R.id.addLessonLayout);
		connectionRequests = (TextView) findViewById(R.id.connectionRequests);
		connectionRequests.setText("Connection Requests");
		studentRequests = (TextView) findViewById(R.id.studentRequests);
		lessonRequests = (TextView) findViewById(R.id.lessonRequests);
		// student=(TextView)findViewById(R.id.student);
	
		myLesson = (TextView) findViewById(R.id.myLesson);
		menuIcon = (ImageView) findViewById(R.id.menuIcon);
		menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
		logout = (TextView) findViewById(R.id.logOut);
		addstudent = (TextView) findViewById(R.id.addstudent);
		tutor_requests = (TextView) findViewById(R.id.can_request);
		tutor_requests.setVisibility(View.GONE);
		myStudent = (TextView) findViewById(R.id.mystu);
		myProfile = (TextView) findViewById(R.id.myProfile);
		tuterList = (TextView) findViewById(R.id.tuterList);
		tuterList.setText("My Connections");
		parentmerge = (TextView) findViewById(R.id.Parentmerge);
		studentmerge = (TextView) findViewById(R.id.Studentmerge);
	
		txt_credit = (TextView) findViewById(R.id.txt_credit);
		txt_credit.setVisibility(View.VISIBLE);

		
		connectionRequests_count=(TextView)findViewById(R.id.connectionRequests_count);
		studentRequests_count=(TextView)findViewById(R.id.studentRequests_count);
		can_request_count=(TextView)findViewById(R.id.can_request_count);
		can_request_count.setVisibility(View.GONE);
		lessonRequests_count=(TextView)findViewById(R.id.lessonRequests_count);
		
		
		
		
		
		SharedPreferences tutorPrefs = getSharedPreferences("tutor_prefs",MODE_PRIVATE);
		parentId = tutorPrefs.getString("parentID", "0");

		Bundle args = new Bundle();
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.clear();
		caldroidFragment = new CaldroidFragment(ParentDashBoard.this);
		Calendar currentdate = Calendar.getInstance();
		str_date = formatter.format(currentdate.getTime());
		try {
			date33 = formatter.parse(str_date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		calendar.setTime(date33);
		args.putInt(CaldroidFragment.MONTH, calendar.get(Calendar.MONTH));
		args.putInt(CaldroidFragment.YEAR, calendar.get(Calendar.YEAR));
		args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
		args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
		caldroidFragment.setArguments(args);
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		// selectdates();
	
		if(tutorPrefs.getString("is_first", "").equals("0"))
		{
			Editor ed = tutorPrefs.edit();
			ed.putString("is_first", "1");
			ed.commit();
			changePasswordPrompt();
			}
		getbasicDetail();
	}

	private void getnotification() {
		if (tutorPrefs.getString("message", "").equals("")) {
		} else {
			Intent intent = new Intent(ParentDashBoard.this,
					NotificationParentActivity.class);
			intent.putExtra("message", tutorPrefs.getString("message", ""));
			startActivity(intent);
		}
		Editor ed = tutorPrefs.edit();
		ed.putString("message", "");
		ed.commit();
	}

	private void setClickListeners() {
		// TODO Auto-generated method stub

		newStudentLayout.setOnClickListener(listener);
		// newLessonLayout.setOnClickListener(listener);
		connectionRequests.setOnClickListener(listener);
		studentRequests.setOnClickListener(listener);
		lessonRequests.setOnClickListener(listener);
		myLesson.setOnClickListener(listener);
		menuIcon.setOnClickListener(listener);
		myProfile.setOnClickListener(listener);
		tuterList.setOnClickListener(listener);
		myStudent.setOnClickListener(listener);
		invoice_layout.setOnClickListener(listener);
		invoice_ext.setOnClickListener(listener);
		studentmerge.setOnClickListener(listener);
		parentmerge.setOnClickListener(listener);
		logout.setOnClickListener(listener);

		txt_credit.setOnClickListener(listener);
		// tutor_requests.setOnClickListener(listener);
		newsFeed_layout.setOnClickListener(listener);
		can_request_layout.setOnClickListener(listener);
		connection_request_alout.setOnClickListener(listener);
		student_re_layout.setOnClickListener(listener);
		lesson_request_layout.setOnClickListener(listener);
		mystudent_layout.setOnClickListener(listener);
		myprofile_layout.setOnClickListener(listener);
		myconnections_layouts.setOnClickListener(listener);
		parnetmerg_layout.setOnClickListener(listener);
		merg_student_layout.setOnClickListener(listener);
		addstudent_layout.setOnClickListener(listener);
		credit_layout.setOnClickListener(listener);
		logout_layout.setOnClickListener(listener);
		tutor_requests.setOnClickListener(listener);
		payment_historyLayout.setOnClickListener(listener);
		parentdashboard.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				menuLayoutGone();
				return false;
			}
		});
	}

	private void dateOnClick() {

		if (caldroidFragment != null) {
			final CaldroidListener listener = new CaldroidListener() {
				@Override
				public void onSelectDate(Date date, View view) {
					System.err.println("single click date--->" + date);

					String str_date1 = formatter.format(date);

					AlertDialog.Builder alert = new AlertDialog.Builder(
							ParentDashBoard.this);
					alert.setTitle("Select date");
					alert.setMessage("" + str_date1);
					alert.setPositiveButton("OK", null);
					alert.show();

				}

				public void onLongClickDate(Date date, View view) {
					System.err.println("long click date--->" + date);
					// caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_blue,date);
					// caldroidFragment.refreshView();
				}

			};

			caldroidFragment.setCaldroidListener(listener);
		}

	}

	public void dateselected() {
		if (Util.isNetworkAvailable(ParentDashBoard.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_pin", tutorPrefs
					.getString("tutorID", "")));
			nameValuePairs.add(new BasicNameValuePair("password", tutorPrefs
					.getString("tutorpass", "")));

			Log.e("date select", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					ParentDashBoard.this, "login", nameValuePairs, true,
					"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) ParentDashBoard.this;
			mLogin.execute();
		} else {
			Util.alertMessage(ParentDashBoard.this,
					"Please check your internet connection");
		}

	}
	
	
	public void getbasicDetail() {
		if (Util.isNetworkAvailable(ParentDashBoard.this)) {

			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("parent_id", tutorPrefs.getString("parentID", "0")));
			//nameValuePairs.add(new BasicNameValuePair("password", tutorPrefs	.getString("tutorpass", "")));

			Log.e("date select", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					ParentDashBoard.this, "getbasicdetail-parent", nameValuePairs, dashboardProgress,"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) ParentDashBoard.this;
			mLogin.execute();
			dashboardProgress=false;
		} else {
			Util.alertMessage(ParentDashBoard.this,
					"Please check your internet connection");
		}

	}

	public void menuLayoutGone() {
		if (menuLayout.getVisibility() == View.VISIBLE) {
			menuLayout.setVisibility(View.GONE);
		}
	}

	private View.OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			if (v == newStudentLayout) {

				// ,myprofile_layout,myconnections_layouts,
				// parnetmerg_layout,merg_student_layout,payment_layout,credit_layout,logout_layout;
				

				Intent intent = new Intent(ParentDashBoard.this,NewsFeedActivity.class);
				startActivity(intent);
				menuLayoutGone();
				/*
				 * }else if(v == newLessonLayout){ Intent intent = new
				 * Intent(ParentDashBoard.this, AddLesson.class);
				 * //intent.putExtra("parentID", parentId);
				 * //intent.putExtra("trigger", "Parent");
				 * startActivity(intent); menuLayoutGone();
				 */

			} else if (v == connection_request_alout | v == connectionRequests) {
				Intent intent = new Intent(ParentDashBoard.this,
						ConnectionRequests.class);
				intent.putExtra("parentID", parentId);
				intent.putExtra("trigger", "Parent");
				menuLayoutGone();

				startActivity(intent);
			} else if (v == student_re_layout | v == studentRequests) {
				Intent intent = new Intent(ParentDashBoard.this,
						StudentRequestActivity.class);
				intent.putExtra("parentID", parentId);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == menuIcon) {
				if (menuLayout.getVisibility() == View.GONE)
					menuLayout.setVisibility(View.VISIBLE);
				else
					menuLayout.setVisibility(View.GONE);
			} else if (v == lesson_request_layout | v == lessonRequests) {
				Intent intent = new Intent(ParentDashBoard.this,
						LessonRequestActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == mylesson_layout | v == myLesson) {
				Intent intent = new Intent(ParentDashBoard.this,
						MyLessonActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == mystudent_layout | v == myStudent) {
				System.err.println("studentlist");
				Intent intent = new Intent(ParentDashBoard.this,
						MyStudentActivity.class);
				startActivity(intent);
				menuLayoutGone();

			} else if (v == myprofile_layout | v == myProfile) {
				Intent intent = new Intent(ParentDashBoard.this,
						MyProfileActivity.class);

				startActivity(intent);
				menuLayoutGone();
			} else if (v == myconnections_layouts | v == tuterList) {
				Intent intent = new Intent(ParentDashBoard.this,
						MyConnectionActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == parnetmerg_layout | v == parentmerge) {
				Intent intent = new Intent(ParentDashBoard.this,
						ParentMerge_Activity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == merg_student_layout | v == studentmerge) {
				Intent intent = new Intent(ParentDashBoard.this,
						MergeStudentActivity.class);
				startActivity(intent);
				menuLayoutGone();

			} else if (v == logout_layout | v == logout) {
				Editor editor = tutorPrefs.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(ParentDashBoard.this,HomeAcitivity.class);
				startActivity(intent);
				finish();
			} else if (v == payment_historyLayout) {// txt_credit

				Intent intent = new Intent(ParentDashBoard.this,
						Payment_Activity.class);

				intent.putExtra("check", "payment");

				startActivity(intent);

			} else if (v == credit_layout | v == txt_credit) {// txt_credit

				Intent intent = new Intent(ParentDashBoard.this,Payment_Activity.class);
				intent.putExtra("check", "credit");
				startActivity(intent);
				menuLayoutGone();

			} else if (v == addstudent_layout| v == addstudent) {
				
				SharedPreferences tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
				Editor editor = tutorPrefs.edit();
				editor.putString("name", "");
				editor.putString("email", "");
				editor.putString("contact", "");
				editor.putString("address", "");
				// editor.putString("parentID","0");
				editor.putString("gender", "male");
				editor.commit();
				Intent intent = new Intent(ParentDashBoard.this,AddStudent.class);
				intent.putExtra("trigger", "parent");
				intent.putExtra("parentID", parentId);
				startActivity(intent);
				menuLayoutGone();
			}
			else if (v == invoice_layout | v == invoice_ext) {
				Intent intent = new Intent(ParentDashBoard.this,InvoiceActivity.class);
				//intent.putExtra("check", "credit");
				startActivity(intent);
				menuLayoutGone();

			}
		}
	};

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		String result="1";
		arraybasicDetail=new ArrayList<Parent>();
		Parent parent=new Parent();
		if (methodName.equals("getbasicdetail-parent")) {

			Log.d("","outputoutput"+output);
			
			arraybasicDetail = parser.getParentBasicdetail(output);
			//adapter = new NewsFeedAdapter(NewsFeedActivity.this,getNewsfeed);;
			try {
				JSONObject jsonChildNode = new JSONObject(output);
				 result = jsonChildNode.getString("result").toString();
				
				if(result.equalsIgnoreCase("0"))
				{
					String fees_due= jsonChildNode.getString("fee_due").toString();
					String activeStudents= jsonChildNode.getString("no of active students").toString();
					
					
					
					
				
					
					
					String connectionRequest = jsonChildNode.getString("no of connection request").toString();
					try{
						int cannCount= Integer.parseInt(connectionRequest);
						if(cannCount==0){
							connectionRequests_count.setVisibility(View.GONE);
							connectionRequests_count.setPadding(7, 2, 7, 2);
						}
						else if(connectionRequest.length()==1)
						{
							connectionRequests_count.setPadding(7, 2, 7, 2);
							//TxtNotiCount.setPadding(left, top, right, bottom)
							connectionRequests_count.setText(""+cannCount);
							System.err.println("one");
							}
						else if(connectionRequest.length()==2)
						{
							connectionRequests_count.setText(""+cannCount);
							connectionRequests_count.setPadding(3, 2, 3, 2);
							System.err.println("two");
							}
						else 
						{
							connectionRequests_count.setPadding(2, 2, 1, 2);
							connectionRequests_count.setText("99+");
							connectionRequests_count.setTextSize(8);
						}
						
					}catch(Exception e){}
					
					String lessonRequest = jsonChildNode.getString("no of lesson request").toString();
					try{
						int lessonCount= Integer.parseInt(lessonRequest);
						if(lessonCount==0){
							lessonRequests_count.setVisibility(View.GONE);
							lessonRequests_count.setPadding(7, 2, 7, 2);
						}
						else if(lessonRequest.length()==1)
						{
							lessonRequests_count.setPadding(7, 2, 7, 2);
							//lessonRequests_count.setPadding(left, top, right, bottom)
							lessonRequests_count.setText(""+lessonCount);
							System.err.println("lesson one");
							}
						else if(lessonRequest.length()==2)
						{
							lessonRequests_count.setText(""+lessonCount);
							lessonRequests_count.setPadding(3, 2, 3, 2);
							System.err.println(" lesson two");
							}
						else 
						{
							System.err.println("lesson three");
							lessonRequests_count.setPadding(1, 2, 1, 2);
							lessonRequests_count.setText("99+");
							}
						
					}catch(Exception e){}
					
					
					String studentRequest= jsonChildNode.getString("no of student request").toString();
					try{
						int studentCount= Integer.parseInt(studentRequest);
						if(studentCount==0){
							studentRequests_count.setVisibility(View.GONE);
							studentRequests_count.setPadding(7, 2, 7, 2);
						}
						else if(connectionRequest.length()==1)
						{
							studentRequests_count.setPadding(7, 2, 7, 2);
							//lessonRequests_count.setPadding(left, top, right, bottom)
							studentRequests_count.setText(""+studentCount);
							System.err.println("one");
							}
						else if(connectionRequest.length()==2)
						{
							studentRequests_count.setText(""+studentCount);
							studentRequests_count.setPadding(3, 2, 3, 2);
							System.err.println("two");
							}
						else 
						{
							studentRequests_count.setPadding(1, 2, 1, 2);
							studentRequests_count.setText("99+");
						}
						
					}catch(Exception e){}
					
					}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			dbHandler.deleteLessonbooked();
			array_lessonbooked= parser.getLessonBooked(output);
			dbHandler.updateLessonBooked(array_lessonbooked);
			
			System.err.println(arraybasicDetail.size());
			Date date=null;
			for(int i=0;i<arraybasicDetail.size();i++)
			{
				parent=arraybasicDetail.get(i);
				String str_date=parent.getLesson_date();
				try {
					date = formatter.parse(str_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				String No_of_lessons=parent.getNo_of_lessons();
				System.err.println(No_of_lessons);
				String fullday=parent.getBlock_out_time_for_fullday();
				String halfday=parent.getBlock_out_time_for_halfday();
				int lesson=Integer.parseInt(No_of_lessons);
				if(lesson==1)
				{
					if(fullday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_1_full,date);
						
						}
					else if(halfday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_1_half,date);
						}
					else
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_1,date);
						}
					
					}
				else if(lesson==2)
				{
					if(fullday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_2_full,date);
						}
					else if(halfday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_2_half,date);
						}
					else
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_2,date);
						}
					
					
				}
				else if(lesson==3)
				{
					if(fullday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_3_full,date);
						}
					else if(halfday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_3_half,date);
						}
					else
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_3,date);
						}
					
				}
				else if(lesson>=4)
				{
					if(fullday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_4_full,date);
						System.err.println(date);
						}
					else if(halfday.equalsIgnoreCase("true"))
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_4_half,date);
						System.err.println(date);
						}
					else
					{
						caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_4,date);
						}
					
				}
			
			}
			caldroidFragment.refreshView();
		}
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.err.println("onresume");
		getbasicDetail();
	}
	private void changePasswordPrompt()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(ParentDashBoard.this);
		alert.setTitle("Tutor Helper");
		alert.setCancelable(false);
		alert.setMessage("Do you want to change password ?");
		alert.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0,
							int arg1) {
						
						Intent intent=new Intent(ParentDashBoard.this,MyProfileActivity.class);
						startActivity(intent);
						
						}
					
				});
		alert.setNegativeButton("No",null);
		alert.show();
	}
}
// 8.
