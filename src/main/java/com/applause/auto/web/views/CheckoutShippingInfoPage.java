package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.applause.auto.web.components.DatePickerChunk;
import com.applause.auto.web.components.ShopRunnerChunk;
import com.applause.auto.web.components.VerifyYourAddressDetailsChunk;
import com.applause.auto.web.helpers.WebHelper;
import java.time.Duration;

@Implementation(is = CheckoutShippingInfoPage.class, on = Platform.WEB)
public class CheckoutShippingInfoPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#srd_so > div > div.srd_msg > a:nth-child(8)", on = Platform.WEB)
  private Button getSignInShopRunnerButton;

  @Locate(css = "input[id='shipping:firstname']", on = Platform.WEB)
  private TextBox getFirstNameTextBox;

  @Locate(css = ".editing input[id^='shipping:firstname']", on = Platform.WEB)
  private TextBox getFirstNameModifyTextBox;

  @Locate(css = "input[id='shipping:lastname']", on = Platform.WEB)
  private TextBox getLastNameTextBox;

  @Locate(css = "input[id='shipping:telephone']", on = Platform.WEB)
  private TextBox getPhoneNumberTextBox;

  @Locate(css = "input[id='shipping:street1']", on = Platform.WEB)
  private TextBox getMainAddressTextBox;

  @Locate(css = "input[id='shipping:postcode']", on = Platform.WEB)
  private TextBox getZipCodeTextBox;

  @Locate(css = "input[id='shipping:city']", on = Platform.WEB)
  private TextBox getCityTextBox;

  @Locate(css = "select[id='shipping:region_id']", on = Platform.WEB)
  private SelectList getStateSelectList;

  @Locate(css = ".shipping-buttons-set button", on = Platform.WEB)
  private Button getContactInfoContinueButton;

  @Locate(css = "button[title='Continue']", on = Platform.WEB)
  private Button getNewAddressContinueButton;

  @Locate(css = ".editing button[title='Update']", on = Platform.WEB)
  private Button getModifiedAddressUpdateButton;

  @Locate(css = "#opc-please-wait", on = Platform.WEB)
  private ContainerElement getShippingLoadingSpinner;

  @Locate(xpath = "//strong[@class='shipping-method-name' and contains(.,'%s')]", on = Platform.WEB)
  private Button getShippingMethodButton;

  @Locate(css = "#gift-message-whole-message", on = Platform.WEB)
  private TextBox getGiftMessageTextBox;

  @Locate(css = "#co-shipping-method-form button", on = Platform.WEB)
  private Button getShippingInfoContinueButton;

  @Locate(css = ".shipping-add-address", on = Platform.WEB)
  private Button getAddNewAddressButton;

  @Locate(css = "#checkout-step-shipping .edit-shipping-date", on = Platform.WEB)
  private Button getEditShippingDateButton;

  @Locate(css = "[title^='Please wait...']", on = Platform.WEB)
  private Text getProgressWrapper;

  @Locate(css = "#checkout-step-shipping .shipping-date #ddate-selected-date", on = Platform.WEB)
  private Text getShippingDateText;

  // TODO: Fix up and add back (tests fail when these are uncommented)
  // @Locate(css = ".checkout-section-content > .shipping-address-item ol li label", on =
  // Platform.WEB)
  // private List<WebElement> getAddressesText;

  // @Locate(css = ".checkout-section-content > .shipping-address-item ol li > a", on =
  // Platform.WEB)
  // private List<WebElement> getAddressesEditButton;

  // @Locate(css = ".checkout-section-content > .shipping-address-item ol li label", on =
  // Platform.WEB)
  // private List<WebElement> getEditAddressButtonByKey;

  /* -------- Actions -------- */

  /**
   * Sign in shop runner shop runner chunk.
   *
   * @return the shop runner chunk
   */
  public ShopRunnerChunk signInShopRunner() {
    logger.info("Click on Sign In shop runner");
    getSyncHelper().sleep(10000);
    WebHelper.scrollToElement(getSignInShopRunnerButton.getWebElement());
    getSignInShopRunnerButton.click();
    return this.create(ShopRunnerChunk.class);
  }

  /** Continue after entering required Shipping info */
  public VerifyYourAddressDetailsChunk continueAfterFillingRequiredContactInfo() {
    logger.info("Clicking Continue after filling shipping info");
    fillShippingInfo();
    continueAfterContactInfo();
    return this.create(VerifyYourAddressDetailsChunk.class);
  }

  /** Fill Required Fields for Shipping */
  public void fillShippingInfo() {
    logger.info("Filling shipping info");
    getSyncHelper().wait(
        Until.uiElement(getFirstNameTextBox).visible().setTimeout(Duration.ofSeconds(30)));
    getFirstNameTextBox.sendKeys(Constants.TestData.FIRST_NAME);
    getLastNameTextBox.sendKeys(Constants.TestData.LAST_NAME);
    getPhoneNumberTextBox.sendKeys(Constants.TestData.PHONE);
    getMainAddressTextBox.sendKeys(Constants.TestData.ADDRESS);
    getZipCodeTextBox.sendKeys(Constants.TestData.ZIP_CODE);
    getCityTextBox.sendKeys(Constants.TestData.CITY);
    WebHelper.jsSelect(getStateSelectList.getWebElement(), Constants.TestData.STATE);
  }

  /**
   * Fill unique shipping info.
   *
   * @param uniqPrefix the uniq prefix
   */
  public void fillUniqueShippingInfo(String uniqPrefix) {
    logger.info("Filling shipping info");
    getFirstNameTextBox.sendKeys(uniqPrefix + Constants.TestData.FIRST_NAME);
    getLastNameTextBox.sendKeys(Constants.TestData.LAST_NAME);
    getPhoneNumberTextBox.sendKeys(Constants.TestData.PHONE);
    getMainAddressTextBox.sendKeys(Constants.TestData.ADDRESS_2);
    getZipCodeTextBox.sendKeys(Constants.TestData.ZIP_CODE_2);
    getCityTextBox.sendKeys(Constants.TestData.CITY);
    WebHelper.jsSelect(getStateSelectList.getWebElement(), Constants.TestData.STATE);
  }

  /**
   * Modify shipping info by delta.
   *
   * @param uniqPrefix the uniq prefix
   */
  public void modifyShippingInfoByDelta(String uniqPrefix) {
    logger.info("Filling shipping info");
    getFirstNameModifyTextBox.clearText();
    getFirstNameModifyTextBox.sendKeys(uniqPrefix + Constants.TestData.FIRST_NAME);
  }

  /** Click continue after contact info section */
  public void continueAfterContactInfo() {
    logger.info("Click Continue on contact section");
    getContactInfoContinueButton.click();
  }

  /**
   * Continue add new address checkout shipping info page.
   *
   * @return the checkout shipping info page
   */
  public CheckoutShippingInfoPage continueAddNewAddress() {
    logger.info("Click Continue on contact section");
    getNewAddressContinueButton.click();

    getSyncHelper().wait(
        Until.uiElement(getShippingLoadingSpinner).visible().setTimeout(Duration.ofSeconds(60)));
    getSyncHelper().wait(
        Until.uiElement(getShippingLoadingSpinner).notVisible().setTimeout(Duration.ofSeconds(60)));

    return this.create(CheckoutShippingInfoPage.class);
  }

  /** Continue after selecting Shipping Method */
  public CheckoutPaymentMethodPage setShippingMethod(String shippingMethod) {
    logger.info("Set Shipping Method");
    selectShippingMethod(shippingMethod);
    continueAfterShippingInfo();
    return this.create(CheckoutPaymentMethodPage.class);
  }

  /**
   * Sets shipping method.
   *
   * @return the shipping method
   */
  public CheckoutPaymentMethodPage setShippingMethod() {
    logger.info("Click continue");
    continueAfterShippingInfo();
    return this.create(CheckoutPaymentMethodPage.class);
  }

  /** Select Shipping Method */
  public void selectShippingMethod(String shippingMethod) {
    logger.info("Select Shipping Method");
    getShippingMethodButton.initializeWithFormat(shippingMethod);
    getShippingMethodButton.scrollToElement();
    getShippingMethodButton.click();
  }

  /** Click continue after shipping info section */
  public void continueAfterShippingInfo() {
    logger.info("Click Continue on shipping section");
    WebHelper.scrollToElement(getShippingInfoContinueButton.getWebElement());
    getSyncHelper().sleep(10000);
    getShippingInfoContinueButton.click();
  }

  /**
   * Get Gift Order Message
   *
   * @return String
   */
  public String getGiftMessage() {
    logger.info("Getting gift Message");
    getGiftMessageTextBox.scrollToElement();
    return getGiftMessageTextBox.getCurrentText();
  }

  /**
   * Add new address checkout shipping info page.
   *
   * @return the checkout shipping info page
   */
  public CheckoutShippingInfoPage addNewAddress() {
    logger.info("Click on Add new address button");
    getAddNewAddressButton.click();
    return this.create(CheckoutShippingInfoPage.class);
  }

  /**
   * TODO: Fix up and add back (tests fail when these are uncommented) Edit address checkout
   * shipping info page.
   *
   * @param addressKey the address key
   * @return the checkout shipping info page
   *     <p>public CheckoutShippingInfoPage editAddress(String addressKey) { logger.info("Click on
   *     edit link for address: " + addressKey); List<String> addresses = getAddresses(); int index
   *     = IntStream.range(0, addresses.size()) .filter(i -> addresses.get(i).contains(addressKey))
   *     .findFirst() .getAsInt(); getAddressesEditButton.get(index).click(); return
   *     this.create(CheckoutShippingInfoPage.class); }
   */

  /**
   * Edit shipping date date picker chunk.
   *
   * @return the date picker chunk
   */
  public DatePickerChunk editShippingDate() {
    logger.info("Click on edit shipping date");
    getEditShippingDateButton.click();
    return this.create(DatePickerChunk.class);
  }

  /**
   * Gets addresses.
   *
   * @return the addresses
   *     <p>public List<String> getAddresses() { List<String> result = getAddressesText .stream()
   *     .map( item -> { String text = item.getText(); logger.info("Found address: " + text); return
   *     text; }) .collect(Collectors.toList()); return result; }
   */

  /**
   * Gets shipping date.
   *
   * @return the shipping date
   */
  public String getShippingDate() {
    return getShippingDateText.getText();
  }

  /**
   * Update after shipping info modification checkout shipping info page.
   *
   * @return the checkout shipping info page
   */
  public CheckoutShippingInfoPage updateAfterShippingInfoModification() {
    logger.info("Click on Update button");
    getModifiedAddressUpdateButton.click();
    return this.create(CheckoutShippingInfoPage.class);
  }
}
