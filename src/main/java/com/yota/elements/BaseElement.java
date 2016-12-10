package com.yota.elements;

import com.yota.BaseEntity;
import com.yota.util.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BaseElement extends BaseEntity {


    protected String name;
    protected By locator;
    protected RemoteWebElement element;

    /**
     * Основной конструктор
     * @param locator локатор элемента
     * @param name название элемента
     */
    protected BaseElement(final By locator, final String name) {
        this.locator = locator;
        this.name = name;
        this.element = (RemoteWebElement) browser.getDriver().findElement(locator);
    }

    /** Возвращает элемент
     * @return элемент
     */
    public RemoteWebElement getElement() {
        return element;
    }

    /** Возвращает название элемента
     * @return название элемента
     */
    public String getName() {
        return name;
    }



    /** Формотирование логов
     * @param message message
     * @return вывод отформатированных логов
     */
    protected String formatLogMsg(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", getElementType(), name,
                Logger.LOG_DELIMITER, message);
    }

    /*
     * Возвращает тип элемента
     * @return тип элемента
     */
    protected abstract String getElementType();


    /**
     * Кликает по элементу и дожидается загрузки страницы
     */
    public void clickAndWait() {
        waitForIsElementPresent();
        click();
        browser.waitForPageToLoad();
        browser.waitForAjaxRequests();
    }


    /**
     * Кликает по элементу
     */
    public void click() {
        waitForIsElementPresent();
        info("Клик");
        browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        element.click();
    }

    /**
     * Возвращает текст внутри элемента
     * @return текст внутри элемента
     */
    public String getText() {
        waitForIsElementPresent();
        return element.getText();
    }



    /**
     * Ждет пока элемент появится на странице, если он не появился в течении
     * заданного времени, то тест завершается
     */
    public void waitForIsElementPresent() {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(),
                Long.parseLong(browser.getTimeout()));

        try {
            wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        List<WebElement> list = driver.findElements(locator);
                        for (WebElement el : list) {
                            if (el instanceof RemoteWebElement
                                    && el.isDisplayed()) {
                                element = (RemoteWebElement) el;
                                if (element.isDisplayed())
                                    return element.isDisplayed();
                            }
                        }
                        element = (RemoteWebElement) driver
                                .findElement(locator);
                    } catch (Exception e) {
                        return false;
                    }
                    return element.isDisplayed();
                }
            });
        } catch (Exception e) {
            System.out.println("wait for element is present fail: "
                    + e.getMessage());
            boolean res = false;
            try {
                res = (element != null) && (element.isDisplayed());
            } catch (Exception e1) {
                System.out.println("wait for element is present fail e1: "
                        + e1.getMessage());
            }
            jassert.assertTrue(formatLogMsg(" отсутствует"), "", res, false);
        }
        jassert.assertTrue(formatLogMsg(" отсутствует"), "", element.isDisplayed(), false);
    }

    /**
     * Кликает по элементу с помощью Javascript
     */
    public void jsClickAndWait() {
        waitForIsElementPresent();
        info("Клик c помощью JS");
        ((JavascriptExecutor) browser.getDriver()).executeScript("arguments[0].click();", element);
        browser.waitForPageToLoad();
        browser.waitForAjaxRequests();
    }

    /** Присваивает тегу некоторое значение(innerHTML) с помощью JS
     * @param value значение
     */
    public void setInnerHtml(String value){
        waitForIsElementPresent();
        element.click();
        info("Ввод значения '" + value  + "'");
        ((JavascriptExecutor) browser.getDriver()).executeScript("arguments[0].innerHTML=\"\";", element);
        ((JavascriptExecutor) browser.getDriver()).executeScript("arguments[0].innerHTML=\"" + value + "\";", element);
    }
}
