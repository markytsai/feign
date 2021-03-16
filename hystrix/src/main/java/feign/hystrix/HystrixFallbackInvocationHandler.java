package feign.hystrix;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

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
    return mockReturner.getGenericReturnType(method);
  }

  private Object mockObject(Method method) throws Exception {
    Class<?> returnType = method.getReturnType();
    Object ret = returnType.newInstance();
    Field[] fields = returnType.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      genPrimitiveValue(field, ret);
    }
    return ret;
  }

  private boolean isGenericField(Method method) {
    Type returnType = method.getGenericReturnType();
    if (returnType instanceof ParameterizedType) {
      return true;
    }
    return false;
  }

  private void genPrimitiveValue(Field field, Object ret) throws Exception {

    Type type = field.getType();
    if (type == int.class) {
      field.set(ret, 1);
      return;
    }
    if (type == double.class) {
      field.set(ret, 1.0);
      return;
    }
    if (type == boolean.class) {
      field.set(ret, true);
      return;
    }
    if (type == char.class) {
      field.set(ret, '0');
      return;
    }
    if (type == long.class) {
      field.set(ret, 1L);
      return;
    }
    if (type == float.class) {
      field.set(ret, 1f);
      return;
    }
    if (type == byte.class) {
      field.set(ret, (byte) 1);
      return;
    }
    if (type == short.class) {
      field.set(ret, (short) 1);
      return;
    }

    if (type == Integer.class) {
      field.set(ret, 1);
      return;
    }
    if (type == Double.class) {
      field.set(ret, 1.0);
      return;
    }
    if (type == Boolean.class) {
      field.set(ret, true);
      return;
    }
    if (type == Character.class) {
      field.set(ret, '0');
      return;
    }
    if (type == Long.class) {
      field.set(ret, 1L);
      return;
    }
    if (type == Float.class) {
      field.set(ret, 1f);
      return;
    }
    if (type == Byte.class) {
      field.set(ret, 1);
      return;
    }
    if (type == Short.class) {
      field.set(ret, 1);
      return;
    }


    if (type == String.class) {
      field.set(ret, MOCK_STRING);
      return;
    }

    if (type == LocalDate.class) {
      field.set(ret, LocalDate.now());
      return;
    }

    if (type == List.class) {
      type.getClass().getGenericInterfaces();

    }

    Object obj = ((Class) type).newInstance();
    for (Field declaredField : obj.getClass().getDeclaredFields()) {
      declaredField.setAccessible(true);
      genPrimitiveValue(declaredField, obj);
    }
    field.set(ret, obj);
    return;
  }
}
