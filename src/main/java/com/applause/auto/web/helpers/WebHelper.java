package com.applause.auto.web.helpers;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class WebHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  /**
   * Returns the test environment (defaulting to uat)
   *
   * @return testEnvironment
   */
  public static String getTestEnvironment() {
    return System.getProperty("environment", "uat").toLowerCase();
  }

  public static boolean useRetry() {
    return !System.getProperty("useRetry", "yes").toLowerCase().equals("no");
  }

  /**
   * Returns a unique value based on given name and timestamp
   *
   * @param name
   * @return String
   */
  public static String getTimestamp(String name) {
    Date date = new Date();
    String time = Long.toString(date.getTime());
    return name + time;
  }

  /**
   * Returns a timestamp
   *
   * @return time
   */
  public static String returnTimestamp() {
    Date date = new Date();
    String time = Long.toString(date.getTime());
    return time;
  }

  /**
   * Clicks on a given element
   *
   * @param webElement
   */
  public static void jsClick(final WebElement webElement) {
    final JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript("arguments[0].click();", webElement);
  }

  /**
   * Selects an option based on an exact text
   *
   * @param element as WebElement
   * @param item as text
   */
  public static void jsSelect(WebElement element, String item) {
    JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript(
        "const textToFind = '"
            + item
            + "';"
            + "const dd = arguments[0];"
            + "dd.selectedIndex = [...dd.options].findIndex (option => option.text === textToFind);",
        element);
  }

  /**
   * Selects an option based on a partial text
   *
   * @param element as WebElement
   * @param text as text
   */
  public static void jsSelectByContainedText(WebElement element, String text) {
    JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript(
        "const textToFind = '"
            + text
            + "';"
            + "const dd = arguments[0];"
            + "dd.selectedIndex = [...dd.options].findIndex (option => option.text.includes(textToFind));",
        element);
  }

  /**
   * Selects an option based on a given value
   *
   * @param element as WebElement
   * @param value as value
   */
  public static void jsSelectByValue(WebElement element, String value) {
    JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript(
        "const valueToFind = '"
            + value
            + "';"
            + "const dd = arguments[0];"
            + "dd.selectedIndex = [...dd.options].findIndex (option => option.value === valueToFind);",
        element);
  }

  /**
   * Shift window view.
   *
   * @param yOffset the y offset
   */
  public static void shiftWindowView(final int yOffset) {
    final JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript(String.format("window.scrollBy(0, %s)", yOffset), "");
  }

  /**
   * Scrolls to the given web element
   *
   * @param element
   */
  public static void scrollToElement(WebElement element) {
    JavascriptExecutor jse = (JavascriptExecutor) SdkHelper.getDriver();
    long windowHeight = (long) jse.executeScript("return window.innerHeight");

    // scrolls element to view...
    jse.executeScript("arguments[0].scrollIntoView(true);", element);

    // ... and then scroll half-way up the page, to ensure
    // the element is not obscured by another element.
    jse.executeScript("window.scrollBy(0, " + -(windowHeight / 2) + ")", "");
  }

  public static boolean isDesktop() {
    return !SdkHelper.getEnvironmentHelper().isMobileAndroid()
        && !SdkHelper.getEnvironmentHelper().isMobileIOS()
        && !getDriverConfig().contains("mobile");
  }

  public static String getDriverConfig() {
    return System.getProperty("driverConfig", "chrome");
  }

  /**
   * Cleans the specified string value - removes any unnecessary white-spaces and line breaks.
   *
   * @param stringValue
   * @return cleaned string
   */
  public static String cleanString(String stringValue) {
    return stringValue.trim().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\u00a0", "").replaceAll("\t", "");
  }

  /**
   * Check if element is displayed
   *
   * @param element
   * @return boolean
   */
  public static boolean isDisplayed(BaseElement element) {
    return element.exists() && element.isDisplayed();
  }
}
