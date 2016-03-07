package com.equiworx.parent;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.FeesDetail;
import com.equiworx.model.InvoiceModel;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class InvoiceActivity extends Activity implements AsyncResponseForTutorHelper {
	private ListView invoiceList;
	private InvoiceAdapter adapter;
	private String ParentId;
	private SharedPreferences tutorPrefs;
	private TutorHelperParser parser;
	private InvoiceModel invoice;
	private ArrayList<InvoiceModel> invoiceArrayList;
	private RelativeLayout back_layout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_invoice);
		
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		ParentId=tutorPrefs.getString("parentID", "");
		
	    setUI();
	    setOnClickListener();
	    getInvoiceDetails();
		
	}

	private void getInvoiceDetails() {
		// TODO Auto-generated method stub
		if (Util.isNetworkAvailable(InvoiceActivity.this)){
			/*ParentId/TutorId
			Trigger -- Parent/Tutor*///t//fetch-lessons-request.php
	
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("pid", ParentId));
						
			Log.e("generateInvoice", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(InvoiceActivity.this, "generateInvoice", nameValuePairs, true, "Please wait...");
			mLogin.delegate = InvoiceActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(InvoiceActivity.this,"Please check your internet connection");
		}
		
	}

	public void setUI() {
		// TODO Auto-generated method stub
		parser = new TutorHelperParser(InvoiceActivity.this);
		invoiceList = (ListView)findViewById(R.id.invoiceList);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
	}
	public void setOnClickListener()
	{
		back_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		invoiceArrayList = parser.getInvoiceDetails(output);
		InvoiceAdapter adapter = new  InvoiceAdapter(this,invoiceArrayList);
		invoiceList.setAdapter(adapter);
	}

	//adapter class for invoice..
	public class InvoiceAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, yearname_text, txtInvoiveUrl, outstandingBalance;
		private FeesDetail historyNode;
		private ArrayList<InvoiceModel> parentList = new ArrayList<InvoiceModel>();
		private LinearLayout yearname_layout;
		private String byMonth;
		private LinearLayout month_layout;

		public InvoiceAdapter(Context ctx,
				ArrayList<InvoiceModel> invoiceArrayList) {
			context = ctx;
			this.parentList = invoiceArrayList;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("", "sizexxx" + parentList.size());

			return parentList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method st
			
		
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
				convertView = inflater.inflate(R.layout.template_invoice_details, group, false);
			}
			month_layout = (LinearLayout) convertView.findViewById(R.id.month_layout);
			yearname_layout = (LinearLayout) convertView.findViewById(R.id.yearname_layout);
			
			InvoiceModel ei = parentList.get(position);
						
			monthName = (TextView) convertView.findViewById(R.id.txtMonthName);
			yearname_text = (TextView) convertView.findViewById(R.id.yearname_text);
			txtInvoiveUrl = (TextView) convertView.findViewById(R.id.txtInvoiveUrl);
			
			
			yearname_text.setText(ei.getYearName());
			monthName.setText(ei.getMonthName());
			txtInvoiveUrl.setText(ei.getInvoiceUrl());
			
			return convertView;
		}

	}
}
