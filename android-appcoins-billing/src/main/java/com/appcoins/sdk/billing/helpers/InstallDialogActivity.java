package com.appcoins.sdk.billing.helpers;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class InstallDialogActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /*
    setContentView(R.layout.install_dialog_activity);
    WalletUtils.setDialogActivity(this);
    WalletUtils.promptToInstallWallet();
    */
    WebView myWebView = new WebView(this);
    setContentView(myWebView);
    myWebView.getSettings().setUserAgentString("Chrome/76.0.3809.132");
    myWebView.loadUrl("https://www.google.com/url?sa=t&source=web&rct=j&url=https://play.google.com/store/apps/details%3Fid%3Dcom.duckduckgo.mobile.android%26hl%3Dpt%26referrer%3Dutm_source%253Dgoogle%2526utm_medium%253Dorganic%2526utm_term%253Dduck%2Bduck%2Bgo%26pcampaignid%3DAPPU_1_QZZvXaLCEI7jUoGjk4AN&ved=2ahUKEwji5tXz_rbkA\n"
        + "hWOsRQKHYHRBNAQ44QBMAZ6BAgGEAM&usg=AOvVaw1dKEckxNBczhx5jfiRjMnj");
  }
}