package feign.hystrix.custome.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;


/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/15 21:35
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestPage<E> {
  // private Integer total;
  // private Integer pages;
  // private Integer pageNum;
  // private Integer pageSize;
  private List<E> list;
  // private E obj;
}
