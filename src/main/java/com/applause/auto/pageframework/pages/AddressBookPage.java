package com.applause.auto.pageframework.pages;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(AddressBookPage.class)
@WebTabletImplementation(AddressBookPage.class)
@WebPhoneImplementation(AddressBookPage.class)
public class AddressBookPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getViewSignature());
    }

    // Public actions

    /**
     * Verify Address Changed Text is Displayed
     *
     * @return boolean
     */
    public boolean isAddressSavedTextDisplayed() {
        LOGGER.info("Verifying Address Changed text is displayed");
        return getAddressSavedBannerText().isDisplayed();
    }

    /**
     * Get Billing Address
     *
     * @return String
     */
    public String getBillingAddress() {
        LOGGER.info("Getting Billing Address");
        return getBillingAddressText().getStringValue();
    }

    /**
     * Delete Billing Address
     */
    public void deleteBillingAddress() {
        LOGGER.info("Deleting Billing Address");
        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
        jse.executeScript("scroll(0,450)", "");
        getDeleteBillingAddressButton().click();
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    /**
     * Verify Billing Address was Deleted
     *
     * @return boolean
     */
    public boolean isBillingAddressDeleted() {
        LOGGER.info("Verifying Billing Address was deleted");
        return getNoBillingAddressText().isDisplayed();
    }

    /**
     * Add New Billing Address
     *
     * @return AddBillingAddressPage
     */
    public AddBillingAddressPage clickAddNewBillingAddress() {
        LOGGER.info("Clicking Add a New Billing Address");
        getAddBillingAddressButton().click();
        return PageFactory.create(AddBillingAddressPage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.account-inner-title > div.left > h2")
    protected Text getViewSignature() { return new Text(this, getLocator(this, "getViewSignature")); }

    @WebElementLocator(webDesktop = "//span[contains(.,'address has been saved')]")
    protected Text getAddressSavedBannerText() { return new Text(this, getLocator(this, "getAddressSavedBannerText")); }

    @WebElementLocator(webDesktop = "#billing_form > ol > li > div.info-col > p")
    protected Text getBillingAddressText() { return new Text(this, getLocator(this, "getBillingAddressText")); }

    @WebElementLocator(webDesktop = "#billing_form > ol > li > div.actions-col > ul > li:nth-child(2) > a")
    protected Button getDeleteBillingAddressButton() { return new Button(this, getLocator(this, "getDeleteBillingAddressButton")); }

    @WebElementLocator(webDesktop = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.main-section > div")
    protected Text getNoBillingAddressText() { return new Text(this, getLocator(this, "getNoBillingAddressText")); }

    @WebElementLocator(webDesktop = "div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.account-inner-title > div.right > a")
    protected Button getAddBillingAddressButton() { return new Button(this, getLocator(this, "getAddBillingAddressButton")); }
}
