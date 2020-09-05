package com.applause.auto.mobile.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
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
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

import org.openqa.selenium.Dimension;

import java.util.List;
import java.util.stream.Collectors;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@Implementation(is = AndroidCreateAccountView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CreateAccountView.class, on = Platform.MOBILE_IOS)
public class CreateAccountView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "(//XCUIElementTypeButton[@name=\"hide password\"])[1]", on = Platform.MOBILE_IOS)
  @Locate(id = "", on = Platform.MOBILE_ANDROID)
  protected Button getShowPasswordButton;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"reveal password\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "", on = Platform.MOBILE_ANDROID)
  protected Button getHidePasswordButton;

  @Locate(xpath = "(//XCUIElementTypeButton[@name=\"hide password\"])[2]", on = Platform.MOBILE_IOS)
  @Locate(id = "", on = Platform.MOBILE_ANDROID)
  protected Button getShowConfirmationPasswordButton;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"reveal password\"])[2]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "", on = Platform.MOBILE_ANDROID)
  protected Button getHideConfirmationPasswordButton;

  @Locate(
      xpath = "//XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[2]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBDayPicker;

  @Locate(
      xpath = "//XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[1]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBMonthPicker;

  @Locate(
      xpath = "//XCUIElementTypeDatePicker/XCUIElementTypePicker/XCUIElementTypePickerWheel[3]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "(//*[@resource-id='android:id/numberpicker_input'])[3]",
      on = Platform.MOBILE_ANDROID)
  protected Picker getDOBYearPicker;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/firstName", on = Platform.MOBILE_ANDROID)
  protected TextBox getFirstnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/lastName", on = Platform.MOBILE_ANDROID)
  protected TextBox getLastnameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/zipCodeEditText",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getZipCodeTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[4]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/birthday", on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBValueTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Intended for users 13+ years old. Plus, get a birthday drink on us!\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='Your birthday drink is on us']",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getDOBGiftTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[5]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/phoneNumber", on = Platform.MOBILE_ANDROID)
  protected TextBox getPhoneNumberTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[6]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/emailAddress", on = Platform.MOBILE_ANDROID)
  protected TextBox getEmailAddressTextBox;

  @Locate(id = "Done", on = Platform.MOBILE_IOS)
  protected TextBox getDOBDoneBtn;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[7]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/confirmEmailAddress",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getConfirmEmailAddressTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeSecureTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
  protected TextBox getHiddenPasswordTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
  protected TextBox getPasswordTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[9]/XCUIElementTypeSecureTextField",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/confirmPassword",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getConfirmPasswordTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[8]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage/following-sibling::XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[contains(@resource-id,'id/passwordRule')]",
      on = Platform.MOBILE_ANDROID)
  protected List<Text> getPasswordHintTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name='Enter your promo code here for special offers.']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='Enter your promo code here for special offers.']",
      on = Platform.MOBILE_ANDROID)
  protected Text getPromoCodeHintTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[10]/XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/promoCode", on = Platform.MOBILE_ANDROID)
  protected TextBox getPromoCodeTextBox;

  @Locate(
      xpath =
          "(//XCUIElementTypeTextView[@value='Yes, please send me emails with exclusive offers, rewards, news, and more.']/following-sibling::XCUIElementTypeButton)[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/receiveMessageFromPeetCheckBox",
      on = Platform.MOBILE_ANDROID)
  protected Checkbox getEmailsWithOffersCheckBox;

  @Locate(
      xpath =
          "(//XCUIElementTypeTextView[contains(@value,'I agree to the Privacy Policy and Terms')]/following-sibling::XCUIElementTypeButton | //XCUIElementTypeTextView[contains(@value,'I agree to the Privacy Policy and Terms')]/preceding-sibling::XCUIElementTypeButton)[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/agreePrivacyPolicyCheckBox",
      on = Platform.MOBILE_ANDROID)
  protected Checkbox getAgreePrivacyPolicyAndTermsAndConditions;

  @Locate(id = "Privacy Policy", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text,'Privacy Policy')]", on = Platform.MOBILE_ANDROID)
  protected Button getPrivacyPolicyButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Create Account\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/createAccount", on = Platform.MOBILE_ANDROID)
  protected Button getCreateAccountButton;

  @Locate(id = "Terms & Conditions", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@text,'Terms')]", on = Platform.MOBILE_ANDROID)
  protected Button getTermsAndConditionsButton;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar[@name=\"CREATE ACCOUNT\"]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='CREATE ACCOUNT']", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(
      xpath =
          "//XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[@width='45']",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "TBD", on = Platform.MOBILE_ANDROID)
  protected Image loadingSpinner;

  /* -------- Actions -------- */

  /**
   * Privacy policy privacy policy view.
   *
   * @return the privacy policy view
   */
  public PrivacyPolicyView privacyPolicy() {
    logger.info("Tap on Privacy Policy");
    getPrivacyPolicyButton.click();

    // wait till the page load, before it ios is not switched back to app
    SyncHelper.sleep(10000);
    return ComponentFactory.create(PrivacyPolicyView.class);
  }

  /**
   * Terms and conditions terms and conditions view.
   *
   * @return the terms and conditions view
   */
  public TermsAndConditionsView termsAndConditions() {
    logger.info("Tap on Terms and Conditions");
    getTermsAndConditionsButton.click();
    SyncHelper.sleep(10000);
    return ComponentFactory.create(TermsAndConditionsView.class);
  }

  /**
   * Sets firstname.
   *
   * @param firstname the firstname
   * @return the firstname
   */
  public CreateAccountView setFirstname(String firstname) {
    logger.info("Set first name to: " + firstname);
    getFirstnameTextBox.clearText();
    getFirstnameTextBox.sendKeys(firstname);
    return this;
  }

  /**
   * Sets lastname.
   *
   * @param lastname the lastname
   * @return the lastname
   */
  public CreateAccountView setLastname(String lastname) {
    logger.info("Set last name to: " + lastname);
    getLastnameTextBox.clearText();
    getLastnameTextBox.sendKeys(lastname);
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

  /**
   * Sets dob.
   *
   * @param day the day
   * @param month the month
   * @param year the year
   * @return the dob
   */
  public CreateAccountView setDOB(String day, String month, String year) {
    logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
    getDOBValueTextBox.click();
    Picker dayPicker = getDOBDayPicker;
    Picker monthPicker = getDOBMonthPicker;
    SyncHelper.wait(Until.uiElement(getDOBDayPicker).visible());

    logger.info("day picker keep: " + dayPicker.getAttributeValue("value"));
    try {
      Integer.parseInt(dayPicker.getAttributeValue("value"));
    } catch (Throwable throwable) {
      logger.info("swapping pickers....");
      dayPicker = getDOBMonthPicker;
      monthPicker = getDOBDayPicker;
    }

    SyncHelper.sleep(500);
    MobileHelper.setPickerValueBasic(day, dayPicker, "next");
    MobileHelper.setPickerValueBasic(month, monthPicker, "next");
    MobileHelper.setPickerValueReverse(year, getDOBYearPicker);
    getDOBDoneBtn.click();
    return this;
  }

  /**
   * Sets email address.
   *
   * @param emailAddress the email address
   * @return the email address
   */
  public CreateAccountView setEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    getEmailAddressTextBox.clearText();
    getEmailAddressTextBox.sendKeys(emailAddress + "\n");
    return this;
  }

  /**
   * Sets confirm email address.
   *
   * @param emailAddress the email address
   * @return the confirm email address
   */
  public CreateAccountView setConfirmEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    getConfirmEmailAddressTextBox.clearText();
    getConfirmEmailAddressTextBox.sendKeys(emailAddress + "\n");
    return this;
  }

  /**
   * Sets password.
   *
   * @param password the password
   * @return the password
   */
  public CreateAccountView setPassword(String password) {
    logger.info("Set password to: " + password);
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    getHiddenPasswordTextBox.clearText();
    getHiddenPasswordTextBox.sendKeys(password + "\n");
    return this;
  }

  /**
   * Sets confirmation password.
   *
   * @param password the password
   * @return the confirmation password
   */
  public CreateAccountView setConfirmationPassword(String password) {
    logger.info("Set confirmation password to: " + password);
    getConfirmPasswordTextBox.clearText();
    getConfirmPasswordTextBox.sendKeys(password + "\n");
    return this;
  }

  /**
   * Sets promo.
   *
   * @param promo the promo
   * @return the promo
   */
  public CreateAccountView setPromo(String promo) {
    logger.info("Set promo to: " + promo);
    getPromoCodeTextBox.clearText();
    getPromoCodeTextBox.sendKeys(promo + "\n");
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    return this;
  }

  /**
   * Show password create account view.
   *
   * @return the create account view
   */
  public CreateAccountView showPassword() {
    logger.info("Click to show password");
    getShowPasswordButton.click();
    return this;
  }

  /**
   * Hide password create account view.
   *
   * @return the create account view
   */
  public CreateAccountView hidePassword() {
    logger.info("Click to hide password");
    getHidePasswordButton.click();
    return this;
  }

  /** Check privacy policy and terms and conditions. */
  public void checkPrivacyPolicyAndTermsAndConditions() {
    logger.info("Click on Privacy Policy button");
    getAgreePrivacyPolicyAndTermsAndConditions.click();
  }

  /**
   * Create account dashboard view.
   *
   * @return the dashboard view
   */
  public DashboardView createAccount() {
    logger.info("Create account");
    getCreateAccountButton.click();
    // wait while dashboard view will be created and loaded (10s!!)
    // temp case while waiter below is not working properly

    SyncHelper.sleep(10000);
    //    SyncHelper.wait(
    //        Until.uiElement(loadingSpinner).notPresent().setTimeout(Duration.ofSeconds(45)));
    return ComponentFactory.create(DashboardView.class);
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
    return getEmailAddressTextBox.getCurrentText();
  }

  /**
   * Gets firstname.
   *
   * @return the firstname
   */
  public String getFirstname() {
    return getFirstnameTextBox.getCurrentText();
  }

  /**
   * Gets lastname.
   *
   * @return the lastname
   */
  public String getLastname() {
    return getLastnameTextBox.getCurrentText();
  }

  /**
   * Gets dob.
   *
   * @return the dob
   */
  public String getDOB() {
    return getDOBValueTextBox.getCurrentText();
  }

  /**
   * Gets hidden password.
   *
   * @return the hidden password
   */
  public String getHiddenPassword() {
    return getHiddenPasswordTextBox.getAttributeValue("value");
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
    return getPasswordTextBox.getCurrentText();
  }

  /**
   * Is email opt in checked boolean.
   *
   * @return the boolean
   */
  public boolean isEmailOptInChecked() {
    return MobileHelper.isAttribtuePresent(getEmailsWithOffersCheckBox.getMobileElement(), "value");
  }

  /**
   * Is create account button enabled boolean.
   *
   * @return the boolean
   */
  public boolean isCreateAccountButtonEnabled() {
    return getCreateAccountButton.isEnabled();
  }

  /** Tap email opt in. */
  public void tapEmailOptIn() {
    logger.info("Tap on email opt in checkbox");
    getEmailsWithOffersCheckBox.click();
  }

  /**
   * Is privacy policy and terms and conditions checked boolean.
   *
   * @return the boolean
   */
  public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    SyncHelper.sleep(1000);
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    SyncHelper.sleep(1000);
    return MobileHelper.isAttribtuePresent(
        getAgreePrivacyPolicyAndTermsAndConditions.getMobileElement(), "value");
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
    return getFirstnameTextBox.isDisplayed();
  }

  public boolean isLastDisplayed() {
    logger.info("Checking lastname field displayed");
    return getLastnameTextBox.isDisplayed();
  }

  public boolean isZipCodeDisplayed() {
    logger.info("Checking zip code field displayed");
    return getZipCodeTextBox.isDisplayed();
  }

  public boolean isDobTextDisplayed() {
    logger.info("Checking dob text field displayed");
    return getDOBGiftTextBox.getCurrentText().equals(Constants.TestData.BIRTHDAY_MESSAGE_IOS);
  }

  public boolean isEmailAddressDisplayed() {
    logger.info("Checking email address field displayed");
    return getEmailAddressTextBox.isDisplayed();
  }

  public boolean isConfirmEmailAddressDisplayed() {
    logger.info("Checking confirm email field displayed");
    return getConfirmEmailAddressTextBox.isDisplayed();
  }

  public boolean isPhoneNumberDisplayed() {
    logger.info("Checking phone field displayed");
    return getPhoneNumberTextBox.isDisplayed();
  }

  public boolean isPasswordDisplayed() {
    logger.info("Checking password field displayed");
    return getHiddenPasswordTextBox.isDisplayed();
  }

  public boolean isConfirmPasswordDisplayed() {
    logger.info("Checking confirm password field displayed");
    MobileHelper.scrollDownHalfScreen(1);
    return getConfirmPasswordTextBox.isDisplayed();
  }

  public boolean isPasswordTextDisplayed() {
    logger.info("Checking password text displayed");
    getHiddenPasswordTextBox.sendKeys(" ");
    boolean result =
        getPasswordHintTextBox.stream()
            .map(item -> item.getText())
            .collect(Collectors.joining("\n"))
            .equals("At least 6 characters\n" + "At least 1 number\n" + "At least 1 letter");
    getHiddenPasswordTextBox.clearText();
    getHiddenPasswordTextBox.sendKeys("\n");
    return result;
  }

  public boolean isPromocodeTextDisplayed() {
    logger.info("Checking promocode text displayed");
    return getPromoCodeHintTextBox.isDisplayed();
  }
}

