package com.applause.auto.new_web.helpers;

import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.lang.invoke.MethodHandles;

public class WebHelper extends SdkHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public static void hoverByAction(BaseElement element) {
    logger.info("Hover over...");
    Actions actions = new Actions(getDriver());
    actions.moveToElement(element.getWebElement()).perform();
    getSyncHelper().sleep(1000); // Wait for action
  }

  public static void scrollToElement(BaseElement element) {
    scrollToElement(element.getWebElement());
  }

  public static void scrollToElement(WebElement element) {
    logger.info("Scrolling to element...");
    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    long windowHeight = (long) jse.executeScript("return window.innerHeight");

    // scrolls element to view...
    jse.executeScript("arguments[0].scrollIntoView(true);", element);

    // ... and then scroll half-way up the page, to ensure
    // the element is not obscured by another element.
    jse.executeScript("window.scrollBy(0, " + -(windowHeight / 2) + ")", "");
  }
}
