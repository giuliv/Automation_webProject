package com.applause.auto.pageframework.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceChunkFactory;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.mobile.BottomNavigationMenuChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;

@AndroidImplementation(AndroidCheckInView.class)
@IosImplementation(CheckInView.class)
public class CheckInView extends AbstractDeviceView {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getSignature(), 120000);
	}

	/**
	 * Add value.
	 */
	public void addValue() {
		LOGGER.info("Tap on Add Value");
		getAddValueButton().pressButton();
	}

	/**
	 * Edit payment methods view.
	 *
	 * @return the payment methods view
	 */
	public PaymentMethodsView edit() {
		LOGGER.info("Tap pencil icon");
		getPencilIconButton().pressButton();
		return DeviceViewFactory.create(PaymentMethodsView.class);
	}

	public String getBalance() {
		return getBalanceText().getStringValue();
	}

	/**
	 * Is amount selected boolean.
	 *
	 * @param amount
	 *            the amount
	 * @return the boolean
	 */
	public boolean isAmountSelected(String amount) {
		if (MobileHelper.isAttribtuePresent(getAmountButton(amount).getMobileElement(), "value")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Confirm peets cards view.
	 *
	 * @return the peets cards view
	 */
	public CheckInView confirm() {
		LOGGER.info("Tap on confirm button");
		getConfirmButton().pressButton();
		return DeviceViewFactory.create(CheckInView.class);
	}

	/**
	 * Gets bottom navigation menu.
	 *
	 * @return the bottom navigation menu
	 */
	public BottomNavigationMenuChunk getBottomNavigationMenu() {
		return DeviceChunkFactory.create(BottomNavigationMenuChunk.class, "");

	}

	/*
	 * Protected Getters
	 */

	@MobileElementLocator(android = "//android.widget.TextView[@text=\"Check In\" or @text=\"Add Value To My Peet's Card\"]", iOS = "(//XCUIElementTypeStaticText[(@name=\"Peet's Card\" or @name=\"Add Value to My Peet's Card\") and @visible=\"true\"])[1]")
	protected Text getSignature() {
		return new Text(getLocator(this, "getSignature"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/addValue", iOS = "Add Value")
	protected Button getAddValueButton() {
		return new Button(getLocator(this, "getAddValueButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/amount", iOS = "//XCUIElementTypeStaticText[@name=\"$\"]/following-sibling::XCUIElementTypeStaticText[1]")
	protected Text getBalanceText() {
		return new Text(getLocator(this, "getBalanceText"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/confirmChangesButton", iOS = "Confirm Value")
	protected Button getConfirmButton() {
		return new Button(getLocator(this, "getConfirmButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/editCreditCardBtn", iOS = "button edit pen")
	protected Button getPencilIconButton() {
		return new Button(getLocator(this, "getPencilIconButton"));
	}

	@MobileElementLocator(android = "//android.widget.LinearLayout/android.widget.Button[@text='%s']", iOS = "//XCUIElementTypeOther/XCUIElementTypeButton[@name='%s']")
	protected Button getAmountButton(String amount) {
		return new Button(getLocator(this, "getAmountButton", amount));
	}

}

class AndroidCheckInView extends CheckInView {
	@Override
	public boolean isAmountSelected(String amount) {
		return getAmountButton(amount).getMobileElement().isSelected();
	}

}
