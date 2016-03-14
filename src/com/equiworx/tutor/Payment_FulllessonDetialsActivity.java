package com.equiworx.tutor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
import com.equiworx.model.Statement;
import com.equiworx.model.StudentList;
import com.equiworx.student.StudentDetailActivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class Payment_FulllessonDetialsActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private TextView tv_title, tv_name, tv_description, tv_stime, tv_duration,
			tv_sdate, tv_edate, tv_recuring;
	private TextView sun, mon, tue, wed, thur, fri, sat, txt_pdf;
	private RelativeLayout back_layout;
	private ListView listView;
	private MyLessonAdapter adapter;
	private TutorHelperParser parser;
	private SharedPreferences tutorPrefs;
	public static ArrayList<Statement> report_detail = new ArrayList<Statement>();
	public static ArrayList<String> array_sessiondate;
	public static int totalfess = 0;
	public static int fees = 0;
	public static String description = "", sdate = "", edate = "", getdays;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lesson_details);

		intializelayout();
		setOnClickListners();
		getLessonDetail();

		tv_title.setText("Lesson Details");

		// getStudentsLayout();
	}

	private void intializelayout() {
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser = new TutorHelperParser(Payment_FulllessonDetialsActivity.this);

		txt_pdf = (TextView) findViewById(R.id.txt_pdf);
		tv_title = (TextView) findViewById(R.id.title);
		listView = (ListView) findViewById(R.id.listView1);

		tv_name = (TextView) findViewById(R.id.textView1);
		tv_description = (TextView) findViewById(R.id.textView2);
		tv_stime = (TextView) findViewById(R.id.textView3);

		tv_duration = (TextView) findViewById(R.id.textView5);

		tv_sdate = (TextView) findViewById(R.id.textView6);
		tv_edate = (TextView) findViewById(R.id.textView7);
		tv_recuring = (TextView) findViewById(R.id.textView8);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		Log.d("", "sizennnnnnnnnnnnnnnnnxxx="
				+ Payment_LessonsDetailsActivity.arraylist_studentlist.size());
		adapter = new MyLessonAdapter(Payment_FulllessonDetialsActivity.this,
				Payment_LessonsDetailsActivity.arraylist_studentlist);
		listView.setAdapter(adapter);

		sun = (TextView) findViewById(R.id.sun);
		mon = (TextView) findViewById(R.id.mon);
		tue = (TextView) findViewById(R.id.tue);
		wed = (TextView) findViewById(R.id.wed);
		thur = (TextView) findViewById(R.id.thur);
		fri = (TextView) findViewById(R.id.fri);
		sat = (TextView) findViewById(R.id.sat);

	}

	private void setOnClickListners() {
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		txt_pdf.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				fetchLesson();

			}
		});
		// listView.setc);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Payment_FulllessonDetialsActivity.this,StudentDetailActivity.class);
				
				i.putExtra("trigger", "parent");
				i.putExtra("name",
						Payment_LessonsDetailsActivity.arraylist_studentlist.get(arg2).getName());
				i.putExtra("address",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getAddress());
				i.putExtra("email",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getEmail());
				i.putExtra("phone",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getContactInfo());
				i.putExtra("fees",Payment_LessonsDetailsActivity.arraylist_studentlist.get(arg2).getStudentfee());
				i.putExtra("notes",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getNotes());
				i.putExtra("gender",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getGender());
				i.putExtra("parentid",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getParentId());
				i.putExtra("studentid",Payment_LessonsDetailsActivity.arraylist_studentlist
								.get(arg2).getStudentId());
				i.putExtra("isActiveInMonth",Payment_LessonsDetailsActivity.arraylist_studentlist
						.get(arg2).getIsActiveInMonth());
				i.putExtra("gender","Male");
				startActivity(i);
			}
		});
	}

	private void fetchLesson() {
		String year = "", str_month = "";

		try {
			year = getIntent().getStringExtra("sdate").substring(0, 4);
			if (tutorPrefs.getString("getLmonth", "").equalsIgnoreCase("")) {
				String month = getIntent().getStringExtra("sdate").substring(5,
						7);

				if (month.equalsIgnoreCase("01")) {
					str_month = "January";

				} else if (month.equalsIgnoreCase("02")) {
					str_month = "February";
				} else if (month.equalsIgnoreCase("03")) {
					str_month = "March";
				} else if (month.equalsIgnoreCase("04")) {
					str_month = "April";
				} else if (month.equalsIgnoreCase("05")) {
					str_month = "May";
				} else if (month.equalsIgnoreCase("06")) {
					str_month = "June";
				} else if (month.equalsIgnoreCase("07")) {
					str_month = "July";
				}

				else if (month.equalsIgnoreCase("08")) {
					str_month = "August";
				}

				else if (month.equalsIgnoreCase("09")) {
					str_month = "September";
				} else if (month.equalsIgnoreCase("10")) {
					str_month = "October";
				} else if (month.equalsIgnoreCase("11")) {
					str_month = "November";
				} else if (month.equalsIgnoreCase("12")) {
					str_month = "December";
				}
				System.err.println("if month=" + str_month);
			} else {
				str_month = tutorPrefs.getString("getLmonth", "");
				System.err.println("else month=" + str_month);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Util.isNetworkAvailable(Payment_FulllessonDetialsActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("month", str_month + "-"
					+ year));
			nameValuePairs.add(new BasicNameValuePair("lesson_ids", getIntent()
					.getStringExtra("lid")));

			Log.e("month-report", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					Payment_FulllessonDetialsActivity.this, "month-report",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) Payment_FulllessonDetialsActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(Payment_FulllessonDetialsActivity.this,
					"Please check your internet connection");
		}

	}

	private void getLessonDetail() {

		tv_name.setText("" + getIntent().getStringExtra("tname"));

		description = getIntent().getStringExtra("description");
		tv_description.setText("" + description);
		try {
			tv_stime.setText(""
					+ getIntent().getStringExtra("stime").subSequence(0, 5)
					+ " - "
					+ getIntent().getStringExtra("etime").subSequence(0, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String value = getIntent().getStringExtra("duration");

		if (value.equalsIgnoreCase("0")) {
			tv_duration.setText("" + value + " hour");

		} else if (value.equalsIgnoreCase("1")) {

			tv_duration.setText("" + value + " hour");

		} else {

			tv_duration.setText("" + value + " hours");

		}

		// tv_duration.setText(String.valueOf(value)+" hours");
		sdate = getIntent().getStringExtra("sdate");
		tv_sdate.setText(sdate);
		edate = getIntent().getStringExtra("edate");
		tv_edate.setText(edate);

		tv_recuring.setText(getIntent().getStringExtra("recuring"));

		getIntent().getStringExtra("tid");
		getIntent().getStringExtra("lid");

		getdays = getIntent().getStringExtra("day");
		System.err.println("day=" + getdays);
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

	public class MyLessonAdapter extends BaseAdapter {
		private Context context;

		private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		private ArrayList<StudentList> arrayLesson = new ArrayList<StudentList>();

		public MyLessonAdapter(Context ctx,
				ArrayList<StudentList> arraylist_mylesson) {
			context = ctx;
			arrayLesson = arraylist_mylesson;
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.studentlist_row,
						parent, false);
			}

			TextView lbl_notes = (TextView) convertView
					.findViewById(R.id.lbl_notes);
			lbl_notes.setVisibility(View.GONE);
			lbl_notes.setText("Address");
			TextView lbl_address = (TextView) convertView
					.findViewById(R.id.lbl_address);
			lbl_address.setVisibility(View.VISIBLE);
			TextView student_fees_lebl = (TextView) convertView
					.findViewById(R.id.student_fees_text);
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.studentname);
			TextView tv_email = (TextView) convertView.findViewById(R.id.Email);
			TextView tv_note = (TextView) convertView.findViewById(R.id.Notes);
			TextView tv_contactno = (TextView) convertView
					.findViewById(R.id.contactno);

			Log.d("",
					"sizennnnnnnnnnnnnnnnnxxx"
							+ Payment_LessonsDetailsActivity.arraylist_studentlist
									.size());
			ImageView img_view = (ImageView) convertView
					.findViewById(R.id.editImg);
			img_view.setVisibility(View.GONE);
			student_fees_lebl.setVisibility(View.VISIBLE);
			// tv_contactno.setText(": "+studentlist.getContactInfo());
			TextView student_fees_text = (TextView) convertView
					.findViewById(R.id.outstandingBalnce);
			student_fees_text.setVisibility(View.VISIBLE);
			tv_name.setText(arrayLesson.get(position).getName());
			tv_email.setText(": " + arrayLesson.get(position).getEmail());
			tv_note.setText(": " + arrayLesson.get(position).getAddress());
			tv_contactno.setText(": " + arrayLesson.get(position).getContactInfo());
			if (arrayLesson.get(position).getStudentfee()
					.equalsIgnoreCase("null")) {
				student_fees_text.setText(": $0");
			} else {
				student_fees_text.setText(": $"
						+ arrayLesson.get(position).getStudentfee());

			}

			return convertView;
		}
	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		fees = 0;
		totalfess = 0;
		if (methodName.equals("month-report")) {

			report_detail = parser.getReportdetails(output);
			for (int i = 0; i < report_detail.size(); i++) {
				String str = report_detail.get(i).getSession_dates();
				array_sessiondate = new ArrayList(Arrays.asList(str.split(",")));
				for (int j = 0; j < array_sessiondate.size(); j++) {
					System.out.println(" -->" + array_sessiondate.get(j));
				}
			}

			for (int k = 0; k < Payment_LessonsDetailsActivity.arraylist_studentlist
					.size(); k++) {

				try {

					if(Payment_LessonsDetailsActivity.arraylist_studentlist
							.get(k).getStudentfee()!=null)
					{
					fees = Integer
							.parseInt(Payment_LessonsDetailsActivity.arraylist_studentlist
									.get(k).getStudentfee());
					}
					totalfess = totalfess + fees;
					System.err.println(totalfess);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// String dates=report_detail.get(index)
			Intent i = new Intent(Payment_FulllessonDetialsActivity.this,
					InvoiceActivity.class);
			startActivity(i);
		}
	}

}