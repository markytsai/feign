package feign.hystrix;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:12
 */
public class HystrixFallbackInvocationHandler implements InvocationHandler {

  private Object api;
  private Class<?> type;
  private static MockReturner mockReturner = new MockReturner();

  public static final String MOCK_STRING = "mockWorker";

  public HystrixFallbackInvocationHandler() {}

  public <T> T proxy() {
    return (T) Proxy.newProxyInstance(type.getClassLoader(),
        type.getInterfaces(), this);
  }

  public HystrixFallbackInvocationHandler(Object api) {
    this.api = api;
    this.type = api.getClass();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // return mockObject(method);
    return mockReturner.mockResponse(method);
  }
}
