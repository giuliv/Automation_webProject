package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import java.time.Duration;

@Implementation(is = PaypalReviewYourPurchasePage.class, on = Platform.WEB)
public class PaypalReviewYourPurchasePage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//*[@id=\"paypalLogo\"]", on = Platform.WEB)
  private Image getViewSignature;

  @Locate(xpath = "//*[contains(@class,'confirmButton')]", on = Platform.WEB)
  private Button getContinueButton;

  @Locate(xpath = "//*[@id='confirmButtonTop' or @class='confirmButton']", on = Platform.WEB)
  private Button getAgreeAndContinueButton;

  /* -------- Actions -------- */

  /**
   * Click Agree and Continue
   *
   * @return CheckoutPlaceOrderPage
   */
  public CheckoutPlaceOrderPage clickAgreeAndContinue() {
    logger.info("Clicking Agree and Continue");
    SdkHelper.getSyncHelper().sleep(10000);
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getContinueButton).clickable().setTimeout(Duration.ofSeconds(45)))
        .click();
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(getAgreeAndContinueButton)
                .clickable()
                .setTimeout(Duration.ofSeconds(45)))
        .click();
    SdkHelper.getDriver().switchTo().window("");
    SdkHelper.getSyncHelper().sleep(30000); // just waiting sandbox to completed
    return SdkHelper.create(CheckoutPlaceOrderPage.class);
  }
}
