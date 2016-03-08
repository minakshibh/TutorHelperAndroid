package com.equiworx.tutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
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

public class HistoryDetails extends Activity implements	AsyncResponseForTutorHelper {
	
	private TextView tv_feescollect, tv_feedue, tv_outbal;
	private ListView listview;
	private LinearLayout lay_invoice, lay_addcredit, lay_payment,lay_statement;
	Intent intent;
	private String month = "bymonth";
	private ArrayList<FeeDetail> historyDetail = new ArrayList<FeeDetail>();
	private ArrayList<FeesDetail> historyDetailByParents;
	private SharedPreferences tutorPrefs;
	private TutorHelperParser parser;
	private historyDetailByMonthAdapter historyDetailByMonthAdapter;
	private HistoryDetailByParentAdapter historyDetailByParentAdapter;
	private SharedPreferences pref;
	private RelativeLayout bymonth_layout, by_parents_layouts, menu_button,back_layout;
	private AlertDialog levelDialog;
    private PopupMenu popup;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_details);
		
		
	}

	private void intializelayout() {
		parser = new TutorHelperParser(HistoryDetails.this);
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		listview = (ListView) findViewById(R.id.listview);
		tv_feescollect = (TextView) findViewById(R.id.fee_collected);
		tv_feedue = (TextView) findViewById(R.id.fee_due);
		tv_outbal = (TextView) findViewById(R.id.outstng_bal);
		lay_invoice = (LinearLayout) findViewById(R.id.invoice);
		lay_payment = (LinearLayout) findViewById(R.id.payment);
		lay_addcredit = (LinearLayout) findViewById(R.id.addcredit);
		lay_statement = (LinearLayout) findViewById(R.id.statement);
		bymonth_layout = (RelativeLayout) findViewById(R.id.bymonth_layout);
		by_parents_layouts = (RelativeLayout) findViewById(R.id.by_parents_layouts);
		menu_button = (RelativeLayout) findViewById(R.id.menu_button);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		menu_button.setVisibility(View.GONE);
		fetchHistoryByParents();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (month.equalsIgnoreCase("bymonth")) {
					FeeDetail id = historyDetail.get(arg2);

					FeesDetail ei = (FeesDetail) id;
					String getLessonIds = ei.getLessonIds();
				//	String getyear=ei.getYear();
					String getmonth=ei.getMonthName();
					System.err.println("getyear"+getmonth);
					
					Editor ed=tutorPrefs.edit(); 
					//ed.putString("getLyear", getyear);
					ed.putString("getLmonth", getmonth);
					ed.commit();
					Intent intent = new Intent(HistoryDetails.this,Payment_LessonsDetailsActivity.class);
					intent.putExtra("getLessonIds", getLessonIds);
					
					startActivity(intent);
				} else if (month.equalsIgnoreCase("byparents")) {
					FeeDetail id1 = historyDetailByParents.get(arg2);

					FeesDetail ei1 = (FeesDetail) id1;
					String getLessonIds = ei1.getLessonIds();
				//	String getyear=ei1.getYear();
				//	String getmonth=ei1.getMonthName();
					//System.err.println(getyear+getmonth);
					
					Editor ed=tutorPrefs.edit(); 
					//ed.putString("getLyear", getyear);
					ed.putString("getLmonth", "");
					ed.commit();
					Intent intent = new Intent(HistoryDetails.this,	Payment_LessonsDetailsActivity.class);
					intent.putExtra("getLessonIds", getLessonIds);
					startActivity(intent);

				} else {
					FeeDetail id = historyDetail.get(arg2);

					FeesDetail ei = (FeesDetail) id;
					String getLessonIds = ei.getLessonIds();
					//String getyear=ei.getYear();
					//String getmonth=ei.getMonthName();
					//System.err.println(getyear+getmonth);
					
					Editor ed=tutorPrefs.edit(); 
					//ed.putString("getLyear", getyear);
					ed.putString("getLmonth", "");
					ed.commit();
					Intent intent = new Intent(HistoryDetails.this,	Payment_LessonsDetailsActivity.class);
					intent.putExtra("getLessonIds", getLessonIds);
					startActivity(intent);
				}

			}
		});
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	intializelayout();
	fetchHistoryDetails();
	setOnClickListners();
}
	private void setOnClickListners() {
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();

			}
		});
		lay_invoice.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//Intent intent = new Intent(HistoryDetails.this, InvoiceActivity.class);
				//intent.putExtra("check", "payment");
				//startActivity(intent);
			}
		});
		lay_addcredit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				intent = new Intent(HistoryDetails.this, Payment_Activity.class);
				intent.putExtra("check", "credit");
				startActivity(intent);

			}
		});
		lay_payment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				intent = new Intent(HistoryDetails.this, Payment_Activity.class);
				intent.putExtra("check", "payment");
				startActivity(intent);

			}
		});
		lay_statement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(HistoryDetails.this, StatementActivity.class);
				//intent.putExtra("check", "payment");
				startActivity(intent);
			}
		});
		by_parents_layouts.setBackgroundColor(getResources().getColor(R.color.lightgray));
		bymonth_layout.setBackgroundColor(getResources().getColor(
				R.color.appBlue));
		bymonth_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu_button.setVisibility(View.GONE);
								
				month = "bymonth";
				listview.setAdapter(null);
				historyDetailByMonthAdapter = new historyDetailByMonthAdapter(HistoryDetails.this,
						historyDetail, month);
				listview.setAdapter(historyDetailByMonthAdapter);
				by_parents_layouts.setBackgroundColor(getResources().getColor(R.color.lightgray));
				bymonth_layout.setBackgroundColor(getResources().getColor(
						R.color.appBlue));
				 
				pref = getApplicationContext().getSharedPreferences("MyPref", 0);
				String totalefeedue = pref.getString("totalFeeDuep", null);
				String totalFeesCollected = pref.getString("totalFeesCollectedp",null);
				String totalOutstandingBalance = pref.getString("totalOutstandingBalancep", null);

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
				
			}
		});
		by_parents_layouts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu_button.setVisibility(View.VISIBLE);
				listview.setAdapter(null);
				month = "byparents";
				historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,
						historyDetailByParents, month);
				listview.setAdapter(historyDetailByParentAdapter);
				bymonth_layout.setBackgroundColor(getResources().getColor(
						R.color.lightgray));
				by_parents_layouts.setBackgroundColor(getResources().getColor(
						R.color.appBlue));
				
				
				pref = getApplicationContext().getSharedPreferences("MyPref", 0);
				String totalefeedue = pref.getString("totalFeeDue", null);
				String totalFeesCollected = pref.getString("totalFeesCollected",null);
				String totalOutstandingBalance = pref.getString("totalOutstandingBalance", null);

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

			}
		});
		menu_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 popup = new PopupMenu(HistoryDetails.this,
						menu_button);
				// Inflating the Popup using xml file
				popup.getMenuInflater().inflate(R.menu.chat_list_item_popup,
						popup.getMenu());

				// registering popup with OnMenuItemClickListener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("Filters")) {

							final CharSequence[] items = { "Outstanding balance >0 " };

							// Creating and Building the Dialog
							AlertDialog.Builder builder = new AlertDialog.Builder(
									HistoryDetails.this);
							builder.setTitle("Select Filter order");
							builder.setSingleChoiceItems(items, -1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int item) {

											switch (item) {
											case 0:
												SortedSet<FeesDetail> personss3 = new TreeSet<FeesDetail>(
														new Comparator<FeesDetail>() {
															@Override
															public int compare(FeesDetail arg0,
																	FeesDetail arg1) {
																return arg0.getOutstandingBalance().compareTo(arg1.getOutstandingBalance());
															}
														});

												Iterator<FeesDetail> iterator3 = historyDetailByParents.iterator();
												while (iterator3.hasNext()) {
													personss3.add(iterator3.next());
												}
												
												historyDetailByParents.clear();
												historyDetailByParents.addAll(personss3);
												listview.setAdapter(null);
												historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,historyDetailByParents,month);
												listview.invalidateViews();
												listview.setAdapter(historyDetailByParentAdapter);
												break;
											case 1:
												// Your code when 4th option
												// seletced
												break;
											//
											}
											levelDialog.dismiss();
										}
									});
							levelDialog = builder.create();
							levelDialog.show();
						} else if (item.getTitle().equals("Sorts")) {

							final CharSequence[] items = { " By name ",	" Fees Collected ", " Outstanding balance " };

							// Creating and Building the Dialog
							AlertDialog.Builder builder = new AlertDialog.Builder(
									HistoryDetails.this);
							builder.setTitle("Select sort order");
							builder.setSingleChoiceItems(items, -1,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int item) {

											switch (item) {
											case 0:
												ArrayList<FeesDetail> sortarArrayList = new ArrayList<FeesDetail>();
												Collections
														.sort(historyDetailByParents,
																new Comparator<FeesDetail>() {

																	@Override
																	public int compare(FeesDetail lhs,	FeesDetail rhs) {
																		// TODO
																		// Auto-generated// method
																		// stub
																		return lhs.getMonthName().compareTo(rhs.getMonthName());
																		
																	}
																});

												Iterator<FeesDetail> iterator = historyDetailByParents
														.iterator();
												while (iterator.hasNext()) {
													sortarArrayList
															.add(iterator.next());
												}
												historyDetailByParents.clear();
												historyDetailByParents.addAll(sortarArrayList);
												listview.setAdapter(null);
												historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,historyDetailByParents,month);
												listview.invalidateViews();
												listview.setAdapter(historyDetailByParentAdapter);
												
												break;
											case 1:
												ArrayList<FeesDetail> sortarArrayList1 = new ArrayList<FeesDetail>();

												Collections
														.sort(historyDetailByParents,
																new Comparator<FeesDetail>() {
																	public int compare(FeesDetail v1,FeesDetail v2) {
																		return v1.getFeeCollected().compareTo(v2.getFeeCollected());
																	}
																});

												Iterator<FeesDetail> iterator1 = historyDetailByParents	.iterator();
												while (iterator1.hasNext()) { 
													sortarArrayList1.add(iterator1.next());
												}
												historyDetailByParents.clear();
												historyDetailByParents.addAll(sortarArrayList1);
												listview.setAdapter(null);
												historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,historyDetailByParents,month);
												listview.invalidateViews();
												listview.setAdapter(historyDetailByParentAdapter);

												break;
											case 2:
												ArrayList<FeesDetail> sortArrayList_balance = new ArrayList<FeesDetail>();
												Collections
														.sort(historyDetailByParents,
																new Comparator<FeesDetail>() {
																	public int compare(	FeesDetail v1,FeesDetail v2) {
																		return v1.getOutstandingBalance().trim().compareTo(v2.getOutstandingBalance().trim());
																	}
																});

												Iterator<FeesDetail> iterator_balance = historyDetailByParents.iterator();
												while (iterator_balance.hasNext()) {

													sortArrayList_balance.add(iterator_balance.next());
												}
												historyDetailByParents.clear();
												historyDetailByParents.addAll(sortArrayList_balance);
												listview.setAdapter(null);
												historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,historyDetailByParents,month);
												listview.invalidateViews();
												listview.setAdapter(historyDetailByParentAdapter);

												
												break;
											case 3:
												// Your code when 4th option
												// seletced
												break;
											//
											}
											levelDialog.dismiss();
										}
									});
							levelDialog = builder.create();
							levelDialog.show();
						}
						return true;
					}

				});

				popup.show();

			}
		});
	}

	private void fetchHistoryDetails() {

		if (Util.isNetworkAvailable(HistoryDetails.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));

			nameValuePairs.add(new BasicNameValuePair("parent_id", ""));
			nameValuePairs.add(new BasicNameValuePair("trigger", "ByMonth"));

			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					HistoryDetails.this, "fetch-payment-history",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) HistoryDetails.this;
			mLogin.execute();

		} else {
			Util.alertMessage(HistoryDetails.this,
					"Please check your internet connection");
		}

	}

	private void fetchHistoryByParents() {

		if (Util.isNetworkAvailable(HistoryDetails.this)) {

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("tutor_id", tutorPrefs
					.getString("tutorID", "")));

			// nameValuePairs.add(new BasicNameValuePair("ParentId", ""));
			nameValuePairs.add(new BasicNameValuePair("trigger", "ByParent"));

			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					HistoryDetails.this, "fetch-payment-history-parent",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) HistoryDetails.this;
			mLogin.execute();

		} else {
			Util.alertMessage(HistoryDetails.this,
					"Please check your internet connection");
		}

	}

	@Override
	public void processFinish(String output, String methodName) {
		// TODO Auto-generated method stub
		if (methodName.equals("fetch-payment-history")) {
			historyDetail = new ArrayList<FeeDetail>();
			listview.setAdapter(null);
			
			historyDetail = parser.getPaymentHistory(output);
			pref = getApplicationContext().getSharedPreferences("MyPref", 0);
			String totalefeedue = pref.getString("totalFeeDuep", null);
			String totalFeesCollected = pref.getString("totalFeesCollectedp",null);
			String totalOutstandingBalance = pref.getString("totalOutstandingBalancep", null);

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
			
			 month = "bymonth";
			historyDetailByMonthAdapter = new historyDetailByMonthAdapter(HistoryDetails.this,historyDetail, month);
			listview.setAdapter(historyDetailByMonthAdapter);

		} else if (methodName.equals("fetch-payment-history-parent")) {
			
			historyDetailByParents = new ArrayList<FeesDetail>();
			listview.setAdapter(null);
			historyDetailByParents = parser.getPaymentHistoryParents(output);
			String month = "byparents";
			historyDetailByParentAdapter = new HistoryDetailByParentAdapter(HistoryDetails.this,
			historyDetailByParents, month);
			listview.setAdapter(historyDetailByParentAdapter);

		}
	}

	public class historyDetailByMonthAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, noLession, feeDue, outstandingBalance,
				feeCollect,outstnding_fess_text	, yearname_text;
	
		private ArrayList<FeeDetail> parentList = new ArrayList<FeeDetail>();
		private LinearLayout yearname_layout;
		private String byMonth;
		private LinearLayout month_layout;

		public historyDetailByMonthAdapter(Context ctx,	ArrayList<FeeDetail> historyDetail, String month) {
			context = ctx;
			this.parentList = historyDetail;
			byMonth = month;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			Log.d("", "sizexxx" + parentList.size());

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
			 month_layout = (LinearLayout) convertView.findViewById(R.id.month_layout);
			 yearname_layout = (LinearLayout) convertView.findViewById(R.id.yearname_layout);

			FeeDetail i = parentList.get(position);
			if (i != null) {
				if (i.isSection()) {
					YearNode si = (YearNode) i;

					yearname_layout.setOnClickListener(null);
					yearname_layout.setOnLongClickListener(null);
					yearname_layout.setLongClickable(false);
					month_layout.setVisibility(View.GONE);
					yearname_layout.setVisibility(View.VISIBLE);
					yearname_text = (TextView) convertView.findViewById(R.id.yearname_text);
					yearname_text.setText(si.getYear());
				} else {
					FeesDetail ei = (FeesDetail) i;
					yearname_layout.setVisibility(View.GONE);
					month_layout.setVisibility(View.VISIBLE);
					monthName = (TextView) convertView.findViewById(R.id.studentname);
					noLession = (TextView) convertView.findViewById(R.id.noLesson);
					outstnding_fess_text= (TextView)convertView.findViewById(R.id.outstnding_fess_text);
					feeDue = (TextView) convertView.findViewById(R.id.dueFess);
					feeCollect = (TextView) convertView.findViewById(R.id.feescollect);
					outstandingBalance = (TextView) convertView.findViewById(R.id.outstandingBalnce);
					
					outstnding_fess_text.setText(": $"+ei.getFee_outstanding());
					monthName.setText(""+ei.getMonthName());
					noLession.setText(": "+ei.getNoOfLessons());
					feeDue.setText(": $" + ei.getFeeDue());
					feeCollect.setText(": $" + ei.getFeeCollected());
					outstandingBalance.setText(": $" + ei.getOutstandingBalance());
				}
			}


			return convertView;
		}

	}

	public class HistoryDetailByParentAdapter extends BaseAdapter {
		private Context context;
		private TextView monthName, noLession, feeDue, outstandingBalance,
				feeCollect,outstnding_fess_text;
		private ArrayList<FeesDetail> parentList = new ArrayList<FeesDetail>();
		private LinearLayout yearname_layout;
		private String byMonth;
		private LinearLayout month_layout;

		public HistoryDetailByParentAdapter(Context ctx,
				ArrayList<FeesDetail> historyDetail, String month) {
			context = ctx;
			this.parentList = historyDetail;
			byMonth = month;

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
				convertView = inflater.inflate(R.layout.single_history_detaile_activity, group, false);
			}
			month_layout = (LinearLayout) convertView.findViewById(R.id.month_layout);
			yearname_layout = (LinearLayout) convertView.findViewById(R.id.yearname_layout);
			FeesDetail ei = parentList.get(position);yearname_layout.setVisibility(View.GONE);
			month_layout.setVisibility(View.VISIBLE);
			outstnding_fess_text= (TextView) convertView.findViewById(R.id.outstnding_fess_text);
			monthName = (TextView) convertView.findViewById(R.id.studentname);
			noLession = (TextView) convertView.findViewById(R.id.noLesson);
			feeDue = (TextView) convertView.findViewById(R.id.dueFess);
			feeCollect = (TextView) convertView.findViewById(R.id.feescollect);
			outstandingBalance = (TextView) convertView.findViewById(R.id.outstandingBalnce);

			outstnding_fess_text.setText(": $" +ei.getFee_outstanding());
			monthName.setText(""+ei.getMonthName());
			noLession.setText(": "+ei.getNoOfLessons());
			feeDue.setText(": $" + ei.getFeeDue());
			feeCollect.setText(": $" + ei.getFeeCollected());
			outstandingBalance.setText(": $" + ei.getOutstandingBalance());

			return convertView;
		}

	}


}