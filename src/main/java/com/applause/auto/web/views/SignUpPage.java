package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants.TestData;
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
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = SignUpPage.class, on = Platform.WEB)
public class SignUpPage extends BaseComponent {

  @Locate(css = "#page_email_signup", on = Platform.WEB)
  private ContainerElement signupSection;

  @Locate(xpath = "//span[@id='fieldcaptureheader2' and text()='10% OFF']", on = Platform.WEB)
  private ContainerElement tenPercentSection;

  @Locate(xpath = "//span[@id='fieldcaptureheader2' and text()='15% OFF']", on = Platform.WEB)
  private ContainerElement fifteenPercentSection;

  @Locate(css = "iframe.email-iframe", on = Platform.WEB)
  private ContainerElement iframeElement;

  @Locate(css = "#contentframe input[type=email]", on = Platform.WEB)
  private TextBox emailField;

  @Locate(css = "#contentframe button[type=submit]", on = Platform.WEB)
  private Button continueButton;

  @Locate(css = "#contentframe input[type=tel]", on = Platform.WEB)
  private TextBox mobileNumberField;

  @Locate(css = "#contentframe button[type=submit]", on = Platform.WEB)
  private Button submitButton;

  @Locate(css = "span#successheader1", on = Platform.WEB)
  private Text successPageHeader;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signupSection).visible().setTimeout(Duration.ofSeconds(40)));
  }

  @Step("Verify '10% OFF + FREE SHIPPING' section is displayed")
  public boolean isTenPercentSectionDisplayed() {
    logger.info("Checking '10% OFF + FREE SHIPPING' section is displayed");
    WebHelper.switchToIFrame(iframeElement);
    return WebHelper.isDisplayed(tenPercentSection, 10);
  }

  @Step("Verify '15% OFF + FREE SHIPPING' section is displayed")
  public boolean isFifteenPercentSectionDisplayed() {
    logger.info("Checking '15% OFF + FREE SHIPPING' section is displayed");
    WebHelper.switchToIFrame(iframeElement);
    return WebHelper.isDisplayed(fifteenPercentSection, 10);
  }

  @Step("Complete first step with email")
  public SignUpPage enterEmailAndClickContinue(String email) {
    logger.info("Entering email '{}' into the field", email);
    WebHelper.waitForElementToAppear(emailField);
    emailField.sendKeys(email);

    logger.info("Clicking on the Continue button");
    continueButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(SignUpPage.class);
  }

  @Step("Complete second step with mobile number")
  public SignUpPage enterMobileNumberAndSubmit(String mobileNumber) {
    logger.info("Entering phone number '{}' into the field", mobileNumber);
    WebHelper.waitForElementToAppear(mobileNumberField);
    mobileNumberField.sendKeys(mobileNumber);

    logger.info("Clicking on the 'GET 15% OFF NOW' submit button");
    submitButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(SignUpPage.class);
  }

  @Step("Verify success message is displayed")
  public boolean isSuccessPageHeaderDisplayed() {
    logger.info("Checking 'Check your Texts' message is displayed");
    WebHelper.switchToIFrame(iframeElement);

    if (!WebHelper.isDisplayed(successPageHeader, 20)) {
      logger.error("'Check your Texts' message is not displayed");
      return false;
    }

    String messageText = successPageHeader.getText().trim();
    if (!messageText.equals(TestData.SIGN_UP_SUCCESS_MESSAGE)) {
      logger.error(
          String.format(
              "Wrong message is displayed. Expected [%s]. Actual [%s]",
              TestData.SIGN_UP_SUCCESS_MESSAGE, messageText));
      return false;
    }

    return true;
  }
}
