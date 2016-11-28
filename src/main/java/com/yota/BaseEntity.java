package com.yota;


import com.yota.util.Browser;
import com.yota.util.Logger;
import static org.testng.AssertJUnit.assertTrue;

public abstract class BaseEntity {

    protected static Logger logger = Logger.getInstance();
    protected static Browser browser = Browser.getInstance();
    protected static JAssert jassert = JAssert.getInstance();

    protected abstract String formatLogMsg(String message);

    protected void info(String message) {
        logger.info(formatLogMsg(message));
    }

    protected void warn(String message) {
        logger.warn(formatLogMsg(message));
    }

    protected void error(String message) {
        logger.error(formatLogMsg(message));
    }

    protected void fatal(String message) {
        logger.fatal(formatLogMsg(message));
        assertTrue(formatLogMsg(message), false);
    }
}
