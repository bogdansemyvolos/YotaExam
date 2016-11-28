package com.yota.elements;


import org.openqa.selenium.By;

public class Button extends BaseElement {

    /**
     * Основной конструктор
     * @param locator Локатор кнопки
     * @param name Имя кнопки
     */
    public Button(final By locator, final String name) {
        super(locator, name);
    }

    /** Возвращает тип элемента
     * @return "Кнопка"
     */
    protected String getElementType() {
        return "Кнопка";
    }

    /**
     * Проверка на то, что кнопка активна
     *
     * @return
     */
    public boolean isEnabled() {
        String elementClass = element.getAttribute("class");
        return !elementClass.toLowerCase().contains("disabled");
    }

    /**
     * Проверка на то, что кнопка не активна
     *
     * @return
     */
    public boolean isDisabled() {
        String elementClass = element.getAttribute("class");
        return elementClass.toLowerCase().contains("disabled");
    }
}