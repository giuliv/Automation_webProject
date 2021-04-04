package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;

@Implementation(is = AddCreamCheeseView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AddCreamCheeseView.class, on = Platform.MOBILE_IOS)
public class AddCreamCheeseView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
	protected Button navigateBackButton;

	@Locate(xpath = "//android.widget.TextView[@text='%s' and @resource-id='com.wearehathway.peets.development:id/productModifierName']/../..//android.widget.ImageButton[@resource-id='com.wearehathway.peets.development:id/decreaseQuantity']", on = Platform.MOBILE_ANDROID)
	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/../XCUIElementTypeButton[@name=\"-\"]", on = Platform.MOBILE_IOS)
	protected Button decreaseAmountButton;

	@Locate(xpath = "//android.widget.TextView[@text='%s' and @resource-id='com.wearehathway.peets.development:id/productModifierName']/../..//android.widget.ImageButton[@resource-id='com.wearehathway.peets.development:id/increaseQuantity']", on = Platform.MOBILE_ANDROID)
	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/../XCUIElementTypeButton[@name=\"+\"]", on = Platform.MOBILE_IOS)
	protected Button increaseAmountButton;

	@Locate(xpath = "//android.widget.TextView[@text='%s' and @resource-id='com.wearehathway.peets.development:id/productModifierName']/../..//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/modifierQuantity']", on = Platform.MOBILE_ANDROID)
	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]/../XCUIElementTypeButton[@name=\"-\"]/preceding-sibling::XCUIElementTypeStaticText[1]", on = Platform.MOBILE_IOS)
	protected Button amountText;

	@Locate(id = "com.wearehathway.peets.development:id/saveChangesButton", on = Platform.MOBILE_ANDROID)
	@Locate(iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Save Changes\"`]", on = Platform.MOBILE_IOS)
	protected Button saveChangesButton;

	/* -------- Actions -------- */

	public <T extends BaseComponent> T saveChanges(Class<T> clazz) {
		saveChangesButton.click();
		return this.create(clazz);
	}

	public AddCreamCheeseView decreaseCount(String category, String value) {
		amountText.format(category).initialize();
		decreaseAmountButton.format(category).initialize();
		int counter = 5;
		while (!amountText.getText().equals(value) && counter-- > 0) {
			decreaseAmountButton.click();
		}
		return this;
	}

	public AddCreamCheeseView incereaseCount(String category, String value) {
		amountText.format(category).initialize();
		increaseAmountButton.format(category).initialize();
		int counter = 5;
		while (!amountText.getText().equals(value) && counter-- > 0) {
			increaseAmountButton.click();
		}
		return this;
	}
}
