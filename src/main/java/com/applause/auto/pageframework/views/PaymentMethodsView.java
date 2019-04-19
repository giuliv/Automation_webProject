package com.applause.auto.pageframework.views;

import com.applause.auto.framework.pageframework.device.AbstractDeviceView;
import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.device.MobileElementLocator;
import com.applause.auto.framework.pageframework.device.factory.AndroidImplementation;
import com.applause.auto.framework.pageframework.device.factory.IosImplementation;
import com.applause.auto.framework.pageframework.devicecontrols.Button;
import com.applause.auto.framework.pageframework.devicecontrols.Text;
import com.applause.auto.framework.pageframework.util.logger.LogController;

import java.lang.invoke.MethodHandles;

@AndroidImplementation(PaymentMethodsView.class)
@IosImplementation(PaymentMethodsView.class)
public class PaymentMethodsView extends AbstractDeviceView {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getLocator(this, "getViewSignature"));
    }

    // Public actions

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
    public CreditCardDetailsView clickSavedPaymentMethod() {
        LOGGER.info("Clicking Payment Method");
        getSavedPaymentMethodButton().pressButton();
        return DeviceViewFactory.create(CreditCardDetailsView.class);
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

    // Protected getters
    @MobileElementLocator(android = "com.wearehathway.peets.development:id/title", iOS = "//XCUIElementTypeNavigationBar")
    protected Text getViewSignature() { return new Text(getLocator(this, "getViewSignature")); }

    @MobileElementLocator(android = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]", iOS = "button back")
    protected Button getBackButton() { return new Button(getLocator(this, "getBackButton")); }

    @MobileElementLocator(android = "new UiSelector().resourceIdMatches(\".*id/sectionHeading\")", iOS = "//XCUIElementTypeStaticText[@name=\"Your Peet's Card\"]")
    protected Text getPeetsCardHeaderText() { return new Text(getLocator(this, "getPeetsCardHeaderText")); }

    @MobileElementLocator(android = "new UiSelector().resourceIdMatches(\".*id/creditCardView\")", iOS = "Peet's Card")
    protected Button getPeetsCard() { return new Button(getLocator(this, "getPeetsCard")); }

    @MobileElementLocator(android = "//android.widget.TextView[@text='Saved Payment Methods']", iOS = "//XCUIElementTypeStaticText[@name=\"Saved Payment Methods\"]")
    protected Text getSavedPaymentMethodHeaderText() { return new Text(getLocator(this, "getSavedPaymentMethodHeaderText")); }

    @MobileElementLocator(android = "//*[contains(@resource-id, 'id/creditCardView')][3]", iOS = "//XCUIElementTypeTable/XCUIElementTypeCell[3]")
    protected Button getSavedPaymentMethodButton() { return new Button(getLocator(this, "getSavedPaymentMethodButton")); }

    @MobileElementLocator(android = "com.wearehathway.peets.development:id/addPaymentView", iOS = "(//XCUIElementTypeStaticText[@name=\"Add New Payment\"])[3]")
    protected Button getAddNewPaymentButton() { return new Button(getLocator(this, "getAddNewPaymentButton")); }
}
