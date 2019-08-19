package com.applause.auto.mobile.components;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceChunk;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.BaseDeviceControl;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(PeetsCardsTransferAmountWarningChunk.class)
@IosImplementation(IOSPeetsCardsTransferAmountWarningChunk.class)
public class PeetsCardsTransferAmountWarningChunk extends AbstractDeviceChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Instantiates a new Peets cards transfer amount warning chunk.
	 *
	 * @param selector
	 *            the selector
	 */
	public PeetsCardsTransferAmountWarningChunk(String selector) {
		super(selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature());
	}

	/**
	 * Gets formatted message.
	 *
	 * @return the formatted message
	 */
	public String getFormattedMessage() {
		return String.format("%s %s %s %s %s", getMainTitleText().getStringValue(), getTitleText().getStringValue(),
				getMessageText1Text().getStringValue(), getMessageText2Text().getStringValue(),
				getMessageText3Text().getStringValue());
	}

	/**
	 * Gets valid message.
	 *
	 * @return the valid message
	 */
	public String getValidMessage() {
		return "One last thing When you transfer a card into the app, you will: Not be able to transfer the value back to the original card No longer be able to add funds to your physical card Be able to access the new value with your digital Peet's Card located in the app";
	}

	/**
	 * Gets formatted message could not process.
	 *
	 * @return the formatted message could not process
	 */
	public String getFormattedMessageCouldNotProcess() {
		return String.format("%s %s %s %s", getMainTitleCouldNotProcessText().getStringValue(),
				getMessageText1CouldNotProcessText().getStringValue(),
				getMessageText2CouldNotProcessText().getStringValue(),
				getMessageText3CouldNotProcessText().getStringValue());
	}

	/**
	 * Gets valid message could not process.
	 *
	 * @return the valid message could not process
	 */
	public String getValidMessageCouldNotProcess() {
		return "We couldn't process your transfer Please check your card number and pin code and try again. If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value. If this issue persist, please contact Peet's customer serviceat cs@peets.com.";
	}

	/**
	 * Is continue button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isContinueButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getContinueButton"));
	}

	/**
	 * Is try again button could not process displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isTryAgainButtonCouldNotProcessDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getTryAgainCouldNotProcessButton"));
	}

	/**
	 * Is cancel button could not process displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isCancelButtonCouldNotProcessDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getCancelCouldNotProcessButton"));
	}

	/**
	 * Is cancel button displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isCancelButtonDisplayed() {
		return syncHelper.isElementDisplayed(getLocator(this, "getCancelButton"));
	}

	/**
	 * Tap continue.
	 */
	public void tapContinue() {
		LOGGER.info("Tap Continue button");
		getContinueButton().pressButton();
	}

	/**
	 * Tap try again.
	 */
	public <T extends AbstractDeviceChunk> T tapTryAgain(Class<T> clazz) {
		LOGGER.info("Tap Try again button");
		getTryAgainCouldNotProcessButton().pressButton();
		return DeviceChunkFactory.create(clazz, "");
	}



	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[1]", iOS = "One last thing")
	protected Text getMainTitleText() {
		return new Text(getLocator(this, "getMainTitleText"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.TextView[2]", iOS = "When you transfer a card into the app, you will:")
	protected Text getTitleText() {
		return new Text(getLocator(this, "getTitleText"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[1]", iOS = "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[1]")
	protected Text getMessageText1Text() {
		return new Text(getLocator(this, "getMessageText1Text"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[2]", iOS = "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[2]")
	protected Text getMessageText2Text() {
		return new Text(getLocator(this, "getMessageText2Text"));
	}

	@MobileElementLocator(android = "//android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView[3]", iOS = "(//XCUIElementTypeStaticText[@name=\"One last thing\"]/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText)[3]")
	protected Text getMessageText3Text() {
		return new Text(getLocator(this, "getMessageText3Text"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView3", iOS = "We couldn’t process your transfer.")
	protected Text getMainTitleCouldNotProcessText() {
		return new Text(getLocator(this, "getMainTitleCouldNotProcessText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView5", iOS = "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[1]")
	protected Text getMessageText1CouldNotProcessText() {
		return new Text(getLocator(this, "getMessageText1CouldNotProcessText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView6", iOS = "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[2]")
	protected Text getMessageText2CouldNotProcessText() {
		return new Text(getLocator(this, "getMessageText2CouldNotProcessText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/textView7", iOS = "(//XCUIElementTypeStaticText[@name=\"We couldn’t process your transfer.\"]/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText)[3]")
	protected Text getMessageText3CouldNotProcessText() {
		return new Text(getLocator(this, "getMessageText3CouldNotProcessText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/continueButton", iOS = "Continue")
	protected Button getContinueButton() {
		return new Button(getLocator(this, "getContinueButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/cancelButton", iOS = "Cancel")
	protected Button getCancelCouldNotProcessButton() {
		return new Button(getLocator(this, "getCancelCouldNotProcessButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/tryAgainButton", iOS = "Try Again")
	protected Button getTryAgainCouldNotProcessButton() {
		return new Button(getLocator(this, "getTryAgainCouldNotProcessButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/cancel", iOS = "Cancel")
	protected Button getCancelButton() {
		return new Button(getLocator(this, "getCancelButton"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='One last thing']", iOS = "One last thing")
	protected BaseDeviceControl getSignature() {
		return new Button(getLocator(this, "getSignature"));
	}
}

class IOSPeetsCardsTransferAmountWarningChunk extends PeetsCardsTransferAmountWarningChunk {
	public IOSPeetsCardsTransferAmountWarningChunk(String selector) {
		super(selector);
	}

	public String getValidMessage() {
		return "One last thing When you transfer a card into the app, you will: Not be able to transfer the value back to the original card No longer be able to add funds to your physical card Be able to access the new value with your digital Peet’s Card located in the app.";
	}

	public String getValidMessageCouldNotProcess() {
		return "We couldn’t process your transfer. Please check your card number and pin code and try again. If there's no value remaining on the card you are trying to transfer, you won't be able to transfer value. If this issue persists, please contact Peet's customer service at cs@peets.com.";
	}

}