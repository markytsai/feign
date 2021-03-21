package feign.hystrix;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/17 12:39
 */
public class ReflectUtil {

  private final static String CODE = "code";
  private final static String MSG = "msg";

  public static boolean setField(Field field, Object ret) throws Exception {
    Class type = field.getType();
    boolean isPrimitive = false;
    if (type == int.class) {
      if (CODE.equals(field.getName())) {
        field.set(ret, 200);
      } else {
        field.set(ret, 999);
      }
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
      if (MSG.equals(field.getName())) {
        field.set(ret, "success");
      } else {
        field.set(ret, "mockString");
      }
      isPrimitive = true;

    }

    if (type == Date.class) {
      field.set(ret, new Date());
      isPrimitive = true;
    }

    if (type == LocalDate.class) {
      field.set(ret, LocalDate.now());
      isPrimitive = true;
    }

    if (type == LocalDateTime.class) {
      field.set(ret, LocalDateTime.now());
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
    if (type == Date.class) {
      return new Date();
    }
    if (type == LocalDate.class) {
      return LocalDate.now();
    }

    if (type == LocalDateTime.class) {
      return LocalDateTime.now();
    }

    return null;
  }
}
