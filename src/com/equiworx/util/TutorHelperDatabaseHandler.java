package com.equiworx.util;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

import com.equiworx.model.Lesson;
import com.equiworx.model.Lesson_Booked;
import com.equiworx.model.MyLesson;
import com.equiworx.model.Parent;
import com.equiworx.model.StudentList;

import android.R.array;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TutorHelperDatabaseHandler extends SQLiteOpenHelper{
 
    //The Android's default system path of your application database.
    @SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/com.qrpatrol.activity/databases/";
 
    private static String DB_NAME = "TutorHelper_db.sqlite";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
    private String TABLE_STUDENT_DETAILS = "StudentDetails";
    private String TABLE_PARENTS_DETAILS = "ParentsDetails";
    private String TABLE_LESSON_DETAILS = "LessonDetails";
    private String TABLE_LESSON_BOOKED = "LessonBooked";
    //field for StudentList table
	private String StudentId = "studentId";
	private String Name = "name";
	private String Email = "email";
	private String Address = "address";
	private String ContactInfo = "contactInfo";
	private String Gender = "gender";
	private String IsActiveInMonth = "isActiveInMonth";
	private String Fees = "fees";
	private String Notes = "notes";
	private String ParentId = "parentId";
	
	//field for Parents Detail table
		private String PID = "Parentid";
		private String ParentName = "name";
		private String ParentEmail = "email";
		private String ParentAddress = "address";
		private String ParentContactInfo ="contact_number";
		private String ParentContactInfo_Alt ="contact_number_alt";
		private String Parent_NO_Students = "no_of_students";
		private String Parent_No_Of_Active_Student = "no_of_active_students";
		private String ParentNotes = "notes";
		private String Parent_Out_Standing_Balance = "outstanding_balance";
	
		//field for lesson Detail table
				private String LID = "lessonid";
				private String Description = "description";
				private String Starttime = "starttime";
				private String Endtime = "endtime";
				private String Startdate ="startdate";
				private String Enddate ="enddate";
				private String Recurring = "recurring";
				private String Lesson_is_active = "lesson_is_active";
				private String Duration = "duration";
				private String Lesson_tutor_id = "lesson_tutor_id";
				private String Tutor_name = "tutor_name";
	    // last_updated
				
				//field for lesson Detail table
			
				private String LessonDate ="lessondate";
			
				
	    SQLiteCursor cursor;
    
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
	
    public TutorHelperDatabaseHandler(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
   
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
		
		String CREATE_STUDENT_TABLE = "CREATE TABLE if NOT Exists " + TABLE_STUDENT_DETAILS + "("
				+ StudentId + " TEXT," + Name + " TEXT," + Email + " TEXT,"
				+ Address + " TEXT,"+ ContactInfo + " TEXT,"+ Gender + " TEXT,"+ IsActiveInMonth + " TEXT,"+ Fees + " TEXT,"
				+ Notes + " TEXT ,"+ ParentId + " TEXT)";

		
		String CREATE_PARENT_DETAILS = "CREATE TABLE if NOT Exists " + TABLE_PARENTS_DETAILS + "("
				+ PID + " TEXT," + ParentName + " TEXT,"
				+ ParentEmail + " TEXT,"+ ParentAddress + " TEXT,"
				+ ParentContactInfo + " TEXT,"+ ParentContactInfo_Alt + " TEXT,"
				+ Parent_NO_Students + " TEXT,"+ Parent_No_Of_Active_Student + " TEXT,"
				+ ParentNotes + " TEXT ,"+ Parent_Out_Standing_Balance + " TEXT)";	
		
		String CREATE_LESSON_DETAILS= "CREATE TABLE if NOT Exists " + TABLE_LESSON_DETAILS + "("
				+ LID + " TEXT," + Description + " TEXT,"
				+ Starttime + " TEXT,"+ Endtime + " TEXT,"
				+ Startdate + " TEXT,"+ Enddate + " TEXT,"
				+ Lesson_is_active + " TEXT,"+ Duration + " TEXT,"
				+ Lesson_tutor_id + " TEXT ,"+ Tutor_name + " TEXT)";	
	
		String CREATE_LESSON_BOOKED= "CREATE TABLE if NOT Exists " + TABLE_LESSON_BOOKED + "("
				+ LID + " TEXT," + Description + " TEXT,"
				+ Starttime + " TEXT,"+ Endtime + " TEXT,"
				+ LessonDate + " TEXT)";
	
		db.execSQL(CREATE_STUDENT_TABLE);
		db.execSQL(CREATE_PARENT_DETAILS);
		db.execSQL(CREATE_LESSON_DETAILS);
		db.execSQL(CREATE_LESSON_BOOKED);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENTS_DETAILS);
		//onCreate(db);
	}
	
	public void deleteStudentDetails(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_STUDENT_DETAILS, null, null);
		db.close();
	}

	public void deleteParentDetail() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PARENTS_DETAILS, null, null);
		db.close();
	}
	public void deleteLessonDetail() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LESSON_DETAILS, null, null);
		db.close();
	}
	public void deleteLessonbooked() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LESSON_BOOKED, null, null);
		db.close();
	}

	
	public void updateLessonBooked(ArrayList<Lesson_Booked> array_lesson) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		for(int i = 0; i<array_lesson.size(); i++){
			Lesson_Booked lesson = array_lesson.get(i);
			//String selectQuery1 = "SELECT  * FROM "+TABLE_PARENTS_DETAILS +" where "+PID+"="+ ParentList.getParentId();  

			try{
				ContentValues values = new ContentValues();
				values.put(LID, lesson.getID()); 
				values.put(Description, lesson.getDescription());
				values.put(Starttime, lesson.getStart_timing());
				values.put(Endtime, lesson.getEnd_timing());
				values.put(LessonDate, lesson.getDate());
				
				
				
				
				//cursor = (SQLiteCursor) db.rawQuery(selectQuery1, null);
				//if (cursor.moveToFirst()) {
					// updating row
				//	int a=db.update(TABLE_PARENTS_DETAILS, values, PID + " = ?",
				//			new String[] { String.valueOf(ParentList.getParentId()) });
				//}else{
					db.insert(TABLE_LESSON_BOOKED, null, values);
				//}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		db.close();
	}
	//get booked date
	public ArrayList<Lesson_Booked> getLessonBooked(String trigger,String date3,String date4) {
		
		
	ArrayList<Lesson_Booked> arrayList_booked=new ArrayList<Lesson_Booked>();
		String selectQuery = null;
		if(trigger.equals("all"))
		{
			selectQuery = "SELECT  * FROM "+TABLE_LESSON_BOOKED;    
			}
		else
		{
			selectQuery = "SELECT  * FROM "+TABLE_LESSON_BOOKED+" where "+LessonDate+">="+date3 +" AND "+LessonDate+"<="+date4;
			}
		SQLiteDatabase db = this.getReadableDatabase();
		
		try{
			cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
			if (cursor.moveToFirst()) {
				do {
				
					Lesson_Booked lesson_booked = new Lesson_Booked();
					lesson_booked.setID(cursor.getString(cursor.getColumnIndex(LID)));
					lesson_booked.setDescription(cursor.getString(cursor.getColumnIndex(Description)));
					lesson_booked.setStart_timing(cursor.getString(cursor.getColumnIndex(Starttime)));
					lesson_booked.setEnd_timing(cursor.getString(cursor.getColumnIndex(Endtime)));
					lesson_booked.setDate(cursor.getString(cursor.getColumnIndex(LessonDate)));
					
					arrayList_booked.add(lesson_booked);
					
				
										//array_studentlist.set(studentList);
				} while (cursor.moveToNext());
			}
			
			cursor.getWindow().clear();
			cursor.close();
			// close inserting data from database
			db.close();
			// return city list
			return arrayList_booked;
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (cursor != null)
			{
				cursor.getWindow().clear();
				cursor.close();
			}
			
			db.close();
			return arrayList_booked;
		}
	}
	// update parent
public void updateLessonDetail(ArrayList<MyLesson> array_lesson) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = this.getWritableDatabase();
			for(int i = 0; i<array_lesson.size(); i++){
				MyLesson lesson = array_lesson.get(i);
				//String selectQuery1 = "SELECT  * FROM "+TABLE_PARENTS_DETAILS +" where "+PID+"="+ ParentList.getParentId();  
	
				try{
					ContentValues values = new ContentValues();
					values.put(LID, lesson.getLessonId()); 
					values.put(Description, lesson.getLessonDescription());
					values.put(Starttime, lesson.getStartTime());
					values.put(Endtime, lesson.getEndTime());
					values.put(Startdate, lesson.getStartTime());
					values.put(Enddate, lesson.getLessonenddate());
					values.put(Lesson_is_active, lesson.getIsActive());
					values.put(Duration, lesson.getDuration());
					values.put(Lesson_tutor_id, lesson.getLesson_tutor_id());
					values.put(Tutor_name, lesson.getTutor_name());
					
					
					
					//cursor = (SQLiteCursor) db.rawQuery(selectQuery1, null);
					//if (cursor.moveToFirst()) {
						// updating row
					//	int a=db.update(TABLE_PARENTS_DETAILS, values, PID + " = ?",
					//			new String[] { String.valueOf(ParentList.getParentId()) });
					//}else{
						db.insert(TABLE_LESSON_DETAILS, null, values);
					//}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			db.close();
		}
	// update parent
	public void updateParentDetail(ArrayList<Parent> parentList) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		for(int i = 0; i<parentList.size(); i++){
			Parent ParentList = parentList.get(i);
			//String selectQuery1 = "SELECT  * FROM "+TABLE_PARENTS_DETAILS +" where "+PID+"="+ ParentList.getParentId();  
	
			try{
				ContentValues values = new ContentValues();
				values.put(PID, ParentList.getParentId()); 
				values.put(ParentName, ParentList.getName());
				values.put(ParentEmail, ParentList.getEmail());
				values.put(ParentAddress, ParentList.getAddress());
				values.put(ParentContactInfo, ParentList.getContactNumber());
				values.put(ParentContactInfo_Alt, ParentList.getAltContactNumber());
				values.put(Parent_NO_Students, ParentList.getStudentCount());
				values.put(Parent_No_Of_Active_Student, ParentList.getActivestudents());
				values.put(ParentNotes, ParentList.getNotes());
				values.put(Parent_Out_Standing_Balance, ParentList.getOutstandingBalance());
				
				
				//cursor = (SQLiteCursor) db.rawQuery(selectQuery1, null);
				//if (cursor.moveToFirst()) {
					// updating row
				//	int a=db.update(TABLE_PARENTS_DETAILS, values, PID + " = ?",
				//			new String[] { String.valueOf(ParentList.getParentId()) });
				//}else{
					db.insert(TABLE_PARENTS_DETAILS, null, values);
				//}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		db.close();
	}
	
//edit student
	public void updateStudentDetails(ArrayList<StudentList> schedule) {
		SQLiteDatabase db = this.getWritableDatabase();
		for(int i = 0; i<schedule.size(); i++){
			StudentList studentList = schedule.get(i);
			String selectQuery = "SELECT  * FROM "+TABLE_STUDENT_DETAILS+" where "+StudentId+"="+ studentList.getStudentId();  
	
			try{
				ContentValues values = new ContentValues();
				values.put(StudentId, studentList.getStudentId()); 
				values.put(Name, studentList.getName());
				values.put(Email, studentList.getEmail());
				values.put(Address, studentList.getAddress());
				values.put(ContactInfo, studentList.getContactInfo());
				values.put(Gender, studentList.getGender());
				values.put(IsActiveInMonth, studentList.getIsActiveInMonth());
				values.put(Fees, studentList.getFees());
				values.put(Notes, studentList.getNotes());
				values.put(ParentId, studentList.getParentId());
				
				
				cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					// updating row
					int a=db.update(TABLE_STUDENT_DETAILS, values, StudentId + " = ?",
							new String[] { String.valueOf(studentList.getStudentId()) });
				}else{
					db.insert(TABLE_STUDENT_DETAILS, null, values);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		db.close();
    }
	

		//get student list
		public StudentList getStudentDetails(String trigger,String id) {
		
				StudentList studentList = new StudentList();
			
				String selectQuery = null;
				if(trigger.equals("all"))
				{
					selectQuery = "SELECT  * FROM "+TABLE_STUDENT_DETAILS;    
					}
				else
				{
					selectQuery = "SELECT  * FROM "+TABLE_STUDENT_DETAILS+" where "+StudentId+" = "+id;
					}
				SQLiteDatabase db = this.getReadableDatabase();
				
				try{
					cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
					if (cursor.moveToFirst()) {
						do {
							
									
							//StudentList studentList = new StudentList();
							studentList.setStudentId(cursor.getString(cursor.getColumnIndex(StudentId)));
							studentList.setName(cursor.getString(cursor.getColumnIndex(Name)));
							studentList.setEmail(cursor.getString(cursor.getColumnIndex(Email)));
							studentList.setAddress(cursor.getString(cursor.getColumnIndex(Address)));
							studentList.setContactInfo(cursor.getString(cursor.getColumnIndex(ContactInfo)));
							studentList.setGender(cursor.getString(cursor.getColumnIndex(Gender)));
							studentList.setIsActiveInMonth(cursor.getString(cursor.getColumnIndex(IsActiveInMonth)));
							
							studentList.setFees(cursor.getString(cursor.getColumnIndex(Fees)));
							studentList.setNotes(cursor.getString(cursor.getColumnIndex(Notes)));
							studentList.setParentId(cursor.getString(cursor.getColumnIndex(ParentId)));
						
												//array_studentlist.set(studentList);
						} while (cursor.moveToNext());
					}
					
					cursor.getWindow().clear();
					cursor.close();
					// close inserting data from database
					db.close();
					// return city list
					return studentList;
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
					if (cursor != null)
					{
						cursor.getWindow().clear();
						cursor.close();
					}
					
					db.close();
					return studentList;
				}
			}
		//get lesosn details
		public ArrayList<MyLesson> getLessonDetails() {
			
		    ArrayList<MyLesson> array_mylesson=new ArrayList<MyLesson>();
			String selectQuery = null;
			//if(trigger.equals("all"))
			//{
				selectQuery = "SELECT  * FROM "+TABLE_PARENTS_DETAILS;    
				//}
			//else
			//{
				//selectQuery = "SELECT  * FROM "+TABLE_PARENTS_DETAILS+" where "+PID+" = "+id;
				//}
				/*Parent temp = new Parent();
	             
				temp.setParentId("0");
				temp.setName("Select existing parent");
				
				getparetd.add(temp);*/
			SQLiteDatabase db = this.getReadableDatabase();
			
			try{
				cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
				
						
						MyLesson myLesson=new MyLesson();				
						//StudentList studentList = new StudentList();
						myLesson.setLessonId(cursor.getString(cursor.getColumnIndex(LID)));
						myLesson.setLessonDescription(cursor.getString(cursor.getColumnIndex(Description)));
						myLesson.setStartTime((cursor.getString(cursor.getColumnIndex(Starttime))));
						myLesson.setEndTime(cursor.getString(cursor.getColumnIndex(Endtime)));
						myLesson.setLessonDate(cursor.getString(cursor.getColumnIndex(Startdate)));
						myLesson.setLessonenddate(cursor.getString(cursor.getColumnIndex(Enddate)));
						myLesson.setLesson_is_active(cursor.getString(cursor.getColumnIndex(Lesson_is_active)));
						
						myLesson.setDuration(cursor.getString(cursor.getColumnIndex(Duration)));
						myLesson.setLesson_tutor_id(cursor.getString(cursor.getColumnIndex(Lesson_tutor_id)));
						myLesson.setTutor_name(cursor.getString(cursor.getColumnIndex(Tutor_name)));
						array_mylesson.add(myLesson);
											//array_studentlist.set(studentList);
					} while (cursor.moveToNext());
				}
				
				cursor.getWindow().clear();
				cursor.close();
				// close inserting data from database
				db.close();
				// return city list
				return array_mylesson;
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (cursor != null)
				{
					cursor.getWindow().clear();
					cursor.close();
				}
				
				db.close();
				return array_mylesson;
			}
		}

//get parents list
	public ArrayList<Parent> getParentsDetails() {
				
					    ArrayList<Parent> getparetd=new ArrayList<Parent>();
						String selectQuery = null;
						//if(trigger.equals("all"))
						//{
							selectQuery = "SELECT  * FROM "+TABLE_PARENTS_DETAILS;    
							//}
						//else
						//{
							//selectQuery = "SELECT  * FROM "+TABLE_PARENTS_DETAILS+" where "+PID+" = "+id;
							//}
							Parent temp = new Parent();
				             
							temp.setParentId("0");
							temp.setName("Select existing parent");
							
							getparetd.add(temp);
						SQLiteDatabase db = this.getReadableDatabase();
						
						try{
							cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
							if (cursor.moveToFirst()) {
								do {
									Parent studentList=new Parent();				
									//StudentList studentList = new StudentList();
									studentList.setParentId(cursor.getString(cursor.getColumnIndex(PID)));
									studentList.setName(cursor.getString(cursor.getColumnIndex(ParentName)));
									studentList.setEmail(cursor.getString(cursor.getColumnIndex(ParentEmail)));
									studentList.setAddress(cursor.getString(cursor.getColumnIndex(ParentAddress)));
									studentList.setContactNumber(cursor.getString(cursor.getColumnIndex(ParentContactInfo)));
									studentList.setAltContactNumber(cursor.getString(cursor.getColumnIndex(ParentContactInfo_Alt)));
									studentList.setStudentCount(cursor.getString(cursor.getColumnIndex(Parent_NO_Students)));
									
									studentList.setActivestudents(cursor.getString(cursor.getColumnIndex(Parent_No_Of_Active_Student)));
									studentList.setNotes(cursor.getString(cursor.getColumnIndex(ParentNotes)));
									studentList.setOutstandingBalance(cursor.getString(cursor.getColumnIndex(Parent_Out_Standing_Balance)));
									getparetd.add(studentList);
														//array_studentlist.set(studentList);
								} while (cursor.moveToNext());
							}
							
							cursor.getWindow().clear();
							cursor.close();
							// close inserting data from database
							db.close();
							// return city list
							return getparetd;
							
						}
						catch (Exception e)
						{
							e.printStackTrace();
							if (cursor != null)
							{
								cursor.getWindow().clear();
								cursor.close();
							}
							
							db.close();
							return getparetd;
						}
					}
		
	//get student 	
		public ArrayList<StudentList> getStudentAll(String trigger,String id) {
			
			ArrayList<StudentList> aastudentList = new ArrayList<StudentList>();
		
			String selectQuery = null;
			if(trigger.equals("all"))
			{
				selectQuery = "SELECT  * FROM "+TABLE_STUDENT_DETAILS;    
				}
			else
			{
				selectQuery = "SELECT  * FROM "+TABLE_STUDENT_DETAILS+" where "+StudentId+" = "+id;
				}
			SQLiteDatabase db = this.getReadableDatabase();
			
			try{
				cursor = (SQLiteCursor) db.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
						
								
						StudentList studentList = new StudentList();
						studentList.setStudentId(cursor.getString(cursor.getColumnIndex(StudentId)));
						studentList.setName(cursor.getString(cursor.getColumnIndex(Name)));
						studentList.setEmail(cursor.getString(cursor.getColumnIndex(Email)));
						studentList.setAddress(cursor.getString(cursor.getColumnIndex(Address)));
						studentList.setContactInfo(cursor.getString(cursor.getColumnIndex(ContactInfo)));
						studentList.setGender(cursor.getString(cursor.getColumnIndex(Gender)));
						studentList.setIsActiveInMonth(cursor.getString(cursor.getColumnIndex(IsActiveInMonth)));
						
						studentList.setFees(cursor.getString(cursor.getColumnIndex(Fees)));
						studentList.setNotes(cursor.getString(cursor.getColumnIndex(Notes)));
						studentList.setParentId(cursor.getString(cursor.getColumnIndex(ParentId)));
						aastudentList.add(studentList);
											//array_studentlist.set(studentList);
					} while (cursor.moveToNext());
				}
				
				cursor.getWindow().clear();
				cursor.close();
				// close inserting data from database
				db.close();
				// return city list
				return aastudentList;
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if (cursor != null)
				{
					cursor.getWindow().clear();
					cursor.close();
				}
				
				db.close();
				return aastudentList;
			}
		}
	
	    public void openDataBase() throws SQLException{
	 
	    	//Open the database
	        String myPath = DB_PATH + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	 
	    }
	 
	    @Override
		public synchronized void close() 
	    {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}

		
	 
}