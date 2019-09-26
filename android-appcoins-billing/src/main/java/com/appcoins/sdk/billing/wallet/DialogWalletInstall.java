package com.appcoins.sdk.billing.wallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.appcoins.billing.sdk.BuildConfig;
import com.appcoins.billing.sdk.R;
import com.appcoins.sdk.billing.helpers.InstallDialogActivity;
import com.appcoins.sdk.billing.helpers.Utils;
import com.appcoins.sdk.billing.helpers.WalletUtils;

import static android.graphics.Typeface.BOLD;

/**
 * Here is important to know in advance if the host app has feature graphic,
 * 1- this boolean hasImage is needed to change layout dynamically
 * 2- if so, we need to get  url of this image and then when copy this code to  apk-migrator
 * as Smali,
 * the correct dialog_wallet_install_graphic needs to be write
 */
public class DialogWalletInstall extends Dialog {

  private Button dialog_wallet_install_button_cancel;
  private Button dialog_wallet_install_button_download;
  private TextView dialog_wallet_install_text_message;
  private ImageView dialog_wallet_install_image_icon;
  private ImageView dialog_wallet_install_image_graphic;
  private int RESULT_USER_CANCELED = 1;

  private final String URL_APTOIDE = "market://details?id="
      + BuildConfig.BDS_WALLET_PACKAGE_NAME
      + "&utm_source=appcoinssdk&app_source="
      + getContext().getPackageName();
  private static Context contextApp;

  public static DialogWalletInstall with(Context context) {
    contextApp = context;
    return new DialogWalletInstall(context);
  }

  public DialogWalletInstall(Context context) {
    super(context);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    requestWindowFeature(Window.FEATURE_NO_TITLE);

    setContentView(contextApp.getResources()
        .getIdentifier("wallet_install_dialog", "layout", contextApp.getPackageName()));
    setCancelable(false);

    buildTop();
    buildMessage();
    buildCancelButton();
    buildDownloadButton();
  }

  private void buildTop() {
    boolean hasImage;
    Drawable icon = null;
    try {
      icon = contextApp.getPackageManager()
          .getApplicationIcon(contextApp.getPackageName());
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    dialog_wallet_install_image_icon = findViewById(contextApp.getResources()
        .getIdentifier("dialog_wallet_install_image_icon", "id", contextApp.getPackageName()));

    dialog_wallet_install_image_graphic = findViewById(contextApp.getResources()
        .getIdentifier("dialog_wallet_install_image_graphic", "id", contextApp.getPackageName()));

    dialog_wallet_install_image_graphic.setOutlineProvider(new ViewOutlineProvider() {
      @Override public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight() + dp(12)), dp(12));
        view.setClipToOutline(true);
      }
    });

    //dialog_wallet_install_has_image
    hasImage = getContext().getResources()
        .getBoolean(R.bool.dialog_wallet_install_has_image) && icon != null;

    if (!hasImage) {
      dialog_wallet_install_image_icon.setVisibility(View.INVISIBLE);
      RelativeLayout.LayoutParams lp =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(124));
      dialog_wallet_install_image_graphic.setLayoutParams(lp);
      int resourceId = contextApp.getResources()
          .getIdentifier("dialog_wallet_install_graphic", "drawable", contextApp.getPackageName());
      dialog_wallet_install_image_graphic.setImageDrawable(contextApp.getResources()
          .getDrawable(resourceId));
    } else {
      dialog_wallet_install_image_icon.setVisibility(View.VISIBLE);
      dialog_wallet_install_image_icon.setImageDrawable(icon);
      RelativeLayout.LayoutParams lp =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(100));
      dialog_wallet_install_image_graphic.setLayoutParams(lp);
      int resourceId = contextApp.getResources()
          .getIdentifier("dialog_wallet_install_empty_image", "drawable", contextApp.getPackageName());
      dialog_wallet_install_image_graphic.setImageDrawable(contextApp.getResources()
          .getDrawable(resourceId));
    }
  }

  private void buildMessage() {
    //R.id.dialog_wallet_install_text_message
    dialog_wallet_install_text_message = findViewById(contextApp.getResources()
        .getIdentifier("dialog_wallet_install_text_message", "id", contextApp.getPackageName()));

    String dialog_message = getContext().getString(R.string.app_wallet_install_wallet_from_iab);

    SpannableStringBuilder messageStylized = new SpannableStringBuilder(dialog_message);

    messageStylized.setSpan(new StyleSpan(BOLD), dialog_message.indexOf("AppCoins"),
        dialog_message.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

    dialog_wallet_install_text_message.setText(messageStylized);
  }

  private void buildDownloadButton() {
    //R.id.dialog_wallet_install_button_download
    dialog_wallet_install_button_download = findViewById(contextApp.getResources()
        .getIdentifier("dialog_wallet_install_button_download", "id", contextApp.getPackageName()));
    dialog_wallet_install_button_download.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        redirectToStore();
        DialogWalletInstall.this.dismiss();
        if (contextApp instanceof InstallDialogActivity) {
          Bundle response = new Bundle();
          response.putInt(Utils.RESPONSE_CODE, RESULT_USER_CANCELED);

          Intent intent = new Intent();
          intent.putExtras(response);

          ((Activity) contextApp).setResult(Activity.RESULT_CANCELED, intent);
          ((Activity) contextApp).finish();
        }
      }
    });
  }

  private void buildCancelButton() {
    //dialog_wallet_install_button_download
    dialog_wallet_install_button_cancel = findViewById(contextApp.getResources()
        .getIdentifier("dialog_wallet_install_button_download", "id", contextApp.getPackageName()));
    dialog_wallet_install_button_cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DialogWalletInstall.this.dismiss();
        if (contextApp instanceof InstallDialogActivity) {
          Bundle response = new Bundle();
          response.putInt(Utils.RESPONSE_CODE, RESULT_USER_CANCELED);

          Intent intent = new Intent();
          intent.putExtras(response);

          ((Activity) contextApp).setResult(Activity.RESULT_CANCELED, intent);
          ((Activity) contextApp).finish();
        }
      }
    });
  }

  private void redirectToStore() {
    //https://developer.android.com/distribute/marketing-tools/linking-to-google-play
    getContext().startActivity(buildStoreViewIntent(URL_APTOIDE));
  }

  private Intent buildStoreViewIntent(String action) {

    final Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(action));
    if (WalletUtils.hasAptoideInstalled()) {
      appStoreIntent.setPackage(BuildConfig.APTOIDE_PACKAGE_NAME);
    }
    return appStoreIntent;
  }

  private int dp(int px) {
    int pixels = (int) (px * getContext().getResources()
        .getDisplayMetrics().density);
    DisplayMetrics displayMetrics = getContext().getResources()
        .getDisplayMetrics();
    return Math.round(pixels / (displayMetrics.xdpi / displayMetrics.densityDpi));
  }
}