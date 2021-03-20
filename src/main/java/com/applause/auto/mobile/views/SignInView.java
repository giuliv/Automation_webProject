package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;

@Implementation(is = AndroidSignInView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SignInView.class, on = Platform.MOBILE_IOS)
public class SignInView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/emailAddress", on = Platform.MOBILE_ANDROID)
  protected TextBox getUsernameTextBox;

  @Locate(xpath = "//XCUIElementTypeStaticText[2]", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected TextBox getMessageTextBox;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Ok']", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='OKAY']", on = Platform.MOBILE_ANDROID)
  protected Button getDismissMessageButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Sign In\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/loginButton", on = Platform.MOBILE_ANDROID)
  protected Button getSignInButton;

  @Locate(id = "hide password", on = Platform.MOBILE_IOS)
  @Locate(id = "NA", on = Platform.MOBILE_ANDROID)
  protected Button getShowPasswordButton;

  @Locate(
      xpath =
          "//XCUIElementTypeSecureTextField|//XCUIElementTypeButton[@name=\"reveal password\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
  protected TextBox getPasswordTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"reveal password\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/password", on = Platform.MOBILE_ANDROID)
  protected TextBox getUnEncryptedPasswordTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Forgot Password?\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/forgotPassword", on = Platform.MOBILE_ANDROID)
  protected Button getForgotPasswordButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"At least 6 characters\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/passwordRule1TextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getPasswordLengthRequirementTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"At least 1 number\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/passwordRule2TextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getPasswordContainsNumbersRequirementTextBox;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"At least 1 letter\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/passwordRule3TextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getPasswordContainsLettersRequirementTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeApplication[@name=\"Peets-Sandbox\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/loader", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getLoader;

  /* -------- Actions -------- */

  /**
   * Get the text value of the reward title
   *
   * @return
   */
  public void setUsername(String username) {
    logger.info("Set username: " + username);
    getUsernameTextBox.clearText();
    getUsernameTextBox.sendKeys(username);
    getSyncHelper().sleep(3000);
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    getPasswordTextBox.clearText();
    getPasswordTextBox.initialize();
    getPasswordTextBox.click();
    getSyncHelper().sleep(1500);
    getPasswordTextBox.sendKeys(password);
    getSyncHelper().sleep(1500);
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
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return getMessageTextBox.getCurrentText();
  }

  /**
   * Dismiss message sign in view.
   *
   * @return the sign in view
   */
  public SignInView dismissMessage() {
    logger.info("Dismissing message");
    getDriver().switchTo().alert().accept();
    return this;
  }

  /**
   * Dismiss ok message sign in view.
   *
   * @return the sign in view
   */
  public SignInView dismissOkMessage() {
    logger.info("Dismissing OK message");
    getDismissMessageButton.click();
    return this;
  }

  /**
   * Gets un encrypted password.
   *
   * @return the un encrypted password
   */
  public String getUnEncryptedPassword() {
    logger.info("UnEncrypted password:  " + getUnEncryptedPasswordTextBox.getCurrentText());
    return getUnEncryptedPasswordTextBox.getCurrentText();
  }

  /** Sign in. */
  public DashboardView signIn() {
    return signIn(DashboardView.class);
  }

  /**
   * Sign in t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T signIn(Class<T> clazz) {
    logger.info("Click on Sign In button");
    getSignInButton.initialize();
    getSignInButton.click();
    return this.create(clazz);
  }

  /** Show password. */
  public void showPassword() {
    logger.info("Click on Show Password button");
    getShowPasswordButton.click();
  }

  /**
   * Is email field displayed
   *
   * @return boolean
   */
  public boolean isEmailFieldDisplayed() {
    return getUsernameTextBox.isDisplayed();
  }

  /**
   * Is password field displayed
   *
   * @return boolean
   */
  public boolean isPasswordFieldDisplayed() {
    return getPasswordTextBox.isDisplayed();
  }

  /**
   * Is show password button displayed
   *
   * @return boolean
   */
  public boolean isShowPasswordButtonDisplayed() {
    return getShowPasswordButton.isDisplayed();
  }

  /**
   * Is forgot password link displayed
   *
   * @return boolean
   */
  public boolean isForgotPasswordLinkDisplayed() {
    return getForgotPasswordButton.isDisplayed();
  }

  /**
   * Is sign in button enabled
   *
   * @return boolean
   */
  public boolean isSignInButtonEnabled() {
    return getSignInButton.isEnabled();
  }

  /**
   * Is password length requirement displayed
   *
   * @return boolean
   */
  public boolean isPasswordLengthRequirementDisplayed() {
    return getPasswordLengthRequirementTextBox.isDisplayed();
  }

  /**
   * Is password contains numbers requirement displayed
   *
   * @return boolean
   */
  public boolean isPasswordContainsNumbersRequirementDisplayed() {
    return getPasswordContainsNumbersRequirementTextBox.isDisplayed();
  }

  /**
   * Is password contains letters requirement displayed
   *
   * @return boolean
   */
  public boolean isPasswordContainsLettersRequirementDisplayed() {
    return getPasswordContainsLettersRequirementTextBox.isDisplayed();
  }
}

class AndroidSignInView extends SignInView {

  /* -------- Actions -------- */

  @Override
  public void showPassword() {
    logger.info("Click on Show Password button");
    MobileElement element = getPasswordTextBox.getMobileElement();
    int x = element.getCenter().getX();
    int y = element.getCenter().getY();
    int width = element.getSize().getWidth();
    AppiumDriver driver = (AppiumDriver) getDriver();
    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();
  }

  @Override
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    // TODO: figure out why this code block is causing an exception on Android
    // while (getPasswordTextBox.getCurrentText().length() != 0) {
    // getPasswordTextBox.clearText();
    // }

    getPasswordTextBox.sendKeys(password);
  }

  @Override
  public String getPassword() {
    return getPasswordTextBox.getAttributeValue("text");
  }

  @Override
  public String getUnEncryptedPassword() {
    logger.info(
        "UnEncrypted password:  "
            + getUnEncryptedPasswordTextBox.getAttributeValue("text").replace("Password ", ""));
    return getUnEncryptedPasswordTextBox.getAttributeValue("text").replace("Password ", "");
  }

  @Override
  public SignInView dismissMessage() {
    logger.info("Dismissing message");
    getDismissMessageButton.click();
    return this;
  }

  @Override
  public String getMessage() {
    return getMessageTextBox.getAttributeValue("text");
  }

  @Override
  public boolean isShowPasswordButtonDisplayed() {
    // There's no show password element id for Android
    return true;
  }

  public <T extends BaseComponent> T signIn(Class<T> clazz) {
    logger.info("Click on Sign In button");
    getDeviceControl().hideKeyboard();
    getSignInButton.click();
    if (getLoader.exists()) {
      getSyncHelper()
          .wait(Until.uiElement(getLoader).notPresent().setTimeout(Duration.ofSeconds(60)));
    }
    return this.create(clazz);
  }
}
