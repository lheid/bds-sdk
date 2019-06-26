package com.appcoins.sdk.billing.helpers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

  public static final int VALID_APP_SIGNATURE = 0;
  public static final int INVALID_APP_SIGNATURE = 1;
  public static final String APTOIDE_STORE_APP_ID = "cm.aptoide.pt";
  public static final String PLAY_STORE_APP_ID = "com.android.vending";
  public static final String AMAZON_STORE_APP_ID = "com.amazon.mShop.android";
  public static final String XIAOMI_STORE_APP_ID = "com.xiaomi.market";
  private static final String TAG = SecurityUtils.class.getName();
  // point a string obfuscator tool - like DexGuard has - to here
  private static final String APP_SIGNATURE = "1BI0Pl1n8Go3MfT0TSKIddaGZbQ=";

  public static int checkAppSignature(Context context) {
    try {

      //check app signature for API 28 only because the GET_SIGNATURES method
      // is deprecated fo this version and must be replaced by GET_SIGNING_CERTIFICATES
      if (Build.VERSION.SDK_INT >= 28) {

        final PackageInfo packageInfo = context.getPackageManager()
            .getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);

        final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();

        final MessageDigest md = MessageDigest.getInstance("SHA");
        for (Signature signature : signatures) {
          md.update(signature.toByteArray());
          final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

          Log.d(TAG,
              String.format("Include this string as a value for SIGNATURE: %s", currentSignature));

          //compare signatures

          if (TextUtils.equals(APP_SIGNATURE, currentSignature)) {
            Log.d(TAG, "VALID_APP_SIGNATURE");
            return VALID_APP_SIGNATURE;
          }
        }
      }
    } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    Log.d(TAG, "INVALID_APP_SIGNATURE");
    return INVALID_APP_SIGNATURE;
  }
}
