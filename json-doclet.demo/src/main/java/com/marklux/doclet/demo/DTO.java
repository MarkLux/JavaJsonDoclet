package com.marklux.doclet.demo;

/**
 * 通用数据包装
 *
 * @author lumin
 */
public class DTO {

    /**
     * 数据
     */
    private Object data;

    /**
     * 是否成功
     */
    private boolean success;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
