package feign.hystrix.custome.entity;

import lombok.Data;

import java.util.List;


@Data
public class ComplaintHandlerMicroResponse {


  private Integer id;
  /**
   * 工单号(处置id)
   */
  private String workNo;

  private Long providerNo;

  private List<String> providerNames;

  private List<UserAppId> userAppIds;


}
