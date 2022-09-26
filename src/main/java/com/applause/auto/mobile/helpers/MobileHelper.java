package com.applause.auto.mobile.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MobileApp;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.ContextManager;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.views.IosSettingsView;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Picker;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class MobileHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  private static Dimension deviceSize;

  static {
    refreshDeviceSize();
  }

  private static AppiumDriver getMobileDriver() {
    return (AppiumDriver) SdkHelper.getDriver();
  }

  private static IOSDriver getIosDriver() {
    return (IOSDriver) SdkHelper.getDriver();
  }

  private static AndroidDriver getAndroidDriver() {
    return (AndroidDriver) SdkHelper.getDriver();
  }

  /** Activates the app */
  public static void activateApp() {
    logger.info("Activate application");
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      getAndroidDriver().activateApp(MobileApp.ANDROID_PACKAGE_ID);
    } else if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.getSyncHelper().sleep(5000);
      hideKeyboardIOSByPressDone();
      getIosDriver().activateApp(MobileApp.IOS_BUNDLE_ID);
    }
    SdkHelper.getSyncHelper().sleep(3000);
  }

  public static void initMobileBrowser() {
    if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
      SdkHelper.getSyncHelper().sleep(5000);
      String currentActivity = ((AndroidDriver) SdkHelper.getDriver()).currentActivity();
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
        getAndroidDriver()
            .findElement(By.xpath("//android.widget.TextView[@text='Chrome']"))
            .click();
        getAndroidDriver().findElement(By.id("android:id/button_once")).click();
        return;
      } else if (isSamsungBrowserStarted) {
        throw new RuntimeException("Only Samsung browser suggested. Exiting");
      } else if (isChromeBrowserStarted) {
        return;
      }
      Assert.assertTrue(false, "Something happens during browser init");
    }
  }

  /** Hide keyboard ios by press done. */
  public static void hideKeyboardIOSByPressDone() {
    logger.info("Hiding keyboard.");
    try {
      getIosDriver().findElement(By.xpath("//*[@name='Done']")).click();
    } catch (Throwable throwable) {

    }
  }

  public static void hideKeyboard() {
    logger.info("Hide keyboard by standard method");
    try {
      getAndroidDriver().hideKeyboard();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

    if (!isAndroid()) {
      try {
        logger.info("Hide keyboard by clicking done");
        getIosDriver().findElement(By.id("Done")).click();
      } catch (NoSuchElementException ex) {
        logger.info("No done button found");
      }

      try {
        logger.info("Hide keyboard by clicking done");
        getIosDriver().findElement(By.id("Toolbar Done Button")).click();
      } catch (NoSuchElementException ex) {
        logger.info("No Toolbar Done Button button found");
      }
    }
  }

  public static void refreshDeviceSize() {
    deviceSize = SdkHelper.getDriver().manage().window().getSize();
  }

  /**
   * Performs a swipe for the given swipe count
   *
   * @param swipeCount
   */
  public static void swipeWithCount(SwipeDirection swipeDirection, int swipeCount) {
    logger.info("Scrolling to bottom of page");
    for (int i = 0; i < swipeCount; i++) {
      SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(swipeDirection);
      SdkHelper.getSyncHelper().sleep(3000);
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
    while (!isElementDisplayed(element, 5) && (count < retries)) {
      scrollDownCloseToMiddleAlgorithm();
      SdkHelper.getSyncHelper().sleep(2000);
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
  // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
  public static void tapOnElementWithOffset(
      BaseElement element, double xRelativeOffset, double yRelativeOffset) {
    //        int xCenter = element.getMobileElement().getCenter().x; //Commented due to sdk
    // migration, look if it worked
    //        int yCenter = element.getMobileElement().getCenter().y;

    int xCenter = element.getDimension().width / 2;
    int yCenter = element.getDimension().height / 2;

    logger.info(
        String.format(
            "Add this offset to click: x = [%f] , y = [%f]", xRelativeOffset, yRelativeOffset));
    logger.info(String.format("Element center is: x = [%d] , y = [%d]", xCenter, yCenter));
    int xAbsoluteOffset = (int) (xCenter + xRelativeOffset);
    int yAbsoluteOffset = (int) (yCenter + yRelativeOffset);
    logger.info(
        String.format(
            "Clicking with offset: x = [%d] , y = [%d]", xAbsoluteOffset, yAbsoluteOffset));
    SdkHelper.getDeviceControl().tapScreenCoordinates(xAbsoluteOffset, yAbsoluteOffset);
  }

  public static void scrollDownCloseToMiddleAlgorithm() {
    scrollDownAlgorithm(0.1, 0.5, 0.3);
  }

  public static void scrollUpCloseToMiddleAlgorithm() {
    scrollDownAlgorithm(0.1, 0.5, 0.7);
  }

  // Todo: Commented as part of update on pom to 4.1.2 [DO WE NEED IT, REPLACE BY APPLAUSE METHOD]
  private static void scrollDownAlgorithm(double startX, double pStartY, double pEndY) {
    Dimension size = deviceSize;
    int startY = (int) (size.getHeight() * pStartY);
    int endY = (int) (size.getHeight() * pEndY);
    startX = (int) (size.getWidth() * startX);
    logger.info("Swiping startX:" + startX + " startY:" + pStartY + " end Y:" + pEndY);

    try {
      //          new TouchAction(getMobileDriver())
      //              .press(PointOption.point((int) startX, startY))
      //              .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
      //              .moveTo(PointOption.point((int) startX, endY))
      //              .release()
      //              .perform();
      SdkHelper.getDeviceControl()
          .swipeAcrossScreenCoordinates((int) startX, startY, (int) startX, endY, 500);
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
    //    tapByCoordinates((int) tapX, (int) tapY); // Remove if native method works
    SdkHelper.getDeviceControl().tapScreenCoordinates((int) tapX, (int) tapY);
  }

  /**
   * Tapping by coordinates on mobile element center
   *
   * @param element
   */
  public static void tapByCoordinatesOnElementCenter(BaseElement element) {
    Point location = element.getLocation();
    Dimension dimension = element.getDimension();
    //    tapByCoordinates(location.x + dimension.getWidth() / 2, location.y + dimension.getHeight()
    // / 2); // Remove if native method works
    SdkHelper.getDeviceControl()
        .tapScreenCoordinates(
            location.x + dimension.getWidth() / 2, location.y + dimension.getHeight() / 2);
  }

  /**
   * Tapping by coordinates on mobile screen
   *
   * @param tapX
   * @param tapY
   */
  // Todo: Commented as part of update on pom to 4.1.2 [DO WE NEED IT, REPLACE BY APPLAUSE METHOD]
  //  public static void tapByCoordinates(int tapX, int tapY) {
  //    new TouchAction(getMobileDriver()).tap(PointOption.point(tapX, tapY)).perform();
  //  }

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
   * Sets picker value basic.
   *
   * @param value the value
   * @param element the element
   * @param order the order
   */
  public static void setPickerValueBasic(String value, Picker element, String order) {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    int loopCounter = 0;
    String pickerWheel = element.getWebElement().getText();
    WebElement elem = element.getWebElement();
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
          ((AndroidDriver) SdkHelper.getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else {
          elem.sendKeys(Keys.BACK_SPACE);
          elem.click();
          elem.sendKeys(value);
          ((AndroidDriver) SdkHelper.getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
        }
      } else {

        //        JavascriptExecutor js = (JavascriptExecutor) SdkHelper.getDriver();
        //        Map<String, Object> params = new HashMap<>();
        //        params.put("order", order);
        //        params.put("offset", 0.1);
        //        params.put("element", element);
        //        try {
        //          js.executeScript("mobile: selectPickerWheelValue", params);
        //        } catch (WebDriverException wex) {
        //          logger.error("Exception thrown during picker wheel scroll");
        //        }
      }
      loopCounter++;
      pickerWheel = elem.getText();
      logger.debug("Updated picker wheel value: " + pickerWheel);
    }
  }

  public static void tapAndroidDeviceBackButton() {
    logger.info("Tapping on device back button");
    getAndroidDriver().pressKey(new KeyEvent().withKey(AndroidKey.BACK));
  }

  public static void scrollDownHalfScreen(int swipeLimit) {
    refreshDeviceSize();
    logger.info("Scrolling down half a screen");
    scrollDownAlgorithm(0.1, 0.6, 0.4);
  }

  public static void swipeAcrossScreenCoordinates(
      double startX, double startY, double endX, double endY, long millis) {
    // Todo: Commented as part of update on pom to 4.1.2 [DO WE NEED IT, REPLACE BY APPLAUSE
    logger.info(String.format("Swiping from [%s, %s] to [%s, %s].", startX, startY, endX, endY));
    Dimension size = getMobileDriver().manage().window().getSize();

    int startX_ = (int) (size.getWidth() * startX);
    int startY_ = (int) (size.getHeight() * startY);
    int endX_ = (int) (size.getWidth() * endX);
    int endY_ = (int) (size.getHeight() * endY);

    logger.info(
        "Scrolling from: [" + startX_ + " , " + startY_ + "] to [" + endX_ + " , " + endY_ + "]");
    SdkHelper.getDeviceControl()
        .swipeAcrossScreenCoordinates(startX_, startY_, endX_, endY_, millis);
    //
    //    PointOption<?> startPoint = PointOption.point(startX_, startY_);
    //    PointOption<?> endPoint = PointOption.point(endX_, endY_);
    //    WaitOptions time = WaitOptions.waitOptions(Duration.ofMillis(millis));
    //    (new TouchAction(getMobileDriver()))
    //        .press(startPoint)
    //        .waitAction(time)
    //        .moveTo(endPoint)
    //        .release()
    //        .perform();
  }

  public static String getElementTextAttribute(BaseElement baseElement) {
    String textAttribute = "text";
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      textAttribute = "value";
    }
    return baseElement.getAttributeValue(textAttribute);
  }

  // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
  public static RGB getMobileElementColour(BaseElement element) {
    org.openqa.selenium.Point point = element.getLocation();

    Dimension dimension = SdkHelper.getDriver().manage().window().getSize();

    double centerX0 = (double) point.getX() / (double) dimension.width;
    File scrFile = ((TakesScreenshot) SdkHelper.getDriver()).getScreenshotAs(OutputType.FILE);

    BufferedImage image = null;
    try {
      image = ImageIO.read(scrFile);
    } catch (IOException e) {
      logger.error("Error during image reading");
    }
    int screenHeight = SdkHelper.getDeviceControl().getScreenSize().height;
    int screenShotHeight = image.getHeight();
    logger.debug("Screen height = " + screenHeight);
    logger.debug("Screenshot height = " + screenShotHeight);

    double centerY0 =
        (double) point.getY()
            / (SdkHelper.getEnvironmentHelper().isMobileIOS()
                ? (double) dimension.height
                : ((double) dimension.height
                    + screenShotHeight
                    - screenHeight)); // correction due to top navbar

    // Getting pixel color by position x and y
    int clr =
        image.getRGB((int) (image.getWidth() * centerX0), ((int) (image.getHeight() * centerY0)));
    RGB result = new RGB(clr);

    logger.debug("Red Color value = " + result.getRed());
    logger.debug("Green Color value = " + result.getGreen());
    logger.debug("Blue Color value = " + result.getBlue());
    return result;
  }

  public static boolean isIosCheckboxChecked(BaseElement element) {
    try {
      BufferedImage rgbImg = ImageIO.read(element.getWebElement().getScreenshotAs(OutputType.FILE));
      BufferedImage blackAndWhiteImg =
          new BufferedImage(rgbImg.getWidth(), rgbImg.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

      Graphics2D graphics = blackAndWhiteImg.createGraphics();
      graphics.drawImage(rgbImg, 0, 0, null);

      int width = blackAndWhiteImg.getWidth();
      int height = blackAndWhiteImg.getHeight();
      int baseColor = blackAndWhiteImg.getRGB(width / 2, (int) (height * 0.85));
      return !IntStream.rangeClosed((int) (height * 0.15), (int) (height * 0.85))
          .allMatch(index -> blackAndWhiteImg.getRGB(width / 2, index) == baseColor);
    } catch (IOException e) {
      throw new RuntimeException("Exception during checkbox value visual evaluation");
    }
  }

  /**
   * Checks whether an element is displayed.
   *
   * @param element
   * @return true, if the element is displayed
   */
  public static boolean isDisplayed(BaseElement element) {
    try {
      return SdkHelper.getQueryHelper().findElement(element.getLocator()).isDisplayed();
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
    int screenHeight = SdkHelper.getDeviceControl().getScreenSize().height;
    IntStream.range(1, 6)
        .filter(
            i -> {
              try {
                SdkHelper.getSyncHelper().sleep(2000);
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
    Dimension size = SdkHelper.getDeviceControl().getScreenSize();
    logger.debug(String.format("Screen size is [%d x %d].", size.width, size.height));
    swipeAcrossScreenCoordinates(0.5, 0.5, 0.5, 0.3, 2000L);
  }

  public static void swipeAcrossMiddleScreenDown() {
    Dimension size = SdkHelper.getDeviceControl().getScreenSize();
    logger.debug(String.format("Screen size is [%d x %d].", size.width, size.height));
    swipeAcrossScreenCoordinates(0.5, 0.5, 0.5, 0.8, 2000L);
  }

  public static boolean isElementDisplayed(BaseElement element, int timeOutInSeconds) {
    try {
      SdkHelper.getSyncHelper()
          .wait(
              Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(timeOutInSeconds)));
      logger.info("Element is displayed");
      return true;
    } catch (TimeoutException ex) {
      logger.info("Element isn't displayed");
      return false;
    }
  }

  public static void waitUntilElementDisappears(BaseElement element, int waitingTime) {
    if (isElementDisplayed(element, 5)) {
      try {
        if (SdkHelper.getEnvironmentHelper().isiOS()) {
          SdkHelper.getSyncHelper()
              .wait(
                  Until.uiElement(element)
                      .notVisible()
                      .setTimeout(Duration.ofSeconds(waitingTime)));
        } else {
          SdkHelper.getSyncHelper()
              .wait(
                  Until.uiElement(element)
                      .notPresent()
                      .setTimeout(Duration.ofSeconds(waitingTime)));
        }
      } catch (TimeoutException ex) {
        logger.info("Element didn't Disappear is still displayed");
      }
    } else {
      logger.info("Element didn't appear. Moving on");
    }
  }

  public static boolean isAndroid() {
    return StringUtils.containsIgnoreCase(
            ContextManager.getInstance().getDriverContext().getDriver().getPlatform().toString(),
            "android")
        ? true
        : false;
  }

  public static void switchToChromeWebView() {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    logger.info("Switching to WEBVIEW_chrome");
    ((SupportsContextSwitching) SdkHelper.getDriver()).context("WEBVIEW_chrome");
    SdkHelper.getSyncHelper().sleep(1000);
  }

  public static void switchToNativeContext() {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    logger.info("Switching to NATIVE_APP");
    ((SupportsContextSwitching) SdkHelper.getDriver()).context("NATIVE_APP");
    SdkHelper.getSyncHelper().sleep(1000);
  }

  public static void tapOnElementCenter(BaseElement element) {
    //     Todo: Commented as part of update on pom to 4.1.2 [DO WE NEED IT, REPLACE BY APPLAUSE
    SdkHelper.getDeviceControl().tapElementCenter(element);
    //    Point elemCoord = element.getMobileElement().getCenter();
    //    AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    //    new TouchAction(driver).tap(PointOption.point(elemCoord.getX(),
    // elemCoord.getY())).perform();
  }

  public static IosSettingsView openSettings() {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    String bundleId = getCurrentBundleId();
    if (!StringUtils.equalsIgnoreCase(bundleId, "com.apple.Preferences")) {
      //      ((AppiumDriver) SdkHelper.getDriver()).runAppInBackground(Duration.ofSeconds(-1));
      // //This method was commented on migration
      SdkHelper.getDeviceControl().runAppInBackground(Duration.ofSeconds(-1).toMillis());
      activateApplication("com.apple.Preferences");
    }
    return SdkHelper.create(IosSettingsView.class);
  }

  public static String getCurrentBundleId() {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    driver.getCapabilities().getCapabilityNames().stream().forEach(x -> logger.info("TEST " + x));
    logger.info("TEST2 " + SdkHelper.getDeviceControl().getContext());

    //    if (driver.getSessionDetails().get("CFBundleIdentifier") == null) {
    //      throw new RuntimeException("Unable to identify running app bundle id");
    //    }
    //    return driver.getSessionDetails().get("CFBundleIdentifier").toString();
    return "DELET ONCE FIXED";
  }

  public static void activateApplication(String bundleId) {
    JavascriptExecutor js = ((AppiumDriver) SdkHelper.getDriver());
    Map<String, Object> params = new HashMap<>();
    params.put("bundleId", bundleId);
    js.executeScript("mobile: launchApp", params);
    logger.info("Application activated: " + bundleId);
  }
}
