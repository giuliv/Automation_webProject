package com.applause.auto.mobile.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Picker;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = AndroidCreateAccountView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CreateAccountView.class, on = Platform.MOBILE_IOS)
public class CreateAccountView extends BaseComponent {

  @Locate(
      xpath =
          "//XCUIElementTypeTextField[@name=\"Email Address\"]/parent::XCUIElementTypeOther/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/passwordToggle\")",
      on = Platform.MOBILE_ANDROID)
  protected Button showPasswordButton;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[2]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBDayPicker;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[1]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBMonthPicker;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[3]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[3]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBYearPicker;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"First Name\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/firstName\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox firstnameTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Last Name\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/lastName\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox lastnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/zipCodeEditText",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getZipCodeTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Date of Birth\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/birthday\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBValueTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@name,\"Intended for users 13+ years old.\")]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[contains(@text,\"Intended for users 13+ years old.\")]",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBGiftTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/phoneNumber", on = Platform.MOBILE_ANDROID)
  protected TextBox getPhoneNumberTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Email Address\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/emailAddress\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox emailAddressTextBox;

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected TextBox getDOBDoneBtn;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeSecureTextField[`value == \"Password\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/password_edit_text\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox passwordTextBox;

  @Locate(xpath = "//*[@name=\"Password\"]", on = Platform.MOBILE_IOS)
  protected TextBox visiblePasswordTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name='Enter your promo code here for special offers.']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='Enter your promo code here for special offers.']",
      on = Platform.MOBILE_ANDROID)
  protected Text getPromoCodeHintTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`value == \"Promo Code (Optional)\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/promoCode\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox promoCodeTextBox;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeButton[`name CONTAINS \"Yes, please send me emails with exclusive offers, rewards, news, and more.\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/receiveMessageFromPeetCheckBox\")",
      on = Platform.MOBILE_ANDROID)
  protected Checkbox emailsWithOffersCheckBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name CONTAINS \"I agree to the\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/agreePrivacyPolicyCheckBox\")",
      on = Platform.MOBILE_ANDROID)
  protected Checkbox agreePrivacyPolicyAndTermsAndConditions;

  @Locate(id = "Privacy Policy", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text,'Privacy Policy')]", on = Platform.MOBILE_ANDROID)
  protected Button getPrivacyPolicyButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name == 'Create Account'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/createAccount\")",
      on = Platform.MOBILE_ANDROID)
  protected Button createAccountButton;

  @Locate(id = "Terms & Conditions", on = Platform.MOBILE_IOS)
  @Locate(id = "termsTextButton", on = Platform.MOBILE_ANDROID)
  protected Button getTermsAndConditionsButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/title\")",
      on = Platform.MOBILE_ANDROID)
  protected Text title;

  @Locate(
      xpath =
          "//XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[@width='45']",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/loader\")",
      on = Platform.MOBILE_ANDROID)
  protected Image loadingSpinner;

  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"cancel\"`]",
      on = Platform.MOBILE_IOS)
  protected Button closeButton;

  /**
   * Privacy policy privacy policy view.
   *
   * @return the privacy policy view
   */
  public PrivacyPolicyView privacyPolicy() {
    logger.info("Tap on Privacy Policy");
    SdkHelper.getSyncHelper().sleep(5000);
    getPrivacyPolicyButton.click();
    SdkHelper.getSyncHelper().sleep(10000);
    MobileHelper.initMobileBrowser();
    // wait till the page load, before it ios is not switched back to app
    SdkHelper.getSyncHelper().sleep(10000);
    return SdkHelper.create(PrivacyPolicyView.class);
  }

  /**
   * Terms and conditions terms and conditions view.
   *
   * @return the terms and conditions view
   */
  public TermsAndConditionsView termsAndConditions() {
    logger.info("Tap on Terms and Conditions");
    getTermsAndConditionsButton.click();
    return SdkHelper.create(TermsAndConditionsView.class);
  }

  @Step("Set first name")
  public CreateAccountView setFirstname(String firstname) {
    logger.info("Set first name to: [{}]", firstname);
    firstnameTextBox.clearText();
    firstnameTextBox.sendKeys(firstname);
    MobileHelper.hideKeyboard();
    return this;
  }

  @Step("Set last name")
  public CreateAccountView setLastname(String lastname) {
    logger.info("Set last name to: " + lastname);
    lastnameTextBox.clearText();
    lastnameTextBox.sendKeys(lastname);
    MobileHelper.hideKeyboard();
    return this;
  }

  /**
   * Sets zip code.
   *
   * @param zipCode the zip code
   * @return the zip code
   */
  public CreateAccountView setZipCode(String zipCode) {
    logger.info("Set ZIP to: " + zipCode);
    getZipCodeTextBox.clearText();
    getZipCodeTextBox.sendKeys(zipCode);
    return this;
  }

  /**
   * Sets phone number.
   *
   * @param phone the phone
   * @return the phone number
   */
  public CreateAccountView setPhoneNumber(String phone) {
    logger.info("Set phone number to: " + phone);
    getPhoneNumberTextBox.clearText();
    getPhoneNumberTextBox.sendKeys(phone + "\n");
    return this;
  }

  @Step("Set Date of Birth")
  public CreateAccountView setDOB(String day, String month, String year) {
    logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
    getDOBValueTextBox.click();

    SdkHelper.getSyncHelper().wait(Until.uiElement(getDOBValueTextBox).present());
    String value = month + " " + day + ", " + year;
    getDOBValueTextBox.sendKeys(value);

    SdkHelper.getSyncHelper().sleep(2000);
    getDOBDoneBtn.click();
    return this;
  }

  @Step("Set email address")
  public CreateAccountView setEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    emailAddressTextBox.clearText();
    emailAddressTextBox.sendKeys(emailAddress + "\n");
    MobileHelper.hideKeyboard();
    return this;
  }

  @Step("Set password")
  public CreateAccountView setPassword(String password) {
    logger.info("Set password to: " + password);
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    passwordTextBox.clearText();
    passwordTextBox.sendKeys(password + "\n");
    MobileHelper.hideKeyboard();
    return this;
  }

  @Step("Set Promo code")
  public CreateAccountView setPromo(String promo) {
    logger.info("Set promo to: [{}]", promo);
    promoCodeTextBox.clearText();
    promoCodeTextBox.sendKeys(promo + "\n");
    SdkHelper.getSyncHelper().sleep(1000); // Wait for promoCode
    MobileHelper.hideKeyboard();
    return this;
  }

  @Step("Tap on Show/Hide Password")
  public CreateAccountView tapOnShowHidePassword() {
    logger.info("Click to show password");
    showPasswordButton.click();
    return this;
  }

  @Step("Check privacy policy and terms and conditions.")
  public void checkPrivacyPolicyAndTermsAndConditions() {
    logger.info("Tapping on Privacy Policy button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    agreePrivacyPolicyAndTermsAndConditions.click();
  }

  @Step("Tap on 'Create Account'")
  public <T extends BaseComponent> T createAccount(Class<T> clazz) {
    logger.info("Create account");
    createAccountButton.click();
    MobileHelper.waitUntilElementDisappears(loadingSpinner, 26);
    return SdkHelper.create(clazz);
  }

  @Step("Tap on 'Create Account'")
  public HomeView tapOnCreateAccount() {
    logger.info("Create account");
    createAccountButton.click();
    MobileHelper.waitUntilElementDisappears(loadingSpinner, 26);
    return SdkHelper.create(HomeView.class);
  }

  /**
   * Gets phone number.
   *
   * @return the phone number
   */
  public String getPhoneNumber() {
    return getPhoneNumberTextBox.getCurrentText();
  }

  /**
   * Gets email address.
   *
   * @return the email address
   */
  public String getEmailAddress() {
    return emailAddressTextBox.getCurrentText();
  }

  /**
   * Gets firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return firstnameTextBox.getCurrentText();
  }

  /**
   * Gets lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return lastnameTextBox.getCurrentText();
  }

  @Step("Gets password text")
  public String getPassword() {
    String password;
    if (visiblePasswordTextBox.exists()) {
      password = visiblePasswordTextBox.getAttributeValue(Attribute.VALUE.getValue().trim());
    } else {
      password = passwordTextBox.getAttributeValue(Attribute.VALUE.getValue().trim());
    }

    logger.info("Current password - [{}]", password);
    return password;
  }

  @Step("Checking if email checkbox checked")
  public boolean isEmailOptInChecked() {
    logger.info("Checking if checkbox checked by color");
    return MobileHelper.isIosCheckboxChecked(emailsWithOffersCheckBox);
  }

  @Step("Check is Create Account Button is Enabled")
  public boolean isCreateAccountButtonEnabled() {
    if (!MobileHelper.isElementDisplayed(createAccountButton, 5)) {
      SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    }
    return createAccountButton.isEnabled();
  }

  @Step("Tap on email opt in checkbox")
  public void tapEmailOptIn() {
    logger.info("Tap on email opt in checkbox");
    emailsWithOffersCheckBox.click();
  }

  @Step("Check Is privacy policy and terms and conditions checked boolean.")
  public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
    if (!MobileHelper.isElementDisplayed(agreePrivacyPolicyAndTermsAndConditions, 5)) {
      SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    }
    return MobileHelper.isIosCheckboxChecked(agreePrivacyPolicyAndTermsAndConditions);
  }

  /**
   * Gets zip code.
   *
   * @return the zip code
   */
  public String getZipCode() {
    return getZipCodeTextBox.getCurrentText();
  }

  public boolean isFirstnameDisplayed() {
    logger.info("Checking firstname field displayed");
    return MobileHelper.isDisplayed(firstnameTextBox);
  }

  public boolean isLastDisplayed() {
    logger.info("Checking lastname field displayed");
    return MobileHelper.isDisplayed(lastnameTextBox);
  }

  public boolean isDobTextDisplayed() {
    logger.info("Checking dob text field displayed");
    return getDOBGiftTextBox.getCurrentText().equals(Constants.TestData.BIRTHDAY_MESSAGE_IOS);
  }

  public boolean isEmailAddressDisplayed() {
    logger.info("Checking email address field displayed");
    return MobileHelper.isDisplayed(emailAddressTextBox);
  }

  public boolean isPasswordDisplayed() {
    logger.info("Checking password field displayed");
    return MobileHelper.isDisplayed(passwordTextBox);
  }

  public boolean isPromocodeTextDisplayed() {
    logger.info("Checking promo code text displayed");
    return MobileHelper.isDisplayed(getPromoCodeHintTextBox);
  }

  @Step("Get Page title")
  public String getTitle() {
    String titleText = title.getText();
    logger.info("Title - [{}]", titleText);
    return titleText;
  }

  @Step("Tap X to return to Peetnik Rewards authentication screen")
  public LandingView close() {
    logger.info("Tapping X button");
    closeButton.click();
    return SdkHelper.create(LandingView.class);
  }
}

