package tests;

import com.appcoins.net.AppCoinsPingClient;
import com.appcoins.net.GetResponseHandler;
import com.appcoins.net.Interceptor;

public class AppCoinsPingClientTest extends AppCoinsPingClient {
  public AppCoinsPingClientTest(String serviceUrl, Interceptor interceptor,
      GetResponseHandler getResponseHandler) {
    super(serviceUrl, interceptor, getResponseHandler);
  }

  @Override public boolean pingServers(){
    return true;
  }

}
