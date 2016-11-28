package com.yota.util;

import com.yota.webdriver.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.NamingException;

/*
 * Bean representing a browser. It contains name, version and platform fields.
 *
 */
public class Browser {

	private static String name;
	private static String version;
	private static String platform;

	private static String timeout;
	private static Browser instance;
	private static RemoteWebDriver driver;

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getPlatform() {
		return platform;
	}

	/**
	 * Базовый конструктор
	 */
	private Browser() {
		Logger.getInstance().info("Запускается браузер...");
	}

	/**
	 * Инициализирует и возвращает объект браузера
	 */
	public static Browser getInstance() {
		if (instance == null) {
			instance = new Browser();
			instance.initBrowser();
			driver = WebDriverFactory.getInstance(instance.getName());
			Logger.getInstance().info(String.format("Браузер '%s' готов", name));
		}
		return instance;
	}


	/**
	 * Закрывает браузер
	 */
	public void exit() {
		try {
			driver.quit();
			Logger.getInstance().info("Браузер закрывается...");
		} catch (Exception e) {
		} finally {
			instance = null;
		}
	}

	/**
	 * Максимизирует локно браузера
	 */
	public void windowMaximise() {
		driver.manage().window().maximize();
	}

	/**
	 * Инициализация
	 */
	private void initBrowser(){
		name = PropertyLoader.loadProperty("browser.name");
		version = PropertyLoader.loadProperty("browser.version");
		platform = PropertyLoader.loadProperty("browser.platform");
		timeout = PropertyLoader.loadProperty("timeout");
	}

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public String getTimeout() {
		return timeout;
	}

	/**
	 * Ожидает загрузку страницы (по Javascript readyState)
	 */
	public void waitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(getTimeout()));
		try {
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					if (!(driver instanceof JavascriptExecutor)) {
						return true;
					}
					Object result = ((JavascriptExecutor) driver)
							.executeScript("return document['readyState'] ? 'complete' == document.readyState : true");
					if (result != null && result instanceof Boolean && (Boolean) result) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			System.out.println("Не удалось дождаться загрузки страницы");
		}
	}

	/**
	 * Ожидает окончания AJAX запросов
	 */
	public void waitForAjaxRequests() {
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(getTimeout()));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(org.openqa.selenium.WebDriver driver) {
				return (Boolean)((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
			}
		});
	}
}