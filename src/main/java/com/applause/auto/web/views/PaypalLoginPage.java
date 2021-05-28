package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Implementation(is = PaypalLoginPage.class, on = Platform.WEB)
public class PaypalLoginPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//*[@id=\"content\"]", on = Platform.WEB)
  private Image getViewSignature;

  @Locate(xpath = "//*[@id=\"email\"]", on = Platform.WEB)
  private TextBox getEmailField;

  @Locate(xpath = "//*[@id=\"btnNext\"]", on = Platform.WEB)
  private Button getNextButton;

  @Locate(xpath = "//*[@id=\"password\"]", on = Platform.WEB)
  private TextBox getPasswordField;

  @Locate(xpath = "//*[@id=\"btnLogin\"]", on = Platform.WEB)
  private Button getLogInButton;

  @Locate(xpath = "[name='injectedUl']", on = Platform.WEB)
  private ContainerElement getIFrame;

  /* -------- Actions -------- */

  /**
   * Enter Paypal Email Address
   *
   * @param email
   */
  public void enterEmail(String email) {
    logger.info("Entering email address");
    getEmailField.clearText();
    getEmailField.sendKeys(email);
  }

  /** Click Next Button */
  public void clickNext() {
    logger.info("Clicking Next button");
    getNextButton.click();
  }

  /**
   * Enter Paypal Password
   *
   * @param password
   */
  public void enterPassword(String password) {
    logger.info("Entering password");
    SdkHelper.getSyncHelper().wait(Until.uiElement(getPasswordField).present()).sendKeys(password);
  }

  /**
   * Click Log In
   *
   * @return PaypalReviewYourPurchasePage
   */
  public PaypalReviewYourPurchasePage clickLogIn() {
    logger.info("Clicking Log In");
    getLogInButton.click();

    // SAFARI flow
    if (SdkHelper.getEnvironmentHelper().isSafari()) {
      // Move to iFrame
      SdkHelper.getSyncHelper().sleep(45000);
      SdkHelper.getDriver().switchTo().defaultContent();
      try {
        SdkHelper.getSyncHelper().wait(Until.uiElement(getIFrame).present());
        SdkHelper.getDriver()
            .switchTo()
            .frame((WebElement) SdkHelper.getDriver().findElement(By.cssSelector("[name='injectedUl']")));
        logger.info("Switched to Iframe successfully");
      } catch (Throwable throwable) {
        logger.info("Switching to iFrame failed");
        logger.warn(throwable.getMessage());
      }
      getPasswordField.clearText();
      getPasswordField.sendKeys(Constants.TestData.PAYPAL_PASSWORD);
      if (SdkHelper.getEnvironmentHelper().isSafari()) {
        WebHelper.jsClick(getLogInButton.getWebElement());
      } else {
        getLogInButton.click();
      }
      SdkHelper.getDriver().switchTo().defaultContent();
    }

    return SdkHelper.create(PaypalReviewYourPurchasePage.class);
  }
}
