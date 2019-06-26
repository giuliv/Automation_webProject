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

@AndroidImplementation(CreditCardDetailsView.class)
@IosImplementation(IosCreditCardDetailsView.class)
public class CreditCardDetailsView extends AbstractDeviceView {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getLocator(this, "getSaveCardButton"));
    }

    // Public actions

    /**
     * Get Header Text
     *
     * @return String
     */
    public String getHeader() {
        LOGGER.info("Getting Header Text");
        return getHeaderText().getStringValue().trim();
    }

    /**
     * Get Credit Card Name
     *
     * @return String
     */
    public String getCreditCardName() {
        LOGGER.info("Getting Credit Card Name");
        return getCCNameText().getStringValue().trim();
    }

    /**
     * Checking for Default Card text
     *
     * @return Boolean
     */
    public Boolean isDefaultCardTextPresent() {
        LOGGER.info("Checking for Default Card text");
        return getDefaultCardText().isTextDisplayed();
    }

    /**
     * Update Exp Date
     *
     * @param expDate
     */
    public void enterExpDate(String expDate) {
        LOGGER.info("Entering Expiration Date");
        getExpDateField().clearTextBox();
        getExpDateField().enterText(expDate);
    }

    /**
     * Save Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView saveCard() {
        LOGGER.info("Save Payment Method");
        getDriver().hideKeyboard();
        getSaveCardButton().pressButton();
        return DeviceViewFactory.create(PaymentMethodsView.class);
    }

    /**
     * Click Delete Card
     */
    public void clickDeleteCard() {
        LOGGER.info("Clicking Delete Card");
        getDeleteButton().pressButton();
    }

    /**
     * Cancel Deleting Payment Method
     *
     * @return CreditCardDetailsView
     */
    public CreditCardDetailsView clickDeleteNo() {
        LOGGER.info("Cancel Deleting Payment Method");
        getDeleteNoButton().pressButton();
        return DeviceViewFactory.create(CreditCardDetailsView.class);
    }

    /**
     * Confirm Deleting Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView clickDeleteYes() {
        LOGGER.info("Confirming Deletion of Card");
        getDeleteYesButton().pressButton();
        return DeviceViewFactory.create(PaymentMethodsView.class);
    }

    /**
     * Get Exp Date
     *
     * @return String
     */
    public String getExpDate() {
        return getExpDateField().getCurrentText();
    }

    // Protected getters
    @MobileElementLocator(android = "com.wearehathway.peets.development:id/saveCardButton", iOS = "Save Card")
    protected Button getSaveCardButton() { return new Button(getLocator(this, "getSaveCardButton")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/title", iOS = "//XCUIElementTypeOther[@name=\"Mobile Test\"]")
    protected Text getHeaderText() { return new Text(getLocator(this, "getHeaderText")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/cardName", iOS = "//XCUIElementTypeStaticText[@name=\"Mobile Test\"]")
    protected Text getCCNameText() { return new Text(getLocator(this, "getCCNameText")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/defaultText", iOS = "Set as Default Payment")
    protected Text getDefaultCardText() { return new Text(getLocator(this, "getDefaultCardText")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/expiry",
            iOS = "//XCUIElementTypeButton[@name=\"Delete Card\"]/preceding-sibling::XCUIElementTypeTextField")
    protected TextBox getExpDateField() { return new TextBox(getLocator(this, "getExpDateField")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/deleteCardBtn", iOS = "Delete Card")
    protected Button getDeleteButton() { return new Button(getLocator(this, "getDeleteButton")); }

    @MobileElementLocator(android = "android:id/button2", iOS = "No")
    protected Button getDeleteNoButton() { return new Button(getLocator(this, "getDeleteNoButton")); }

    @MobileElementLocator(android = "android:id/button1", iOS = "Yes")
    protected Button getDeleteYesButton() { return new Button(getLocator(this, "getDeleteYesButton")); }
}

class IosCreditCardDetailsView extends CreditCardDetailsView {

    public PaymentMethodsView saveCard() {
        LOGGER.info("Save Payment Method");
        getKeyboardDoneButton().pressButton();
        getSaveCardButton().pressButton();
        getBackButton().pressButton();
        return DeviceViewFactory.create(PaymentMethodsView.class);
    }

    @MobileElementLocator(iOS = "Done")
    protected Button getKeyboardDoneButton() { return new Button(getLocator(this, "getKeyboardDoneButton")); }

    @MobileElementLocator(iOS = "button back")
    protected Button getBackButton() { return new Button(getLocator(this, "getBackButton")); }
}
