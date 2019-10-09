package com.appcoins.sdk.billing;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.appcoins.sdk.billing.helpers.EventLogger;
import com.appcoins.sdk.billing.helpers.PayloadHelper;
import java.util.List;

public class CatapultAppcoinsBilling implements AppcoinsBillingClient {

  private final Billing billing;
  private final RepositoryConnection connection;

  public CatapultAppcoinsBilling(Billing billing, RepositoryConnection connection) {
    this.billing = billing;
    this.connection = connection;
  }

  @Override public PurchasesResult queryPurchases(String skuType) {
    return billing.queryPurchases(skuType);
  }

  @Override public void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams,
      SkuDetailsResponseListener onSkuDetailsResponseListener) {
    billing.querySkuDetailsAsync(skuDetailsParams, onSkuDetailsResponseListener);
  }

  @Override
  public void consumeAsync(String token, ConsumeResponseListener consumeResponseListener) {
    billing.consumeAsync(token, consumeResponseListener);
  }

  @Override public int launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams,
      PackageManager packageManager) {

    int responseCode;
    try {
      String payload = PayloadHelper.buildIntentPayload(billingFlowParams.getOrderReference(),
          billingFlowParams.getDeveloperPayload(), billingFlowParams.getOrigin());

      Log.d("Message: ", payload);

      Thread eventLoggerThread = new Thread(new EventLogger(billingFlowParams.getSku(),
          activity.getApplicationContext()
              .getPackageName()));
      eventLoggerThread.start();

      LaunchBillingFlowResult launchBillingFlowResult =
          billing.launchBillingFlow(billingFlowParams, payload);

      responseCode = (int) launchBillingFlowResult.getResponseCode();

      if (responseCode != ResponseCode.OK.getValue()) {
        return responseCode;
      }

      Intent intent = buildTargetIntent(launchBillingFlowResult, packageManager);
      activity.startActivity(intent);
    } catch (NullPointerException e) {
      return ResponseCode.ERROR.getValue();
    } catch (ServiceConnectionException e) {
      return ResponseCode.SERVICE_UNAVAILABLE.getValue();
    }
    return ResponseCode.OK.getValue();
  }

  private Intent buildTargetIntent(LaunchBillingFlowResult launchBillingFlowResult,
      PackageManager packageManager) {
    Intent intent = (Intent) launchBillingFlowResult.getBuyIntent();
    List<ResolveInfo> list =
        packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    String walletPackageName = "com.appcoins.wallet";
    String aptoidePackageName = "cm.aptoide.dev";
    if (BuildConfig.BUILD_TYPE.equals("debug")) {
      walletPackageName += ".dev";
      aptoidePackageName += ".dev";
    }
    for (ResolveInfo app : list) {
      if (app.activityInfo.packageName.equals(aptoidePackageName)) {
        //If there's aptoide installed always choose Aptoide as default to open url
        intent.setPackage(app.activityInfo.packageName);
        break;
      } else if (app.activityInfo.packageName.equals(walletPackageName)) {
        //If Aptoide is not installed and wallet is installed then choose Wallet as default to
        // open url
        intent.setPackage(app.activityInfo.packageName);
      }
    }
    return intent;
  }

  @Override public void startConnection(final AppCoinsBillingStateListener listener) {
    if (!isReady()) {
      connection.startConnection(listener);
    }
  }

  @Override public void endConnection() {
    if (isReady()) {
      connection.endConnection();
    }
  }

  @Override public boolean isReady() {
    return billing.isReady();
  }
}



