package me.gwma.java.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

    // 首先获得日志记录这个对象
    static private Logger logger = LoggerFactory.getLogger(Slf4jTest.class);

    public static void main(String[] args) {
        // 记录error信息
        logger.error("[error message]");
        // // 记录info，还可以传入参数
        logger.info("[info message] {}={}","info content", "arg 2");
        // 记录deubg信息
        logger.debug("[debug message]");
        // 记录trace信息
        logger.trace("[trace message]");

        System.out.println("hello world");
    }
}
