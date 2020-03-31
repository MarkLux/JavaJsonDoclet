package com.marklux.doclet;

import com.sun.javadoc.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义doclet：打印类名
 *
 * @author lumin
 */
public class CustomDoclet extends Doclet {

    /**
     * 设置语言版本，只有大于1.5版本才能获取泛型类型
     */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }

    /**
     * 主入口方法
     * @param root
     * @return
     */
    public static boolean start(RootDoc root) {
        if (root != null) {
            ClassDoc[] classDocs = root.classes();
            for (ClassDoc classDoc : classDocs) {
                System.out.println(classDoc.name());
                // 这里fields的入参填写false才可以取到私有属性
                for (FieldDoc fieldDoc: classDoc.fields(false)) {
                    System.out.println(fieldDoc.name());
                    System.out.println(fieldDoc.type().qualifiedTypeName());
                }
            }
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
}
