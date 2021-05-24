package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = ToppingsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ToppingsView.class, on = Platform.MOBILE_IOS)
public class ToppingsView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
	protected Button navigateBackButton;

	@Locate(id = "com.wearehathway.peets.development:id/saveChangesButton", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]", on = Platform.MOBILE_IOS)
	protected Button saveChangesButton;

	@Locate(xpath = "//android.widget.TextView[@text='Whipped Cream']", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Whipped Cream\"`]", on = Platform.MOBILE_IOS)
	protected Button whippedCreamButton;

	/* -------- Actions -------- */
	public ToppingsView setWhippedCream() {
		whippedCreamButton.click();
		return this;
	}

	public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
		getSyncHelper().sleep(1000);
		getDeviceControl().tapElementCenter(saveChangesButton);
		getSyncHelper().sleep(2000);
		return this.create(clazz);
	}
}
