package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

@Implementation(is = AddressBookPage.class, on = Platform.WEB)
public class AddressBookPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//span[contains(.,'address has been saved')]", on = Platform.WEB)
  private Text getAddressSavedBannerText;

  @Locate(css = "#billing_form > ol > li > div.info-col > p", on = Platform.WEB)
  private Text getBillingAddressText;

  @Locate(css = "#shipping_form > ol > li > div.info-col > p", on = Platform.WEB)
  private Text getShippingAddressText;

  @Locate(
      css = "#billing_form > ol > li > div.actions-col > ul > li:nth-child(2) > a",
      on = Platform.WEB)
  private Button getDeleteBillingAddressButton;

  @Locate(
      css = "#shipping_form > ol > li > div.actions-col > ul > li:nth-child(2) > a",
      on = Platform.WEB)
  private Button getDeleteShippingAddressButton;

  @Locate(
      css =
          "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.main-section > div",
      on = Platform.WEB)
  private Text getNoBillingAddressText;

  @Locate(
      css =
          "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.main-section > div",
      on = Platform.WEB)
  private Text getNoShippingAddressText;

  @Locate(
      css =
          "div.account-container.account-inner-section > div.addresses-list > div:nth-child(2) > div.account-inner-title > div.right > a",
      on = Platform.WEB)
  private Button getAddBillingAddressButton;

  @Locate(
      css =
          "div.account-container.account-inner-section > div.addresses-list > div:nth-child(1) > div.account-inner-title > div.right > a",
      on = Platform.WEB)
  private Button getAddShippingAddressButton;

  /* -------- Actions -------- */

  /**
   * Verify Address Changed Text is Displayed
   *
   * @return boolean
   */
  public boolean isAddressSavedTextDisplayed() {
    logger.info("Verifying Address Changed text is displayed");
    return getAddressSavedBannerText.isDisplayed();
  }

  /**
   * Get Billing Address
   *
   * @return String
   */
  public String getBillingAddress() {
    logger.info("Getting Billing Address");
    return getBillingAddressText.getText();
  }

  /**
   * Get Shipping Address
   *
   * @return
   */
  public String getShippingAddress() {
    logger.info("Getting Shipping Address");
    return getShippingAddressText.getText();
  }

  /** Delete Billing Address */
  public void deleteBillingAddress() {
    logger.info("Deleting Billing Address");
    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    jse.executeScript("scroll(0,450)", "");
    getDeleteBillingAddressButton.click();
    Alert alert = getDriver().switchTo().alert();
    alert.accept();
  }

  /** Delete Shipping Address */
  public void deleteShippingAddress() {
    logger.info("Deleting Shipping Address");
    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    jse.executeScript("scroll(0,450)", "");
    getDeleteShippingAddressButton.click();
    Alert alert = getDriver().switchTo().alert();
    alert.accept();
  }

  /**
   * Verify Billing Address was Deleted
   *
   * @return boolean
   */
  public boolean isBillingAddressDeleted() {
    logger.info("Verifying Billing Address was deleted");
    return getNoBillingAddressText.isDisplayed();
  }

  /**
   * Verify Shipping Address was Deleted
   *
   * @return boolean
   */
  public boolean isShippingAddressDeleted() {
    logger.info("Verifying Shipping Address was deleted");
    return getNoShippingAddressText.isDisplayed();
  }

  /**
   * Add New Billing Address
   *
   * @return AddBillingAddressPage
   */
  public AddBillingAddressPage clickAddNewBillingAddress() {
    logger.info("Clicking Add a New Billing Address");
    getAddBillingAddressButton.click();
    return this.create(AddBillingAddressPage.class);
  }

  /**
   * Add New Shipping Address
   *
   * @return AddShippingAddressPage
   */
  public AddShippingAddressPage clickAddNewShippingAddress() {
    logger.info("Clicking Add a New Shipping Address");
    getAddShippingAddressButton.click();
    return this.create(AddShippingAddressPage.class);
  }
}
