package com.example.jimbrayhackwebview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private WebView mWebView;
	private JimbrayWebClient mWebViewClient;
	private WebSettings mWebSettings;
	
	private String jsStr = "javascript:getvalbyid()";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViews();
		init();
		
		mWebView.loadUrl("file:///android_asset/index.html");
	}
	
	private void findViews() {
		mWebView = (WebView) findViewById(R.id.webview);
	}
	
	private void init() {
		mWebViewClient = new JimbrayWebClient();
		mWebSettings = mWebView.getSettings();
		mWebView.setWebViewClient(mWebViewClient);
		
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
			
		});

		mWebSettings.setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new WebAppInterface(this), "Jimbray");
	}

	private class JimbrayWebClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);  
	        return true;  
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			//在这里注入Js
			mWebView.loadUrl("http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js");
			mWebView.loadUrl(jsStr);
		}
		
	}
	
	public class WebAppInterface {
		Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c)
        {
            mContext = c;
        }

        /** Show a toast from the web page */
        // 如果target 大于等于API 17，则需要加上如下注解
        @JavascriptInterface
        public void showToast(String toast)
        {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }
	} 
}
