package com.marklux.doclet.demo;

import com.marklux.doclet.annotation.Model;

import java.util.List;

/**
 * 用户模型
 * @author lumin
 */
@Model(namespace = "com.marklux.test", name = "user")
public class User {

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别，male: 男；female: 女
     */
    private String sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 图片列表
     */
    private List<String> photos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
