package com.equiworx.tutor;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.lesson.Payment_Activity;
import com.equiworx.model.FeeDetail;
import com.equiworx.model.FeesDetail;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import com.equiworx.util.YearNode;

public class MakePayment_SelectParentActivity extends Activity implements
		AsyncResponseForTutorHelper, OnClickListener, OnItemSelectedListener {

	private SharedPreferences tutorPrefs;
	private String parentId;
	private LinearLayout navigation_bar;
	private ArrayList<FeeDetail> historyDetail;
	private TutorHelperParser parser;
	private ListView listview;
	private HistoryDetailAdapter adapter;
	private LinearLayout lay_payment, lay_addcredit, lay_statement;
	private RelativeLayout back_layout;
	private SharedPreferences pref;
	private TextView tv_feescollect, tv_feedue, tv_outbal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_details);
		
		
		
	}

	

	private void setUI() {
		// TODO Auto-generated method stub
		parser = new TutorHelperParser(MakePayment_SelectParentActivity.this);
		parentId = getIntent().getStringExtra("parentId");
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		navigation_bar = (LinearLayout) findViewById(R.id.navigation_bar);
		lay_payment = (LinearLayout) findViewById(R.id.payment);
		lay_payment.setOnClickListener(this);
		lay_addcredit = (LinearLayout) findViewById(R.id.addcredit);
		lay_statement = (LinearLayout) findViewById(R.id.lay_statement);
		lay_addcredit.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.listview);
		tv_feescollect = (TextView) findViewById(R.id.fee_collected);
		tv_feedue = (TextView) findViewById(R.id.fee_due);
		tv_outbal = (TextView) findViewById(R.id.outstng_bal);
		navigation_bar.setVisibility(View.GONE);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);

		Boolean Value = true;
		getTutorDetails(Value);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				final FeeDetail i = historyDetail.get(position);
				FeesDetail ei = (FeesDetail) i;
				String lessionIds = ei.getLessonIds();
				Editor ed = tutorPrefs.edit();
				// ed.putString("getLyear", getyear);
				ed.putString("getLmonth", "");
				ed.commit();
				Intent intent = new Intent(
						MakePayment_SelectParentActivity.this,
						Payment_LessonsDetailsActivity.class);
				intent.putExtra("getLessonIds", lessionIds);
				startActivity(intent);

			}
		});
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lay_statement.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MakePayment_SelectParentActivity.this,
						StatementActivity.class);
				startActivity(i);
			}
		});
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	setUI();
}
	private void getTutorDetails(Boolean value) {

		if (Util.isNetworkAvailable(MakePayment_SelectParentActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));
			Log.d("", "tutor id" + tutorPrefs.getString("tutorID", ""));
			Log.d("", "parentId" + parentId);
			nameValuePairs.add(new BasicNameValuePair("parent_id", parentId));
			nameValuePairs.add(new BasicNameValuePair("trigger", "ByParent"));

			String nam = nameValuePairs.toString();
			System.err.println(nam);
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MakePayment_SelectParentActivity.this,
					"fetch-payment-history", nameValuePairs, true,
					"Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MakePayment_SelectParentActivity.this;
			mLogin.execute();

		} else {
			Util.alertMessage(MakePayment_SelectParentActivity.this,
					"Please check your internet connection");
		}

	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		Log.d("", "outputbalwinderrr" + output);

		if (methodName.equals("fetch-payment-history")) {
			historyDetail = new ArrayList<FeeDetail>();

			Log.d("", "outputbalwinderrr" + output);

			historyDetail = parser.getPaymentHistory(output);

			Log.d("", "sizeeeeeeeeeeeeeeeeeeeeee=" + historyDetail.size());
			pref = getApplicationContext().getSharedPreferences("MyPref", 0);
			String totalefeedue = pref.getString("totalFeeDue", "");
			String totalFeesCollected = pref
					.getString("totalFeesCollected", "");
			String totalOutstandingBalance = pref.getString(
					"totalOutstandingBalance", "");

			try {
				if (!totalefeedue.isEmpty()) {

					tv_feedue.setText(": $" + totalefeedue);

				} else {

					tv_feedue.setText(": $" + "0");

				}
				if (!totalFeesCollected.isEmpty()) {
					tv_feescollect.setText(": $" + totalFeesCollected);
				} else {

					tv_feescollect.setText(": $" + "0");
				}
				if (!totalOutstandingBalance.isEmpty()) {
					tv_outbal.setText(": $" + totalOutstandingBalance);
				} else {

					tv_outbal.setText(": $" + "0");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			adapter = new HistoryDetailAdapter(
					MakePayment_SelectParentActivity.this, historyDetail);
			listview.setAdapter(adapter);

		}
	}

	public class HistoryDetailAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, noLession, feeDue, outstandingBalance,
				feeCollect, outstnding_fess_text, yearname_text;
		private List<FeeDetail> parentList = null;
		private LinearLayout yearname_layout, month_layout;

		// public ArrayList<Parent> mDisplayedValues;

		public HistoryDetailAdapter(Context ctx,
				ArrayList<FeeDetail> historyDetail) {
			context = ctx;
			this.parentList = historyDetail;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return parentList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return parentList.get(position);
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
						R.layout.single_history_detaile_activity, group, false);
			}
			month_layout = (LinearLayout) convertView
					.findViewById(R.id.month_layout);
			yearname_layout = (LinearLayout) convertView
					.findViewById(R.id.yearname_layout);

			FeeDetail i = parentList.get(position);
			if (i != null) {
				if (i.isSection()) {
					YearNode si = (YearNode) i;

					yearname_layout.setOnClickListener(null);
					yearname_layout.setOnLongClickListener(null);
					yearname_layout.setLongClickable(false);
					month_layout.setVisibility(View.GONE);
					yearname_layout.setVisibility(View.VISIBLE);
					yearname_text = (TextView) convertView
							.findViewById(R.id.yearname_text);
					yearname_text.setText(si.getYear());
				} else {
					FeesDetail ei = (FeesDetail) i;
					yearname_layout.setVisibility(View.GONE);
					month_layout.setVisibility(View.VISIBLE);
					monthName = (TextView) convertView
							.findViewById(R.id.studentname);
					noLession = (TextView) convertView
							.findViewById(R.id.noLesson);
					outstnding_fess_text = (TextView) convertView
							.findViewById(R.id.outstnding_fess_text);
					feeDue = (TextView) convertView.findViewById(R.id.dueFess);
					feeCollect = (TextView) convertView
							.findViewById(R.id.feescollect);
					outstandingBalance = (TextView) convertView
							.findViewById(R.id.outstandingBalnce);
					outstnding_fess_text.setText("$" + ei.getFee_outstanding());
					monthName.setText(ei.getMonthName());
					noLession.setText(ei.getNoOfLessons());
					feeDue.setText("$" + ei.getFeeDue());
					feeCollect.setText("$" + ei.getFeeCollected());
					outstandingBalance
							.setText("$" + ei.getOutstandingBalance());
				}
			}
			return convertView;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.payment:
			Intent intent = new Intent(MakePayment_SelectParentActivity.this,
					Payment_Activity.class);
			// intent.putExtra("AddCredit", "PayFees");
			intent.putExtra("check", "payment");
			startActivity(intent);
			break;

		case R.id.addcredit:
			Intent intent1 = new Intent(MakePayment_SelectParentActivity.this,
					Payment_Activity.class);
			// intent1.putExtra("AddCredit", "AddCredit");
			intent1.putExtra("check", "credit");
			startActivity(intent1);

		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
