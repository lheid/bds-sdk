package com.asf.appcoins.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class IntentActivity extends Activity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    WebView myWebView = (WebView) findViewById(R.id.webview);
    myWebView.loadUrl("https://play.google.com/store/apps/details?id=com.appcoins.wallet");
  }
}
