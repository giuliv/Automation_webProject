package com.applause.auto.web.components.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.my_account.ReferralsPage;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = ShareLinkComponent.class, on = Platform.WEB)
@Implementation(is = ShareLinkComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class ShareLinkComponent extends BaseComponent {

  @Locate(xpath = "//div[@id='talkable-offer']/iframe", on = Platform.WEB)
  private Button mainIframe;

  @Locate(xpath = "//a[contains(@class, 'js-copy')]", on = Platform.WEB)
  private Button copyLinkButton;

  @Locate(xpath = "//a[@class='popup-close']", on = Platform.WEB)
  private Button closeButton;

  @Locate(xpath = "//div[contains(@class, 'share-link')]", on = Platform.WEB)
  private Text personalLink;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainIframe).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Share Via Email Component URL: " + getDriver().getCurrentUrl());
  }

  @Step("Get Personal Link")
  public String getPersonalLink() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    String link = WebHelper.cleanString(personalLink.getText());
    logger.info("Personal Link URL - [{}]", link);
    SdkHelper.getDriver().switchTo().defaultContent();
    return link;
  }

  @Step("Click on 'Copy Link' Button")
  public ShareLinkComponent clickCopyLinkButton() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    logger.info("Clicking on 'Copy Link' button");
    copyLinkButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return this;
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
