package com.yota.tests;

import com.yota.BaseEntity;
import com.yota.elements.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * Abstract class representation of a BasePage in the UI. BasePage object pattern
 *
 */
public abstract class BasePage extends BaseEntity{

	protected WebDriver webDriver;

	protected By		titleLocator;	// локатор элемента, по которому определяется открытие формы
	protected String	title;			// заголовок формы, например, "Логин"
	protected String	name;			// полное имя формы, которое выводится в лог, например, "Форма 'Логин'"

	/**
	 * Основной конструктор
	 * @param titleLocator Локатор заголовка формы
	 * @param title Имя формы
	 */
	protected BasePage(final By titleLocator, final String title) {
		init(titleLocator, title);
		assertIsOpen();
	}

	/**
	 * Инициализация полей класса значениями, переданными в конструктор
	 */
	protected void init(final By titleLocator, final String title) {
		this.titleLocator = titleLocator;
		this.title = title;
		this.name = String.format("Страница '%1$s'", this.title);
	}

	/**
	 * Проверка открытия формы Если форма не открыта, тест прекращает свою работу
	 */
	public void assertIsOpen() {
		Label elem = new Label(titleLocator, title);
		try {
			elem.waitForIsElementPresent();
			info(String.format("Страница '%1$s' появилась", this.title));
		} catch (Throwable e) {
			fatal(String.format("Страница '%1$s' не отобразилась", this.title));
		}
	}

	/** Формотирование логов
	 * @param message message
	 * @return вывод отформатированных логов
	 */
	protected String formatLogMsg(String message) {
		return message;
	}


}