class AndroidCreateAccountView extends CreateAccountView {

  /* -------- Elements -------- */

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getDOBOkButton;

  /* -------- Actions -------- */

  @Override
  public DashboardView createAccount() {
    logger.info("Create account");
    getCreateAccountButton.click();
    SyncHelper.sleep(35000);
    return ComponentFactory.create(DashboardView.class);
  }

  @Override
  public AndroidTermsAndConditionsView termsAndConditions() {
    logger.info("Tap on Terms and Conditions");
    // sometimes this code is throwing exception: Coordinate x =
    // [238572] exceeds the width of element
    Dimension size = getTermsAndConditionsButton.getMobileElement().getSize();
    logger.info(String.format("Terms label size: [%s]", size));
    MobileHelper.tapOnElementWithOffset(getTermsAndConditionsButton, size.getWidth() / 3, 0);
    // logger.info("Tap on Terms and Conditions");
    // getTermsAndConditionsButton.click();
    return ComponentFactory.create(AndroidTermsAndConditionsView.class);
  }

  @Override
  public CreateAccountView setDOB(String day, String month, String year) {
    logger.info(String.format("Set DOB number to: %s / %s / %s", day, month, year));
    getDOBValueTextBox.click();
    Picker dayPicker = getDOBDayPicker;
    Picker monthPicker = getDOBMonthPicker;
    SyncHelper.wait(Until.uiElement(getDOBDayPicker).visible());
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
    //    DeviceControl.hideKeyboard();
    getDOBOkButton.click();

    return ComponentFactory.create(CreateAccountView.class);
  }

