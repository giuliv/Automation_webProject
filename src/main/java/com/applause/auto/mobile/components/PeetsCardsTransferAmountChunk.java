package com.applause.auto.mobile.components;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.mobile.helpers.MobileHelper;

@AndroidImplementation(PeetsCardsTransferAmountChunk.class)
@IosImplementation(IOSPeetsCardsTransferAmountChunk.class)
public class PeetsCardsTransferAmountChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	public PeetsCardsTransferAmountChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	/**
	 * Enter card number.
	 *
	 * @param cardNumber
	 *            the card number
	 */
	public void enterCardNumber(String cardNumber) {
		LOGGER.info("Enter card number: " + cardNumber);
		getCardNumberTextBox().clickTextBox();
		getCardNumberTextBox().clearTextBox();
		getCardNumberTextBox().enterText(cardNumber);
	}

	/**
	 * Enter card pin.
	 *
	 * @param cardNumber
	 *            the card number
	 */
	public void enterCardPin(String cardNumber) {
		LOGGER.info("Enter card pin: " + cardNumber);
		getCardPinTextBox().clickTextBox();
		getCardPinTextBox().clearTextBox();
		getCardPinTextBox().enterText(cardNumber);
	}

	/**
	 * Transfer peets cards transfer amount warning chunk.
	 *
	 * @return the peets cards transfer amount warning chunk
	 */
	public PeetsCardsTransferAmountWarningChunk transfer() {
		LOGGER.info("Tap on transfer button");
		getDriver().hideKeyboard();
		getTransferButton().pressButton();
		return DeviceChunkFactory.create(PeetsCardsTransferAmountWarningChunk.class, "");
	}

	/**
	 * Gets card number.
	 *
	 * @return the card number
	 */
	public String getCardNumber() {
		return getCardNumberTextBox().getText().replace(" ", "");
	}

	/**
	 * Gets pin number.
	 *
	 * @return the pin number
	 */
	public String getPinNumber() {
		return getCardPinTextBox().getText().replace(" ", "");
	}



	@MobileElementLocator(android = "com.wearehathway.peets.development:id/cardNumber", iOS = "(//XCUIElementTypeTextField)[1]")
	protected TextBox getCardNumberTextBox() {
		return new TextBox(getLocator(this, "getCardNumberTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/pinCode", iOS = "(//XCUIElementTypeTextField)[2]")
	protected TextBox getCardPinTextBox() {
		return new TextBox(getLocator(this, "getCardPinTextBox"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/mergeCards", iOS = "(//XCUIElementTypeButton[@name=\"Transfer Value\"])[2]")
	protected Button getTransferButton() {
		return new Button(getLocator(this, "getTransferButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/transferValueLayout", iOS = "Enter your card info to transfer the balance to your digital card.")
	protected BaseDeviceControl getSignature() {
		return new Button(getLocator(this, "getSignature"));
	}

}

class IOSPeetsCardsTransferAmountChunk extends PeetsCardsTransferAmountChunk {
	public IOSPeetsCardsTransferAmountChunk(String selector) {
		super(selector);
	}

	public PeetsCardsTransferAmountWarningChunk transfer() {
		LOGGER.info("Tap on transfer button");
		MobileHelper.hideKeyboardIOSByPressDone();
		getTransferButton().pressButton();
		return DeviceChunkFactory.create(PeetsCardsTransferAmountWarningChunk.class, "");
	}

}