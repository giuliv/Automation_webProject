package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
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

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(signature).present());
  }

  @Step("Create account")
  public MyAccountPage createAccount(
      String firstName, String lastName, String email, String password, String confirmPassword) {

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

    logger.info("Clicking over Create account button");
    createButton.click();
    SdkHelper.getSyncHelper().sleep(5000); // Wait for action

    return SdkHelper.create(MyAccountPage.class);
  }
}
