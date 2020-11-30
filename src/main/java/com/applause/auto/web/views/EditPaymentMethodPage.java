package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = EditPaymentMethodPage.class, on = Platform.WEB)
public class EditPaymentMethodPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      css = "div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1",
      on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = "#NameOnCard", on = Platform.WEB)
  private TextBox getNameOnCardField;

  @Locate(css = "#send2", on = Platform.WEB)
  private Button getSavePaymentMethodButton;

  /* -------- Actions -------- */

  /**
   * Enter Name on Card
   *
   * @param name
   * @return String
   */
  public String enterNameOnCard(String name) {
    logger.info("Entering Name on Card");
    name = WebHelper.getTimestamp(name);
    getNameOnCardField.sendKeys(name);
    return name;
  }

  /**
   * Click Save Payment Method Button
   *
   * @return PaymentMethodsPage
   */
  public PaymentMethodsPage clickSavePaymentMethod() {
    logger.info("Clicking Save Payment Method");
    getSavePaymentMethodButton.click();
    // wait for animation
    getSyncHelper().sleep(2000);
    return this.create(PaymentMethodsPage.class);
  }
}
