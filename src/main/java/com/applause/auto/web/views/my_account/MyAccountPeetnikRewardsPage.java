package com.applause.auto.web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.components.ReloadComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Base;
import com.applause.auto.web.views.ContactUsPage;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = MyAccountPeetnikRewardsPage.class, on = Platform.WEB)
public class MyAccountPeetnikRewardsPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = ".ac-rewards", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = ".ac-dashboard__section-heading", on = Platform.WEB)
  private Button titleText;

  @Locate(css = ".ac-dashboard__section-header", on = Platform.WEB)
  private Button subtitleText;

  @Locate(css = ".rewards-settings ul > li:nth-child(1)", on = Platform.WEB)
  private Button userName;

  @Locate(css = ".rewards-settings ul > li:nth-child(2)", on = Platform.WEB)
  private Button userEmail;

  @Locate(css = ".rewards-settings__link", on = Platform.WEB)
  private Link customerExperienceTeamLink;

  @Locate(css = ".ac-cards", on = Platform.WEB)
  private ContainerElement peetsCardsSectionContainer;

  @Locate(css = ".dashboard-rewards__image-wrapper", on = Platform.WEB)
  private ContainerElement peetnikRewardsBanner;

  @Locate(css = ".ac-cards__price", on = Platform.WEB)
  private Text giftCardBalance;

  @Locate(css = ".ac-cards__reload", on = Platform.WEB)
  private Text giftCardAutoReload;

  @Locate(css = ".ac-cards__actions button", on = Platform.WEB)
  protected Button giftCardReloadCardButton;

  @Locate(css = ".ac-cards a", on = Platform.WEB)
  protected Button giftCardManageButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
  }

  /* -------- Actions -------- */

  @Step("Verify title is displayed")
  public boolean isTitleDisplayed() {
    return titleText.isDisplayed() && !titleText.getText().isEmpty();
  }

  @Step("Verify subtitle is displayed")
  public boolean isSubTitleDisplayed() {
    return subtitleText.isDisplayed() && !subtitleText.getText().isEmpty();
  }

  @Step("Get user name")
  public String getUserName() {
    return userName.getText().trim();
  }

  @Step("Get email")
  public String getEmail() {
    return userEmail.getText().trim();
  }

  @Step("Click 'Customer experience team' link")
  public ContactUsPage clickCustomerExperienceTeamLink() {
    logger.info("Clicking 'Customer experience team' link");
    customerExperienceTeamLink.click();
    return SdkHelper.create(ContactUsPage.class);
  }

  @Step("Verify Peetnik reward banner is displayed")
  public boolean isPeetnikRewardsBannerDisplayed() {
    return WebHelper.isDisplayed(peetnikRewardsBanner);
  }

  @Step("Verify Peets card section is displayed")
  public boolean isPeetsCardsSectionDisplayed() {
    return WebHelper.isDisplayed(peetsCardsSectionContainer);
  }

  @Step("Verify Peets Gift card balance is displayed")
  public boolean isPeetsCardsBalanceDisplayed() {
    return WebHelper.isDisplayed(giftCardBalance);
  }

  @Step("Verify Auto Reload ON/OFF is displayed")
  public boolean isPeetsCardsAutoReloadDisplayed() {
    return WebHelper.isDisplayed(giftCardAutoReload);
  }

  @Step("Verify Reload card button is displayed")
  public boolean isPeetsCardsReloadButtonDisplayed() {
    return WebHelper.isDisplayed(giftCardReloadCardButton);
  }

  @Step("Click Peets Cards Reload button")
  public ReloadComponent clickPeetsCardsReloadButton() {
    WebHelper.scrollToElement(giftCardReloadCardButton);
    giftCardReloadCardButton.click();
    return SdkHelper.create(ReloadComponent.class);
  }

  @Step("Click Peets Cards Manage button")
  public MyCardsPage clickPeetsCardsManageButton() {
    WebHelper.scrollToElement(giftCardManageButton);
    giftCardManageButton.click();
    return SdkHelper.create(MyCardsPage.class);
  }
}
