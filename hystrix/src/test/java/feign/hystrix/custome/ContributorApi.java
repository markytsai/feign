package feign.hystrix.custome;

import feign.Param;
import feign.RequestLine;
import feign.hystrix.Mock;
import feign.hystrix.custome.entity.Contributor;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:15
 */
interface ContributorApi {

  @Mock
  @RequestLine("GET /repos/{owner}/{repo}/contributors")
  Contributor singleContributor(@Param("owner") String owner, @Param("repo") String repo);

  @RequestLine("GET /all")
  Contributor singleContributor();
}
