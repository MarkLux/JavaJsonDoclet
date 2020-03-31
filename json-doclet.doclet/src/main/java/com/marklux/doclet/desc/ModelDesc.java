package com.marklux.doclet.desc;

import java.util.List;

/**
 * 模型描述
 *
 * hint1: 不要以java语言的思路去思考，面向使用者的永远是"模型"这一概念，而不是"类"
 * 即便换任何语言都要保证通用型。
 *
 * hint2: 模型上不要直接挂载方法
 *
 * @author lumin
 */
public class ModelDesc  {


    /**
     * 类型（完整类名）
     */
    private String type;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否有父类型
     */
    private boolean hasParent;

    /**
     * 父类型（java完整类名）
     */
    private String parentType;

    /**
     * 是否包含泛型
     */
    private boolean containGenericType;

    /**
     * 泛型元素类型（可能嵌套，先只解析一层）
     */
    private String genericElementType;

    /**
     * 属性
     */
    private List<PropertyDesc> properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public boolean isContainGenericType() {
        return containGenericType;
    }

    public void setContainGenericType(boolean containGenericType) {
        this.containGenericType = containGenericType;
    }

    public String getGenericElementType() {
        return genericElementType;
    }

    public void setGenericElementType(String genericElementType) {
        this.genericElementType = genericElementType;
    }

    public List<PropertyDesc> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDesc> properties) {
        this.properties = properties;
    }
}
