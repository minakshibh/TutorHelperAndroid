package com.equiworx.lesson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Lesson;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

@SuppressLint("ResourceAsColor")
public class LessonRequestActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private ArrayList<Lesson> arraylist_lesson = new ArrayList<Lesson>();
	private LessonAdapter adapter;
	private ListView listView;
	private TextView tv_title;
	private String str_trigger = "", str_parentid = "", str_tutorid = "";
	private TutorHelperParser parser;
	private SharedPreferences tutorPrefs;
	private String message,LessionId,lessonDes,startTime,Startdate;
	private RelativeLayout back_layout;
	private AlarmManagerBroadcastReceiver alarm;
	long startTime1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lessonrequest);
		alarm = new AlarmManagerBroadcastReceiver();
		initailiselayout();
		onClickListeners();
		fetchlLessonList();
	}

	private void onClickListeners() {
		// TODO Auto-generated method stub
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initailiselayout() {
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title = (TextView) findViewById(R.id.title);
		tv_title.setText("Lesson Requests");
		listView = (ListView) findViewById(R.id.listView_lesson);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		parser = new TutorHelperParser(LessonRequestActivity.this);

	}

	private void fetchlLessonList() {
		if (Util.isNetworkAvailable(LessonRequestActivity.this)) {
			/*
			 * ParentId/TutorId Trigger -- Parent/Tutor
			 */// t//fetch-lessons-request.php

			if (tutorPrefs.getString("mode", "").equals("parent")) {
				str_trigger = "Parent";
				str_parentid = tutorPrefs.getString("parentID", "");
				str_tutorid = "";
			} else if (tutorPrefs.getString("mode", "").equals("tutor")) {
				str_trigger = "Tutor";
				str_parentid = "";
				str_tutorid = tutorPrefs.getString("tutorID", "");

			}

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("parent_id", str_parentid));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", str_tutorid));
			nameValuePairs.add(new BasicNameValuePair("trigger", str_trigger));
			nameValuePairs.add(new BasicNameValuePair("type", "ForMe"));
			Log.e("lesson", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					LessonRequestActivity.this, "fetch-lessons-request",nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) LessonRequestActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(LessonRequestActivity.this,
					"Please check your internet connection");
		}

	}

	public class LessonAdapter extends BaseAdapter {
		private Context context;
		// private TextView tv_topic, ParentId,tv_note,tv_contactno;
		private DateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");
		private DateFormat showtimeformatter = new SimpleDateFormat("HH:mm");
		// CheckBox checkbox;
		// private ImageView call,email;
		private Lesson lesson;

		public LessonAdapter(Context ctx) {
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist_lesson.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist_lesson.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView,ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.lesson_row, parent,
						false);
			}

			

			TextView approve = (TextView) convertView.findViewById(R.id.imageView_connect);
			TextView reject = (TextView) convertView.findViewById(R.id.imageView_reject);
			TextView studentname_new=(TextView) convertView.findViewById(R.id.studentname_new);
			
			TextView name = (TextView) convertView.findViewById(R.id.name);
			TextView description = (TextView) convertView
					.findViewById(R.id.description);
			TextView fees = (TextView) convertView.findViewById(R.id.fees);
			TextView sun = (TextView) convertView.findViewById(R.id.sun);
			TextView mon = (TextView) convertView.findViewById(R.id.mon);
			TextView tue = (TextView) convertView.findViewById(R.id.tue);
			TextView wed = (TextView) convertView.findViewById(R.id.wed);
			TextView thur = (TextView) convertView.findViewById(R.id.thur);
			TextView fri = (TextView) convertView.findViewById(R.id.fri);
			TextView sat = (TextView) convertView.findViewById(R.id.sat);
			// TextView stime = (TextView)convertView.findViewById(R.id.stime);
			TextView etime = (TextView) convertView.findViewById(R.id.etime);

			lesson = arraylist_lesson.get(position);
			//
			name.setText(lesson.getTutername()+ " has send you lesson request for "+ lesson.getStudentname());
			description.setText(": " + lesson.getLessonDescription());
			fees.setText("Fees : $" + lesson.getFees());

			// ArrayList<String> days=new ArrayList<String>();
			String getdays = lesson.getLessonDays();
			System.err.println("day=" + getdays);
			/*
			 * try{ getdays.split(","); } catch(Exception e) {
			 * e.printStackTrace(); }
			 */
			if (getdays.contains("Sunday")) {
				sun.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				sun.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Monday")) {
				mon.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				mon.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Tuesday")) {
				tue.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				tue.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Wednesday")) {
				wed.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				wed.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Thursday")) {
				thur.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				thur.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Saturday")) {
				sat.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				sat.setTextColor(getResources().getColor(R.color.gray));
			}
			if (getdays.contains("Friday")) {
				fri.setTextColor(getResources().getColor(R.color.appBlue));
			} else {
				fri.setTextColor(getResources().getColor(R.color.gray));
			}

			/*
			 * String stime=lesson.getLessonStartTime(); String
			 * endtime=lesson.getLessonEndTime(); Date sdate = null,edate=null;
			 */
			try {
				etime.setText(": "+lesson.getLessonStartTime().subSequence(0, 5)
						+ " - " + lesson.getLessonEndTime().substring(0, 5));
				/*
				 * sdate = (Date)timeformatter.parse(stime); stime =
				 * showtimeformatter.format(sdate); edate =
				 * (Date)timeformatter.parse(endtime); endtime =
				 * showtimeformatter.format(edate);
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
			// etime.setText(stime +" - "+endtime);

			LinearLayout time = (LinearLayout) convertView.findViewById(R.id.lay_time);
			time.setVisibility(View.GONE);
			LinearLayout namestu = (LinearLayout) convertView.findViewById(R.id.lay_studentnames);
			namestu.setVisibility(View.VISIBLE);
			TextView duration = (TextView) convertView.findViewById(R.id.duration);
			//duration.setText("Duration :" + lesson.getLessonDuration());
			if (lesson.getLessonDuration().equalsIgnoreCase("0") ) {
				duration.setText(": "+lesson.getLessonDuration()+" hour");
				
			}else if (lesson.getLessonDuration().equalsIgnoreCase("1")) {
				
				duration.setText(": "+lesson.getLessonDuration()+" hour");
				
			}else{
				
				duration.setText(": "+lesson.getLessonDuration()+" hours");
				
			}
			studentname_new.setText("Date");
			TextView studentnames = (TextView) convertView.findViewById(R.id.studentnames);
			studentnames.setText(":" + lesson.getLessonDate() + "-"+ lesson.getEnddate());

			approve.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					message = "Request accepted successfully";
					LessionId=lesson.getLessonId();
					lessonDes=lesson.getLessonDescription();
					startTime=lesson.getLessonStartTime();
					Startdate=lesson.getLessonDate();
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("request_id",	 arraylist_lesson.get(position).getRequestId()));
					nameValuePairs.add(new BasicNameValuePair("trigger",str_trigger));
					nameValuePairs.add(new BasicNameValuePair("response","Accepted"));
					Log.e("approve", nameValuePairs.toString());
					AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(LessonRequestActivity.this,"accept-lesson-request", nameValuePairs, true,
							"Please wait...");
					mLogin.delegate = (AsyncResponseForTutorHelper) LessonRequestActivity.this;
					mLogin.execute();

				}
			});
			reject.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					message = "Request rejected successfully";
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("request_id",	arraylist_lesson.get(position).getRequestId()));
					nameValuePairs.add(new BasicNameValuePair("trigger",str_trigger));
					nameValuePairs.add(new BasicNameValuePair("response","Rejected"));	
					Log.e("approve", nameValuePairs.toString());
					AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(LessonRequestActivity.this,
							"accept-lesson-request", nameValuePairs, true,	"Please wait...");
					mLogin.delegate = (AsyncResponseForTutorHelper) LessonRequestActivity.this;
					mLogin.execute();
				}
			});

			return convertView;
		}
	}

	public void processFinish(String output, String methodName) {
		Log.e("fetch lesson=", output);
		if (methodName.equals("fetch-lessons-request")) {

			arraylist_lesson = parser.getLesson(output);
			if (arraylist_lesson.size() == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(LessonRequestActivity.this);
				alert.setTitle("Tutor Helper");
				
				alert.setMessage("no lesson requests");
				alert.setCancelable(false);
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								finish();
							}
						});
				alert.show();
			} else {
				adapter = new LessonAdapter(LessonRequestActivity.this);
				listView.setAdapter(adapter);
			}
		}
		// accept-lesson-request
		else if (methodName.equals("accept-lesson-request")) {
			System.err.println(output);
			try {

				JSONObject jsonChildNode = new JSONObject(output);
				Log.d("","outputoutput"+output);
				Log.d("","jsonChildNodejsonChildNodejsonChildNode"+jsonChildNode);
				String result = jsonChildNode.getString("result").toString();
				// greatest_last_updated =
				// jsonChildNode.getString("greatest_last_updated").toString();
				String jsonmessage = jsonChildNode.getString("message").toString();
				if (message.equalsIgnoreCase("Request accepted successfully")) {
					String getDate=startTime;
					 String hour = null,min = null,month = null,years = null,dayohmonth = null;
					try {
						String[] parts = getDate.split(":");
						hour = parts[0]; // 004
						min = parts[1]; 
						System.out.println(hour);
						System.out.println(min);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
						String[] parts1 = Startdate.split("-");
						years = parts1[0]; // 004
						month = parts1[1];
						dayohmonth = parts1[2];
						System.out.println("years"+years);
						System.out.println("month"+month);
						System.out.println("day"+dayohmonth);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
//					int minute = 0;
//					int hourOfDay = 0; 
//					
//					Calendar calNow = Calendar.getInstance();
//					Calendar calSet = (Calendar) calNow.clone();
//					calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
//					calSet.set(Calendar.MINUTE, minute);
//					calSet.set(Calendar.SECOND, 0);
//					calSet.set(Calendar.MILLISECOND, 0);
//
//					if (calSet.compareTo(calNow) <= 0) {
//						// Today Set time passed, count to tomorrow
//						calSet.add(Calendar.DATE, 1);
//					}
//					Long curenttime=calSet.getTimeInMillis();
					
					System.out.println("years"+years);
					System.out.println("month"+month);
					System.out.println("day"+dayohmonth);
					Calendar calendar = Calendar.getInstance();
					calendar.set(Integer.parseInt(years),Integer.parseInt(month), Integer.parseInt(dayohmonth),Integer.parseInt(hour),Integer.parseInt(min) , 0);
					 startTime1 = calendar.getTimeInMillis();
					//if (startTime1 > curenttime) {
						
					//}else{
						
						
					//}
						
				}
				if (result.equalsIgnoreCase("0")) {
					Log.e("lesson request", output);
					AlertDialog.Builder alert = new AlertDialog.Builder(LessonRequestActivity.this);
					alert.setTitle("Tutor Helper");
					alert.setMessage("Your lesson "+message);
					alert.setCancelable(false);
					alert.setPositiveButton("ok",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									Intent intent = new Intent(LessonRequestActivity.this,AlarmManagerBroadcastReceiver.class);
									intent.putExtra("item_name", LessionId);
									intent.putExtra("message", lessonDes);
									intent.putExtra("item_id", LessionId);
									intent.putExtra("activityToTrigg","com.equiworx.lesson.LessonRequestActivity");
									System.out.append("notification is fired XXXXXXXXXXXXXXXX");
									PendingIntent mAlarmSender;
									mAlarmSender = PendingIntent.getBroadcast(LessonRequestActivity.this, 0, intent, 0);
									AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
									am.set(AlarmManager.RTC_WAKEUP,startTime1 + 5000, mAlarmSender);
									listView.setAdapter(null);
									adapter = new LessonAdapter(LessonRequestActivity.this);
									listView.invalidateViews();
									listView.setAdapter(adapter);
									finish();
								}
							});
					alert.show();
				} else {
					Util.alertMessage(LessonRequestActivity.this, jsonmessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
