package feign.hystrix;

import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Map;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/17 12:39
 */
public class ReflectUtil {

  private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS =
      new ImmutableMap.Builder<Class<?>, Class<?>>()
          .put(boolean.class, Boolean.class)
          .put(byte.class, Byte.class)
          .put(char.class, Character.class)
          .put(double.class, Double.class)
          .put(float.class, Float.class)
          .put(int.class, Integer.class)
          .put(long.class, Long.class)
          .put(short.class, Short.class)
          .put(void.class, Void.class)
          .build();

  @SuppressWarnings("unchecked")
  public static boolean setField(Field field, Object ret) throws Exception {
    Class type = field.getType();
    boolean isPrimitive = false;
    if (type == int.class) {
      field.set(ret, 999);
      isPrimitive = true;
    }
    if (type == double.class) {
      field.set(ret, 99.99);
      isPrimitive = true;
    }
    if (type == boolean.class) {
      field.set(ret, true);
      isPrimitive = true;
    }
    if (type == char.class) {
      field.set(ret, '9');
      isPrimitive = true;
    }
    if (type == long.class) {
      field.set(ret, 999);
      isPrimitive = true;
    }
    if (type == float.class) {
      field.set(ret, 9.99);
      isPrimitive = true;
    }
    if (type == byte.class) {
      field.set(ret, (byte) 9);
      isPrimitive = true;
    }
    if (type == short.class) {
      field.set(ret, (short) 9);
      isPrimitive = true;
    }

    if (type == Integer.class) {
      field.set(ret, 9);
      isPrimitive = true;
    }
    if (type == Double.class) {
      field.set(ret, 999.9);
      isPrimitive = true;

    }
    if (type == Boolean.class) {
      field.set(ret, Boolean.TRUE);
      isPrimitive = true;

    }
    if (type == Character.class) {
      field.set(ret, '0');
      isPrimitive = true;

    }
    if (type == Long.class) {
      field.set(ret, 999L);
      isPrimitive = true;

    }
    if (type == Float.class) {
      field.set(ret, 9.99f);
      isPrimitive = true;

    }
    if (type == Byte.class) {
      field.set(ret, (byte) 9);
      isPrimitive = true;

    }
    if (type == Short.class) {
      field.set(ret, (short) 9);
      isPrimitive = true;

    }

    if (type == String.class) {
      field.set(ret, "mockString");
      isPrimitive = true;

    }

    if (type == LocalDate.class) {
      field.set(ret, LocalDate.now());
      isPrimitive = true;
    }

    return isPrimitive;
  }

  public static Object generateWrapperUsingPrimitive(Type type) {

    if (type == Integer.class) {
      return 9;
    }
    if (type == Double.class) {
      return 999.9;
    }
    if (type == Boolean.class) {
      return Boolean.TRUE;
    }
    if (type == Character.class) {
      return '0';
    }
    if (type == Long.class) {
      return 999L;
    }
    if (type == Float.class) {
      return 9.99f;
    }
    if (type == Byte.class) {
      return (byte) 9;
    }
    if (type == Short.class) {
      return (short) 9;
    }
    if (type == String.class) {
      return "mockString";
    }
    return null;
  }

  public static Object genObj(Type type) {
    if (type == String.class) {
      return "mockString";
    }
    if (type == Integer.class) {
      return 999;
    }
    if (type == Long.class) {
      return 999L;
    }
    return null;
  }


  public static boolean isPrimitive(Type type) {
    if (type instanceof Class) {
      if (((Class) type).isPrimitive()) {
        return true;
      }
    }
    return false;
  }

  public static boolean isPrimitiveWrapper(Class<?> type) {
    if (PRIMITIVES_TO_WRAPPERS.values().contains(type)) {
      return true;
    }
    return false;
  }


}
