package feign.hystrix.custome;

import com.google.gson.Gson;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.hystrix.HystrixFeign;
import feign.hystrix.custome.entity.Contributor;
import feign.hystrix.custome.entity.RestResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:15
 */
public class HystrixCustomTest {

  @Rule
  public final ExpectedException thrown = ExpectedException.none();
  @Rule
  public final MockWebServer server = new MockWebServer();

  final String fallbackStr = "fallbackWorker";

  @Test
  public void costumedFallbackTest() throws Exception {
    Contributor successfulContributor = new Contributor();
    server.enqueue(new MockResponse().setResponseCode(100)
        // .setBodyDelay(1000, TimeUnit.SECONDS)
        .setBody(new Gson().toJson(successfulContributor)));
    server.enqueue(new MockResponse().setResponseCode(100)
        // .setBodyDelay(1000, TimeUnit.SECONDS)
        .setBody(new Gson().toJson(successfulContributor)));
    server.enqueue(new MockResponse().setResponseCode(100)
        // .setBodyDelay(1000, TimeUnit.SECONDS)
        .setBody(new Gson().toJson(successfulContributor)));


    ContributorApi fallback = new ContributorApi() {
      @Override
      public RestResponse<Contributor> singleContributor(String owner, String repo) {
        Contributor fallbackContributor = new Contributor();
        fallbackContributor.setName("fallbackWorker");
        return new RestResponse<>(fallbackContributor);
      }

      @Override
      public Contributor singleContributor() {
        Contributor fallbackContributor = new Contributor();
        fallbackContributor.setName("fallbackWorker");
        return fallbackContributor;
      }
    };

    final ContributorApi api =
        target(ContributorApi.class, "http://localhost:" + server.getPort(), fallback);
    RestResponse<?> c = api.singleContributor("Netflix", "feign");
    System.out.println(c);
  }

  protected <E> E target(Class<E> api, String url, E fallback) {
    return HystrixFeign.builder()
        .decoder(new GsonDecoder())
        .encoder(new GsonEncoder())
        .target(api, url, fallback);
  }

}
