package com.equiworx.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.equiworx.lesson.Payment_Activity;
import com.equiworx.model.Cancellation;
import com.equiworx.model.Connection;
import com.equiworx.model.DialogC;
import com.equiworx.model.FeeDetail;
import com.equiworx.model.FeesDetail;
import com.equiworx.model.InvoiceModel;
import com.equiworx.model.Lesson;
import com.equiworx.model.LessonDetails;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.model.MyLesson;
import com.equiworx.model.NewFeed;
import com.equiworx.model.Parent;
import com.equiworx.model.Payment;
import com.equiworx.model.Statement;
import com.equiworx.model.StudentIdFee;
import com.equiworx.model.StudentList;
import com.equiworx.model.StudentRequest;
import com.equiworx.model.Tutor;
import com.equiworx.model.TutorLesson;

public class TutorHelperParser {

	private Context context;

	public TutorHelperParser(Context ctx) {
		this.context = ctx;
	}

	public Tutor parseTutorDetails(String response) {

		Tutor tutor = null;
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				tutor = new Tutor();
				tutor.setTutorId(jsonChildNode.getString("tutor_id").toString());
				tutor.setName(jsonChildNode.getString("name").toString());
				tutor.setEmail(jsonChildNode.getString("email").toString());
				tutor.setContactNumber(jsonChildNode
						.getString("contact_number").toString());
				tutor.setAltContactNumber(jsonChildNode.getString(
						"alt_c_number").toString());
				tutor.setAddress(jsonChildNode.getString("address").toString());
				tutor.setGender(jsonChildNode.getString("gender").toString());

			} else {
				//tutor = null;
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tutor;
	}

	public Tutor parseTutorEdit(String response) {

		Tutor tutor = new Tutor();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				tutor.setTutorId(jsonChildNode.getString("tutor_pin")
						.toString());
				tutor.setName(jsonChildNode.getString("name").toString());
				tutor.setEmail(jsonChildNode.getString("email").toString());
				tutor.setContactNumber(jsonChildNode
						.getString("contact_number").toString());
				tutor.setAltContactNumber(jsonChildNode.getString(
						"alt_c_number").toString());
				tutor.setAddress(jsonChildNode.getString("address").toString());
				tutor.setGender(jsonChildNode.getString("gender").toString());

			} else {
				tutor = null;
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tutor;
	}

	public Parent parseParentDetails(String response) {

		Parent parent = new Parent();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				parent.setParentId(jsonChildNode.getString("parent_id")
						.toString());
				parent.setName(jsonChildNode.getString("name").toString());
				parent.setEmail(jsonChildNode.getString("email").toString());
				parent.setContactNumber(jsonChildNode.getString(
						"contact_number").toString());
				parent.setAltContactNumber(jsonChildNode.getString(
						"alt_c_number").toString());
				parent.setAddress(jsonChildNode.getString("address").toString());
				parent.setCredits(jsonChildNode.getString("credits").toString());
				parent.setGender(jsonChildNode.getString("gender").toString());
				parent.setPin(jsonChildNode.getString("pin").toString());
			} else {
				parent = null;
				showAlert("ParentId or password you entered do not match.");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parent;
	}

	public Parent parseParentEdit(String response) {

		Parent parent = new Parent();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				parent.setParentId(jsonChildNode.getString("parent_pin")
						.toString());
				parent.setName(jsonChildNode.getString("name").toString());
				parent.setEmail(jsonChildNode.getString("email").toString());
				parent.setContactNumber(jsonChildNode.getString(
						"contact_number").toString());
				parent.setAltContactNumber(jsonChildNode.getString(
						"alt_c_number").toString());
				parent.setAddress(jsonChildNode.getString("address").toString());
				parent.setCredits(jsonChildNode.getString("credits").toString());
				parent.setGender(jsonChildNode.getString("gender").toString());
				// parent.setPin(jsonChildNode.getString("pin").toString());
			} else {
				parent = null;
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parent;
	}

