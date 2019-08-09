package com.applause.auto.mobile.views;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

@AndroidImplementation(AndroidPaymentMethodsView.class)
@IosImplementation(PaymentMethodsView.class)
public class PaymentMethodsView extends AbstractDeviceView {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getLocator(this, "getViewSignature"));
	}



	/**
	 * Check for Peets Card Header
	 *
	 * @return String
	 */
	public String getPeetsCardHeader() {
		LOGGER.info("Checking for Peets Card Header");
		return getPeetsCardHeaderText().getStringValue();
	}

	/**
	 * Check for Saved Payment Method Header
	 *
	 * @return String
	 */
	public String getSavedPaymentHeader() {
		LOGGER.info("Getting Saved Payment Method Header");
		return getSavedPaymentMethodHeaderText().getStringValue();
	}

	/**
	 * Click Peets Card
	 *
	 * @return PeetsCardSettingsView
	 */
	public PeetsCardSettingsView clickPeetsCard() {
		LOGGER.info("Clicking Peets Card");
		getPeetsCard().pressButton();
		return DeviceViewFactory.create(PeetsCardSettingsView.class);
	}

	/**
	 * Click Payment Method
	 *
	 * @return CreditCardDetailsView
	 */
	public <T extends AbstractDeviceView> T clickSavedPaymentMethod(Class<T> clazz) {
		LOGGER.info("Clicking Payment Method");
		getSavedPaymentMethodButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}

	/**
	 * Click saved payment method 2 t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends AbstractDeviceView> T clickSavedPaymentMethod2(Class<T> clazz) {
		LOGGER.info("Clicking Payment Method");
		getSavedPaymentMethod2Button().pressButton();
		return DeviceViewFactory.create(clazz);
	}

	/**
	 * Click Add New Payment Button
	 *
	 * @return AddNewCardView
	 */
	public AddNewCardView clickAddNewPayment() {
		LOGGER.info("Clicking Add New Payment");
		getAddNewPaymentButton().pressButton();
		return DeviceViewFactory.create(AddNewCardView.class);
	}


	@MobileElementLocator(android = "com.wearehathway.peets.development:id/title", iOS = "//*[contains(@name,\"Payment Method\")][1]")
	protected Text getViewSignature() {
		return new Text(getLocator(this, "getViewSignature"));
	}

	@MobileElementLocator(android = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", iOS = "button back")
	protected Button getBackButton() {
		return new Button(getLocator(this, "getBackButton"));
	}

	@MobileElementLocator(android = "new UiSelector().resourceIdMatches(\".*id/sectionHeading\")", iOS = "//XCUIElementTypeStaticText[@name=\"Your Peet's Card\"]")
	protected Text getPeetsCardHeaderText() {
		return new Text(getLocator(this, "getPeetsCardHeaderText"));
	}

	@MobileElementLocator(android = "new UiSelector().resourceIdMatches(\".*id/creditCardView\")", iOS = "Peet's Card")
	protected Button getPeetsCard() {
		return new Button(getLocator(this, "getPeetsCard"));
	}

	@MobileElementLocator(android = "//android.widget.TextView[@text='Saved Payment Methods']", iOS = "//XCUIElementTypeStaticText[@name=\"Saved Payment Methods\"]")
	protected Text getSavedPaymentMethodHeaderText() {
		return new Text(getLocator(this, "getSavedPaymentMethodHeaderText"));
	}

	@MobileElementLocator(android = "//*[contains(@resource-id, 'id/creditCardView')][1]", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell[1]")
	protected Button getSavedPaymentMethod2Button() {
		return new Button(getLocator(this, "getSavedPaymentMethod2Button"));
	}

	@MobileElementLocator(android = "//*[contains(@resource-id, 'id/creditCardView')][3]", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell[3]")
	protected Button getSavedPaymentMethodButton() {
		return new Button(getLocator(this, "getSavedPaymentMethodButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/saveChangesButton", iOS = "")
	protected Button getSaveChangesButton() {
		return new Button(getLocator(this, "getSaveChangesButton"));
	}

	@MobileElementLocator(android = "com.wearehathway.peets.development:id/addPaymentView", iOS = "(//XCUIElementTypeStaticText[@name=\"Add New Payment\"])[3]")
	protected Button getAddNewPaymentButton() {
		return new Button(getLocator(this, "getAddNewPaymentButton"));
	}

}

class AndroidPaymentMethodsView extends PaymentMethodsView {
	public <T extends AbstractDeviceView> T clickSavedPaymentMethod2(Class<T> clazz) {
		LOGGER.info("Selecting Saved card");
		getSavedPaymentMethod2Button().pressButton();
		getSaveChangesButton().pressButton();
		return DeviceViewFactory.create(clazz);
	}
}
