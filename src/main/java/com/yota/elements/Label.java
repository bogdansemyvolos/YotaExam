package com.yota.elements;
import org.openqa.selenium.By;

public class Label extends BaseElement {

    /** Базовый конструктор.
     * @param locator локатор поля
     * @param name название поля
     */
    public Label(final By locator, final String name) {
        super(locator, name);
    }

    /** Возвращает тип элемента
     * @return "Поле"
     */
    protected String getElementType() {
        return "Поле";
    }

}
