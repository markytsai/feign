package feign.hystrix.custome.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yeqi
 * @date 2020/7/8 13:24:55 用户-内部流转appId权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAppId {

  /**
   * 自定义(内部流转)appId
   */
  private String appId;

  /**
   * 品牌编号
   */
  private String brandNo;

  /**
   * 流量商编号
   */
  private String providerNo;

}
