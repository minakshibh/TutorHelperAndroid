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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
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
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerateActivity extends Activity
{
	
	private static String FILE = Environment.getExternalStorageDirectory()+File.separator+"TH_report.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	TextView txt1;
	//String mPath = Environment.getExternalStorageDirectory() + File.separator + "test.png";
	Bitmap bitmap;
	View layout_main;
	Button strat, pdff;
	String path, sdpath;
	private BaseFont bfBold;
	static String tutorid="",tutorname="";
	private BaseFont bf;
	private int pageNumber = 0;
	private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	SharedPreferences tutorPrefs;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_statement);
		
			//new EventListTask2().execute();
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
	        document.addTitle("Statement");
	        document.addSubject("Using iText");
	        document.addKeywords("Java, PDF, iText");
	        document.addAuthor("Lars Vogel");
	        document.addCreator("Lars Vogel");
	    }

		
	    private static void addTitlePage(Document document) throws DocumentException {
	    	
	    	Paragraph paragraph = new Paragraph();
	    	paragraph.add(("Statement"));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
	        
			Paragraph preface = new Paragraph();
	     	 // We add one empty line
	        addEmptyLine(preface, 1);
	        // Lets write a big header
	      //  preface.add(new Paragraph("", catFont));
	       // preface.setAlignment(Element.ALIGN_CENTER);
	       // addEmptyLine(preface, 1);
	    
	        preface.add(new Paragraph("Start Date : "+StatementActivity.startdate));
	        preface.setAlignment(Element.ALIGN_LEFT);
	       // addEmptyLine(preface, 1);
	            //preface.add(new Paragraph("End Date : "+StatementActivity.enddate+"                                     "+"Tutor Id : "+tutorid, smallBold));
	        //addEmptyLine(preface, 1);
	     // preface.add(new Paragraph("Parent Name : "+StatementActivity.parentname+"                                     "+"Tutor Name : "+tutorname, smallBold));
	    
	    	
	    	document.add(preface);
	       
	    	Paragraph paragraph2 = new Paragraph();
	    	paragraph2.add("Generate Date : "+getCurrentDateTime());
			paragraph2.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph2);
			
			Paragraph paragraph3 = new Paragraph();
	    	paragraph3.add("End Date : "+StatementActivity.enddate);
			paragraph3.setAlignment(Element.ALIGN_LEFT);
			document.add(paragraph3);
			
			Paragraph paragraph4 = new Paragraph();
	    	paragraph4.add("Tutor Id : "+tutorid);
			paragraph4.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph4);
			
			Paragraph paragraph5 = new Paragraph();
	    	paragraph5.add(("Parent Name : "+StatementActivity.parentname));
			paragraph5.setAlignment(Element.ALIGN_LEFT);
			document.add(paragraph5);
			
			Paragraph paragraph6 = new Paragraph();
	    	paragraph6.add("Tutor Name : "+tutorname);
			paragraph6.setAlignment(Element.ALIGN_RIGHT);
			addEmptyLine(paragraph6, 2);
			document.add(paragraph6);
			
			
	    	
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

	      
	       
	        document.add(table);
	        // Start a new page
	       // document.newPage();
	        
	    }

	   /* private static void addContent(Document document) throws DocumentException {
	        Anchor anchor = new Anchor("ESTIMATING APP", catFont);
	        anchor.setName("ESTIMATING APP");

	        // Second parameter is the number of the chapter
	        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

	        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
	        Section subCatPart = catPart.addSection(subPara);
	        subCatPart.add(new Paragraph("Hello"));

	        subPara = new Paragraph("Subcategory 2", subFont);
	        subCatPart = catPart.addSection(subPara);
	        subCatPart.add(new Paragraph("Paragraph 1"));
	        subCatPart.add(new Paragraph("Paragraph 2"));
	        subCatPart.add(new Paragraph("Paragraph 3"));

	        // Add a list
	    //    createList(subCatPart);
	        Paragraph paragraph = new Paragraph();
	        addEmptyLine(paragraph, 2);
	        subCatPart.add(paragraph);

	        // Add a table
	        createTable(subCatPart);

	        // Now add all this to the document
	        document.add(catPart);

	        // Next section
	        anchor = new Anchor("Second Chapter", catFont);
	        anchor.setName("Second Chapter");

	        // Second parameter is the number of the chapter
	        catPart = new Chapter(new Paragraph(anchor), 1);

	        subPara = new Paragraph("Subcategory", subFont);
	        subCatPart = catPart.addSection(subPara);
	        subCatPart.add(new Paragraph("This is a very important message"));

	        // Now add all this to the document
	        document.add(catPart);

	    }*/

	    private static void createTable(Paragraph subCatPart)
	            throws BadElementException {
	    	 float[] columnWidths = {1f, 2f, 3f, 2f,2f};
		        PdfPTable table = new PdfPTable(columnWidths);
		        table.setWidthPercentage(90f);
	       

	        // t.setBorderColor(BaseColor.GRAY);
	        // t.setPadding(4);
	        // t.setSpacing(4);
	        // t.setBorderWidth(1);

	        PdfPCell c1 = new PdfPCell(new Phrase("S no:",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        //c1 = new PdfPCell(new Paragraph(teacherToolsVO.getToolName(),font10));
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Date",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("Remark",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	        c1 = new PdfPCell(new Phrase("Payment Mode",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	       
	        c1 = new PdfPCell(new Phrase("Fee Paid",smallBold));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	       for(int i=0;i<StatementActivity.statement_detail.size();i++) 
	       {
	    	   int j=i+1;
	        table.setHeaderRows(i);
	        table.addCell("    "+j+"");
	        table.addCell(" "+StatementActivity.statement_detail.get(i).getLast_updated());
	        table.addCell(" "+StatementActivity.statement_detail.get(i).getRemarks());
	        table.addCell(" "+StatementActivity.statement_detail.get(i).getPayment_mode());
	        table.addCell("$ "+StatementActivity.statement_detail.get(i).getFee_paid());
	       
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
