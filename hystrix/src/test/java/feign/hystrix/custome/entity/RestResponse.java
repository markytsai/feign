package feign.hystrix.custome.entity;

import lombok.Getter;
import lombok.ToString;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:16
 */
@Getter
@ToString
public class RestResponse<T> {
  private int code;
  private String msg;
  private T data;

  public RestResponse() {
    this.code = 200;
  }

  public RestResponse(T data) {
    this.code = 200;
    this.msg = "success";
    this.data = data;
  }

  public RestResponse(int code, T data) {
    this.code = code;
    this.data = data;
  }

  public RestResponse(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
