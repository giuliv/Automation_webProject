package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.devicecontrols.TextBox;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(AddNewCardView.class)
@IosImplementation(IosAddNewCardView.class)
public class AddNewCardView extends AbstractDeviceView {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getLocator(this, "getViewSignature"));
    }

    // Public actions

    /**
     * Enter Card Number
     *
     * @param cardNum
     */
    public void enterCardNumber(String cardNum) {
        LOGGER.info("Entering Card Number");
        getCardNumberTextBox().enterText(cardNum);
    }

    /**
     * Enter Expiration Date
     *
     * @param expDate
     */
    public void enterExpDate(String expDate) {
        LOGGER.info("Entering Exp Date");
        getExpDateTextBox().enterText(expDate);
    }

    /**
     * Enter CVV Code
     *
     * @param cvv
     */
    public void enterCvvCode(String cvv) {
        LOGGER.info("Entering CVV Code");
        getCvvTextBox().enterText(cvv);
    }

    /**
     * Enter Zip Code
     *
     * @param zipCode
     */
    public void enterZipCode(String zipCode) {
        LOGGER.info("Entering Zip Code");
        getZipTextBox().enterText(zipCode);
    }

    /**
     * Enter Card Name
     *
     * @param cardName
     */
    public void enterCardName(String cardName) {
        LOGGER.info("Entering Card Name");
        getCardNameTextBox().enterText(cardName);
    }

    /**
     * Make Payment Method Default
     */
    public void selectMakeDefault() {
        LOGGER.info("Making Card Default");
        getDriver().hideKeyboard();
        getDefaultToggle().pressButton();
    }

    /**
     * Save Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView saveCard() {
        LOGGER.info("Saving Payment Method");
        getSaveCardButton().pressButton();
        return DeviceViewFactory.create(PaymentMethodsView.class);
    }

    // Protected getters
    @MobileElementLocator(android = "//android.widget.TextView[@text='Add New Card']", iOS = "//XCUIElementTypeOther[@name=\"Add New Card\"]")
    protected Text getViewSignature() { return new Text(getLocator(this, "getViewSignature")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/cardNumber", iOS = "**/XCUIElementTypeTextField[`value == 'Card Number'`]")
    protected TextBox getCardNumberTextBox() { return new TextBox(getLocator(this, "getCardNumberTextBox")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/expiryDate", iOS = "**/XCUIElementTypeTextField[`value == 'MM/YY'`]")
    protected TextBox getExpDateTextBox() { return new TextBox(getLocator(this, "getExpDateTextBox")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/cvv", iOS = "**/XCUIElementTypeTextField[`value == 'CVV'`]")
    protected TextBox getCvvTextBox() { return new TextBox(getLocator(this, "getCvvTextBox")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/zip", iOS = "**/XCUIElementTypeTextField[`value == 'Zip'`]")
    protected TextBox getZipTextBox() { return new TextBox(getLocator(this, "getZipTextBox")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/cardName", iOS = "**/XCUIElementTypeTextField[`value == 'Card Name'`]")
    protected TextBox getCardNameTextBox() { return new TextBox(getLocator(this, "getCardNameTextBox")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/isDefaultSwitch", iOS = "**/XCUIElementTypeSwitch")
    protected Button getDefaultToggle() { return new Button(getLocator(this, "getDefaultToggle")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/saveCardButton", iOS = "Save Card")
    protected Button getSaveCardButton() { return new Button(getLocator(this, "getSaveCardButton")); }
}

class IosAddNewCardView extends AddNewCardView {

    public void selectMakeDefault() {
        LOGGER.info("Making Card Default");
        getKeyboardDoneButton().pressButton();
        getDefaultToggle().pressButton();
    }

    @MobileElementLocator(iOS = "Done")
    protected Button getKeyboardDoneButton() { return new Button(getLocator(this, "getKeyboardDoneButton")); }
}
