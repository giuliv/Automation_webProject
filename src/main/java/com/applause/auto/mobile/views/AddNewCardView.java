package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = AddNewCardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosAddNewCardView.class, on = Platform.MOBILE_IOS)
public class AddNewCardView extends BaseComponent {

    // Public actions

    /**
     * Enter Card Number
     *
     * @param cardNum
     */
    public void enterCardNumber(String cardNum) {
        logger.info("Entering Card Number");
        getCardNumberTextBox().sendKeys(cardNum);
    }

    /**
     * Enter Expiration Date
     *
     * @param expDate
     */
    public void enterExpDate(String expDate) {
        logger.info("Entering Exp Date");
        getExpDateTextBox().sendKeys(expDate);
    }

    /**
     * Enter CVV Code
     *
     * @param cvv
     */
    public void enterCvvCode(String cvv) {
        logger.info("Entering CVV Code");
        getCvvTextBox().sendKeys(cvv);
    }

    /**
     * Enter Zip Code
     *
     * @param zipCode
     */
    public void enterZipCode(String zipCode) {
        logger.info("Entering Zip Code");
        getZipTextBox().sendKeys(zipCode);
    }

    /**
     * Enter Card Name
     *
     * @param cardName
     */
    public void enterCardName(String cardName) {
        logger.info("Entering Card Name");
        getCardNameTextBox().sendKeys(cardName);
    }

    /**
     * Make Payment Method Default
     */
    public void selectMakeDefault() {
        logger.info("Making Card Default");
        getDriver().hideKeyboard();
        getDefaultToggle().click();
    }

    /**
     * Save Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView saveCard() {
        logger.info("Saving Payment Method");
        getSaveCardButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
    }

    // Protected getters
    @Locate(xpath = "//XCUIElementTypeOther[@name=\"Add New Card\"]", on = Platform.MOBILE_IOS)
    @Locate(xpath = "//android.widget.TextView[@text='Add New Card']", on = Platform.MOBILE_ANDROID)
    protected Text getViewSignature() { return new Text(getLocator(this, "getViewSignature")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Card Number'`]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/cardNumber", on = Platform.MOBILE_ANDROID)
    protected TextBox getCardNumberTextBox() { return new TextBox(getLocator(this, "getCardNumberTextBox")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'MM/YY'`]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/expiryDate", on = Platform.MOBILE_ANDROID)
    protected TextBox getExpDateTextBox() { return new TextBox(getLocator(this, "getExpDateTextBox")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'CVV'`]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/cvv", on = Platform.MOBILE_ANDROID)
    protected TextBox getCvvTextBox() { return new TextBox(getLocator(this, "getCvvTextBox")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Zip'`]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/zip", on = Platform.MOBILE_ANDROID)
    protected TextBox getZipTextBox() { return new TextBox(getLocator(this, "getZipTextBox")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeTextField[`value == 'Card Name'`]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/cardName", on = Platform.MOBILE_ANDROID)
    protected TextBox getCardNameTextBox() { return new TextBox(getLocator(this, "getCardNameTextBox")); }

    @Locate(iOSClassChain = "**/XCUIElementTypeSwitch", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/isDefaultSwitch", on = Platform.MOBILE_ANDROID)
    protected Button getDefaultToggle() { return new Button(getLocator(this, "getDefaultToggle")); }

    @Locate(id = "Save Card", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/saveCardButton", on = Platform.MOBILE_ANDROID)
    protected Button getSaveCardButton() { return new Button(getLocator(this, "getSaveCardButton")); }
}

class IosAddNewCardView extends AddNewCardView {

    public void selectMakeDefault() {
        logger.info("Making Card Default");
        getKeyboardDoneButton().click();
        getDefaultToggle().click();
    }

    @Locate(id = "Done", on = Platform.MOBILE_IOS)
    protected Button getKeyboardDoneButton() { return new Button(getLocator(this, "getKeyboardDoneButton")); }
}
