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
import com.applause.auto.framework.pageframework.webcontrols.Image;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(PaypalLoginPage.class)
@WebTabletImplementation(PaypalLoginPage.class)
@WebPhoneImplementation(PaypalLoginPage.class)
public class PaypalLoginPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getEmailField());
    }

    // Public actions

    /**
     * Enter Paypal Email Address
     *
     * @param email
     */
    public void enterEmail(String email) {
        LOGGER.info("Entering email address");
        getEmailField().setText(email);
    }

    /**
     * Click Next Button
     */
    public void clickNext() {
        LOGGER.info("Clicking Next button");
        getNextButton().click();
    }

    /**
     * Enter Paypal Password
     *
     * @param password
     */
    public void enterPassword(String password) {
        LOGGER.info("Entering password");
        syncHelper.waitForElementToAppear(getPasswordField());
        getPasswordField().setText(password);
    }

    /**
     * Click Log In
     *
     * @return PaypalReviewYourPurchasePage
     */
    public PaypalReviewYourPurchasePage clickLogIn() {
        LOGGER.info("Clicking Log In");
        getLogInButton().click();
        return PageFactory.create(PaypalReviewYourPurchasePage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = "//*[@id=\"content\"]")
    protected Image getViewSignature() { return new Image(this, getLocator(this, "getViewSignature")); }

    @WebElementLocator(webDesktop = "//input[@id='email']")
    protected EditField getEmailField() { return new EditField(this, getLocator(this, "getEmailField")); }

    @WebElementLocator(webDesktop = "//*[@id=\"btnNext\"]")
    protected Button getNextButton() { return new Button(this, getLocator(this, "getNextButton")); }

    @WebElementLocator(webDesktop = "//*[@id=\"password\"]")
    protected EditField getPasswordField() { return new EditField(this, getLocator(this, "getPasswordField")); }

    @WebElementLocator(webDesktop = "//*[@id=\"btnLogin\"]")
    protected Button getLogInButton() { return new Button(this, getLocator(this, "getLogInButton")); }
}
