package feign.hystrix.custome;


import com.google.gson.Gson;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.hystrix.HystrixFeign;
import feign.hystrix.custome.entity.Contributor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/13 11:35
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
//                .setBodyDelay(1000, TimeUnit.SECONDS)
                .setBody(new Gson().toJson(successfulContributor)));
        server.enqueue(new MockResponse().setResponseCode(100)
//                .setBodyDelay(1000, TimeUnit.SECONDS)
                .setBody(new Gson().toJson(successfulContributor)));
        server.enqueue(new MockResponse().setResponseCode(100)
//                .setBodyDelay(1000, TimeUnit.SECONDS)
                .setBody(new Gson().toJson(successfulContributor)));


        ContributorApi fallback = new ContributorApi() {
            @Override
            public Contributor singleContributor(String owner, String repo) {
                Contributor fallbackContributor = new Contributor();
                fallbackContributor.setName("fallbackWorker");
                return fallbackContributor;
            }

            @Override
            public Contributor singleContributor() {
                Contributor fallbackContributor = new Contributor();
                fallbackContributor.setName("fallbackWorker");
                return fallbackContributor;
            }
        };

        final ContributorApi api = target(ContributorApi.class, "http://localhost:" + server.getPort(), fallback);
        Contributor c = api.singleContributor("Netflix", "feign");
        Contributor c1 = api.singleContributor("Netflix", "feign");
        Contributor c2 = api.singleContributor();
        System.out.println(c);
        System.out.println(c1);
        System.out.println(c2);
    }

    protected <E> E target(Class<E> api, String url, E fallback) {
        return HystrixFeign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(api, url, fallback);
    }

}
