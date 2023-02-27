package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.tooltips.BaseTooltipComponent;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.time.Duration;
import org.openqa.selenium.Dimension;

@Implementation(is = AndroidSignInView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SignInView.class, on = Platform.MOBILE_IOS)
public class SignInView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(iOSClassChain = "**/XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/emailAddress\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox emailTextBox;

  @Locate(xpath = "//XCUIElementTypeStaticText[2]", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected TextBox getMessageTextBox;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Ok']", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='OKAY']", on = Platform.MOBILE_ANDROID)
  protected Button getDismissMessageButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == 'Sign In'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/loginButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button signInButton;

  @Locate(id = "hide password", on = Platform.MOBILE_IOS)
  @Locate(id = "NA", on = Platform.MOBILE_ANDROID)
  protected Button showPasswordButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeSecureTextField", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/password\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox passwordTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeTextField[`label == \"Password\"`]",
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
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/loader\")",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getLoader;

  /* -------- Actions -------- */

  /**
   * Get the text value of the reward title
   *
   * @return
   */
  public void setEmail(String email) {
    logger.info("Set email: " + email);
    emailTextBox.clearText();
    emailTextBox.sendKeys(email);
    SdkHelper.getSyncHelper().sleep(3000);
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    passwordTextBox.clearText();
    passwordTextBox.initialize();
    passwordTextBox.click();
    SdkHelper.getSyncHelper().sleep(1500);
    for (int i = 0; i < password.length(); i++) {
      passwordTextBox.sendKeys(String.valueOf(password.charAt(i)));
      SdkHelper.getSyncHelper().sleep(500);
    }
    SdkHelper.getSyncHelper().sleep(3000);
  }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
    return passwordTextBox.getCurrentText();
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
    SdkHelper.getDriver().switchTo().alert().accept();
    return this;
  }

  /**
   * Dismiss ok message sign in view.
   *
   * @return the sign in view
   */
  public SignInView dismissOkMessage() {
    logger.info("Dismissing OK message");
    try {
      getDismissMessageButton.click();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
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
    signInButton.initialize();
    signInButton.click();
    SdkHelper.create(BaseTooltipComponent.class).closeAnyTooltipIfDisplayed(5);
    return SdkHelper.create(clazz);
  }

  /** Show password. */
  public void showPassword() {
    logger.info("Click on Show Password button");
    showPasswordButton.click();
  }

  /**
   * Is email field displayed
   *
   * @return boolean
   */
  public boolean isEmailFieldDisplayed() {
    return MobileHelper.isElementDisplayed(emailTextBox, 5);
  }

  /**
   * Is password field displayed
   *
   * @return boolean
   */
  public boolean isPasswordFieldDisplayed() {
    return MobileHelper.isElementDisplayed(passwordTextBox, 5);
  }

  /**
   * Is show password button displayed
   *
   * @return boolean
   */
  public boolean isShowPasswordButtonDisplayed() {
    return showPasswordButton.isDisplayed();
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
    return signInButton.isEnabled();
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
  @Locate(xpath = "//android.widget.Button[@text='OKAY']", on = Platform.MOBILE_ANDROID)
  protected Button okButton;

  /* -------- Actions -------- */

  @Override
  public void showPassword() {
    logger.info("Click on Show Password button");
    Dimension element = passwordTextBox.getDimension();
    int x = element.width / 2;
    int y = element.height / 2;
    int width = element.getWidth();
    SdkHelper.getDeviceControl().tapElementCoordinates(passwordTextBox, x + width / 2 - 5, y);
  }

  @Override
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    passwordTextBox.sendKeys(password);
  }

  @Override
  public String getPassword() {
    return passwordTextBox.getAttributeValue("text");
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

  @Override
  public <T extends BaseComponent> T signIn(Class<T> clazz) {
    logger.info("Click on Sign In button");
    MobileHelper.hideKeyboard();
    signInButton.click();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getLoader).notPresent().setTimeout(Duration.ofSeconds(120)));
    SdkHelper.create(BaseTooltipComponent.class).closeAnyTooltipIfDisplayed(5);
    return SdkHelper.create(clazz);
  }
}
