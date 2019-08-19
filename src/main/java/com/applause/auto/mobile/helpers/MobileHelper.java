package com.applause.auto.mobile.helpers;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.applause.auto.common.data.Constants.MobileApp;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.pageobjectmodel.elements.BaseElement;
import com.applause.auto.pageobjectmodel.elements.Picker;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.util.helper.SyncHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileHelper {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
	private static Dimension deviceSize;

	static {
		refreshDeviceSize();
	}

	private static AppiumDriver getMobileDriver() {
		return (AppiumDriver) DriverManager.getDriver();
	}

	/**
	 * Activates the app
	 */
	public static void activateApp() {
		getMobileDriver().activateApp(MobileApp.IOS_BUNDLE_ID);
	}

	/**
	 * Hide keyboard ios by press done.
	 */
	public static void hideKeyboardIOSByPressDone() {
		logger.info("Hiding keyboard.");
		getMobileDriver().findElement(By.xpath("//*[@name='Done']")).click();
	}

	public static void refreshDeviceSize() {
		deviceSize = DriverManager.getDriver().manage().window().getSize();
	}

	/**
	 * Performs a swipe for the given swipe count
	 *
	 * @param swipeCount
	 */
	public static void swipeWithCount(SwipeDirection swipeDirection, int swipeCount) {
		logger.info("Scrolling to bottom of page");
		for (int i = 0; i < swipeCount; i++) {
			DeviceControl.swipeAcrossScreenWithDirection(swipeDirection);
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
			SyncHelper.sleep(2000);
			count++;
		}

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
	public static void tapOnElementWithOffset(BaseElement element, double xRelativeOffset,
											  double yRelativeOffset) {
		int x = element.getMobileElement().getCenter().x;
		int y = element.getMobileElement().getCenter().y;
		Dimension dimension = element.getDimension();
		int xAbsoluteOffset = (int) (dimension.width * xRelativeOffset);
		int yAbsoluteOffset = (int) (dimension.height * yRelativeOffset);
		DeviceControl.tapElementCoordinates(element, xAbsoluteOffset, y + yAbsoluteOffset);
	}


	private static void scrollDownCloseToMiddleAlgorithm() {
		double pStartY = 0;
		double pEndY = 0;
		if (EnvironmentHelper.isMobileIOS(getMobileDriver())) {
			pStartY = 0.6;
			pEndY = -0.4;
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
		logger.info("Swiping Down...");
		try {
			new TouchAction(getMobileDriver()).press(PointOption.point((int) startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(PointOption.point((int) startX, endY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).release().perform();
		} catch (WebDriverException wex) {
			logger.warn("Swipe cause error, probably nothing to swipe: " + wex.getMessage());
		}
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

		logger.debug(String.format("Tapping at %s, %s", (int) tapX, (int) tapY));
		new TouchAction(getMobileDriver()).tap(PointOption.point((int) tapX, (int) tapY)).perform();

	}


	/**
	 * Sets picker value reverse.
	 *
	 * @param value
	 *            the value
	 * @param element
	 *            the element
	 */
	public static void setPickerValueReverse(String value, Picker element) {
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
			if (!EnvironmentHelper.isMobileIOS(getMobileDriver())) {
				elem.sendKeys(Keys.BACK_SPACE);
				element.setValue(value);
			} else {
				JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
				Map<String, Object> params = new HashMap<>();
				params.put("order", order);
				params.put("offset", 0.1);
				params.put("element", ((RemoteWebElement) elem).getId());
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
}
