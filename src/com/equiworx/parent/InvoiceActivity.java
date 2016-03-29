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
import com.equiworx.model.InvoiceModel;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class InvoiceActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private ListView invoiceList;
	private String ParentId;
	private SharedPreferences tutorPrefs;
	private TutorHelperParser parser;
	private ArrayList<InvoiceModel> invoiceArrayList;
	private RelativeLayout back_layout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_invoice);

		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		ParentId = tutorPrefs.getString("parentID", "");

		setUI();
		setOnClickListener();
		getInvoiceDetails();

	}

	private void getInvoiceDetails() {
		// TODO Auto-generated method stub
		if (Util.isNetworkAvailable(InvoiceActivity.this)) {
			/*
			 * ParentId/TutorId Trigger -- Parent/Tutor
			 */// t//fetch-lessons-request.php

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("pid", ParentId));

			Log.e("generateInvoice", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					InvoiceActivity.this, "generateInvoice", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = InvoiceActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(InvoiceActivity.this,
					"Please check your internet connection");
		}

	}

	public void setUI() {
		// TODO Auto-generated method stub
		parser = new TutorHelperParser(InvoiceActivity.this);
		invoiceList = (ListView) findViewById(R.id.invoiceList);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
	}

	public void setOnClickListener() {
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
		InvoiceAdapter adapter = new InvoiceAdapter(this, invoiceArrayList);
		invoiceList.setAdapter(adapter);
	}

	// adapter class for invoice..
	public class InvoiceAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, yearname_text, txtInvoiveUrl;
		private ArrayList<InvoiceModel> arrayList = new ArrayList<InvoiceModel>();

		public InvoiceAdapter(Context ctx,
				ArrayList<InvoiceModel> invoiceArrayList) {
			context = ctx;
			this.arrayList = invoiceArrayList;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("", "sizexxx" + arrayList.size());

			return arrayList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method st

			return arrayList.get(position);
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
						R.layout.template_invoice_details, group, false);
			}
		
			LinearLayout myLayout = (LinearLayout)convertView.findViewById(R.id.lay_row);
			/* if (convertView == null) {
	                if (getItemViewType(position) == 0) {
	                    convertView = LayoutInflater.from(context).inflate(R.layout.template_item_selected_asset_header, parent, false);
	                    TextView tv = (TextView) convertView.findViewById(R.id.selected_assets_item_header_textview);

	                    sectionHolder = new SectionHolder(tv);
	                    convertView.setTag(sectionHolder);
	                }
	                else {
	                    convertView = LayoutInflater.from(context).inflate(R.layout.template_item_selected_asset, parent, false);
	                    TextView tv1 = (TextView) convertView.findViewById(R.id.selected_assets_item_cell_textview1);
	                    TextView tv2 = (TextView) convertView.findViewById(R.id.selected_assets_item_cell_textview2);

	                    rowHolder = new RowHolder(tv1, tv2);
	                    convertView.setTag(rowHolder);
	                }
	            }*/
			InvoiceModel invoiceModel = arrayList.get(position);

			
			yearname_text = (TextView) convertView.findViewById(R.id.yearname_text);
		   yearname_text.setText(invoiceModel.getYearName());
			System.err.println("size="+invoiceModel.getArrayList().size());
			for(int i=0;i<invoiceModel.getArrayList().size();i++)
			{
				
                View hiddenInfo = getLayoutInflater().inflate(R.layout.row_invoicemonth, myLayout, false);
                monthName = (TextView)hiddenInfo.findViewById(R.id.txtMonthName);
            	txtInvoiveUrl = (TextView)hiddenInfo.findViewById(R.id.txtInvoiveUrl);
				monthName.setText(invoiceModel.getArrayList().get(i).getMonth());
				txtInvoiveUrl.setText(invoiceModel.getArrayList().get(i).getInvoicelink());
				myLayout.addView(hiddenInfo);
				}
			

			return convertView;
			
		}

	}
}
/*TextView tv = new TextView(InvoiceActivity.this);
tv.setText(invoiceModel.getArrayList().get(i).getMonth());
tv.setPadding(10, 10, 10, 10);
tv.setTextSize(20);*/