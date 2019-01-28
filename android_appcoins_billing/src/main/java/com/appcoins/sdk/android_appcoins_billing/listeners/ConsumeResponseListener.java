package com.appcoins.sdk.android_appcoins_billing.listeners;

import com.appcoins.sdk.billing.ResponseListener;

public interface ConsumeResponseListener extends ResponseListener {

    void onConsumeResponse(int responseCode, String purchaseToken);

}
