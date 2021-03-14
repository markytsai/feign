package feign.hystrix;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockReturner {

    private final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS
            = new ImmutableMap.Builder<Class<?>, Class<?>>()
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


    public Object getGenericReturnType(Method method) throws Exception {
        Type returnType = method.getGenericReturnType();
        Object obj = null;
        // 是泛型
        if (returnType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) returnType;
            Class<?> rawType = (Class<?>) pType.getRawType();
            Type[] actualTypeArguments = pType.getActualTypeArguments();
            obj = rawType.newInstance();
            recursive(obj, actualTypeArguments, false);
            // 普通类型
        } else if (returnType instanceof Class) {
            obj = ((Class<?>) returnType).newInstance();
            recursive(obj, null, false);
        }
        return obj;
    }

    private void recursive(Object target, Type[] actualTypeArguments, boolean stop) throws Exception {

        if (stop) {
            return;
        }
        Type actualTypeArgument = null;
        if (actualTypeArguments != null) {
            actualTypeArgument = actualTypeArguments[0];
        }
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Type fieldType = field.getGenericType();

            if (setField(field.getType(), field, target)) {
                continue;
            }

            // 自定义泛型
            if (field.isAnnotationPresent(Generic.class)) {
                Object fieldObj = null;

                // 自定义泛型中包含泛型
                if (actualTypeArgument instanceof ParameterizedType) {
                    ParameterizedType pType = (ParameterizedType) actualTypeArgument;
                    Class<?> rawType = (Class<?>) pType.getRawType();
                    Type[] innerActualTypeArguments = pType.getActualTypeArguments();
                    if (rawType == List.class) {
                        List mockList = new ArrayList();
                        Object listObj = null;
                        if (isPrimitiveWrapper((Class<?>) innerActualTypeArguments[0])) {
                            listObj = generateWrapperUsingPrimitive(innerActualTypeArguments[0]);
                        } else {
                            listObj = ((Class) innerActualTypeArguments[0]).newInstance();
                        }
                        mockList.add(listObj);
                        if (!isPrimitiveWrapper((Class<?>) innerActualTypeArguments[0]) && innerActualTypeArguments[0] != String.class) {
                            recursive(listObj, innerActualTypeArguments, false);
                        }
                        field.set(target, mockList);
                        continue;
                    }
                    fieldObj = rawType.newInstance();
                    recursive(fieldObj, innerActualTypeArguments, false);
                    field.set(target, fieldObj);
                }
                if (actualTypeArgument instanceof Class) {

                    if (isPrimitiveWrapper((Class<?>) actualTypeArgument)) {
                        fieldObj = generateWrapperUsingPrimitive(actualTypeArgument);
                    } else {
                        fieldObj = ((Class<?>) actualTypeArgument).newInstance();
                    }
                    if (fieldType instanceof ParameterizedType && ((ParameterizedType) fieldType).getRawType() == List.class) {
                        List mockList = new ArrayList();
                        mockList.add(fieldObj);
                        field.set(target, mockList);
                    } else {
                        field.set(target, fieldObj);
                    }
                    if (isPrimitive((Class<?>) actualTypeArgument) || isPrimitiveWrapper((Class<?>) actualTypeArgument)) {
                        recursive(fieldObj, null, true);
                    } else {
                        recursive(fieldObj, null, false);
                    }
                    continue;
                }
            }

            // 原生泛型List Map Set
            if (fieldType instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) fieldType;
                Class<?> rawType = (Class<?>) pType.getRawType();
                Type[] arguments = pType.getActualTypeArguments();
                if (rawType == List.class) {
                    List mockList = new ArrayList();
                    Object listObj = null;
                    if (isPrimitive(arguments[0].getClass()) || arguments[0] == String.class) {
                        listObj = ((Class) arguments[0]).newInstance();
                    } else {

                        listObj = ((Class) arguments[0]).newInstance();
                    }
                    mockList.add(listObj);
                    if (!arguments[0].getClass().isPrimitive() && arguments[0] != String.class) {
                        recursive(listObj, arguments, false);
                    }
                    field.set(target, mockList);
                }
            }

            if (fieldType instanceof Class) {
                if (setField(field.getType(), field, target)) {
                    continue;
                }
                Type type = field.getType();
                Object obj = ((Class) type).newInstance();
                for (Field declaredField : obj.getClass().getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    recursive(obj, null, false);
                }
                field.set(target, obj);
            }
        }
    }

    private Object generateWrapperUsingPrimitive(Type type) {

        if (type == Integer.class) {
            return new Integer(9);
        }
        if (type == Double.class) {
            return new Double(999.9);
        }
        if (type == Boolean.class) {
            return new Boolean(true);
        }
        if (type == Character.class) {
            return new Character('0');
        }
        if (type == Long.class) {
            return new Long(999);
        }
        if (type == Float.class) {
            return new Float(9.99);
        }
        if (type == Byte.class) {
            return new Byte((byte) 9);
        }
        if (type == Short.class) {
            return new Short((short) 9);
        }
        return null;
    }

    public boolean isPrimitive(Class<?> type) {
        if (type.isPrimitive()) {
            return true;
        }
        return false;
    }

    public boolean isPrimitiveWrapper(Class<?> type) {
        if (PRIMITIVES_TO_WRAPPERS.values().contains(type)) {
            return true;
        }
        return false;
    }

    public boolean setField(Type type, Field field, Object ret) throws Exception {
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
            field.set(ret, 999L);
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
            field.set(ret, new Integer(9));
            isPrimitive = true;
        }
        if (type == Double.class) {
            field.set(ret, new Double(999.9));
            isPrimitive = true;

        }
        if (type == Boolean.class) {
            field.set(ret, new Boolean(true));
            isPrimitive = true;

        }
        if (type == Character.class) {
            field.set(ret, new Character('0'));
            isPrimitive = true;

        }
        if (type == Long.class) {
            field.set(ret, new Long(999));
            isPrimitive = true;

        }
        if (type == Float.class) {
            field.set(ret, new Float(9.99));
            isPrimitive = true;

        }
        if (type == Byte.class) {
            field.set(ret, new Byte((byte) 9));
            isPrimitive = true;

        }
        if (type == Short.class) {
            field.set(ret, new Short((short) 9));
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

}
