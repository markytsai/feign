package feign.hystrix.custome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.hystrix.HystrixFeign;
import feign.hystrix.custome.entity.ComplaintHandlerMicroResponse;
import feign.hystrix.custome.entity.Contributor;
import feign.hystrix.custome.entity.RestPage;
import feign.hystrix.custome.entity.RestResponse;
import feign.hystrix.custome.entity.RiskMicroClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:15
 */
public class MockFeignTest {

    @Rule
    public final MockWebServer server = new MockWebServer();

    @Test
    public void costumedFallbackTest() {
        Contributor successfulContributor = new Contributor();
        server.enqueue(
                new MockResponse().setResponseCode(100).setBody(new Gson().toJson(successfulContributor)));
        server.enqueue(
                new MockResponse().setResponseCode(100).setBody(new Gson().toJson(successfulContributor)));
        server.enqueue(
                new MockResponse().setResponseCode(100).setBody(new Gson().toJson(successfulContributor)));

        RiskMicroClient riskFallback = () -> {
            List<ComplaintHandlerMicroResponse> responses =
                    Arrays.asList(new ComplaintHandlerMicroResponse());
            RestPage restPage = new RestPage(responses);
            return new RestResponse<>("this is fallback", restPage);
        };
        RiskMicroClient client =
                target(RiskMicroClient.class, "http://localhost:" + server.getPort(), riskFallback);
        RestResponse<RestPage<ComplaintHandlerMicroResponse>> restPageRestResponse =
                client.queryComplaintHandlerList();
        Gson niceGson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(niceGson.toJson(restPageRestResponse));
    }

    protected <E> E target(Class<E> api, String url, E fallback) {
        return HystrixFeign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(api, url, fallback);
    }

}
