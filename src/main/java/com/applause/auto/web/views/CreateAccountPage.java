package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.Strategy;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.my_account.MyAccountPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = CreateAccountPage.class, on = Platform.WEB)
public class CreateAccountPage extends Base {

  @Locate(xpath = "//h1[contains(text(),'Create an account')]", on = Platform.WEB)
  private ContainerElement signature;

  @Locate(css = ".account__form-btn", on = Platform.WEB)
  private Button createButton;

  @Locate(id = "FirstName", on = Platform.WEB)
  private TextBox firstNameTextBox;

  @Locate(id = "LastName", on = Platform.WEB)
  private TextBox lastNameTextBox;

  @Locate(id = "Email", on = Platform.WEB)
  private TextBox emailTextBox;

  @Locate(id = "Password", on = Platform.WEB)
  private TextBox passwordTextBox;

  @Locate(id = "PasswordCheck", on = Platform.WEB)
  private TextBox confirmPasswordTextBox;

  @Locate(css = "%s + span > span[id*=error]", on = Platform.WEB)
  private Text fieldErrorMessage;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(signature).present());
  }

  @Step("Create account")
  public MyAccountPage createAccount(
      String firstName, String lastName, String email, String password, String confirmPassword) {
    return createAccount(
        firstName, lastName, email, password, confirmPassword, MyAccountPage.class);
  }

  @Step("Create account")
  public <T extends BaseComponent> T createAccount(
      String firstName,
      String lastName,
      String email,
      String password,
      String confirmPassword,
      Class<T> tClass) {

    logger.info("Enter firstName: " + firstName);
    firstNameTextBox.sendKeys(firstName);

    logger.info("Enter lastName: " + lastName);
    lastNameTextBox.sendKeys(lastName);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Enter valid email address: " + email);
    emailTextBox.sendKeys(email);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    logger.info("Enter password: " + password);
    passwordTextBox.sendKeys(password);

    logger.info("Enter confirm password: " + confirmPassword);
    confirmPasswordTextBox.sendKeys(confirmPassword);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    if (SdkHelper.getEnvironmentHelper().isiOS()) {
      logger.info("Scrolling[iOS]...Looking for createButton");
      WebHelper.scrollToElement(createButton);
      SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    }

    return clickCreateAccountButton(tClass);
  }

  @Step("Click Create account button")
  public <T extends BaseComponent> T clickCreateAccountButton(Class<T> expectedClass) {
    if (WebHelper.getDriverConfig().toLowerCase().contains("landscape")
        && SdkHelper.getEnvironmentHelper().isAndroid()) {
      logger.info("Android Landscape hide keyboard");
      SdkHelper.getDeviceControl().pressAndroidKeyBack();
      SdkHelper.getSyncHelper().sleep(500); // Wait for keyboard to be hidden
    }

    logger.info("Clicking over Create account button");
    createButton.click();
    SdkHelper.getSyncHelper().sleep(5000); // Wait for action
    return SdkHelper.create(expectedClass);
  }

  @Step("Verify error message is displayed under First Name field")
  public boolean isFirstNameFieldErrorMessageDisplayed() {
    return getFieldErrorMessageElement(firstNameTextBox).isDisplayed();
  }

  @Step("Verify error message is displayed under Last Name field")
  public boolean isLastNameFieldErrorMessageDisplayed() {
    return getFieldErrorMessageElement(lastNameTextBox).isDisplayed();
  }

  @Step("Verify error message is displayed under Email field")
  public boolean isEmailFieldErrorMessageDisplayed() {
    return getFieldErrorMessageElement(emailTextBox).isDisplayed();
  }

  @Step("Verify error message is displayed under Password field")
  public boolean isPasswordFieldErrorMessageDisplayed() {
    return getFieldErrorMessageElement(passwordTextBox).isDisplayed();
  }

  @Step("Verify error message is displayed under Password confirmation field")
  public boolean isPasswordConfirmationFieldErrorMessageDisplayed() {
    return getFieldErrorMessageElement(confirmPasswordTextBox).isDisplayed();
  }

  private Text getFieldErrorMessageElement(TextBox field) {
    fieldErrorMessage
        .format("#" + field.getLocator().getLocatorStringMap().get(Strategy.ID))
        .initialize();
    return fieldErrorMessage;
  }
}
