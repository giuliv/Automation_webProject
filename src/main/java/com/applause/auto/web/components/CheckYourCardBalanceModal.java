package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = CheckYourCardBalanceModal.class, on = Platform.WEB)
public class CheckYourCardBalanceModal extends BaseComponent {

  @Locate(css = "div#modalCheckBalance", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "input[class*=balance-number]", on = Platform.WEB)
  private TextBox cardNumberField;

  @Locate(css = "input[class*=balance-pin]", on = Platform.WEB)
  private TextBox pinField;

  @Locate(css = "button[class*=balance-submit]", on = Platform.WEB)
  private Button checkBalanceButton;

  @Locate(css = ".check-balance__display > .check-balance__value", on = Platform.WEB)
  private Text currentBalance;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  @Step("Enter Card number")
  public void enterCardNumber(String cardNumber) {
    cardNumberField.clearText();
    cardNumberField.sendKeys(cardNumber);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  @Step("Enter Card pin")
  public void enterPinNumber(String pin) {
    pinField.clearText();
    pinField.sendKeys(pin);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  @Step("Click Check balance button")
  public CheckYourCardBalanceModal clickCheckBalance() {
    logger.info("Clicking Check balance button");
    checkBalanceButton.click();
    return this;
  }

  @Step("Verify Current balance is displayed")
  public boolean isBalanceDisplayed() {
    return currentBalance.isDisplayed() && !currentBalance.getText().isEmpty();
  }
}