  @Override
  public CreateAccountView showPassword() {
    logger.info("Click on Show Password button");
    MobileElement element = getPasswordTextBox.getMobileElement();
    int x = element.getCenter().getX();
    int y = element.getCenter().getY();
    int width = element.getSize().getWidth();
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();
    return this;
  }

  @Override
  public CreateAccountView hidePassword() {
    logger.info("Click to hide password");
    MobileElement element = getPasswordTextBox.getMobileElement();
    int x = element.getCenter().getX();
    int y = element.getCenter().getY();
    int width = element.getSize().getWidth();
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();
    return this;
  }

  @Override
  public boolean isEmailOptInChecked() {
    return getEmailsWithOffersCheckBox.getAttributeValue("checked").equals("true");
  }

  @Override
  public boolean isPrivacyPolicyAndTermsAndConditionsChecked() {
    SyncHelper.sleep(1000);
    MobileHelper.swipeWithCount(SwipeDirection.UP, 2);
    SyncHelper.sleep(1000);
    return getAgreePrivacyPolicyAndTermsAndConditions.getAttributeValue("checked").equals("true");
  }

  @Override
  public String getPassword() {
    logger.info(
        "Password: " + getPasswordTextBox.getAttributeValue("text").replace("Password ", ""));
    return getPasswordTextBox.getAttributeValue("text").replace("Password ", "");
  }

