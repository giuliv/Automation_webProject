package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = PayPalPage.class, on = Platform.WEB)
public class PayPalPage extends Base {
  @Locate(css = "#paypal-container", on = Platform.WEB)
  protected Button paypalContainer;

  @Locate(css = "a#braintree-paypal-button", on = Platform.WEB)
  protected Button payWithPayPalButton;

  @Locate(css = "input#email", on = Platform.WEB)
  protected TextBox emailTextBox;

  @Locate(css = "#password", on = Platform.WEB)
  protected TextBox passTextBox;

  @Locate(css = "#btnNext", on = Platform.WEB)
  protected Button nextButton;

  @Locate(css = "#btnLogin", on = Platform.WEB)
  protected Button loginButton;

  @Locate(css = "#consentButton", on = Platform.WEB)
  protected Button consentButton;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(paypalContainer).present());
  }

  private void clickPayWithPayPal() {
    logger.info("Clicking pay with paypal button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(payWithPayPalButton).clickable()).click();
    WebHelper.waitForNewWindowToAppear(30);
  }

  private String switchToPopUp() {
    logger.info("Switching to paypal's popup");
    List<String> wHandles =
        SdkHelper.getDriver().getWindowHandles().stream().collect(Collectors.toList());
    String cHandle = SdkHelper.getDriver().getWindowHandle();
    WebHelper.switchToTab(wHandles.get(wHandles.size() - 1));
    return cHandle;
  }

  public void switchToMainWindow(String parent) {
    logger.info("Switching back to main window");
    WebHelper.switchToTab(parent);
  }

  private void fillEmailInput(String email) {
    logger.info("Entering " + email + " into the input form");
    SdkHelper.getSyncHelper().wait(Until.uiElement(emailTextBox).clickable());
    emailTextBox.sendKeys(email);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  private void fillPasswordInput(String password) {
    logger.info("Entering " + password + " into the paypal password field");
    SdkHelper.getSyncHelper().wait(Until.uiElement(passTextBox).clickable());
    passTextBox.sendKeys(password);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  private void clickNext() {
    logger.info("clicking next");
    SdkHelper.getSyncHelper().wait(Until.uiElement(nextButton).clickable()).click();
  }

  private void clickLogin() {
    logger.info("clicking login");
    SdkHelper.getSyncHelper().wait(Until.uiElement(loginButton).clickable()).click();
  }

  private void clickConsent() {
    logger.info("clicking agree and continue");
    SdkHelper.getSyncHelper().wait(Until.uiElement(consentButton).clickable());
    consentButton.scrollToElement();
    consentButton.click();
  }

  public AcceptancePage fillPayPal(String username, String password) {
    logger.info("filling in paypal info");
    clickPayWithPayPal();
    String parent = switchToPopUp();
    fillEmailInput(username);
    clickNext();
    fillPasswordInput(password);
    clickLogin();
    clickConsent();
    switchToMainWindow(parent);

    return SdkHelper.create(AcceptancePage.class);
  }
}
