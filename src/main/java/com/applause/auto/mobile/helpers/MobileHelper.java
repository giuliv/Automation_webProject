package com.applause.auto.mobile.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileApp;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Picker;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

public class MobileHelper extends SdkHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  private static Dimension deviceSize;

  static {
    refreshDeviceSize();
  }

  private static AppiumDriver getMobileDriver() {
    return (AppiumDriver) getDriver();
  }

  /** Activates the app */
  public static void activateApp() {
    logger.info("Activate application");
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      getMobileDriver().activateApp(MobileApp.ANDROID_PACKAGE_ID);
    }
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      getSyncHelper().sleep(5000);
      hideKeyboardIOSByPressDone();
      getMobileDriver().activateApp(MobileApp.IOS_BUNDLE_ID);
    }
    getSyncHelper().sleep(3000);
  }

  public static void initMobileBrowser() {
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      getSyncHelper().sleep(5000);
      String currentActivity = ((AndroidDriver) getDriver()).currentActivity();
      boolean isSamsungBrowserStarted =
          currentActivity.equals("com.sec.android.app.sbrowser")
              || currentActivity.contains("help_intro.HelpIntroActivity");
      boolean isChromeBrowserStarted =
          currentActivity.equals("com.android.chrome")
              || currentActivity.contains("ChromeTabbedActivity");
      boolean isIntentActionStarted =
          currentActivity.equals("com.android.internal.app.ResolverActivity");

      logger.info("Current activity: " + currentActivity);

      if (isIntentActionStarted) {
        ((AppiumDriver) getDriver())
            .findElementByXPath("//android.widget.TextView[@text='Chrome']")
            .click();
        ((AppiumDriver) getDriver()).findElementById("android:id/button_once").click();
        return;
      } else if (isSamsungBrowserStarted) {
        throw new RuntimeException("Only Samsung browser suggested. Exiting");
        //        ((AppiumDriver) getDriver())
        //
        // .findElementById("com.sec.android.app.sbrowser:id/help_intro_legal_optional_checkbox")
        //            .click();
        //        ((AppiumDriver) getDriver())
        //
        // .findElementById("com.sec.android.app.sbrowser:id/help_intro_legal_agree_button")
        //            .click();
        //        getSyncHelper().sleep(10000);
      } else if (isChromeBrowserStarted) {
        return;
      }
      Assert.assertTrue(false, "Something happens during browser init");
    }
  }

  /** Hide keyboard ios by press done. */
  public static void hideKeyboardIOSByPressDone() {
    logger.info("Hiding keyboard.");
    getMobileDriver().findElement(By.xpath("//*[@name='Done']")).click();
  }

  public static void refreshDeviceSize() {
    deviceSize = getDriver().manage().window().getSize();
  }

  /**
   * Performs a swipe for the given swipe count
   *
   * @param swipeCount
   */
  public static void swipeWithCount(SwipeDirection swipeDirection, int swipeCount) {
    logger.info("Scrolling to bottom of page");
    for (int i = 0; i < swipeCount; i++) {
      getDeviceControl().swipeAcrossScreenWithDirection(swipeDirection);
      getSyncHelper().sleep(3000);
    }
  }

  /**
   * Scroll down to element close to the middle
   *
   * @param element
   * @param retries
   */
  public static void scrollDownToElementCloseToMiddle(BaseElement element, int retries) {
    logger.info("Scrolling to bottom of page close to middle");
    refreshDeviceSize();
    int count = 0;
    while (!element.isDisplayed() && (count < retries)) {
      scrollDownCloseToMiddleAlgorithm();
      getSyncHelper().sleep(2000);
      count++;
    }
  }

  /**
   * public void tap on element with relative offset
   *
   * @param element BaseDeviceControl element
   * @param xRelativeOffset offset from element center to the right in percents of element width.
   *     Value should be from 0 to 1
   * @param yRelativeOffset offset from element center to the top in percents of element height.
   *     Value should be from 0 to 1
   */
  public static void tapOnElementWithOffset(
      BaseElement element, double xRelativeOffset, double yRelativeOffset) {
    int xCenter = element.getMobileElement().getCenter().x;
    int yCenter = element.getMobileElement().getCenter().y;
    logger.info(
        String.format(
            "Add this offset to click: x = [%f] , y = [%f]", xRelativeOffset, yRelativeOffset));
    logger.info(String.format("Element center is: x = [%d] , y = [%d]", xCenter, yCenter));
    // Dimension dimension = element.getDimension();
    int xAbsoluteOffset = (int) (xCenter + xRelativeOffset);
    int yAbsoluteOffset = (int) (yCenter + yRelativeOffset);
    logger.info(
        String.format(
            "Clicking with offset: x = [%d] , y = [%d]", xAbsoluteOffset, yAbsoluteOffset));
    getDeviceControl().tapScreenCoordinates(xAbsoluteOffset, yAbsoluteOffset);
  }

  public static void scrollDownCloseToMiddleAlgorithm() {
    double pStartY = 0;
    double pEndY = 0;
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      pStartY = 0.5;
      pEndY = 0.3;
    } else { // Android scrolls faster so the start and end must be gentler
      pStartY = 0.50;
      pEndY = 0.30;
    }
    scrollDownAlgorithm(0.1, pStartY, pEndY);
  }

  public static void scrollUpCloseToMiddleAlgorithm() {
    double pStartY = 0;
    double pEndY = 0;
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      pStartY = 0.5;
      pEndY = 0.7;
    } else { // Android scrolls faster so the start and end must be gentler
      pStartY = 0.50;
      pEndY = 0.70;
    }
    scrollDownAlgorithm(0.1, pStartY, pEndY);
  }

  private static void scrollDownAlgorithm(double startX, double pStartY, double pEndY) {
    Dimension size = deviceSize;
    int startY = (int) (size.getHeight() * pStartY);
    int endY = (int) (size.getHeight() * pEndY);
    startX = (int) (size.getWidth() * startX);
    logger.info("Swiping startX:" + startX + " startY:" + pStartY + " end Y:" + pEndY);
    try {
      new TouchAction(getMobileDriver())
          .press(PointOption.point((int) startX, startY))
          .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
          .moveTo(PointOption.point((int) startX, endY))
          .release()
          .perform();
    } catch (WebDriverException wex) {
      logger.warn("Swipe cause error, probably nothing to swipe: " + wex.getMessage());
    }
  }

  /**
   * Tap.
   *
   * @param x the x
   * @param y the y
   */
  public static void tap(double x, double y) {
    MobileHelper.refreshDeviceSize();

    int width = deviceSize.getWidth();
    int height = deviceSize.getHeight();
    double tapX = x;
    double tapY = y;
    if (0 < x && x < 1) {
      tapX = width * x;
    }

    if (0 < y && y < 1) {
      tapY = height * y;
    }

    logger.debug(String.format("Tapping at %s, %s", (int) tapX, (int) tapY));
    tapByCoordinates((int) tapX, (int) tapY);
  }

  /**
   * Tapping by coordinates on mobile element center
   *
   * @param element
   */
  public static void tapByCoordinatesOnElementCenter(BaseElement element) {
    Point location = element.getLocation();
    Dimension dimension = element.getDimension();
    tapByCoordinates(location.x + dimension.getWidth() / 2, location.y + dimension.getHeight() / 2);
  }

  /**
   * Tapping by coordinates on mobile screen
   *
   * @param tapX
   * @param tapY
   */
  public static void tapByCoordinates(int tapX, int tapY) {
    new TouchAction(getMobileDriver()).tap(PointOption.point(tapX, tapY)).perform();
  }

  /**
   * Sets picker value reverse.
   *
   * @param value the value
   * @param element the element
   */
  public static void setPickerValueReverse(String value, Picker element) {
    setPickerValueBasic(value, element, "previous");
  }

  /**
   * Is attribtue present boolean.
   *
   * @param element the element
   * @param attribute the attribute
   * @return the boolean
   */
  public static boolean isAttribtuePresent(MobileElement element, String attribute) {
    getSyncHelper().sleep(5000);
    Boolean result = false;
    try {
      String value = element.getAttribute(attribute);
      if (value != null) {
        result = true;
      }
    } catch (Exception e) {
    }

    return result;
  }

  /**
   * Sets picker value basic.
   *
   * @param value the value
   * @param element the element
   * @param order the order
   */
  public static void setPickerValueBasic(String value, Picker element, String order) {
    int loopCounter = 0;
    String pickerWheel = element.getMobileElement().getText();
    MobileElement elem = element.getMobileElement();
    logger.debug("Initial picker wheel value: " + pickerWheel);
    logger.debug("Expected value to: " + value);
    while (!pickerWheel.contentEquals(value.trim()) && loopCounter < 30) {
      logger.debug("Initial picker wheel value: " + pickerWheel);
      logger.debug("Sending value to: " + value);
      logger.debug("Loop #" + loopCounter);
      if (!SdkHelper.getEnvironmentHelper().isMobileIOS()) {
        if (value.matches("\\d+")) {
          elem.click();
          elem.sendKeys(Keys.BACK_SPACE + value);
          elem.sendKeys(Keys.BACK_SPACE + value);
          ((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));

        } else {
          elem.sendKeys(Keys.BACK_SPACE);
          elem.click();
          elem.sendKeys(value);
          ((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
        }
      } else {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("offset", 0.1);
        params.put("element", elem.getId());
        try {
          js.executeScript("mobile: selectPickerWheelValue", params);
        } catch (WebDriverException wex) {
          logger.error("Exception thrown during picker wheel scroll");
        }
      }
      loopCounter++;
      pickerWheel = elem.getText();
      logger.debug("Updated picker wheel value: " + pickerWheel);
    }
  }

  public static void tapAndroidDeviceBackButton() {
    logger.info("Tapping on device back button");
    AndroidDriver androidDriver = (AndroidDriver) getMobileDriver();
    androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
  }

  public static void scrollDownHalfScreen(int swipeLimit) {
    refreshDeviceSize();
    logger.info("Scrolling down half a screen");
    scrollDownAlgorithm(0.1, 0.6, 0.4);
  }

  public static void scrollUpHalfScreen(int swipeLimit) {
    refreshDeviceSize();
    logger.info("Scrolling down half a screen");
    scrollDownAlgorithm(0.1, 0.4, 0.6);
  }

  public static void swipeAcrossScreenCoordinates(
      double startX, double startY, double endX, double endY, long millis) {
    logger.info(String.format("Swiping from [%s, %s] to [%s, %s].", startX, startY, endX, endY));
    Dimension size = getMobileDriver().manage().window().getSize();

    int startX_ = (int) (size.getWidth() * startX);
    int startY_ = (int) (size.getHeight() * startY);
    int endX_ = (int) (size.getWidth() * endX);
    int endY_ = (int) (size.getHeight() * endY);

    logger.info(
        "Scrolling from: [" + startX_ + " , " + startY_ + "] to [" + endX_ + " , " + endY_ + "]");

    PointOption<?> startPoint = PointOption.point(startX_, startY_);
    PointOption<?> endPoint = PointOption.point(endX_, endY_);
    WaitOptions time = WaitOptions.waitOptions(Duration.ofMillis(millis));
    (new TouchAction(getMobileDriver()))
        .press(startPoint)
        .waitAction(time)
        .moveTo(endPoint)
        .release()
        .perform();
  }

  /** Scrolling down view until 'element' will be available on the screen */
  public static void scrollUntilElementSectionWillBeAvailableOnTheScreenInWebView(
      BaseElement element, String elementName, int maxSwipingAttempts) {
    int currentSwipingAttempts = 1;
    getSyncHelper().sleep(10000);
    int screenHeight = 0;
    int screenWidth = 0;
    logger.info("Scrolling down to element: ", elementName);
    while (currentSwipingAttempts <= maxSwipingAttempts) {
      try {
        getSyncHelper().wait(Until.uiElement(element).present().setTimeout(Duration.ofSeconds(5)));
        logger.info(elementName + " is present");
        return;
      } catch (UnsupportedCommandException uce) {
        logger.info("UnsupportedCommandException catched");
        ((AppiumDriver) getDriver()).context("NATIVE_APP");
        screenHeight = getDeviceControl().getScreenSize().getHeight();
        screenWidth = getDeviceControl().getScreenSize().getWidth();
        getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
        getSyncHelper().sleep(5000);
        currentSwipingAttempts++;
        logger.info("XML Dump: ", getDriver().getPageSource());
      } catch (WebDriverException e) {
        logger.info(elementName + " is not present");
        logger.info("Swipe attempt: " + currentSwipingAttempts);
        getDeviceControl()
            .swipeAcrossScreenCoordinates(
                screenWidth / 2,
                (int) (screenHeight * 0.75),
                screenWidth / 2,
                (int) (screenHeight * 0.4),
                1000);
        getSyncHelper().sleep(5000);
        currentSwipingAttempts++;
        logger.info("XML Dump: ", getDriver().getPageSource());
      }
    }
  }

  public static String getElementTextAttribute(BaseElement baseElement) {
    String textAttribute = "text";
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      textAttribute = "value";
    }
    return baseElement.getAttributeValue(textAttribute);
  }

  public static BufferedImage getMobileScreenshotBufferedImage() {
    byte[] screenShot = getMobileDriver().getScreenshotAs(OutputType.BYTES);
    if (ArrayUtils.isNotEmpty(screenShot)) {
      ByteArrayInputStream bais = new ByteArrayInputStream(screenShot);
      try {
        return ImageIO.read(bais);
      } catch (IOException e) {
        logger.info("Error reading input stream for screenshot image");
        throw new RuntimeException(e);
      }
    } else throw new IllegalStateException("Was not able to get mobile screenshot");
  }

  public static RGB getMobileElementColour(MobileElement element) {

    org.openqa.selenium.Point point = element.getCenter();

    Dimension dimension = getDriver().manage().window().getSize();

    double centerX0 = (double) point.getX() / (double) dimension.width;
    File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

    BufferedImage image = null;
    try {
      image = ImageIO.read(scrFile);
    } catch (IOException e) {
      logger.error("Error during image reading");
    }
    int screenHeight = getDeviceControl().getScreenSize().height;
    int screenShotHeight = image.getHeight();
    logger.debug("Screen height = " + screenHeight);
    logger.debug("Screenshot height = " + screenShotHeight);
    double centerY0 =
        (double) point.getY()
            / (SdkHelper.getEnvironmentHelper().isMobileIOS()
                ? (double) dimension.height
                : ((double) dimension.height
                    + screenShotHeight
                    - screenHeight)); // correction due to top nav bar

    // Getting pixel color by position x and y
    int clr =
        image.getRGB((int) (image.getWidth() * centerX0), ((int) (image.getHeight() * centerY0)));
    RGB result = new RGB(clr);

    logger.debug("Red Color value = " + result.getRed());
    logger.debug("Green Color value = " + result.getGreen());
    logger.debug("Blue Color value = " + result.getBlue());
    return result;
  }

  public static boolean isIosCheckboxChecked(MobileElement element) {
    logger.info("Checking if checkbox checked by color");
    return MobileHelper.getMobileElementColour(element).getBlue() != 255;
  }

  /**
   * Checks whether an element is displayed.
   *
   * @param element
   * @return true, if the element is displayed
   */
  public static boolean isDisplayed(BaseElement element) {
    try {
      return getQueryHelper().findElement(element.getLocator()).isDisplayed();
    } catch (Exception ex) {
      return false;
    }
  }

  public static void scrollElementIntoView(BaseElement element) {
    logger.info("Trying to get first element into view area");
    try {
      element.initialize();
    } catch (NoSuchElementException nse) {
      IntStream.range(1, 6).forEach(i -> scrollUpCloseToMiddleAlgorithm());
    }
    int screenHeight = getDeviceControl().getScreenSize().height;
    IntStream.range(1, 6)
        .filter(
            i -> {
              try {
                element.initialize();
                Dimension dimension = element.getDimension();
                Point location = element.getLocation();
                logger.info(
                    String.format("Location [%s] height [%s]", location.y, dimension.height));
                if (location.y + dimension.height + Constants.BOTTOM_BORDER_SIZE > screenHeight) {
                  logger.info("Element is on bottom");
                  swipeAcrossMiddleScreenUp();
                  return false;
                } else {
                  if (location.y < screenHeight / 2.5) {
                    logger.info("Element is on top");
                    swipeAcrossMiddleScreenDown();
                    return false;
                  } else {
                    logger.info("Element is in visible part, continue");
                    return true;
                  }
                }
              } catch (NoSuchElementException nse) {
                swipeAcrossMiddleScreenUp();
              }
              return false;
            })
        .findFirst();
  }

  public static void swipeAcrossMiddleScreenUp() {
    Dimension size = getDeviceControl().getScreenSize();
    logger.debug(String.format("Screen size is [%d x %d].", size.width, size.height));
    swipeAcrossScreenCoordinates(0.5, 0.5, 0.5, 0.3, 2000L);
  }

  public static void swipeAcrossMiddleScreenDown() {
    Dimension size = getDeviceControl().getScreenSize();
    logger.debug(String.format("Screen size is [%d x %d].", size.width, size.height));
    swipeAcrossScreenCoordinates(0.5, 0.5, 0.5, 0.8, 2000L);
  }
}
