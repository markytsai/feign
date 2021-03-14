package feign.hystrix.custome.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/13 13:37
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Contributor {
    private String name;
    private Integer age;
    private Boolean isRecruited;
    private Certificate certificate;
    private List<String> titles = Arrays.asList("主管", "组长");
    private List<Certificate> certificates;









}