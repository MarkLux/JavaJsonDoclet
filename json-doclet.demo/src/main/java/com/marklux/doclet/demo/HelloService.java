package com.marklux.doclet.demo;

import com.marklux.doclet.annotation.Service;

/**
 * 测试服务
 *
 * @author lumin
 */
@Service(namespace = "com.marklux.test", alias = "helloService", tags = {"user", "say"})
public interface HelloService {

    /**
     * 说你好
     * @param user 用户模型
     * @return 你好文本
     */
    String sayHello(User user);

    /**
     * 打招呼
     * @param s  名字
     * @return 招呼文本
     */
    DTO greeting(String s);
}
