package com.marklux.doclet.desc;

import java.util.List;
import java.util.Map;

/**
 * 方法描述符
 *
 * @author lumin
 */
public class MethodDesc  {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法别称（用于区分同名的重载方法）
     */
    private String methodAlias;

    /**
     * 注释说明
     */
    private String comment;

    /**
     * 入参说明
     */
    private Map<String, String> paramsComment;

    /**
     * 返回值注释
     */
    private String resultComment;

    /**
     * 返回值类型（java：完整类名）
     */
    private PropertyDesc resultType;

    /**
     * 入参描述
     */
    private List<PropertyDesc> params;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public PropertyDesc getResultType() {
        return resultType;
    }

    public void setResultType(PropertyDesc resultType) {
        this.resultType = resultType;
    }

    public List<PropertyDesc> getParams() {
        return params;
    }

    public void setParams(List<PropertyDesc> params) {
        this.params = params;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Map<String, String> getParamsComment() {
        return paramsComment;
    }

    public void setParamsComment(Map<String, String> paramsComment) {
        this.paramsComment = paramsComment;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    public String getMethodAlias() {
        return methodAlias;
    }

    public void setMethodAlias(String methodAlias) {
        this.methodAlias = methodAlias;
    }
}
