package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.springframework.util.StringUtils;

@Implementation(is = AndroidChangePasswordView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ChangePasswordView.class, on = Platform.MOBILE_IOS)
public class ChangePasswordView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeNavigationBar[@visible='true' and @name=\"CHANGE PASSWORD\"]|(//XCUIElementTypeAlert//XCUIElementTypeStaticText[@visible='true'])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='CHANGE PASSWORD']|//*[@text='OKAY']",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(xpath = "(//XCUIElementTypeButton[@name=\"hide password\"])[1]", on = Platform.MOBILE_IOS)
  @Locate(id = "NA", on = Platform.MOBILE_ANDROID)
  protected Button getShowPasswordButton;

  @Locate(
      xpath =
          "//XCUIElementTypeAlert//XCUIElementTypeStaticText[@name=\"Sorry, something's wrong\" or @name=\"Your new password has been set\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[@resource-id='android:id/message']|//*[@resource-id='com.wearehathway.peets.development:id/md_content']",
      on = Platform.MOBILE_ANDROID)
  protected Text getMessageText;

  @Locate(xpath = "//XCUIElementTypeCell[@name=\"Location\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[@text='OKAY']", on = Platform.MOBILE_ANDROID)
  protected Button getMessageOkButton;

  @Locate(
      xpath =
          "//XCUIElementTypeOther[1]/XCUIElementTypeSecureTextField|//XCUIElementTypeButton[@name=\"reveal password\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/oldPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getOldPasswordTextBox;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"reveal password\"]/preceding-sibling::XCUIElementTypeTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/oldPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getOldPasswordUnhiddenTextBox;

  @Locate(
      xpath = "//XCUIElementTypeOther[2]/XCUIElementTypeSecureTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/newPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getNewPasswordTextBox;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Change Password\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/updateButton", on = Platform.MOBILE_ANDROID)
  protected Button getChangePasswordButton;

  /* -------- Actions -------- */

  /**
   * Sets current password.
   *
   * @param password the password
   */
  public void setCurrentPassword(String password) {
    logger.info("Set current password to: " + password);
    getOldPasswordTextBox.clearText();
    getOldPasswordTextBox.sendKeys(password);
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return getMessageText.getText();
  }

  /**
   * Verify Error Message
   *
   * @return Boolean
   */
  public Boolean verifyMessage() {
    return getMessage().equals("Sorry, something's wrong");
  }

  /** Show password. */
  public void showPassword() {
    logger.info("Click on Show Password button");
    getShowPasswordButton.click();
  }

  /**
   * Dismiss message t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T dismissMessage(Class<T> clazz) {
    logger.info("Alert should be accepted");
    DriverManager.getDriver().switchTo().alert().accept();
    return ComponentFactory.create(clazz);
  }

  /**
   * Set new pas ósword.
   *
   * @param password the password
   */
  public void setNewPassword(String password) {
    logger.info("Set new password to: " + password);
    getNewPasswordTextBox.clearText();
    getNewPasswordTextBox.sendKeys(password);
  }

  /**
   * Change password t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T changePassword(Class<T> clazz) {
    logger.info("Tap on change password button");
    getChangePasswordButton.click();
    // wait change password is applied
    SyncHelper.sleep(5000);
    return ComponentFactory.create(clazz);
  }

  /**
   * Gets current password unhide.
   *
   * @return the current password unhide
   */
  public String getCurrentPasswordUnhide() {
    return getOldPasswordUnhiddenTextBox.getCurrentText();
  }
}

class AndroidChangePasswordView extends ChangePasswordView {

  /* -------- Actions -------- */

  @Override
  public void showPassword() {
    logger.info("Click on Show Password button");
    MobileElement element = getOldPasswordTextBox.getMobileElement();
    int x = element.getCenter().getX();
    int y = element.getCenter().getY();
    int width = element.getSize().getWidth();
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();
  }

  @Override
  public void setCurrentPassword(String password) {
    logger.info("Set current password to: " + password);
    // workaround for password cleanup for android
    while (!getOldPasswordTextBox.getAttributeValue("text").equals("Current Password")
        && !StringUtils.isEmpty(getOldPasswordTextBox.getAttributeValue("text"))) {
      getOldPasswordTextBox.clearText();
    }
    getOldPasswordTextBox.click();
    getOldPasswordTextBox.sendKeys(password);
  }

  @Override
  public String getCurrentPasswordUnhide() {
    logger.info("My password: " + getOldPasswordUnhiddenTextBox.getAttributeValue("text"));
    return getOldPasswordUnhiddenTextBox.getAttributeValue("text").replace("Current Password ", "");
  }

  @Override
  public <T extends BaseComponent> T dismissMessage(Class<T> clazz) {
    logger.info("Tap on OKAY to dismiss message");
    getMessageOkButton.click();
    return ComponentFactory.create(clazz);
  }

  public Boolean verifyMessage() {
    return getMessage().equals("Operation failed, check your current password and try again");
  }
}
