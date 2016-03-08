package com.equiworx.tutorhelper;

import java.util.List;
import java.util.Random;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.equiworx.notification.NotificationParentActivity;
import com.equiworx.notification.NotificationTutorActivity;
import com.equiworx.parent.ParentDashBoard;
import com.equiworx.tutor.TutorDashboard;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
public class GCMIntentService extends GCMBaseIntentService{
	//android:theme="@android:style/Theme.Dialog"
	MediaPlayer mp;
	static //private int count=0;
	SharedPreferences tutorPrefs;
	private static final String TAG = "GCMIntentService";
	public GCMIntentService() {
		super(Notification_Util.SENDER_ID);
	}
	int flag = 0;
	
	protected void onRegistered(Context context, String registrationId) {
		String str_role = null,str_userid=null;
		tutorPrefs= context.getSharedPreferences("tutor_prefs", 0);
		Log.e(TAG, "Device registered: regId = " + registrationId);
		Notification_Util.displayMessage(context, getString(R.string.gcm_registered));
		TelephonyManager tManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String udid = tManager.getDeviceId();
		if(tutorPrefs.getString("mode", "").equalsIgnoreCase("tutor"))
    	{
    	   str_role="Tutor";
    	   str_userid=tutorPrefs.getString("tutorID", "");
    	   
    	}else if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent"))
    	 {
    	   str_role="Parent";
    	   str_userid=tutorPrefs.getString("parentID", "");
       }
	ServerUtilities.register(context, "Android",str_role,registrationId,str_userid, udid);
	}


