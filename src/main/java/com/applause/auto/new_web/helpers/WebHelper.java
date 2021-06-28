package com.applause.auto.new_web.helpers;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.lang.invoke.MethodHandles;

public class WebHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public static void hoverByAction(BaseElement element) {
    logger.info("Hover over...");
    Actions actions = new Actions(SdkHelper.getDriver());
    actions.moveToElement(element.getWebElement()).perform();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  public static void scrollToElement(BaseElement element) {
    scrollToElement(element.getWebElement());
  }

  public static void scrollToElement(WebElement element) {
    logger.info("Scrolling to element...");
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

  public static boolean isMobile() {
    return SdkHelper.getEnvironmentHelper().isMobileAndroid()
        || SdkHelper.getEnvironmentHelper().isMobileIOS();
  }

  public static void switchToIFrame(BaseElement frame) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(frame).present());
    SdkHelper.getDriver().switchTo().frame(frame.getWebElement());
    SdkHelper.getSyncHelper().sleep(3000); // Wait until iFrame is ready
  }

  public static void switchToIFrameAndSetData(BaseElement frame, TextBox element, String data) {
    switchToIFrame(frame);

    SdkHelper.getSyncHelper().wait(Until.uiElement(element).clickable());
    element.sendKeys(data);

    SdkHelper.getDriver().switchTo().defaultContent();
    SdkHelper.getSyncHelper().sleep(3000); // Waits for iFrame switch ends
  }

  public static String getRandomMail() {
    long uniq = System.currentTimeMillis();
    return String.format("test_automation_%s@gmail.com", uniq);
  }
}
