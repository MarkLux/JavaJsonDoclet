package com.marklux.doclet.parser;

import com.sun.javadoc.ClassDoc;

public class ServiceDoc {

    /**
     * 别名
     */
    private String alias;

    /**
     * 标签
     */
    private String[] tags;

    /**
     * class doc
     */
    private ClassDoc classDoc;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public ClassDoc getClassDoc() {
        return classDoc;
    }

    public void setClassDoc(ClassDoc classDoc) {
        this.classDoc = classDoc;
    }
}