	public String parseResponse(String response) {

		String result, message = "";

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				return message;
			} else {
				showAlert(message);
				message = "failure";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	public Parent getParentInfo(String response) {

		Parent parent = new Parent();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("parent_info"));

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					parent.setParentId(jsonObj.getString("parent_id")
							.toString());
					parent.setName(jsonObj.getString("name").toString());
					parent.setEmail(jsonObj.getString("email").toString());
					parent.setContactNumber(jsonObj.getString("contact_number")
							.toString());
					parent.setAltContactNumber(jsonObj
							.getString("alt_c_number").toString());
					parent.setAddress(jsonObj.getString("address").toString());

				}
			} else {
				parent = null;
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parent;
	}

	public ArrayList<Parent> getParents(String response) {
		String result, message, lastUpdated;
		ArrayList<Parent> parentList = new ArrayList<Parent>();
		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				lastUpdated = jsonChildNode.getString("greatest_last_updated")
						.toString();
				/*
				 * Editor editor = appPrefs.edit();
				 * editor.putString("incidentTimeStamp", lastUpdated);
				 * editor.commit();
				 */

				Parent temp = new Parent();

				temp.setParentId("0");
				temp.setName("Select existing parent");

				parentList.add(temp);

				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("parent_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					Parent parent = new Parent();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					parent.setParentId(jsonObj.optString("parent_id")
							.toString());
					parent.setName(jsonObj.optString("name").toString());
					parent.setEmail(jsonObj.optString("email").toString());
					parent.setContactNumber(jsonObj.optString("contact_number")
							.toString());
					parent.setAltContactNumber(jsonObj
							.optString("alt_c_number").toString());
					parent.setAddress(jsonObj.optString("address").toString());

					parentList.add(parent);
				}
			} else {
				parentList = new ArrayList<Parent>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parentList;
	}

	public void showAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Tutor Helper");
		alert.setMessage(message);
		alert.setPositiveButton("Okay", null);
		alert.show();
	}

	public ArrayList<Connection> getConnectionInfo(String response) {

		ArrayList<Connection> connectionlist = new ArrayList<Connection>();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("request_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					Connection connection = new Connection();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					connection.setTutorId(jsonObj.getString("tutor_id")
							.toString());
					connection.setParentId(jsonObj.getString("parent_id")
							.toString());
					connection.setRequestId(jsonObj.getString("request_id")
							.toString());
					connection.setParentName(jsonObj.getString("tutor_name")
							.toString());
					connection.setTutorName(jsonObj.getString("parent_name")
							.toString());

					connectionlist.add(connection);
				}
			} else {
				connectionlist = new ArrayList<Connection>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connectionlist;
	}

	public ArrayList<StudentRequest> getStudentRequestInfo(String response) {

		ArrayList<StudentRequest> arraylist = new ArrayList<StudentRequest>();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("request_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					StudentRequest studentRequest = new StudentRequest();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					studentRequest.setRequestID(jsonObj.getString("ID")
							.toString());
					studentRequest.setStudentId(jsonObj.getString("student_id")
							.toString());
					studentRequest.setName(jsonObj.getString("student_name")
							.toString());
					studentRequest.setEmail(jsonObj.getString("student_email")
							.toString());
					studentRequest.setAddress(jsonObj.getString(
							"student_address").toString());
					studentRequest.setContactInfo(jsonObj.getString(
							"student_contact").toString());
					studentRequest.setGender(jsonObj
							.getString("student_gender").toString());
					studentRequest.setTutorId(jsonObj.getString("tutor_id")
							.toString());
					studentRequest.setFees(jsonObj.getString("fee").toString());
					studentRequest.setTutorName(jsonObj.getString("tutor_name")
							.toString());
					studentRequest.setTutorEmail(jsonObj.getString(
							"tutor_email").toString());
					studentRequest.setTutorContact(jsonObj.getString(
							"tutor_contact_number").toString());
					studentRequest.setParentName(jsonObj.getString(
							"parent_name").toString());
					studentRequest.setParentEmail(jsonObj.getString(
							"parent_email").toString());
					studentRequest.setParentContact(jsonObj.getString(
							"parent_contact_info").toString());

					arraylist.add(studentRequest);
				}
			} else {
				arraylist = new ArrayList<StudentRequest>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist;
	}

	public ArrayList<Parent> getParentlist(String response) {

		ArrayList<Parent> arraylist_parentList = new ArrayList<Parent>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			greatest_last_updated = jsonChildNode.getString(
					"greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("parent_list"));

				for (int i = 0; i < jsonArray.length(); i++) {

					Parent parent = new Parent();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					parent.setParentId(jsonObj.getString("parent_id")
							.toString());
					parent.setName(jsonObj.getString("name").toString());
					parent.setEmail(jsonObj.getString("email").toString());
					parent.setContactNumber(jsonObj.getString("contact_number")
							.toString());
					parent.setAltContactNumber(jsonObj
							.getString("alt_c_number").toString());
					parent.setAddress(jsonObj.getString("address").toString());
					parent.setStudentCount(jsonObj.getString("no._of_students")
							.toString());
					String activestudent = jsonObj.getString(
							"no._of_active_students").toString();
					parent.setActivestudents(activestudent);
					parent.setLessonCount(jsonObj.getString("no._of_lessons")
							.toString());
					parent.setNotes(jsonObj.getString("notes").toString());
					parent.setOutstandingBalance(jsonObj.getString(
							"outstanding_balance").toString());
					if (activestudent.equals("0")) {

					} else {
						arraylist_parentList.add(parent);
					}
				}
			} else {
				arraylist_parentList = new ArrayList<Parent>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_parentList;
	}

	public ArrayList<Parent> getAllParentlist(String response) {

		ArrayList<Parent> arraylist_parentList = new ArrayList<Parent>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			greatest_last_updated = jsonChildNode.getString(
					"greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("parent_list"));

				for (int i = 0; i < jsonArray.length(); i++) {

					Parent parent = new Parent();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					parent.setParentId(jsonObj.getString("parent_id")
							.toString());
					parent.setName(jsonObj.getString("name").toString());
					parent.setEmail(jsonObj.getString("email").toString());
					parent.setContactNumber(jsonObj.getString("contact_number")
							.toString());
					parent.setAltContactNumber(jsonObj
							.getString("alt_c_number").toString());
					parent.setAddress(jsonObj.getString("address").toString());
					parent.setStudentCount(jsonObj.getString("no._of_students")
							.toString());
					parent.setLessonCount(jsonObj.getString("no._of_lessons")
							.toString());
					parent.setNotes(jsonObj.getString("notes").toString());
					parent.setOutstandingBalance(jsonObj.getString(
							"outstanding_balance").toString());

					arraylist_parentList.add(parent);
				}
			} else {
				arraylist_parentList = new ArrayList<Parent>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_parentList;
	}

	public ArrayList<StudentList> getStudentlist(String response) {

		ArrayList<StudentList> arraylist_studentList = new ArrayList<StudentList>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			greatest_last_updated = jsonChildNode.getString(
					"greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("student_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					StudentList studentList = new StudentList();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					studentList.setStudentId(jsonObj.getString("student_id")
							.toString());
					String name = jsonObj.getString("name").toString();
					// System.err.println("name="+name);
					studentList.setName(name);
					studentList.setEmail(jsonObj.getString("email").toString());
					studentList.setAddress(jsonObj.getString("address")
							.toString());
					studentList.setContactInfo(jsonObj
							.getString("contact_info").toString());
					studentList.setGender(jsonObj.getString("gender")
							.toString());
					studentList.setFees(jsonObj.getString("fee").toString());
					studentList.setParentId(jsonObj.getString("parent_id")
							.toString());
					studentList.setIsActiveInMonth(jsonObj.getString(
							"isActiveInMonth").toString());
					studentList.setNotes(jsonObj.getString("notes").toString());

					arraylist_studentList.add(studentList);

				}
			} else {
				arraylist_studentList = new ArrayList<StudentList>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_studentList;
	}

	public ArrayList<StudentList> getStudentlistwithoutNote(String response) {

		ArrayList<StudentList> arraylist_studentList = new ArrayList<StudentList>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			greatest_last_updated = jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("student_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					StudentList studentList = new StudentList();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					studentList.setStudentId(jsonObj.getString("student_id")
							.toString());
					String name = jsonObj.getString("name").toString();
					// System.err.println("name="+name);
					studentList.setName(name);
					studentList.setEmail(jsonObj.getString("email").toString());
					studentList.setAddress(jsonObj.getString("address")
							.toString());
					studentList.setContactInfo(jsonObj
							.getString("contact_info").toString());
					studentList.setGender(jsonObj.getString("gender")
							.toString());
					studentList.setFees(jsonObj.getString("fee").toString());
					studentList.setParentId(jsonObj.getString("parent_id")
							.toString());
					studentList.setIsActiveInMonth(jsonObj.getString(
							"isActiveInMonth").toString());

					arraylist_studentList.add(studentList);

				}
			} else {
				arraylist_studentList = new ArrayList<StudentList>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_studentList;
	}

	public ArrayList<MyLesson> getMyLesson(String response) {

		ArrayList<MyLesson> arraylist_mylesson = new ArrayList<MyLesson>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				// JSONObject lesson_data = new JSONObject(message);

				String str_lesson_data = jsonChildNode.getString("lesson_data");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);

				for (int j = 0; j < jsonArray1.length(); j++) {
					MyLesson myLesson = new MyLesson();
					JSONObject jsonObj1 = jsonArray1.getJSONObject(j);
					JSONArray jsonArray = new JSONArray(
							jsonObj1.getString("student_info"));
					ArrayList<StudentList> arraylist = new ArrayList<StudentList>();

					for (int i = 0; i < jsonArray.length(); i++) {
						StudentList studentList = new StudentList();
						JSONObject jsonObj = jsonArray.getJSONObject(i);

						studentList.setStudentId(jsonObj
								.getString("student_id").toString());
						String name = jsonObj.getString("student_name")
								.toString();
						System.err.println("name=" + name);
						studentList.setName(name);
						studentList.setEmail(jsonObj.getString("student_email")
								.toString());
						studentList.setAddress(jsonObj.getString(
								"student_address").toString());
						studentList.setContactInfo(jsonObj.getString(
								"student_contact_info").toString());
						// studentList.setStudentfee(jsonObj.getString("student_fee").toString());
						arraylist.add(studentList);

					}
					myLesson.setArray_studentlist(arraylist);

					myLesson.setStudentno("" + arraylist.size());
					// arraylist.clear();
					// myLesson.setTutorId(jsonObj1.getString("parent_id"));
					// myLesson.setParentName(jsonObj1.getString("parent_name"));
					// myLesson.setParentEmail(jsonObj1.getString("parent_email"));

					myLesson.setLessonId(jsonObj1.getString("lesson_id"));
					// myLesson.setGreatest_last_updated(jsonObj1.getString("greatest_last_updated"));
					myLesson.setLesson_tutor_id(jsonObj1.getString("lesson_tutor_id"));
					myLesson.setTutor_name(jsonObj1.getString("tutor_name"));
					myLesson.setLessonTopic(jsonObj1.getString("lesson_topic"));
					myLesson.setLessonDescription(jsonObj1.getString("lesson_description"));
					myLesson.setStartTime(jsonObj1.getString("lesson_start_time"));
					myLesson.setEndTime(jsonObj1.getString("lesson_end_time"));
					myLesson.setDuration(jsonObj1.getString("lesson_duration"));
					myLesson.setDays(jsonObj1.getString("lesson_days"));
					String lessondate = jsonObj1.getString("lesson_date");
					myLesson.setLessonDate(jsonObj1.getString("lesson_date"));

					System.err.println("lesson_date=" + lessondate);
					myLesson.setLessonenddate(jsonObj1.getString("end_date"));
					myLesson.setIsRecurring(jsonObj1
							.getString("lesson_is_recurring"));
					myLesson.setIsActive(jsonObj1.getString("lesson_is_active"));

					arraylist_mylesson.add(myLesson);
				}

			} else {
				// arraylist_mylesson = new ArrayList<MyLesson>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_mylesson;
	}

	public ArrayList<Lesson> getLesson(String response) {

		ArrayList<Lesson> arraylist_lesson = new ArrayList<Lesson>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("lesson_request"));

				for (int i = 0; i < jsonArray.length(); i++) {
					Lesson lesson = new Lesson();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					lesson.setRequestId(jsonObj.getString("request_id")
							.toString());
					String name = jsonObj.getString("lesson_id").toString();
					System.err.println("name=" + name);
					lesson.setLessonId(name);
					lesson.setTutorId(jsonObj.getString("tutor_id").toString());
					lesson.setParentId(jsonObj.getString("parent_id")
							.toString());
					lesson.setStudentId(jsonObj.getString("student_id")
							.toString());
					lesson.setLessonTopic(jsonObj.getString("lesson_topic")
							.toString());
					lesson.setLessonDescription(jsonObj.getString(
							"lesson_description").toString());
					lesson.setLessonDuration(jsonObj.getString(
							"lesson_duration").toString());
					lesson.setLessonStartTime(jsonObj.getString(
							"lesson_start_time").toString());
					lesson.setLessonEndTime(jsonObj
							.getString("lesson_end_time").toString());
					lesson.setLessonDate(jsonObj.getString("lesson_date")
							.toString());
					lesson.setEnddate(jsonObj.getString("lesson_end_date")
							.toString());
					lesson.setCreatedDate(jsonObj.getString(
							"lesson_created_date").toString());
					lesson.setIsRecurring(jsonObj.getString(
							"lesson_is_recurring").toString());
					lesson.setLessonDays(jsonObj.getString("lesson_days")
							.toString());
					lesson.setRequestStatus(jsonObj.getString("request_status")
							.toString());
					lesson.setFees(jsonObj.getString("lesson_fee").toString());

					lesson.setParentname(jsonObj.getString("parent_name")
							.toString());
					lesson.setTutername(jsonObj.getString("tutor_name")
							.toString());
					lesson.setStudentname(jsonObj.getString("student_name")
							.toString());

					arraylist_lesson.add(lesson);

				}
			} else {
				arraylist_lesson = new ArrayList<Lesson>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_lesson;
	}

	public ArrayList<LessonDetails> getTutordetails(String response) {

		ArrayList<LessonDetails> arraylist_lessondetails = new ArrayList<LessonDetails>();
		ArrayList<Lesson_Booked> arraylist_lessonbooked = new ArrayList<Lesson_Booked>();
		String result, message;

		try {
			TutorLesson tutorLesson = new TutorLesson();
			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			tutorLesson.setResult(result);
			message = jsonChildNode.getString("message").toString();
			tutorLesson.setMessage(message);
			tutorLesson.setActive_students(jsonChildNode.getString(
					"no of active students").toString());
			tutorLesson.setFee_due(jsonChildNode.getString("fee_due")
					.toString());
			tutorLesson.setFee_collected(jsonChildNode.getString(
					"fee_collected").toString());

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("lesson_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					LessonDetails lesson = new LessonDetails();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					lesson.setBlock_out_time_for_fullday(jsonObj.getString(
							"block_out_time_for_fullday").toString());
					lesson.setBlock_out_time_for_halfday(jsonObj.getString(
							"block_out_time_for_halfday").toString());
					lesson.setNo_of_lessons(jsonObj.getString("no._of_lessons")
							.toString());
					String lessondate=jsonObj.getString("lesson_date");
					lesson.setLesson_date(lessondate
							.toString());
					
					
					JSONArray jsonArraytimimg = new JSONArray(
							jsonObj.getString("timing"));

					for (int j = 0; j < jsonArraytimimg.length(); j++) {
						Lesson_Booked lesson_booked = new Lesson_Booked();
						JSONObject jsonObj_timing = jsonArraytimimg.getJSONObject(j);
						
						
						lesson_booked.setID(jsonObj_timing.getString("ID").toString());
						lesson_booked.setDescription(jsonObj_timing.getString("description").toString());
						lesson_booked.setStart_timing(jsonObj_timing.getString("start_timing").toString());
						lesson_booked.setEnd_timing(jsonObj_timing.getString("end_timing").toString());
						lesson_booked.setDate(lessondate);
						arraylist_lessonbooked.add(lesson_booked);
						
					}
					arraylist_lessondetails.add(lesson);

				}
			} else {
				arraylist_lessondetails = new ArrayList<LessonDetails>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_lessondetails;
	}
	
	
	public ArrayList<Lesson_Booked> getLessonBooked(String response) {

		ArrayList<LessonDetails> arraylist_lessondetails = new ArrayList<LessonDetails>();
		ArrayList<Lesson_Booked> arraylist_lessonbooked = new ArrayList<Lesson_Booked>();
		String result, message;

		try {
			TutorLesson tutorLesson = new TutorLesson();
			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			tutorLesson.setResult(result);
			message = jsonChildNode.getString("message").toString();
			tutorLesson.setMessage(message);
			tutorLesson.setActive_students(jsonChildNode.getString(
					"no of active students").toString());
			tutorLesson.setFee_due(jsonChildNode.getString("fee_due")
					.toString());
			tutorLesson.setFee_collected(jsonChildNode.getString(
					"fee_collected").toString());

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("lesson_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					LessonDetails lesson = new LessonDetails();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					lesson.setBlock_out_time_for_fullday(jsonObj.getString(
							"block_out_time_for_fullday").toString());
					lesson.setBlock_out_time_for_halfday(jsonObj.getString(
							"block_out_time_for_halfday").toString());
					lesson.setNo_of_lessons(jsonObj.getString("no._of_lessons")
							.toString());
					String lessondate=jsonObj.getString("lesson_date").replace("-", "");
					lesson.setLesson_date(lessondate
							.toString());
					
					
					JSONArray jsonArraytimimg = new JSONArray(
							jsonObj.getString("timing"));

					for (int j = 0; j < jsonArraytimimg.length(); j++) {
						Lesson_Booked lesson_booked = new Lesson_Booked();
						JSONObject jsonObj_timing = jsonArraytimimg.getJSONObject(j);
						
						
						lesson_booked.setID(jsonObj_timing.getString("ID").toString());
						lesson_booked.setDescription(jsonObj_timing.getString("description").toString());
						lesson_booked.setStart_timing(jsonObj_timing.getString("start_timing").toString());
						lesson_booked.setEnd_timing(jsonObj_timing.getString("end_timing").toString());
						lesson_booked.setDate(lessondate);
						arraylist_lessonbooked.add(lesson_booked);
						
					}
					arraylist_lessondetails.add(lesson);

				}
			} else {
				arraylist_lessondetails = new ArrayList<LessonDetails>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_lessonbooked;
	}

	public ArrayList<Tutor> getTuterList(String response) {

		ArrayList<Tutor> arraylist_tuterlist = new ArrayList<Tutor>();
		String result, message, greatest_last_updated;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("tutor_list"));

				for (int i = 0; i < jsonArray.length(); i++) {
					Tutor tutor = new Tutor();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					tutor.setTutorId(jsonObj.getString("tutor_id").toString());
					tutor.setName(jsonObj.getString("name").toString());

					tutor.setEmail(jsonObj.getString("email").toString());
					tutor.setContactNumber(jsonObj.getString("contact_number")
							.toString());
					tutor.setAltContactNumber(jsonObj.getString("alt_c_number")
							.toString());
					tutor.setAddress(jsonObj.getString("address").toString());
					String note = jsonObj.getString("notes").toString();
					tutor.setNotes(note);
					arraylist_tuterlist.add(tutor);

					JSONObject jsonChildNode1 = jsonArray.getJSONObject(i);

					JSONArray jsonArray1 = new JSONArray(
							jsonChildNode1.getString("fee_list"));

					ArrayList<StudentIdFee> array_student = new ArrayList<StudentIdFee>();
					for (int j = 0; j < jsonArray1.length(); j++) {
						JSONObject jsonObj1 = jsonArray1.getJSONObject(j);

						StudentIdFee studentIdFee = new StudentIdFee();
						String id = jsonObj1.getString("student_id").toString();
						Log.e("id", id);//
						studentIdFee.setStudentid(id);
						studentIdFee.setFee(jsonObj1.getString("fee")
								.toString());
						array_student.add(studentIdFee);
					}
					tutor.setStudent(array_student);
				}
			} else {
				arraylist_tuterlist = new ArrayList<Tutor>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_tuterlist;
	}

	// getting tutor requests ..........

	public ArrayList<Cancellation> getCancelRequestList(String response) {

		ArrayList<Cancellation> arraylist_Cancellation = new ArrayList<Cancellation>();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				JSONArray jsonArray = new JSONArray(
						jsonChildNode.getString("request_info"));

				for (int i = 0; i < jsonArray.length(); i++) {

					Cancellation parent = new Cancellation();
					JSONObject jsonObj = jsonArray.getJSONObject(i);

					parent.setParent_name(jsonObj.getString("parent_name")
							.toString());
					parent.setParent_id(jsonObj.getString("parent_id")
							.toString());
					parent.setLesson_id(jsonObj.getString("lesson_id")
							.toString());
					parent.setReason(jsonObj.getString("reason").toString());
					parent.setStatus(jsonObj.getString("status").toString());
					parent.setRequest_date(jsonObj.getString("request_date")
							.toString());
					parent.setID(jsonObj.getString("ID").toString());

					parent.setLesson_tutor_id(jsonObj.getString(
							"lesson_tutor_id").toString());
					parent.setTutor_name(jsonObj.getString("tutor_name")
							.toString());
					parent.setLesson_description(jsonObj.getString(
							"lesson_description").toString());
					parent.setLesson_start_time(jsonObj.getString(
							"lesson_start_time").toString());
					parent.setLesson_end_time(jsonObj.getString(
							"lesson_end_time").toString());

					parent.setLesson_duration(jsonObj.getString(
							"lesson_duration").toString());
					parent.setLesson_days(jsonObj.getString("lesson_days")
							.toString());
					parent.setLesson_date(jsonObj.getString("lesson_date")
							.toString());
					parent.setEnd_date(jsonObj.getString("end_date").toString());
					parent.setLesson_is_recurring(jsonObj.getString(
							"lesson_is_recurring").toString());
					parent.setLesson_is_active(jsonObj.getString(
							"lesson_is_active").toString());

					arraylist_Cancellation.add(parent);
				}
			} else {
				arraylist_Cancellation = new ArrayList<Cancellation>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_Cancellation;
	}

	// /get student history
	public ArrayList<MyLesson> getStudentHistory(String response) {

		ArrayList<MyLesson> arraylist_mylesson = new ArrayList<MyLesson>();
		String result, message;

		try {

			JSONObject jsonChildNode = new JSONObject(response);

			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();

			if (result.equals("0")) {
				// JSONObject lesson_data = new JSONObject(message);

				String str_lesson_data = jsonChildNode.getString("lesson_data");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);

				for (int j = 0; j < jsonArray1.length(); j++) {
					MyLesson myLesson = new MyLesson();
					JSONObject jsonObj1 = jsonArray1.getJSONObject(j);

					myLesson.setLessonId(jsonObj1.getString("lesson_id"));
					myLesson.setLesson_tutor_id(jsonObj1
							.getString("lesson_tutor_id"));
					myLesson.setTutor_name(jsonObj1.getString("tutor_name"));
					myLesson.setLessonTopic(jsonObj1.getString("lesson_topic"));
					myLesson.setLessonDescription(jsonObj1
							.getString("lesson_description"));
					myLesson.setStartTime(jsonObj1
							.getString("lesson_start_time"));
					myLesson.setEndTime(jsonObj1.getString("lesson_end_time"));
					myLesson.setDuration(jsonObj1.getString("lesson_duration"));
					myLesson.setDays(jsonObj1.getString("lesson_days"));
					String lessondate = jsonObj1.getString("lesson_date");
					myLesson.setLessonDate(jsonObj1.getString("lesson_date"));

					System.err.println("lesson_date=" + lessondate);
					myLesson.setLessonenddate(jsonObj1.getString("end_date"));
					myLesson.setIsRecurring(jsonObj1
							.getString("lesson_is_recurring"));
					myLesson.setIsActive(jsonObj1.getString("lesson_is_active"));

					arraylist_mylesson.add(myLesson);
				}

			} else {
				// arraylist_mylesson = new ArrayList<MyLesson>();
				showAlert(message);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arraylist_mylesson;
	}

	public ArrayList<FeeDetail> getPaymentHistory(String output) {
		// TODO Auto-generated method stub
		String result, message, month_list, yearName, Total_Feedue, Total_Fee_Collected, Total_OutstandingBalance, fees_outstanding;
		FeesDetail feeDetail = null;
		YearNode yearNode = null;
		String totalFeeDue, totalFeesCollected, totalOutstandingBalance;
		ArrayList<FeesDetail> historyDetails = new ArrayList<FeesDetail>();
		ArrayList<FeeDetail> FeesData = new ArrayList<FeeDetail>();
		ArrayList<YearNode> YearData = null;
		try {

			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			totalFeeDue = jsonChildNode.getString("Total_Feedue");
			totalFeesCollected = jsonChildNode.getString("Total_Fee_Collected");
			totalOutstandingBalance = jsonChildNode
					.getString("Total_OutstandingBalance");

			SharedPreferences pref = context.getApplicationContext()
					.getSharedPreferences("MyPref", 0);
			Editor editor = pref.edit();
			editor.putString("totalFeeDuep", totalFeeDue);
			editor.putString("totalFeesCollectedp", totalFeesCollected);
			editor.putString("totalOutstandingBalancep",
					totalOutstandingBalance);
			editor.commit();
			String str_lesson_data = jsonChildNode.getString("year_data");
			JSONArray yearArray = new JSONArray(str_lesson_data);
			if (result.equals("0")) {
				historyDetails = new ArrayList<FeesDetail>();
				YearData = new ArrayList<YearNode>();

				for (int j = 0; j < yearArray.length(); j++) {

					JSONObject monthobj = yearArray.getJSONObject(j);
					FeesData.add(new YearNode(monthobj.getString("name")));
					month_list = monthobj.getString("month_list");
					JSONArray monthArrayList = new JSONArray(month_list);

					for (int i = 0; i < monthArrayList.length(); i++) {

						JSONObject jsonObj1 = monthArrayList.getJSONObject(i);

						FeesData.add(new FeesDetail(jsonObj1.getString("name"),
								jsonObj1.getString("no of lessons"), jsonObj1
										.getString("feeDue"), jsonObj1
										.getString("fee_collected"), jsonObj1
										.getString("outstanding_balance"),
								jsonObj1.getString("lesson_ids"), jsonObj1
										.getString("fee_outstanding")));

					}

				}

			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return FeesData;
	}

	public ArrayList<FeesDetail> getPaymentHistory1(String output) {
		// TODO Auto-generated method stub
		String result, message, month_list, yearName, Total_Feedue, Total_Fee_Collected, Total_OutstandingBalance, fees_outstanding;
		FeesDetail feeDetail = null;
		YearNode yearNode = null;
		String totalFeeDue, totalFeesCollected, totalOutstandingBalance;
		ArrayList<FeesDetail> historyDetails = null;
		ArrayList<FeeDetail> FeesData = new ArrayList<FeeDetail>();
		ArrayList<YearNode> YearData = null;
		try {

			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();

			totalFeeDue = jsonChildNode.getString("Total_Feedue");
			totalFeesCollected = jsonChildNode.getString("Total_Fee_Collected");
			totalOutstandingBalance = jsonChildNode
					.getString("Total_OutstandingBalance");

			SharedPreferences pref = context.getApplicationContext()
					.getSharedPreferences("MyPref", 0);
			Editor editor = pref.edit();
			editor.putString("totalFeeDuep", totalFeeDue);
			editor.putString("totalFeesCollectedp", totalFeesCollected);
			editor.putString("totalOutstandingBalancep",
					totalOutstandingBalance);
			editor.commit();
			String str_lesson_data = jsonChildNode.getString("year_data");
			JSONArray yearArray = new JSONArray(str_lesson_data);
			if (result.equals("0")) {
				historyDetails = new ArrayList<FeesDetail>();
				YearData = new ArrayList<YearNode>();

				for (int j = 0; j < yearArray.length(); j++) {

					JSONObject monthobj = yearArray.getJSONObject(j);
					// FeesData.add(new YearNode(monthobj.getString("name")));
					month_list = monthobj.getString("month_list");
					JSONArray monthArrayList = new JSONArray(month_list);
					historyDetails = new ArrayList<FeesDetail>();
					for (int i = 0; i < monthArrayList.length(); i++) {

						JSONObject jsonObj1 = monthArrayList.getJSONObject(i);
						FeesDetail feesdetail = new FeesDetail();

						feesdetail.setMonthName(jsonObj1.getString("name"));
						feesdetail.setNoOfLessons(jsonObj1
								.getString("no of lessons"));
						feesdetail.setFeeDue(jsonObj1.getString("feeDue"));
						feesdetail.setFeeCollected(jsonObj1
								.getString("fee_collected"));
						feesdetail.setOutstandingBalance(jsonObj1
								.getString("outstanding_balance"));
						feesdetail.setLessonIds(jsonObj1
								.getString("lesson_ids"));
						feesdetail.setFee_outstanding(jsonObj1
								.getString("fee_outstanding"));

						historyDetails.add(feesdetail);
					}

				}

			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return historyDetails;
	}

	public ArrayList<FeesDetail> getPaymentHistoryParents(String output) {
		// TODO Auto-generated method stub
		String result, message, totalFeeDue, totalFeesCollected, totalOutstandingBalance;
		ArrayList<FeesDetail> historyPaymentParents = new ArrayList<FeesDetail>();

		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			message = jsonChildNode.getString("message").toString();
			totalFeeDue = jsonChildNode.getString("Total_Feedue");
			totalFeesCollected = jsonChildNode.getString("Total_Fee_Collected");
			totalOutstandingBalance = jsonChildNode
					.getString("Total_OutstandingBalance");

			SharedPreferences pref = context.getApplicationContext()
					.getSharedPreferences("MyPref", 0);
			Editor editor = pref.edit();
			editor.putString("totalFeeDue", totalFeeDue);
			editor.putString("totalFeesCollected", totalFeesCollected);
			editor.putString("totalOutstandingBalance", totalOutstandingBalance);
			editor.commit();
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode.getString("parent_list");//
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
				for (int i = 0; i < jsonArray1.length(); i++) {

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					historyPaymentParents.add(new FeesDetail(jsonObj1
							.getString("name"), jsonObj1
							.getString("no of lessons"), jsonObj1
							.getString("feeDue"), jsonObj1
							.getString("fee_collected"), jsonObj1
							.getString("outstanding_balance"), jsonObj1
							.getString("lesson_ids"), jsonObj1.getString("ID"),
							jsonObj1.getString("fee_outstanding")));

				}

			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return historyPaymentParents;
	}

	public ArrayList<MyLesson> getLessonDetails(String output) {
		// TODO Auto-generated method stub
		String result, message;
		ArrayList<MyLesson> historyPaymentParents = new ArrayList<MyLesson>();

		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode.getString("lesson_list");//
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
				for (int i = 0; i < jsonArray1.length(); i++) {

					MyLesson myLesson = new MyLesson();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					myLesson.setLessonId(jsonObj1.getString("lesson_id"));
					myLesson.setLesson_tutor_id(jsonObj1
							.getString("lesson_tutor_id"));
					myLesson.setTutor_name(jsonObj1.getString("tutor_name"));
					// myLesson.setLseeonIds(jsonObj1.getString("lesson_ids"));
					myLesson.setLessonTopic(jsonObj1.getString("lesson_topic"));
					myLesson.setLessonDescription(jsonObj1
							.getString("lesson_description"));
					myLesson.setDuration(jsonObj1.getString("lesson_duration"));

					myLesson.setDays(jsonObj1.getString("lesson_days"));
					myLesson.setLessonDate(jsonObj1.getString("lesson_date"));
					myLesson.setLessonenddate(jsonObj1.getString("end_date"));

					myLesson.setIsRecurring(jsonObj1
							.getString("lesson_is_recurring"));
					myLesson.setIsActive(jsonObj1.getString("lesson_is_active"));
					myLesson.setStartTime(jsonObj1
							.getString("lesson_start_time"));
					myLesson.setEndTime(jsonObj1.getString("lesson_end_time"));
					myLesson.setStudentno(jsonObj1.getString("no of students"));
					String student_info = jsonObj1.getString("student_info");//

					JSONArray jsonArray2 = new JSONArray(student_info);
					ArrayList<StudentList> historyPaymentParents1 = new ArrayList<StudentList>();
					for (int j = 0; j < jsonArray2.length(); j++) {

						StudentList studentList = new StudentList();
						JSONObject jsonObj11 = jsonArray2.getJSONObject(j);
						studentList.setStudentId(jsonObj11.getString("student_id"));
						studentList.setName(jsonObj11.getString("student_name"));
						studentList.setEmail(jsonObj11.getString("student_email"));
						studentList.setAddress(jsonObj11.getString("student_address"));
						studentList.setStudentfee(jsonObj11.getString("student_fee"));
						studentList.setContactInfo(jsonObj11.getString("student_contact_info"));
						historyPaymentParents1.add(studentList);
					}
					myLesson.setStudebtlist(historyPaymentParents1);

					historyPaymentParents.add(myLesson);
				}

			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return historyPaymentParents;
	}

	public void getPayment(String output) {
		// TODO Auto-generated method stub
		String result, message;
		// ArrayList<paren> historyPaymentParents = new ArrayList<Tutor>();
		Payment_Activity.arraylist_payment = new ArrayList<Payment>();
		Payment_Activity.arraylist_credit = new ArrayList<Payment>();

		Payment_Activity.arraylist_payment.clear();
		Payment_Activity.arraylist_credit.clear();
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			// greatest_last_updated =
			// jsonChildNode.getString("greatest_last_updated").toString();
			message = jsonChildNode.getString("message").toString();
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode
						.getString("payment_list");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
				for (int i = 0; i < jsonArray1.length(); i++) {

					Payment myLesson = new Payment();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					myLesson.setID(jsonObj1.getString("ID"));
					myLesson.setTutor_id(jsonObj1.getString("tutor_id"));
					myLesson.setTutor_name(jsonObj1.getString("tutor_name"));
					myLesson.setParent_id(jsonObj1.getString("parent_id"));
					myLesson.setParent_name(jsonObj1.getString("parent_name"));
					String mode = jsonObj1.getString("payment_mode");
					myLesson.setPayment_mode(mode);
					System.err.println("mode==" + mode);
					myLesson.setRemarks(jsonObj1.getString("remarks"));
					myLesson.setFee_paid(jsonObj1.getString("fee_paid"));
					myLesson.setLast_updated(jsonObj1.getString("last_updated"));
					
					if (mode.equalsIgnoreCase("PayFees")) {
					
						Payment_Activity.arraylist_payment.add(myLesson);
					} else if (mode.equalsIgnoreCase("AddCredit")) {
						Payment_Activity.arraylist_credit.add(myLesson);
					}

				}
				System.err.println("sizee payment"
						+ Payment_Activity.arraylist_payment.size());
				System.err.println("sizee credit "
						+ Payment_Activity.arraylist_credit.size());
			} else {

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return historyPaymentParents;
	}

	public ArrayList<DialogC> getAddCreditResults(String output) {
		// TODO Auto-generated method stub
		String result, message;
		ArrayList<DialogC> arrayList = new ArrayList<DialogC>();
		try {
			DialogC dialog = new DialogC();
			JSONObject jsonChildNode = new JSONObject(output);
			dialog.setResult(jsonChildNode.getString("result"));
			dialog.setSuccess(jsonChildNode.getString("message"));
			arrayList.add(dialog);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

	public ArrayList<NewFeed> getNewsFeed(String output) {
		// TODO Auto-generated method stub
		String result, message,pageToken;
		// ArrayList<paren> historyPaymentParents = new ArrayList<Tutor>();
		ArrayList<NewFeed> getnewsfeed = new ArrayList<NewFeed>();
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			pageToken= jsonChildNode.getString("pageToken").toString();
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode
						.getString("notification_list");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
				for (int i = 0; i < jsonArray1.length(); i++) {

					NewFeed newsfeed = new NewFeed();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					newsfeed.setId(jsonObj1.getString("ID"));
					newsfeed.setTutorid(jsonObj1.getString("tutor_id"));
					newsfeed.setRecievedId(jsonObj1.getString("receiver_id"));
					newsfeed.setParent_id(jsonObj1.getString("parent_id"));
					newsfeed.setRequest_id(jsonObj1.getString("request_id"));
					String notitype = jsonObj1.getString("notitype");
					newsfeed.setNotitype(notitype);
					newsfeed.setLessionid(jsonObj1.getString("lesson_id"));
					newsfeed.setMessage(jsonObj1.getString("message"));
					newsfeed.setLast_updated(jsonObj1.getString("last_updated"));
					newsfeed.setStudentid(jsonObj1.getString("student_id"));
					
					getnewsfeed.add(newsfeed);
				}
				
			} else {

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getnewsfeed;
	}

	public ArrayList<Parent> getParentBasicdetail(String output) {
		// TODO Auto-generated method stub
		String result, message,pageToken,fees_due,fee_collected;
		ArrayList<Parent> getbasicdetailParent = new ArrayList<Parent>();
		
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			fees_due= jsonChildNode.getString("fee_due").toString();
			fee_collected= jsonChildNode.getString("fee_collected").toString();
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode.getString("lesson_list");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
		        
				for (int i = 0; i < jsonArray1.length(); i++) {

					Parent basicdetail = new Parent();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					basicdetail.setBlock_out_time_for_fullday(jsonObj1.getString("block_out_time_for_fullday"));
					basicdetail.setBlock_out_time_for_halfday(jsonObj1.getString("block_out_time_for_halfday"));
					basicdetail.setNo_of_lessons(jsonObj1.getString("no._of_lessons"));
					basicdetail.setLesson_date(jsonObj1.getString("lesson_date"));
										
					getbasicdetailParent.add(basicdetail);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getbasicdetailParent;
	}
	
	public ArrayList<Statement> getStatementdetail(String output) {
		// TODO Auto-generated method stub
		String result, message,pageToken,fees_due,fee_collected;
		ArrayList<Statement> getbasicdetailStatement = new ArrayList<Statement>();
		
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			fees_due= jsonChildNode.getString("message").toString();
		
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode.getString("payment_list");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
		        
				for (int i = 0; i < jsonArray1.length(); i++) {

					Statement basicdetail = new Statement();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					basicdetail.setID(jsonObj1.getString("ID"));
					basicdetail.setTutor_id(jsonObj1.getString("tutor_id"));
					basicdetail.setTutor_name(jsonObj1.getString("tutor_name"));
					basicdetail.setParent_id(jsonObj1.getString("parent_id"));
					basicdetail.setParent_name(jsonObj1.getString("parent_name"));
					basicdetail.setPayment_mode(jsonObj1.getString("payment_mode"));
					basicdetail.setRemarks(jsonObj1.getString("remarks"));
					basicdetail.setFee_paid(jsonObj1.getString("fee_paid"));
					basicdetail.setLast_updated(jsonObj1.getString("last_updated"));
										
					getbasicdetailStatement.add(basicdetail);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getbasicdetailStatement;
	}

	public ArrayList<Statement> getReportdetails(String output) {
		// TODO Auto-generated method stub
		String result, message,pageToken,fees_due,fee_collected;
		ArrayList<Statement> getbasicdetailStatement = new ArrayList<Statement>();
		
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			fees_due= jsonChildNode.getString("message").toString();
		
			if (result.equals("0")) {
				String str_lesson_data = jsonChildNode.getString("lesson_data");
				JSONArray jsonArray1 = new JSONArray(str_lesson_data);
		        
				for (int i = 0; i < jsonArray1.length(); i++) {

					Statement basicdetail = new Statement();

					JSONObject jsonObj1 = jsonArray1.getJSONObject(i);
					basicdetail.setLesson_id(jsonObj1.getString("lesson_id"));
					String date=jsonObj1.getString("session_dates");
					//date
							basicdetail.setSession_dates(date);
					
										
					getbasicdetailStatement.add(basicdetail);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getbasicdetailStatement;
	}

	public ArrayList<InvoiceModel> getInvoiceDetails(String output) {
		
		// TODO Auto-generated method stub
		
		String result, message;
		ArrayList<InvoiceModel> getbasicdetailStatement = new ArrayList<InvoiceModel>();
		
		try {
			JSONObject jsonChildNode = new JSONObject(output);
			result = jsonChildNode.getString("result").toString();
			message= jsonChildNode.getString("message").toString();
		
			if (result.equals("0")) {
				
				JSONArray jsonArrayInvoices = new JSONArray(jsonChildNode.getString("invoices"));
		        
				for (int i = 0; i < jsonArrayInvoices.length(); i++) {

					InvoiceModel invoice = new InvoiceModel();
					JSONObject jsonObj1 = jsonArrayInvoices.getJSONObject(i);
					invoice.setYearName(jsonObj1.getString("year").toString());
					JSONArray jsonArraymonths = new JSONArray(jsonObj1.getString("months"));
			        
					for (int j = 0; j < jsonArraymonths.length(); j++) {
						JSONObject jsonObj2 = jsonArraymonths.getJSONObject(j);
						invoice.setMonthName(jsonObj2.getString("month").toString());
						invoice.setInvoiceUrl(jsonObj2.getString("invoicelink").toString());
						}
				
					
										
					getbasicdetailStatement.add(invoice);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getbasicdetailStatement ;
		
	}

}

