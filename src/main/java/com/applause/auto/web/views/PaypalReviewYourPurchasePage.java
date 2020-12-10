package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
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
    getSyncHelper().sleep(10000);
    getSyncHelper()
        .wait(Until.uiElement(getContinueButton).clickable().setTimeout(Duration.ofSeconds(45)))
        .click();
    getSyncHelper()
        .wait(
            Until.uiElement(getAgreeAndContinueButton)
                .clickable()
                .setTimeout(Duration.ofSeconds(45)))
        .click();
    getDriver().switchTo().window("");
    getSyncHelper().sleep(30000); // just waiting sandbox to completed
    return this.create(CheckoutPlaceOrderPage.class);
  }
}
