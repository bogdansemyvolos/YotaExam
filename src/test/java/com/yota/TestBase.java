package com.yota;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.yota.util.PropertyLoader;
import com.yota.util.Browser;

public class TestBase extends BaseEntity {
    private static final String SCREENSHOT_FOLDER = "target/screenshots/";
    private static final String SCREENSHOT_FORMAT = ".png";

    protected WebDriver webDriver;
    protected String websiteUrl;
    protected Browser browser;
    protected Process process;

    @BeforeSuite
    public void startApp() {
        Runtime rt = Runtime.getRuntime();
        try {
            process = rt.exec("java -jar src/main/resources/test-slider-1.0.0-SNAPSHOT.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public void init() {
        websiteUrl = PropertyLoader.loadProperty("site.url");
        browser = Browser.getInstance();
        browser.windowMaximise();
        webDriver = browser.getDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (webDriver != null) {
            browser.exit();
        }
        stopApp();
    }

    @AfterMethod
    public void setScreenshot(ITestResult result) {
        if (!result.isSuccess()) {
            try {
                WebDriver returned = new Augmenter().augment(webDriver);
                if (returned != null) {
                    File f = ((TakesScreenshot) returned).getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(f,
                                new File(SCREENSHOT_FOLDER + result.getName() + SCREENSHOT_FORMAT));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ScreenshotException se) {
                se.printStackTrace();
            }
        }
    }

    public void stopApp() {
        process.destroy();
    }


    protected String formatLogMsg(String message) {
        return message;
    }
}
