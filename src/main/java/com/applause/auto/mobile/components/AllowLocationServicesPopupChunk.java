package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.SelectCoffeeBarView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import org.openqa.selenium.Point;

@Implementation(is = AndroidAllowLocationServicesPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AllowLocationServicesPopupChunk.class, on = Platform.MOBILE_IOS)
public class AllowLocationServicesPopupChunk extends BaseComponent {

	public AllowLocationServicesPopupChunk(String selector) {
		super(selector);
	}

	/**
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getImageAltText() {
		return getImageAltTextText.getText().replaceAll("’", "'").replaceAll("\\.$", "");
	}

	/**
	 * Gets formatted message.
	 *
	 * @return the formatted message
	 */
	public String getFormattedMessage() {
		return String.format("%s %s %s %s %s",
				getSubTitleText.getText().replaceAll("’", "'").replaceAll("\\.$", ""), getImageAltText(),
				getMessageText1Text.getText().replaceAll("• ", ""),
				getMessageText2Text.getText().replaceAll("• ", ""),
				getMessageText3Text.getText().replaceAll("• ", ""));
	}

	/**
	 * Is allow button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAllowButtonDisplayed() {
		return SyncHelper.isElementDisplayed(getLocator(this, "getAllowButton"));
	}

	/**
	 * Is not now button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isNotNowButtonDisplayed() {
		return SyncHelper.isElementDisplayed(getLocator(this, "getNotNowButton"));
	}

	/**
	 * Allow select coffee bar view.
	 *
	 * @return the select coffee bar view
	 */
	public SelectCoffeeBarView allow() {
		logger.info("Tap Allow button");
		getAllowButton.click();
		AllowLocationServicesSystemPopupChunk allowLocationServicesSystemPopupChunk = DeviceComponentFactory
				.create(AllowLocationServicesSystemPopupChunk.class, "");

		logger.info("Tap Allow");
		allowLocationServicesSystemPopupChunk.allow();
		return ComponentFactory.create(SelectCoffeeBarView.class);
	}

	/**
	 * Not now allow location services system popup chunk.
	 *
	 * @return the allow location services system popup chunk
	 */
	public void notNow() {
		logger.info("Tap Not Now button");
		getNotNowButton.click();
	}

	@Locate(id = "Allow Location Services to help you find nearby Peet’s Coffeebars.", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/allowButton", on = Platform.MOBILE_ANDROID)
	protected ContainerElement getSignature;

	@Locate(id = "Allow Location Services to help you find nearby Peet’s Coffeebars.", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/textView3", on = Platform.MOBILE_ANDROID)
	protected Text getImageAltTextText;

	@Locate(id = "Allow", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/allowButton", on = Platform.MOBILE_ANDROID)
	protected Button getAllowButton;

	@Locate(id = "Not Now", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/notNowButton", on = Platform.MOBILE_ANDROID)
	protected Button getNotNowButton;

	@Locate(id = "Location Services will:", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/textView4", on = Platform.MOBILE_ANDROID)
	protected Text getSubTitleText;

	@Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/textView5", on = Platform.MOBILE_ANDROID)
	protected Text getMessageText1Text;

	@Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/textView6", on = Platform.MOBILE_ANDROID)
	protected Text getMessageText2Text;

	@Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Location Services will:\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[3]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/textView7", on = Platform.MOBILE_ANDROID)
	protected Text getMessageText3Text;
}

class AndroidAllowLocationServicesPopupChunk extends AllowLocationServicesPopupChunk {

	public AndroidAllowLocationServicesPopupChunk(String selector) {
		super(selector);
	}

	public void notNow() {
		logger.info("Tap Not Now button");
		Point point = getNotNowButton.getMobileElement().getCenter();
		new TouchAction(getDriver()).tap(PointOption.point(point.x, point.y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.tap(PointOption.point(point.x + getDriver().manage().window().getSize().width / 4,
						point.y + getDriver().manage().window().getSize().height / 10))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).tap(PointOption.point(point.x, point.y))
				.perform();
	}

	@Override
	public SelectCoffeeBarView allow() {
		logger.info("Tap Allow button");
		getAllowButton.click();
		AllowLocationServicesSystemPopupChunk allowLocationServicesSystemPopupChunk = DeviceComponentFactory
				.create(AllowLocationServicesSystemPopupChunk.class, "");

		logger.info("Tap Settings");
		getSettingsButton.click();

		logger.info("Tap Permissions");
		MobileHelper.scrollDownHalfScreen(1);
		getPermissionsButton.click();

		logger.info("Toggle Locations");
		if (!getLocationsCheckbox.isChecked()) {
			getLocationsCheckbox.click();
		}

		logger.info("Navigate back");
		getNavigateBackButton.click();
		SyncHelper.sleep(5000);
		getNavigateBackButton.click();
		SyncHelper.sleep(5000);

		return ComponentFactory.create(SelectCoffeeBarView.class);
	}

	@Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
	protected Button getSettingsButton;

	@Locate(xpath = "//android.widget.TextView[@text='Permissions']", on = Platform.MOBILE_ANDROID)
	protected Button getPermissionsButton;

	@Locate(xpath = "//android.widget.RelativeLayout/android.widget.TextView[@text='Location']/../..//android.widget.Switch", on = Platform.MOBILE_ANDROID)
	protected Checkbox getLocationsCheckbox;

	@Locate(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", on = Platform.MOBILE_ANDROID)
	protected Button getNavigateBackButton;

}