package com.equiworx.parent;

import java.util.ArrayList;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Connection;
import com.equiworx.model.StudentRequest;
import com.equiworx.student.StudentRequestActivity;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperParser;
import com.equiworx.util.Util;

public class ConnectionRequests extends Activity implements
		AsyncResponseForTutorHelper {

	private ArrayList<Connection> array_connection;
	private ConnectionAdapter adapter;
	private ListView connection_listview;
	private TutorHelperParser parser;
	private RelativeLayout back_layout;
	private TextView title;
	private SharedPreferences tutorPrefs;

	private String parentId, trigger, message = "";

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connectionrequest);

		fetchlConnectionList();
		setOnClick();

	}

	private void setOnClick() {
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		title=(TextView)findViewById(R.id.title);
		title.setText("Connection Requests");
		back_layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void fetchlConnectionList() {
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		parser = new TutorHelperParser(ConnectionRequests.this);
		connection_listview = (ListView) findViewById(R.id.list_request);

		if (Util.isNetworkAvailable(ConnectionRequests.this)) {
			/*
			 * ParentId/TutorId Trigger -- Parent/Tutor
			 */
			
			trigger = getIntent().getStringExtra("trigger");

			if(trigger.equalsIgnoreCase("Tutor"))
			{
				parentId = tutorPrefs.getString("tutorID","");
			}
			else{
				parentId = tutorPrefs.getString("parentID","");
			}
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("parent_id", parentId));
			nameValuePairs.add(new BasicNameValuePair("trigger", trigger));
			Log.e("fetch-connection-request", nameValuePairs.toString());
			AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
					ConnectionRequests.this, "fetch-connection-request",
					nameValuePairs, true, "Please wait...");
			mLogin.delegate = (AsyncResponseForTutorHelper) ConnectionRequests.this;
			mLogin.execute();
		} else {
			Util.alertMessage(ConnectionRequests.this,
					"Please check your internet connection");
		}
	}

	public class ConnectionAdapter extends BaseAdapter {
		private Context context;
		private TextView tv_name, tv_id;
		private Button iv_connect, iv_reject;
		private Connection connection;

		public ConnectionAdapter(Context ctx) {
			context = ctx;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return array_connection.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return array_connection.get(position);
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
				convertView = inflater.inflate(R.layout.connection_row, parent,
						false);
			}

			connection = array_connection.get(position);

			tv_name = (TextView) convertView.findViewById(R.id.name);
			tv_id = (TextView) convertView.findViewById(R.id.id);
			iv_connect = (Button) convertView.findViewById(R.id.imageView_connect);
			iv_reject = (Button) convertView.findViewById(R.id.imageView_reject);
			if(trigger.equalsIgnoreCase("tutor"))
			{
				iv_connect.setVisibility(View.INVISIBLE);
				iv_reject.setText("RESEND");
				tv_id.setText("Parent ID : " + connection.getParentId());
				tv_name.setText( "you has sent to "+connection.getParentName()+" a connection request.");
			}
			else{
				iv_connect.setVisibility(View.VISIBLE);	
				iv_connect.setText("CONNECT");
				tv_id.setText("Tuter ID : " + connection.getTutorId());
				tv_name.setText(connection.getTutorName()
						+ " has sent you a connection request.");
			}
			
			

		
			
			
			iv_connect.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// /approve-connection-request.php
					if (Util.isNetworkAvailable(ConnectionRequests.this)) {
						/*
						 * ParentId/TutorId Trigger -- Parent/Tutor
						 */
						message = "Request approved successfully";
						ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("request_id",array_connection.get(position).getRequestId()));
						nameValuePairs.add(new BasicNameValuePair("status",
								"Approved"));
						Log.e("approve", nameValuePairs.toString());
						AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
								ConnectionRequests.this,
								"approve-connection-request", nameValuePairs,
								true, "Please wait...");
						mLogin.delegate = (AsyncResponseForTutorHelper) ConnectionRequests.this;
						mLogin.execute();
					} else {
						Util.alertMessage(ConnectionRequests.this,
								"Please check your internet connection");
					}
				}
			});
			
			iv_reject.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					
						if (Util.isNetworkAvailable(ConnectionRequests.this)) {
							
							if(trigger.equalsIgnoreCase("tutor"))
							{
							
								
								ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
								nameValuePairs.add(new BasicNameValuePair("pin",array_connection.get(position).getParentId()));
							
								Log.e("resend-parent-details", nameValuePairs.toString());
								
								AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
										ConnectionRequests.this,
										"resend-parent-details", nameValuePairs,
										true, "Please wait...");
								mLogin.delegate = (AsyncResponseForTutorHelper) ConnectionRequests.this;
								mLogin.execute();
							}
							else{
								
								
								ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
								nameValuePairs.add(new BasicNameValuePair("request_id",array_connection.get(position).getRequestId()));
								nameValuePairs.add(new BasicNameValuePair("status",
										"Rejected"));
								Log.e("approve", nameValuePairs.toString());
								AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
										ConnectionRequests.this,
										"approve-connection-request", nameValuePairs,
										true, "Please wait...");
								mLogin.delegate = (AsyncResponseForTutorHelper) ConnectionRequests.this;
								mLogin.execute();
								
								
							}
						} else {
							Util.alertMessage(ConnectionRequests.this,"Please check your internet connection");
						}
					}
				
			});
			return convertView;
		}
	}

	@Override
	public void processFinish(String output, String methodName) {
		Log.e("connection=", output);
		if (methodName.equalsIgnoreCase("fetch-connection-request")) {
			array_connection = new ArrayList<Connection>();

			array_connection = parser.getConnectionInfo(output);

			if (array_connection.size() == 0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						ConnectionRequests.this);
				alert.setTitle("Tutor Helper");
				alert.setMessage("No connection requests");
				alert.setCancelable(false);
				alert.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								finish();
							}
						});
				alert.show();
			} else {
				adapter = new ConnectionAdapter(ConnectionRequests.this);
				connection_listview.setAdapter(adapter);
			}
		} else if (methodName.equalsIgnoreCase("approve-connection-request")) {
			// fetchlConnectionList();
			try {

				JSONObject jsonChildNode = new JSONObject(output);

				String result = jsonChildNode.getString("result").toString();
				// greatest_last_updated =
				// jsonChildNode.getString("greatest_last_updated").toString();
				String jsonmessage = jsonChildNode.getString("message")
						.toString();
				if (result.equalsIgnoreCase("0")) {
					/*Log.e("lesson request", output);

					AlertDialog.Builder alert = new AlertDialog.Builder(
							ConnectionRequests.this);
					alert.setTitle("Tutor Helper");
					alert.setMessage(message);
					alert.setCancelable(false);
					alert.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {*/

									if(message.equalsIgnoreCase("Request approved successfully"))
										{
											AlertDialog.Builder alert = new AlertDialog.Builder(
													ConnectionRequests.this);
											alert.setTitle("Tutor Helper");
											alert.setMessage("Your connection request approved successfully,Now please approve corresponding student requests.");
											alert.setCancelable(false);
											alert.setPositiveButton("ok",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface arg0,
																int arg1) {
											
														Intent intent=new Intent(ConnectionRequests.this,StudentRequestActivity.class);
														startActivity(intent);
														finish();
															
														}
													});
											alert.show();
										}
									else{
									
										AlertDialog.Builder alert = new AlertDialog.Builder(
												ConnectionRequests.this);
										alert.setTitle("Tutor Helper");
										alert.setMessage("Your connection request rejected successfully.");
										alert.setCancelable(false);
										alert.setPositiveButton("ok",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface arg0,
															int arg1) {
										
														finish();
														
													}
												});
										alert.show();
									}
							/*	}
							});
					alert.show();*/
				} else {
					Util.alertMessage(ConnectionRequests.this, jsonmessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else if(methodName.equalsIgnoreCase("resend-parent-details"))
		{
			finish();
				}

	}
}
