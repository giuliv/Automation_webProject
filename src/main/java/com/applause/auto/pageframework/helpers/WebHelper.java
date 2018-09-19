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

    /**
     * Returns a unique value based on given name and timestamp
     * @param name
     * @return String
     */
    public String getTimestamp(String name) {
        Date date = new Date();
        String time = Long.toString(date.getTime());
        return name + time;
    }

    /**
     * Returns a timestamp
     * @return time
     */
    public String returnTimestamp() {
        Date date = new Date();
        String time = Long.toString(date.getTime());
        return time;
    }

    /**
     * Clicks on a given element
     * @param webElement
     */
    public void jsClick(final WebElement webElement) {
        final JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", webElement);
    }

    /**
	 * Selects an option based on an exact text
	 * @param element as WebElement
	 * @param item as text
	 */
    public void jsSelect(WebElement element, String item) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("const textToFind = '" + item + "';" +
                "const dd = arguments[0];" +
                "dd.selectedIndex = [...dd.options].findIndex (option => option.text === textToFind);", element);
    }

    /**
	 * Selects an option based on a partial text
	 * @param element as WebElement
	 * @param text as text
	 */
    public void jsSelectByContainedText(WebElement element, String text) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("const textToFind = '" + text + "';" +
                "const dd = arguments[0];" +
                "dd.selectedIndex = [...dd.options].findIndex (option => option.text.includes(textToFind));", element);
    }

    /**
	 * Selects an option based on a given value
	 * @param element as WebElement
	 * @param value as value
	 */
	public void jsSelectByValue(WebElement element, String value) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("const valueToFind = '" + value + "';" + "const dd = arguments[0];"
				+ "dd.selectedIndex = [...dd.options].findIndex (option => option.value === valueToFind);", element);
	}
}