  @Override
  public String getHiddenPassword() {
    return getPasswordTextBox.getAttributeValue("text");
  }

  public CreateAccountView setPromo(String promo) {
    logger.info("Set promo to: " + promo);
    getPromoCodeTextBox.clearText();
    getPromoCodeTextBox.sendKeys(promo);
    DeviceControl.hideKeyboard();
    return this;
  }

  public CreateAccountView setPassword(String password) {
    logger.info("Set password to: " + password);
    getHiddenPasswordTextBox.clearText();
    getHiddenPasswordTextBox.sendKeys(password);
    DeviceControl.hideKeyboard();
    return this;
  }

  public CreateAccountView setConfirmationPassword(String password) {
    logger.info("Set confirmation password to: " + password);
    getConfirmPasswordTextBox.clearText();
    getConfirmPasswordTextBox.sendKeys(password);
    DeviceControl.hideKeyboard();
    return this;
  }

  public CreateAccountView setPhoneNumber(String phone) {
    logger.info("Set phone number to: " + phone);
    getPhoneNumberTextBox.clearText();
    getPhoneNumberTextBox.sendKeys(phone);
    DeviceControl.hideKeyboard();
    return this;
  }

  public CreateAccountView setEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    getEmailAddressTextBox.clearText();
    getEmailAddressTextBox.sendKeys(emailAddress);
    DeviceControl.hideKeyboard();
    return this;
  }

  public CreateAccountView setConfirmEmailAddress(String emailAddress) {
    logger.info("Set email address to: " + emailAddress);
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.UP);
    getConfirmEmailAddressTextBox.clearText();
    getConfirmEmailAddressTextBox.sendKeys(emailAddress);
    DeviceControl.hideKeyboard();
    return this;
  }

  public boolean isPasswordTextDisplayed() {
    logger.info("Checking password text displayed");
    getHiddenPasswordTextBox.sendKeys(" ");
    boolean result =
        getPasswordHintTextBox.stream()
            .map(item -> item.getText())
            .collect(Collectors.joining("\n"))
            .equals("At least 6 characters\n" + "At least 1 number\n" + "At least 1 letter");
    getHiddenPasswordTextBox.clearText();
    return result;
  }

  public boolean isDobTextDisplayed() {
    logger.info("Checking dob text field displayed");
    return getDOBGiftTextBox.isDisplayed();
  }
}
