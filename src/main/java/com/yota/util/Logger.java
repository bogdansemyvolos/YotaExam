package com.yota.util;

import org.testng.Assert;


public class Logger {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
    private static Logger instance = null;
    public static final String LOG_DELIMITER = "::";


    private Logger() {
    }

    /**
     * Инициализирует и возвращает логер
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }


    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void fatal(String message) {
        logger.fatal(message);
        Assert.assertTrue(false);
    }
}
