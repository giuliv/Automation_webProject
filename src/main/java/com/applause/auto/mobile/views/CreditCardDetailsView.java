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

@Implementation(is = CreditCardDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosCreditCardDetailsView.class, on = Platform.MOBILE_IOS)
public class CreditCardDetailsView extends BaseComponent {

    // Public actions

    /**
     * Get Header Text
     *
     * @return String
     */
    public String getHeader() {
        logger.info("Getting Header Text");
        return getHeaderText().getText().trim();
    }

    /**
     * Get Credit Card Name
     *
     * @return String
     */
    public String getCreditCardName() {
        logger.info("Getting Credit Card Name");
        return getCCNameText().getText().trim();
    }

    /**
     * Checking for Default Card text
     *
     * @return Boolean
     */
    public Boolean isDefaultCardTextPresent() {
        logger.info("Checking for Default Card text");
        return getDefaultCardText().isVisible();
    }

    /**
     * Update Exp Date
     *
     * @param expDate
     */
    public void enterExpDate(String expDate) {
        logger.info("Entering Expiration Date");
        getExpDateField().clearText();
        getExpDateField().sendKeys(expDate);
    }

    /**
     * Save Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView saveCard() {
        logger.info("Save Payment Method");
        getDriver().hideKeyboard();
        getSaveCardButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
    }

    /**
     * Click Delete Card
     */
    public void clickDeleteCard() {
        logger.info("Clicking Delete Card");
        getDeleteButton().click();
    }

    /**
     * Cancel Deleting Payment Method
     *
     * @return CreditCardDetailsView
     */
    public CreditCardDetailsView clickDeleteNo() {
        logger.info("Cancel Deleting Payment Method");
        getDeleteNoButton().click();
        return ComponentFactory.create(CreditCardDetailsView.class);
    }

    /**
     * Confirm Deleting Payment Method
     *
     * @return PaymentMethodsView
     */
    public PaymentMethodsView clickDeleteYes() {
        logger.info("Confirming Deletion of Card");
        getDeleteYesButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
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
    @Locate(id = "Save Card", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/saveCardButton", on = Platform.MOBILE_ANDROID)
    protected Button getSaveCardButton() { return new Button(getLocator(this, "getSaveCardButton")); }

    @Locate(xpath = "//XCUIElementTypeOther[@name=\"Mobile Test\"]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
    protected Text getHeaderText() { return new Text(getLocator(this, "getHeaderText")); }

    @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Mobile Test\"]", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/cardName", on = Platform.MOBILE_ANDROID)
    protected Text getCCNameText() { return new Text(getLocator(this, "getCCNameText")); }

    @Locate(id = "Set as Default Payment", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/defaultText", on = Platform.MOBILE_ANDROID)
    protected Text getDefaultCardText() { return new Text(getLocator(this, "getDefaultCardText")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/expiry",
            iOS = "//XCUIElementTypeButton[@name=\"Delete Card\"]/preceding-sibling::XCUIElementTypeTextField")
    protected TextBox getExpDateField() { return new TextBox(getLocator(this, "getExpDateField")); }

    @Locate(id = "Delete Card", on = Platform.MOBILE_IOS)
    @Locate(id = "com.wearehathway.peets.development:id/deleteCardBtn", on = Platform.MOBILE_ANDROID)
    protected Button getDeleteButton() { return new Button(getLocator(this, "getDeleteButton")); }

    @Locate(id = "No", on = Platform.MOBILE_IOS)
    @Locate(id = "android:id/button2", on = Platform.MOBILE_ANDROID)
    protected Button getDeleteNoButton() { return new Button(getLocator(this, "getDeleteNoButton")); }

    @Locate(id = "Yes", on = Platform.MOBILE_IOS)
    @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
    protected Button getDeleteYesButton() { return new Button(getLocator(this, "getDeleteYesButton")); }
}

class IosCreditCardDetailsView extends CreditCardDetailsView {

    public PaymentMethodsView saveCard() {
        logger.info("Save Payment Method");
        getKeyboardDoneButton().click();
        getSaveCardButton().click();
        getBackButton().click();
        return ComponentFactory.create(PaymentMethodsView.class);
    }

    @Locate(id = "Done", on = Platform.MOBILE_IOS)
    protected Button getKeyboardDoneButton() { return new Button(getLocator(this, "getKeyboardDoneButton")); }

    @Locate(id = "button back", on = Platform.MOBILE_IOS)
    protected Button getBackButton() { return new Button(getLocator(this, "getBackButton")); }
}
