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

@WebDesktopImplementation(EditPaymentMethodPage.class)
@WebTabletImplementation(EditPaymentMethodPage.class)
@WebPhoneImplementation(EditPaymentMethodPage.class)
public class EditPaymentMethodPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    WebHelper webHelper = new WebHelper();

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getViewSignature());
    }

    // Public actions

    /**
     * Enter Name on Card
     *
     * @param name
     * @return String
     */
    public String enterNameOnCard(String name) {
        LOGGER.info("Entering Name on Card");
        name = webHelper.getTimestamp(name);
        getNameOnCardField().setText(name);
        return name;
    }

    /**
     * Click Save Payment Method Button
     *
     * @return PaymentMethodsPage
     */
    public PaymentMethodsPage clickSavePaymentMethod() {
        LOGGER.info("Clicking Save Payment Method");
        getSavePaymentMethodButton().click();
        //wait for animation
        syncHelper.suspend(2000);
        return PageFactory.create(PaymentMethodsPage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1")
    protected Text getViewSignature() { return new Text(this, getLocator(this, "getViewSignature")); }

    @WebElementLocator(webDesktop = "#NameOnCard")
    protected EditField getNameOnCardField() { return new EditField(this, getLocator(this, "getNameOnCardField")); }

    @WebElementLocator(webDesktop = "#send2")
    protected Button getSavePaymentMethodButton() { return new Button(this, getLocator(this, "getSavePaymentMethodButton")); }

}
