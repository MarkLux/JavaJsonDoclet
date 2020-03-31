package com.marklux.doclet.test;

import com.marklux.doclet.CustomDoclet;
import com.marklux.doclet.JsonDoclet;
import com.sun.tools.javadoc.Main;

public class CustomDocletTest {

    public static void main(String[] args) {
        Main.execute("test", JsonDoclet.class.getName(), Thread.currentThread().getContextClassLoader(),
                "-sourcepath", "json-doclet.demo/src/main/java",
                "-subpackages", "com.marklux.doclet",
                "-outputfile", "schema.json",
                "-encoding", "UTF-8");
    }
}
