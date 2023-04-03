package com.applause.auto.web.helpers;

import static com.applause.auto.framework.SdkHelper.getDriver;
import static io.appium.java_client.Setting.NATIVE_WEB_TAP;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.remote.SupportsContextSwitching;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public static boolean exists(BaseElement element, int timeoutInSeconds) {
    boolean exists = false;
    Duration previous = SdkHelper.getDriver().getTimeout();
    try {
      SdkHelper.getDriver().setTimeout(timeoutInSeconds);
      exists = element.exists();
    } catch (Throwable t) {
      SdkHelper.getDriver().setTimeout(previous);
    }

    WebHelper.setDefaultTimeOut();
    return exists;
  }

  public static void setDefaultTimeOut() {
    // Set the default wait time on elements to 20 seconds
    SdkHelper.getDriver().setTimeout(20);
  }

  public static void hoverByAction(BaseElement element) {
    logger.info("Hover over...");
    Actions actions = new Actions(SdkHelper.getDriver());
    actions.moveToElement(element.getWebElement()).perform();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  public static void clickByAction(BaseElement element) {
    logger.info("Clicking By Action...");
    Actions actions = new Actions(SdkHelper.getDriver());
    actions.moveToElement(element.getWebElement()).click().perform();
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

  /** Scrolls to the top of the page */
  public static void scrollToPageTop() {
    ((JavascriptExecutor) SdkHelper.getDriver()).executeScript("window.scrollTo(0, 0);");
  }

  /** Scrolls page to bottom */
  public static void scrollToPageBottom() {
    ((JavascriptExecutor) SdkHelper.getDriver())
        .executeScript("window.scrollTo(0,document.body.scrollHeight);");
  }

  public static void removeElement(BaseElement element) {
    if (exists(element, 5))
      try {
        ((JavascriptExecutor) SdkHelper.getDriver())
            .executeScript(
                "return document.querySelector('" + getLocatorString(element) + "').remove();");
      } catch (Exception e) {
        logger.info("Unable to remove element");
      }
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

  public static void clickButtonOverIFrame(BaseElement frame, BaseElement button) {
    if (exists(frame, 10)) {
      logger.info("Switching to special offer frame...");
      switchToIFrame(frame);

      if (button.isClickable()) {
        logger.info("Closing 10% off iFrame");
        jsClick(button.getWebElement());
        SdkHelper.getSyncHelper().wait(Until.uiElement(button).notVisible());
      }

      logger.info("Switching to default content...");
      SdkHelper.getDriver().switchTo().defaultContent();
    }
  }

  public static void clickButtonAfterSwitchingContextIOS(BaseElement button) {
    String oldContext = WebHelper.getCurrentContext();
    WebHelper.switchToNativeContext();
    button.click();
    WebHelper.switchToWeb(oldContext);
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
  }

  public static void allowLocationOnceIOS(BaseElement mainButton, BaseElement confirmButton) {
    logger.info("Allow Location on iOS devices");
    String oldContext = WebHelper.getCurrentContext();
    WebHelper.switchToNativeContext();

    if (exists(mainButton, 5)) {
      logger.info("Allow Once, If location popUp displayed");
      mainButton.click();
    }

    if (exists(confirmButton, 5)) {
      logger.info("Allow, If confirm location popUp displayed");
      confirmButton.click();
    }

    WebHelper.switchToWeb(oldContext);
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
  }

  /**
   * Fix created for phone input on iOS devices
   *
   * @param button
   * @param text
   */
  public static void sendKeysOverIframeBySwitchingContextIOS(BaseElement button, String text) {
    String oldContext = WebHelper.getCurrentContext();
    WebHelper.switchToNativeContext();
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change

    if (button.getWebElement().getAttribute("value").equalsIgnoreCase("phone")) {
      button.getWebElement().sendKeys(text);
    }

    WebHelper.switchToWeb(oldContext);
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
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
    return String.format("test_automation_a%s@gmail.com", uniq);
  }

  public static String getTestExecution() {
    return System.getProperty("execution", "test").toLowerCase();
  }

  public static String getTestEnvironment() {
    return System.getProperty("environment", "uat").toLowerCase();
  }

  public static boolean isProdEnv() {
    return getTestEnvironment().toLowerCase().contains("production");
  }

  public static Float cleanPrice(String price) {
    String cleanPrice = price.replace("$", "");
    logger.info("Clean price: " + cleanPrice);

    return Float.parseFloat(cleanPrice);
  }

  public static boolean isSafari() {
    return getDriverConfig().toLowerCase().contains("safari")
        || SdkHelper.getEnvironmentHelper().isSafari();
  }

  public static void nativeIOSClick(BaseElement element) {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(element).present().setTimeout(Duration.ofSeconds(15)));
    ((IOSDriver) SdkHelper.getDriver()).setSetting(NATIVE_WEB_TAP, true);

    element.getWebElement().click();
    ((IOSDriver) SdkHelper.getDriver()).setSetting(NATIVE_WEB_TAP, false);
  }

  public static void click(BaseElement element) {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      nativeIOSClick(element);
    } else {
      element.click();
    }
  }

  public static void jsClick(final WebElement webElement) {
    final JavascriptExecutor executor = (JavascriptExecutor) SdkHelper.getDriver();
    executor.executeScript("arguments[0].click();", webElement);
  }

  /**
   * Click On Element and in case of ElementClickInterceptedException scroll up and then click again
   *
   * @param element
   */
  public static void clickOnElementAndScrollUpIfNeeded(BaseElement element, int shiftWindow) {
    try {
      element.click();
    } catch (ElementClickInterceptedException e) {
      int yCoordinate = getPagePositionY();
      scrollPageByCoordinates(yCoordinate - shiftWindow);
      SdkHelper.getSyncHelper().sleep(2000);
      logger.info("Clicking again");
      WebHelper.jsClick(element.getWebElement());
    }
  }

  /**
   * Clicks an element at an accurate point on devices, with native tap. This method is to mitigate
   * issues where the different device sizes cause the element locations to differ.
   *
   * @param x coordinate
   * @param y coordinate
   */
  // Todo: Commented as part of update on pom to 4.1.2 [Seems not needed]
  //  public static void clickOnCoordinatesWithNativeTap(int x, int y) {
  //    logger.info("Clicking element with native tap at (" + x + ", " + y + ").");
  //    new TouchAction((AppiumDriver<?>) SdkHelper.getDriver())
  //        .tap(PointOption.point(x, y))
  //        .release()
  //        .perform();
  //  }

  /** @return Y-position of page */
  public static int getPagePositionY() {
    String javascript = "return window.scrollY;";
    return (int)
        Float.parseFloat(
            String.valueOf(((JavascriptExecutor) SdkHelper.getDriver()).executeScript(javascript)));
  }

  /** Scrolls to the top of the page */
  public static void scrollPageByCoordinates(int endPoint) {
    logger.info(String.format("Scrolling Page Coordinates - [0, %s]", endPoint));
    ((JavascriptExecutor) SdkHelper.getDriver())
        .executeScript(String.format("window.scrollTo(%d,%d);", 0, endPoint));
  }

  public static <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    SdkHelper.getDriver().navigate().back();
    SdkHelper.getSyncHelper().sleep(3000); // Wait for action
    return SdkHelper.create(clazz);
  }

  public static <T extends BaseComponent> T navigateTo(Class<T> clazz, String url) {
    SdkHelper.getDriver().navigate().to(url);
    SdkHelper.getSyncHelper().sleep(3000); // Wait for action
    return SdkHelper.create(clazz);
  }

  public static <V extends BaseComponent> V refreshMe(Class<V> expectedClass) {
    logger.info("Refresh site");
    SdkHelper.getDriver().navigate().refresh();
    return SdkHelper.create(expectedClass);
  }

  /**
   * Navigate to specific URL
   *
   * @param expectedUrl
   * @param clazz
   * @return AbstractPage
   */
  public static <T extends BaseComponent> T navigateToUrl(String expectedUrl, Class<T> clazz) {
    logger.info("Navigation to [{}]", expectedUrl);
    getDriver().navigate().to(expectedUrl);
    return SdkHelper.create(clazz);
  }

  /**
   * Cleans the specified string value - removes any unnecessary white-spaces and line breaks.
   *
   * @param stringValue
   * @return cleaned string
   */
  public static String cleanString(String stringValue) {
    return stringValue
        .trim()
        .replaceAll("\n", "")
        .replaceAll("\r", "")
        .replaceAll("\u00a0", "")
        .replaceAll("\t", "");
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

  //ONBOARDING --------------------------------------------------
  //Check if Strings are equal
/*  public static boolean isStringEqual(String element1, String element2){
    if (element1 != element2){
      return true;
    }
    return false;
  }*/

  /**
   * Check if element exist and then if isDisplayed
   *
   * @param element
   * @param timeToWaitInSec
   * @return boolean
   */
  public static boolean isDisplayed(BaseElement element, int timeToWaitInSec) {
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(timeToWaitInSec)));
      return isDisplayed(element);
    } catch (Exception e) {
      return isDisplayed(element);
    }
  }

  public static void waitForVisibleOrPresent(BaseElement element) {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(element).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(element).visible());
    }
  }

  /**
   * If element exist wait For Element To Disapear
   *
   * @param element
   * @param timeOut
   */
  public static void waitForElementToDisappear(BaseElement element, int timeOut) {
    if (waitForElementToAppear(element, timeOut)) {
      try {
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(element).notVisible().setTimeout(Duration.ofSeconds(timeOut)));
      } catch (TimeoutException e) {
        logger.debug("Element didn't disappear after [{}] sec", timeOut);
      }
    }
  }

  /**
   * If element exist wait For Element To Appear
   *
   * @param element
   */
  public static boolean waitForElementToAppear(BaseElement element) {
    return waitForElementToAppear(element, 10);
  }

  /**
   * If element exist wait For Element To Appear
   *
   * @param element
   * @param timeOut
   */
  public static boolean waitForElementToAppear(BaseElement element, int timeOut) {
    if (!isDisplayed(element)) {
      try {
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(element).visible().setTimeout(Duration.ofSeconds(timeOut)));
        return true;
      } catch (TimeoutException e) {
        logger.debug("Element didn't appeared after [{}] sec", timeOut);
      }
    }
    return false;
  }

  /**
   * Check if Text is displayed and not empty
   *
   * @param element
   * @return boolean
   */
  public static boolean isTextDisplayedAndNotEmpty(Text element) {
    return isDisplayed(element) && !element.getText().isEmpty();
  }

  /**
   * Switches to tab/new window
   *
   * @param oldWindowHandle
   */
  public static void switchToNewTab(String oldWindowHandle) {
    // TODO: Replace static wait with wait for condition
    SdkHelper.getSyncHelper().sleep(1000);
    List<String> windows = waitForNewWindowToAppear(20000);

    // get the valid windows again, to ensure that the correct new window appears
    windows = waitForNewWindowToAppear(20000);

    for (String windowHandle : windows) {
      if (!windowHandle.equals(oldWindowHandle)) {
        logger.info(
            "Switching to new window handle ["
                + windowHandle
                + "] from ["
                + oldWindowHandle
                + "].");
        switchToTab(windowHandle);
        return;
      }
    }
  }

  /**
   * Switches to tab/new window
   *
   * @param newUrl
   */
  public static void switchToNewTabThatContainsUrl(String newUrl) {
    // TODO: Replace static wait with wait for condition
    SdkHelper.getSyncHelper().sleep(1000);
    List<String> windows = waitForNewWindowToAppear(20000);

    // get the valid windows again, to ensure that the correct new window appears
    windows = waitForNewWindowToAppear(20000);
    // reverse windows to start from new one
    Collections.reverse(windows);
    String currentUrl;
    for (String windowHandle : windows) {
      logger.info("Switching to new window handle [" + windowHandle + "].");
      switchToTab(windowHandle);
      currentUrl = WebHelper.getCurrentUrl();
      if (currentUrl.contains(newUrl)) {
        return;
      } else {
        logger.info(String.format("Tab URL [%s] didn't contain [%s]", currentUrl, newUrl));
      }
    }
  }

  /**
   * Wait For New Window To Appear
   *
   * @param timeToWaitInMs
   * @return ArrayList
   */
  public static List<String> waitForNewWindowToAppear(long timeToWaitInMs) {
    Set<String> windows = SdkHelper.getDriver().getWindowHandles();
    long timeLimit = System.currentTimeMillis() + timeToWaitInMs;

    while (windows.size() < 2 && timeLimit > System.currentTimeMillis()) {
      // TODO: Replace static wait with wait for condition
      SdkHelper.getSyncHelper().sleep(500);
      windows = SdkHelper.getDriver().getWindowHandles();
    }

    if (windows.size() < 2) {
      throw new RuntimeException(
          "No new tabs appeared, staying at: "
              + SdkHelper.getDriver().getTitle()
              + " ("
              + SdkHelper.getDriver().getCurrentUrl()
              + ").");
    }

    return new ArrayList<>(windows);
  }

  /**
   * Switches to tab/new window
   *
   * @param windowToSwitch
   */
  public static void switchToTab(String windowToSwitch) {
    SdkHelper.getDriver().switchTo().window(windowToSwitch);

    logger.info("Switching to: " + SdkHelper.getDriver().getCurrentUrl());
    try {
      new WebDriverWait(SdkHelper.getDriver(), 10)
          .until(w -> !SdkHelper.getDriver().getCurrentUrl().contains("about:blank"));
    } catch (Exception e) {
      throw new RuntimeException("New tab is blank.");
    }
  }

  /**
   * Get integer from the sentence
   *
   * @param sentence the sentence
   * @return int number from string
   */
  public static int getNumberFromString(String sentence) {
    return Integer.parseInt(sentence.replaceAll("[^\\d.]", ""));
  }

  public static List<WebElement> findShadowElementsBy(WebElement parent, By by) {
    // Important: xpath search does not working
    if (parent == null) {
      // Search root
      Set<WebElement> result = new LinkedHashSet(SdkHelper.getDriver().findElements(by));
      if (result.size() == 0) {
        getShadowElementsFromRoot()
            .stream()
            .forEach(elem -> result.addAll(findShadowElementsBy(elem, by)));
      }
      return new ArrayList<>(result);
    } else {
      Set<WebElement> result = new LinkedHashSet<>();
      List<WebElement> descendant = parent.findElements(by);
      if (!descendant.isEmpty()) {
        logger.info("Element found: " + descendant.size());
        result.addAll(descendant);
      } else {
        WebElement shadow = getWebElementFromShadowRoot(parent);
        if (shadow != null) {
          logger.info("Switching to shadow node");
          result.addAll(findShadowElementsBy(shadow, by));
        } else {
          List<WebElement> shadowNodes = getShadowElementsFromParent(parent);
          logger.info("Found shadow nodes on level: " + shadowNodes.size());
          shadowNodes
              .stream()
              .forEach(
                  elem -> {
                    logger.info("Searching for element in shadow node...");
                    List<WebElement> nodes = findShadowElementsBy(elem, by);
                    if (!nodes.isEmpty()) {
                      logger.info("Element found: " + nodes.size());
                    }
                    result.addAll(nodes);
                  });
        }
      }
      return new ArrayList<>(result);
    }
  }

  public static WebElement getWebElementFromShadowRoot(WebElement webElement) {
    WebElement returnObj = null;
    Object shadowRoot =
        ((JavascriptExecutor) SdkHelper.getDriver())
            .executeScript("return arguments[0].shadowRoot", webElement);
    if (shadowRoot instanceof WebElement) {
      // ChromeDriver 95
      returnObj = (WebElement) shadowRoot;
    } else if (shadowRoot instanceof Map) {
      // ChromeDriver 96+
      // Based on
      // https://github.com/SeleniumHQ/selenium/issues/10050#issuecomment-974231601
      Map<String, Object> shadowRootMap = (Map<String, Object>) shadowRoot;
      String shadowRootKey = (String) shadowRootMap.keySet().toArray()[0];
      String id = (String) shadowRootMap.get(shadowRootKey);
      RemoteWebElement remoteWebElement = new RemoteWebElement();
      remoteWebElement.setParent((RemoteWebDriver) SdkHelper.getDriver());
      remoteWebElement.setId(id);
      returnObj = remoteWebElement;
    } else if (shadowRoot == null) {
      return null;
    } else {
      Assert.fail("Unexpected return type for shadowRoot in expandRootElement()");
    }
    return returnObj;
  }

  public static ArrayList<WebElement> findShadowElementsBy(By... by) {
    Set<WebElement> result = null;
    for (By locator : by) {
      if (result == null) {
        result = new LinkedHashSet<>(findShadowElementsBy((WebElement) null, locator));
      } else {
        logger.info("trying: by " + locator);
        result = new LinkedHashSet<>(findShadowElementsBy(result, locator));
      }
      logger.info("Found elements: " + result.size());
    }
    return new ArrayList<>(result);
  }

  public static List<WebElement> getShadowElementsFromParent(WebElement webElement) {
    return (List<WebElement>)
        ((JavascriptExecutor) SdkHelper.getDriver())
            .executeScript(
                "return Array.from(arguments[0].querySelectorAll('*')).filter(function(item){return item.shadowRoot != null})",
                webElement);
  }

  public static List<WebElement> getShadowElementsFromRoot() {
    return (List<WebElement>)
        ((JavascriptExecutor) SdkHelper.getDriver())
            .executeScript(
                "return Array.from(document.querySelectorAll('*')).filter(function(item){return item.shadowRoot != null})");
  }

  public static ArrayList<WebElement> findShadowElementsBy(Set<WebElement> parents, By by) {
    // Important: xpath search does not working
    Set<WebElement> result = new LinkedHashSet<>();
    parents
        .stream()
        .forEach(
            parent -> {
              WebElement shadow = getWebElementFromShadowRoot(parent);
              if (shadow != null) {
                logger.info(">>>Found shadow DOM element: " + shadow);
                result.addAll(shadow.findElements(by));
              } else {
                logger.info(">>>>Element without shadow: " + parent);
                result.addAll(parent.findElements(by));
                getShadowElementsFromParent(parent)
                    .forEach(elem -> result.addAll(findShadowElementsBy(elem, by)));
              }
            });
    return new ArrayList<>(result);
  }

  /**
   * Gets random value within a specific range
   *
   * @param min
   * @param max
   * @return Random value within range
   */
  public static int getRandomValueWithinRange(int min, int max) {
    return new Random().nextInt((max - min) + 1) + min;
  }

  public static String getRandomPhoneNumber() {
    return "8" + getRandomValueWithinRange(100000000, 999999999);
  }

  /** return old tab so test can switch back if need be */
  public static void getNewTab() {
    ArrayList<String> tabs = new ArrayList(SdkHelper.getDriver().getWindowHandles());
    SdkHelper.getDriver().switchTo().window(tabs.get(tabs.size() - 1));
  }

  /** Wait for page is loading to complete */
  public static void waitForPageLoadingToComplete() {
    logger.debug("Wait 20sec for page loading to complete");
    new WebDriverWait(SdkHelper.getDriver(), 20)
        .until(
            driver ->
                ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState")
                    .equals("complete"));
  }

  /** Get the Current URL */
  public static String getCurrentUrl() {
    String url = SdkHelper.getDriver().getCurrentUrl();
    logger.debug("Current url [{}]", url);
    return url;
  }

  public static String getLocatorString(BaseElement element) {
    String locatorString = element.getLocator().getLocatorStringMap().entrySet().toString();
    return locatorString.substring(0, locatorString.length() - 1).split("=")[1];
  }

  /** A slow navigation helper for when you need to wait for url change. * */
  public static void slowNavigationHelper(String linkHref, int seconds) {
    for (int i = 0; i <= seconds; i++) {
      SdkHelper.getSyncHelper().sleep(1000);
      if (WebHelper.getCurrentUrl().contains(linkHref)) {
        logger.info("-- Navigation has happened.");
        break;
      }
    }
  }

  public static void hideKeyboard() {
    // Todo: Commented as part of update on pom to 4.1.2 [REVIEW AGAIN!!!]
    if (isMobile()) {
      //      Set<String> contexts = ((AppiumDriver) SdkHelper.getDriver()).getContextHandles();
      Set<String> contexts = ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles();

      logger.info("Checking native context is shown on UI");
      Optional<String> nativeContext =
          contexts
              .stream()
              .filter(context -> StringUtils.containsIgnoreCase(context, "native"))
              .findAny();
      if (nativeContext.isPresent()) {
        try {
          logger.info("Test running on mobile device... ");
          logger.info("Switching to native context");
          //          ((AppiumDriver) SdkHelper.getDriver()).context(nativeContext.get());
          ((SupportsContextSwitching) SdkHelper.getDriver()).context(nativeContext.get());
          SdkHelper.getSyncHelper().sleep(1000);
          logger.info("Switched to native context");

          try {
            if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
              ((IOSDriver) SdkHelper.getDriver())
                  .hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
              SdkHelper.getSyncHelper().sleep(2000); // Wait for keyboard to be hidden on iOS
              logger.info("iOS Keyboard was hidden");
            } else {
              ((AndroidDriver) SdkHelper.getDriver()).hideKeyboard();
              logger.info("Android Keyboard was hidden");
            }
          } catch (Exception e) {
            logger.info("There was an error while hiding the keyboard");
          }
        } finally {
          logger.info("Switching back to web context");
          contexts.remove(nativeContext.get());
          //          ((AppiumDriver) SdkHelper.getDriver()).context(contexts.iterator().next());
          ((SupportsContextSwitching) SdkHelper.getDriver()).context(contexts.iterator().next());
        }
      }
      SdkHelper.getSyncHelper().sleep(2000);
    }
  }

  /**
   * Round double value
   *
   * @param value
   * @param places
   * @return double
   */
  public static double roundDouble(double value, int places) {
    logger.info("Round value: '{}' with places: '{}'", value, places);
    double scale = Math.pow(10, places);
    double roundDouble = Math.round(value * scale) / scale;
    logger.info("Rounded double value: {}", roundDouble);
    return roundDouble;
  }

  /** Closes other tabs */
  public static void closeOtherTabs() {
    logger.info("Closing all other tabs.");
    String currentWindow = SdkHelper.getDriver().getWindowHandle();

    for (String windowHandle : SdkHelper.getDriver().getWindowHandles()) {
      if (!windowHandle.equals(currentWindow)) {
        SdkHelper.getDriver().switchTo().window(windowHandle);
        SdkHelper.getDriver().close();
      }
    }

    SdkHelper.getDriver().switchTo().window(currentWindow);
  }

  public static boolean useRetry() {
    return !System.getProperty("useRetry", "yes").toLowerCase().equals("no");
  }

  public static String getInnerHtml(WebElement element) {
    String value =
        (String)
            ((JavascriptExecutor) SdkHelper.getDriver())
                .executeScript("return arguments[0].innerHTML;", element);
    return value;
  }

  public static Boolean getVisibility(WebElement element) {
    Boolean value =
        (Boolean)
            ((JavascriptExecutor) SdkHelper.getDriver())
                .executeScript("return arguments[0].checkVisibility();", element);
    return value;
  }

  public static Boolean getVisibilityAlter(WebElement element) {
    Boolean value =
        (Boolean)
            ((JavascriptExecutor) SdkHelper.getDriver())
                .executeScript("return (arguments[0].offsetParent === null);", element);
    return value;
  }

  public static void clickAndroidBackButton() {
    logger.info("Clicking on the Android back button");
    ((AndroidDriver) getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
  }

  public static boolean nativeContextIsPresent() {
    logger.info("Verify two or more contexts are present");
    return ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles().size() >= 2;
  }

  public static boolean isAppleStoreDisplayed() {
    logger.info("Review if apple store is displayed");
    String oldContext = WebHelper.getCurrentContext();
    WebHelper.switchToNativeContext();
    logger.info("Page Source [Debug] " + SdkHelper.getDriver().getPageSource());
    int total =
        SdkHelper.getDriver().findElements(By.xpath("//*[contains(@name,\"AppStore\")]")).size();
    WebHelper.switchToWeb(oldContext);
    SdkHelper.getSyncHelper().sleep(3000); // Extra wait due to context change
    return total > 1;
  }

  public static void switchToNativeContext() {
    logger.info("Switching to native context");
    Set<String> contexts = ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles();
    Optional<String> nativeContext =
        contexts
            .stream()
            .filter(context -> StringUtils.containsIgnoreCase(context, "native"))
            .findAny();
    ((SupportsContextSwitching) SdkHelper.getDriver()).context(nativeContext.get());
    SdkHelper.getSyncHelper().sleep(1000);
    logger.info("Switched to native context");
  }

  public static void closeCurrentTab() {
    logger.info("Closing current tab.");
    String currentWindow = SdkHelper.getDriver().getWindowHandle();
    SdkHelper.getDriver().close();

    SdkHelper.getDriver()
        .switchTo()
        .window((String) SdkHelper.getDriver().getWindowHandles().toArray()[0]);
  }

  public static String getCurrentContext() {
    String oldContext = ((SupportsContextSwitching) SdkHelper.getDriver()).getContext();
    logger.info("Current context: {}", oldContext);
    return oldContext;
  }

  public static void switchToWeb(String webContext) {
    try {
      logger.info("Switching to context: {}", webContext);
      logger.info(
          "Available contexts: "
              + ((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles());
      ((SupportsContextSwitching) SdkHelper.getDriver()).context(webContext);
      SdkHelper.getSyncHelper().sleep(1000);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Wait 1 minutes for page URL contains expected parameter
   *
   * @param urlParameter
   * @return boolean
   */
  public static boolean doesPageUrlContainExpectedParameter(String urlParameter) {
    return new WebDriverWait(getDriver(), Duration.ofMinutes(1))
        .until(driver -> WebHelper.getCurrentUrl().contains(urlParameter));
  }

  /** @return JavaScript window width */
  public static int getJavascriptWindowWidth() {
    int windowWidth =
        ((Long)
                getJavascriptExecutor()
                    .executeScript("return window.innerWidth || document.body.clientWidth"))
            .intValue();
    logger.info("Current window width is: " + windowWidth);
    return windowWidth;
  }

  private static JavascriptExecutor getJavascriptExecutor() {
    return (JavascriptExecutor) getDriver();
  }

  public static boolean isDesktopSiteVersion() {
    // The site with windows width more than 767 is shown in desktop version even on mobile browser
    // with landscape orientation
    return getJavascriptWindowWidth() > 767;
  }

  public static void clickIosWithNativeTap(BaseElement element) {
    logger.info("Clicking with Native Tap");
    ((IOSDriver) SdkHelper.getDriver()).nativeWebTap(true);
    element.click();
    ((IOSDriver) SdkHelper.getDriver()).nativeWebTap(false);
  }
}
