package feign.hystrix.custome.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:16
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Certificate {
  private LocalDate awardDate;
  private Committee committee;

  @NoArgsConstructor
  @AllArgsConstructor
  @ToString
  public static class Committee {
    private String name;
    private Integer memberNum;
  }
}
