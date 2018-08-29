package com.applause.auto.pageframework.helpers;

import com.applause.auto.framework.pageframework.util.drivers.DriverWrapperManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.invoke.MethodHandles;
import java.util.Date;

public class WebHelper {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().getClass());

    private static WebDriver getDriver() {
        return (WebDriver) DriverWrapperManager.getInstance().getPrimaryDriverWrapper().getDriver();
    }

    public String getTimestamp(String name) {
        Date date = new Date();
        String time = Long.toString(date.getTime());
        return name + time;
    }

    public void jsClick(final WebElement webElement) {
        final JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", webElement);
    }
}
