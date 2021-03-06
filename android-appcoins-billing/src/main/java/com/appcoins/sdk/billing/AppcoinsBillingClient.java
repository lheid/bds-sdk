package com.appcoins.sdk.billing;

import android.app.Activity;
import android.content.Intent;

public interface AppcoinsBillingClient {
  PurchasesResult queryPurchases(String skuType);

  void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams,
      SkuDetailsResponseListener onSkuDetailsResponseListener);

  void consumeAsync(String token, ConsumeResponseListener consumeResponseListener);

  int launchBillingFlow(Activity activity, BillingFlowParams billingFlowParams);

  void startConnection(AppCoinsBillingStateListener listener);

  void endConnection();

  boolean isReady();

  boolean onActivityResult(int requestCode, int resultCode, Intent data);
}