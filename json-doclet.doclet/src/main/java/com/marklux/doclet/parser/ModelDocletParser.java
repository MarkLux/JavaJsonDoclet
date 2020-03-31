package com.marklux.doclet.parser;

import com.marklux.doclet.desc.ModelDesc;
import com.marklux.doclet.desc.PropertyDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型doc parser
 *
 * @author lumin
 */
public class ModelDocletParser {

    public static ModelDesc parseModelDesc(ClassDoc classDoc) {
        if (classDoc == null) {
            return null;
        }

        ModelDesc modelDesc = new ModelDesc();

        // 完整类名
        modelDesc.setType(classDoc.qualifiedTypeName());
        // 所在包
        modelDesc.setPackageName(classDoc.containingPackage().name());

        // 父类
        ClassDoc superClassDoc = classDoc.superclass();
        if (superClassDoc != null) {
            modelDesc.setHasParent(true);
            modelDesc.setParentType(superClassDoc.qualifiedTypeName());
        }

        // 描述信息: 直接解析类描述，无javadoc标准注解
        modelDesc.setDescription(classDoc.commentText());

        // 是否包含泛型
        if (classDoc.asParameterizedType() != null) {
            modelDesc.setContainGenericType(true);
            modelDesc.setGenericElementType(classDoc.asParameterizedType().qualifiedTypeName());
        }


        List<PropertyDesc> propertyDescs = new ArrayList<>();

        // 属性列表，解析私有属性
        for (FieldDoc fieldDoc: classDoc.fields(false)) {
            PropertyDesc propertyDesc = PropertyDocletParser.parseProperty(fieldDoc);
            propertyDescs.add(propertyDesc);
            modelDesc.setProperties(propertyDescs);
        }

        return modelDesc;
    }
}
