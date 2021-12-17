package com.applause.auto.new_web.components.my_account;

import com.applause.auto.common.data.dto.ShareViaEmailDto;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.my_account.ReferralsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = ShareViaEmailComponent.class, on = Platform.WEB)
@Implementation(is = ShareViaEmailComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class ShareViaEmailComponent extends BaseComponent {

  @Locate(xpath = "//div[@id='talkable-offer']/iframe", on = Platform.WEB)
  private Button mainIframe;

  @Locate(xpath = "//input[@id='email_recipient_list']", on = Platform.WEB)
  private TextBox emailField;

  @Locate(xpath = "//textarea[@id='share_message']", on = Platform.WEB)
  private TextBox noteField;

  @Locate(xpath = "//input[@value='Send Email']", on = Platform.WEB)
  private Button sendEmailButton;

  @Locate(
      xpath = "//div[contains(@class, 'is-error') and @style='display: block;']",
      on = Platform.WEB)
  private Text errorMessage;

  @Locate(xpath = "//a[contains(@class, 'close-email-share-popup')]", on = Platform.WEB)
  private Button closeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainIframe).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Share Via Email Component URL: " + getDriver().getCurrentUrl());
  }

  @Step("Enter Share Data")
  public ShareViaEmailComponent enterShareData(ShareViaEmailDto data) {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    logger.info("Typing [{}] into the email Field", data.getEmail());
    emailField.clearText();
    emailField.sendKeys(data.getEmail());

    logger.info("Typing [{}] into the note Field", data.getNote());
    noteField.clearText();
    noteField.sendKeys(data.getNote());

    logger.info("Clicking on 'Send Email'");
    sendEmailButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return this;
  }

  @Step("Check if Error message is displayed")
  public boolean isErrorDisplayed() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(errorMessage);
    SdkHelper.getDriver().switchTo().defaultContent();
    return isDisplayed;
  }

  @Step("Close modal.")
  public ReferralsPage close() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    logger.info("Clicking on 'Close' button");
    closeButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(ReferralsPage.class);
  }
}
