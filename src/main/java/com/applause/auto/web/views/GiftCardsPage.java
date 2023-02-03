package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.components.CheckYourCardBalanceModal;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = GiftCardsPage.class, on = Platform.WEB)
public class GiftCardsPage extends Base {

  @Locate(id = "giftCards", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".gift-cards__shop-cards > a:nth-child(1)", on = Platform.WEB)
  private Link sendGiftInstantlyCard;

  @Locate(css = ".gift-cards__shop-cards > a:nth-child(2)", on = Platform.WEB)
  private Link sendCardByMailCard;

  @Locate(css = "a.gift-cards-banner__cta", on = Platform.WEB)
  private Link manageCardsButton;

  @Locate(id = "modalCheckBalanceBtn", on = Platform.WEB)
  private Link checkBalanceButton;

  @Locate(css = "h1.page-hero__heading", on = Platform.WEB)
  private Text headerText;

  @Locate(css = ".page-hero__description-text", on = Platform.WEB)
  private Text descriptionText;

  @Locate(css = ".page-hero__image .hide-mobile img", on = Platform.WEB)
  @Locate(css = ".page-hero__image .mobile-only img", on = Platform.WEB_MOBILE_PHONE)
  private Image bannerImage;

  @Locate(css = "a.page-hero__cta", on = Platform.WEB)
  private Link sendEGiftCardNowButton;

  @Locate(
      xpath = "//a[contains(@class,'shop-card--small') and .//h2[normalize-space()='%s']]",
      on = Platform.WEB)
  private Link bannerGetStartedLink;

  @Locate(css = "#usi_close", on = Platform.WEB)
  private Button closeBannerButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
  }

  /* -------- Actions -------- */

  @Step("Click Send a gift instantly")
  public RecipientFormPage clickSendGiftInstantlyCard() {
    logger.info("Clicking Send a gift instantly");
    sendGiftInstantlyCard.click();
    return SdkHelper.create(RecipientFormPage.class);
  }

  @Step("Click Send a card by mail")
  public ProductDetailsPage clickSendCardByEmailCard() {
    logger.info("Clicking Send a card by");
    sendCardByMailCard.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Click Manage cards button")
  public SignInPage clickManageCardsButton() {
    logger.info("Clicking Manage cards");
    manageCardsButton.click();
    return SdkHelper.create(SignInPage.class);
  }

  @Step("Click Check balance button")
  public CheckYourCardBalanceModal clickCheckBalanceButton() {
    logger.info("Clicking Check balance button");
    checkBalanceButton.click();
    return SdkHelper.create(CheckYourCardBalanceModal.class);
  }

  @Step("Verify page header is displayed")
  public boolean isPageHeaderDisplayed() {
    logger.info("Checking page header is displayed");
    return WebHelper.isDisplayed(headerText) && !headerText.getText().isEmpty();
  }

  @Step("Verify page description is displayed")
  public boolean isPageDescriptionDisplayed() {
    logger.info("Checking page description is displayed");
    return WebHelper.isDisplayed(descriptionText) && !descriptionText.getText().isEmpty();
  }

  @Step("Verify page banner image is displayed")
  public boolean isPageBannerImageDisplayed() {
    logger.info("Checking page banner image is displayed");
    return WebHelper.isDisplayed(bannerImage);
  }

  @Step("Click Send a card by mail")
  public void clickSendEGiftCardNowButton() {
    logger.info("Clicking SEND AN E-GIFT CARD NOW button");
    sendEGiftCardNowButton.click();
    SdkHelper.getSyncHelper().sleep(3000);
    // TODO After click on the SEND AN E-GIFT CARD NOW button we get "406 Not Acceptable" page but
    // with expected URL
  }

  @Step("Verify banner is displayed")
  public boolean isBannerDisplayed(String bannerHeader) {
    logger.info("Checking banner with header '{}' is displayed", bannerHeader);
    bannerGetStartedLink.format(bannerHeader).initialize();
    return WebHelper.isDisplayed(bannerGetStartedLink);
  }

  @Step("Click on the banner")
  public void clickOnBanner(String bannerHeader) {
    logger.info("Clicking on the banner: {}", bannerHeader);
    bannerGetStartedLink.format(bannerHeader).initialize();
    bannerGetStartedLink.click();
    SdkHelper.getSyncHelper().sleep(3000);
    // TODO After click on the SEND AN E-GIFT CARD NOW button we get "406 Not Acceptable" page but
    // with expected URL
  }

  public <T extends BaseComponent> T closeBannerButton(Class<T> clazz) {

    if (closeBannerButton.isDisplayed()) {
      try {
        logger.info("Closing the banner");
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(closeBannerButton).visible().setTimeout(Duration.ofSeconds(40)));
        closeBannerButton.click();
        return WebHelper.navigateBack(clazz);
      } catch (Exception e) {
        logger.info("Banner is not displayed");
        return SdkHelper.create(clazz);
      }
    } else {
      return SdkHelper.create(clazz);
    }
  }
}
