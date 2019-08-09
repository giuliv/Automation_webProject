package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import java.lang.invoke.MethodHandles;

@Implementation(is = PaypalSelectPaymentMethodPage.class, on = Platform.WEB)
public class PaypalSelectPaymentMethodPage extends BaseComponent {

    /* -------- Elements -------- */

    /* -------- Actions -------- */


    /**
     * Select Visa Payment Method
     */
    public void selectVisaPayment() {
        logger.info("Selecting Visa Payment Ending in 1111");
        getCreditCardButton().click();
    }

    /**
     * Click Continue
     * @return PaypalReviewYourPurchasePage
     */
    public PaypalReviewYourPurchasePage clickContinue() {
        logger.info("Clicking Continue");
        getContinueButton().click();
        return ComponentFactory.create(PaypalReviewYourPurchasePage.class);
    }

    // Protected getters
    @Locate(css = ".noBottom.paymentsHeader.alpha", on = Platform.WEB)
    protected Text getViewSignature() { return new Text(this, getLocator(this, "getViewSignature")); }

    @Locate(css = "#xoSelectFi > div.row-fluid.justMember > div.span14.trayInner.reviewSections > div.reviews.reviews_first > div > div.trayInner.trayInnerDefault.multipleFi > div > ul > li:nth-child(3) > experience:nth-child(2) > div > div > ng-transclude > div.row-fluid.radioButton > label", on = Platform.WEB)
    protected Button getCreditCardButton() { return new Button(this, getLocator(this, "getCreditCardButton")); }

    @Locate(xpath = "//*[@id=\"button\"]", on = Platform.WEB)
    protected Button getContinueButton() { return new Button(this, getLocator(this, "getContinueButton")); }
}
