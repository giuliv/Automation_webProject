package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@Implementation(is = AndroidSignInView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SignInView.class, on = Platform.MOBILE_IOS)
public class SignInView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/emailAddress", on = Platform.MOBILE_ANDROID)
  protected TextBox getUsernameTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeAlert//XCUIElementTypeStaticText[@name=\"The email and password you entered don't match. Please try again.\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected TextBox getMessageTextBox;

  @Locate(xpath = "//XCUIElementTypeTextField", on = Platform.MOBILE_IOS)
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
    SyncHelper.sleep(2000);
  }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    getPasswordTextBox.clearText();
    SyncHelper.sleep(500);
    getPasswordTextBox.sendKeys(password);
    SyncHelper.sleep(500);
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
    DriverManager.getDriver().switchTo().alert().accept();
    return this;
  }

  /**
   * Gets un encrypted password.
   *
   * @return the un encrypted password
   */
  public String getUnEncryptedPassword() {
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
    getSignInButton.click();
    SyncHelper.sleep(1000);
    return ComponentFactory.create(clazz);
  }

  /** Show password. */
  public void showPassword() {
    logger.info("Click on Show Password button");
    getShowPasswordButton.click();
    SyncHelper.sleep(500);
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
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();
  }

  @Override
  public void setPassword(String password) {
    logger.info("Set password: " + password);
    // TODO: figure out why this code block is causing an exception on Android
    // while (getPasswordTextBox.getCurrentText().length() != 0) {
    //   getPasswordTextBox.clearText();
    // }

    getPasswordTextBox.sendKeys(password);
  }

  @Override
  public String getPassword() {
    return getPasswordTextBox.getAttributeValue("text");
  }

  @Override
  public String getUnEncryptedPassword() {
    return getUnEncryptedPasswordTextBox.getAttributeValue("text");
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

  public <T extends BaseComponent> T signIn(Class<T> clazz) {
    logger.info("Click on Sign In button");
    DeviceControl.hideKeyboard();
    getSignInButton.click();
    return ComponentFactory.create(clazz);
  }
}
