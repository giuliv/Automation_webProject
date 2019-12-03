package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;

@Implementation(is = ProfileDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosProfileDetailsView.class, on = Platform.MOBILE_IOS)
public class ProfileDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/firstName", on = Platform.MOBILE_ANDROID)
  protected TextBox getFirstnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/lastName", on = Platform.MOBILE_ANDROID)
  protected TextBox getLastnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/zipCode", on = Platform.MOBILE_ANDROID)
  protected TextBox getZipCodeTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/birthday", on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/phoneNumber", on = Platform.MOBILE_ANDROID)
  protected TextBox getPhoneNumberTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[6]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/emailAddress", on = Platform.MOBILE_ANDROID)
  protected TextBox getEmailAddressTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[7]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/confirmEmailAddress",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getConfirmEmailAddressTextBox;

  @Locate(id = "Save", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/saveButton", on = Platform.MOBILE_ANDROID)
  protected Button getSaveButton;

  @Locate(id = "Change Password", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/changePassword", on = Platform.MOBILE_ANDROID)
  protected Button getChangePasswordButton;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc='Navigate up']",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(xpath = "//XCUIElementTypeOther[@name=\"PROFILE DETAILS\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='PROFILE DETAILS']",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignature;

  /* -------- Actions -------- */

  /**
   * Sets firstname.
   *
   * @param firstname the firstname
   * @return the firstname
   */
  public ProfileDetailsView setFirstname(String firstname) {
    logger.info("Set first name to: " + firstname);
    getFirstnameTextBox.sendKeys(firstname);
    return this;
  }

  /**
   * Change password profile details view.
   *
   * @return the profile details view
   */
  public ChangePasswordView changePassword() {
    logger.info("Tap on change password button");
    DeviceControl.tapElementCenter(getChangePasswordButton);
    return ComponentFactory.create(ChangePasswordView.class);
  }

  /**
   * Sets lastname.
   *
   * @param lastname the lastname
   * @return the lastname
   */
  public ProfileDetailsView setLastname(String lastname) {
    logger.info("Set last name to: " + lastname);
    getLastnameTextBox.sendKeys(lastname);
    return this;
  }

  /**
   * Sets zip code.
   *
   * @param zipCode the zip code
   * @return the zip code
   */
  public ProfileDetailsView setZipCode(String zipCode) {
    logger.info("Set ZIP to: " + zipCode);
    getZipCodeTextBox.sendKeys(zipCode);
    return this;
  }

  /**
   * Sets phone number.
   *
   * @param phone the phone
   * @return the phone number
   */
  public ProfileDetailsView setPhoneNumber(String phone) {
    logger.info("Set phone number to: " + phone);
    getPhoneNumberTextBox.clearText();
    getPhoneNumberTextBox.sendKeys(phone);
    return this;
  }

  /**
   * Sets email address.
   *
   * @param emailAddress the email address
   * @return the email address
   */
  public ProfileDetailsView setEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    getEmailAddressTextBox.clearText();
    getEmailAddressTextBox.sendKeys(emailAddress);
    return this;
  }

  /**
   * Sets confirm email address.
   *
   * @param emailAddress the email address
   * @return the confirm email address
   */
  public ProfileDetailsView setConfirmEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    DeviceControl.hideKeyboard();
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.DOWN);
    getConfirmEmailAddressTextBox.clearText();
    getConfirmEmailAddressTextBox.sendKeys(emailAddress);
    return this;
  }

  /**
   * Gets phone number.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return getPhoneNumberTextBox.getAttributeValue("text");
  }

  /**
   * Gets email address.
   *
   * @return the email address
   */
  public String getEmailAddress() {
    // probably should be overridden for iOS with get 'value' attribute
    return getEmailAddressTextBox.getAttributeValue("text");
  }

  /**
   * Gets firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    // probably should be overridden for iOS with get 'value' attribute
    return getFirstnameTextBox.getAttributeValue("text");
  }

  /**
   * Gets lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    // probably should be overridden for iOS with get 'value' attribute
    return getLastnameTextBox.getAttributeValue("text");
  }

  /**
   * Gets dob.
   *
   * @return the dob
   */
  public String getDOB() {
    return getDOBTextBox.getAttributeValue("text");
  }

  /**
   * Go back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T goBack(Class<T> clazz) {
    logger.info("Tap back button");
    getBackButton.click();
    SyncHelper.sleep(4000);
    return ComponentFactory.create(clazz);
  }

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  public String getZipCode() {
    // probably should be overridden for iOS with get 'value' attribute
    return getZipCodeTextBox.getAttributeValue("text");
  }

  /**
   * Save account menu mobile chunk.
   *
   * @return the account menu mobile chunk
   */
  public AccountMenuMobileChunk save() {
    logger.info("Click on SAVE button");
    DeviceControl.hideKeyboard();
    getSaveButton.click();
    SyncHelper.sleep(5000);
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }
}

class IosProfileDetailsView extends ProfileDetailsView {

  /* -------- Elements -------- */

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected Button getDoneButton;

  /* -------- Actions -------- */

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  public String getZipCode() {
    return getZipCodeTextBox.getAttributeValue("value");
  }

  /**
   * Gets email address.
   *
   * @return the email address
   */
  public String getEmailAddress() {
    return getEmailAddressTextBox.getAttributeValue("value");
  }

  /**
   * Gets firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return getFirstnameTextBox.getAttributeValue("value");
  }

  /**
   * Gets lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return getLastnameTextBox.getAttributeValue("value");
  }

  public AccountMenuMobileChunk save() {
    logger.info("Click on SAVE button");
    getDoneButton.click();
    getSaveButton.click();
    SyncHelper.sleep(5000);
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  public ProfileDetailsView setConfirmEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    getDoneButton.click();
    getConfirmEmailAddressTextBox.clearText();
    getConfirmEmailAddressTextBox.sendKeys(emailAddress);
    return this;
  }

  public ProfileDetailsView setZipCode(String zipCode) {
    logger.info("Set ZIP to: " + zipCode);
    getZipCodeTextBox.clearText();
    getZipCodeTextBox.sendKeys(zipCode);
    return this;
  }

  public ProfileDetailsView setLastname(String lastname) {
    logger.info("Set last name to: " + lastname);
    getLastnameTextBox.clearText();
    getLastnameTextBox.sendKeys(lastname);
    return this;
  }

  public ProfileDetailsView setFirstname(String firstname) {
    logger.info("Set first name to: " + firstname);
    getFirstnameTextBox.clearText();
    getFirstnameTextBox.sendKeys(firstname);
    return this;
  }
}
