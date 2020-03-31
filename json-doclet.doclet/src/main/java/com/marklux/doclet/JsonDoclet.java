package com.marklux.doclet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.marklux.doclet.annotation.Model;
import com.marklux.doclet.annotation.Service;
import com.marklux.doclet.constant.DocletConstants;
import com.marklux.doclet.desc.MethodDesc;
import com.marklux.doclet.desc.ModelDesc;
import com.marklux.doclet.desc.PropertyDesc;
import com.marklux.doclet.desc.ServiceDesc;
import com.marklux.doclet.parser.MethodDocletParser;
import com.marklux.doclet.parser.ModelDocletParser;
import com.marklux.doclet.parser.PropertyDocletParser;
import com.marklux.doclet.parser.ServiceDoc;
import com.sun.javadoc.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json doclet
 *
 * @author lumin
 */
public class JsonDoclet extends Doclet {

    /**
     * 设置语言版本，只有大于1.5版本才能获取泛型类型
     */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    /**
     * 主入口
     */
    public static boolean start(RootDoc root) {
        ClassDoc[] classDocs = root.classes();
        // 储存所有扫描到的class doc
        Map<String, ClassDoc> classDocMap = new HashMap<>();
        // 储存最终产出的所有模型描述符 (全量）
        Map<String, ModelDesc> modelDescMap = new HashMap<>();
        // 储存最终产出的所有服务描述符（全量）
        Map<String, ServiceDesc> serviceDescMap = new HashMap<>();
        // 记录所有包含指定注解的模型
        List<ClassDoc> rootModels = new ArrayList<>();
        // 记录所有包含指定注解的服务
        List<ServiceDoc> rootServices = new ArrayList<>();
        // 扫描注解
        for (ClassDoc classDoc : classDocs) {
            // 全量的classDoc
            classDocMap.put(classDoc.qualifiedName(), classDoc);
            for (AnnotationDesc annotationDesc : classDoc.annotations()) {
                AnnotationTypeDoc annotationTypeDoc = annotationDesc.annotationType();
                if (eqClazz(annotationTypeDoc.qualifiedName(), Model.class)) {
                    rootModels.add(classDoc);
                }
                if (eqClazz(annotationTypeDoc.qualifiedName(), Service.class)) {
                    // 进一步检索注解类型
                    ServiceDoc serviceDoc = parseServiceDoc(classDoc, annotationDesc);
                    rootServices.add(serviceDoc);
                }
            }
        }

        JSONObject outputJson = new JSONObject();

        // 正式解析
        // 以基本的模型为入口开始递归
        for (ClassDoc classDoc : rootModels) {
            ModelDesc modelDesc = ModelDocletParser.parseModelDesc(classDoc);
            if (modelDesc != null) {
                // 当前模型下扫描出的所有服务模型
                modelDescMap.put(modelDesc.getType(), modelDesc);
                walkModel(modelDesc, classDocMap, modelDescMap);
            }
        }
        // 解析服务
        for (ServiceDoc serviceDoc : rootServices) {
            walkService(serviceDoc, classDocMap, modelDescMap, serviceDescMap);
        }


        // 找到指定的文件目录
        String[][] options = root.options();
        String outputFile = "";
        for (String[] opt : options) {
            if (opt[0].equals("-outputfile")) {
                outputFile = opt[1];
            }
        }

        if (outputFile != null && outputFile.length() > 0) {
            outputJson.put("models", modelDescMap.values());
            outputJson.put("services", serviceDescMap.values());
            writeToFile(outputJson.toJSONString(), outputFile);
        }

        return true;
    }

    /**
     * 解析参数长度
     */
    public static int optionLength(String option) {
        Map<String, Integer> options = new HashMap<String, Integer>();
        options.put("-outputfile", 2);

        Integer value = options.get(option);
        if (value != null) {
            return value;
        } else {
            return 0;
        }
    }

