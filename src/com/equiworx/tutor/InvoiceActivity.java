package com.equiworx.tutor;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.widget.Toast;
import com.equiworx.tutorhelper.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceActivity extends Activity {
	
	private static String FILE = Environment.getExternalStorageDirectory()+File.separator+"TH_Report.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font smalls = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	static String tutorid="",tutorname="";
	SharedPreferences tutorPrefs;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_invoice);
		
		tutorPrefs = getSharedPreferences("tutor_prefs", MODE_PRIVATE);	
		tutorid= tutorPrefs.getString("tutorID","");
		tutorname=tutorPrefs.getString("tutorname", "");
		   try {
	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(FILE));
	            document.open();
	            addMetaData(document);
	            addTitlePage(document);
	            //addContent(document);
	            //createImage();
	            document.close();
	            displaypdf();
	            finish();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }  
		}
		private static void addMetaData(Document document) {
	        document.addTitle("Raport");
	        document.addSubject("Using iText");
	        document.addKeywords("Java, PDF, iText");
	        document.addAuthor("Lars Vogel");
	        document.addCreator("Lars Vogel");
	    }

		
	    private static void addTitlePage(Document document) throws DocumentException {
	    	Paragraph paragraph = new Paragraph();
			paragraph.add("Report");
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			/*Paragraph paragraph1 = new Paragraph();
			paragraph1.add("Report Generate Date : "+getCurrentDateTime());
			paragraph1.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph1);*/
	        Paragraph preface = new Paragraph();
	     	        // We add one empty line
	        addEmptyLine(preface, 1);
	        // Lets write a big header
	        preface.add(new Paragraph(Payment_FulllessonDetialsActivity.description, catFont));
	       // preface.setAlignment(Element.ALIGN_RIGHT);
	        addEmptyLine(preface, 1);
	      preface.add(new Paragraph("Report Generate Date : "+getCurrentDateTime()));
	        preface.add(new Paragraph("Start Date : "+Payment_FulllessonDetialsActivity.sdate+""));
	        preface.add(new Paragraph("End Date : "+Payment_FulllessonDetialsActivity.edate));
	        preface.add(new Paragraph("Days : "+Payment_FulllessonDetialsActivity.getdays));
	        addEmptyLine(preface, 1);
	        //preface.add(new Paragraph("Parent Name : "+StatementActivity.parentname+"                                      "+"Tutor Name : "+tutorname, subFont));
	    	//preface.setAlignment(Element.ALIGN_MIDDLE);
	    	addEmptyLine(preface, 1);
	    	document.add(preface);
	       
	    	
	    	
	    	/*Paragraph preface_Right = new Paragraph();
	      	preface_Right.add(new Paragraph("Generate Date : "+getCurrentDateTime(), subFont));
	    	preface_Right.add(new Paragraph("Tutor Id : "+tutorid, subFont));
	    	preface_Right.add(new Paragraph("Tutor Name : "+tutorname, subFont));
	    	preface_Right.setAlignment(Element.ALIGN_RIGHT);
	    	addEmptyLine(preface_Right, 2);

	    	document.add(preface_Right);*/

	    	Paragraph table = new Paragraph("", subFont);
	      
	      //   Anchor anchor = new Anchor("Table", catFont);
	       // anchor.setName("ESTIMATING APP");
	       // Chapter catPart = new Chapter(new Paragraph(anchor), 1);
	        //Section subCatPart = catPart.addSection(subPara);
	        createTable(table);
	       /* preface.add(new Paragraph(
	                "This document is a preliminary version and not subject to your license agreement or anycvcxvcxvcxvcxvcxvvcxvvcxcxv" +
	                "vcx" +
	        
	                "        vbclbclkbfgkkfgfgkfgk other agreement with vogella.de ;-).",
	                redFont));*/

	      
	        addEmptyLine(table, 2);
	        document.add(table);
	        
	        Paragraph title = new Paragraph();
 	        // Lets write a big header
	        title.add(new Paragraph("Session Details : ", smallBold));
	     // We add one empty line
	        addEmptyLine(title, 1);
	        title.setAlignment(Element.ALIGN_RIGHT);
	        document.add(title);
	    	
	        Paragraph table2 = new Paragraph("", subFont);
	         createTable2(table2);
	       
	        document.add(table2);
	        // Start a new page
	       // document.newPage();
	        
	    }

	    private static void createTable(Paragraph subCatPart)
	            throws BadElementException {
	    	 float[] columnWidths = {1f, 2f, 2.5f, 2f,2.5f};
	        PdfPTable table = new PdfPTable(columnWidths);
	        table.setWidthPercentage(90f);


	        PdfPCell c1 = new PdfPCell(new Phrase("S No",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        //c1 = new PdfPCell(new Paragraph(teacherToolsVO.getToolName(),font10));
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Student Id",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Student Name",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	        c1 = new PdfPCell(new Phrase("Tutor Id",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Tutor Name",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	     
	       for(int i=0;i<Payment_LessonsDetailsActivity.arraylist_studentlist.size();i++) 
	       {
	    	   int j=i+1;
	        table.setHeaderRows(i);
	       // table.addCell("     "+j+"");
	        insertCell(table, ""+j, Element.ALIGN_CENTER, 1, smalls);
	        insertCell(table, Payment_LessonsDetailsActivity.arraylist_studentlist.get(i).getStudentId(), Element.ALIGN_CENTER, 1, smalls);
	        //table.addCell(" "+Payment_LessonsDetailsActivity.arraylist_studentlist.get(i).getStudentId());
	       // table.addCell(" "+Payment_LessonsDetailsActivity.arraylist_studentlist.get(i).getName());
	        insertCell(table, Payment_LessonsDetailsActivity.arraylist_studentlist.get(i).getName(), Element.ALIGN_CENTER, 1, smalls);
	      //  table.addCell(" "+tutorid);
	        insertCell(table, tutorid, Element.ALIGN_CENTER, 1, smalls);
	        insertCell(table, tutorname, Element.ALIGN_CENTER, 1, smalls);
	       // table.addCell(" "+tutorname);
	       
	       }

	        subCatPart.add(table);

	    }
	    
	    private static void createTable2(Paragraph subCatPart)
	            throws BadElementException {
	    	
	    	float[] columnWidths = {0.5f, 2f, 2f};
		     PdfPTable table = new PdfPTable(columnWidths);
		     table.setWidthPercentage(80f);
		     

	        // t.setBorderColor(BaseColor.GRAY);
	        // t.setPadding(4);
	        // t.setSpacing(4); tts000
	        // t.setBorderWidth(1);

	        PdfPCell c1 = new PdfPCell(new Phrase("S No",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        //c1 = new PdfPCell(new Paragraph(teacherToolsVO.getToolName(),font10));
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Session Date",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("/hr",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	     
	       for(int i=0;i<Payment_FulllessonDetialsActivity.array_sessiondate.size();i++) 
	       {
	    	   int j=i+1;
	        table.setHeaderRows(i);
	       // table.addCell("     "+j+"");
	        insertCell(table, ""+j, Element.ALIGN_CENTER, 1, smalls);
	        insertCell(table, Payment_FulllessonDetialsActivity.array_sessiondate.get(i), Element.ALIGN_CENTER, 1, smalls);
	        insertCell(table,"$"+Payment_FulllessonDetialsActivity.totalfess+"/hr", Element.ALIGN_CENTER, 1, smalls);
	      //  table.addCell(" "+Payment_FulllessonDetialsActivity.array_sessiondate.get(i));
	       // table.addCell(" "+"$"+Payment_FulllessonDetialsActivity.totalfess);
	       // table.addCell(StatementActivity.statement_detail.get(i).getPayment_mode());
	      //  table.addCell("$ "+StatementActivity.statement_detail.get(i).getFee_paid());
	       
	       }

	        subCatPart.add(table);

	    }

	    private static void createList(Section subCatPart) {
	        List list = new List(true, false, 10);
	        list.add(new ListItem("First point"));
	        list.add(new ListItem("Second point"));
	        list.add(new ListItem("Third point"));
	        subCatPart.add(list);
	    }

	    private static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }
	    
	    private static void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
	      
	     //create a new cell with the specified Text and Font
	     PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
	     //set the cell alignment
	     cell.setHorizontalAlignment(align);
	     //set the cell column span in case you want to merge two or more cells
	     cell.setColspan(colspan);
	     //in case there is no text and you wan to create an empty row
	     if(text.trim().equalsIgnoreCase("")){
	      cell.setMinimumHeight(10f);
	     }
	     //add the call to the table
	     table.addCell(cell);
	      
	    }
	
	    public void displaypdf() {
	    //	String dir="/sdcard";
	        File file = null;
	            file = new File(FILE);
	        Toast.makeText(getApplicationContext(), file.toString() , Toast.LENGTH_LONG).show();
	        if(file.exists()) {
	            Intent target = new Intent(Intent.ACTION_VIEW);
	            target.setDataAndType(Uri.fromFile(file), "application/pdf");
	            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	            Intent intent = Intent.createChooser(target, "Open File");
	            try {
	                startActivity(intent);
	            } catch (ActivityNotFoundException e) {
	                // Instruct the user to install a PDF reader here, or something
	            	e.printStackTrace();
	            }
	        }
	        else
	            Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
	    }
	    private static String getCurrentDateTime() {		
			Calendar mCalendar=Calendar.getInstance();
			String selectedDate=formatter.format(mCalendar.getTime());	
			return selectedDate;
		}
}
