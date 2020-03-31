package com.marklux.doclet.desc;

/**
 * 属性描述符 可以对标 Type
 *
 * @author lumin
 */
public class PropertyDesc {

    /**
     * 属性名
     */
    private String name;

    /**
     * 原始完整类型 (java: 完整类名)
     */
    private String type;

    /**
     * key类型（java：完整类名）, 仅供map类型使用
     */
    private String keyType;

    /**
     * 元素类型（java：完整类名）, map & collections 使用
     */
    private String valueType;

    /**
     * 注释
     */
    private String comment;

    /**
     * mock值
     */
    private Object mockValue;

    /**
     * 是否是集合类型
     */
    private boolean collection;

    /**
     * 是否是kv映射类型
     */
    private boolean map;

    /**
     * 是否包含泛型
     */
    private boolean containGenericType;

    /**
     * 是否原子类型（包括装箱类）
     */
    private boolean primitive;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getMockValue() {
        return mockValue;
    }

    public void setMockValue(Object mockValue) {
        this.mockValue = mockValue;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    public boolean isMap() {
        return map;
    }

    public void setMap(boolean map) {
        this.map = map;
    }

    public boolean isContainGenericType() {
        return containGenericType;
    }

    public void setContainGenericType(boolean containGenericType) {
        this.containGenericType = containGenericType;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }
}
