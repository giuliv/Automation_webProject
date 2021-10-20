package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;

import java.util.List;

@Implementation(is = ShippingPage.class, on = Platform.WEB)
@Implementation(is = ShippingPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ShippingPage extends Base {

  @Locate(css = "div[data-step='shipping_method']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "fieldset div[data-shipping-method]", on = Platform.WEB)
  private List<ContainerElement> shippingMethods;

  @Locate(id = "continue_button", on = Platform.WEB)
  private Button continueToPaymentButton;

  @Locate(xpath = "//a[contains(@class, 'previous')]", on = Platform.WEB)
  private Button returnToInformationButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Shipping Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Select shipping method")
  public String selectShippingMethodByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingMethods.get(index)).visible());
    logger.info("Shipping Method Selected: " + shippingMethods.get(index).getText());

    shippingMethods.get(index).click();
    return shippingMethods.get(index).getText();
  }

  @Step("Click continue to payments")
  public PaymentsPage clickContinueToPayments() {
    logger.info("Clicking Continue to Payments");
    SdkHelper.getSyncHelper().wait(Until.uiElement(continueToPaymentButton).present());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(continueToPaymentButton);
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }

    continueToPaymentButton.click();
    return SdkHelper.create(PaymentsPage.class);
  }

  @Step("Get container")
  public boolean isDisplayed() {
    return com.applause.auto.web.helpers.WebHelper.isDisplayed(mainContainer);
  }

  /**
   * Click on 'Return to information'
   *
   * @return CheckoutPage
   */

  @Step("Return to information")
  public CheckOutPage clickOnReturnToInformation() {
    logger.info("Clicking on 'Return to information'");
    returnToInformationButton.click();
    return SdkHelper.create(CheckOutPage.class);
  }
}
