package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalLogger {
    private static Logger logger = LoggerFactory.getLogger("logger");

    public static void info(String msg){
        logger.info(msg);
    }

    public static void error(String msg){
        logger.error(msg);
    }

    public static void debug(String msg){
        logger.debug(msg);
    }

}
