package com.equiworx.tutor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
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
import com.equiworx.lesson.AddLessonActivity;
import com.equiworx.lesson.LessonRequestActivity;
import com.equiworx.lesson.MyLessonActivity;
import com.equiworx.model.LessonDetails;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.model.MyLesson;
import com.equiworx.model.Parent;
import com.equiworx.notification.NotificationTutorActivity;
import com.equiworx.parent.ConnectionRequests;
import com.equiworx.parent.MyProfileActivity;
import com.equiworx.student.AddStudent;
import com.equiworx.tutorhelper.HomeAcitivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class TutorDashboard extends FragmentActivity implements
		AsyncResponseForTutorHelper {

	public static ArrayList<MyLesson> arraylist_mylesson = new ArrayList<MyLesson>();
	private CaldroidFragment caldroidFragment;
	private Calendar calendar;
	private Date date33, date;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private SharedPreferences tutorPrefs;
	private TutorHelperParser parser;
	private LessonDetails lessonDetails;
	private ArrayList<MyLesson> Lessondetail;
	private RelativeLayout tutordashboard;
	private ImageView menuIcon, back;
	private TextView tv_title, activestudents, feesdue, feesCollected, tutorid,
			tutorname;
	private TextView connectionRequests, studentRequests, lessonRequests,
			myLesson, myProfile, tuterList, logout, mystudent, studentmerge,
			parentmerge, tutor_requests;
	private LinearLayout activeStudentsLayout, feesLayout, newStudentLayout,
			newLessonLayout;
	private LinearLayout menuLayout;
	private ArrayList<LessonDetails> arraylist_lessondetails = new ArrayList<LessonDetails>();
	private ArrayList<Parent> parentList = new ArrayList<Parent>();
	private ArrayList<Lesson_Booked> array_lessonbooked = new ArrayList<Lesson_Booked>();
	private String str_date;
	private String SelectedDate;
	private LinearLayout merge_layout, newsFeed_layout;
	private TutorHelperDatabaseHandler dbHandler;
	private LinearLayout can_request_layout, connection_request_alout,
			student_re_layout, lesson_request_layout, mystudent_layout,
			mylesson_layout, myprofile_layout, myconnections_layouts,
			merg_student_layout, credit_layout, logout_layout, addstudent,Pending_layout;
	private TextView connectionRequests_count,studentRequests_count,can_request_count,lessonRequests_count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_tutor_dashboard);

		initializeLayout();
		calenderInitialize();
		setClickListeners();
		dateOnClick();
		getnotification();
		getTutorDetails(true);

	}

	private void getnotification() {
		if (tutorPrefs.getString("message", "").equals("")) {

		} else {

			Intent intent = new Intent(TutorDashboard.this,
					NotificationTutorActivity.class);
			intent.putExtra("message", tutorPrefs.getString("message", ""));
			startActivity(intent);
		}
		Editor ed = tutorPrefs.edit();
		ed.putString("message", "");
		ed.commit();
	}

	private void initializeLayout() {
		parser = new TutorHelperParser(TutorDashboard.this);
		dbHandler = new TutorHelperDatabaseHandler(TutorDashboard.this);
		lessonDetails = new LessonDetails();

		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title = (TextView) findViewById(R.id.title);
		connection_request_alout = (LinearLayout) findViewById(R.id.connection_request_alout);
		student_re_layout = (LinearLayout) findViewById(R.id.student_re_layout);
		merge_layout = (LinearLayout) findViewById(R.id.parnetmerg_layout);
		merg_student_layout = (LinearLayout) findViewById(R.id.merg_student_layout);
		addstudent = (LinearLayout) findViewById(R.id.addstudent_layout);
		addstudent.setVisibility(View.GONE);
		credit_layout = (LinearLayout) findViewById(R.id.credit_layout);
		connection_request_alout.setVisibility(View.VISIBLE);
		student_re_layout.setVisibility(View.GONE);
		merge_layout.setVisibility(View.GONE);
		merg_student_layout.setVisibility(View.GONE);
		credit_layout.setVisibility(View.GONE);

		tv_title.setText("DashBoard");

		tutordashboard = (RelativeLayout) findViewById(R.id.tutordashboard);
		connectionRequests = (TextView) findViewById(R.id.connectionRequests);
		studentRequests = (TextView) findViewById(R.id.studentRequests);
		tutorid = (TextView) findViewById(R.id.id);
		tutorname = (TextView) findViewById(R.id.name);
		tutorid.setText(tutorPrefs.getString("tutorID", ""));
		System.out.print("tutpr name" + tutorPrefs.getString("tutorname", ""));
		tutorname.setText(tutorPrefs.getString("tutorname", ""));

		connectionRequests.setVisibility(View.VISIBLE);
		studentRequests.setVisibility(View.GONE);
		mystudent = (TextView) findViewById(R.id.mystu);
		mystudent.setVisibility(View.GONE);
		activestudents = (TextView) findViewById(R.id.activestudents);
		feesdue = (TextView) findViewById(R.id.feesdue);
		feesCollected = (TextView) findViewById(R.id.feesCollected);
		parentmerge = (TextView) findViewById(R.id.Parentmerge);
		parentmerge.setVisibility(View.GONE);
		studentmerge = (TextView) findViewById(R.id.Studentmerge);
		studentmerge.setVisibility(View.GONE);
		studentmerge.setOnClickListener(listener);
		parentmerge.setOnClickListener(listener);
		lessonRequests = (TextView) findViewById(R.id.lessonRequests);
		myLesson = (TextView) findViewById(R.id.myLesson);
		myProfile = (TextView) findViewById(R.id.myProfile);
		tuterList = (TextView) findViewById(R.id.tuterList);
		tuterList.setText("My Connections");
		
		activeStudentsLayout = (LinearLayout) findViewById(R.id.activeStudentsLayout);
		feesLayout = (LinearLayout) findViewById(R.id.feesLayout);
		newStudentLayout = (LinearLayout) findViewById(R.id.addStudentlayout);
		newLessonLayout = (LinearLayout) findViewById(R.id.addLessonLayout);
		newsFeed_layout = (LinearLayout) findViewById(R.id.newsFeed_layout);

		tutor_requests = (TextView) findViewById(R.id.can_request);

		menuIcon = (ImageView) findViewById(R.id.menuIcon);
		back = (ImageView) findViewById(R.id.back);
		back.setVisibility(View.INVISIBLE);
		menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
		logout = (TextView) findViewById(R.id.logOut);

		student_re_layout = (LinearLayout) findViewById(R.id.student_re_layout);
		lesson_request_layout = (LinearLayout) findViewById(R.id.lesson_request_layout);
		mystudent_layout = (LinearLayout) findViewById(R.id.mystudent_layout);
		mylesson_layout = (LinearLayout) findViewById(R.id.mylesson_layout);
		myprofile_layout = (LinearLayout) findViewById(R.id.myprofile_layout);
		myconnections_layouts = (LinearLayout) findViewById(R.id.myconnections_layouts);
		merg_student_layout = (LinearLayout) findViewById(R.id.merg_student_layout);
		can_request_layout = (LinearLayout) findViewById(R.id.can_request_layout);
		Pending_layout= (LinearLayout) findViewById(R.id.Pending_layout);
		Pending_layout.setVisibility(View.GONE);
		can_request_layout.setVisibility(View.VISIBLE);
		credit_layout.setVisibility(View.GONE);
		merg_student_layout.setVisibility(View.GONE);
		mystudent_layout.setVisibility(View.GONE);

		credit_layout = (LinearLayout) findViewById(R.id.credit_layout);
		logout_layout = (LinearLayout) findViewById(R.id.logout_layout);
		
		connectionRequests_count=(TextView)findViewById(R.id.connectionRequests_count);
		//connectionRequests_count.setVisibility(View.GONE);
		studentRequests_count=(TextView)findViewById(R.id.studentRequests_count);
		studentRequests_count.setVisibility(View.GONE);
		can_request_count=(TextView)findViewById(R.id.can_request_count);
		lessonRequests_count=(TextView)findViewById(R.id.lessonRequests_count);

	}

	private void calenderInitialize() {
		Bundle args = new Bundle();
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.clear();
		caldroidFragment = new CaldroidFragment(TutorDashboard.this);
		// caldroidFragment.getFragments().setBackgroundColor(Color.WHITE);
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

	}

	private void getTutorDetails(Boolean value) {

		if (Util.isNetworkAvailable(TutorDashboard.this)) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));

			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					TutorDashboard.this, "getbasicdetail", nameValuePairs,
					value, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) TutorDashboard.this;
			mLogin.execute();
		} else {
			Util.alertMessage(TutorDashboard.this,
					"Please check your internet connection");
		}

	}

	private void setClickListeners() {

		logout_layout.setOnClickListener(listener);
		can_request_layout.setOnClickListener(listener);
		myconnections_layouts.setOnClickListener(listener);
		activeStudentsLayout.setOnClickListener(listener);
		feesLayout.setOnClickListener(listener);
		newStudentLayout.setOnClickListener(listener);
		newLessonLayout.setOnClickListener(listener);
		lessonRequests.setOnClickListener(listener);
		myLesson.setOnClickListener(listener);
		myProfile.setOnClickListener(listener);
		tuterList.setOnClickListener(listener);
		menuIcon.setOnClickListener(listener);
		newsFeed_layout.setOnClickListener(listener);
		logout.setOnClickListener(listener);
		mylesson_layout.setOnClickListener(listener);
		lesson_request_layout.setOnClickListener(listener);
		tutor_requests.setOnClickListener(listener);
		myprofile_layout.setOnClickListener(listener);
		connectionRequests.setOnClickListener(listener);
		connection_request_alout.setOnClickListener(listener);
		tutordashboard.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				menuLayoutGone();
				return false;
			}
		});

	}

	private void dateOnClick() {

		if (caldroidFragment != null) {
			final CaldroidListener listener = new CaldroidListener() {
				public void onSelectDate(Date date, View view) {
					System.err.println("single click date--->" + date);
					SelectedDate = formatter.format(date);

					webLessonDetails();

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

	public void webLessonDetails() {
		if (Util.isNetworkAvailable(TutorDashboard.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("lesson_date",
					SelectedDate));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));
			// nameValuePairs.add(new
			// BasicNameValuePair("parent_id",tutorPrefs.getString("tutorpass",
			// "")));

			Log.e("get lesson detail", nameValuePairs.toString());// /get-lesson-detail.php
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					TutorDashboard.this, "get-lesson-detail", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) TutorDashboard.this;
			mLogin.execute();
		} else {
			Util.alertMessage(TutorDashboard.this,
					"Please check your internet connection");
		}
	}

	

	private void fetchlParentList(boolean b) {
		if (Util.isNetworkAvailable(TutorDashboard.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));
			Log.e("connection", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					TutorDashboard.this, "fetch-parent", nameValuePairs, b,
					"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) TutorDashboard.this;
			mLogin.execute();
		} else {
			Util.alertMessage(TutorDashboard.this,
					"Please check your internet connection");
		}

	}

	private View.OnClickListener listener = new View.OnClickListener() {
		public void onClick(View v) {
			if (v == activeStudentsLayout) {
				Intent intent = new Intent(TutorDashboard.this,	ParentListActivity.class);
				// intent.putExtra("title", "Student Management");
				Editor editor = tutorPrefs.edit();
				editor.putString("ptitle", "Student Management");
				editor.commit();
				startActivity(intent);
				menuLayoutGone();
			} else if (v == feesLayout) {
				Intent i = new Intent(TutorDashboard.this, HistoryDetails.class);
				startActivity(i);
				menuLayoutGone();
			} else if (v == newStudentLayout) {
				SharedPreferences tutorPrefs = getSharedPreferences(
						"tutor_prefs", MODE_PRIVATE);

				Editor editor = tutorPrefs.edit();
				editor.putString("name", "");
				editor.putString("email", "");
				editor.putString("contact", "");
				editor.putString("address", "");
				editor.putString("newparentID", "0");
				editor.commit();

				Intent intent = new Intent(TutorDashboard.this,
						AddStudent.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == newLessonLayout) {
				Intent intent = new Intent(TutorDashboard.this,
						AddLessonActivity.class);
				// intent.putExtra("TutorID", tutorid);
				startActivity(intent);
				finish();
				menuLayoutGone();
			} else if (v == menuIcon) {
				if (menuLayout.getVisibility() == View.GONE)
					menuLayout.setVisibility(View.VISIBLE);
				else
					menuLayout.setVisibility(View.GONE);
			} else if (v == lesson_request_layout | v == lessonRequests) {
				Intent intent = new Intent(TutorDashboard.this,
						LessonRequestActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == mylesson_layout | v == myLesson) {
				Intent intent = new Intent(TutorDashboard.this,
						MyLessonActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == myprofile_layout | v == myProfile) {
				Intent intent = new Intent(TutorDashboard.this,
						MyProfileActivity.class);
				startActivity(intent);
				menuLayoutGone();
			} else if (v == myconnections_layouts | v == tuterList) {
				Intent intent = new Intent(TutorDashboard.this,ParentListActivity.class);
				Editor editor = tutorPrefs.edit();
				editor.putString("ptitle", "My Connections");
				editor.commit();
				startActivity(intent);
				menuLayoutGone();
			} else if (v == can_request_layout | v == tutor_requests) {
				Intent intent = new Intent(TutorDashboard.this,
						CancellationActivity.class);
				startActivity(intent);
				menuLayoutGone();

			} else if (v == logout_layout | v == logout) {
				Editor editor = tutorPrefs.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(TutorDashboard.this,
						HomeAcitivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			} else if (v == newsFeed_layout) {

				Intent intent = new Intent(TutorDashboard.this,
						NewsFeedActivity.class);
				startActivity(intent);
			}
			else if (v == connectionRequests | v==connection_request_alout) {

				Intent intent = new Intent(TutorDashboard.this,ConnectionRequests.class);
				intent.putExtra("trigger", "Tutor");
				startActivity(intent);
				menuLayoutGone();
			}
			
		}
	};

	public void menuLayoutGone() {
		if (menuLayout.getVisibility() == View.VISIBLE) {
			menuLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void processFinish(String output, String methodName) {
		if (methodName.equals("getbasicdetail")) {

			Log.e("gettutor==", output);

			try {
				// TutorLesson tutorLesson=new TutorLesson();
				JSONObject jsonChildNode = new JSONObject(output);
				String str_activestudents = jsonChildNode.getString(
						"no of active students").toString();
				if (str_activestudents.equals("null")) {
					activestudents.setText("0");
				} else {
					activestudents.setText(str_activestudents);
				}

				String str_feesdue = jsonChildNode.getString("fee_due").toString();
				if (str_feesdue.equals("null")) {
					feesdue.setText("$ 0");
				} else {
					feesdue.setText("$ " + str_feesdue);
				}
				String c_fees = jsonChildNode.getString("fee_collected").toString();
				if (c_fees.equals("null")) {
					feesCollected.setText("$" + "0");
				} else {
					feesCollected.setText("$" + c_fees);
				}

			
				// for connection request count
				String connectionRequest = jsonChildNode.getString("no of connection request").toString();
				
				try{
					int canCount= Integer.parseInt(connectionRequest);
					if(canCount==0){
						connectionRequests_count.setVisibility(View.GONE);
						connectionRequests_count.setPadding(7, 2, 7, 2);
					}
					else if(connectionRequest.length()==1)
					{
						connectionRequests_count.setPadding(7, 2, 7, 2);
						//TxtNotiCount.setPadding(left, top, right, bottom)
						connectionRequests_count.setText(""+canCount);
						System.err.println("one");
						}
					else if(connectionRequest.length()==2)
					{
						connectionRequests_count.setText(""+canCount);
						connectionRequests_count.setPadding(3, 2, 3, 2);
						System.err.println("two");
						}
					else 
					{
						System.err.println("three");
						connectionRequests_count.setPadding(1, 2, 1, 2);
						connectionRequests_count.setText("99+");
						}
					
				}catch(Exception e){}
				
				// for cancellation request count
				String cancellationRequest = jsonChildNode.getString("no of cancellation request").toString();
				
				try{
					int canCount= Integer.parseInt(cancellationRequest);
					if(canCount==0){
						can_request_count.setVisibility(View.GONE);
						can_request_count.setPadding(7, 2, 7, 2);
					}
					else if(cancellationRequest.length()==1)
					{
						can_request_count.setPadding(7, 2, 7, 2);
						//TxtNotiCount.setPadding(left, top, right, bottom)
						can_request_count.setText(""+canCount);
						System.err.println("one");
						}
					else if(cancellationRequest.length()==2)
					{
						can_request_count.setText(""+canCount);
						can_request_count.setPadding(3, 2, 3, 2);
						System.err.println("two");
						}
					else 
					{
						can_request_count.setPadding(1, 2, 1, 2);
						can_request_count.setText("99+");
					}
					
				}catch(Exception e){}
				
				// for lesson request count
				String lessonRequest = jsonChildNode.getString("no of lesson request").toString();
				
				try{
					int lessonCount= Integer.parseInt(lessonRequest);
					if(lessonCount==0){
						lessonRequests_count.setVisibility(View.GONE);
						lessonRequests_count.setPadding(7, 2, 7, 2);
					}
					else if(cancellationRequest.length()==1)
					{
						lessonRequests_count.setPadding(7, 2, 7, 2);
						//TxtNotiCount.setPadding(left, top, right, bottom)
						lessonRequests_count.setText(""+lessonCount);
						System.err.println("one");
						}
					else if(cancellationRequest.length()==2)
					{
						lessonRequests_count.setText(""+lessonCount);
						lessonRequests_count.setPadding(3, 2, 3, 2);
						System.err.println("two");
						}
					else 
					{
						lessonRequests_count.setPadding(1, 2, 1, 2);
						lessonRequests_count.setText("99+");
					}
					
				}catch(Exception e){}
				
				
				//String c_fees = jsonChildNode.getString("fee_collected").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			arraylist_lessondetails = parser.getTutordetails(output);

			dbHandler.deleteLessonbooked();
			array_lessonbooked = parser.getLessonBooked(output);
			dbHandler.updateLessonBooked(array_lessonbooked);

			System.err.println(arraylist_lessondetails.size());
			for (int i = 0; i < arraylist_lessondetails.size(); i++) {
				lessonDetails = arraylist_lessondetails.get(i);
				String str_date = lessonDetails.getLesson_date();
				try {
					date = formatter.parse(str_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String No_of_lessons = lessonDetails.getNo_of_lessons();
				String fullday = lessonDetails.getBlock_out_time_for_fullday();
				String halfday = lessonDetails.getBlock_out_time_for_halfday();
				int lesson = Integer.parseInt(No_of_lessons);
				if (lesson == 1) {
					if (fullday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_1_full, date);

					} else if (halfday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_1_half, date);
					} else {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_1, date);
					}

				} else if (lesson == 2) {
					if (fullday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_2_full, date);
					} else if (halfday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_2_half, date);
					} else {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_2, date);
					}

				} else if (lesson == 3) {
					if (fullday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_3_full, date);
					} else if (halfday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_3_half, date);
					} else {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_3, date);
					}

				} else if (lesson >= 4) {
					if (fullday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_4_full, date);
						System.err.println(date);
					} else if (halfday.equalsIgnoreCase("true")) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_4_half, date);
						System.err.println(date);
					} else {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.circle_4, date);
					}

				}
				// 5) I was not able to test, but if there is any calendar
				// conflict, it needs to be alerted. ie, 2 lessons are scheduled
				// at the same time, but of a different topic. For our first
				// phase of the app, it is a single tutor, so they definitely
				// cannot split themselves into 2..
			}
			caldroidFragment.refreshView();
		} else if (methodName.equalsIgnoreCase("get-lesson-detail")) {

			Lessondetail = new ArrayList<MyLesson>();
			Lessondetail = parser.getMyLesson(output);
			if (Lessondetail.size() == 0) {
				Util.alertMessage(TutorDashboard.this,
						"No lessons for this date");
			} else {
				Intent intent = new Intent(TutorDashboard.this,
						MyLessonActivity.class);
				intent.putExtra("date", SelectedDate);
				startActivity(intent);

			}

		} else if (methodName.equalsIgnoreCase("fetch-parent")) {
			parentList = new ArrayList<Parent>();
			parentList = parser.getAllParentlist(output);
			dbHandler.deleteParentDetail();
			dbHandler.updateParentDetail(parentList);

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initializeLayout();
		getTutorDetails(false);
		fetchlParentList(false);

	}
}