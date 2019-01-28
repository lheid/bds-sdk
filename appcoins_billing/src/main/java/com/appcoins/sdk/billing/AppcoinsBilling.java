package com.appcoins.sdk.billing;

import java.util.List;

public interface AppcoinsBilling {

    PurchasesResult queryPurchases(String skuType);

    void querySkuDetailsAsync(SkuDetailsParam skuDetailsParam , ResponseListener onSkuDetailsResponseListener);

    void launchBillingFlow(Object act,String sku, String itemType, List<String> oldSkus, int requestCode, ResponseListener listener, String extraData);

    void consumeAsync(String purchaseToken,ResponseListener listener);
}

