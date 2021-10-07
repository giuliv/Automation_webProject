package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = PaypalSelectPaymentMethodPage.class, on = Platform.WEB)
public class PaypalSelectPaymentMethodPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".noBottom.paymentsHeader.alpha", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(
      css =
          "#xoSelectFi > div.row-fluid.justMember > div.span14.trayInner.reviewSections > div.reviews.reviews_first > div > div.trayInner.trayInnerDefault.multipleFi > div > ul > li:nth-child(3) > experience:nth-child(2) > div > div > ng-transclude > div.row-fluid.radioButton > label",
      on = Platform.WEB)
  private Button getCreditCardButton;

  @Locate(xpath = "//*[@id=\"button\"]", on = Platform.WEB)
  private Button getContinueButton;

  /* -------- Actions -------- */

  /** Select Visa Payment Method */
  public void selectVisaPayment() {
    logger.info("Selecting Visa Payment Ending in 1111");
    getCreditCardButton.click();
  }

  /**
   * Click Continue
   *
   * @return PaypalReviewYourPurchasePage
   */
  public PaypalReviewYourPurchasePage clickContinue() {
    logger.info("Clicking Continue");
    getContinueButton.click();
    return SdkHelper.create(PaypalReviewYourPurchasePage.class);
  }
}
