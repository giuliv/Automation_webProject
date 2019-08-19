package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.QueryHelper;
import com.applause.auto.util.helper.SyncHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = AndroidPeetsSettingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetsSettingsView.class, on = Platform.MOBILE_IOS)
public class PeetsSettingsView extends BaseComponent {

	/**
	 * Open location.
	 */
	public void openLocation() {
		if (QueryHelper.elementCount(getLocator(this, "getLocationButton")) == 0) {
			MobileHelper.scrollDownToElementCloseToMiddle(getLocator(this, "getPeetsAppMenuItem"), 30);
			getPeetsAppMenuItem.tap();
		}
		logger.info("Open Location menu");
		getLocationButton.tap();
	}

	/**
	 * Select never.
	 */
	public void selectNever() {
		SyncHelper.sleep(5000);
		logger.info("Select Never");

		getNeverButton.tap();
	}

	/**
	 * Back to app general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView backToApp() {
		logger.info("Returning to application");
		MobileHelper.activateApp();
		return ComponentFactory.create(GeneralSettingsView.class);
	}

	@Locate(xpath = "//XCUIElementTypeApplication[@name=\"Settings\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.android.settings:id/main_content", on = Platform.MOBILE_ANDROID)
	protected TextBox getSignature;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Peet's\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "", on = Platform.MOBILE_ANDROID)
	protected TextBox getPeetsAppMenuItem;

	@Locate(xpath = "//XCUIElementTypeCell[@name=\"Location\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.android.settings:id/switch_widget", on = Platform.MOBILE_ANDROID)
	protected Button getLocationButton;

	@Locate(xpath = "//XCUIElementTypeCell[@name=\"Never\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.Button[@text='CLOSE']", on = Platform.MOBILE_ANDROID)
	protected Button getNeverButton;
}

class AndroidPeetsSettingsView extends PeetsSettingsView {
	@Override
	public void openLocation() {
		logger.info("Open Location menu");
		getLocationButton.tap();
	}

	@Override
	public GeneralSettingsView backToApp() {
		logger.info("Returning to application");
		MobileHelper.tapAndroidDeviceBackButton();
		return ComponentFactory.create(GeneralSettingsView.class);
	}

}
