package com.equiworx.tutor;




import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Cancellation;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class CancellationActivity extends Activity implements AsyncResponseForTutorHelper {
	
	private SharedPreferences tutorPrefs;
	private String tutorId;
	private ArrayList<Cancellation> tutorrequest;
	private TutorHelperParser parser;
	private ListView listView;
	private TuturRequestAdpter tuturRequestAdpter;
	private ImageView back;
	private RelativeLayout back_layout;
	private TextView title;
	private String message="";
	private DateFormat timeformatter = new SimpleDateFormat("HH:mm:ss");  
 	private DateFormat showtimeformatter = new SimpleDateFormat("HH:mm"); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutor_request_activity);
		
		SetUI();
		fetchTutorRequestList();
		onClick();
	}

	private void onClick() {
		back.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		finish();
				
			}
		});
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
						
					}
				});
		
	}

	private void SetUI() {
		parser = new TutorHelperParser(CancellationActivity.this);
		listView = (ListView) findViewById(R.id.listview);
		title=(TextView)findViewById(R.id.title);
		title.setText("Cancellation Requests");
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tutorId = tutorPrefs.getString("tutorID", "0");
		System.err.println("tutorId"+tutorId);
		back=(ImageView)findViewById(R.id.back);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
	}

	private void fetchTutorRequestList() {
	
		if (Util.isNetworkAvailable(CancellationActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorId));
			Log.e("cancel", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					CancellationActivity.this, "fetch-lesson-cancellation",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) CancellationActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(CancellationActivity.this,"Please check your internet connection");
		}
	}



	public class TuturRequestAdpter extends BaseAdapter {
		private Context context;
		private TextView parentName;
		private TextView accept_request, reject_request;
		private Cancellation cancel;
		private String requestId; 

		private ArrayList<Cancellation> arraylist;

		// public ArrayList<Parent> mDisplayedValues;

		public TuturRequestAdpter(CancellationActivity tutorRequestActivity,
				ArrayList<Cancellation> tutorrequest) {
			// TODO Auto-generated constructor stub
			context = tutorRequestActivity;
			this.arraylist = tutorrequest;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup group) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.singlr_tutor_request_listview, group, false);
			}

			cancel = arraylist.get(position);

			parentName = (TextView) convertView.findViewById(R.id.name);
			accept_request = (TextView) convertView.findViewById(R.id.imageView_connect);
			reject_request = (TextView) convertView.findViewById(R.id.imageView_reject);

			TextView	description = (TextView)convertView.findViewById(R.id.description);
			TextView	sun = (TextView)convertView.findViewById(R.id.sun);
			TextView	mon = (TextView)convertView.findViewById(R.id.mon);
			TextView	tue = (TextView)convertView.findViewById(R.id.tue);
			TextView	wed = (TextView)convertView.findViewById(R.id.wed);
			TextView	thur = (TextView)convertView.findViewById(R.id.thur);
			TextView	fri = (TextView)convertView.findViewById(R.id.fri);
			TextView	sat = (TextView)convertView.findViewById(R.id.sat);
			TextView	etime = (TextView)convertView.findViewById(R.id.etime);

//				
			parentName.setText(cancel.getParent_name()+" has send you lesson cancellation request.");
			description.setText("Description:"+cancel.getLesson_description());
		
			String getdays=cancel.getLesson_days();
			System.err.println("day="+getdays);
		
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
			
		  	 String stime=cancel.getLesson_start_time();
		   	String endtime=cancel.getLesson_end_time();
		 	 Date sdate = null,edate=null;
		 		try {
		 			sdate = (Date)timeformatter.parse(stime);
		 			stime = showtimeformatter.format(sdate);
		 			
		 			edate = (Date)timeformatter.parse(endtime);
		 			endtime = showtimeformatter.format(edate);
		 		} catch (ParseException e) {
		 			e.printStackTrace();
		 		}
		 		
		 	
		 	etime.setText(stime +" - "+endtime);
		//	etime.setText(cancel.getLesson_start_time() +" - "+cancel.getLesson_end_time());
			TextView reason=(TextView)convertView.findViewById(R.id.reason);
			//time.setVisibility(View.GONE);
			reason.setText(cancel.getReason());
			reject_request.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					// TODO Auto-generated method stub
					requestId=cancel.getID();
					String status="Rejected";
					message="Request reject successfully";
					if (Util.isNetworkAvailable(CancellationActivity.this)) {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						   nameValuePairs.add(new BasicNameValuePair("request_id", requestId));
			                nameValuePairs.add(new BasicNameValuePair("status", status));
						Log.e("accpect cancel", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								CancellationActivity.this, "approve-cancellation-request",
								nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) CancellationActivity.this;
						mLogin.execute();
					} else {
						Util.alertMessage(CancellationActivity.this,"Please check your internet connection");
					}
				}
			});

			accept_request.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					// TODO Auto-generated method stub
					requestId=cancel.getID();
					String status="Approved";
					message="Request approved successfully";
					if (Util.isNetworkAvailable(CancellationActivity.this)) {
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						   nameValuePairs.add(new BasicNameValuePair("request_id", requestId));
			                nameValuePairs.add(new BasicNameValuePair("status", status));
						Log.e("accpect cancel", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(CancellationActivity.this, 
								"approve-cancellation-request",nameValuePairs, true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) CancellationActivity.this;
						mLogin.execute();
					} else {
						Util.alertMessage(CancellationActivity.this,"Please check your internet connection");
					}
				}
			});
			return convertView;
		}
	}
	

	@Override
	public void processFinish(String output, String methodName) {
		if (methodName.equals("fetch-lesson-cancellation")) {
			tutorrequest = new ArrayList<Cancellation>();
			tutorrequest = parser.getCancelRequestList(output);
			if(tutorrequest.size()==0)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(CancellationActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("no Cancellation requests");
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
				tuturRequestAdpter = new TuturRequestAdpter(CancellationActivity.this, tutorrequest);
				listView.setAdapter(tuturRequestAdpter);
			}
			
			

		}
		else if(methodName.equalsIgnoreCase("approve-cancellation-request"))
		{
			//fetchTutorRequestList();
			try {

				JSONObject jsonChildNode = new JSONObject(output);	
				
			String	result = jsonChildNode.getString("result").toString();
				//greatest_last_updated = jsonChildNode.getString("greatest_last_updated").toString();
			String	jsonmessage = jsonChildNode.getString("message").toString();
			if(result.equalsIgnoreCase("0"))
			{
				Log.e("lesson request", output);
				
				AlertDialog.Builder alert = new AlertDialog.Builder(CancellationActivity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage(message);
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
				Util.alertMessage(CancellationActivity.this, jsonmessage);
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
}


	



