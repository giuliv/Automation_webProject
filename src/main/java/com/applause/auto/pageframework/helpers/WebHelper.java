package com.applause.auto.pageframework.helpers;

import java.lang.invoke.MethodHandles;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applause.auto.framework.pageframework.util.TestHelper;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapperManager;

public class WebHelper {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().getClass());

	private static WebDriver getDriver() {
		return (WebDriver) DriverWrapperManager.getInstance().getPrimaryDriverWrapper().getDriver();
	}

	/**
	 * Returns a unique value based on given name and timestamp
	 * 
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
	 * 
	 * @return time
	 */
	public String returnTimestamp() {
		Date date = new Date();
		String time = Long.toString(date.getTime());
		return time;
	}

	/**
	 * Clicks on a given element
	 * 
	 * @param webElement
	 */
	public void jsClick(final WebElement webElement) {
		final JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", webElement);
	}

	/**
	 * Selects an option based on an exact text
	 * 
	 * @param element
	 *            as WebElement
	 * @param item
	 *            as text
	 */
	public void jsSelect(WebElement element, String item) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript(
				"const textToFind = '" + item + "';" + "const dd = arguments[0];"
						+ "dd.selectedIndex = [...dd.options].findIndex (option => option.text === textToFind);",
				element);
	}

	/**
	 * Selects an option based on a partial text
	 * 
	 * @param element
	 *            as WebElement
	 * @param text
	 *            as text
	 */
	public void jsSelectByContainedText(WebElement element, String text) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript(
				"const textToFind = '" + text + "';" + "const dd = arguments[0];"
						+ "dd.selectedIndex = [...dd.options].findIndex (option => option.text.includes(textToFind));",
				element);
	}

	/**
	 * Selects an option based on a given value
	 * 
	 * @param element
	 *            as WebElement
	 * @param value
	 *            as value
	 */
	public void jsSelectByValue(WebElement element, String value) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript(
				"const valueToFind = '" + value + "';" + "const dd = arguments[0];"
						+ "dd.selectedIndex = [...dd.options].findIndex (option => option.value === valueToFind);",
				element);
	}

	/**
	 * Shift window view.
	 *
	 * @param yOffset
	 *            the y offset
	 */
	public static void shiftWindowView(final int yOffset) {
		final JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript(String.format("window.scrollBy(0, %s)", yOffset), "");
	}

	public static WebElement waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 10);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitForLoad() {
		LOGGER.debug("======> Wait for page load");
		long startTime = TestHelper.getCurrentGMT6Time();

		try {
			waitForDocument(120);
			// waitForJquery(120);
		} catch (Throwable t) {
			LOGGER.warn(t.getMessage());
			LOGGER.warn("Page not loaded properly. Continue....");
		}
		LOGGER.debug("======> Wait for page load comleted in: " + (TestHelper.getCurrentGMT6Time() - startTime));
	}

	public static void waitForJquery(int seconds) {
		LOGGER.info("jQuery synchronization wait for " + seconds + " seconds");
		long start = System.currentTimeMillis();
		long end = start + seconds * 1000;
		boolean exitCond = false;
		while ((System.currentTimeMillis() < end) && !exitCond) {
			try {
				exitCond = (boolean) ((JavascriptExecutor) getDriver())
						.executeScript("return !!window.$ && window.$.active == 0", new Object[0]);
				LOGGER.info("waitForJquery script returned " + exitCond);
			} catch (WebDriverException we) {
				LOGGER.info("jQuery throw exception. Status undefined: " + we.getMessage());
			}
		}
		if (!exitCond) {
			LOGGER.info("waitForDocument timed out.");
		}
	}

	/**
	 * Wait for document.
	 *
	 * @param seconds
	 *            the seconds
	 */
	public static void waitForDocument(int seconds) {
		LOGGER.info("Page load synchronization wait for " + seconds + " seconds");
		long start = System.currentTimeMillis();
		long end = start + seconds * 1000;
		boolean exitCond = false;
		while ((System.currentTimeMillis() < end) && !exitCond) {
			try {
				exitCond = ((JavascriptExecutor) getDriver()).executeScript("return document.readyState")
						.equals("complete");
			} catch (WebDriverException we) {
				LOGGER.info("Page load throw exception. Status undefined" + we.getMessage());
			}
		}
		if (!exitCond) {
			LOGGER.info("waitForDocument timed out.");
		}
	}

}
