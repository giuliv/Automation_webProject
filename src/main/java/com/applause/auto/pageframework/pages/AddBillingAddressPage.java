package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.drivers.BrowserType;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Dropdown;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;
import org.openqa.selenium.Keys;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(AddBillingAddressPage.class)
@WebTabletImplementation(AddBillingAddressPage.class)
@WebPhoneImplementation(AddBillingAddressPage.class)
public class AddBillingAddressPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    WebHelper webHelper = new WebHelper();

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getViewSignature());
    }

    // Public actions

    /**
     * Enter Address Line 1
     *
     * @param address
     */
    public void enterAddressLine1(String address) {
        LOGGER.info("Entering Address");
        getAddressLine1Field().setText(address);
    }

    /**
     * Enter Address Line 2
     *
     * @param address
     */
    public void enterAddressLine2(String address) {
        LOGGER.info("Entering Address");
        getAddressLine2Field().setText(address);
    }

    /**
     * Enter Zip Code
     *
     * @param zipCode
     */
    public void enterZipCode(String zipCode) {
        LOGGER.info("Entering Zip Code");
        getZipcodeField().setText(zipCode);
    }

    /**
     * Select State
     *
     * @param state
     */
    public void selectState(String state) {
        LOGGER.info("Selecting State");
        if (env.getBrowserType() == BrowserType.SAFARI) {
            webHelper.jsSelect(getStateDropdown().getWebElement(), "New York");
        }
//        getStateDropdown().select(state);
    }

    /**
     * Enter City
     *
     * @param city
     */
    public void enterCity(String city) {
        LOGGER.info("Entering City");
        getCityField().setText(city);
    }

    /**
     * Enter Phone Number
     *
     * @param phoneNum
     */
    public void enterPhoneNumber(String phoneNum) {
        LOGGER.info("Entering Phone Number");
        getPhoneNumberField().setText(phoneNum);
    }

    /**
     * Click Save Address
     *
     * @return AddressBookPage
     */
    public AddressBookPage clickSaveAddress() {
        LOGGER.info("Clicking Save Address");
        getSaveAddressButton().click();
        if (getUseAddressAsEnteredButton().exists()) {
            getUseAddressAsEnteredButton().click();
        }
        return PageFactory.create(AddressBookPage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
    protected Text getViewSignature() { return new Text(this, getLocator(this, "getViewSignature")); }

    @WebElementLocator(webDesktop = "#street_1")
    protected EditField getAddressLine1Field() { return new EditField(this, getLocator(this, "getAddressLine1Field")); }

    @WebElementLocator(webDesktop = "#street_2")
    protected EditField getAddressLine2Field() { return new EditField(this, getLocator(this, "getAddressLine2Field")); }

    @WebElementLocator(webDesktop = "#zip")
    protected EditField getZipcodeField() { return new EditField(this, getLocator(this, "getZipcodeField")); }

    @WebElementLocator(webDesktop = "#region_id")
    protected EditField getStateDropdown() { return new EditField(this, getLocator(this, "getStateDropdown")); }

    @WebElementLocator(webDesktop = "#city")
    protected EditField getCityField() { return new EditField(this, getLocator(this, "getCityField")); }

    @WebElementLocator(webDesktop = "#telephone")
    protected EditField getPhoneNumberField() { return new EditField(this, getLocator(this, "getPhoneNumberField")); }

    @WebElementLocator(webDesktop = "button.button.btn-save")
    protected Button getSaveAddressButton() { return new Button(this, getLocator(this, "getSaveAddressButton")); }

    @WebElementLocator(webDesktop = "#qas-popup > div.modal-content-area > div > div.two-columns > div.right-column > div.qas-box-content > button")
    protected Button getUseAddressAsEnteredButton() { return new Button(this, getLocator(this, "getUseAddressAsEnteredButton")); }

}
