package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.*;

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

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("CheckOut Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public void setCheckOutData() {
    logger.info("Setting CheckOut data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(mail).visible());

    mail.sendKeys(Constants.WebTestData.EMAIL);
    firstName.sendKeys(Constants.WebTestData.FIRST_NAME);
    lastName.sendKeys(Constants.WebTestData.LAST_NAME);

    SdkHelper.getSyncHelper().sleep(1000);

    address.sendKeys(Constants.WebTestData.ADDRESS);
    city.sendKeys(Constants.WebTestData.CITY);
    phone.sendKeys(Constants.WebTestData.PHONE);

    country.select(Constants.WebTestData.COUNTRY);
    SdkHelper.getSyncHelper().sleep(1000);

    province.select(Constants.WebTestData.PROVINCE);
    SdkHelper.getSyncHelper().sleep(1000);

    zip.sendKeys(Constants.WebTestData.ZIP);
  }

  public ShippingPage clickContinueToShipping() {
    logger.info("Clicking Continue to Shipping");
    SdkHelper.getSyncHelper().wait(Until.uiElement(continueToShipping).visible());
    continueToShipping.click();

    return SdkHelper.create(ShippingPage.class);
  }
}
