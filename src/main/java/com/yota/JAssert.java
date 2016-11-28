package com.yota;


import com.yota.elements.Label;
import com.yota.util.Logger;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.openqa.selenium.By;

public class JAssert {
    private Logger logger = Logger.getInstance();
    private final String messageTemplate = "%s содержит ";
    private final String incorrectM = "некорректный текст: ";
    private final String correctM = "корректный текст: ";
    private static JAssert instance = null;

    /**
     * @param entityName название сущности, текст внутри которой проверяется. Например: Поле
     * @param expected ожидаемый текст
     * @param actual актуальный текст
     * @param printSuccessResult устанавливается в true, если есть необходимость выводить сообщение о корректном тексте в сущности
     */
    public final void assertText(final String entityName, final String expected,  String actual, final Boolean printSuccessResult){
        if (!expected.equals(actual)) {
            logger.warn(String.format(messageTemplate, entityName) + incorrectM + actual + " ; Ожидалось: " + expected);
        } else {
            if(printSuccessResult){
                logger.info(String.format(messageTemplate, entityName) + correctM + "'" + expected + "'");
            }
        }
    }


    /**
     * @param entityName название сущности, текст внутри которой проверяется. Например: Поле
     * @param expected ожидаемый текст
     * @param actual актуальный текст
     * @param printSuccessResult устанавливается в true, если есть необходимость выводить сообщение о корректном тексте в сущности
     */
    public final void assertTextContains(final String entityName, final String expected,  final String actual, final Boolean printSuccessResult){
        if (!actual.contains(expected)) {
            logger.warn(String.format(messageTemplate, entityName) + incorrectM + actual + " ; Ожидалось: " + expected);
        } else {
            if(printSuccessResult){
                logger.info(String.format(messageTemplate, entityName) + correctM + expected);
            }
        }
    }

    /**
     * @param entityName название сущности, текст внутри которой проверяется. Например: Поле
     * @param expected ожидаемый текст
     * @param actual актуальный текст
     * @param printSuccessResult устанавливается в true, если есть необходимость выводить сообщение о корректном тексте в сущности
     */
    public final void assertTextContains(final String entityName, final String[] expected,  final String actual, final Boolean printSuccessResult){
        for(int i = 0; i < expected.length; i++){
            assertTextContains(entityName,expected[i],actual,printSuccessResult);
            if(printSuccessResult){
                logger.info(String.format(messageTemplate, entityName) + correctM + expected[i]);
            }
        }
    }

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
