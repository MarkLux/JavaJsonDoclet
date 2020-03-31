package com.marklux.doclet.desc;


import java.util.List;

/**
 * 服务描述
 *
 * @author lumin
 */
public class ServiceDesc {

    /**
     * 服务别名
     */
    private String alias;

    /**
     * 服务定义（java: interface)
     */
    private String serviceInterface;

    /**
     * 说明
     */
    private String description;

    /**
     * 标签
     */
    private String[] tags;

    /**
     * 方法列表
     */
    private List<MethodDesc> methods;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MethodDesc> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodDesc> methods) {
        this.methods = methods;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
