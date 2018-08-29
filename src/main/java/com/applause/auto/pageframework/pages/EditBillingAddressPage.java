package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(EditBillingAddressPage.class)
@WebTabletImplementation(EditBillingAddressPage.class)
@WebPhoneImplementation(EditBillingAddressPage.class)
public class EditBillingAddressPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());
    WebHelper webHelper = new WebHelper();

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getViewSignature());
    }

    // Public actions

    /**
     * Enter Address
     *
     * @param address1
     * @param address2
     * @return String
     */
    public String enterAddress(String address1, String address2) {
        LOGGER.info("Entering Address");
        address1 = webHelper.getTimestamp(address1);
        getAddressLine1Field().setText(address1);
        getAddressLine2Field().setText(address2);
        return address1;
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

    @WebElementLocator(webDesktop = "button.button.btn-save")
    protected Button getSaveAddressButton() { return new Button(this, getLocator(this, "getSaveAddressButton")); }

    @WebElementLocator(webDesktop = "#qas-popup > div.modal-content-area > div > div.two-columns > div.right-column > div.qas-box-content > button")
    protected Button getUseAddressAsEnteredButton() { return new Button(this, getLocator(this, "getUseAddressAsEnteredButton")); }
}