    private static ServiceDoc parseServiceDoc(ClassDoc rawDoc, AnnotationDesc annotationDesc) {
        // 扫描注解信息
        AnnotationDesc.ElementValuePair[] elementValuePairs = annotationDesc.elementValues();
        String alias = "";
        String modelClass = "";
        AnnotationValue[] tags = {};
        for (AnnotationDesc.ElementValuePair pair : elementValuePairs) {
            if (DocletConstants.DOMAIN_SERVICE_ANNOTATION_MODEL.equals(pair.element().name())) {
                modelClass = ((ClassDoc) pair.value().value()).qualifiedName();
            } else if (DocletConstants.DOMAIN_SERVICE_ANNOTATION_ALIAS.equals(pair.element().name())) {
                alias = (String) pair.value().value();
            } else if (DocletConstants.DOMAIN_SERVICE_ANNOTATION_TAG.equals(pair.element().name())) {
                tags = (AnnotationValue[]) pair.value().value();
            }
        }
        ServiceDoc serviceDoc = new ServiceDoc();
        serviceDoc.setClassDoc(rawDoc);
        serviceDoc.setAlias(alias);
        String[] tagVals = new String[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagVals[i] = (String) tags[i].value();
        }
        serviceDoc.setTags(tagVals);
        return serviceDoc;
    }

    /**
     * 递归遍历所有需要录入schema的模型
     *
     * @param modelDesc    根model
     * @param classDocMap  全量classDoc
     * @param modelDescMap 输出modelDescMap
     */
    private static void walkModel(ModelDesc modelDesc, Map<String, ClassDoc> classDocMap,
                                  Map<String, ModelDesc> modelDescMap) {
        if (modelDesc == null || modelDesc.getProperties() == null) {
            return;
        }
        // 扫描所有属性
        for (PropertyDesc propertyDesc : modelDesc.getProperties()) {
            List<String> needDocPropTypes = new ArrayList<>();
            if (!propertyDesc.isPrimitive()
                    && !modelDescMap.containsKey(propertyDesc.getType())) {
                // 如果是集合/map类型，要替换type
                if (!propertyDesc.isCollection() && !propertyDesc.isMap()) {
                    needDocPropTypes.add(propertyDesc.getType());
                }
                if (propertyDesc.getKeyType() != null
                        && !PropertyDocletParser.isPrimitive(propertyDesc.getKeyType())
                        && !modelDescMap.containsKey(propertyDesc.getKeyType())) {
                    // key 注入
                    needDocPropTypes.add(propertyDesc.getKeyType());
                }
                if (propertyDesc.getValueType() != null
                        && !PropertyDocletParser.isPrimitive(propertyDesc.getValueType())
                        && !modelDescMap.containsKey(propertyDesc.getValueType())) {
                    // value 注入
                    needDocPropTypes.add(propertyDesc.getValueType());
                }
            }
            if (!needDocPropTypes.isEmpty()) {
                for (String propType : needDocPropTypes) {
                    ClassDoc propTypeDoc = classDocMap.get(propType);
                    if (propTypeDoc != null) {
                        ModelDesc propModelDesc = ModelDocletParser.parseModelDesc(propTypeDoc);
                        if (propModelDesc != null) {
                            modelDescMap.put(propModelDesc.getType(), propModelDesc);
                            // 递归深入
                            walkModel(propModelDesc, classDocMap, modelDescMap);
                        }
                    }
                }
            }
        }
    }

