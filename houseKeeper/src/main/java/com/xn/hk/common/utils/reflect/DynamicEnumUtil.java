package com.xn.hk.common.utils.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.reflect.ConstructorAccessor;
import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

/**
 * @author wanlei
 * @description 动态新增和修改枚举的工具类
 * @date 2020/4/23 14:41
 **/
@SuppressWarnings("restriction")
public class DynamicEnumUtil {
    private static ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();

    private static void setFailsafeFieldValue(Field field, Object target, Object value) throws NoSuchFieldException,
            IllegalAccessException {

        // let's make the field accessible
        field.setAccessible(true);

        // next we change the modifier in the Field instance to
        // not be final anymore, thus tricking reflection into
        // letting us modify the static final field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);

        // blank out the final bit in the modifiers int
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);

        FieldAccessor fa = reflectionFactory.newFieldAccessor(field, false);
        fa.set(target, value);
    }

    private static void blankField(Class<?> enumClass, String fieldName) throws NoSuchFieldException,
            IllegalAccessException {
        for (Field field : Class.class.getDeclaredFields()) {
            if (field.getName().contains(fieldName)) {
                AccessibleObject.setAccessible(new Field[]{field}, true);
                setFailsafeFieldValue(field, enumClass, null);
                break;
            }
        }
    }

    private static void cleanEnumCache(Class<?> enumClass) throws NoSuchFieldException, IllegalAccessException {
        blankField(enumClass, "enumConstantDirectory"); // Sun (Oracle?!?) JDK 1.5/6
        blankField(enumClass, "enumConstants"); // IBM JDK
    }

    private static ConstructorAccessor getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes)
            throws NoSuchMethodException {
        Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
        parameterTypes[0] = String.class;
        parameterTypes[1] = int.class;
        System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
        return reflectionFactory.newConstructorAccessor(enumClass.getDeclaredConstructor(parameterTypes));
    }

    private static Object makeEnum(Class<?> enumClass, String value, int ordinal, Class<?>[] additionalTypes,
                                   Object[] additionalValues) throws Exception {
        Object[] parms = new Object[additionalValues.length + 2];
        parms[0] = value;
        parms[1] = Integer.valueOf(ordinal);
        System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
        return enumClass.cast(getConstructorAccessor(enumClass, additionalTypes).newInstance(parms));
    }

    /**
     * get enum by enumName
     *
     * @param values
     * @param enumName
     * @return
     */
    private static <T extends Enum<?>> T getEnum(List<T> values, String enumName) {
        for (T value : values) {
            if (value.name().equals(enumName)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Add an enum instance to the enum class given as argument
     * if exists update old enum according to enumName with new enum value
     *
     * @param <T>      the type of the enum (implicit)
     * @param enumType the class of the enum to be modified
     * @param enumName the name of the new enum instance to be added to the class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> void saveEnum(Class<T> enumType, String enumName, Class<?>[] additionalTypes, Object[] additionalValues) {

        // 0. Sanity checks
        if (!Enum.class.isAssignableFrom(enumType)) {
            throw new RuntimeException("class " + enumType + " is not an instance of Enum");
        }

        // 1. Lookup "$VALUES" holder in enum class and get previous enum instances
        Field valuesField = null;
        Field[] fields = enumType.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }
        AccessibleObject.setAccessible(new Field[]{valuesField}, true);

        try {

            // 2. Copy it
            T[] previousValues = (T[]) valuesField.get(enumType);
            List<T> values = new ArrayList<T>(Arrays.asList(previousValues));

            // 3. build new enum
            T newValue = (T) makeEnum(enumType, enumName, values.size(), additionalTypes, additionalValues);

            // check old enum ,if exist ,remove old enum, equal to replace old enum with new enum value
            T oldEnum = getEnum(values, enumName);
            if (oldEnum != null) {
                System.out.println("Enum：" + enumName + " is exist,execute update enum value");
                values.remove(oldEnum);
            }

            // 4. add new value
            values.add(newValue);

            // 5. Set new values field
            setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));

            // 6. Clean enum cache
            cleanEnumCache(enumType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
