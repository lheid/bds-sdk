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
  private static final String TAG = SecurityUtils.class.getName();
  // point a string obfuscator tool - like DexGuard has - to here
  private static final String APP_SIGNATURE = "UeCPXCfLgdNf8NJH6fiO2mdqpR4=";

  public static int checkAppSignature(Context context, String packageName) {
    try {

      final MessageDigest md = MessageDigest.getInstance("SHA");
      final String currentSignature = Base64.encodeToString(md.digest(), Base64.NO_WRAP);

      //check app signature for API 28 only because the GET_SIGNATURES method
      // is deprecated fo this version and must be replaced by GET_SIGNING_CERTIFICATES
      if (Build.VERSION.SDK_INT >= 28) {

        final PackageInfo packageInfo = context.getPackageManager()
            .getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES);

        final Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();

        for (Signature signature : signatures) {
          md.update(signature.toByteArray());
        }
      } else {

        //check app signature for API's under 28
        final PackageInfo packageInfo = context.getPackageManager()
            .getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

        for (Signature signature : packageInfo.signatures) {
          md.update(signature.toByteArray());
        }
      }

      Log.d(TAG,
          String.format("Include this string as a value for SIGNATURE: %s", currentSignature));

      //compare signatures
      if (compareSignatures(APP_SIGNATURE, currentSignature)) {
        return VALID_APP_SIGNATURE;
      }
    } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    Log.d(TAG, "INVALID_APP_SIGNATURE");
    return INVALID_APP_SIGNATURE;
  }

  public static boolean compareSignatures(String sig1, String sig2) {
    if (TextUtils.equals(sig1, sig2)) {
      Log.d(TAG, "VALID_APP_SIGNATURE");
      return true;
    }
    return false;
  }
}

