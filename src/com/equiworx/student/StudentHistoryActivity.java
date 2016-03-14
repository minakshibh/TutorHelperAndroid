package com.equiworx.student;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.lesson.LessonDetailsActivity;
import com.equiworx.model.MyLesson;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StudentHistoryActivity extends Activity implements
		AsyncResponseForTutorHelper {

	private ListView listview;
	private ImageView back;
	private ArrayList<MyLesson> arraylist_history = new ArrayList<MyLesson>();
	private TextView title;
	private HistoryAdapter adapter;
	private TutorHelperParser parser;
	private MyLesson mylesson;
	private RelativeLayout back_layout;

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_studenthistory);

		initializelayout();
		fetchStudentHistory();
		onclickListenser();

	}

	private void initializelayout() {
		parser = new TutorHelperParser(StudentHistoryActivity.this);
		listview = (ListView) findViewById(R.id.listview);
		title = (TextView) findViewById(R.id.title);
		title.setText("Student History");
		back = (ImageView) findViewById(R.id.back);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
	}

	private void onclickListenser() {

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();

			}
		});
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				finish();

			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				mylesson = arraylist_history.get(position);
				Intent intent = new Intent(StudentHistoryActivity.this,
						LessonDetailsActivity.class);
				intent.putExtra("check", "yes");
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
				startActivity(intent);

			}
		});
	}

	private void fetchStudentHistory() {
		// fetch-student-history.php
		if (Util.isNetworkAvailable(StudentHistoryActivity.this)) {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("student_id", getIntent()
					.getStringExtra("studentid")));

			Log.e("student history", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					StudentHistoryActivity.this, "fetch-student-history",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) StudentHistoryActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(StudentHistoryActivity.this,
					"Please check your internet connection");
		}
	}

	public class HistoryAdapter extends BaseAdapter {
		private Context context;
		private MyLesson adapter_MyLesson;

		public HistoryAdapter(Context ctx) {
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist_history.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist_history.get(position);
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
				convertView = inflater.inflate(R.layout.lesson_row, parent,
						false);
			}

			adapter_MyLesson = arraylist_history.get(position);
			TextView approve = (TextView) convertView
					.findViewById(R.id.imageView_connect);
			approve.setVisibility(View.GONE);
			TextView reject = (TextView) convertView
					.findViewById(R.id.imageView_reject);
			reject.setVisibility(View.GONE);
			TextView time = (TextView) convertView.findViewById(R.id.time);
			// time.setVisibility(View.GONE);
			Button cancel = (Button) convertView.findViewById(R.id.cancel);
			cancel.setVisibility(View.GONE);

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

			//
			name.setText(adapter_MyLesson.getTutor_name()
					+ " has send you lesson request for ");
			name.setVisibility(View.GONE);
			description.setText("Description  : "
					+ adapter_MyLesson.getLessonDescription());
			description.setMaxLines(1);
			// fees.setText("Fees : "+myLesson.getFees());
			fees.setVisibility(View.GONE);
			// ArrayList<String> days=new ArrayList<String>();
			String getdays = adapter_MyLesson.getDays();
			System.err.println("day=" + getdays);
			if (getdays != null) {
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
			}
			// stime.setText(""+myLesson.getStartTime() +" - ");
			try {
				etime.setText(adapter_MyLesson.getStartTime().subSequence(0, 5)
						+ " - "
						+ adapter_MyLesson.getEndTime().subSequence(0, 5));
			} catch (Exception e) {
				e.printStackTrace();
			}
			TextView duration = (TextView) convertView
					.findViewById(R.id.duration);
			duration.setText("Duration : " + adapter_MyLesson.getDuration());
			time.setText("Date : " + adapter_MyLesson.getLessonDate() + " - "
					+ adapter_MyLesson.getLessonenddate());

			return convertView;
		}
	}

	@Override
	public void processFinish(String output, String methodName) {
		if (methodName.equalsIgnoreCase("fetch-student-history")) {
			arraylist_history = parser.getStudentHistory(output);

			if (arraylist_history.size() == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						StudentHistoryActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("no lesson attend by student yet now");
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								finish();
							}
						});
				alert.show();
			} else {

				adapter = new HistoryAdapter(StudentHistoryActivity.this);
				listview.setAdapter(adapter);
			}
		}
	}

}

/*
 * "result": 0, "message": "success", "lesson_data": [ { "lesson_id": "2",
 * "lesson_tutor_id": "Tnavu50", "tutor_name": null, "lesson_topic": "",
 * "lesson_description": "iphone", "lesson_start_time": "13:38:52",
 * "lesson_end_time": "15:38:52", "lesson_duration": "120", "lesson_days":
 * "Friday", "lesson_date": "2015-05-05", "end_date": "2015-06-05",
 * "lesson_is_recurring": "NO", "lesson_is_active": "true" }]
 */