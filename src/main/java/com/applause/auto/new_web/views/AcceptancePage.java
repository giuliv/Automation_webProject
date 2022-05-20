package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.List;

@Implementation(is = AcceptancePage.class, on = Platform.WEB)
@Implementation(is = AcceptancePage.class, on = Platform.WEB_MOBILE_PHONE)
public class AcceptancePage extends Base {

  @Locate(css = "div[data-order-update-options],.os-order-number", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(className = "os-order-number", on = Platform.WEB)
  private Text orderNumber;

  @Locate(css = ".product__description__name", on = Platform.WEB)
  private List<Text> productName;

  @Locate(css = "span[data-checkout-subtotal-price-target]", on = Platform.WEB)
  private Text subTotal;

  @Locate(css = "div[role='region']", on = Platform.WEB)
  private Text mapSection;

  @Locate(css = "span.payment-due__price", on = Platform.WEB)
  private Text total;

  @Locate(css = "span[data-checkout-discount-amount-target]", on = Platform.WEB)
  private Text discount;

  @Locate(css = "span[data-checkout-total-shipping-target]", on = Platform.WEB)
  private Text shippingPrice;

  @Locate(css = "span.order-summary-toggle__text--show > span", on = Platform.WEB)
  private ContainerElement orderSummarySection;

  @Locate(css = "button span.arrive-button", on = Platform.WEB)
  @Locate(css = "#track_arrive_with_attribution", on = Platform.WEB_MOBILE_PHONE)
  private Button trackPackageButton;

  @Locate(css = "div[data-arrive-phone-wrapper] input[type='tel']", on = Platform.WEB)
  private TextBox trackPackagePhone;

  @Locate(css = "button[data-analytics-shipping-updates-method]", on = Platform.WEB)
  @Locate(
      css = "button[data-analytics-shipping-updates-method='phone']",
      on = Platform.WEB_MOBILE_PHONE)
  private Button shippingUpdates;

  @Locate(css = "#customer_notification_form--phone input[type='tel']", on = Platform.WEB)
  private TextBox shippingUpdatesPhone;

  @Locate(xpath = "//h3[text()='Contact information']//parent::div//bdo", on = Platform.WEB)
  private Text customerMail;

  @Locate(xpath = "//h3[text()='Shipping address']//parent::div//address", on = Platform.WEB)
  private Text shippingAddress;

  @Locate(xpath = "(//h3[text()='Shipping method']//parent::div/p)[2]", on = Platform.WEB)
  private Text shippingMethod;

  @Locate(xpath = "//h3[text()='Payment method']//parent::div//li", on = Platform.WEB)
  private Text paymentMethod;

  @Locate(xpath = "//h3[text()='Billing address']//parent::div//address", on = Platform.WEB)
  private Text billingAddress;

  @Locate(css = "a[data-osp-continue-button]", on = Platform.WEB)
  private Text continueShoppingButton;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  private Text discountsMessage;

  @Locate(css = "li.reduction-code span", on = Platform.WEB)
  protected List<Text> discountsMessageList;

  @Locate(
      xpath = "//span[contains(@class, 'description') and contains(@class, 'variant')]",
      on = Platform.WEB)
  private Text orderGrind;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(30)));
    logger.info("Acceptance Page URL: " + getDriver().getCurrentUrl());

    if (!WebHelper.isDesktop()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(orderSummarySection).present());
      WebHelper.scrollToElement(orderSummarySection.getWebElement());
      SdkHelper.getSyncHelper().wait(Until.uiElement(orderSummarySection).clickable());
      logger.info("Open Order Summary Section");

      orderSummarySection.click();
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }
  }

  @Step("Get order number")
  public boolean isOrderNumberDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(orderNumber).visible());
    return orderNumber.isDisplayed();
  }

  @Step("Get subtotal")
  public boolean isSubTotalDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(subTotal).visible());
    return subTotal.isDisplayed();
  }

  @Step("Get map")
  public boolean isMapDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mapSection).visible());
    return mapSection.isDisplayed();
  }

  @Step("Get discount")
  public String getDiscountText() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(discount).visible());
    logger.info("Discount text: " + discount.getText().trim());

    return discount.getText().trim();
  }

  @Step("Get shipping price")
  public String getShippingPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingPrice).visible());
    logger.info("Shipping price: " + shippingPrice.getText().trim());

    return shippingPrice.getText().trim();
  }

  @Step("Get Continue shopping")
  public boolean isContinueShoppingDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(continueShoppingButton).present());
    WebHelper.scrollToElement(continueShoppingButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    return continueShoppingButton.isDisplayed();
  }

  @Step("Get order name")
  public String getOrderNameByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName.get(index)).visible());
    logger.info("Product Name: " + productName.get(index).getText().toLowerCase());

    return productName.get(index).getText().toLowerCase();
  }

  @Step("Get total price")
  public String getTotalPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(total).visible());
    logger.info("Total Price: " + total.getText());

    return total.getText();
  }

  @Step("Get phone number from track package")
  public String getPhoneFromTrackPackageSection() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(trackPackagePhone).visible());
    logger.info("Phone from Track Package Section: " + trackPackagePhone.getCurrentText());

    return trackPackagePhone.getCurrentText();
  }

  @Step("Get phone number from shipping update")
  public String getPhoneFromShippingUpdatesSection() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingUpdatesPhone).visible());
    logger.info("Phone from Shipping Updates Section: " + shippingUpdatesPhone.getCurrentText());

    return shippingUpdatesPhone.getCurrentText();
  }

  @Step("Get customer mail")
  public String getCustomerMail() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(customerMail).visible());
    logger.info("Customer Mail: " + customerMail.getText());

    return customerMail.getText();
  }

  @Step("Get shipping address")
  public String getShippingAddressData() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingAddress).visible());
    logger.info("Shipping Address: " + shippingAddress.getText());

    return shippingAddress.getText();
  }

  @Step("Get shipping method")
  public String getShippingMethod() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingMethod).visible());
    logger.info("Shipping Method: " + shippingMethod.getText());

    return shippingMethod.getText();
  }

  @Step("Get payment method")
  public String getPaymentMethod() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(paymentMethod).visible());
    logger.info("Payment Method: " + paymentMethod.getText());

    return paymentMethod.getText();
  }

  @Step("Get billing address")
  public String getBillingAddressData() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(billingAddress).visible());
    logger.info("Billing Address: " + billingAddress.getText());

    return billingAddress.getText();
  }

  @Step("Click track package")
  public void clickOverTrackPackageButton() {
    logger.info("Clicking track package button... ");
    WebHelper.scrollToElement(trackPackageButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    trackPackageButton.click();
  }

  @Step("Get discount message")
  public String getDiscountMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(discountsMessage).present());
    logger.info("Discount message: " + discountsMessage.getText());

    return discountsMessage.getText();
  }

  @Step("Get discount message from index")
  public String getDiscountMessageByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(discountsMessageList.get(index)).visible());
    logger.info("[Acceptance Page] Discount Message: " + discountsMessageList.get(index).getText());

    return discountsMessageList.get(index).getText();
  }

  @Step("Get discount")
  public boolean isDiscountPresent() {
    return discountsMessage.exists();
  }

  @Step("Click shipping updates")
  public void clickOverShippingUpdatesButton() {
    logger.info("Clicking shipping updated button... ");
    WebHelper.scrollToElement(shippingUpdates.getWebElement()); // Wait for scroll

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      shippingUpdates.click();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
  }

  @Step("Get Order Grind")
  public String getOrderGrind() {
    String grind = WebHelper.cleanString(orderGrind.getText());
    logger.info("Order Grind - [{}]", grind);
    return grind;
  }
}
