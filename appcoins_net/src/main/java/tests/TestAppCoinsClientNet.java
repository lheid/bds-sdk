package tests;

import com.appcoins.net.AppCoinsClient;
import com.appcoins.net.AppCoinsClientResponse;
import com.appcoins.net.AppCoinsClientResponsePing;
import com.appcoins.net.ClientResponseHandler;
import com.appcoins.net.Interceptor;
import com.appcoins.net.QueryParams;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAppCoinsClientNet {

  @Test
  public void testGetCampaign(){

    AppCoinsClientTest appCoinsClient = new AppCoinsClientTest("", 1, "" + "", new Interceptor() {
      @Override public void OnInterceptPublish(String log) {

      }
    });

    QueryParams queryParams = new QueryParams("aaaa","bbbb","cccc","ddd");
    appCoinsClient.getCampaign(queryParams, new ClientResponseHandler() {
      @Override public void clientResponseHandler(AppCoinsClientResponse appcoinsClientResponse) {
       assertEquals(appcoinsClientResponse.getMsg(),"Response");
      }
    });
  }

  @Test
  public void testCheckConectivity(){
    AppCoinsClientTest appCoinsClient = new AppCoinsClientTest("", 1, "" + "", new Interceptor() {
      @Override public void OnInterceptPublish(String log) {

      }
    });

    appCoinsClient.checkConnectivity(new ClientResponseHandler() {
      @Override public void clientResponseHandler(AppCoinsClientResponse appcoinsClientResponse) {
        assertEquals(((AppCoinsClientResponsePing) appcoinsClientResponse).HasConnection(),true);
      }
    });
  }
}
