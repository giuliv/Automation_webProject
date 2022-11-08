package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;

@Implementation(is = AndroidChangePasswordView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ChangePasswordView.class, on = Platform.MOBILE_IOS)
public class ChangePasswordView extends BaseComponent {

  /* -------- Elements -------- */
  @Locate(xpath = "//XCUIElementTypeButton[@name=\"button back\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

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

  @Locate(xpath = "//XCUIElementTypeOther/*[@*='Current Password']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/oldPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getCurrentPasswordTextBox;

  @Locate(xpath = "//XCUIElementTypeOther/*[@*='New Password']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/oldPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getOldPasswordUnhiddenTextBox;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"hide password\"`][1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/oldPasswordToggle",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getCurrentPasswordShowHideToggle;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"hide password\"`][2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/newPasswordToggle",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getNewPasswordShowHideToggle;

  @Locate(
      xpath = "//XCUIElementTypeOther[2]/XCUIElementTypeSecureTextField",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/newPassword", on = Platform.MOBILE_ANDROID)
  protected TextBox getNewPasswordTextBox;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Change Password\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/updateButton", on = Platform.MOBILE_ANDROID)
  protected Button getChangePasswordButton;

  /* -------- Actions -------- */

  @Step("Sets current password.")
  public void setCurrentPassword(String password) {
    logger.info("Set current password to: " + password);
    getCurrentPasswordTextBox.click();
    getCurrentPasswordTextBox.clearText();
    getCurrentPasswordTextBox.sendKeys(password);
  }

  @Step("Click Back button")
  public <T extends BaseComponent> T goBack(Class<T> clazz) {
    logger.info("Tap back button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getBackButton).present().setTimeout(Duration.ofSeconds(15)));
    getBackButton.click();
    SdkHelper.getSyncHelper().sleep(4000);
    return SdkHelper.create(clazz);
  }

  @Step("Gets message.")
  public String getMessage() {
    return getMessageText.getText();
  }

  @Step("Verify Error Message")
  public Boolean verifyMessage() {
    return getMessage().equals("Sorry, something's wrong");
  }

  @Step("Show password.")
  public void showPassword() {
    logger.info("Click on Show Password button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getShowPasswordButton).present());
    getShowPasswordButton.click();
  }

  @Step("Dismiss message t.")
  public <T extends BaseComponent> T dismissMessage(Class<T> clazz) {
    logger.info("Alert should be accepted");
    SdkHelper.getDriver().switchTo().alert().accept();
    return SdkHelper.create(clazz);
  }

  @Step("Set new password")
  public void setNewPassword(String password) {
    logger.info("Set new password to: " + password);
    getNewPasswordTextBox.click();
    getNewPasswordTextBox.clearText();
    getNewPasswordTextBox.sendKeys(password);
  }

  @Step("Change password t.")
  public <T extends BaseComponent> T changePassword(Class<T> clazz) {
    logger.info("Tap on change password button");
    getChangePasswordButton.click();
    // wait change password is applied
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(clazz);
  }

  @Step("Gets current password unhide.")
  public String getCurrentPasswordUnhide() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getCurrentPasswordTextBox).present());
    return getCurrentPasswordTextBox.getCurrentText();
  }

  @Step("Verify Back arrow button is displayed")
  public boolean isBackArrowButtonDisplayed() {
    return MobileHelper.isDisplayed(getBackButton);
  }

  @Step("Verify CHANGE PASSWORD header is displayed")
  public boolean isChangePasswordHeaderDisplayed() {
    return MobileHelper.isDisplayed(getSignature);
  }

  @Step("Verify Change Password button is displayed")
  public boolean isChangePasswordButtonDisplayed() {
    return MobileHelper.isDisplayed(getChangePasswordButton);
  }

  @Step("Verify Current password field with show/hide password icon")
  public boolean isCurrentPasswordFieldDisplayed() {
    if (!MobileHelper.isDisplayed(getCurrentPasswordTextBox)) {
      logger.error("Current password field isn't displayed");
      return false;
    }

    if (!MobileHelper.isDisplayed(getCurrentPasswordShowHideToggle)) {
      logger.error("Current password field show/hide icon isn't displayed");
      return false;
    }

    return true;
  }

  @Step("Verify New password field with show/hide password icon")
  public boolean isNewPasswordFieldDisplayed() {
    if (!MobileHelper.isDisplayed(getNewPasswordTextBox)) {
      logger.error("New password field isn't displayed");
      return false;
    }

    if (!MobileHelper.isDisplayed(getNewPasswordShowHideToggle)) {
      logger.error("New password field show/hide icon isn't displayed");
      return false;
    }

    return true;
  }
}

class AndroidChangePasswordView extends ChangePasswordView {

  /* -------- Actions -------- */

  @Override
  public void setNewPassword(String password) {
    logger.info("Set new password to: " + password);
    getNewPasswordTextBox.clearText();
    getNewPasswordTextBox.sendKeys(password);
    SdkHelper.getDeviceControl().hideKeyboard();
  }

  @Override
  // Todo: Commented as part of update on pom to 4.1.2 [REVIEW IF WORKAROUND WORKED]
  public void showPassword() {
    //    logger.info("Click on Show Password button");
    //    MobileElement element = getCurrentPasswordTextBox.getMobileElement();
    //    int x = element.getCenter().getX();
    //    int y = element.getCenter().getY();
    //    int width = element.getSize().getWidth();
    //    AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    //    (new TouchAction(driver)).tap(PointOption.point(x + width / 2 - 5, y)).perform();

    // New Code added [Test if works?], I do not have a clue, why the + width / 2 - 5
    Dimension element = getCurrentPasswordTextBox.getDimension();
    int x = element.width / 2;
    int y = element.height / 2;
    int width = element.getWidth();
    SdkHelper.getDeviceControl()
        .tapElementCoordinates(getCurrentPasswordTextBox, x + width / 2 - 5, y);
  }

  @Override
  public void setCurrentPassword(String password) {
    logger.info("Set current password to: " + password);
    // workaround for password cleanup for android
    while (!getCurrentPasswordTextBox.getAttributeValue("text").equals("Current Password")
        && !StringUtils.isEmpty(getCurrentPasswordTextBox.getAttributeValue("text"))) {
      getCurrentPasswordTextBox.clearText();
    }
    getCurrentPasswordTextBox.click();
    getCurrentPasswordTextBox.sendKeys(password);
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
    return SdkHelper.create(clazz);
  }

  @Override
  public Boolean verifyMessage() {
    return getMessage().equals("Operation failed, check your current password and try again");
  }
}
