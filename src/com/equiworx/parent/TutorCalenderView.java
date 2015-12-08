package com.equiworx.parent;

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
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.LessonDetails;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.tutor.TutorDashboard;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


public class TutorCalenderView extends FragmentActivity implements AsyncResponseForTutorHelper {
	
	private CaldroidFragment caldroidFragment;
	private Date date33;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	LinearLayout calendar_layout;
	ArrayList<String> value = new ArrayList<String>();
	ImageView back;
	ArrayList<Lesson_Booked> array_lessonbooked=new ArrayList<Lesson_Booked>();
	int timeslot=0;
	TextView title;
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_tutorcalendarview);

	initializelayout();

	onclickListenser();
	
	calenderInitialize();
	getTutorDetails(true);
	dateOnClick();
}

	

	private void onclickListenser() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}



	private void initializelayout() {
		title=(TextView)findViewById(R.id.title);
		title.setText("Tutors Calendar");
		calendar_layout=(LinearLayout)findViewById(R.id.calendar1);
		back=(ImageView)findViewById(R.id.back);
	}
	private void calenderInitialize() {
		Bundle args = new Bundle();
	Calendar	calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.clear();
        caldroidFragment = new CaldroidFragment(TutorCalenderView.this);
        //caldroidFragment.getFragments().setBackgroundColor(Color.WHITE);
        Calendar currentdate = Calendar.getInstance();
		String str_date = formatter.format(currentdate.getTime());
		try {
    		  date33=formatter.parse(str_date);
    		 
  			} 
    	  catch (ParseException e) 
			{
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
		
		if (Util.isNetworkAvailable(TutorCalenderView.this)){
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_id", getIntent().getStringExtra("tutorid")));
				
			Log.e("getbasicdetail",nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(TutorCalenderView.this, "getbasicdetail", nameValuePairs, value, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) TutorCalenderView.this;
			mLogin.execute();
		}else {
			Util.alertMessage(TutorCalenderView.this,"Please check your internet connection");
		}
		
	}
	private void dateOnClick() {
		
		if (caldroidFragment != null)
		{
			final CaldroidListener listener = new CaldroidListener()
			  {
				public void onSelectDate(Date date, View view) {
					
					
				String	SelectedDate = formatter.format(date);
				String caldate=SelectedDate.replace("-", "");
				value.clear();
				for(int i=0;i<array_lessonbooked.size();i++)
				{
					String getdate=array_lessonbooked.get(i).getDate().toString().replace("-", "");
				
										
					
					if(caldate.equalsIgnoreCase(getdate))
					{
						timeslot=1;
						System.err.println("getdate"+getdate);
						System.err.println("caldate"+caldate);
						try{
						value.add(""+array_lessonbooked.get(i).getStart_timing().substring(0, 5) +" - "+ array_lessonbooked.get(i).getEnd_timing().substring(0, 5)); 
						}
					catch(Exception e)
					{}
					}
					else
					{
						//timeslot=0;
						}
				}
				TimeslotShow();	
					}
				public void onLongClickDate(Date date, View view)
					{
						System.err.println("long click date--->"+date);
						//caldroidFragment.setBackgroundResourceForDate(R.drawable.circle_blue,date);
						//caldroidFragment.refreshView();	
					}
		
			 };
				
			  	caldroidFragment.setCaldroidListener(listener);
		}
		
	}
	
public void TimeslotShow()
{
	 
	if(timeslot==1)
	{
		timeslot=0;	
		final AlertDialog.Builder builder = new AlertDialog.Builder(TutorCalenderView.this);
			LayoutInflater inflator = (TutorCalenderView.this).getLayoutInflater();
			final View couponView = inflator.inflate(R.layout.timeslot_layout, null);
			builder.setView(couponView);
			final AlertDialog dialog1 =builder.create();
			dialog1.setTitle("Booked Time Slots");
			dialog1.setCancelable(false);
			//dialog1.setMessage("coupon code");
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		        lp.width = LayoutParams.MATCH_PARENT;
		        lp.height = LayoutParams.MATCH_PARENT;
		        dialog1.getWindow().setAttributes(lp); 
			Button btnCheck = (Button) couponView.findViewById(R.id.button_ok);
			btnCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					// TODO Auto-generated method stub
				
				dialog1.dismiss();
				}
			});
			ListView listview = (ListView) couponView.findViewById(R.id.listview);
			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, value);
			 listview.setAdapter(adapter);
			/*Button withoutCheck = (Button) couponView.findViewById(R.id.continueWithoutCoupon);
			withoutCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					// TODO Auto-generated method stub
			
				dialog1.dismiss();
				}
			});*/
			dialog1.show();
}
	else
	{
		Util.alertMessage(TutorCalenderView.this, "No lessons for this date");
	}
		
	
}





	@Override
	public void processFinish(String output, String methodName) {
		if(methodName.equals("getbasicdetail"))
		{
		Date date=null;
		ArrayList<LessonDetails>	arraylist_lessondetails=new ArrayList<LessonDetails>();
	
		LessonDetails lessonDetails=new LessonDetails();
		TutorHelperParser parser=new TutorHelperParser(TutorCalenderView.this);
		Log.e("gettutor==", output);
			
		/*	try {
				//TutorLesson tutorLesson=new TutorLesson();
				JSONObject jsonChildNode = new JSONObject(output);	
				String str_activestudents=jsonChildNode.getString("no of active students").toString();*/
			/*	if(str_activestudents.equals("null"))
				{
					activestudents.setText("0");
					}
				else
				{
					activestudents.setText(str_activestudents);
					}
			
				String str_feesdue=jsonChildNode.getString("fee_due").toString();
				if(str_feesdue.equals("null"))
				{
					feesdue.setText("$ 0");
					}
				else
				{
					feesdue.setText("$ "+str_feesdue);
					}
				String c_fees=jsonChildNode.getString("fee_collected").toString();
				if(c_fees.equals("null"))
				{
					feesCollected.setText("$"+"0");
					}
				else
				{
					feesCollected.setText("$"+c_fees);
					}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}*/
			//value.clear();
			array_lessonbooked.clear();
			arraylist_lessondetails = parser.getTutordetails(output);
			array_lessonbooked= parser.getLessonBooked(output);
			
			/*dbHandler.deleteLessonbooked();
			array_lessonbooked= parser.getLessonBooked(output);
			dbHandler.updateLessonBooked(array_lessonbooked);*/
			
			System.err.println(arraylist_lessondetails.size());
			for(int i=0;i<arraylist_lessondetails.size();i++)
			{
				lessonDetails=arraylist_lessondetails.get(i);
				String str_date=lessonDetails.getLesson_date();
				try {
					date = formatter.parse(str_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				String No_of_lessons=lessonDetails.getNo_of_lessons();
				String fullday=lessonDetails.getBlock_out_time_for_fullday();
				String halfday=lessonDetails.getBlock_out_time_for_halfday();
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
				//5) I was not able to test, but if there is any calendar conflict, it needs to be alerted. ie, 2 lessons are scheduled at the same time, but of a different topic. For our first phase of the app, it is a single tutor, so they definitely cannot split themselves into 2..
			}
			caldroidFragment.refreshView();
		}
		
	}
}
