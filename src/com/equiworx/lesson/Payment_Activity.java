package com.equiworx.lesson;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Payment;
import com.equiworx.tutor.AddCreditsActivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class Payment_Activity  extends Activity implements AsyncResponseForTutorHelper{
	
	private ListView listView;
	private TextView tv_title,total_credit;
	private ImageView back,rightbutton;
	private TutorHelperParser parser;
	private SharedPreferences tutorPrefs;
	private String str_trigger="",str_parentid="",str_tutorid="";
	public static ArrayList<Payment> arraylist_payment=new ArrayList<Payment>();
	public static ArrayList<Payment> arraylist_credit=new ArrayList<Payment>();
	private PaymentAdapter adapter;
	private CreditAdapter credit_adapter;
	private String  check="";
	private Payment payment;
	private RelativeLayout menu_button,back_layout;
	private EditText autocomp_txtview;
	private LinearLayout layout_credit;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_paymentdetails);
		
		initailiselayout();
		onClickListeners();
		
		
	}

	private void initailiselayout() {
		payment=new Payment();
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		tv_title=(TextView)findViewById(R.id.title);
		total_credit=(TextView)findViewById(R.id.total_credit);
		check=getIntent().getStringExtra("check");
		
		listView=(ListView)findViewById(R.id.listView_lesson);
		back=(ImageView)findViewById(R.id.back);
		parser=new TutorHelperParser(Payment_Activity.this);
		menu_button=(RelativeLayout)findViewById(R.id.menu_button);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		menu_button.setVisibility(View.VISIBLE);
		layout_credit=(LinearLayout)findViewById(R.id.layout_credit);
		rightbutton=(ImageView)findViewById(R.id.rightmenu);
		autocomp_txtview=(EditText)findViewById(R.id.editText_search);
		autocomp_txtview.setVisibility(View.GONE);
		rightbutton.setImageResource(R.drawable.add_icon);
		
		if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent"))
		{
			str_trigger="parent";
			str_parentid=tutorPrefs.getString("parentID", "");
			str_tutorid="";
			rightbutton.setVisibility(View.GONE);
			layout_credit.setVisibility(View.VISIBLE);
			menu_button.setVisibility(View.GONE);
			}
		else if(tutorPrefs.getString("mode", "").equalsIgnoreCase("tutor"))
		{
			str_trigger="tutor";
			str_parentid="";
			str_tutorid=tutorPrefs.getString("tutorID","");
			rightbutton.setVisibility(View.VISIBLE);
			menu_button.setVisibility(View.VISIBLE);
			layout_credit.setVisibility(View.GONE);
			
			}
		
		if(check.equalsIgnoreCase("payment"))
		{
			tv_title.setText("Payment");
			//autocomp_txtview.setVisibility(View.VISIBLE);
			searchParent();
			}
		else
		{
			tv_title.setText("Credit");
			//autocomp_txtview.setVisibility(View.GONE);
			searchCredit();
			}
		
		back_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	
	}

	public void searchParent()
	{
	    
		autocomp_txtview.addTextChangedListener(new TextWatcher() {
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		//	adapter.getFilter().filter(s.toString());
			String text = autocomp_txtview.getText().toString().toLowerCase();
			if (arraylist_payment.size()>0) {
				adapter.filter(text);
			}else{
				
				//adapter.filter(null);
				
			}
			
			}
		public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		public void afterTextChanged(Editable s) {
	
			}
		});
	}
		public void searchCredit()
		{
		    
			autocomp_txtview.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			//	adapter.getFilter().filter(s.toString());
				String text = autocomp_txtview.getText().toString().toLowerCase();
				if (arraylist_credit.size()>0) {
					credit_adapter.filter(text);
				}else{
					
					//credit_adapter.filter(null);
				}
				
				}
			public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
				}
			public void afterTextChanged(Editable s) {
		
				}
			});
}
	private void fetchpayment() {
		if (Util.isNetworkAvailable(Payment_Activity.this)){
			/*ParentId/TutorId
			Trigger -- Parent/Tutor*///t//fetch-lessons-request.php
	
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("parent_id", str_parentid));
			nameValuePairs.add(new BasicNameValuePair("tutor_id", str_tutorid));
			nameValuePairs.add(new BasicNameValuePair("trigger", str_trigger));
			
			Log.e("transaction-detail", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(Payment_Activity.this, "fetch-transaction-detail", nameValuePairs, true, "Please wait...");
			mLogin.delegate = Payment_Activity.this;
			mLogin.execute();
		}else {
			
			Util.alertMessage(Payment_Activity.this,"Please check your internet connection");
		}
		
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	fetchpayment();
}
	private void onClickListeners() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		menu_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(check.equalsIgnoreCase("payment"))
				{
					Intent intent=new Intent(Payment_Activity.this,AddCreditsActivity.class);
					intent.putExtra("AddCredit", "PayFees");
					startActivity(intent);
					
				}
				else
				{
			
					Intent intent1=new Intent(Payment_Activity.this,AddCreditsActivity.class);
					intent1.putExtra("AddCredit", "AddCredit");
					startActivity(intent1);
					}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
					if(check.equalsIgnoreCase("payment"))
					{
						payment = arraylist_payment.get(position);
						Intent intent=new Intent(Payment_Activity.this,PaymentdetailsActivity.class);
						//Editor ed=tutorPrefs.edit();
						intent.putExtra("p_id", payment.getParent_id());
						intent.putExtra("p_name", payment.getParent_name());
						intent.putExtra("t_name", payment.getTutor_name());
						intent.putExtra("t_id", payment.getTutor_id());
						intent.putExtra("ID", payment.getID());
						intent.putExtra("fees", payment.getFee_paid());
						intent.putExtra("remarks", payment.getRemarks());
						intent.putExtra("paymentmode", payment.getPayment_mode());
						intent.putExtra("lastupdate", payment.getLast_updated());
						intent.putExtra("check", "payment");
						//ed.commit();
						startActivity(intent);
						
						}
					else
					{
						payment = arraylist_credit.get(position);	
						Intent intent=new Intent(Payment_Activity.this,PaymentdetailsActivity.class);
						//Editor ed=tutorPrefs.edit();
						intent.putExtra("p_id", payment.getParent_id());
						intent.putExtra("p_name", payment.getParent_name());
						intent.putExtra("t_name", payment.getTutor_name());
						intent.putExtra("t_id", payment.getTutor_id());
						intent.putExtra("ID", payment.getID());
						intent.putExtra("fees", payment.getFee_paid());
						intent.putExtra("remarks", payment.getRemarks());
						intent.putExtra("paymentmode", payment.getPayment_mode());
						intent.putExtra("lastupdate", payment.getLast_updated());
						intent.putExtra("check", "credit");
						//ed.commit();
						startActivity(intent);
					
						}
					
				}
			});
	}
	public class PaymentAdapter extends BaseAdapter
	{			
		private Context context;
		//private TextView tv_topic, ParentId,tv_note,tv_contactno;
		private Payment lesson;
		
		private List<Payment> parentList=null;
		private ArrayList<Payment> arraylist;
		public PaymentAdapter(Context ctx, List<Payment> arraylist_payment)
		{
			context = ctx;
			this.parentList = arraylist_payment;
			this.arraylist = new ArrayList<Payment>();
			this.arraylist.addAll(parentList);
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null){
			    convertView = inflater.inflate(R.layout.payment_row, parent, false);
			}
			
			lesson = parentList.get(position);
			TextView panme=(TextView)convertView.findViewById(R.id.p_name);
		
			TextView tname=(TextView)convertView.findViewById(R.id.date);
			tname.setText("Date : "+lesson.getLast_updated());
			TextView fees=(TextView)convertView.findViewById(R.id.fees);
			Log.d("","getFee_paid"+lesson.getFee_paid());
			Log.d("","getLast_updated"+lesson.getLast_updated());
			fees.setText("$ "+lesson.getFee_paid()+"");
			
			if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent"))
			{
				panme.setText(""+lesson.getTutor_name());
			}else{
				
				panme.setText(""+lesson.getParent_name());
			}
			
			
			
			return convertView;
		}
		
		
		// Filter function
				public void filter(String charText) {
					charText = charText.toLowerCase();
					parentList.clear();
					if (charText.length() == 0) {
						parentList.addAll(arraylist);
					} else {
						for (Payment wp : arraylist) {
							if (wp.getTutor_name().toLowerCase() //search by parent name
									.contains(charText))
							{
								parentList.add(wp);
							}
							else if(wp.getTutor_id().toLowerCase() //search by parent id
									.contains(charText))
							{
								parentList.add(wp);
							}
						}
					}
					notifyDataSetChanged();
				}
	}
	
	public class CreditAdapter extends BaseAdapter
	{			
		private Context context;
		//private TextView tv_topic, ParentId,tv_note,tv_contactno;
		private Payment lesson;
		private List<Payment> parentList=null;
		private ArrayList<Payment> arraylist;
		public CreditAdapter(Context ctx, List<Payment> arraylist_credit)
		{
			context = ctx;
			this.parentList = arraylist_credit;
			this.arraylist = new ArrayList<Payment>();
			this.arraylist.addAll(parentList);
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null){
			    convertView = inflater.inflate(R.layout.payment_row, parent, false);
			}

			lesson = parentList.get(position);
		
			TextView panme=(TextView)convertView.findViewById(R.id.p_name);
			panme.setText(""+lesson.getParent_name());
			TextView tname=(TextView)convertView.findViewById(R.id.date);
			tname.setText("Date : "+lesson.getLast_updated());
			TextView fees=(TextView)convertView.findViewById(R.id.fees);
			fees.setText("$ "+lesson.getFee_paid()+"");

			if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent"))
			{
				panme.setText(""+lesson.getTutor_name());
			}else{
				
				panme.setText(""+lesson.getParent_name());
			}
			return convertView;
		}
		
		// Filter function
		public void filter(String charText) {
			charText = charText.toLowerCase();
			parentList.clear();
			if (charText.length() == 0) {
				parentList.addAll(arraylist);
			} else {
				for (Payment wp : arraylist) {
					if (wp.getTutor_name().toLowerCase() //search by parent name
							.contains(charText))
					{
						parentList.add(wp);
					}
					else if(wp.getTutor_id().toLowerCase() //search by parent id
							.contains(charText))
					{
						parentList.add(wp);
					}
				}
			}
			notifyDataSetChanged();
		}
	}
	
	@Override
	public void processFinish(String output, String methodName) {
		
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			String result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			String message = jsonChildNode.getString("message").toString();
			String totalcredit = jsonChildNode.getString("totalCredits").toString();
			if(result.equals("0"))
			{
				total_credit.setText("$ "+totalcredit);
					}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			}
		if(methodName.equals("fetch-transaction-detail")){
			 parser.getPayment(output);
		//	 total_credit.setText();
			 if(arraylist_payment.size()==0)
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(Payment_Activity.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("no detail");
				alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						//finish();
					}
				});	
				alert.show();
			}
			else
			{
				System.err.println(getIntent().getStringExtra("check"));
				if(getIntent().getStringExtra("check").equalsIgnoreCase("payment"))
				{
					adapter = new PaymentAdapter(Payment_Activity.this,arraylist_payment);
					listView.setAdapter(adapter);
					
					
					System.err.println("payment");
					}
				else
				{
					credit_adapter = new CreditAdapter(Payment_Activity.this,arraylist_credit);
					listView.setAdapter(credit_adapter);
					System.err.println("credit");
					
				}
			}
		}
	}
}
