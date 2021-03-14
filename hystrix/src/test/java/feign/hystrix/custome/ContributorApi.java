package feign.hystrix.custome;

import feign.hystrix.Mock;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.custome.entity.Contributor;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/13 13:38
 */
interface ContributorApi {

    @Mock
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    Contributor singleContributor(@Param("owner") String owner, @Param("repo") String repo);

    @RequestLine("GET /all")
    Contributor singleContributor();
}