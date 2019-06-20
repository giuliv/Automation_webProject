package com.applause.auto.pageframework.chunks.mobile;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import org.openqa.selenium.Point;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

@AndroidImplementation(AndroidAllowLocationServicesPopupChunk.class)
@IosImplementation(AllowLocationServicesPopupChunk.class)
public class AllowLocationServicesPopupChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	public AllowLocationServicesPopupChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getLocator(this, "getSignature"));
	}

	/**
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return getTitleText().getStringValue().replaceAll("’", "'").replaceAll("\\.$", "");
	}

	/**
	 * Gets formatted message.
	 *
	 * @return the formatted message
	 */
	public String getFormattedMessage() {
		return String.format("%s %s %s %s %s",
				getSubTitleText().getStringValue().replaceAll("’", "'").replaceAll("\\.$", ""), getTitle(),
				getMessageText1Text().getStringValue().replaceAll("• ", ""),
				getMessageText2Text().getStringValue().replaceAll("• ", ""),
				getMessageText3Text().getStringValue().replaceAll("• ", ""));
	}

	/**
	 * Is allow button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAllowButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getAllowButton"));
	}

	/**
	 * Is not now button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isNotNowButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getNotNowButton"));
	}

	/**
	 * Allow allow location services system popup chunk.
	 *
	 * @return the allow location services system popup chunk
	 */
	public AllowLocationServicesSystemPopupChunk allow() {
		LOGGER.info("Tap Allow button");
		getAllowButton().pressButton();
		return DeviceChunkFactory.create(AllowLocationServicesSystemPopupChunk.class, "");
	}

	/**
	 * Not now allow location services system popup chunk.
	 *
	 * @return the allow location services system popup chunk
	 */
	public void notNow() {
		LOGGER.info("Tap Not Now button");
		getNotNowButton().pressButton();
	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/allowButton", iOS = "Allow Location Services to help you find nearby Peet’s Coffeebars.")
	protected BaseDeviceControl getSignature() {
		return new BaseDeviceControl(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView3", iOS = "Allow Location Services to help you find nearby Peet’s Coffeebars.")
	protected Text getTitleText() {
		return new Text(getLocator(this, "getTitleText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/allowButton", iOS = "Allow")
	protected Button getAllowButton() {
		return new Button(getLocator(this, "getAllowButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/notNowButton", iOS = "Not Now")
	protected Button getNotNowButton() {
		return new Button(getLocator(this, "getNotNowButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView4", iOS = "Location Services will:")
	protected Text getSubTitleText() {
		return new Text(getLocator(this, "getSubTitleText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView5", iOS = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[1]")
	protected Text getMessageText1Text() {
		return new Text(getLocator(this, "getMessageText1Text"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView6", iOS = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[2]")
	protected Text getMessageText2Text() {
		return new Text(getLocator(this, "getMessageText2Text"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView7", iOS = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[3]")
	protected Text getMessageText3Text() {
		return new Text(getLocator(this, "getMessageText3Text"));
	}
}

class AndroidAllowLocationServicesPopupChunk extends AllowLocationServicesPopupChunk {

	public AndroidAllowLocationServicesPopupChunk(String selector) {
		super(selector);
	}

	public void notNow() {
		LOGGER.info("Tap Not Now button");
		Point point = getNotNowButton().getMobileElement().getCenter();
		new TouchAction(getDriver()).tap(PointOption.point(point.x, point.y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.tap(PointOption.point(point.x + getDriver().manage().window().getSize().width / 4,
						point.y + getDriver().manage().window().getSize().height / 10))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).tap(PointOption.point(point.x, point.y))
				.perform();
	}

}