package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = UpdateSubscriptionPaymentChunk.class, on = Platform.WEB)
@Implementation(is = UpdateSubscriptionPaymentChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class UpdateSubscriptionPaymentChunk extends BaseComponent {

  @Locate(css = ".ac-subscriptions__iframe-wrap", on = Platform.WEB)
  private ContainerElement getBaseComponent;

  @Locate(css = ".ac-subscriptions__iframe-wrap > iframe", on = Platform.WEB)
  private ContainerElement getBaseFrameContainer;

  @Locate(css = "#cardNumber-container > iframe", on = Platform.WEB)
  private TextBox getCardNumberFrame;

  @Locate(id = "firstName", on = Platform.WEB)
  private TextBox getFirstNameField;

  @Locate(id = "lastName", on = Platform.WEB)
  private TextBox getLastNameField;

  @Locate(id = "address1", on = Platform.WEB)
  private TextBox getAddressField;

  @Locate(id = "city", on = Platform.WEB)
  private TextBox getCityField;

  @Locate(id = "state", on = Platform.WEB)
  private TextBox getStateField;

  @Locate(id = "postalCode", on = Platform.WEB)
  private TextBox getPostalCodeField;

  @Locate(id = "expirationMonth", on = Platform.WEB)
  private TextBox getExpirationMonthField;

  @Locate(id = "expirationYear", on = Platform.WEB)
  private TextBox getExpirationYearField;

  @Locate(css = "input", on = Platform.WEB)
  private TextBox getCardNumberFieldField;

  @Locate(id = "pay-button", on = Platform.WEB)
  private Button continueButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getBaseComponent).present());
    SdkHelper.getDriver().switchTo().frame(getBaseFrameContainer.getWebElement());
    //    SdkHelper.getSyncHelper().wait(Until.uiElement(getFirstNameField).present());
  }

  /* -------- Actions -------- */

  @Step("Update payment data")
  public void updatePaymentData() {
    logger.info("Entering payment information");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getFirstNameField).visible().setTimeout(Duration.ofSeconds(30)));
    getFirstNameField.sendKeys(Constants.TestData.FIRST_NAME);
    getLastNameField.sendKeys(Constants.TestData.LAST_NAME);
    getAddressField.sendKeys(Constants.TestData.ADDRESS);
    getCityField.sendKeys(Constants.TestData.CITY);
    getStateField.sendKeys(Constants.TestData.STATE.substring(0, 2));
    getPostalCodeField.sendKeys(Constants.TestData.ZIP_CODE);
    getExpirationMonthField.sendKeys(WebTestData.CREDIT_CARD_EXPIRATION_MONTH);
    getExpirationYearField.sendKeys(WebTestData.CREDIT_CARD_EXPIRATION_YEAR);

    logger.info("Entering card number");
    SdkHelper.getDriver().switchTo().frame(getCardNumberFrame.getWebElement());
    getCardNumberFieldField.sendKeys(WebTestData.CREDIT_CARD);
    SdkHelper.getDriver().switchTo().defaultContent();

    logger.info("Clicking 'Continue' button");
    SdkHelper.getDriver().switchTo().frame(getBaseFrameContainer.getWebElement());
    continueButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
  }
}
