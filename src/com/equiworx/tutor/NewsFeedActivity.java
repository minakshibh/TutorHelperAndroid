			package com.equiworx.tutor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.NewFeed;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewsFeedActivity extends Activity implements
		AsyncResponseForTutorHelper {
	public static ArrayList<NewFeed> getNewsfeed = new ArrayList<NewFeed>();
	private NewsFeedAdapter adapter;
	private ListView newsfeedList;
	private RelativeLayout back_layout;
	private SharedPreferences tutorPrefs;
	private String str_parentid;
	private TutorHelperParser parser;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mylesson);
		
		setUI();
		getResponce();

	}

	private void getResponce() {
		// TODO Auto-generated method stub
		if (Util.isNetworkAvailable(NewsFeedActivity.this)) {
			/*
			 * ParentId/TutorId Trigger -- Parent/Tutor
			 */// t//fetch-lessons-request.php

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("page_token", "0"));
			nameValuePairs.add(new BasicNameValuePair("user_id", str_parentid));
			// nameValuePairs.add(new BasicNameValuePair("trigger",
			// str_trigger));

			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					NewsFeedActivity.this, "fetch-news-feed", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = NewsFeedActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(NewsFeedActivity.this,
					"Please check your internet connection");
		}

	}

	private void setUI() {
		// TODO Auto-generated method stub
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser = new TutorHelperParser(NewsFeedActivity.this);
		title=(TextView)findViewById(R.id.title);
		title.setText("News Feed");
		newsfeedList = (ListView) findViewById(R.id.listView_mylesson);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);

		if (tutorPrefs.getString("mode", "").equalsIgnoreCase("parent")) {

			str_parentid = tutorPrefs.getString("parentID", "");
		} else if (tutorPrefs.getString("mode", "").equalsIgnoreCase("tutor")) {
			str_parentid = tutorPrefs.getString("tutorID", "");

		}
		
		back_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});	
	}

	public class NewsFeedAdapter extends BaseAdapter {
		private Context context;
		// private TextView tv_topic, ParentId,tv_note,tv_contactno;
		private NewFeed newfeed;
        private String part1,part2;
		// private List<Payment> parentList = null;
		private ArrayList<NewFeed> parentList;

		public NewsFeedAdapter(Context ctx, ArrayList<NewFeed> arraylist_payment) {
			context = ctx;
			this.parentList = arraylist_payment;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return parentList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return parentList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.activity_newsfeed_singleui, parent, false);
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));
			String getcuurentdate=dateFormat.format(cal.getTime());

			newfeed = parentList.get(position);
			TextView message_date = (TextView) convertView.findViewById(R.id.message_date);

			TextView message_name = (TextView) convertView.findViewById(R.id.message_name);
			message_name.setMovementMethod(new ScrollingMovementMethod());
			message_name.setText(newfeed.getMessage());
			
			String getDate=newfeed.getLast_updated();
			
			try {
				String[] parts = getDate.split(" ");
				 part1 = parts[0]; // 004
				 part2 = parts[1]; 
				System.out.println(part2);
				System.out.println(part1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		if (getcuurentdate.equalsIgnoreCase(getDate)) {
			
			message_date.setText(part2);
		}else{
			
		   message_date.setText(part1);
		}
			return convertView;
		}

	
	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		if (methodName.equals("fetch-news-feed")) {

			getNewsfeed = parser.getNewsFeed(output);
			adapter = new NewsFeedAdapter(NewsFeedActivity.this,getNewsfeed);;
			
			newsfeedList.setAdapter(adapter);
		}
	}
}
