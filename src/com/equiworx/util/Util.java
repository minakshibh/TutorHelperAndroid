package com.equiworx.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.equiworx.tutorhelper.R;


public class Util {

	static 	Context context;
	
	static public void alertMessage(Context ctx,String str)
	{
		context=ctx;
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Tutor Helper");
		alert.setMessage(str);
		alert.setPositiveButton("OK", null);
		alert.show();
	}
	static public void showToast(Context ctx,String str)
	{
		context=ctx;
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	public static final boolean isValidPhoneNumber(CharSequence target) {
	    if (target == null || TextUtils.isEmpty(target)) {
	        return false;
	    } else {
	        return android.util.Patterns.PHONE.matcher(target).matches();
	    }
	}
	
	static public void actionAlertMessage(Context ctx,String str)
	{
		context=ctx;
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Tutor Helper");
		alert.setMessage(str);
		alert.setPositiveButton("OK", null);
		alert.show();
	}
	
	static public boolean isNetworkAvailable(Context mCtx) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	public static String showImage(String path, ImageView imageView){
		int targetW = 500;
		 int targetH = 500;

		    // Get the dimensions of the bitmap
		    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		    bmOptions.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path, bmOptions);
		    int photoW = bmOptions.outWidth;
		    int photoH = bmOptions.outHeight;

		    // Determine how much to scale down the image
		    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

		    // Decode the image file into a Bitmap sized to fill the View
		    bmOptions.inJustDecodeBounds = false;
		    bmOptions.inSampleSize = scaleFactor;
		    bmOptions.inPurgeable = true;

		   Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
		    
//	        bitmap = (Bitmap) data.getExtras().get("data"); 
		   imageView.setImageBitmap(bitmap);
	   	
	        ByteArrayOutputStream bao = new ByteArrayOutputStream(); //convert images into base64
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
	        byte[] ba = bao.toByteArray();
	        String encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
	        
	        return encodedImage;
	}
	
	static public  void hideKeyboard(Context cxt) {
		context=cxt;
	    InputMethodManager inputManager = (InputMethodManager) cxt.getSystemService(Context.INPUT_METHOD_SERVICE);
	    
	    // check if no view has focus:
	    View view = ((Activity) cxt).getCurrentFocus();
	    if (view != null) {
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	
	
	public static String createImageFile() {
		try{
		    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		    String imageFileName = "TutorHelper_" + timeStamp;
	
		    String mCurrentPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+imageFileName+".jpg";
		    return mCurrentPhotoPath;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getResponseFromUrl(String functionName, List<NameValuePair> param, Context context){
		String responseString = "";
		
		try {
			
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			int timeoutSocket = 61000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpPost request = new HttpPost(context.getResources().getString(R.string.baseUrl)+"/"+functionName+".php");
	        request.setEntity(new UrlEncodedFormEntity(param));
	        HttpResponse response = httpClient.execute(request);
	       
	         HttpEntity httpEntity = response.getEntity();
	         responseString = EntityUtils.toString(httpEntity);
	         Log.e(functionName, "Output="+responseString);
	         
		} catch (ParseException e) {
			e.printStackTrace();
			Util.alertMessage(context, "Something went wrong. Please try again later.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseString;
	}
}
