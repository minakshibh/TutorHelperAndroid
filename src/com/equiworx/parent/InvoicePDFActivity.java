package com.equiworx.parent;


import com.equiworx.tutorhelper.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InvoicePDFActivity extends Activity {

    private WebView webView;
    private String url="";
    private TextView title;
    private RelativeLayout 	back_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_invoicepdf);
       
        if(getIntent().getStringExtra("url")!=null)
        {
        	url=getIntent().getStringExtra("url");
        	System.err.println("url"+url);
	       
	        }
        webView=(WebView)findViewById(R.id.webView);
        loadWebViewLoad(webView);
        title=(TextView)findViewById(R.id.title);
		title.setText("Invoice");
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		back_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }   
    private void loadWebViewLoad(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        String pdf = url;
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        //webview.loadUrl(url);
    }
   
}