package com.asf.appcoins.sdk.ads.repository;

import android.os.IBinder;

public interface ConnectionLifeCycle {
  void onConnect(IBinder service, final AppcoinsAdvertisementListener listener);

  void onDisconnect(final AppcoinsAdvertisementListener listener);
}
