package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = ProfileDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosProfileDetailsView.class, on = Platform.MOBILE_IOS)
public class ProfileDetailsView extends BaseComponent {

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[1]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/firstName\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getFirstnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[2]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/lastName\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getLastnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[3]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/zipCode\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getZipCodeTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[4]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/birthday\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[5]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/phoneNumber\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getPhoneNumberTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[6]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/emailAddress\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getEmailAddressTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton/../XCUIElementTypeOther[.//XCUIElementTypeOther[5]]//XCUIElementTypeOther[7]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/confirmEmailAddress\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getConfirmEmailAddressTextBox;

  @Locate(id = "Save", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/saveButton", on = Platform.MOBILE_ANDROID)
  protected Button getSaveButton;

  @Locate(id = "Change Password", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/changePassword", on = Platform.MOBILE_ANDROID)
  protected Button getChangePasswordButton;

  //  @Locate(id = "button back", on = Platform.MOBILE_IOS) //Review if changed indeed [15.01.2021]
  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeNavigationBar[`name == 'PROFILE DETAILS'`]/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button backButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name='PROFILE DETAILS']", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='PROFILE DETAILS']",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignature;

  @Locate(
      xpath =
          "//XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther//XCUIElementTypeOther[4]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/birthday", on = Platform.MOBILE_ANDROID)
  protected TextBox getBirthdayTextBox;

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
    SdkHelper.getDeviceControl().tapElementCenter(getChangePasswordButton);
    return SdkHelper.create(ChangePasswordView.class);
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
    SdkHelper.getDeviceControl().hideKeyboard();
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.DOWN);
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
    return getPhoneNumberTextBox.getAttributeValue("text").replaceAll("[^\\d.]", "").trim();
  }

  /**
   * Gets email address.
   *
   * @return the email address
   */
  public String getEmailAddress() {
    // probably should be overridden for iOS with get 'value' attribute
    return getEmailAddressTextBox.getAttributeValue("text").replace("Email Address ", "");
  }

  /**
   * Gets firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    // probably should be overridden for iOS with get 'value' attribute
    return getFirstnameTextBox.getAttributeValue("text").replace("First Name ", "");
  }

  /**
   * Gets lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    // probably should be overridden for iOS with get 'value' attribute
    return getLastnameTextBox.getAttributeValue("text").replace("Last Name ", "");
  }

  /**
   * Gets dob.
   *
   * @return the dob
   */
  public String getDOB() {
    return getDOBTextBox.getAttributeValue("text").replace("Date of Birth ", "");
  }

  @Step("Navigate back")
  public <T extends BaseComponent> T goBack(Class<T> clazz) {
    logger.info("Tapping back button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(backButton).present().setTimeout(Duration.ofSeconds(15)));
    backButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  public String getZipCode() {
    // probably should be overridden for iOS with get 'value' attribute
    return getZipCodeTextBox.getAttributeValue("text").replace("Zip Code (Optional) ", "");
  }

  /**
   * Save account menu mobile chunk.
   *
   * @return the account menu mobile chunk
   */
  public MoreOptionsView save() {
    logger.info("Click on SAVE button");
    SdkHelper.getDeviceControl().hideKeyboard();
    getSaveButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  /**
   * Is change password link available boolean.
   *
   * @return the boolean
   */
  public boolean isChangePasswordLinkAvailable() {
    logger.info("Checking if password link available");
    try {
      getChangePasswordButton.initialize();
      return getChangePasswordButton.isDisplayed();
    } catch (Throwable throwable) {
      logger.info("Password change button does not found");
    }
    return false;
  }

  @Step("Verify save button is available")
  public boolean isSaveButtonAvailable() {
    logger.info("Checking if Save link available");
    try {
      getSaveButton.initialize();
      return getSaveButton.isDisplayed();
    } catch (Throwable throwable) {
      logger.info("Save button does not found");
    }
    return false;
  }

  @Step("Verify Back button is displayed")
  public boolean isBackButtonDisplayed() {
    return backButton.isDisplayed();
  }

  @Step("Verify Header is displayed")
  public boolean isHeaderDisplayed() {
    return getSignature.isDisplayed();
  }

  @Step("Verify birthday field is displayed")
  public boolean isBirthdayFieldDisplayed() {
    return getBirthdayTextBox.isDisplayed();
  }

  @Step("Verify email field is displayed")
  public boolean isEmailFieldDisplayed() {
    return getEmailAddressTextBox.isDisplayed();
  }

  @Step("Verify first name field is displayed")
  public boolean isFirstNameFieldDisplayed() {
    return getFirstnameTextBox.isDisplayed();
  }

  @Step("Verify last name field is displayed")
  public boolean isLastNameFieldDisplayed() {
    return getLastnameTextBox.isDisplayed();
  }

  @Step("Verify phone number field is displayed")
  public boolean isPhoneNumberFieldDisplayed() {
    return getPhoneNumberTextBox.isDisplayed();
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
   * Gets phone number.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return getPhoneNumberTextBox.getAttributeValue("value").replaceAll("[^\\d.]", "").trim();
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

  public MoreOptionsView save() {
    logger.info("Click on SAVE button");
    if (getDoneButton.exists()) {
      getDoneButton.click();
    }
    getSaveButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Override
  public String getDOB() {
    return getDOBTextBox.getAttributeValue("value");
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

  public ChangePasswordView changePassword() {
    logger.info("Tap on change password button");
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    getChangePasswordButton.initialize();
    SdkHelper.getDeviceControl().tapElementCenter(getChangePasswordButton);
    return SdkHelper.create(ChangePasswordView.class);
  }
}
