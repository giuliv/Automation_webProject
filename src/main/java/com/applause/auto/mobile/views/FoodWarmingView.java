package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = FoodWarmingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = FoodWarmingView.class, on = Platform.MOBILE_IOS)
public class FoodWarmingView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
	protected Button navigateBackButton;

	@Locate(xpath = "//android.widget.TextView[@text='Warm']", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Warm\"`]", on = Platform.MOBILE_IOS)
	protected Button chooseWarmButton;

	@Locate(id = "com.wearehathway.peets.development:id/saveChangesButton", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]", on = Platform.MOBILE_IOS)
	protected Button saveChangesButton;

	/* -------- Actions -------- */
	public FoodWarmingView warm() {
		logger.info("Select Warm");
		chooseWarmButton.click();
		return this;
	}

	public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
		saveChangesButton.click();
		return this.create(clazz);
	}
}
