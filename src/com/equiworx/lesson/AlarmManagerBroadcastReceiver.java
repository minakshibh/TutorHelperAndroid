package com.equiworx.lesson;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.equiworx.tutorhelper.R;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	static StringBuilder msgStr;
	final public static String ONE_TIME = "onetime";

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		// Acquire the lock
		wl.acquire();

		// You can do the processing here update the widget/remote views.
		Bundle extras = intent.getExtras();
		msgStr = new StringBuilder();

		if (extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)) {
			msgStr.append("One time Timer : ");
		}
		Format formatter = new SimpleDateFormat("hh:mm:ss a");
		msgStr.append(formatter.format(new Date()));

		Toast.makeText(context, "balwinder11" + msgStr, Toast.LENGTH_LONG)
				.show();

		Bundle bundle = intent.getExtras();
		String itemName = bundle.getString("item_name");
		String reminderOrAlarmMessage = bundle.getString("message");
		String activityToTrigg = bundle.getString("activityToTrigg");
		int itemId = Integer.parseInt(bundle.getString("item_id"));
		NotificationManager nm = (NotificationManager) context
				.getSystemService("notification");
		CharSequence text = itemName + " " + itemId;
		System.out.append("notification is fired XXXXXXXXXXXXXXXX111111");
		System.out.append("notification is fired XXXXXXXXXXXXXXXX111111");
		System.out.append("notification is fired XXXXXXXXXXXXXXXX111111");
		Notification notification = new Notification(R.drawable.app_icon, text,
				System.currentTimeMillis());
		Intent newIntent = new Intent();
		newIntent.setAction(activityToTrigg);
		newIntent.putExtra("item_id", itemName);
		CharSequence text1 = itemName + " " + itemId;
		CharSequence text2 = reminderOrAlarmMessage;// context.getString(R.string.notif_Go_To_Details);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0,
				newIntent, 0);
		notification.setLatestEventInfo(context, text1, text2, pIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_ALL;
		nm.notify(1, notification);

		// Release the lock
		wl.release();

	}

	public void SetAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		intent.putExtra(ONE_TIME, Boolean.FALSE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		// After after 30 seconds
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}

	public void CancelAlarm(Context context) {
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	public void setOnetimeTimer(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		intent.putExtra(ONE_TIME, Boolean.TRUE);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}
}
