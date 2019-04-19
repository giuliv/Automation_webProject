package com.applause.auto.pageframework.helpers;

import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.AbstractDeviceUIData;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.PickerWheel;
import com.applause.auto.framework.pageframework.util.DeviceControl;
import com.applause.auto.framework.pageframework.util.TestHelper;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapper;
import com.applause.auto.framework.pageframework.util.drivers.DriverWrapperManager;
import com.applause.auto.framework.pageframework.util.environment.EnvironmentUtil;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.util.queryhelpers.DeviceElementQueryHelper;
import com.applause.auto.framework.pageframework.util.screenshots.MobileScreenshotManager;
import com.applause.auto.framework.pageframework.util.synchronization.MobileNativeSyncHelper;
import com.applause.auto.pageframework.enums.Direction;
import com.applause.auto.pageframework.testdata.TestConstants;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileHelper {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
	private static EnvironmentUtil env = EnvironmentUtil.getInstance();

	private static Boolean controlPanelIsOpen = false;

	private static Dimension deviceSize = null;

	public static Boolean wifiOff = false;

	public void waitUntilVisible() {
		if (deviceSize == null) {
			refreshDeviceSize();
		}
	}

	static {
		refreshDeviceSize();
	}

	/**
	 * Swipe Left
	 */
	public static void swipeLeft() {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.8);
		int endX = (int) (size.width * 0.2);
		int startY = size.height / 2;
		LOGGER.info("Swiping left...");
		new TouchAction(getDriver()).press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).moveTo(PointOption.point(endX, 0))
				.release().perform();
	}

	public static void swipeLeft(double y) {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.8);
		int endX = (int) (size.width * 0.2);
		double startY;
		if (y < 1) {
			startY = size.height * y;
		} else {
			startY = y;
		}
		LOGGER.info("Swiping left...");
		int i = 5;
		while (i != 0) {
			i--;
			try {
				new TouchAction(getDriver()).press(PointOption.point(startX, (int) startY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(100))).moveTo(PointOption.point(endX, 0))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(100))).release().perform();
				LOGGER.info("Swiping completed");
				break;
			} catch (Throwable throwable) {
				LOGGER.error("Something happened during swipe. Attempts left #" + i);
			}

		}
	}

	public static void activateApp() {
		getDriver().activateApp(TestConstants.MobileApp.IOS_BUNDLE_ID);
	}

	public static void swipeLeftSlow(double y) {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.55);
		int endX = (int) (size.width * 0.45);
		double startY;
		if (y < 1) {
			startY = size.height * y;
		} else {
			startY = y;
		}
		LOGGER.info("Swiping left...");
		new TouchAction(getDriver()).press(PointOption.point(startX, (int) startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).moveTo(PointOption.point(endX, 0))
				.release().perform();
	}

	public static void swipeDownSlow(double y) {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.55);
		int endX = (int) (size.width * 0.45);
		double startY;
		if (y < 1) {
			startY = size.height * y;
		} else {
			startY = y;
		}
		LOGGER.info("Swiping left...");
		new TouchAction(getDriver()).press(PointOption.point(startX, (int) startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).moveTo(PointOption.point(endX, 0))
				.release().perform();
	}

	/**
	 * Swipe Right
	 */
	public static void swipeRight() {
		Dimension size = deviceSize;
		swipeRight(0.5);
	}

	public static void swipeRight(double y) {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.2);
		int endX = (int) (size.width * 0.8);
		double startY;
		if (y < 1) {
			startY = size.height * y;
		} else {
			startY = y;
		}
		LOGGER.info("Swiping right...");
		new TouchAction(getDriver()).press(PointOption.point(startX, (int) startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).moveTo(PointOption.point(endX, 0))
				.release().perform();
	}

	public static void swipeRightSlow(double y) {
		Dimension size = deviceSize;
		int startX = (int) (size.width * 0.45);
		int endX = (int) (size.width * 0.55);
		double startY;
		if (y < 1) {
			startY = size.height * y;
		} else {
			startY = y;
		}
		LOGGER.info("Swiping right...");
		new TouchAction(getDriver()).press(PointOption.point(startX, (int) startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(250))).moveTo(PointOption.point(endX, 0))
				.release().perform();
	}

	/**
	 * Performs a quick swipe action towards the left of a specified element
	 *
	 * @param element
	 *            - Specified BaseDeviceControl element
	 */
	public static void swipeLeftOnElement(BaseDeviceControl element) {
		swipeHorizontallyOnElement(element, Direction.LEFT);
	}

	public static void swipeDownOnElement(BaseDeviceControl element) {
		swipeVerticallyOnElement(element, Direction.DOWN);

	}

	/**
	 * Performs a quick swipe action towards the right of a specified element
	 *
	 * @param element
	 *            - Specified BaseDeviceControl element
	 */
	public static void swipeRightOnElement(BaseDeviceControl element) {
		swipeHorizontallyOnElement(element, Direction.RIGHT);
	}

	/**
	 * Performs a quick swipe towards the left on given element position
	 *
	 * @param element
	 *            - Specified BaseDeviceControl element
	 */
	public static void swipeLeftOnElementPosition(BaseDeviceControl element) {
		swipeHorizontallyOnElementPosition(element, Direction.LEFT);
	}

	/**
	 * Performs a quick swipe towards the right on given element position
	 *
	 * @param element
	 *            - Specified BaseDeviceControl element
	 */
	public static void swipeRightOnElementPosition(BaseDeviceControl element) {
		swipeHorizontallyOnElementPosition(element, Direction.RIGHT);
	}

	/**
	 * Performs a quick swipe action towards a set direction of a specified element
	 *
	 * @param element
	 *            - Specified BaseDeviceControl element
	 * @param direction
	 *            - Horizontal direction that the element will be swiped towards. Can only be 'left'
	 *            or 'right'.
	 */
	private static void swipeHorizontallyOnElement(BaseDeviceControl element, Direction direction) {
		if (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) {
			Assert.fail("'" + direction + "' is not a valid horizontal direction. Please specify 'left' or 'right'.");
		}

		LOGGER.debug("Swiping " + direction + " on [" + element + "] element.");
		int elementX = element.getLocation().x;
		int elementY = element.getLocation().y;
		int elementHalfWidth = element.getDimension().width;
		int swipeDirection = direction.equals(Direction.LEFT) ? (int) (elementHalfWidth * 0.8)
				: (int) (elementHalfWidth * 0.2);
		LOGGER.info("Swiping...");
		getDeviceControl().swipe(elementX, elementY, swipeDirection, elementY, 1000);
	}

	/**
	 * Swipe horizontally on element position
	 * 
	 * @param element
	 *            - Specified BaseDeviceControl element
	 * @param direction
	 *            - Horizontal direction in which the scroll will be executed
	 */
	private static void swipeHorizontallyOnElementPosition(BaseDeviceControl element, Direction direction) {
		if (direction.equals(Direction.DOWN) || direction.equals(Direction.UP)) {
			Assert.fail("'" + direction + "' is not a valid horizontal direction. Please specify 'left' or 'right'.");
		}
		Dimension size = getDriver().manage().window().getSize();
		int startX = (int) (size.width * 0.2);
		int endX = (int) (size.width * 0.8);
		int elementY = element.getCenterY();
		// Appium java client has bug where
		// iOS coordinates are relative, but Android are absolute
		LOGGER.info("Swiping...");
		if (env.getIsMobileIOS()) {
			if (direction.equals(Direction.LEFT)) {
				new TouchAction(getDriver()).press(PointOption.point(startX, elementY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis((long) 1000)))
						.moveTo(PointOption.point(startX - endX, elementY)).release().perform();
			} else {
				new TouchAction(getDriver()).press(PointOption.point(endX, elementY))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis((long) 1000)))
						.moveTo(PointOption.point(endX - startX, elementY)).release().perform();
			}
		} else {
			LOGGER.debug("x1 " + startX + "x2 " + endX);
			if (direction.equals(Direction.RIGHT)) {
				getDeviceControl().swipe(startX, elementY, endX, elementY, 1000);
			} else {
				getDeviceControl().swipe(endX, elementY, startX, elementY, 1000);
			}
		}
	}

	private static void swipeVerticallyOnElement(BaseDeviceControl element, Direction direction) {
		if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
			Assert.fail("'" + direction + "' is not a valid vertical direction. Please specify 'up' or 'down'.");
		}

		LOGGER.debug("Swiping " + direction + " on [" + element + "] element.");
		int elementX = element.getCenterX();
		int elementY = element.getCenterY();
		int elementHalfHeight = element.getDimension().height;
		int swipeDirection = direction.equals(Direction.UP) ? (int) (elementHalfHeight * 0.8)
				: (int) (elementHalfHeight * 0.2);
		LOGGER.info("Swiping...");
		int swipeLimit = 0;
		while (!element.getMobileElement().isDisplayed() && swipeLimit < 30) {
			swipeLimit++;
			getDeviceControl().swipe(elementX, elementY, elementX, swipeDirection, 1000);
		}
	}

	public static String generateRandomString() {

		return "test_" + System.currentTimeMillis() / 1000;
	}

	public static MobileElement androidScrollTo(String UiSelector, String scrollSelector, int maxSwipes) {
		LOGGER.info("Scrolling to: " + UiSelector);
		return getAndroidDriver().findElement(MobileBy.AndroidUIAutomator(
				String.format("new UiScrollable(%s).setMaxSearchSwipes(%s)" + ".scrollIntoView(%s);", scrollSelector,
						maxSwipes, UiSelector)));
	}

	public static MobileElement androidScrollTo(String UiSelector) {
		return androidScrollTo(UiSelector, "new UiSelector().className(\"android.widget.ScrollView\").instance(0)", 10);
	}

	public static MobileElement androidScrollTo(String UiSelector, int index) {
		return androidScrollTo(UiSelector,
				String.format("new UiSelector().className(\"android.widget.ScrollView\").instance(%s)", index), 10);
	}

	public static MobileElement androidScrollToText(String s, String scrollSelector) {
		LOGGER.info("Scrolling to: " + s);
		return getAndroidDriver().findElement(MobileBy.AndroidUIAutomator(String.format(
				"new UiScrollable(%s)" + ".scrollIntoView(new UiSelector().text(\"%s\"));", scrollSelector, s)));

	}

	public static MobileElement androidScrollToText(String s) {
		return androidScrollToText(s, "new UiSelector().className(\"android.widget.ScrollView\").instance(0)");
	}

	public static MobileElement androidScrollToWithRetries(String UiSelector, int retries) {
		Boolean found = false;
		int count = 0;
		MobileElement ret = null;
		while (!found && count < retries) {
			try {
				ret = androidScrollTo(UiSelector,
						"new UiSelector().className(\"android.widget.ScrollView\").instance(0)", 10);
				found = true;
			} catch (TimeoutException | NoSuchElementException e) {
				LOGGER.debug("Retrying...");
			}
			count++;
		}
		return ret;
	}

	public static void jsScrollDown() {
		JavascriptExecutor js = getDriver();
		HashMap<String, String> scrollObject = new HashMap<>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}

	public static void jsScrollUp() {
		JavascriptExecutor js = getDriver();
		HashMap<String, String> scrollObject = new HashMap<>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: scroll", scrollObject);
	}

	public static AndroidDriver<MobileElement> getAndroidDriver() {
		AndroidDriver<MobileElement> androidDriver = (AndroidDriver<MobileElement>) getDriver();
		return androidDriver;
	}

	public static IOSDriver<MobileElement> getIOSDriver() {
		IOSDriver<MobileElement> IOSDriver = (IOSDriver<MobileElement>) getDriver();
		return IOSDriver;
	}

	public static <T> T selectRandomFromList(List<T> list) {
		int size = list.size();
		return list.get(new Random().nextInt(size));
	}

	public static <T> T selectRandomFromList(T[] list) {
		int size = list.length;
		return list[new Random().nextInt(size)];
	}

	/**
	 * Hide Keyboard if Present
	 *
	 */
	public static void hideKeyboardIfPresent() {
		if (!env.getIsMobileIOS()) {
			try {
				LOGGER.info("Hiding keyboard.");
				getDriver().hideKeyboard();
			} catch (Exception e) {
				LOGGER.debug("Original error: Soft keyboard not present, cannot hide keyboard");
			}
		} else {
			try {
				LOGGER.info("Hiding keyboard.");
				// ((IOSDriver)
				// getDriver()).hideKeyboard(HideKeyboardStrategy.TAP_OUTSIDE,
				// "Done");
				getDriver().navigate().back();
			} catch (Exception e) {
				LOGGER.debug("Original error: Soft keyboard not present, cannot hide keyboard");
			}
		}
	}

	/**
	 * Scrolls down with a maximum swipeLimit
	 *
	 * @param swipeLimit
	 */
	public static void scrollDown(int swipeLimit) {
		LOGGER.info("Scrolling down to element.");
		refreshDeviceSize();
		for (int i = 0; i < swipeLimit; i++) {
			scrollDownAlgorithm();
			getSyncHelper().suspend(500);
		}
	}

	public static void scrollUp(int swipeLimit) {
		int countOfSwipes = 0;

		while (countOfSwipes < swipeLimit) {
			scrollUpAlgorithm();
			getSyncHelper().suspend(1000);
			countOfSwipes++;
		}
	}

	public static void scrollDownCloseToMiddle(int swipeLimit) {
		LOGGER.info("Scrolling down to element.");
		refreshDeviceSize();
		for (int i = 0; i < swipeLimit; i++) {
			scrollDownCloseToMiddleAlgorithm();
			getSyncHelper().suspend(500);
		}
	}

	public static void refreshDeviceSize() {
		deviceSize = getDriver().manage().window().getSize();
	}

	/**
	 * Scrolls down with a maximum swipeLimit
	 *
	 * @param swipeLimit
	 */
	public static void scrollDown(int swipeLimit, double startX, double startY, double endY) {
		refreshDeviceSize();
		for (int i = 0; i < swipeLimit; i++) {
			scrollDownAlgorithm(startX, startY, endY);
			getSyncHelper().suspend(500);
		}
	}

	/**
	 * Scroll up
	 *
	 * @param swipeLimit
	 *            the swipe limit
	 * @param startX
	 *            - position where to start swipe on vertical line
	 */
	public static void scrollUp(int swipeLimit, double startX) {
		LOGGER.info("Scrolling up to element.");
		refreshDeviceSize();
		for (int i = 0; i < swipeLimit; i++) {
			scrollUpAlgorithm(startX);
			getSyncHelper().suspend(1000);
		}
	}

	/**
	 * Scroll Up on Small Screens
	 *
	 * @param swipeLimit
	 * @param startX
	 */
	public static void smallScreenScrollUp(int swipeLimit, double startX) {
		LOGGER.info("Scrolling up to element.");
		if (!env.getIsTablet()) {
			refreshDeviceSize();
			for (int i = 0; i < swipeLimit; i++) {
				scrollUpAlgorithm(startX);
				getSyncHelper().suspend(500);
			}
		}
	}

	/**
	 * Scroll Down on Small Screens
	 *
	 * @param swipeLimit
	 * @param startX
	 */
	public static void smallScreenScrollDown(int swipeLimit, double startX) {
		LOGGER.info("Scrolling down to element.");
		if (!env.getIsTablet()) {
			refreshDeviceSize();
			for (int i = 0; i < swipeLimit; i++) {
				scrollDownAlgorithm(startX, 0.4, 0.1);
				getSyncHelper().suspend(500);
			}
		}
	}

	/**
	 * Scroll from Point A to Point B
	 * 
	 * @param start
	 * @param end
	 */
	public static void scrollFrom(Point start, Point end) {
		LOGGER.info(String.format("swipe from (%s,%s) to (%s,%s)", start.x, start.y, end.x, end.y));
		getDeviceControl().swipe(start.x, start.y, end.x, end.y, 1000);
	}

	/**
	 * Scrolls down with a maximum swipeLimit
	 *
	 * @param swipeLimit
	 */
	public static void scrollToBottom(int swipeLimit) {
		LOGGER.info("Scrolling to bottom of page");
		refreshDeviceSize();
		for (int i = 0; i < swipeLimit; i++) {
			scrollDownAlgorithm(0.1, 0.8, 0.2);
			getSyncHelper().suspend(500);
		}
	}

	public static void scrollDownToElement(String locator, int retries) {
		refreshDeviceSize();
		int count = 0;
		while (!getSyncHelper().isElementDisplayed(locator) && count < retries) {
			scrollDownAlgorithm();
			getSyncHelper().suspend(2000);
			count++;
		}

	}

	public static void scrollDownToElementCloseToMiddle(String locator, int retries) {
		LOGGER.info("Scrolling to bottom of page close to middle");
		refreshDeviceSize();
		int count = 0;
		while (!getSyncHelper().isElementDisplayed(locator) && (count < retries)) {
			scrollDownCloseToMiddleAlgorithm();
			getSyncHelper().suspend(2000);
			count++;
		}

	}

	/**
	 * 
	 * @param locator
	 * @param retries
	 * @param startX
	 *            - position where to start swipe on vertical line
	 */
	public static void scrollUpToElement(String locator, int retries, double startX) {
		refreshDeviceSize();
		int count = 0;
		while (!getSyncHelper().isElementDisplayed(locator) && count < retries) {
			scrollUpAlgorithm(startX);
			getSyncHelper().suspend(1500);
			count++;
		}
	}

	/**
	 * public void tap on element
	 */
	public static void tapOnElement(BaseDeviceControl element) {
		int x = element.getCenterX();
		int y = element.getCenterY();
		getDeviceControl().doCoordinateTap(x, y);
	}

	/**
	 * public void tap on coordinates
	 */
	public static void tapOnCoordinates(int x, int y) {
		LOGGER.info(String.format("Tapping on X=[%s], Y=[%s] coordinates", x, y));
		getDeviceControl().doCoordinateTap(x, y);
	}

	/**
	 * public void tap on element with relative offset
	 * 
	 * @param element
	 *            BaseDeviceControl element
	 * @param xRelativeOffset
	 *            offset from element center to the right in percents of element width. Value should
	 *            be from 0 to 1
	 * @param yRelativeOffset
	 *            offset from element center to the top in percents of element height. Value should
	 *            be from 0 to 1
	 */
	public static void tapOnElementWithOffset(BaseDeviceControl element, double xRelativeOffset,
			double yRelativeOffset) {
		int x = element.getCenterX();
		int y = element.getCenterY();
		Dimension dimension = element.getDimension();
		int xAbsoluteOffset = (int) (dimension.width * xRelativeOffset);
		int yAbsoluteOffset = (int) (dimension.height * yRelativeOffset);
		getDeviceControl().doCoordinateTap(x + xAbsoluteOffset, y + yAbsoluteOffset);
	}

	/**
	 * Scroll Down By start and end Coordinates
	 * 
	 * @param pStartY
	 * @param pEndY
	 */
	public static void scrollDownByCoordinates(double pStartY, double pEndY) {
		refreshDeviceSize();
		scrollDownAlgorithm(0.1, pStartY, pEndY);
	}

	/*
	 * Private methods
	 */

	private static void scrollDownAlgorithm() {
		double pStartY = 0;
		double pEndY = 0;
		if (env.getIsMobileIOS()) {
			pStartY = 0.8;
			pEndY = 0.2;
		} else { // Android scrolls faster so the start and end must be gentler
			pStartY = 0.60;
			pEndY = 0.40;
		}
		scrollDownAlgorithm(0.1, pStartY, pEndY);
	}

	private static void scrollUpAlgorithm() {
		Dimension size = getDriver().manage().window().getSize();
		int startY = (int) (size.getHeight() * 0.2);
		int endY = (int) (size.getHeight() * 0.8);
		int startX = (int) (size.getWidth() * 0.1);
		if (env.getIsMobileIOS()) {
			endY -= startY;
		}
		new TouchAction(getDriver()).press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(startX, endY))
				.release().perform();
	}

	private static void scrollDownCloseToMiddleAlgorithm() {
		double pStartY = 0;
		double pEndY = 0;
		if (env.getIsMobileIOS()) {
			pStartY = 0.7;
			pEndY = -0.3;
		} else { // Android scrolls faster so the start and end must be gentler
			pStartY = 0.60;
			pEndY = 0.40;
		}
		scrollDownAlgorithm(0.1, pStartY, pEndY);
	}

	private static void scrollDownAlgorithm(double startX, double pStartY, double pEndY) {
		Dimension size = deviceSize;
		int startY = (int) (size.getHeight() * pStartY);
		int endY = (int) (size.getHeight() * pEndY);
		startX = (int) (size.getWidth() * startX);
		LOGGER.info("Swiping Down...");
		try {
			new TouchAction(getDriver()).press(PointOption.point((int) startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
					.moveTo(PointOption.point((int) startX, endY)).release().perform();
		} catch (WebDriverException wex) {
			LOGGER.warn("Swipe cause error, probably nothing to swipe: " + wex.getMessage());
		}
	}

	/**
	 * Scroll Up Algorithm
	 * 
	 * @param startXrelativeCooficient
	 *            - position where to start swipe on vertical line
	 * 
	 */
	private static void scrollUpAlgorithm(double startXrelativeCooficient) {
		Dimension size = deviceSize;
		int startY = (int) (size.getHeight() * 0.4);
		int endY = (int) (size.getHeight() * 0.8);
		int startX = (int) (size.getWidth() * startXrelativeCooficient);
		LOGGER.info("Swiping Down...");
		try {
			new TouchAction(getDriver()).press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(startX, endY))
					.release().perform();
		} catch (WebDriverException wex) {
			LOGGER.warn("Swipe cause error, probably nothing to swipe: " + wex.getMessage());
		}
	}

	private static DriverWrapper getDriverWrapper() {
		return DriverWrapperManager.getInstance().getPrimaryDriverWrapper();
	}

	private static AppiumDriver<?> getDriver() {
		return (AppiumDriver<?>) getDriverWrapper().getDriver();
	}

	private static DeviceControl getDeviceControl() {
		return getDriverWrapper().getDeviceControl();
	}

	/**
	 * Gets sync helper.
	 *
	 * @return the sync helper
	 */
	public static MobileNativeSyncHelper getSyncHelper() {
		return (MobileNativeSyncHelper) DriverWrapperManager.getInstance().getPrimaryDriverWrapper().getSyncHelper();
	}

	/**
	 * Regex matches boolean.
	 *
	 * @param pattern
	 *            the pattern
	 * @param text
	 *            the text
	 * @return the boolean
	 */
	public static Boolean regexMatches(String pattern, String text) {
		return regexMatches(pattern, text, true);
	}

	/**
	 * Regex matches boolean.
	 *
	 * @param pattern
	 *            the pattern
	 * @param text
	 *            the text
	 * @param caseInsensitive
	 *            the case insensitive
	 * @return the boolean
	 */
	public static Boolean regexMatches(String pattern, String text, Boolean caseInsensitive) {
		LOGGER.info(String.format("Trying to match %s on %s", pattern, text));
		Pattern p;
		if (caseInsensitive) {
			p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		} else {
			p = Pattern.compile(pattern);
		}
		return p.matcher(text).find();
	}

	/**
	 * Switches to a WINDOW within the last webview, that contains a specified element. The element
	 * will be checked in each window 5 times by default.
	 *
	 * @param elementLocator
	 */
	public static void switchToWindowWithinLastWebviewContainingElement(String elementLocator) {
		switchToWindowWithinLastWebviewContainingElement(elementLocator, 5);
	}

	/**
	 * Switches to a WINDOW within the last webview, that contains a specified element
	 *
	 * @param elementLocator
	 * @param numberOfTimesToCheckElementInWindows
	 */
	public static void switchToWindowWithinLastWebviewContainingElement(String elementLocator,
			int numberOfTimesToCheckElementInWindows) {
		// there is currently an issue in iOS where certain webviews contain
		// multiple windows, so tests often fail due to not finding a specific
		// element in the webview's default window, even though the specific
		// webview is
		// correct. This method solves this issue by searching for the element
		// within the multiple windows and then switching to that correct
		// window.
		String webview = "";

		for (int i = 0; i < numberOfTimesToCheckElementInWindows; i++) {
			List<String> webviews = new ArrayList<>(getDriver().getContextHandles());
			webview = webviews.get(webviews.size() - 1);

			if (webview.equals("NATIVE_APP")) {
				getSyncHelper().suspend(20000);
				continue;
			} else {
				LOGGER.debug("Switching to last webview: " + webview);
				LOGGER.debug("Webviews " + getDriver().getContextHandles());
				getDriver().context(webview);
			}

			LOGGER.debug("Window count: " + getDriver().getWindowHandles().size());
			List<String> windows = new ArrayList<>(getDriver().getWindowHandles());

			if (EnvironmentUtil.getInstance().getIsMobileIOS()) {
				Collections.sort(windows, Collections.reverseOrder());
				LOGGER.debug("Windows " + windows);
			}

			long timeLimit = TestHelper.getCurrentGMT6Time() + 500;

			String selectedWindow = "";

			for (String window : windows) {
				LOGGER.debug("Switching to window: " + window);
				getDriver().switchTo().window(window);
				selectedWindow = window;

				while (!queryHelper().doesElementExist(elementLocator) && timeLimit > TestHelper.getCurrentGMT6Time()) {
					LOGGER.debug("Element [" + elementLocator + "] does not exist in the last window.");
				}

				if (!queryHelper().doesElementExist(elementLocator)) {
					LOGGER.debug("Refreshing view.");
					getDriver().navigate().refresh();
					continue;
				} else {
					LOGGER.info("Element [" + elementLocator + "] found in [" + selectedWindow + "] window.");
					return;
				}
			}
		}
	}

	/**
	 * Gets element text.
	 *
	 * @param baseDeviceControl
	 *            the base device control
	 * @param iOSAttribute
	 *            the os attribute
	 * @param androidAttribute
	 *            the android attribute
	 * @return the element text
	 */
	public static String getElementText(BaseDeviceControl baseDeviceControl, String iOSAttribute,
			String androidAttribute) {
		if (env.getIsMobileIOS()) {
			return baseDeviceControl.getAttributeValue(iOSAttribute);
		} else {
			return baseDeviceControl.getAttributeValue(androidAttribute);
		}
	}

	/**
	 * Gets element text.
	 *
	 * @param baseDeviceControl
	 *            the base device control
	 * @return the element text
	 */
	public static String getElementText(BaseDeviceControl baseDeviceControl) {
		return getElementText(baseDeviceControl, "name", "text");
	}

	/**
	 * Gets number.
	 *
	 * @param text
	 *            the text
	 * @return the number
	 */
	public static String getNumber(String text) {
		Pattern pattern = Pattern.compile("[\\d,]+");
		Matcher matcher = pattern.matcher(text);
		matcher.find();
		return matcher.group(0);
	}

	/**
	 * Handle alert.
	 *
	 * @param alertName
	 *            the alert name
	 */
	public static void handleAlert(String alertName) {

		try {
			getDriver().switchTo().alert().accept();
			LOGGER.info("Accepted " + alertName);
		} catch (Exception e) {
			LOGGER.info(alertName + " didn't appear. Continuing...");
		}
	}

	/**
	 * Wait for alert to dismiss.
	 *
	 * @param timeout
	 *            the timeout
	 */
	public static void waitForAlertToDismiss(long timeout) {
		boolean alertDisplayed = false;
		getSyncHelper().suspend(2000); // warm time to get alert displayed
		long endTime = System.currentTimeMillis() + timeout;
		do {
			try {
				getDriver().switchTo().alert();
				LOGGER.info("Alert still on the screen");
				alertDisplayed = true;
			} catch (Exception | Error e) {
				LOGGER.info("Alert was dismissed");
			}
		} while (endTime < System.currentTimeMillis() && alertDisplayed);
	}

	/**
	 * Tap.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
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

		LOGGER.debug(String.format("Tapping at %s, %s", (int) tapX, (int) tapY));
		new TouchAction(getDriver()).tap(PointOption.point((int) tapX, (int) tapY)).perform();

	}

	/**
	 * Wait for video loading icon.
	 */
	public static void waitForVideoLoadingIcon() {
		if (env.getIsMobileIOS()) {
			getSyncHelper().waitForElementToDisappear("@name == 'Loading'");
		} else {
			waitForLoadingIcon();
		}
	}

	/**
	 * Wait for loading icon.
	 *
	 * @param retries
	 *            the retries
	 */
	public static void waitForLoadingIcon(int retries) {
		waitForElementToDisappear(getLoadingIconLocator(), retries);
	}

	/**
	 * Wait for loading icon.
	 */
	public static void waitForLoadingIcon() {
		waitForLoadingIcon(1);
	}

	/**
	 * Gets random option.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param options
	 *            the options
	 * @return the random option
	 */
	public static <T> T getRandomOption(T[] options) {
		int size = options.length;
		return options[new Random().nextInt(size)];
	}

	/**
	 * Gets random option.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param options
	 *            the options
	 * @return the random option
	 */
	public static <T> T getRandomOption(java.util.List<T> options) {
		int size = options.size();
		return options.get(new Random().nextInt(size));
	}

	/*
	 * For IOS picker wheel it is scrolling only one value at a time so used loop until it sets the
	 * value
	 */
	public static void setPickerValue(String value, PickerWheel element) {
		setPickerValueBasic(value, element, "next");
	}

	/**
	 * Sets picker value reverse.
	 *
	 * @param value
	 *            the value
	 * @param element
	 *            the element
	 */
	public static void setPickerValueReverse(String value, PickerWheel element) {
		setPickerValueBasic(value, element, "previous");
	}

	/**
	 * Is attribtue present boolean.
	 *
	 * @param element
	 *            the element
	 * @param attribute
	 *            the attribute
	 * @return the boolean
	 */
	public static boolean isAttribtuePresent(MobileElement element, String attribute) {
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
	 * @param value
	 *            the value
	 * @param element
	 *            the element
	 * @param order
	 *            the order
	 */
	public static void setPickerValueBasic(String value, PickerWheel element, String order) {
		int loopCounter = 0;
		String pickerWheel = element.getMobileElement().getText();
		MobileElement elem = element.getMobileElement();
		while (!pickerWheel.contentEquals(value.trim()) && loopCounter < 30) {
			LOGGER.debug("Initial picker wheel value: " + pickerWheel);
			LOGGER.debug("Sending value to: " + value);
			LOGGER.debug("Loop #" + loopCounter);
			if (!env.getIsMobileIOS()) {
				elem.sendKeys(Keys.BACK_SPACE);
				elem.sendKeys(value);
			} else {
				JavascriptExecutor js = (JavascriptExecutor) getDriver();
				Map<String, Object> params = new HashMap<>();
				params.put("order", order);
				params.put("offset", 0.1);
				params.put("element", ((RemoteWebElement) elem).getId());
				try {
					js.executeScript("mobile: selectPickerWheelValue", params);
				} catch (WebDriverException wex) {
					LOGGER.error("Exception thrown during picker wheel scroll");
				}
			}
			loopCounter++;
			pickerWheel = elem.getText();
			LOGGER.debug("Updated picker wheel value: " + pickerWheel);
		}
	}

	/**
	 * Generate random string of length 4
	 * 
	 * @return String
	 */
	public static String getRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 4) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return WordUtils.capitalize(saltStr.toLowerCase());
	}

	/**
	 * Generate random number
	 * 
	 * @param limit
	 * @return int
	 */
	public static int getRandomNum(int limit) {
		Random rand = new Random();
		return rand.nextInt(limit);
	}

	/**
	 * * Gets elements collection.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @param locator
	 *            the locator
	 * @return the elements collection
	 */
	public static <T extends AbstractDeviceUIData> List<T> getElementsCollection(Class<T> clazz, String locator) {
		int total = 0;
		if (env.getIsMobileIOS()) {
			if (locator.startsWith("//") || locator.startsWith("(/")) {
				total = getDriver().findElements(MobileBy.xpath(locator.replace("[<<s>>]", ""))).size();
			} else if (locator.startsWith("XCUI") || locator.startsWith("**")) {
				total = getDriver().findElements(MobileBy.iOSClassChain(locator.replace("[<<s>>]", ""))).size();
			} else {
				throw new RuntimeException("Invalid locator strategy. Expected xpath or iOS class chain");
			}
		} else {
			total = getDriver().findElements(By.xpath(locator.replace("[<<s>>]", ""))).size();
		}

		List<T> result = new ArrayList<>();

		for (int i = 1; i <= total; i++) {
			try {
				T element = clazz.getConstructor(String.class)
						.newInstance((String.format(locator.replace("<<s>>", "%s"), i)));
				result.add(element);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException("Error elements list creation1");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException("Error elements list creation2");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException("Error elements list creation3");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw new RuntimeException("Error elements list creation4");
			}

		}
		return result;
	}

	/**
	 * Gets chunks collection factory.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @param locator
	 *            the locator
	 * @return the chunks collection factory
	 */
	public static <T extends AbstractDeviceChunk> List<T> getChunksCollectionFactory(Class<T> clazz, String locator) {
		int total;
		if (env.getIsMobileIOS()) {
			if (locator.startsWith("//") || locator.startsWith("(/")) {
				total = getDriver().findElements(MobileBy.xpath(locator.replace("[<<s>>]", ""))).size();
			} else if (locator.startsWith("XCUI") || locator.startsWith("**")) {
				total = getDriver().findElements(MobileBy.iOSClassChain(locator.replace("[<<s>>]", ""))).size();
			} else {
				throw new RuntimeException("Invalid locator strategy. Expected xpath or iOS class chain");
			}
		} else {
			total = getDriver().findElements(By.xpath(locator.replace("[<<s>>]", ""))).size();
		}

		List<T> result = new ArrayList<>();
		for (int i = 1; i <= total; i++) {
			T element = DeviceChunkFactory.create(clazz, String.format(locator.replace("<<s>>", "%s"), i));
			result.add(element);
		}
		return result;
	}

	public static Boolean waitForElementToDisappear(String selector, int retries) {
		int count = 0;
		LOGGER.debug(String.format("Waiting for element with selector: %s to disappear", selector));
		while (count < retries) {
			try {
				getSyncHelper().waitForElementToDisappear(selector);
				return true;
			} catch (Exception e) {
				getSyncHelper().suspend(1000);
			}
			count++;
		}
		throw new RuntimeException(String.format("Element with selector '%s' is still displayed after the timeout.",
				new Object[] { selector }));
	}

	public static Boolean waitForElementToDisappearDOM(String selector, int retries) {
		int count = 0;
		Boolean disappeared = false;
		while (!disappeared && (count < retries)) {
			try {
				getSyncHelper().waitForElementToAppear(selector);
				disappeared = false;
				getSyncHelper().suspend(1000);
			} catch (AssertionError e) {
				disappeared = true;
				return disappeared;
			}
			count++;
		}
		throw new RuntimeException(String.format("Element with selector '%s' is still displayed after the timeout.",
				new Object[] { selector }));
	}

	public static void tapButtonAndExpectElementWithRetries(Button button, String expectedLocator, int retries) {
		int count = 0;
		button.tap();
		boolean elementIsDisplayed = getSyncHelper().isElementDisplayed(expectedLocator, 3000);
		while (!elementIsDisplayed && count < retries) {
			LOGGER.debug("Expected element was not displayed. Tapping button again..." + (count + 1));
			button.tap();
			elementIsDisplayed = getSyncHelper().isElementDisplayed(expectedLocator);
			count++;
		}
	}

	public static void tapButtonWithRetries(Button button, int retries) {
		int count = 0;
		String selector = button.getSelector();
		button.tap();
		boolean elementIsDisplayed = getSyncHelper().isElementDisplayed(selector, 3000);
		while (elementIsDisplayed && count < retries) {
			LOGGER.debug("Tapping button again..." + (count + 1));
			button.tap();
			elementIsDisplayed = getSyncHelper().isElementDisplayed(selector);
			count++;
		}
		if (elementIsDisplayed) {
			Assert.fail("Button could not be tapped");
		}
	}

	public static void tapAndroidDeviceBackButton() {
		LOGGER.info("Tapping on device back button");
		getAndroidDriver().pressKey(new KeyEvent().withKey(AndroidKey.BACK));
	}

	public static void scrollDownHalfScreen(int swipeLimit) {
		refreshDeviceSize();
		LOGGER.info("Scrolling down half a screen");

		scrollDownAlgorithm(0.1, 0.6, 0.4);
	}

	private static String getLoadingIconLocator() {
		String androidLocator = "progressBar";
		String iOSLocator = "**/XCUIElementTypeActivityIndicator";
		if (env.getIsMobileIOS()) {
			return iOSLocator;
		} else {
			return androidLocator;
		}
	}

	private static DeviceElementQueryHelper queryHelper() {
		return (DeviceElementQueryHelper) DriverWrapperManager.getInstance().getPrimaryDriverWrapper().getQueryHelper();
	}

	public static Element getElementFromPageSource(String locator) {
		String pageSource = getDriver().getPageSource();
		LOGGER.debug("PAGE SOURCE: " + pageSource);
		return getElementFromPageSource(locator, pageSource);
	}

	public static Element getElementFromPageSource(String locator, String pageSource) {
		Element element = null;
		XPath xPath = XPathFactory.newInstance().newXPath();
		LOGGER.debug(String.format("Looking for locator: %s in page source: %s", locator, pageSource));
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(pageSource));
			Document doc = db.parse(is);
			element = (Element) xPath.compile(locator).evaluate(doc, XPathConstants.NODE);
		} catch (Throwable th) {
			LOGGER.info("Something went wrong: " + th.getMessage());
		}
		return element;
	}

	public static NodeList getElementsFromPageSource(String locator, String pageSource) {
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList element = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(pageSource));
			Document doc = db.parse(is);
			element = (NodeList) xPath.compile(locator).evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException | ParserConfigurationException | IOException | SAXException e) {
			LOGGER.info("Something went wrong: " + e.getMessage());
		}
		return element;
	}

	public static void waitForElementFromPageSource(String locator, int retryCount) {
		int count = 0;
		Boolean found = false;
		while (count < retryCount && !found) {
			LOGGER.debug("Try: " + (count + 1));
			try {
				getElementFromPageSource(locator);
				found = true;
			} catch (Exception e) {
				// Do nothing
			}
			count++;
		}

		if (!found) {
			Assert.fail(String.format("Element [%s] was not found before timeout.", locator));
		}

	}

	/**
	 * Gets device hour
	 *
	 * @return String
	 */
	public static String getDeviceHour() {
		LOGGER.info("Getting device time");
		if (env.getIsMobileIOS()) {
			String datetime = getIOSDriver().findElementByXPath("//XCUIElementTypeOther[contains(@name,':')]")
					.getText();
			String time = datetime.split(":")[0];
			if (datetime.contains("PM")) {
				int PM = Integer.parseInt(time) + 12;
				time = String.valueOf(PM);
			} else if (datetime.contains("AM") && time.equals("12")) {
				int AM = Integer.parseInt(time) - 12;
				time = String.valueOf(AM);
			}
			LOGGER.debug("Device Time: " + datetime);
			return time;
		} else {
			String datetime = getAndroidDriver().getDeviceTime();
			LOGGER.debug("Device Time: " + datetime);
			Pattern pattern = Pattern.compile("\\d{1,2}:\\d{2}:\\d{2}");
			Matcher matcher = pattern.matcher(datetime);
			matcher.find();
			String time = matcher.group(0);
			return time.split(":")[0];
		}
	}

	public static void closeControlPanel() {
		MobileElement mainWindow = MobileHelper.getIOSDriver()
				.findElement(MobileBy.xpath("//XCUIElementTypeWindow[1]"));
		if (controlPanelIsOpen) {
			int width = mainWindow.getSize().getWidth();
			int height = mainWindow.getSize().getHeight();

			int x = (int) (width * 0.5);
			int y = (int) (height * 0.3);
			new TouchAction(getDriver()).tap(PointOption.point(x, y)).release().perform();

			getSyncHelper().suspend(2000); // wait for animation
			controlPanelIsOpen = false;
		}

	}

	// iOS only
	public static void toggleWifiControlPanel() {
		MobileElement wiFiButton = MobileHelper.getIOSDriver()
				.findElement(MobileBy.xpath("//*[starts-with(@label, 'Wi-Fi')]"));
		if (!controlPanelIsOpen) {
			throw new IllegalStateException("Control panel is not open");
		}
		int x = wiFiButton.getLocation().getX();
		int y = wiFiButton.getLocation().getY();
		try {
			LOGGER.info("Tapping on 'Wi-Fi'");
			wiFiButton.click();
		} catch (Exception e) {
			LOGGER.info(String.format("Tapping at %s, %s", x, y));
			new TouchAction(getDriver()).tap(PointOption.point(x, y));
		}
		getSnapshotManager().takeRemoteDeviewScreenshotWithDescription("WiFiState", "Current wi-fi- state");
		wifiOff = !wifiOff;
	}

	// Android only
	public static void androidTurnOffNetwork() {
		androidToggleAirplaneMode(false);
		androidToggleWifi(false);

		getSyncHelper().suspend(15000);
		MobileElement button = null;
		// On samsung phone, sometimes pop up appears when network turns off
		try {
			button = getAndroidDriver().findElement(MobileBy.id("android:id/button1"));
		} catch (Exception e) {
			// do nothing
		} finally {
			if (button != null) {
				button.click();
			}
		}
	}

	/**
	 * Android Turn on/off Airplane Mode
	 * 
	 * @param status
	 *            status - turn on/off Airplane Mode
	 */
	public static void androidToggleAirplaneMode(Boolean status) {
		try {
			if (status) {
				LOGGER.info("Turning on AirplaneMode");
				getSyncHelper().suspend(2000);
				getAndroidDriver().setConnection(new ConnectionState(ConnectionState.AIRPLANE_MODE_MASK));
			} else {
				LOGGER.info("Turning off AirplaneMode");
				getSyncHelper().suspend(2000);
				getAndroidDriver().setConnection(new ConnectionStateBuilder().withAirplaneModeDisabled().build());
			}
		} catch (WebDriverException e) {
			LOGGER.debug("Something happened during AirplaneMode disabling: " + e.getMessage());
		}
		getSyncHelper().suspend(5000);
	}

	public static void androidToggleWifi(Boolean status) {
		try {
			if (status) {
				LOGGER.info("Turning on WiFi");
				getSyncHelper().suspend(2000);
				ConnectionState state = getAndroidDriver()
						.setConnection(new ConnectionStateBuilder(getAndroidDriver().getConnection()).withWiFiEnabled()
								.withDataDisabled().build());
				LOGGER.info(String.format("Airplane Mode state is - [%s]; Data state is - [%s]; WiFi state is - [%s]",
						state.isAirplaneModeEnabled(), state.isDataEnabled(), state.isWiFiEnabled()));
				wifiOff = false;
			} else {
				LOGGER.info("Turning off WiFi");
				getSyncHelper().suspend(2000);
				ConnectionState state = getAndroidDriver()
						.setConnection(new ConnectionStateBuilder(getAndroidDriver().getConnection()).withWiFiDisabled()
								.withDataDisabled().build());
				LOGGER.info(String.format("Airplane Mode state is - [%s]; Data state is - [%s]; WiFi state is - [%s]",
						state.isAirplaneModeEnabled(), state.isDataEnabled(), state.isWiFiEnabled()));
				wifiOff = true;
			}
		} catch (WebDriverException e) {
			LOGGER.debug("Something happened during wifi toggling: " + e.getMessage());
		}
		getSyncHelper().suspend(5000);
	}

	// Android only
	public static void androidTurnOnNetwork() {
		getAndroidDriver().setConnection(new ConnectionState(ConnectionState.WIFI_MASK));
		wifiOff = false;
	}

	/**
	 * Check if current run is Mobile IOS
	 * 
	 * @return boolean
	 */
	public static boolean isMobileIOS() {
		return env.getIsMobileIOS();
	}

	public static MobileScreenshotManager getSnapshotManager() {
		return (MobileScreenshotManager) DriverWrapperManager.getInstance().getPrimaryDriverWrapper()
				.getSnapshotManager();
	}

	// public static boolean isElementDisplayed(String selector, int timeout) {
	// WebElement element = null;
	// DeviceElementQueryHelper queryHelper = new DeviceElementQueryHelper(getDriver());
	// getDriver().manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
	// long end = TestHelper.getCurrentGMT6Time() + (long) timeout;
	// while (TestHelper.getCurrentGMT6Time() < end) {
	// try {
	// element = queryHelper.findElement(selector);
	// if (element == null) {
	// LOGGER.info("Waiting for " + selector + " to appear");
	// } else if (element != null) {
	// if (element.isDisplayed()) {
	// return true;
	// }
	// }
	// } catch (NoSuchElementException var8) {
	// LOGGER.debug("Element [" + selector + "] wasn't located, waiting and rerunning loop");
	// }
	// }
	// return false;
	// }

}
