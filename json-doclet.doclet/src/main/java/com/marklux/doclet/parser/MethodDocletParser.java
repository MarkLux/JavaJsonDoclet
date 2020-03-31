package com.marklux.doclet.parser;

import com.marklux.doclet.constant.DocletConstants;
import com.marklux.doclet.desc.MethodDesc;
import com.marklux.doclet.desc.PropertyDesc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务描述符解析器
 *
 * @author lumin
 */
public class MethodDocletParser {

    public static MethodDesc parseMethodDesc(MethodDoc methodDoc) {

        MethodDesc methodDesc = new MethodDesc();
        // 方法名
        methodDesc.setMethodName(methodDoc.name());
        // 方法注释
        methodDesc.setComment(methodDoc.commentText());
        // 参数注释
        ParamTag[] paramTags = methodDoc.paramTags();
        if (paramTags != null) {
            Map<String, String> paramCommentMap = new HashMap<>();
            for (ParamTag paramTag: paramTags) {
                paramCommentMap.put(paramTag.parameterName(), paramTag.parameterComment());
            }
            methodDesc.setParamsComment(paramCommentMap);
        }
        // 返回值注释
        Tag[] returnTags = methodDoc.tags(DocletConstants.DOMAIN_METHOD_TAG_RETURN);
        if (returnTags != null && returnTags.length > 0) {
            methodDesc.setResultComment(returnTags[0].text());
        }
        // 参数类型
        Parameter[] parameters = methodDoc.parameters();
        List<PropertyDesc> propertyDescs = new ArrayList<>();
        if (parameters != null) {
            for (Parameter parameter: parameters) {
                PropertyDesc propertyDesc = PropertyDocletParser.parseProperty(parameter);
                if (propertyDesc != null) {
                    propertyDescs.add(propertyDesc);
                }
            }
        }
        methodDesc.setParams(propertyDescs);
        // 返回值
        if (methodDoc.returnType() != null
                && !void.class.getCanonicalName().equals(methodDoc.returnType().typeName())) {
            PropertyDesc returnDesc = new PropertyDesc();
            PropertyDocletParser.parseProperty(returnDesc, methodDoc.returnType());
            methodDesc.setResultType(returnDesc);
        }

        return methodDesc;
    }
}
