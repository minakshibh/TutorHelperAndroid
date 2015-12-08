package com.equiworx.tutor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Parent;
import com.equiworx.model.Statement;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class StatementActivity extends Activity implements AsyncResponseForTutorHelper{
	
	TextView title,txt_startdate,txt_enddate;
	RelativeLayout back;
	int mYear, mMonth, mDay;
	private int  sYear, sMonth, sDay; 
	private Spinner parentSpinner;
	LinearLayout lay_startdate,lay_enddate;
	String str_startdate="",str_enddate,parentId="";
	int datecheck2;
	static String startdate="";
	static String enddate="";
	static String parentname="";
	
	Button button_genrate;
	SharedPreferences tutorPrefs;
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	private DateFormat timeformatter = new SimpleDateFormat("HH:mm");  
	private ArrayList<Parent> parentList=new ArrayList<Parent>();
	public static ArrayList<Statement> statement_detail=new ArrayList<Statement>();
	private TutorHelperParser parser;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_statement);
		
		setUI();
		onclickListener();
		getExistingParents();
		
		
		/*ArrayAdapter<Parent> spinnerArrayAdapter = new ArrayAdapter<Parent>(this,
		          android.R.layout.simple_spinner_item, parentList);
		spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
		    // Step 3: Tell the spinner about our adapter
		System.err.println("sizeeeeee==="+parentList.size());
	
		parentSpinner.setAdapter(spinnerArrayAdapter);	
		*/
		
		
	}
	
	private void setUI(){
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser=new TutorHelperParser(StatementActivity.this);
	title=(TextView)findViewById(R.id.title);
	button_genrate=(Button)findViewById(R.id.btn_generate);
	title.setText("Statement");
	back=(RelativeLayout)findViewById(R.id.back_layout);
	parentSpinner = (Spinner)findViewById(R.id.parentSpinner);
	lay_startdate=(LinearLayout)findViewById(R.id.lay_startdate);
	lay_enddate=(LinearLayout)findViewById(R.id.lay_enddate);
	txt_startdate=(TextView)findViewById(R.id.txt_startdate);
	txt_enddate=(TextView)findViewById(R.id.txt_enddate);
	}
	private void getExistingParents() {
		if (Util.isNetworkAvailable(StatementActivity.this)){
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("last_updated_date", ""));
			nameValuePairs.add(new BasicNameValuePair("tutor_id",  tutorPrefs.getString("tutorID","")));
			
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(StatementActivity.this, "fetch-parent", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) StatementActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(StatementActivity.this,"Please check your internet connection");
		}
	}
	private void PDF_Data()
	{
		startdate=txt_startdate.getText().toString();
		enddate=txt_enddate.getText().toString();
		
		
		if (Util.isNetworkAvailable(StatementActivity.this)){
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("start_date", txt_startdate.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("end_date", txt_enddate.getText().toString() ));
			nameValuePairs.add(new BasicNameValuePair("parent_id",  parentId));
			nameValuePairs.add(new BasicNameValuePair("tutor_id",  tutorPrefs.getString("tutorID","")));
			
			Log.e("parent-statement", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(StatementActivity.this, "parent-statement", nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) StatementActivity.this;
			mLogin.execute();
		}else {
			Util.alertMessage(StatementActivity.this,"Please check your internet connection");
		}
	}
	private void onclickListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
				
			}
		});
		
		button_genrate.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
		// TODO Auto-generated method stub
			if(txt_enddate.getText().toString().equalsIgnoreCase("Select end date")||txt_startdate.getText().toString().equalsIgnoreCase("Select start date"))
			{
				Util.alertMessage(StatementActivity.this, "Please select date");
				}
			else if (parentSpinner.getSelectedItem().toString().trim().equalsIgnoreCase("Select existing parent")) {
				Util.alertMessage(StatementActivity.this, "Please select Parent");
			   
			}
			else
			{
				PDF_Data();
				
				}
		
			}
		});
		
		getCurretDate();
		String sdate=mYear + "-" + (mMonth+1) + "-" + mDay;
		 Date date1 = null;
			try {
				date1 = (Date)formatter.parse(sdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
					
			 str_startdate = formatter.format(date1);
			 txt_startdate.setText("Select start date");
	
			////////////////start date//////////////////////// 
			lay_startdate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 DatePickerDialog dpd = new DatePickerDialog(StatementActivity.this,
			                new DatePickerDialog.OnDateSetListener() {
			                public void onDateSet(DatePicker view, int year,
			               int monthOfYear, int dayOfMonth) {
			     	     // long currentdateintoInt= System.currentTimeMillis();
			     	      String sdate=year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
			     	      mYear=year;
			     	      mMonth=monthOfYear;
			     	      mDay=dayOfMonth;
			        	 Date date = null;
						try {
							date = (Date)formatter.parse(sdate);
							//datecheck=0;
							formatter = new SimpleDateFormat("yyyy-MM-dd");
					    	str_startdate = formatter.format(date);
					    	txt_startdate.setText(""+str_startdate);
			            	//getNextEnddate(str_startdate,1);
			            	//ed_Lessonenddate.setText("select end date"); 
						} catch (ParseException e) {
							e.printStackTrace();
						}
								
			                	
			       //check date condition.........
								
			    		  try{
			    			  Calendar c = Calendar.getInstance();
			    			  System.out.println("Current date => " + c.getTime());
			    			  String currentdate = formatter.format(c.getTime());
			    		      Date seldate = formatter.parse(str_startdate);
			    		      System.out.println("Select date => " + seldate);
						      Date curdate = formatter.parse(currentdate);
						     if (curdate.before(seldate))
						      {
						    	 //txt_startdate.setText("select start date"); 
						         System.out.println("date2 is Greater than my date1");    
						     //    datecheck=1;

							    	Util.alertMessage(StatementActivity.this, "Please select start date less from current date");  
							    	txt_startdate.setText("select start date"); 
							    	txt_enddate.setText("select end date"); 
						      		}
						     else if(curdate.equals(seldate))
						     {/* 
						    	// datecheck=0;
			//check for start time......  	 
			   		    	  try{
			           			  Calendar ctime = Calendar.getInstance();
			           			  System.out.println("Current time => " + ctime.getTime());

			           			 String currenttime = timeformatter.format(ctime.getTime());
			           			  
			        			     Date seltime = timeformatter.parse(str_starttime);
			        			     Date curtime = timeformatter.parse(currenttime);

			        			     if (curtime.before(seltime))
			        			      {
			        			         System.out.println("date2 is Greater than my date1");    
			        			      
			        			      }
			        			     else if(curtime.equals(seltime))
			        			     {
			        			    	// Util.alertMessage(AddLesson.this, "Select date Greater than start lesson date");  
			        			    	// ed_Lessonenddate.setText("select lesson end date"); 
			        			     }
			        			     else 
			        			     {
			        			    	if(datecheck==1)
			        			    	{
			        			    		
			        			    	}
			        			    	else{
			        			    		System.err.println("Please select Start time Greater from current time");	
			        			    		Util.alertMessage(StatementActivity.this,"Time "+btn_editst.getText().toString() + " is passed away. Please select future time.");  
			        			    	//	btn_editst.setText("select start time"); 
			        			    			}
			        			     	}

			        			    }catch (ParseException e1){
			        			        e1.printStackTrace();
			        			    }
						    	
						     */
						    	 txt_enddate.setText("select end date"); 	 
						     }
						     else
						     {
						    	//Util.alertMessage(StatementActivity.this, "Please select start date Greater from current date");  
						    	//txt_startdate.setText("select start date"); 
						    	//txt_enddate.setText("select end date"); 
						    	 txt_enddate.setText("select end date"); 
						     	}

						    }catch (ParseException e1){
						        e1.printStackTrace();
						    	}
			    			 
			                }
			            }, mYear, mMonth, mDay);
			      			dpd.setTitle("Select start date");
				            dpd.show();
					
				}
			});
			
			
		gettomarrowDate();
		String edate=sYear + "-" + (sMonth+1) + "-" + sDay;
		 Date dateend = null;
			try {
				dateend = (Date)formatter.parse(edate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
		str_enddate = formatter.format(dateend);
	    
		txt_enddate.setText("Select end date");
		
		////////////////end date//////////////////////// 
		lay_enddate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   DatePickerDialog dpd = new DatePickerDialog(StatementActivity.this, new DatePickerDialog.OnDateSetListener() {
		               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		              	String sdate=year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
		              		mYear=year;
		              		mMonth=monthOfYear;
		                	mDay=dayOfMonth;    				
		    		    	Date date = null;
							
		    		    	try {
								date = (Date)formatter.parse(sdate);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							
							 formatter = new SimpleDateFormat("yyyy-MM-dd");
		    		    	 String  s = formatter.format(date);
		    		    	 txt_enddate.setText(""+s);
		    		    	 
		    		    	 //check date..........
		    		    	 try{
		        			     Date seldate = formatter.parse(s);
		        			     Calendar c = Calendar.getInstance();
		        			     //String currentdate = formatter.format(str_startdate);
		        			     Date curdate = formatter.parse(str_startdate);
		        			     
		        			     System.out.println("select date => " + seldate);
		        			     System.out.println("start date => " + curdate);
		        			     if (curdate.before(seldate))
		        			      {
		        			         System.out.println("selected date is Greater than start date"); 
		        			         try{
					    			  Calendar c1 = Calendar.getInstance();
					    			  System.out.println("Current date => " + c1.getTime());
					    			  String currentdate = formatter.format(c1.getTime());
					    		      Date seldate1 = formatter.parse(str_startdate);
					    		      System.out.println("Select date => " + seldate1);
								      Date curdate1 = formatter.parse(currentdate);
								     if (curdate1.before(seldate))
								      {
								    	 //txt_startdate.setText("select start date"); 
								         System.out.println("date2 is Greater than my date1");    
								     //    datecheck=1;

									    	Util.alertMessage(StatementActivity.this, "Please select end date less from current date");  
									    	//txt_startdate.setText("select start date"); 
									    	txt_enddate.setText("select end date"); 
								      		}
								     else if(curdate1.equals(seldate))
								     { 
								    	// datecheck=0;
					//check for start time......  	 
					   		    	  try{
					           			  Calendar ctime = Calendar.getInstance();
					           			  System.out.println("Current time => " + ctime.getTime());

					           			 String currenttime = timeformatter.format(ctime.getTime());
					           			  
					        			     Date seltime = timeformatter.parse(sdate);
					        			     Date curtime = timeformatter.parse(currenttime);

					        			     if (curtime.before(seltime))
					        			      {
					        			         System.out.println("date2 is Greater than my date1");    
					        			      
					        			      }
					        			     else if(curtime.equals(seltime))
					        			     {
					        			    	// Util.alertMessage(AddLesson.this, "Select date Greater than start lesson date");  
					        			    	// ed_Lessonenddate.setText("select lesson end date"); 
					        			     }
					        			     else 
					        			     {
					        			    	
					        			    		System.err.println("Please select Start time Greater from current time");	
					        			    		Util.alertMessage(StatementActivity.this,"Please select end date less from current time");  
					        			    	//	btn_editst.setText("select start time"); 
					        			    			
					        			     	}

					        			    }catch (ParseException e1){
					        			        e1.printStackTrace();
					        			    }
								    	 
								     }
								     else
								     {
								    	//Util.alertMessage(StatementActivity.this, "Please select start date Greater from current date");  
								    	//txt_startdate.setText("select start date"); 
								    	//txt_enddate.setText("select end date"); 
								     	}

								    }catch (ParseException e1){
								        e1.printStackTrace();
								    	}
		        			      	}
		        			     else if(curdate.equals(seldate))
		        			     {
		        			    	// datecheck2=1;
		        			    	
		        			     	}
		        			     else
		        			     {
		        			    	 datecheck2=1;
		        			    	// selected();
		        			    	 
		        			     	}

		        			    }catch (ParseException e1){
		        			        e1.printStackTrace();
		        		
		        			    }
		    		  if( datecheck2==1)
		    		  {
		    			  Util.alertMessage(StatementActivity.this, "Please select  end date Greater from  start date");  
		    			  txt_enddate.setText("select end date");
		    			 // txt_startdate.setText("select start date"); 
		    			
				    	datecheck2=0;
		    		  }
		               }
		            }, mYear, mMonth, mDay);
			            dpd.setTitle("Select end date");
			            dpd.show();	
					
				}
			});
