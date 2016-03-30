package com.equiworx.parent;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.InvoiceDetail;
import com.equiworx.model.InvoiceModel;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class InvoiceActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private ExpandableListView invoiceList;
	private String ParentId;
	private SharedPreferences tutorPrefs;
	private TutorHelperParser parser;
	private ArrayList<InvoiceModel> invoiceArrayList;
	private RelativeLayout back_layout;
	private boolean setValue=true;
	private ArrayList<InvoiceDetail> childinvoiceArrayList;
	private TextView title;

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
		title=(TextView)findViewById(R.id.title);
		title.setText("Invoice");
		invoiceList = (ExpandableListView)findViewById(R.id.invoiceList);
		invoiceList.setGroupIndicator(null);
		
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
		//InvoiceAdapter adapter = new InvoiceAdapter(this, invoiceArrayList);
		//invoiceList.setAdapter(adapter);
		 ArrayList<InvoiceModel> groups = invoiceArrayList;
		    final CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this, groups);
		    invoiceList.setAdapter(adapter);
		  
	
	}
	public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
		 
	    private Context mContext;
	    private ArrayList<InvoiceModel>  mGroups;
	    private LayoutInflater mInflater;
	 
	    public CustomExpandableListAdapter(Context context, ArrayList<InvoiceModel> groups) {
	        mContext = context;
	        mGroups = groups;
	        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    @Override
	    public int getGroupCount() {
	        return mGroups.size();
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return mGroups.get(groupPosition).arrayList.size();
	    }
	 
	    @Override
	    public Object getGroup(int groupPosition) {
	        return mGroups.get(groupPosition);
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return mGroups.get(groupPosition).arrayList.get(childPosition);
	    }
	 
	    @Override
	    public long getGroupId(int groupPosition) {
	        return 0;
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return 0;
	    }
	 
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	 
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	 
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.template_invoice_details, null);
	        }
	 
	        // Get the group item
	        InvoiceModel group = (InvoiceModel) getGroup(groupPosition);
	 
	        // Set group name
	        TextView textView = (TextView) convertView.findViewById(R.id.yearname_text);
	        textView.setText(invoiceArrayList.get(groupPosition).getYearName());
	        invoiceList.expandGroup(groupPosition);
	        return convertView;
	    }
	 
	    @Override
	    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	 
	 
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.row_invoicemonth, null);
	        }
	 
	        // Get child name
	      //  String children = (String) getChild(groupPosition, childPosition);
	 
	        // Set child name
	        TextView text = (TextView) convertView.findViewById(R.id.txtMonthName);
	        text.setText(invoiceArrayList.get(groupPosition).getArrayList().get(childPosition).getMonth());
	 
	        TextView text2 = (TextView) convertView.findViewById(R.id.txtInvoiveUrl);
	        text2.setText(invoiceArrayList.get(groupPosition).getArrayList().get(childPosition).getInvoicelink());
	        text2.setOnClickListener(new View.OnClickListener() {
		    	@Override
				public void onClick(View v) {
		    		if(!invoiceArrayList.get(groupPosition).getArrayList().get(childPosition).getInvoicelink().equals(""))
		    		{
			    		Intent intent=new Intent(InvoiceActivity.this,InvoicePDFActivity.class);
						System.err.println("sss"+invoiceArrayList.get(groupPosition).getArrayList().get(childPosition).getInvoicelink());
						intent.putExtra("url",invoiceArrayList.get(groupPosition).getArrayList().get(childPosition).getInvoicelink());
						startActivity(intent);
		    		}
					
				}
			});
	        /*convertView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Toast.makeText(mContext, children, Toast.LENGTH_SHORT).show();
	            }
	        });*/
	 
	        return convertView;
	    }
	 
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	}
}
/*
	// adapter class for invoice..
	public class InvoiceAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, yearname_text, txtInvoiveUrl;
		private ArrayList<InvoiceModel> arrayList = new ArrayList<InvoiceModel>();
		int i=0;
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
			

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.template_invoice_details, group, false);
			
		
			LinearLayout myLayout = (LinearLayout)convertView.findViewById(R.id.lay_row);
			 if (convertView == null) {
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
	            }
			final InvoiceModel invoiceModel = arrayList.get(position);

			
			yearname_text = (TextView) convertView.findViewById(R.id.yearname_text);
		   yearname_text.setText(invoiceModel.getYearName());
			System.err.println("size="+invoiceModel.getArrayList().size());
			
			
			for(i=0;i<invoiceModel.getArrayList().size();i++)
				{
					
	                View hiddenInfo = getLayoutInflater().inflate(R.layout.row_invoicemonth, myLayout, false);
	                monthName = (TextView)hiddenInfo.findViewById(R.id.txtMonthName);
	            	txtInvoiveUrl = (TextView)hiddenInfo.findViewById(R.id.txtInvoiveUrl);
					monthName.setText(invoiceModel.getArrayList().get(i).getMonth());
					txtInvoiveUrl.setText(invoiceModel.getArrayList().get(i).getInvoicelink());
					txtInvoiveUrl.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(InvoiceActivity.this,InvoicePDFActivity.class);
								System.err.println("sss"+invoiceModel.getArrayList().get(i-1).getInvoicelink());
								intent.putExtra("url",(invoiceModel.getArrayList().get(i-1).getInvoicelink()));
								startActivity(intent);
							}
						});
					myLayout.addView(hiddenInfo);
					}
			
				
			return convertView;
			}
			else
			{
				return convertView;
				}
			
		}

	}
}*/
/*TextView tv = new TextView(InvoiceActivity.this);
tv.setText(invoiceModel.getArrayList().get(i).getMonth());
tv.setPadding(10, 10, 10, 10);
tv.setTextSize(20);*/