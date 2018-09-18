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

    public String returnTimestamp() {
        Date date = new Date();
        String time = Long.toString(date.getTime());
        return time;
    }

    public void jsClick(final WebElement webElement) {
        final JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", webElement);
    }

    public void jsSelect(WebElement element, String item) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("const textToFind = '" + item + "';" +
                "const dd = arguments[0];" +
                "dd.selectedIndex = [...dd.options].findIndex (option => option.text === textToFind);", element);
    }

    public void jsSelectByContainedText(WebElement element, String text) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("const textToFind = '" + text + "';" +
                "const dd = arguments[0];" +
                "dd.selectedIndex = [...dd.options].findIndex (option => option.text.includes(textToFind));", element);
    }

	public void jsSelectByValue(WebElement element, String value) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("const valueToFind = '" + value + "';" + "const dd = arguments[0];"
				+ "dd.selectedIndex = [...dd.options].findIndex (option => option.value === valueToFind);", element);
	}
}