//	String name=	parentSpinner.getSelectedItem();
		parentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				// TODO Auto-generated method stub
				Util.hideKeyboard(StatementActivity.this);
				
				Parent parent = (Parent)parentSpinner.getSelectedItem();
			
				if(!parent.getParentId().equals("0")){
				parentId = parent.getParentId();
				parentname=parent.getName();
				System.err.println("spinnerrr  yeeeeeeeeeees"+parentId);
				}
						
						
			}
		public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void getCurretDate() {
		  final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
	}
	private void gettomarrowDate() {
		  Calendar cal = Calendar.getInstance(); //Get the Current date
			  cal.add(Calendar.DAY_OF_MONTH,1);//add one day
			  cal.getTime();
	        sYear = cal.get(Calendar.YEAR);
	        sMonth = cal.get(Calendar.MONTH);
	        sDay = cal.get(Calendar.DAY_OF_MONTH);
		}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		 if(methodName.equals("fetch-parent")){
				parentList = parser.getParents(output);
				
				ArrayAdapter<Parent> spinnerArrayAdapter = new ArrayAdapter<Parent>(this,
				          android.R.layout.simple_spinner_item, parentList);
				spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
				    // Step 3: Tell the spinner about our adapter
				parentSpinner.setAdapter(spinnerArrayAdapter);
	}
		 else if(methodName.equals("parent-statement"))
		 {
			 statement_detail= parser.getStatementdetail(output);
			 Intent i=new Intent(StatementActivity.this,PdfGenerateActivity.class);
			 startActivity(i);
		 }
			 
}
}
