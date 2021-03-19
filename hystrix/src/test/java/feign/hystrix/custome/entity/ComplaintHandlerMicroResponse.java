package feign.hystrix.custome.entity;

import lombok.Data;


@Data
public class ComplaintHandlerMicroResponse {


  private Integer id;
  /**
   * 工单号(处置id)
   */
  private String workNo;

  private String providerNo;

  private String providerName;

  /**
   * 客诉类型(工单分类)
   */
  private String workOrderType;
  /**
   * 客诉类型描述
   */
  private String workOrderTypeShow;

  private String cityCode;

  private String cityName;

  /**
   * 品牌编号
   */
  private String brandNo;

  private String brandName;

  /**
   * 司机姓名
   */
  private String driverName;

  private String driverPhone;

  /**
   * 司机编号
   */
  private String driverNo;

  /**
   * 订单编号
   */
  private String orderNo;

  private Integer payState;

  private String payStateShow;

  private Integer needReparation;

  private String needReparationShow;

  private Integer needCallback;

  private String needCallbackShow;

  private String workOrderReason;

  private String handleContext;

  private String handlePerson;

  private String handleState;

  private String handleStateShow;

  private String handleTime;

  private String handleReason;

  private Integer lastOperatorId;

  private String lastOperatorName;

  private String createPerson;

  private String bizDate;

  private String lastOperatorTime;

  private String cancelDetails;

  private String cancelReasons;

  private String cancelPersons;
}
