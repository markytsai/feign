package feign.hystrix.custome.entity;

import feign.RequestLine;
import feign.hystrix.Mapping;
import feign.hystrix.Mock;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/19 16:59
 */
public interface RiskMicroClient {

  @Mock(
          mappings = {
                  @Mapping(type = RestResponse.class, name = "code", value = "200"),
                  @Mapping(type = RestResponse.class, name = "msg", value = "success")
          }
  )
  @RequestLine("GET /test")
  RestResponse<RestPage<ComplaintHandlerMicroResponse>> queryComplaintHandlerList();

}