	protected void onUnregistered(Context context, String registrationId) {
		Log.e(TAG, "Device unregistered");
		Notification_Util.displayMessage(context, getString(R.string.gcm_unregistered));
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			//	             ServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}


	protected void onMessage(final Context context, Intent intent) {
		
		tutorPrefs = context.getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		/*String riderName = null;
		String sentMessage= null;
		String DriverName=null,driverMessage=null,driverImage=null,driver TripId=null,driverVehicleImage=null,driverLastData=null;	*/	

		//String message = getString(R.string.gcm_message);
		String message = intent.getStringExtra("message");
		//Log.e("tutor: On message", message);
		
		Notification_Util.displayMessage(context, message);

		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
		Log.d("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
		ComponentName componentInfo = taskInfo.get(0).topActivity;
		if(componentInfo.getPackageName().equalsIgnoreCase("com.equiworx.tutorhelper")){

			Log.e("inside application","app running");
			String mode=tutorPrefs.getString("mode", "");
			Log.e("modeeeeeeee",mode);
			if(tutorPrefs.getString("mode", "").equalsIgnoreCase("parent")){
				
				if(message.contains("requested to give a lesson"))	//lesson request 
				{
					goToParent(message);
					}
				else if(message.contains("Accepted the request for lesson"))	//accept lesson //issue
				{
					//Tutor Mrs. test3 have Accepted the request for lesson on 
					if(message.contains("Tutor"))
					{
						goToParent(message);
						}
					}
				else if(message.contains("Rejected the request for lesson"))	//reject lesson //issue
				{
					if(message.contains("Tutor"))
					{
						goToParent(message);
						}
					// Mrs. test3 have Rejected the request for lesson on 

					}
				else if(message.contains("connection request. Please check"))	//connection request
				{
					//amrik singh has sent you a connection request. Please check the mobile app in order to approve/reject the same.
					goToParent(message);
					}
				/*else if(message.contains("connection request has been approved"))	//connection approve //connection request has been approved
				{
					goToParent(message);
					}
				else if(message.contains("connection request Rejected"))	//connection reject
				{
					goToParent(message);
					}*/
				else if(message.contains("have been added to your account"))	//student request //done
				{
					goToParent(message);
					//Tutor Request to parent== A new student named happy have been added to your account by nav. 
					//Please check the mobile app in order to approve/reject the same.
					}
				/*else if(message.contains("have been Approved"))	//student request Approved  //done
				{
					// Your request for student creation for the student named dfgd have been Approved by Mrs test3
					goToParent(message);
					} 
				else if(message.contains("have been Rejected"))	//student request Rejected //done
				{
					//Your request for student creation for the student named new student. have been Rejected by Mrs test3
					goToParent(message);
					}*/
				else if(message.contains("lesson cancellation request has been approved"))	//lesson cancellation request for approved
				{
					//Your lesson cancellation request has been approved by Mr. amrik singh
					goToParent(message);
					}
				else if(message.contains("lesson cancellation request has been rejected"))	//lesson cancellation request for rejected
				{
					goToParent(message);
					//Your lesson cancellation request has been rejected by Mr. amrik singh
					}
				else
				{
					Log.e("not show parenttttttttt notification", message);
					}
					
				
				}
			
			
			
			
			
			
			
			
			
			
			
			else if(tutorPrefs.getString("mode", "").equalsIgnoreCase("tutor")){
				if(message.contains("requested a lesson"))	//lesson request //done
				{
					goToTutor(message);
					}
				else if(message.contains("Accepted the request for lesson"))	//accept lesson //issue
				{
					//Mrs. test3 have Accepted the request for lesson on 
					if(message.contains("Parent"))
					{
						goToParent(message);
						}
					}
				else if(message.contains("Rejected the request for lesson"))	//reject lesson  //issue
				{
					//Mrs. test3 have Rejected the request for lesson on 
				
					if(message.contains("Parent"))
					{
						goToParent(message);
						}
					}
				/*else if(message.contains("connection request"))	//connection request  //done
				{
					//amrik singh has sent you a connection request. Please check the mobile app in order to approve/reject the same.
					goToTutor(message);
					}*/
				else if(message.contains("connection request has been approved"))	//connection approve //connection request has been approved
				{
					goToTutor(message);
					}
				else if(message.contains("connection request rejected"))	//connection reject
				{
					goToTutor(message);
					}
				/*else if(message.contains("have been added to your account"))	//student request //done
				{
					goToTutor(message);
					//Tutor Request to parent== A new student named happy have been added to your account by nav. 
					//Please check the mobile app in order to approve/reject the same.
					
					}*/
					
				else if(message.contains("have been Approved"))	//student request Approved
				{
					// Your request for student creation for the student named dfgd have been Approved by Mrs test3

					goToTutor(message);
					} 
				else if(message.contains("have been Rejected"))	//student request Rejected
				{
					//Your request for student creation for the student named new student. have been Rejected by Mrs test3

					goToTutor(message);
					}
				else if(message.contains("have cancelled the request for lesson on"))	
				{
					goToTutor(message);
					}
				else{
						Log.e("not show tutor notification", message);
						}	
					
				}
		}
		else {
		
			generateNotification(context, message);
			Log.e("message", message);
			
		
		}
	}
protected void goToParent(String message ){
			
		Intent launchDialog = new Intent(this.getApplicationContext(), NotificationParentActivity.class);
		launchDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		launchDialog.putExtra("message", message);
		startActivity(launchDialog);
	}
protected void goToTutor(String message ){
		Intent launchDialog = new Intent(this.getApplicationContext(), NotificationTutorActivity.class);
		launchDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		launchDialog.putExtra("message", message);
		startActivity(launchDialog);
	}
	protected void onDeletedMessages(Context context, int total) {
		Log.e(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		Notification_Util.displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	public void onError(Context context, String errorId) {
		Log.e(TAG, "Received error: " + errorId);
		Notification_Util.displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.e(TAG, "Received recoverable error: " + errorId);
		Notification_Util.displayMessage(context, getString(R.string.gcm_recoverable_error,  errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**Issues a notification to inform the user that server has sent a message. */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {

		Intent notificationIntent = null;
		int icon = R.drawable.app_icon;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);
		System.err.println("title="+title);
		System.err.println("message="+message);
		String ss= tutorPrefs.getString("mode", "");
		 if(ss.equalsIgnoreCase("tutor"))
         {
        	 notificationIntent = new Intent(context, TutorDashboard.class);
         	 Editor e=tutorPrefs.edit();
	    	 e.putString("message", message);
	    	 e.commit();
         }
         else if(ss.equalsIgnoreCase("parent"))
         {
        	notificationIntent = new Intent(context, ParentDashBoard.class);
        
	    	 Editor e=tutorPrefs.edit();
	    	 e.putString("message", message);
	    	 e.commit();
//        	notificationIntent.putExtra("Message",message);
        	Log.i("tag", "Send Message on Map View:"+message);
         }
	
		//notificationIntent = new Intent(context, HomeAcitivity.class);
			
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.drawable.app_icon;

		Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = notificationSound;
	


		Random r = new Random();
		int random = r.nextInt(100);
		notificationManager.notify(message.length() + random, notification);	     
	}
	

}
