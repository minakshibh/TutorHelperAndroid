package com.equiworx.lesson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.equiworx.asynctasks.AsyncResponseForTutorHelper;
import com.equiworx.asynctasks.AsyncTaskForTutorHelper;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.parent.ParentDashBoard;
import com.equiworx.tutor.TutorDashboard;
import com.equiworx.tutorhelper.R;
import com.equiworx.util.TutorHelperDatabaseHandler;
import com.equiworx.util.Util;

public class AddLessonActivity extends Activity implements
		AsyncResponseForTutorHelper {

	private Button btn_done;
	private CheckBox checkbox_sun, checkbox_mon, checkbox_tus, checkbox_wed,
			checkbox_thur, checkbox_sat, checkbox_fri, checkbox_all;
	private EditText ed_description;
	private RadioGroup radiogroup;
	private TextView btn_editst, btn_editet, ed_lessondate, ed_Lessonenddate;
	private TextView txt_title, txt_students, addStudent;

	private String str_starttime = "", str_endtime = "", str_day = "",
			str_tutorid = "", str_setdate2 = "", str_setdate3 = "",
			str_setdate4 = "", str_setdate5 = "", str_setdate6 = "",
			str_setdate1 = "", str_setdate0;
	private TextView txt_sun, txt_mon, txt_tus, txt_wed, txt_thur, txt_sat,
			txt_fri;
	private String str_isrec = "YES";
	private String str_duration = "", str_startdate;
	public String str_students_list = "";
	private int mHour, mMinute, mYear, mMonth, mDay;
	private int sYear, sMonth, sDay;
	public static ArrayList<String> arraylist_addstu = new ArrayList<String>();
	private String first_enddate, str_reqsender = "";
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat timeformatter = new SimpleDateFormat("HH:mm");
	private SharedPreferences tutorPrefs;
	private static int datecheck = 0;
	private int datecheck2 = 0;
	private int timecheck = 0, timecheck2 = 0;
	private LinearLayout lay_endtime_edit, lay_starttime_edit, lay_Lessondate,
			lay_Lessonenddate;
	// private LinearLayout
	// lay_sun,lay_mon,lay_tue,lay_wed,lay_thu,lay_sat,lay_fri,lay_all;
	static ArrayList<Activity> AddActivities;
	private ArrayList<String> arraylist_days;
	private ArrayList<Integer> arraylistdate;
	private int hours, min, days;

	private RelativeLayout back_layout;
	private int date_overlap = 0;
	private String lesson_des = "";
	private TutorHelperDatabaseHandler dbHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addlesson);

		initialiselayout();
		onClickListeners();

	}

	@Override
	protected void onResume() {

		txt_students = (TextView) findViewById(R.id.textView_students);
		txt_students.setText("" + arraylist_addstu.size() + " Students");
		super.onResume();
	}

	private void initialiselayout() {
		// Initialize element
		dbHandler = new TutorHelperDatabaseHandler(AddLessonActivity.this);
		arraylistdate = new ArrayList<Integer>();
		AddActivities = new ArrayList<Activity>();
		arraylist_days = new ArrayList<String>();
		AddActivities.add(AddLessonActivity.this);

		arraylist_addstu.clear();
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);
		txt_title = (TextView) findViewById(R.id.title);
		txt_title.setText("Add Lesson");
		ed_description = (EditText) findViewById(R.id.desription);
		ed_lessondate = (TextView) findViewById(R.id.ed_Lessondate);
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);

		String Student = "0";
		Log.i("tag", "Student LIST::::::::::" + arraylist_addstu.size());
		for (int i = 0; i < arraylist_addstu.size(); i++) {
			if (i == 0) {
				Student = arraylist_addstu.get(i);
			} else if (i > 0) {
				Student = Student + "," + arraylist_addstu.get(i);
			}

		}

		btn_done = (Button) findViewById(R.id.done);
		addStudent = (TextView) findViewById(R.id.addstudents);
		String str = "Select Students:";
		SpannableString content = new SpannableString(str);
		content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
		addStudent.setText(content);

		btn_editst = (TextView) findViewById(R.id.starttime_edit);
		btn_editet = (TextView) findViewById(R.id.endtime_edit);
		lay_endtime_edit = (LinearLayout) findViewById(R.id.lay_endtime_edit);
		lay_starttime_edit = (LinearLayout) findViewById(R.id.lay_starttime_edit);

		lay_Lessondate = (LinearLayout) findViewById(R.id.lay_Lessondate);
		lay_Lessonenddate = (LinearLayout) findViewById(R.id.lay_Lessonenddate);
		ed_Lessonenddate = (TextView) findViewById(R.id.ed_Lessonenddate);
		radiogroup = (RadioGroup) findViewById(R.id.radio_isrec);
		// check box
		checkbox_all = (CheckBox) findViewById(R.id.checkBox_all);
		checkbox_sun = (CheckBox) findViewById(R.id.checkBox_sun);
		checkbox_mon = (CheckBox) findViewById(R.id.checkBox_mon);
		checkbox_tus = (CheckBox) findViewById(R.id.checkBox_tue);
		checkbox_wed = (CheckBox) findViewById(R.id.checkBox_wed);
		checkbox_thur = (CheckBox) findViewById(R.id.checkBox_thur);
		checkbox_fri = (CheckBox) findViewById(R.id.checkBox_fri);
		checkbox_sat = (CheckBox) findViewById(R.id.checkBox_sat);

		/*
		 * lay_mon=(LinearLayout)findViewById(R.id.lay_mon);
		 * lay_tue=(LinearLayout)findViewById(R.id.lay_tue);
		 * lay_wed=(LinearLayout)findViewById(R.id.lay_wed);
		 * lay_thu=(LinearLayout)findViewById(R.id.lay_thu);
		 * lay_fri=(LinearLayout)findViewById(R.id.lay_fri);
		 * lay_sat=(LinearLayout)findViewById(R.id.lay_sat);
		 * lay_sun=(LinearLayout)findViewById(R.id.lay_sun);
		 */

		txt_sun = (TextView) findViewById(R.id.Sunday);
		txt_mon = (TextView) findViewById(R.id.Monday);
		txt_tus = (TextView) findViewById(R.id.Tuesday);
		txt_wed = (TextView) findViewById(R.id.Wednesday);
		txt_thur = (TextView) findViewById(R.id.Thursday);
		txt_sat = (TextView) findViewById(R.id.Saturday);
		txt_fri = (TextView) findViewById(R.id.Friday);

		if (str_isrec.equalsIgnoreCase("no")) {
			lay_Lessonenddate.setEnabled(false);
		} else {
			lay_Lessonenddate.setEnabled(true);
		}

	}

	private void onClickListeners() {
		getCurrentTime();
		back_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tutorPrefs.getString("mode", "").equalsIgnoreCase("parent")) {

					finish();
				} else if (tutorPrefs.getString("mode", "").equalsIgnoreCase(
						"tutor")) {
					Intent intent = new Intent(AddLessonActivity.this,
							TutorDashboard.class);
					startActivity(intent);
					finish();
				}
			}
		});

		// for start time.............
		mMinute = mintuecheck(mMinute);
		String stime = mHour + ":" + mMinute;

		Date date = null;
		try {
			date = (Date) timeformatter.parse(stime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		str_starttime = timeformatter.format(date);
		btn_editst.setText(str_starttime);

		lay_starttime_edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				TimePickerDialog tpd = new TimePickerDialog(
						AddLessonActivity.this,
						new TimePickerDialog.OnTimeSetListener() {
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								minute = mintuecheck(minute);

								String stime = hourOfDay + ":" + minute;
								String etime = hourOfDay + 1 + ":" + minute;
								mHour = hourOfDay;
								mMinute = minute;
								Date date = null, date1 = null;
								try {
									date = (Date) timeformatter.parse(stime);
									date1 = (Date) timeformatter.parse(etime);

									// timeformatter = new
									// SimpleDateFormat("HH:mm:ss");
									str_starttime = timeformatter.format(date);
									String endtime = timeformatter
											.format(date1);
									btn_editst.setText(str_starttime);

									btn_editet.setText(endtime);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								/*
								 * //check for time...... try{ Calendar c =
								 * Calendar.getInstance();
								 * System.out.println("Current time => " +
								 * c.getTime());
								 * 
								 * String currentdate =
								 * timeformatter.format(c.getTime());
								 * 
								 * Date seldate =
								 * timeformatter.parse(str_starttime); Date
								 * curdate = timeformatter.parse(currentdate);
								 * 
								 * if (curdate.before(seldate)) {
								 * System.out.println
								 * ("date2 is Greater than my date1");
								 * 
								 * } else if(curdate.equals(seldate)) { //
								 * Util.alertMessage(AddLesson.this,
								 * "Select date Greater than start lesson date"
								 * ); //
								 * ed_Lessonenddate.setText("select lesson end date"
								 * ); } else { if(datecheck==1) {
								 * 
								 * } else{ System.err.println(
								 * "Please select Start time Greater from current time"
								 * ); Util.alertMessage(AddLessonActivity.this,
								 * "Please select Start time Greater from current time"
								 * ); btn_editst.setText("select start time"); }
								 * }
								 * 
								 * }catch (ParseException e1){
								 * e1.printStackTrace(); }
								 */
							}

						}, mHour, mMinute, true);

				tpd.show();
				tpd.setTitle("Select start time");

			}
		});

		// for end time..............
		getCurrentTime();
		mMinute = mintuecheck(mMinute);
		String str_etime = (mHour + 1) + ":" + (mMinute);

		Date dateendt = null;
		try {
			dateendt = (Date) timeformatter.parse(str_etime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// timeformatter = new SimpleDateFormat("HH:mm:ss");
		str_endtime = timeformatter.format(dateendt);

		btn_editet.setText(str_endtime);
		lay_endtime_edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TimePickerDialog tpd = new TimePickerDialog(
						AddLessonActivity.this,
						new TimePickerDialog.OnTimeSetListener() {

							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// Display Selected time in textbox
								minute = mintuecheck(minute);

								String stime = hourOfDay + ":" + minute;
								mHour = hourOfDay;
								mMinute = minute;
								Date date = null;
								try {
									date = (Date) timeformatter.parse(stime);
									// timeformatter = new
									// SimpleDateFormat("HH:mm:ss");
									str_endtime = timeformatter.format(date);
									btn_editet.setText("" + str_endtime);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								// check for time........
								try {
									Calendar c = Calendar.getInstance();
									System.out.println("Current time => "
											+ c.getTime());

									// String currentdate =
									// timeformatter.format(c.getTime());

									Date seldate = timeformatter
											.parse(str_endtime);
									Date curdate = timeformatter
											.parse(str_starttime);

									if (curdate.before(seldate)) {
										System.out
												.println("date2 is Greater than my date1");

									} else if (curdate.equals(seldate)) {
										timecheck2 = 1;

									} else {
										timecheck2 = 1;
										// Util.alertMessage(AddLessonActivity.this,
										// "Please select Greater time from lesson start time");
										// btn_editet.setText("select end time");
									}

								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								if (timecheck2 == 1) {

									Util.alertMessage(AddLessonActivity.this,
											"Please select end time Greater from lesson start time");
									btn_editet.setText("select end time");
									timecheck2 = 0;
								}

							}
						}, mHour, mMinute, true);
				tpd.setTitle("Select end time");
				tpd.show();

			}
		});

		// end time end.............
		// check box.............
		checkbox_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					checkbox_sun.setChecked(true);
					checkbox_mon.setChecked(true);
					checkbox_tus.setChecked(true);
					checkbox_wed.setChecked(true);
					checkbox_thur.setChecked(true);
					checkbox_fri.setChecked(true);
					checkbox_sat.setChecked(true);
				} else {
					checkbox_sun.setChecked(false);
					checkbox_mon.setChecked(false);
					checkbox_tus.setChecked(false);
					checkbox_wed.setChecked(false);
					checkbox_thur.setChecked(false);
					checkbox_fri.setChecked(false);
					checkbox_sat.setChecked(false);
				}

			}
		});

		checkbox_mon.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					String getdate = null;
					// str_mon="Monday,";
					arraylist_days.add("Monday");
					try {
						getdate = str_setdate0.replace("-", "");
						System.err.println("getdate=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_mon="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Monday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}
				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_tus.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_tus="Tuesday,";
					arraylist_days.add("Tuesday");
					try {
						String getdate = str_setdate1.replace("-", "");
						System.err.println("getdate=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_tus="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Tuesday")) {
							arraylist_days.remove(i);

							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}
				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_wed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_wed="Wednesday,";
					arraylist_days.add("Wednesday");
					try {
						String getdate = str_setdate2.replace("-", "");
						System.err.println("getdate=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_wed="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Wednesday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}

				// str_day=str_sun+str_mon+str_tus+str_wed+str_thur+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_thur.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_thur="Thursday,";
					arraylist_days.add("Thursday");
					try {
						String getdate = str_setdate3.replace("-", "");
						System.err.println("getday=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_thur="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Thursday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}

				}
				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_fri.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_fri="Friday,";
					arraylist_days.add("Friday");
					try {
						String getdate = str_setdate4.replace("-", "");
						System.err.println("getdate=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_fri="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Friday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				}

				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_sat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_sat="Saturday,";
					arraylist_days.add("Saturday");
					try {
						String getdate = str_setdate5.replace("-", "");
						System.err.println("getdate=" + getdate);
						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);
							// date = (Date)formatter.parse(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					// str_sat="";
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Saturday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
					}

				}
				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		checkbox_sun.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// str_sun="Sunday,";
					arraylist_days.add("Sunday");
					try {
						String getdate = str_setdate6.replace("-", "");
						System.err.println("getdate=" + getdate);

						arraylistdate.add(Integer.parseInt(getdate));
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (str_isrec.equalsIgnoreCase("no")) {

						Collections.sort(arraylistdate); // Sort the arraylist
						String bigdate = ""
								+ arraylistdate.get(arraylistdate.size() - 1);
						Date date = null;
						try {
							DateFormat dtimeformatter = new SimpleDateFormat(
									"yyyyMMdd");
							date = (Date) dtimeformatter.parse(bigdate);

						} catch (ParseException e) {
							e.printStackTrace();
						}

						ed_Lessonenddate.setText(formatter.format(date));

					}
				} else {
					for (int i = 0; i < arraylist_days.size(); i++) {
						if (arraylist_days.get(i).equalsIgnoreCase("Sunday")) {
							arraylist_days.remove(i);
							try {
								arraylistdate.remove(i);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}

					}
					// str_sun="";
				}
				// str_day=str_sun+str_mon+str_tus+str_thur+str_wed+str_fri+str_sat;
				System.err.println("total=" + arraylist_days.toString());
			}
		});
		// check box end.............

		// lesson start date.............
		getCurretDate();

		String sdate = mYear + "-" + (mMonth + 1) + "-" + mDay;
		Date date1 = null;
		try {
			date1 = (Date) formatter.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		str_startdate = formatter.format(date1);
		ed_lessondate.setText(str_startdate);

		lay_Lessondate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DatePickerDialog dpd = new DatePickerDialog(
						AddLessonActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// long currentdateintoInt=
								// System.currentTimeMillis();
								String sdate = year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth;
								mYear = year;
								mMonth = monthOfYear;
								mDay = dayOfMonth;
								Date date = null;
								try {
									date = (Date) formatter.parse(sdate);
									datecheck = 0;
									formatter = new SimpleDateFormat(
											"yyyy-MM-dd");
									str_startdate = formatter.format(date);
									ed_lessondate.setText("" + str_startdate);
									getNextEnddate(str_startdate, 1);
									// ed_Lessonenddate.setText("select end date");
								} catch (ParseException e) {
									e.printStackTrace();
								}

								// check date condition.........
								resetCheckbox(); // reset check box
								try {
									Calendar c = Calendar.getInstance();
									System.out.println("Current date => "
											+ c.getTime());
									String currentdate = formatter.format(c
											.getTime());
									Date seldate = formatter
											.parse(str_startdate);
									System.out.println("Select date => "
											+ seldate);
									Date curdate = formatter.parse(currentdate);
									if (curdate.before(seldate)) {
										System.out
												.println("date2 is Greater than my date1");
										datecheck = 1;
									} else if (curdate.equals(seldate)) {
										datecheck = 0;
										// check for start time......
										try {
											Calendar ctime = Calendar
													.getInstance();
											System.out
													.println("Current time => "
															+ ctime.getTime());

											String currenttime = timeformatter
													.format(ctime.getTime());

											Date seltime = timeformatter
													.parse(str_starttime);
											Date curtime = timeformatter
													.parse(currenttime);

											if (curtime.before(seltime)) {
												System.out
														.println("date2 is Greater than my date1");

											} else if (curtime.equals(seltime)) {
												// Util.alertMessage(AddLesson.this,
												// "Select date Greater than start lesson date");
												// ed_Lessonenddate.setText("select lesson end date");
											} else {
												if (datecheck == 1) {

												} else {
													System.err
															.println("Please select Start time Greater from current time");
													Util.alertMessage(
															AddLessonActivity.this,
															"Time "
																	+ btn_editst
																			.getText()
																			.toString()
																	+ " is passed away. Please select future time.");
													btn_editst
															.setText("select start time");
												}
											}

										} catch (ParseException e1) {
											e1.printStackTrace();
										}

									} else {
										Util.alertMessage(
												AddLessonActivity.this,
												"Please select lesson start date Greater from current date");
										ed_lessondate
												.setText("select start date");
										ed_Lessonenddate
												.setText("select end date");
									}

								} catch (ParseException e1) {
									e1.printStackTrace();
								}

							}
						}, mYear, mMonth, mDay);
				dpd.setTitle("Select lesson start date");
				dpd.show();
			}
		});

		// for lesson end date
		gettomarrowDate();
		String edate = sYear + "-" + (sMonth + 1) + "-" + sDay;
		Date dateend = null;
		try {
			dateend = (Date) formatter.parse(edate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		first_enddate = formatter.format(dateend);

		ed_Lessonenddate.setText(first_enddate);
		findDate();// select days
		lay_Lessonenddate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(
						AddLessonActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								String sdate = year + "-" + (monthOfYear + 1)
										+ "-" + dayOfMonth;
								mYear = year;
								mMonth = monthOfYear;
								mDay = dayOfMonth;
								Date date = null;
								try {
									date = (Date) formatter.parse(sdate);
								} catch (ParseException e) {
									e.printStackTrace();
								}

								formatter = new SimpleDateFormat("yyyy-MM-dd");
								String s = formatter.format(date);
								ed_Lessonenddate.setText("" + s);

								// check date..........
								try {
									Date seldate = formatter.parse(s);
									Date curdate = formatter
											.parse(str_startdate);

									System.out.println("select date => "
											+ seldate);
									System.out.println("start date => "
											+ curdate);
									if (curdate.before(seldate)) {
										System.out
												.println("selected date is Greater than start date");
										resetCheckbox();
										findDate();
									} else if (curdate.equals(seldate)) {
										datecheck2 = 1;

									} else {
										datecheck2 = 1;
										// selected();

									}

								} catch (ParseException e1) {
									e1.printStackTrace();

								}
								if (datecheck2 == 1) {
									Util.alertMessage(AddLessonActivity.this,
											"Please select lesson end date Greater from lesson start date");
									ed_Lessonenddate.setText("select end date");
									datecheck2 = 0;
								}
							}
						}, mYear, mMonth, mDay);
				dpd.setTitle("Select lesson end date");
				dpd.show();
			}
		});
		radiogroup
				.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.radioButton_yes:
							// do operations specific to this selection
							str_isrec = "YES";
							System.err.println("str=" + str_isrec);
							lay_Lessonenddate.setEnabled(true);

							break;

						case R.id.radioButton_no:
							str_isrec = "NO";
							System.err.println("str=" + str_isrec);
							lay_Lessonenddate.setEnabled(false);
							break;
						}
					}
				});

		addStudent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				arraylist_addstu.clear();
				Intent i = new Intent(AddLessonActivity.this,
						StudentListActivity.class);
				i.putExtra("parentID", getIntent().getStringExtra("parentID"));
				i.putExtra("tuterID", getIntent().getStringExtra("tuterID"));
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);

			}
		});

		btn_done.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				getdays();
				String check = emptyFieldCheck();

				for (int i = 0; i < arraylist_addstu.size(); i++) {
					if (i == 0) {
						str_students_list = arraylist_addstu.get(i);
					} else if (i > 0) {
						str_students_list = str_students_list + ","
								+ arraylist_addstu.get(i);
					}
				}

				if (check.equals("success")) {
					timecheck();
					if (datecheck == 1) {
						Add_Lesson();
					} else {
						if (timecheck == 1) {
							Add_Lesson();
						} else {
							Util.alertMessage(AddLessonActivity.this,
									"Please select start time Greater from current time");
						}

					}

				} else
					Util.alertMessage(AddLessonActivity.this, check);
			}
		});

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void resetCheckbox() {
		str_day = "";
		checkbox_sun.setChecked(false);
		checkbox_mon.setChecked(false);
		checkbox_tus.setChecked(false);
		checkbox_wed.setChecked(false);
		checkbox_thur.setChecked(false);
		checkbox_fri.setChecked(false);
		checkbox_sat.setChecked(false);
		checkbox_all.setChecked(false);
	}

	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/*
	 * private void findDatetoweekday() { if(check) }
	 */
	private void findDate() {
		String lesson_startdate = ed_lessondate.getText().toString().trim();
		String lesson_enddate = ed_Lessonenddate.getText().toString().trim();
		Date l_startdate = null, l_enddate = null;
		try {
			DateFormat dtimeformatter = new SimpleDateFormat("yyyy-MM-dd");
			l_startdate = (Date) dtimeformatter.parse(lesson_startdate);
			l_enddate = (Date) dtimeformatter.parse(lesson_enddate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(l_startdate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(l_enddate);

		System.err.println("cal=1=====" + cal1);
		System.err.println("cal2======" + cal2);
		int day = getDaysDifference(cal1, cal2);

		int dayname = getDay(l_startdate);
		System.err.println("dayyyyyyyyyyyyy======" + day);
		System.err.println("day nameeeeee======" + dayname);

		if (day >= 7) {

			txt_sun.setTextColor(getResources().getColor(R.color.black));
			txt_mon.setTextColor(getResources().getColor(R.color.black));
			txt_tus.setTextColor(getResources().getColor(R.color.black));
			txt_wed.setTextColor(getResources().getColor(R.color.black));
			txt_thur.setTextColor(getResources().getColor(R.color.black));
			txt_sat.setTextColor(getResources().getColor(R.color.black));
			txt_fri.setTextColor(getResources().getColor(R.color.black));

			checkbox_sun.setEnabled(true);
			checkbox_mon.setEnabled(true);
			checkbox_tus.setEnabled(true);
			checkbox_wed.setEnabled(true);
			checkbox_thur.setEnabled(true);
			checkbox_fri.setEnabled(true);
			checkbox_sat.setEnabled(true);
			checkbox_all.setVisibility(View.VISIBLE);
		} else if (day == 1) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);
			String getday = "" + l_startdate.getDay();
			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());

			if (getday.equalsIgnoreCase("1"))// Tuesday ==1
			{
				checkbox_tus.setEnabled(true);
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("2")) {
				checkbox_wed.setEnabled(true);
				txt_wed.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("3")) {
				checkbox_thur.setEnabled(true);
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_fri.setEnabled(true);
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("5")) {
				checkbox_sat.setEnabled(true);
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("6")) {
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				checkbox_sun.setEnabled(true);

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
			} else if (getday.equalsIgnoreCase("0")) { // Monday==0
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				checkbox_mon.setEnabled(true);

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
			}

		}

		else if (day == 2) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);
			String getday = "" + l_startdate.getDay();

			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());

			if (getday.equalsIgnoreCase("1"))// Tuesday==1
			{

				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
				str_setdate2 = "" + getNextday(firstdate, 2);

			} else if (getday.equalsIgnoreCase("2")) {
				checkbox_thur.setEnabled(true);
				checkbox_wed.setEnabled(true);
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
				str_setdate3 = "" + getNextday(firstdate, 2);
			} else if (getday.equalsIgnoreCase("3")) {
				checkbox_fri.setEnabled(true);
				checkbox_thur.setEnabled(true);
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
				str_setdate4 = "" + getNextday(firstdate, 2);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_sat.setEnabled(true);
				checkbox_fri.setEnabled(true);
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
				str_setdate5 = "" + getNextday(firstdate, 2);
			} else if (getday.equalsIgnoreCase("5")) {
				checkbox_sun.setEnabled(true);
				checkbox_sat.setEnabled(true);
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
				str_setdate6 = "" + getNextday(firstdate, 2);
			} else if (getday.equalsIgnoreCase("6")) {
				checkbox_mon.setEnabled(true);
				checkbox_sun.setEnabled(true);
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
				str_setdate0 = "" + getNextday(firstdate, 2);
			} else if (getday.equalsIgnoreCase("0")) {
				checkbox_tus.setEnabled(false);
				checkbox_mon.setEnabled(false);
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
				str_setdate1 = "" + getNextday(firstdate, 2);
			}

		}

		else if (day == 3) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);

			String getday = "" + l_startdate.getDay();
			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());
			if (getday.equalsIgnoreCase("1"))// Monday
			{

				checkbox_thur.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
				str_setdate2 = "" + getNextday(firstdate, 2);
				str_setdate3 = "" + getNextday(firstdate, 3);
			} else if (getday.equalsIgnoreCase("2"))// Tuesday
			{
				checkbox_fri.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
				str_setdate3 = "" + getNextday(firstdate, 2);
				str_setdate4 = "" + getNextday(firstdate, 3);
			} else if (getday.equalsIgnoreCase("3"))//
			{
				checkbox_sat.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
				str_setdate4 = "" + getNextday(firstdate, 2);
				str_setdate5 = "" + getNextday(firstdate, 3);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_sun.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
				str_setdate5 = "" + getNextday(firstdate, 2);
				str_setdate6 = "" + getNextday(firstdate, 3);

			} else if (getday.equalsIgnoreCase("5")) {

				checkbox_mon.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
				str_setdate6 = "" + getNextday(firstdate, 2);
				str_setdate0 = "" + getNextday(firstdate, 3);
			} else if (getday.equalsIgnoreCase("6")) {
				checkbox_tus.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
				str_setdate0 = "" + getNextday(firstdate, 2);
				str_setdate1 = "" + getNextday(firstdate, 3);
			} else if (getday.equalsIgnoreCase("0")) {
				checkbox_thur.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
				str_setdate1 = "" + getNextday(firstdate, 2);
				str_setdate2 = "" + getNextday(firstdate, 3);
			}
		}

		else if (day == 4) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);
			String getday = "" + l_startdate.getDay();

			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());
			if (getday.equalsIgnoreCase("1")) {
				checkbox_fri.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
				str_setdate2 = "" + getNextday(firstdate, 2);
				str_setdate3 = "" + getNextday(firstdate, 3);
				str_setdate4 = "" + getNextday(firstdate, 4);
			} else if (getday.equalsIgnoreCase("2")) {
				checkbox_sat.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
				str_setdate3 = "" + getNextday(firstdate, 2);
				str_setdate4 = "" + getNextday(firstdate, 3);
				str_setdate5 = "" + getNextday(firstdate, 4);
			}

			else if (getday.equalsIgnoreCase("3")) {
				checkbox_sun.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
				str_setdate4 = "" + getNextday(firstdate, 2);
				str_setdate5 = "" + getNextday(firstdate, 3);
				str_setdate6 = "" + getNextday(firstdate, 4);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_mon.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);

				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
				str_setdate5 = "" + getNextday(firstdate, 2);
				str_setdate6 = "" + getNextday(firstdate, 3);
				str_setdate0 = "" + getNextday(firstdate, 4);
			} else if (getday.equalsIgnoreCase("5")) {
				checkbox_tus.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);

				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
				str_setdate6 = "" + getNextday(firstdate, 2);
				str_setdate0 = "" + getNextday(firstdate, 3);
				str_setdate1 = "" + getNextday(firstdate, 4);
			} else if (getday.equalsIgnoreCase("6")) {
				checkbox_wed.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);

				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
				str_setdate0 = "" + getNextday(firstdate, 2);
				str_setdate1 = "" + getNextday(firstdate, 3);
				str_setdate2 = "" + getNextday(firstdate, 4);
			} else if (getday.equalsIgnoreCase("0")) {
				checkbox_thur.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);

				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
				str_setdate1 = "" + getNextday(firstdate, 2);
				str_setdate2 = "" + getNextday(firstdate, 3);
				str_setdate3 = "" + getNextday(firstdate, 4);
			}
		}

		else if (day == 5) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);
			String getday = "" + l_startdate.getDay();
			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());
			if (getday.equalsIgnoreCase("1")) {
				checkbox_sat.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);

				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
				str_setdate2 = "" + getNextday(firstdate, 2);
				str_setdate3 = "" + getNextday(firstdate, 3);
				str_setdate4 = "" + getNextday(firstdate, 4);
				str_setdate5 = "" + getNextday(firstdate, 5);
			} else if (getday.equalsIgnoreCase("2")) {
				checkbox_sun.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);

				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
				str_setdate3 = "" + getNextday(str_setdate2, 2);
				str_setdate4 = "" + getNextday(str_setdate2, 3);
				str_setdate5 = "" + getNextday(str_setdate2, 4);
				str_setdate6 = "" + getNextday(str_setdate2, 5);
			}

			else if (getday.equalsIgnoreCase("3")) {
				checkbox_mon.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);

				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
				str_setdate4 = "" + getNextday(firstdate, 2);
				str_setdate5 = "" + getNextday(firstdate, 3);
				str_setdate6 = "" + getNextday(firstdate, 4);
				str_setdate0 = "" + getNextday(firstdate, 5);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_tus.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);

				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
				str_setdate5 = "" + getNextday(firstdate, 2);
				str_setdate6 = "" + getNextday(firstdate, 3);
				str_setdate0 = "" + getNextday(firstdate, 4);
				str_setdate1 = "" + getNextday(firstdate, 5);
			} else if (getday.equalsIgnoreCase("5")) {
				checkbox_thur.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);

				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
				str_setdate6 = "" + getNextday(firstdate, 2);
				str_setdate0 = "" + getNextday(firstdate, 3);
				str_setdate1 = "" + getNextday(firstdate, 4);
				str_setdate2 = "" + getNextday(firstdate, 5);
			} else if (getday.equalsIgnoreCase("6")) {
				checkbox_fri.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_thur.setEnabled(true);

				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
				str_setdate0 = "" + getNextday(firstdate, 2);
				str_setdate1 = "" + getNextday(firstdate, 3);
				str_setdate2 = "" + getNextday(firstdate, 4);
				str_setdate3 = "" + getNextday(firstdate, 5);
			} else if (getday.equalsIgnoreCase("0")) {
				checkbox_sat.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);

				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
				str_setdate1 = "" + getNextday(firstdate, 2);
				str_setdate2 = "" + getNextday(firstdate, 3);
				str_setdate3 = "" + getNextday(firstdate, 4);
				str_setdate4 = "" + getNextday(firstdate, 5);
			}
		}

		else if (day == 6) {

			txt_sun.setTextColor(getResources().getColor(R.color.gray));
			txt_mon.setTextColor(getResources().getColor(R.color.gray));
			txt_tus.setTextColor(getResources().getColor(R.color.gray));
			txt_wed.setTextColor(getResources().getColor(R.color.gray));
			txt_thur.setTextColor(getResources().getColor(R.color.gray));
			txt_sat.setTextColor(getResources().getColor(R.color.gray));
			txt_fri.setTextColor(getResources().getColor(R.color.gray));

			checkbox_sun.setEnabled(false);
			checkbox_mon.setEnabled(false);
			checkbox_tus.setEnabled(false);
			checkbox_wed.setEnabled(false);
			checkbox_thur.setEnabled(false);
			checkbox_fri.setEnabled(false);
			checkbox_sat.setEnabled(false);
			checkbox_all.setVisibility(View.GONE);
			String getday = "" + l_startdate.getDay();
			System.err.println("dayyyyyyyyyyyyy======" + l_startdate.getDay());
			if (getday.equalsIgnoreCase("1")) {
				checkbox_sun.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);

				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate1 = "" + getNextday(firstdate, 1);
				str_setdate2 = "" + getNextday(firstdate, 2);
				str_setdate3 = "" + getNextday(firstdate, 3);
				str_setdate4 = "" + getNextday(firstdate, 4);
				str_setdate5 = "" + getNextday(firstdate, 5);
				str_setdate6 = "" + getNextday(firstdate, 6);
			} else if (getday.equalsIgnoreCase("2")) {
				checkbox_mon.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);

				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate2 = "" + getNextday(firstdate, 1);
				str_setdate3 = "" + getNextday(firstdate, 2);
				str_setdate4 = "" + getNextday(firstdate, 3);
				str_setdate5 = "" + getNextday(firstdate, 4);
				str_setdate6 = "" + getNextday(firstdate, 5);
				str_setdate0 = "" + getNextday(firstdate, 6);
			}

			else if (getday.equalsIgnoreCase("3")) {
				checkbox_tus.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);

				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate3 = "" + getNextday(firstdate, 1);
				str_setdate4 = "" + getNextday(firstdate, 2);
				str_setdate5 = "" + getNextday(firstdate, 3);
				str_setdate6 = "" + getNextday(firstdate, 4);
				str_setdate0 = "" + getNextday(firstdate, 5);
				str_setdate1 = "" + getNextday(firstdate, 6);
			} else if (getday.equalsIgnoreCase("4")) {
				checkbox_wed.setEnabled(true);
				checkbox_fri.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);

				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate4 = "" + getNextday(firstdate, 1);
				str_setdate5 = "" + getNextday(firstdate, 2);
				str_setdate6 = "" + getNextday(firstdate, 3);
				str_setdate0 = "" + getNextday(firstdate, 4);
				str_setdate1 = "" + getNextday(firstdate, 5);
				str_setdate2 = "" + getNextday(firstdate, 6);

			} else if (getday.equalsIgnoreCase("5")) {
				checkbox_tus.setEnabled(true);
				checkbox_sat.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);

				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate5 = "" + getNextday(firstdate, 1);
				str_setdate6 = "" + getNextday(firstdate, 2);
				str_setdate0 = "" + getNextday(firstdate, 3);
				str_setdate1 = "" + getNextday(firstdate, 4);
				str_setdate2 = "" + getNextday(firstdate, 5);
				str_setdate3 = "" + getNextday(firstdate, 6);
			} else if (getday.equalsIgnoreCase("6")) {
				checkbox_fri.setEnabled(true);
				checkbox_sun.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);

				txt_fri.setTextColor(getResources().getColor(R.color.black));
				txt_sun.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate6 = "" + getNextday(firstdate, 1);
				str_setdate0 = "" + getNextday(firstdate, 2);
				str_setdate1 = "" + getNextday(firstdate, 3);
				str_setdate2 = "" + getNextday(firstdate, 4);
				str_setdate3 = "" + getNextday(firstdate, 5);
				str_setdate4 = "" + getNextday(firstdate, 6);
			} else if (getday.equalsIgnoreCase("0")) {

				checkbox_sat.setEnabled(true);
				checkbox_mon.setEnabled(true);
				checkbox_tus.setEnabled(true);
				checkbox_wed.setEnabled(true);
				checkbox_thur.setEnabled(true);
				checkbox_fri.setEnabled(true);

				txt_sat.setTextColor(getResources().getColor(R.color.black));
				txt_mon.setTextColor(getResources().getColor(R.color.black));
				txt_tus.setTextColor(getResources().getColor(R.color.black));
				txt_wed.setTextColor(getResources().getColor(R.color.black));
				txt_thur.setTextColor(getResources().getColor(R.color.black));
				txt_fri.setTextColor(getResources().getColor(R.color.black));

				String firstdate = l_startdate + "";
				str_setdate0 = "" + getNextday(firstdate, 1);
				str_setdate1 = "" + getNextday(firstdate, 2);
				str_setdate2 = "" + getNextday(firstdate, 3);
				str_setdate3 = "" + getNextday(firstdate, 4);
				str_setdate4 = "" + getNextday(firstdate, 5);
				str_setdate5 = "" + getNextday(firstdate, 6);
			}
		}

	}

	public static int getDaysDifference(Date fromDate, Date toDate) {
		if (fromDate == null || toDate == null)
			return 0;

		return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int getDaysDifference(Calendar calendar1, Calendar calendar2) {
		if (calendar1 == null || calendar2 == null)
			return 0;

		return (int) ((calendar2.getTimeInMillis() - calendar1
				.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}

	/*
	 * private void printOutput(String type, Date d1, Date d2, long result) {
	 * System.out.println(type+ "- Days between: " + formatter.format(d1) +
	 * " and " + formatter.format(d2) + " is: " + result); }
	 */

	/** Manual Method - YIELDS INCORRECT RESULTS - DO NOT USE **/
	/* This method is used to find the no of days between the given dates */
	public static long calculateDays(Date dateEarly, Date dateLater) {
		return (dateLater.getTime() - dateEarly.getTime())
				/ (24 * 60 * 60 * 1000);
	}

	/** Using Calendar - THE CORRECT WAY **/
	public static long daysBetween(Date startDate, Date endDate) {
		return datecheck;

	}

	public void getbookedDate() {
		
		ArrayList<Lesson_Booked> array_lesson = new ArrayList<Lesson_Booked>();
		int EndA = Integer.parseInt(btn_editst.getText().toString().trim()
				.replace(":", "")
				+ "00");
		int EndB = Integer.parseInt(btn_editet.getText().toString().trim()
				.replace(":", "")
				+ "00");

		String lesson_startdate = ed_lessondate.getText().toString().trim();// +btn_editst.getText().toString().trim();
		String lesson_enddate = ed_Lessonenddate.getText().toString().trim();// +btn_editet.getText().toString().trim();
		System.err.println("leson start" + lesson_startdate);
		System.err.println("leson end" + lesson_enddate);

		Date date1 = null, date2 = null;
		try {

			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(lesson_startdate);
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(lesson_enddate);
		

		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat newformatter = new SimpleDateFormat("yyyyMMdd");
		String date3 = newformatter.format(date1);
		String date4 = newformatter.format(date2);
		System.err.println("" + date3);
		System.err.println("" + date4);
		array_lesson = dbHandler.getLessonBooked("adll", date3, date4);

		for (int i = 0; i < array_lesson.size(); i++) {
			System.err.println("get lesson"
					+ array_lesson.get(i).getDate().replace(":", ""));

			int StartA = Integer.parseInt(array_lesson.get(i).getStart_timing()
					.replace(":", ""));
			int StartB = Integer.parseInt(array_lesson.get(i).getEnd_timing()
					.replace(":", ""));// start b
			System.err.println("StartA" + StartA);// 1040
			System.err.println("StartB" + StartB);// 1040
			System.err.println("EndA" + EndA);// 1150
			System.err.println("EndB" + EndB);// 1150
			if (StartA < EndB) {
				if (EndA < StartB) {
				
					System.err.println("yyyyyyyyyyyyyyy");
					date_overlap = 1;
					lesson_des = array_lesson.get(i).getDescription();
					
				} else {
					System.err.println("2nd nottttttttttt");
					
				}

			} else {
				System.err.println("1st nottttttttttt");
				
			}
		}

	
	}

	public void Add_Lesson() {
		findDuration();

		if (Util.isNetworkAvailable(AddLessonActivity.this)) {

			if (tutorPrefs.getString("mode", "").equalsIgnoreCase("parent")) {
				str_reqsender = "Parent";
				str_tutorid = getIntent().getStringExtra("tutorid");
			} else if (tutorPrefs.getString("mode", "").equalsIgnoreCase(
					"tutor")) {
				str_reqsender = "Tutor";
				str_tutorid = tutorPrefs.getString("tutorID", "");

			}

			getbookedDate();

			if (date_overlap == 1) {
				Util.alertMessage(AddLessonActivity.this, "Leasson "
						+ lesson_des + " already Exist for this time slot.");
				date_overlap = 0;
			} else {

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("tutor_id",
						str_tutorid));
				nameValuePairs.add(new BasicNameValuePair("topic", ""));
				nameValuePairs.add(new BasicNameValuePair("description",
						ed_description.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("start_time",
						btn_editst.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("end_time",
						btn_editet.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("days", str_day));
				nameValuePairs.add(new BasicNameValuePair("lesson_date",
						ed_lessondate.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("end_date",
						ed_Lessonenddate.getText().toString().trim()));
				nameValuePairs.add(new BasicNameValuePair("duration", ""
						+ str_duration));
				nameValuePairs.add(new BasicNameValuePair("is_rec", str_isrec));
				nameValuePairs.add(new BasicNameValuePair("student_list",
						str_students_list));
				nameValuePairs.add(new BasicNameValuePair("req_sender",
						str_reqsender));

				Log.e("create-lesson", nameValuePairs.toString());

				AsyncTaskForTutorHelper mLogin = new AsyncTaskForTutorHelper(
						AddLessonActivity.this, "create-lesson",
						nameValuePairs, true, "Please wait...");
				mLogin.delegate = (AsyncResponseForTutorHelper) AddLessonActivity.this;
				mLogin.execute();
			}
		} else {
			Util.alertMessage(AddLessonActivity.this,
					"Please check your internet connection");
		}
	}

	private void getCurrentTime() {
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		// mSec=c.get(Calendar.SECOND);

	}


	private void getdays() {
		str_day = "";
		System.err.println("size=" + arraylist_days.size());
		for (int i = 0; i < arraylist_days.size(); i++) {
			if (i == 0) {
				str_day = arraylist_days.get(0);
				System.err.println(str_day);
			} else {
				str_day = str_day + "," + arraylist_days.get(i);
				System.err.println(str_day);
			}
		}

	}

	private void getCurretDate() {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

	}

	private void gettomarrowDate() {
		Calendar cal = Calendar.getInstance(); // Get the Current date
		cal.add(Calendar.DAY_OF_MONTH, 1);// add one day
		cal.getTime();
		sYear = cal.get(Calendar.YEAR);
		sMonth = cal.get(Calendar.MONTH);
		sDay = cal.get(Calendar.DAY_OF_MONTH);
	}

	private String getNextEnddate(String startdate, int day) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatter.parse(startdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, day); // number of days to add
		String output = formatter.format(c.getTime());
		if (day == 1) {
			ed_Lessonenddate.setText(output);
		}
		return output;
	}

	private String getNextday(String startdate, int day) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatter.parse(startdate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, day); // number of days to add
		String output = formatter.format(c.getTime());

		return output;
	}

	protected String emptyFieldCheck() {
		if (ed_description.getText().toString().trim().equals("")) {
			return "Please enter description";
		} else if (btn_editst.getText().toString().trim()
				.equals("select start time")) {
			return "Please select start time";
		} else if (btn_editet.getText().toString().trim()
				.equals("select end time")) {
			return "Please select end time";

		} else if (ed_lessondate.getText().toString().trim()
				.equals("select start date")) {
			return "Please select start lesson date";
		} else if (arraylist_addstu.size() == 0) {
			return "Please add student";
		} else if (ed_Lessonenddate.getText().toString().trim()
				.equals("select end date")) {
			return "Please select lesson end date";
		} else if (str_day.equals("")) {
			return "Please select day";
		} else {
			return "success";
		}
	}

	public void findDuration() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

		Date date1 = null;
		Date date2 = null;
		try {
			date1 = simpleDateFormat.parse(btn_editst.getText().toString()
					.trim());
			date2 = simpleDateFormat.parse(btn_editet.getText().toString()
					.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int stime = Integer.parseInt(btn_editst.getText().toString().trim()
				.replace(":", ""));

		int etime = Integer.parseInt(btn_editet.getText().toString().trim()
				.replace(":", ""));

		long difference = date2.getTime() - date1.getTime();
		days = (int) (difference / (1000 * 60 * 60 * 24));
		hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
		min = (int) difference / (60 * 1000);

		int total = etime - stime;
		int minutes = (int) total / 60;
		str_duration = "" + min; 
	}

	public void processFinish(String output, String methodName) {
		System.err.println(output);
		if (methodName.equals("create-lesson")) {
			try {
				// TutorLesson tutorLesson=new TutorLesson();
				JSONObject jsonChildNode = new JSONObject(output);
				String result = jsonChildNode.getString("result");
				String message = jsonChildNode.getString("message");
				if (result.equals("0")) {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							AddLessonActivity.this);
					alert.setTitle("Tutor Helper");
					alert.setMessage("Lesson Request has sent successfully..!");
					alert.setPositiveButton("ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									if (tutorPrefs.getString("mode", "")
											.equalsIgnoreCase("parent")) {
										Intent intent = new Intent(
												AddLessonActivity.this,
												ParentDashBoard.class);
										startActivity(intent);
										for (int i = 0; i < AddActivities
												.size(); i++) {
											AddActivities.get(i).finish();
										}
									} else {
										Intent intent = new Intent(
												AddLessonActivity.this,
												TutorDashboard.class);
										startActivity(intent);

										for (int i = 0; i < AddActivities
												.size(); i++) {
											AddActivities.get(i).finish();
										}
									}
								}
							});
					alert.show();

				} else {
					Util.alertMessage(AddLessonActivity.this, message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void timecheck() {
		try {
			Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());

			String currentdate = timeformatter.format(c.getTime());

			Date seldate = timeformatter.parse(str_starttime);
			Date curdate = timeformatter.parse(currentdate);

			if (curdate.before(seldate)) {
				System.out.println("date2 is Greater than my date1");
				System.err.println("1");
				timecheck = 1;

			} else if (curdate.equals(seldate)) {
			
				timecheck = 1;

				System.err.println("=");
			} else {
				System.err.println("3");
				timecheck = 0;
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

	public int mintuecheck(int minute) {

		if (minute >= 51 && minute <= 59)
			minute = 60;
		else if (minute >= 41 && minute <= 49)
			minute = 50;
		else if (minute >= 31 && minute <= 39)
			minute = 40;
		else if (minute >= 21 && minute <= 29)
			minute = 30;
		else if (minute >= 11 && minute <= 19)
			minute = 20;
		else if (minute >= 1 && minute <= 9)
			minute = 10;
		else {
			minute = minute;
		}
		return minute;
	}

	@Override
	public void onBackPressed() {
	}
}