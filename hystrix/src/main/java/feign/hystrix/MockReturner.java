package feign.hystrix;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static feign.hystrix.ReflectUtil.*;

/**
 * @description:
 * @author: caizhenya
 * @email: caizhenya@shandiantech.com
 * @date: 2021/3/16 20:11
 */

public class MockReturner {

  public Object mockResponse(Method method) throws Exception {
    Type returnType = method.getGenericReturnType();
    Object response = null;
    if (returnType instanceof ParameterizedType) {
      Type rawType = ((ParameterizedType) returnType).getRawType();
      if (rawType instanceof Class) {
        response = ((Class) rawType).newInstance();
        Type argument = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        recursive(response, returnType, argument);
      }
    }
    if (returnType instanceof Class) {
      response = ((Class<?>) returnType).newInstance();
      recursive(response, returnType, null);
    }
    return response;
  }

  public void recursive(Object target, Type targetType, Type argument) throws Exception {

    Field[] declaredFields = target.getClass().getDeclaredFields();
    for (Field declaredField : declaredFields) {
      declaredField.setAccessible(true);
      Type fieldGenericType = declaredField.getGenericType();

      if (fieldGenericType != Collection.class && setField(declaredField, target)) {
        continue;
      }

      if (fieldGenericType instanceof Class) {
        Object obj = ((Class<?>) fieldGenericType).newInstance();
        declaredField.set(target, obj);
        recursive(obj, declaredField.getGenericType(), null);
      }

      // List<T> List<String> List<Object> List<List<T>> RestPage<T>
      if (fieldGenericType instanceof ParameterizedType) {
        Type fieldRawType = ((ParameterizedType) fieldGenericType).getRawType();
        List mockList = new ArrayList();

        // List<T> List<String> List<Object> List<List<T>>
        if (fieldRawType == List.class) {
          Type actualTypeArgument =
              ((ParameterizedType) fieldGenericType).getActualTypeArguments()[0];
          Object listObj;

          // List<Integer> List<String>
          listObj = genObj(actualTypeArgument);
          if (listObj != null) {
            mockList.add(listObj);
            declaredField.set(target, mockList);
            continue;
          }

          // List<List<String>>
          if (actualTypeArgument instanceof ParameterizedType) {
            Type actualTypeArgument1 =
                ((ParameterizedTypeImpl) fieldGenericType).getActualTypeArguments()[0];
            listObj = new ArrayList<>();
            recursive(listObj, fieldGenericType, actualTypeArgument1);
            mockList.add(listObj);
            declaredField.set(target, mockList);
          }

          // List<T>
          if (actualTypeArgument instanceof TypeVariable) {
            if (argument instanceof Class) {
              listObj = generateWrapperUsingPrimitive(argument);
              if (listObj == null) {
                listObj = ((Class<?>) argument).newInstance();
                mockList.add(listObj);
                recursive(listObj, argument, null);

              }
              mockList.add(listObj);
              declaredField.set(target, mockList);
              continue;
            }
          }

          if (actualTypeArgument instanceof Class) {
            listObj = ((Class) actualTypeArgument).newInstance();
            mockList.add(listObj);
            recursive(listObj, actualTypeArgument, null);
          }
        }
        declaredField.set(target, mockList);
      }

      if (fieldGenericType instanceof TypeVariable) {
        Object obj;
        Type varType = ((ParameterizedType) targetType).getActualTypeArguments()[0];
        if (varType instanceof ParameterizedType) {
          Type rawType = ((ParameterizedType) varType).getRawType();

          // start
          List mockList = new ArrayList();
          if (rawType == List.class) {
            Type actualTypeArgument =
                ((ParameterizedTypeImpl) argument).getActualTypeArguments()[0];
            Object listObj;

            // List<Integer> List<String>
            listObj = genObj(actualTypeArgument);
            if (listObj != null) {
              mockList.add(listObj);
              declaredField.set(target, mockList);
              continue;
            }

            // List<List<String>>
            if (actualTypeArgument instanceof ParameterizedType) {
              Type actualTypeArgument1 =
                  ((ParameterizedTypeImpl) fieldGenericType).getActualTypeArguments()[0];
              listObj = new ArrayList<>();
              recursive(listObj, fieldGenericType, actualTypeArgument1);
              mockList.add(listObj);
              declaredField.set(target, mockList);
            }

            // List<T>
            if (actualTypeArgument instanceof TypeVariable) {
              if (argument instanceof Class) {
                listObj = generateWrapperUsingPrimitive(argument);
                if (listObj == null) {
                  listObj = ((Class<?>) argument).newInstance();
                  mockList.add(listObj);
                  recursive(listObj, argument, null);

                }
                mockList.add(listObj);
                declaredField.set(target, mockList);
                continue;
              }
            }

            if (actualTypeArgument instanceof Class) {
              listObj = ((Class) actualTypeArgument).newInstance();
              mockList.add(listObj);
              recursive(listObj, actualTypeArgument, null);
            }
            declaredField.set(target, mockList);
            continue;
          }

          // end

          if (rawType instanceof Class) {
            obj = ((Class) rawType).newInstance();
            declaredField.set(target, obj);
            recursive(obj, varType, ((ParameterizedTypeImpl) argument).getActualTypeArguments()[0]);
          }
        }
        if (varType instanceof Class) {
          obj = generateWrapperUsingPrimitive(argument);
          if (obj == null) {
            obj = ((Class<?>) varType).newInstance();
            declaredField.set(target, obj);
            recursive(obj, targetType, null);
          }
          declaredField.set(target, obj);
        }
      }
    }
  }
}
