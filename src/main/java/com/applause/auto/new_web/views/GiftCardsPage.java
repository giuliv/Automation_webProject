package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.CheckYourCardBalanceModal;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
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

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
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
}
