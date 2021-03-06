package com.equiworx.tutor;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Tutor;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class MyConnectionActivity extends Activity implements
		AsyncResponseForTutorHelper {
	private ListView listView;
	private SharedPreferences tutorPrefs;
	private ArrayList<Tutor> tutorList;
	public static ArrayList<String> array_studentIDs = new ArrayList<String>();
	private ParentAdapter adapter;
	private Tutor tutor;
	private TutorHelperParser parser;
	private ImageView rightmenu;
	RelativeLayout back_layout;
	private TextView title;

	// private Button btn_addlist;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tutorlist);

		intializelayout();
		fetchTuterList();
		setOnClickListners();

	}

	private void setOnClickListners() {
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				array_studentIDs.clear();
				tutor = tutorList.get(position);
				Intent intent = new Intent(MyConnectionActivity.this,TutorDetailActivity.class);
				intent.putExtra("name", tutor.getName());
				intent.putExtra("email", tutor.getEmail());
				intent.putExtra("address", tutor.getAddress());
				intent.putExtra("phone", tutor.getContactNumber());
				intent.putExtra("notes", tutor.getNotes());
				intent.putExtra("tutorid", tutor.getTutorId());
				
				intent.putExtra("feecollect",tutorList.get(position).getFee_collected());
				intent.putExtra("feedue",tutorList.get(position).getFee_Due());
				intent.putExtra("outstand",	tutorList.get(position).getOutstanding_balance());
				intent.putExtra("feeoutstand",tutorList.get(position).getFee_outstanding());
				
				
				
				System.err.println("students=" + tutor.getStudent().size());
				for (int i = 0; i < tutor.getStudent().size(); i++) {
					System.err.println("student id="
							+ tutor.getStudent().get(i).getStudentid());
					array_studentIDs.add(tutor.getStudent().get(i)
							.getStudentid());
				}
				intent.putExtra("nostudent", ""+array_studentIDs.size());
				startActivity(intent);

			}
		});
		/*
		 * btn_addlist.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { Intent i=new
		 * Intent(TutorListActivity.this,AddLesson.class); startActivity(i);
		 * 
		 * } });
		 */
	}

	private void fetchTuterList() {
		if (Util.isNetworkAvailable(MyConnectionActivity.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("parent_id", tutorPrefs
					.getString("parentID", "")));

			Log.e("tutor list", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					MyConnectionActivity.this, "fetch-tutor", nameValuePairs,
					true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) MyConnectionActivity.this;
			mLogin.execute();
		} else {
			Util.alertMessage(MyConnectionActivity.this,
					"Please check your internet connection");
		}

	}

	private void intializelayout() {
		tutor = new Tutor();

		parser = new TutorHelperParser(MyConnectionActivity.this);
		title = (TextView) findViewById(R.id.title);
		title.setText("My Connections");

		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		listView = (ListView) findViewById(R.id.listview);

		rightmenu = (ImageView) findViewById(R.id.rightmenu);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		rightmenu.setVisibility(View.GONE);
	}

	@Override
	public void processFinish(String output, String methodName) {
		Log.e("tutorlist", output);
		if (methodName.equals("fetch-tutor")) {

			tutorList = new ArrayList<Tutor>();
			tutorList = parser.getTuterList(output);

			adapter = new ParentAdapter(MyConnectionActivity.this);

			listView.setAdapter(adapter);

		}

	}

	public class ParentAdapter extends BaseAdapter {
		private Context context;
		private TextView tutor_id, email_id, notes_text, tutor_name,view_fee_Detail;
		private CheckBox checkbox;
		// private ImageView call, email;
		private Tutor tutor;

		// private String checking;
		// private RelativeLayout mainlayout;

		public ParentAdapter(Context ctx) {
			context = ctx;
		}

		@Override
		public void notifyDataSetChanged() {
			// do your sorting here

			super.notifyDataSetChanged();
		}

		public void addItem(String string) {
			// TODO Auto-generated method stub

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return tutorList.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return tutorList.get(position);
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
						R.layout.single_tutorlistview_cell, group, false);
			}

			tutor = tutorList.get(position);

			view_fee_Detail=(TextView)convertView.findViewById(R.id.view_fee_Detail);
			String view_fee=view_fee_Detail.getText().toString();
					
			SpannableString content1 = new SpannableString(view_fee);
			content1.setSpan(new UnderlineSpan(), 0, view_fee.length(), 0);
			view_fee_Detail.setText(content1);
			
			tutor_id = (TextView) convertView.findViewById(R.id.tutor_id);
			email_id = (TextView) convertView.findViewById(R.id.email_id);
			notes_text = (TextView) convertView.findViewById(R.id.notes_text);
			tutor_name = (TextView) convertView.findViewById(R.id.tutor_name);
			checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);
			checkbox.setVisibility(View.GONE);
			// email = (ImageView)
			// convertView.findViewById(R.id.imageView_email);

			tutor_id.setText(tutor.getTutorId());
			email_id.setText(": " + tutor.getEmail());
			notes_text.setText(": " + tutor.getContactNumber());
			tutor_name.setText("" + tutor.getName());

			/*
			 * checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
			 * { public void onCheckedChanged(CompoundButton buttonView, boolean
			 * isChecked) { if(isChecked) { //i++; tutor =
			 * tutorList.get(position); String tutorid=tutor.getTutorId();
			 * System.err.println("iddd=="+tutorid);
			 * 
			 * addtutorList.add(tutorid); } else { try{ tutor =
			 * tutorList.get(position); String
			 * tutorid=tutorList.get(position).getTutorId(); for(int
			 * i=0;i<addtutorList.size();i++) {
			 * //System.out.println("Array list="+AddLesson.add.get(i));
			 * if(tutorid.equals(addtutorList.get(i))) { addtutorList.remove(i);
			 * } }
			 * 
			 * //arraylist_addstu.remove(studentid); } catch(Exception e) {
			 * e.printStackTrace(); } tutor = tutorList.get(position); String
			 * tutorid=tutor.getTutorId();
			 * System.err.println("old id="+tutorid);
			 * 
			 * //AddLesson.str_students_list.replace(studentid, "");
			 * //System.err.println("total="+AddLesson.str_students_list); }
			 * String temp="arraylist="+addtutorList.toString();
			 * System.err.println(temp);
			 * 
			 * } });
			 */
			view_fee_Detail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CustomDailogViewFees(
							tutorList.get(position).getFee_collected(),
							tutorList.get(position).getFee_Due(),
							tutorList.get(position).getOutstanding_balance(),
							tutorList.get(position).getFee_outstanding());
							
				}
			});
			return convertView;
		}
		 private void CustomDailogViewFees(String feeCollect,String feeDue,String outBal,String fee_outstanding)
		    {
		        // custom dialog
		        final Dialog dialog = new Dialog(MyConnectionActivity.this);
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
	}

}
