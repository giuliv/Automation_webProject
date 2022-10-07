package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = RegisterPeetCardComponent.class, on = Platform.WEB)
@Implementation(is = RegisterPeetCardComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class RegisterPeetCardComponent extends BaseComponent {

  @Locate(css = ".register-card__fields", on = Platform.WEB)
  private ContainerElement getBaseComponent;

  @Locate(id = "registerNumber", on = Platform.WEB)
  private TextBox cardNumberTextBox;

  @Locate(id = "registerPin", on = Platform.WEB)
  private TextBox pinNumberTextBox;

  @Locate(css = ".register-card__form button[type=\"submit\"]", on = Platform.WEB)
  private TextBox registerCardButton;

  @Locate(css = "[class='message message--success']", on = Platform.WEB)
  private Text registerMessage;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getBaseComponent).visible());
  }

  public void setCardNumber(String cardNumber) {
    logger.info("Type card number: {}", cardNumber);
    cardNumberTextBox.sendKeys(cardNumber);
  }

  public void setPinNumber(String pinNumber) {
    logger.info("Type pin number: {}", pinNumber);
    pinNumberTextBox.sendKeys(pinNumber);
  }

  public void clickRegisterCardButton() {
    logger.info("Click on register card button");
    registerCardButton.click();
  }

  public void registerCard(String cardNumber, String pinNumber) {
    setCardNumber(cardNumber);
    setPinNumber(pinNumber);
    clickRegisterCardButton();
  }

  public String getRegisterMessage() {
    logger.info("Getting register message: {}", registerMessage.getText());
    return registerMessage.getText();
  }
}
