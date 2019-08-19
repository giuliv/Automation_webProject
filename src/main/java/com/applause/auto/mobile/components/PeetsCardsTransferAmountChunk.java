package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = PeetsCardsTransferAmountChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IOSPeetsCardsTransferAmountChunk.class, on = Platform.MOBILE_IOS)
public class PeetsCardsTransferAmountChunk extends BaseComponent {

	public PeetsCardsTransferAmountChunk(String selector) {
		super(selector);
	}

	/**
	 * Enter card number.
	 *
	 * @param cardNumber
	 *            the card number
	 */
	public void enterCardNumber(String cardNumber) {
		logger.info("Enter card number: " + cardNumber);
		getCardNumberTextBox.clickBox();
		getCardNumberTextBox.clearText();
		getCardNumberTextBox.sendKeys(cardNumber);
	}

	/**
	 * Enter card pin.
	 *
	 * @param cardNumber
	 *            the card number
	 */
	public void enterCardPin(String cardNumber) {
		logger.info("Enter card pin: " + cardNumber);
		getCardPinTextBox.clickBox();
		getCardPinTextBox.clearText();
		getCardPinTextBox.sendKeys(cardNumber);
	}

	/**
	 * Transfer peets cards transfer amount warning chunk.
	 *
	 * @return the peets cards transfer amount warning chunk
	 */
	public PeetsCardsTransferAmountWarningChunk transfer() {
		logger.info("Tap on transfer button");
		getDriver().hideKeyboard();
		getTransferButton.click();
		return DeviceComponentFactory.create(PeetsCardsTransferAmountWarningChunk.class, "");
	}

	/**
	 * Gets card number.
	 *
	 * @return the card number
	 */
	public String getCardNumber() {
		return getCardNumberTextBox.getCurrentText().replace(" ", "");
	}

	/**
	 * Gets pin number.
	 *
	 * @return the pin number
	 */
	public String getPinNumber() {
		return getCardPinTextBox.getCurrentText().replace(" ", "");
	}

	@Locate(xpath = "(//XCUIElementTypeTextField)[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/cardNumber", on = Platform.MOBILE_ANDROID)
	protected TextBox getCardNumberTextBox;

	@Locate(xpath = "(//XCUIElementTypeTextField)[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/pinCode", on = Platform.MOBILE_ANDROID)
	protected TextBox getCardPinTextBox;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"Transfer Value\"])[2]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/mergeCards", on = Platform.MOBILE_ANDROID)
	protected Button getTransferButton;

	@Locate(id = "Enter your card info to transfer the balance to your digital card.", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/transferValueLayout", on = Platform.MOBILE_ANDROID)
	protected ContainerElement getSignature;

}

class IOSPeetsCardsTransferAmountChunk extends PeetsCardsTransferAmountChunk {
	public IOSPeetsCardsTransferAmountChunk(String selector) {
		super(selector);
	}

	public PeetsCardsTransferAmountWarningChunk transfer() {
		logger.info("Tap on transfer button");
		MobileHelper.hideKeyboardIOSByPressDone();
		getTransferButton.click();
		return DeviceComponentFactory.create(PeetsCardsTransferAmountWarningChunk.class, "");
	}

}