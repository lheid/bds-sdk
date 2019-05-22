package com.appcoins.sdk.billing;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import com.appcoins.sdk.billing.helpers.PayloadHelper;

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

  @Override public int launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams) {

    int responseCode;
    try {
      String payload = PayloadHelper.buildIntentPayload(billingFlowParams.getOrderReference(),
          billingFlowParams.getDeveloperPayload(), billingFlowParams.getOrigin());

      Log.d("Message: ", payload);

      LaunchBillingFlowResult launchBillingFlowResult =
          billing.launchBillingFlow(billingFlowParams, payload);

      responseCode = (int) launchBillingFlowResult.getResponseCode();

      if (responseCode != ResponseCode.OK.getValue()
          || !launchBillingFlowResult.isHasWalletInstalled()) {
        return responseCode;
      }

      PendingIntent pendingIntent = (PendingIntent) launchBillingFlowResult.getBuyIntent();
      activity.startIntentSenderForResult(pendingIntent.getIntentSender(),
          billingFlowParams.getRequestCode(), new Intent(), 0, 0, 0);
    } catch (NullPointerException e) {
      return ResponseCode.ERROR.getValue();
    } catch (IntentSender.SendIntentException e) {
      return ResponseCode.ERROR.getValue();
    } catch (ServiceConnectionException e) {
      return ResponseCode.SERVICE_UNAVAILABLE.getValue();
    }
    return ResponseCode.OK.getValue();
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


