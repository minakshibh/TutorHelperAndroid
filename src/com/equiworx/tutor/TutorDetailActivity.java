package com.equiworx.tutor;

import java.util.ArrayList;
import java.util.Arrays;
import com.equiworx.lesson.AddLessonActivity;
import com.equiworx.model.StudentList;
import com.equiworx.parent.TutorCalenderView;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class TutorDetailActivity extends Activity {


	private TextView name, email,  address, contact, studentid,no_of_Student,title,view_fee_Detail;
	private TutorHelperDatabaseHandler dbHandler;
	private StudentAdapter adapter;
	private ListView listview;
	private LinearLayout btn_addlist,button_calendar;
	private RelativeLayout back_layout;
	private ArrayList<StudentList> arraylist_student = new ArrayList<StudentList>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tutordetail);

		initializelayout();
		fetchTutorDetails();
		onclickListenser();
		fetchlStudentList();
	}

	private void initializelayout() {
		
		dbHandler = new TutorHelperDatabaseHandler(TutorDetailActivity.this);
		listview = (ListView) findViewById(R.id.listview);
		btn_addlist = (LinearLayout) findViewById(R.id.button_addlesson);
		button_calendar= (LinearLayout) findViewById(R.id.button_calendar);
		name = (TextView) findViewById(R.id.name);
		email = (TextView) findViewById(R.id.email);
		address = (TextView) findViewById(R.id.address);
		contact = (TextView) findViewById(R.id.contact);
		studentid = (TextView) findViewById(R.id.studentid);
		no_of_Student=(TextView)findViewById(R.id.no_of_Student);
		back_layout=(RelativeLayout)findViewById(R.id.back_layout);
		view_fee_Detail=(TextView)findViewById(R.id.view_fee_Detail);
		String view_fee =view_fee_Detail.getText().toString();
		SpannableString content1 = new SpannableString(view_fee);
		content1.setSpan(new UnderlineSpan(), 0, view_fee.length(), 0);
		view_fee_Detail.setText(content1);
		title=(TextView)findViewById(R.id.title);
		title.setText("Tutor Details");

	}
	private void onclickListenser() {
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		contact.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				String uri = "tel:" + contact.getText().toString().trim();
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse(uri));
				startActivity(intent);
			}
		});
		email.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", email.getText().toString().trim(),
								null));
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));
			}
		});
		btn_addlist.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(TutorDetailActivity.this, AddLessonActivity.class);
				i.putExtra("tutorid", getIntent().getStringExtra("tutorid"));
				startActivity(i);
				finish();

			}
		});
		button_calendar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TutorDetailActivity.this, TutorCalenderView.class);
				i.putExtra("tutorid", getIntent().getStringExtra("tutorid"));
				startActivity(i);
				
			}
		});
		view_fee_Detail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String feeCollect=getIntent().getStringExtra("feecollect");
				String feeDue=getIntent().getStringExtra("feedue");
				String outBal=getIntent().getStringExtra("outstand");
				String fee_outstanding=getIntent().getStringExtra("feeoutstand");
						
				CustomDailogViewFees(feeCollect,feeDue,outBal,fee_outstanding);
			}
		});
	}
	 private void CustomDailogViewFees(String feeCollect,String feeDue,String outBal,String fee_outstanding)
	    {
	        // custom dialog
	        final Dialog dialog = new Dialog(TutorDetailActivity.this);
	       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        dialog.setTitle("Fee Details");
	        dialog.setContentView(R.layout.parent_feesview);
	        dialog.setCancelable(false);
	        Window window = dialog.getWindow();
	        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        // set the custom dialog components - text view
	        TextView fee_collected = (TextView) dialog.findViewById(R.id.fee_collected);
	        TextView fee_due = (TextView) dialog.findViewById(R.id.fee_due);
	        TextView outstng_bal = (TextView) dialog.findViewById(R.id.outstng_bal);
	        TextView txtOk = (TextView) dialog.findViewById(R.id.txtOk);
	        TextView  txt_fee_outstanding=(TextView) dialog.findViewById(R.id.fee_outstanding);
	      try{  
		        if(feeCollect==null)
			        {
			        	  fee_collected.setText("$"+"0");
			        }else{
			        
			        	fee_collected.setText("$"+feeCollect);
			        }
	      	}catch(Exception e){  
	      	}
	      /////////////////
	      try{
	        if(feeDue==null)
	        { 
	        	fee_due.setText("$"+"0");
	        	
	        }
	        else{
	        	 fee_due.setText("$"+feeDue);
	        }
	      }catch(Exception e){
	    	  
	      }
	      ///////////////////
	       try{
	        if(outBal==null)
	        { 
	        	outstng_bal.setText("$"+"0");
	        	
	        }
	        else{
	        	 outstng_bal.setText("$"+outBal);
	        }
	      }catch(Exception e){
	    	  
	      }
	       
	       
	       //////////////
	       try{
		        if(fee_outstanding==null)
		        { 
		        	txt_fee_outstanding.setText("$"+"0");
		        	
		        }
		        else{
		        	txt_fee_outstanding.setText("$"+fee_outstanding);
		        }
		      }catch(Exception e){
		    	  
		      }
	       
	        txtOk.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dialog.dismiss();
	              
	            }
	        });
	       
	        dialog.show();
	    }
	private void fetchTutorDetails() {
		name.setText("" + getIntent().getStringExtra("name"));

		String str = getIntent().getStringExtra("email");
		SpannableString content = new SpannableString(str);
		content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
		email.setText(content);

		address.setText(getIntent().getStringExtra("address"));

		String str1 = getIntent().getStringExtra("phone");
		SpannableString content1 = new SpannableString(str1);
		content1.setSpan(new UnderlineSpan(), 0, str1.length(), 0);
		contact.setText(content1);

		studentid.setText("Tutor Id :" + getIntent().getStringExtra("tutorid"));
		
		no_of_Student.setText(getIntent().getStringExtra("nostudent")+" Students");
	}

	private void fetchlStudentList() {

		arraylist_student = new ArrayList<StudentList>();
		ArrayList<StudentList> array=new ArrayList<StudentList>();
		try {
		
			for (int i = 0; i < MyConnectionActivity.array_studentIDs.size(); i++) {
								
				System.err.println("data=="+dbHandler.getStudentDetails("single", MyConnectionActivity.array_studentIDs.get(i)));
		
				array.add(dbHandler.getStudentDetails("single", MyConnectionActivity.array_studentIDs.get(i)));
				
				if(array.get(i).getName()!=null)
				{
					arraylist_student.add(dbHandler.getStudentDetails("single", MyConnectionActivity.array_studentIDs.get(i)));
					}
				
			}
			arraylist_student.removeAll(Arrays.asList("null"));  
			adapter = new StudentAdapter(TutorDetailActivity.this);
			listview.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public class StudentAdapter extends BaseAdapter {
		private Context context;
		private TextView tv_name, tv_email, tv_note, tv_contactno;
		private ImageView img_edit;
		private StudentList studentlist;

		public StudentAdapter(Context ctx) {
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return arraylist_student.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arraylist_student.get(position);
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

			studentlist = arraylist_student.get(position);
			TextView lbl_notes = (TextView) convertView
					.findViewById(R.id.lbl_notes);
			lbl_notes.setVisibility(View.GONE);
			TextView lbl_address = (TextView) convertView
					.findViewById(R.id.lbl_address);
			lbl_address.setVisibility(View.VISIBLE);

			tv_name = (TextView) convertView.findViewById(R.id.studentname);
			tv_email = (TextView) convertView.findViewById(R.id.Email);
			tv_note = (TextView) convertView.findViewById(R.id.Notes);
			tv_contactno = (TextView) convertView.findViewById(R.id.contactno);
			img_edit = (ImageView) convertView.findViewById(R.id.editImg);
			tv_contactno.setText(": " + studentlist.getContactInfo());

			tv_email.setText(": " + studentlist.getEmail());
			tv_note.setText(": " + studentlist.getAddress());
			tv_name.setText(studentlist.getName());
			img_edit.setVisibility(View.GONE);
			
			return convertView;
		}
	}
}