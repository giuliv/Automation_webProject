package com.applause.auto.web.views;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;

import java.lang.invoke.MethodHandles;

@WebDesktopImplementation(PaypalSelectPaymentMethodPage.class)
@WebTabletImplementation(PaypalSelectPaymentMethodPage.class)
@WebPhoneImplementation(PaypalSelectPaymentMethodPage.class)
public class PaypalSelectPaymentMethodPage extends AbstractPage {
    protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

    @Override
    protected void waitUntilVisible() {
        syncHelper.waitForElementToAppear(getLocator(this, "getContinueButton"));
    }

    // Public actions

    /**
     * Select Visa Payment Method
     */
    public void selectVisaPayment() {
        LOGGER.info("Selecting Visa Payment Ending in 1111");
        getCreditCardButton().click();
    }

    /**
     * Click Continue
     * @return PaypalReviewYourPurchasePage
     */
    public PaypalReviewYourPurchasePage clickContinue() {
        LOGGER.info("Clicking Continue");
        getContinueButton().click();
        return PageFactory.create(PaypalReviewYourPurchasePage.class);
    }

    // Protected getters
    @WebElementLocator(webDesktop = ".noBottom.paymentsHeader.alpha")
    protected Text getViewSignature() { return new Text(this, getLocator(this, "getViewSignature")); }

    @WebElementLocator(webDesktop = "#xoSelectFi > div.row-fluid.justMember > div.span14.trayInner.reviewSections > div.reviews.reviews_first > div > div.trayInner.trayInnerDefault.multipleFi > div > ul > li:nth-child(3) > experience:nth-child(2) > div > div > ng-transclude > div.row-fluid.radioButton > label")
    protected Button getCreditCardButton() { return new Button(this, getLocator(this, "getCreditCardButton")); }

    @WebElementLocator(webDesktop = "//*[@id=\"button\"]")
    protected Button getContinueButton() { return new Button(this, getLocator(this, "getContinueButton")); }
}
