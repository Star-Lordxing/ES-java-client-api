package com.es.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestLog {
    private static Logger logger = LogManager.getLogger("HelloLog4j");
    public static void main(String ags[]){
        logger.info("你好，{}",1);
    }
}
