package com.applause.auto.new_web.helpers;

import static com.applause.auto.framework.SdkHelper.getDriver;
import static io.appium.java_client.Setting.NATIVE_WEB_TAP;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.touch.offset.PointOption;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebHelper {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  public static String previousTab = null;

  public static boolean exists(BaseElement element, int timeoutInSeconds) {
    boolean exists = false;
    Duration previous = SdkHelper.getDriver().getTimeout();
    try {
      SdkHelper.getDriver().setTimeout(timeoutInSeconds);
      exists = element.exists();
    } catch (Throwable t) {
      SdkHelper.getDriver().setTimeout(previous);
    }
    return exists;
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
    try {
      scrollToElement(element.getWebElement());
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
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
    return String.format("test_automation_a%s@gmail.com", uniq);
  }

  public static String getTestExecution() {
    return System.getProperty("execution", "test").toLowerCase();
  }

  public static String getTestEnvironment() {
    return System.getProperty("environment", "uat").toLowerCase();
  }

  public static Float cleanPrice(String price) {
    String cleanPrice = price.replace("$", "");
    logger.info("Clean price: " + cleanPrice);

    return Float.parseFloat(cleanPrice);
  }

  public static boolean isSafari() {
    return getDriverConfig().contains("SAFARI_MAC") || SdkHelper.getEnvironmentHelper().isSafari();
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
      element.click();
    }
  }

  /**
   * Clicks an element at an accurate point on devices, with native tap. This method is to mitigate
   * issues where the different device sizes cause the element locations to differ.
   *
   * @param x coordinate
   * @param y coordinate
   */
  public static void clickOnCoordinatesWithNativeTap(int x, int y) {
    logger.info("Clicking element with native tap at (" + x + ", " + y + ").");
    new TouchAction((AppiumDriver<?>) SdkHelper.getDriver())
        .tap(PointOption.point(x, y))
        .release()
        .perform();
  }

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
    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      SdkHelper.getDriver().navigate().back();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }

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

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      getDriver().navigate().to(expectedUrl);
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
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
   * If element exist wait For Element To Disapear
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
        getShadowElementsFromRoot().stream()
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
          shadowNodes.stream()
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
    parents.stream()
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

  /** return old tab so test can swich back if need be */
  public static void getNewTab() {
    previousTab = SdkHelper.getDriver().getWindowHandle();
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
    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    String url;
    try {
      url = SdkHelper.getDriver().getCurrentUrl();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
      SdkHelper.getSyncHelper().sleep(3000); // Remove when fixed
      url = SdkHelper.getDriver().getCurrentUrl();
    }

    logger.debug("Current url [{}]", url);
    return url;
  }

  /** A slow navigation helper for when you need to wait for url change. * */
  public static void slowNavigationHelper(String linkHref, int seconds) {
    for (int i = 0; i <= seconds; i++) {
      SdkHelper.getSyncHelper().sleep(1000);
      try {
        if (WebHelper.getCurrentUrl().contains(linkHref)) {
          logger.info("-- Navigation has happened.");
          break;
        }
      } catch (WebDriverException e) {
        logger.info("Frame detached issue seen");
      }
    }
  }

  public static void hideKeyboard() {
    if (isMobile()) {
      Set<String> contexts = ((AppiumDriver) SdkHelper.getDriver()).getContextHandles();
      logger.info("Checking native context is shown on UI");
      Optional<String> nativeContext =
          contexts.stream()
              .filter(context -> StringUtils.containsIgnoreCase(context, "native"))
              .findAny();
      if (nativeContext.isPresent()) {
        try {
          logger.info("Test running on mobile device... ");
          logger.info("Switching to native context");
          ((AppiumDriver) SdkHelper.getDriver()).context(nativeContext.get());
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
          ((AppiumDriver) SdkHelper.getDriver()).context(contexts.iterator().next());
        }
      }
      SdkHelper.getSyncHelper().sleep(2000);
    }
  }
}
