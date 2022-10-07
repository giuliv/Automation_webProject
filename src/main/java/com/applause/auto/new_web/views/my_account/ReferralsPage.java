package com.applause.auto.new_web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.my_account.ShareLinkComponent;
import com.applause.auto.new_web.components.my_account.ShareViaEmailComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = ReferralsPage.class, on = Platform.WEB)
@Implementation(is = ReferralsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ReferralsPage extends BaseComponent {

  @Locate(xpath = "//h2[text()='Referrals']", on = Platform.WEB)
  private Text baseElement;

  @Locate(xpath = "//div[@class='ac-referrals']/h2", on = Platform.WEB)
  private Text title;

  @Locate(
      xpath = "//div[@class='socials']/ul/li/a[contains(@class, 'facebook')]",
      on = Platform.WEB)
  private Button shareButton;

  @Locate(xpath = "//div[@class='socials']/ul/li/a[@data-toggle='email-share']", on = Platform.WEB)
  private Button emailButton;

  @Locate(xpath = "//div[@class='socials']/ul/li/a[@data-toggle='link-share']", on = Platform.WEB)
  private Button copyButton;

  @Locate(xpath = "//div[@class='stats']//span[@class='title']", on = Platform.WEB)
  private LazyList<Text> statsList;

  @Locate(xpath = "//div[@id='talkable-offer']/iframe", on = Platform.WEB)
  private Button mainIframe;

  @Locate(css = ".track-table", on = Platform.WEB)
  private ContainerElement trackTable;

  @Locate(
      css = "[class=\"ac-tout__cta btn-link router-link-exact-active router-link-active\"]",
      on = Platform.WEB)
  private Button starSharingButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(baseElement).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Referrals page URL: " + getDriver().getCurrentUrl());
  }

  @Step("Check if 'Referrals' is Displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }

  @Step("Get page Title")
  public String getTitle() {
    String titleString = title.getText();
    logger.info("Page title - [{}]", titleString);
    return titleString;
  }

  @Step("Check if 'Share' button is Displayed")
  public boolean isShareButtonDisplayed() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(shareButton);
    SdkHelper.getDriver().switchTo().defaultContent();
    logger.info("'Share' Button is Displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'Email' button is Displayed")
  public boolean isEmailButtonDisplayed() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(emailButton);
    SdkHelper.getDriver().switchTo().defaultContent();
    logger.info("'Email' Button is Displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'Copy' button is Displayed")
  public boolean isCopyButtonDisplayed() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(copyButton);
    SdkHelper.getDriver().switchTo().defaultContent();
    logger.info("'Email' Button is Displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  public List<String> getListOfStats() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    List<String> list =
        statsList.stream()
            .map(
                item ->
                    WebHelper.cleanString(
                        item.getText().replaceAll("\n", " ").replaceAll("\t", " ").toLowerCase()))
            .collect(Collectors.toList());
    SdkHelper.getDriver().switchTo().defaultContent();
    return list;
  }

  public List<String> getListOfStatsValues() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    List<String> list =
        statsList.stream()
            .map(
                item ->
                    WebHelper.cleanString(
                        item.getText().replaceAll("\n", " ").replaceAll("\t", " ").toLowerCase()))
            .collect(Collectors.toList());
    list.forEach(x -> logger.info("Stats values: " + x.trim()));
    SdkHelper.getDriver().switchTo().defaultContent();
    return list;
  }

  @Step("Click on 'Share' Button")
  public void clickOnShareButton() {
    logger.info("Clicking on 'Share' button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    shareButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    WebHelper.switchToNewTab(windowHandle);
  }

  @Step("Click on 'Email' Button")
  public ShareViaEmailComponent clickOnEmailButton() {
    logger.info("Clicking on 'Email' button");
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    emailButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(ShareViaEmailComponent.class);
  }

  @Step("Click on 'Copy' Button")
  public ShareLinkComponent clickOnCopyButton() {
    logger.info("Clicking on 'Copy' button");
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    copyButton.click();
    SdkHelper.getDriver().switchTo().defaultContent();
    return SdkHelper.create(ShareLinkComponent.class);
  }

  @Step("Check if 'Track' table is Displayed")
  public boolean isTrackTableDisplayed() {
    SdkHelper.getDriver().switchTo().frame(mainIframe.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(trackTable);
    SdkHelper.getDriver().switchTo().defaultContent();
    logger.info("'Track' table is Displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'Start sharing' button is Displayed")
  public boolean isStartSharingButtonDisplayed() {
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(starSharingButton.getWebElement());
    boolean isDisplayed = WebHelper.isDisplayed(starSharingButton);
    logger.info("'Start sharing' table is Displayed - [{}]", isDisplayed);
    return isDisplayed;
  }
}
