package feign.hystrix.custome.entity;

import feign.RequestLine;
import feign.hystrix.Mock;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/19 16:59
 */
public interface RiskMicroClient {

  // @Mock
  @RequestLine("GET /test")
  RestResponse<RestPage<ComplaintHandlerMicroResponse>> queryComplaintHandlerList();

}