    /**
     * 递归遍历所有领域服务及其出入参模型
     *
     * @param serviceDoc   服务doc
     * @param classDocMap  全量classDoc
     * @param modelDescMap 输出modelDescMap
     */
    private static void walkService(ServiceDoc serviceDoc, Map<String, ClassDoc> classDocMap,
                                    Map<String, ModelDesc> modelDescMap, Map<String, ServiceDesc> serviceDescs) {
        if (serviceDoc == null || serviceDescs.containsKey(serviceDoc.getClassDoc().qualifiedName())) {
            return;
        }

        ServiceDesc serviceDesc = new ServiceDesc();
        serviceDesc.setAlias(serviceDoc.getAlias());
        serviceDesc.setTags(serviceDoc.getTags());
        serviceDesc.setDescription(serviceDoc.getClassDoc().commentText());
        serviceDesc.setServiceInterface(serviceDoc.getClassDoc().qualifiedName());

        List<MethodDesc> methodDescs = new ArrayList<>();
        // 解析方法
        MethodDoc[] methodDocs = serviceDoc.getClassDoc().methods();
        // 用于储存方法名相同的重载方法
        Map<String, Integer> methodNames = new HashMap<>();
        for (MethodDoc methodDoc : methodDocs) {
            MethodDesc methodDesc = MethodDocletParser.parseMethodDesc(methodDoc);
            if (methodDesc != null) {
                if (methodNames.containsKey(methodDesc.getMethodName())) {
                    // 发生重载的方法
                    Integer suffix = methodNames.get(methodDesc.getMethodName());
                    suffix = suffix + 1;
                    methodDesc.setMethodAlias(methodDesc.getMethodName() + "_" + suffix);
                    methodNames.put(methodDesc.getMethodName(), suffix);
                } else {
                    methodNames.put(methodDesc.getMethodName(), 0);
                }
                methodDescs.add(methodDesc);
                // 补充方法中声明的模型
                List<String> needDocTypes = new ArrayList<>();
                // 返回值和参数中额外声明的模型
                List<PropertyDesc> returnAndParamDescs = new ArrayList<>();
                if (methodDesc.getParams() != null) {
                    returnAndParamDescs.addAll(methodDesc.getParams());
                }
                if (methodDesc.getResultType() != null) {
                    returnAndParamDescs.add(methodDesc.getResultType());
                }
                List<String> propertyTypes = new ArrayList<>();
                for (PropertyDesc propertyDesc : returnAndParamDescs) {
                    // 如果不是原子类型，也没有在已知的ModelDesc中，说明需要重新解析
                    // map 和 list要单独判断
                    if (propertyDesc.isCollection()) {
                        propertyTypes.add(propertyDesc.getValueType());
                    } else if (propertyDesc.isMap()) {
                        propertyTypes.add(propertyDesc.getKeyType());
                        propertyTypes.add(propertyDesc.getValueType());
                    } else {
                        propertyTypes.add(propertyDesc.getType());
                    }
                }
                for (String propertyType : propertyTypes) {
                    if (!PropertyDocletParser.isPrimitive(propertyType) && !modelDescMap.containsKey(propertyType)) {
                        needDocTypes.add(propertyType);
                    }
                }
                // 递归生成模型
                if (!needDocTypes.isEmpty()) {
                    // 入参
                    for (Parameter p : methodDoc.parameters()) {
                        ClassDoc pClassDoc = p.type().asClassDoc();
                        if (pClassDoc == null) {
                            continue;
                        }
                        if (needDocTypes.contains(pClassDoc.qualifiedName())) {
                            ModelDesc modelDesc = ModelDocletParser.parseModelDesc(pClassDoc);
                            if (modelDesc != null) {
                                modelDescMap.put(modelDesc.getType(), modelDesc);
                                walkModel(modelDesc, classDocMap, modelDescMap);
                            }
                        }
                    }
                    // 返回结果
                    if (methodDoc.returnType() != null) {
                        ClassDoc rClassDoc = methodDoc.returnType().asClassDoc();
                        ModelDesc modelDesc = ModelDocletParser.parseModelDesc(rClassDoc);
                        if (modelDesc != null) {
                            modelDescMap.put(modelDesc.getType(), modelDesc);
                            walkModel(modelDesc, classDocMap, modelDescMap);
                        }
                    }
                }
            }
        }
        serviceDesc.setMethods(methodDescs);

        serviceDescs.put(serviceDesc.getServiceInterface(), serviceDesc);
    }

    private static boolean eqClazz(String str, Class<?> clazz) {
        return str.equals(clazz.getCanonicalName());
    }

    private static void writeToFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println("fail to write schema file, " + e);
        }
    }
}
