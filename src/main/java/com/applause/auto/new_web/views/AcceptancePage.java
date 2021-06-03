package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = AcceptancePage.class, on = Platform.WEB)
@Implementation(is = AcceptancePage.class, on = Platform.WEB_MOBILE_PHONE)
public class AcceptancePage extends Base {

  @Locate(css = "div[data-order-update-options]", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(className = "os-order-number", on = Platform.WEB)
  private Text orderNumber;

  @Locate(css = "#order-summary .product__description__name", on = Platform.WEB)
  private Text productName;

  @Locate(css = "span[data-checkout-subtotal-price-target]", on = Platform.WEB)
  private Text subTotal;

  @Locate(css = "span.payment-due__price", on = Platform.WEB)
  private Text total;

  @Locate(css = "button[data-arrive-phone]", on = Platform.WEB)
  private Button trackPackageButton;

  @Locate(css = "div[data-arrive-phone-wrapper] input[type='tel']", on = Platform.WEB)
  private TextBox trackPackagePhone;

  @Locate(css = "button[data-analytics-shipping-updates-method]", on = Platform.WEB)
  private Button shippingUpdates;

  @Locate(css = "#customer_notification_form--phone input[type='tel']", on = Platform.WEB)
  private TextBox shippingUpdatesPhone;

  @Locate(xpath = "//h3[text()='Contact information']//parent::div//bdo", on = Platform.WEB)
  private Text customerMail;

  @Locate(xpath = "//h3[text()='Shipping address']//parent::div//address", on = Platform.WEB)
  private Text shippingAddress;

  @Locate(xpath = "(//h3[text()='Shipping method']//parent::div/p)[2]", on = Platform.WEB)
  private Text shippingMethod;

  @Locate(xpath = "//h3[text()='Billing address']//parent::div//address", on = Platform.WEB)
  private Text billingAddress;

  @Locate(css = "a[data-osp-continue-button]", on = Platform.WEB)
  private Text continueShoppingButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Acceptance Page URL: " + getDriver().getCurrentUrl());
  }

  public boolean isOrderNumberDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(orderNumber).visible());
    return orderNumber.isDisplayed();
  }

  public boolean isSubTotalDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(subTotal).visible());
    return subTotal.isDisplayed();
  }

  public String getOrderName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("Product Name: " + productName.getText().toLowerCase());

    return productName.getText().toLowerCase();
  }

  public String getTotalPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(total).visible());
    logger.info("Total Price: " + total.getText());

    return total.getText();
  }

  public String getPhoneFromTrackPackageSection() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(trackPackagePhone).visible());
    logger.info("Phone from Track Package Section: " + trackPackagePhone.getCurrentText());

    return trackPackagePhone.getCurrentText();
  }

  public String getPhoneFromShippingUpdatesSection() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingUpdatesPhone).visible());
    logger.info("Phone from Shipping Updates Section: " + shippingUpdatesPhone.getCurrentText());

    return shippingUpdatesPhone.getCurrentText();
  }

  public void clickOverTrackPackageButton() {
    logger.info("Clicking track package button... ");
    trackPackageButton.click();
  }

  public void clickOverShippingUpdatesButton() {
    logger.info("Clicking shipping updated button... ");
    shippingUpdates.click();
  }

  /* -------- Actions -------- */
}
