package tests;

import com.appcoins.net.AppCoinsHTTPClient;
import com.appcoins.net.GetResponseHandler;
import com.appcoins.net.Interceptor;
import java.io.IOException;

public class AppCoinsHTTPClientTest extends AppCoinsHTTPClient {
  public AppCoinsHTTPClientTest(String serviceUrl, Interceptor interceptor, String params,
      GetResponseHandler getResponseHandler) {
    super(serviceUrl, interceptor, params, getResponseHandler);
  }

  @Override public String getCampaign() throws IOException {
    return "Response";
  }



}
