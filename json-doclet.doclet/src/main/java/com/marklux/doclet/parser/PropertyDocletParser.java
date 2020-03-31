package com.marklux.doclet.parser;

import com.google.common.collect.Sets;
import com.marklux.doclet.desc.PropertyDesc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.Type;

import java.util.*;

/**
 * 属性解析
 *
 * @author lumin
 */
public class PropertyDocletParser {

    /**
     * 集合类型
     */
    private static final Set<String> collectionTypes
            = Sets.newHashSet(
            Collection.class.getCanonicalName(),
            List.class.getCanonicalName(),
            Set.class.getCanonicalName()
    );

    /**
     * 映射类型
     */
    private static final Set<String> mapTypes
            = Sets.newHashSet(
            Map.class.getCanonicalName()
    );

    /**
     * 原子类型
     */
    private static final Set<String> primitiveTypes
            = Sets.newHashSet(
            Integer.class.getCanonicalName(),
            String.class.getCanonicalName(),
            Long.class.getCanonicalName(),
            Boolean.class.getCanonicalName(),
            Byte.class.getCanonicalName(),
            String.class.getCanonicalName(),
            Short.class.getCanonicalName(),
            Double.class.getCanonicalName(),
            Float.class.getCanonicalName(),
            Character.class.getCanonicalName(),
            Object.class.getCanonicalName(),
            Date.class.getCanonicalName(),
            Calendar.class.getCanonicalName(),
            Void.class.getCanonicalName(),
            boolean.class.getCanonicalName(),
            byte.class.getCanonicalName(),
            char.class.getCanonicalName(),
            short.class.getCanonicalName(),
            int.class.getCanonicalName(),
            float.class.getCanonicalName(),
            double.class.getCanonicalName(),
            long.class.getCanonicalName(),
            void.class.getCanonicalName()
    );

    public static boolean isPrimitive(String className) {
        return primitiveTypes.contains(className);
    }

    public static PropertyDesc parseProperty(FieldDoc fieldDoc) {
        // 过滤，不解析方法
        if (fieldDoc == null|| fieldDoc.isMethod()) {
            return null;
        }

        // 基本信息
        PropertyDesc propertyDesc = new PropertyDesc();
        propertyDesc.setName(fieldDoc.name());
        propertyDesc.setComment(fieldDoc.commentText());


        Type type = fieldDoc.type();

        parseProperty(propertyDesc, type);

        return propertyDesc;
    }

    public static PropertyDesc parseProperty(Parameter parameter) {

        if (parameter == null) {
            return null;
        }

        PropertyDesc propertyDesc = new PropertyDesc();
        propertyDesc.setName(parameter.name());
        // 不用在这里维护注释，注释在外层会解决的
        Type type = parameter.type();

        parseProperty(propertyDesc, type);

        return propertyDesc;
    }

    public static void parseProperty(PropertyDesc propertyDesc, Type type) {

        propertyDesc.setType(type.qualifiedTypeName());

        if (type.dimension() != null && type.dimension().length() > 0) {
            // 是数组类型
            propertyDesc.setCollection(true);
            propertyDesc.setType(type.qualifiedTypeName() + type.dimension());
            propertyDesc.setValueType(type.qualifiedTypeName());
        } else if (collectionTypes.contains(type.qualifiedTypeName())) {
            // 集合
            propertyDesc.setCollection(true);
            // 获取泛型元素类型
            ParameterizedType parameterizedType = type.asParameterizedType();
            if (parameterizedType != null) {
                propertyDesc.setContainGenericType(true);
                Type[] pTypes = parameterizedType.typeArguments();
                if (pTypes.length == 1) {
                    propertyDesc.setValueType(pTypes[0].qualifiedTypeName());
                }
            }
        } else if (mapTypes.contains(type.qualifiedTypeName())) {
            // map
            propertyDesc.setMap(true);
            ParameterizedType parameterizedType = type.asParameterizedType();
            if (parameterizedType != null) {
                propertyDesc.setContainGenericType(true);
                Type[] pTypes = parameterizedType.typeArguments();
                if (pTypes.length == 2) {
                    propertyDesc.setKeyType(pTypes[0].qualifiedTypeName());
                    propertyDesc.setValueType(pTypes[1].qualifiedTypeName());
                }
            }
        } else {
            // 非复合类型才有可能是基本类型
            if (type.isPrimitive() || primitiveTypes.contains(type.qualifiedTypeName())) {
                propertyDesc.setPrimitive(true);
            }
        }
    }
}
