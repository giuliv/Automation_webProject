package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = SweetenersView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SweetenersView.class, on = Platform.MOBILE_IOS)
public class SweetenersView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
	protected Button navigateBackButton;

	@Locate(xpath = "//android.widget.TextView[@text='Raw Sugar']/../..//android.widget.ImageButton[@resource-id='com.wearehathway.peets.development:id/increaseQuantity']", on = Platform.MOBILE_ANDROID)
	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Raw Sugar\"]/../XCUIElementTypeButton[@label = '+']", on = Platform.MOBILE_IOS)
	protected Button addRawSugarAmountButton;

	@Locate(xpath = "//android.widget.TextView[@text='Raw Sugar']/../..//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/modifierQuantity']", on = Platform.MOBILE_ANDROID)
	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Raw Sugar\"]/following-sibling::XCUIElementTypeStaticText[2]", on = Platform.MOBILE_IOS)
	protected Text rawSugarAmountText;

	@Locate(id = "com.wearehathway.peets.development:id/saveChangesButton", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]", on = Platform.MOBILE_IOS)
	protected Button saveChangesButton;

	/* -------- Actions -------- */
	public SweetenersView setRawSugarAmount(String amount) {
		int counter = 5;
		while (!rawSugarAmountText.getText().equals(amount) && counter-- > 0) {
			addRawSugarAmountButton.click();
		}
		return this;
	}

	public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
		saveChangesButton.click();
		return this.create(clazz);
	}
}
