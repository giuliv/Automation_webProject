package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.*;

import java.time.Duration;

@Implementation(is = CheckOutPage.class, on = Platform.WEB)
@Implementation(is = CheckOutPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckOutPage extends Base {

  @Locate(css = "div[data-step='contact_information']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "checkout_email", on = Platform.WEB)
  private TextBox mail;

  @Locate(id = "checkout_shipping_address_first_name", on = Platform.WEB)
  private TextBox firstName;

  @Locate(id = "checkout_shipping_address_last_name", on = Platform.WEB)
  private TextBox lastName;

  @Locate(id = "checkout_shipping_address_address1", on = Platform.WEB)
  private TextBox address;

  @Locate(id = "checkout_shipping_address_city", on = Platform.WEB)
  private TextBox city;

  @Locate(id = "checkout_shipping_address_phone", on = Platform.WEB)
  private TextBox phone;

  @Locate(css = "select#checkout_shipping_address_country", on = Platform.WEB)
  private SelectList country;

  @Locate(css = "select#checkout_shipping_address_province", on = Platform.WEB)
  private SelectList province;

  @Locate(id = "checkout_shipping_address_zip", on = Platform.WEB)
  private TextBox zip;

  @Locate(id = "continue_button", on = Platform.WEB)
  private Button continueToShipping;

  @Locate(css = "p[class*='logged-in']", on = Platform.WEB)
  private Text existingUser;

  @Locate(css = "select[data-address-selector]", on = Platform.WEB)
  private SelectList existingAddress;

  @Locate(id = "btn-proceed-address", on = Platform.WEB)
  private Button proceedButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("CheckOut Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public void setCheckOutData() {
    logger.info("Setting CheckOut data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(mail).visible());

    mail.sendKeys(Constants.WebTestData.EMAIL);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    firstName.sendKeys(Constants.WebTestData.FIRST_NAME);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    lastName.sendKeys(Constants.WebTestData.LAST_NAME);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    address.sendKeys(Constants.WebTestData.ADDRESS);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    city.sendKeys(Constants.WebTestData.CITY);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    phone.sendKeys(Constants.WebTestData.PHONE);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    country.select(Constants.WebTestData.COUNTRY);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    province.select(Constants.WebTestData.PROVINCE);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    zip.sendKeys(Constants.WebTestData.ZIP);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action
  }

  public void setCheckOutDataAsExistingUser() {
    logger.info("Setting CheckOut data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(address).visible());

    if (existingAddress.exists()) {
      logger.info("Using existing saved address");

      existingAddress.click();
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action

      existingAddress.getOptions().get(0).click();
    } else {
      address.sendKeys(Constants.WebTestData.ADDRESS);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      city.sendKeys(Constants.WebTestData.CITY);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      phone.sendKeys(Constants.WebTestData.PHONE);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      country.select(Constants.WebTestData.COUNTRY);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      province.select(Constants.WebTestData.PROVINCE);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      zip.sendKeys(Constants.WebTestData.ZIP);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action
    }
  }

  public String isExistingUserMailCorrect() {
    logger.info("Reviewing existing user...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(existingUser).visible());

    return existingUser.getText();
  }

  public ShippingPage clickContinueToShipping() {
    logger.info("Clicking Continue to Shipping");
    SdkHelper.getSyncHelper().wait(Until.uiElement(continueToShipping).visible());
    continueToShipping.click();

    if (WebHelper.getTestEnvironment().equalsIgnoreCase("production") && proceedButton.exists()) {
      proceedButton.click();
    }

    return SdkHelper.create(ShippingPage.class);
  }
}
