package com.equiworx.lesson;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equiworx.tutorhelper.R;

public class PaymentdetailsActivity extends Activity {
	
	private SharedPreferences tutorPrefs;
	private TextView  title;
	private TextView tv_pid,tv_pname,tv_tid,tv_tname,tv_fees,tv_remarks,tv_paymentmode,tv_lastupdate;
	private String check="";
	private LinearLayout lay_pname,lay_tname;
	private TextView lay_pid,lay_tid;
	private RelativeLayout back_layout;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fullpayment);
		
		initailiselayout();
		onClickListeners();
		fetchpayment();
	}

	private void onClickListeners() {
		// TODO Auto-generated method stub
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	
	private void initailiselayout() {
		// TODO Auto-generated method stub
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
	    title=(TextView)findViewById(R.id.title);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		//tv_id=(TextView)findViewById(R.id.textView8);
		tv_pid=(TextView)findViewById(R.id.textView3);
		tv_pname=(TextView)findViewById(R.id.textView4);
		tv_tid=(TextView)findViewById(R.id.textView1);
		tv_tname=(TextView)findViewById(R.id.textView2);
		
		tv_fees=(TextView)findViewById(R.id.textView5);
		tv_remarks=(TextView)findViewById(R.id.textView6);
		tv_paymentmode=(TextView)findViewById(R.id.textView7);
		tv_lastupdate=(TextView)findViewById(R.id.textView9);
		lay_pname=(LinearLayout)findViewById(R.id.lay_pname);
		lay_tname=(LinearLayout)findViewById(R.id.lay_tname);
		
		
		check=getIntent().getStringExtra("check");
			if(check.equalsIgnoreCase("payment")){
					title.setText("Payment Details");
				}
				else
				{
					title.setText("Credit Details");
					}
		
		if(tutorPrefs.getString("mode", "").equals("parent"))
		{
			tv_pid.setVisibility(View.GONE);
			lay_pname.setVisibility(View.GONE);
			}
		else if(tutorPrefs.getString("mode", "").equals("tutor"))
		{
			tv_tid.setVisibility(View.GONE);
			lay_tname.setVisibility(View.GONE);
			}
	}

	private void fetchpayment() {
		
		tv_pid.setText(getIntent().getStringExtra("p_id"));
		tv_pname.setText(getIntent().getStringExtra("p_name"));
		tv_tname.setText(getIntent().getStringExtra("t_name"));
		tv_tid.setText(getIntent().getStringExtra("t_id"));
		
		tv_fees.setText("$"+getIntent().getStringExtra("fees"));
		tv_remarks.setText(getIntent().getStringExtra("remarks"));
		tv_paymentmode.setText(getIntent().getStringExtra("paymentmode"));
		tv_lastupdate.setText(getIntent().getStringExtra("lastupdate"));
		
	}
}
