package tests;

import com.appcoins.net.AppCoinsClient;
import com.appcoins.net.AppCoinsClientResponse;
import com.appcoins.net.AppCoinsClientResponsePing;
import com.appcoins.net.AppCoinsHTTPClient;
import com.appcoins.net.ClientResponseHandler;
import com.appcoins.net.GetCampaignOperation;
import com.appcoins.net.Interceptor;
import com.appcoins.net.QueryParams;

public class AppCoinsClientTest extends AppCoinsClient {
  public AppCoinsClientTest(String packageName, int versionCode, String serviceUrl,
      Interceptor interceptor) {
    super(packageName, versionCode, serviceUrl, interceptor);
  }

  @Override
  public AppCoinsHTTPClient createAppcoinsHttpClient(ClientResponseHandler clientResponseHandler,
      QueryParams queryParams) {
    GetCampaignOperation getCampaignOperation = new GetCampaignOperation();
    return new AppCoinsHTTPClientTest("", new Interceptor() {
      @Override public void OnInterceptPublish(String log) {

      }
    }, getCampaignOperation.mapParams("", Integer.toString(1), queryParams), response -> {

      AppCoinsClientResponse appcoinsClientResponse =
          getCampaignOperation.mapResponse((String) response);
      clientResponseHandler.clientResponseHandler(appcoinsClientResponse);
    });
  }

  @Override
  public AppCoinsHTTPClient createAppCoinsPingClient(ClientResponseHandler clientResponseHandler) {
    return new AppCoinsPingClientTest("" + "", new Interceptor() {
      @Override public void OnInterceptPublish(String log) {

      }
    }, response -> {

      AppCoinsClientResponse appcoinsClientResponse =
          new AppCoinsClientResponsePing((boolean) response);
      clientResponseHandler.clientResponseHandler(appcoinsClientResponse);
    });
  }
}
