package com.equiworx.util;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CustomcheckBox extends LinearLayout implements Checkable 
	{ 
	       // Add your constructors 

	   public CustomcheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

			@Override 
	        public boolean isChecked() { 
	                // TODO Auto-generated method stub 
	                return this.checked; 
	        } 

	        @Override 
	        public void setChecked(boolean checked) { 
	                // TODO Auto-generated method stub 
	                getCheckBox().setChecked(checked); 
	                this.checked = checked; 
	                refreshDrawableState(); 

	               // Append your event handler here 
	        } 

	        @Override 
	        public void toggle() { 
	                // TODO Auto-generated method stub 
	                //getCheckBox().setChecked(!checked); 
	                setChecked(!checked); 
	        } 

	        private CheckBox getCheckBox() 
	        { 

	                if (checkbox == null) 
	                { 
	                        checkbox = (CheckBox) findViewById(android.R.id.checkbox); 
//	                        checkbox.setFocusable(false); 
//	                        checkbox.setClickable(false);                 // The check box 
	//should decline the click event, hence the list item could be clicked. 
	                } 
	                return checkbox; 
	        } 

	        private CheckBox checkbox; 
	        private boolean checked; 

	} 


