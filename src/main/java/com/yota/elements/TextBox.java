package com.yota.elements;

import org.openqa.selenium.By;

public class TextBox extends BaseElement {

    /** Базовый конструктор
     * @param locator локатор текстбокса
     * @param name название текстбокса
     */
    public TextBox(final By locator, final String name) {
        super(locator, name);
    }

    /** Возвращает тип элемента
     * @return "Текстбокс"
     */
    protected String getElementType() {
        return "Текстбокс";
    }

    /** Вводит текст с выводом лога
     * @param value текст
     */
    public void type(String value) {
        info(String.format("Ввод" + " '%1$s'", value));
        element.sendKeys(value);
    }

    /** Вводит текст с ожиданием появления текстбокса и очисткой поля
     * @param value текст
     */
    public void setText(String value) {
        waitForIsElementPresent();
        element.click();
        element.clear();
        type(value);
    }


}