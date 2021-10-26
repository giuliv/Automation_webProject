package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.ShipDateInfoComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = ShippingPage.class, on = Platform.WEB)
@Implementation(is = ShippingPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ShippingPage extends Base {

  @Locate(css = "div[data-step='shipping_method']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "fieldset div[data-shipping-method]", on = Platform.WEB)
  private List<ContainerElement> shippingMethods;

  @Locate(
      xpath = "//button[@id='continue_button'][not(contains(@class, 'disabled'))]",
      on = Platform.WEB)
  private Button continueToPaymentButton;

  @Locate(xpath = "//a[contains(@class, 'previous')]", on = Platform.WEB)
  private Button returnToInformationButton;

  @Locate(xpath = "//a[.//span[text()='Change contact information']]", on = Platform.WEB)
  private Button changeContactInformationButton;

  @Locate(xpath = "//a[.//span[text()='Change shipping address']]", on = Platform.WEB)
  private Button changeShipToButton;

  @Locate(
      xpath = "//button[contains(@class, 'checkout-tooltip') and not(contains(@class, 'close'))]",
      on = Platform.WEB)
  private Button infoButton;

  @Locate(
      xpath =
          "//div[contains(@class, 'content-box')]/div[contains(@class, 'review-block')][1]//div[contains(@class, 'content')]",
      on = Platform.WEB)
  private Text contactEmail;

  @Locate(
      xpath =
          "//div[contains(@class, 'content-box')]/div[contains(@class, 'review-block')][2]//div[contains(@class, 'content')]",
      on = Platform.WEB)
  private Text shipToAddress;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Shipping Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Select shipping method")
  public String selectShippingMethodByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingMethods.get(index)).visible());
    ContainerElement element = shippingMethods.get(index);
    String methodName = element.getText();
    logger.info("Shipping Method Selected: [{}]", methodName);
    element.click();
    return methodName;
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
    return WebHelper.isDisplayed(mainContainer);
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

  @Step("Click on 'Change contact information'")
  public CheckOutPage clickOnChangeContactInformation() {
    logger.info("Clicking on 'Change contact information'");
    changeContactInformationButton.click();
    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Click on 'Change shipping address'")
  public CheckOutPage clickOnChangeShipTo() {
    logger.info("Clicking on 'Change shipping address'");
    changeShipToButton.click();
    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Click on 'Info' icon near the Shipping method")
  public ShipDateInfoComponent clickOnInfoButton() {
    logger.info("Clicking on 'Info' icon near the Shipping method");
    infoButton.click();
    return SdkHelper.create(ShipDateInfoComponent.class);
  }

  @Step("Get Contact Email")
  public String getContactEmail() {
    String email = WebHelper.cleanString(contactEmail.getText());
    logger.info("Contact Email - [{}]", email);
    return email;
  }

  @Step("Get Ship to address")
  public String getShipToAddress() {
    String address = WebHelper.cleanString(shipToAddress.getText());
    logger.info("Ship To Address - [{}]", address);
    return address;
  }
}
