package com.yota.webdriver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

	/* Browsers constants */
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String INTERNET_EXPLORER = "ie";
    public static final String SAFARI = "safari";
        
        private WebDriverFactory(){}


	/*
	 * Factory method to return a WebDriver instance given the browser to hit
	 * @param browser : String representing the local browser to hit
	 * @return WebDriver instance
	 */
	public static RemoteWebDriver getInstance(String browser) {

		RemoteWebDriver webDriver = null;

		if (CHROME.equals(browser)) {
			setChromeDriver();
			webDriver = new ChromeDriver();
		} else if (FIREFOX.equals(browser)) {

			FirefoxProfile ffProfile = new FirefoxProfile();
			webDriver = new FirefoxDriver(ffProfile);

		} else if (INTERNET_EXPLORER.equals(browser)) {
            isSupportedPlatform(browser);
			webDriver = new InternetExplorerDriver();

		} else if (SAFARI.equals(browser)) {
            isSupportedPlatform(browser);
            webDriver = new SafariDriver();

    } else  {
      webDriver = new RemoteWebDriver(DesiredCapabilities.android());

    }
		return webDriver;
	}

	/*
	 * Helper method to set ChromeDriver location into the right ststem property
	 */
	private static void setChromeDriver() {
		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String chromeBinary = "src/main/resources/drivers/chrome/chromedriver"
				+ (os.equals("win") ? ".exe" : "");
		System.setProperty("webdriver.chrome.driver", chromeBinary);
	}

    private static void isSupportedPlatform(String browser) {
        boolean is_supported = true;
        Platform current = Platform.getCurrent();
		if (INTERNET_EXPLORER.equals(browser)) {
            is_supported = Platform.WINDOWS.is(current) || Platform.VISTA.is(current);
        } else if (SAFARI.equals(browser)) {
            is_supported = Platform.MAC.is(current) || Platform.WINDOWS.is(current);
        }
        assert is_supported : "Platform is not supported by " + browser.toUpperCase() + " browser";
    }
}