class AndroidCreateAccountView extends CreateAccountView {

  /* -------- Elements -------- */

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getDOBOkButton;

  @Override
  @Step("Tap on 'Create Account'")
  public <T extends BaseComponent> T createAccount(Class<T> clazz) {
    logger.info("Create account");
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    createAccountButton.click();
    MobileHelper.waitUntilElementDisappears(loadingSpinner, 26);
    return SdkHelper.create(clazz);
  }

  @Override
  @Step("Checking if email checkbox checked")
  public boolean isEmailOptInChecked() {
    return emailsWithOffersCheckBox.getAttributeValue("checked").equals("true");
  }

  @Override
  @Step("Check Is privacy policy and terms and conditions checked boolean.")
  public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
    if (!MobileHelper.isElementDisplayed(agreePrivacyPolicyAndTermsAndConditions, 5)) {
      MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    }
    return agreePrivacyPolicyAndTermsAndConditions.getAttributeValue("checked").equals("true");
  }

  @Override
  @Step("Gets password text")
  public String getPassword() {
    String password =
        passwordTextBox
            .getAttributeValue(Attribute.TEXT.getValue())
            .replace("Password ", "")
            .trim();
    logger.info("Password: [{}]", password);
    return password;
  }

  @Override
  @Step("Set Promo code")
  public CreateAccountView setPromo(String promo) {
    logger.info("Set promo to: " + promo);
    promoCodeTextBox.clearText();
    promoCodeTextBox.sendKeys(promo);
    MobileHelper.hideKeyboard();
    return this;
  }

  @Override
  @Step("Set password")
  public CreateAccountView setPassword(String password) {
    logger.info("Set password to: " + password);
    passwordTextBox.clearText();
    passwordTextBox.sendKeys(password);
    MobileHelper.hideKeyboard();
    return this;
  }

  public CreateAccountView setPhoneNumber(String phone) {
    logger.info("Set phone number to: " + phone);
    getPhoneNumberTextBox.clearText();
    getPhoneNumberTextBox.sendKeys(phone);
    MobileHelper.hideKeyboard();
    return this;
  }

  @Override
  @Step("Set email address")
  public CreateAccountView setEmailAddress(String emailAddress) {
    logger.info("Set email address to: [{}]", emailAddress);
    emailAddressTextBox.clearText();
    emailAddressTextBox.sendKeys(emailAddress);
    MobileHelper.hideKeyboard();
    return this;
  }

  @Override
  public boolean isDobTextDisplayed() {
    logger.info("Checking dob text field displayed");
    return getDOBGiftTextBox.isDisplayed();
  }

  @Override
  public LandingView close() {
    logger.info("Tapping X button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).visible());
    closeButton.click();
    return SdkHelper.create(LandingView.class);
  }

  @Override
  @Step("Set Date of Birth")
  public CreateAccountView setDOB(String day, String month, String year) {
    logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
    getDOBValueTextBox.click();
    Picker dayPicker = getDOBDayPicker;
    Picker monthPicker = getDOBMonthPicker;
    // SdkHelper.getSyncHelper().wait(Until.uiElement(getDOBDayPicker).visible());
    logger.info("Day picker exist:" + dayPicker.exists());
    logger.info("Day picker visible:" + dayPicker.isDisplayed());

    String dataSource;
    try {
      logger.info("day picker keep: " + dayPicker.getAttributeValue("value"));
      dataSource = "value";
    } catch (Throwable throwable) {
      logger.info("day picker keep: " + dayPicker.getAttributeValue("text"));
      dataSource = "text";
    }

    try {
      Integer.parseInt(dayPicker.getAttributeValue(dataSource));
    } catch (Throwable throwable) {
      logger.info("swapping pickers....");
      dayPicker = getDOBMonthPicker;
      monthPicker = getDOBDayPicker;
    }

    MobileHelper.setPickerValueBasic(day, dayPicker, "next");
    MobileHelper.setPickerValueBasic(month.substring(0, 3), monthPicker, "next");
    MobileHelper.setPickerValueReverse(year, getDOBYearPicker);
    getDOBOkButton.click();

    return SdkHelper.create(CreateAccountView.class);
  }
}
