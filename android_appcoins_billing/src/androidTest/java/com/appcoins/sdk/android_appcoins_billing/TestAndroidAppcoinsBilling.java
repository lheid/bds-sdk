package com.appcoins.sdk.android_appcoins_billing;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.appcoins.sdk.android_appcoins_billing.helpers.IabHelper;
import com.appcoins.sdk.android_appcoins_billing.helpers.Utils;
import com.appcoins.sdk.android_appcoins_billing.service.WalletBillingService;
import com.appcoins.sdk.android_appcoins_billing.types.IabResult;

public class TestAndroidAppcoinsBilling {
/*
    public void setUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        WalletBillingServiceTest wbs = new WalletBillingServiceTest(appContext,);
        IabhelperTest iabt = new IabhelperTest();
        iabt.setWalletConnection(wbs);

    }

}

public class WalletBillingServiceTest extends WalletBillingService {

    public WalletBillingServiceTest(IBinder service) {
        super(service);
    }

    @Override
    public Bundle getSkuDetails(int apiVersion, String packageName, String type, Bundle skusBundle) throws RemoteException {
        Bundle b = new Bundle();
        b.putStringArrayList(Utils.RESPONSE_CODE,null);
        b.putStringArrayList(Utils.RESPONSE_GET_SKU_DETAILS_LIST,null);
    }

}

public class IabhelperTest extends IabHelper {

    public IabhelperTest(Context ctx, String base64PublicKey) {
        super(ctx, base64PublicKey);
    }

    public void setWalletConnection(WalletBillingServiceTest wbs){
        super.mService = wbs;
    }
*/
}