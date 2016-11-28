package com.yota;

import com.yota.util.Logger;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;

public class JAssert {
    private Logger logger = Logger.getInstance();
    private final String messageTemplate = "%s содержит ";
    private final String incorrectM = "некорректный текст: ";
    private final String correctM = "корректный текст: ";
    private static JAssert instance = null;


    /** Проверяет что актуальное значение верное
     * @param errorMessage сообщение если не true
     * @param successMessage сообщение если true
     * @param actual актуальное значение
     * @param printSuccessResult выводить инфу об успешной проверке в лог?
     */
    public final void assertTrue(final String errorMessage, final String successMessage, final Boolean actual, final Boolean printSuccessResult){
        Assert.assertTrue(errorMessage, actual);
        if(printSuccessResult){
            logger.info(successMessage);
        }
    }

    /** Проверяет что актуальное значение верное
     * @param errorMessage сообщение если не true
     * @param successMessage сообщение если true
     * @param expected ожидаемое значение
     * @param actual актуальное значение
     * @param printSuccessResult выводить инфу об успешной проверке в лог?
     */
    public final void assertEquals(final String errorMessage, final String successMessage, final String expected, final String actual, final Boolean printSuccessResult){
        try {
            Assert.assertEquals(errorMessage, expected, actual);
        }
        catch (AssertionFailedError e)
        {
            logger.fatal(errorMessage + ": " + actual + " :: Ожидаемое значение: " + expected);
        }
        if(printSuccessResult){
            logger.info(successMessage + ": " + actual);
        }
    }


    /**
     * Возвращает статический экзепляр класса
     * @return статический экзепляр класса
     */
    public static JAssert getInstance(){
        if(instance == null){
            instance = new JAssert();
        }
        return instance;
    }
}
