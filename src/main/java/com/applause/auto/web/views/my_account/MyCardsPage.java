package com.applause.auto.web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.components.RegisterPeetCardComponent;
import com.applause.auto.web.components.ReloadComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.GiftCardsPage;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = MyCardsPage.class, on = Platform.WEB)
@Implementation(is = MyCardsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MyCardsPage extends BaseComponent {

  @Locate(css = ".my-cards", on = Platform.WEB)
  private Text baseElement;

  @Locate(css = "[class=\"ac-table__list list-reset\"]>li", on = Platform.WEB)
  private List<MyCardsPage> peetsCardTable;

  @Locate(css = "[aria-label=\"Peet's Card #\"]", on = Platform.WEB)
  private Text peetsCardNumber;

  @Locate(css = "[aria-label=\"Card Balance\"]", on = Platform.WEB)
  private Text peetsCardBalance;

  @Locate(xpath = "//button[contains(text(), 'Reload')]", on = Platform.WEB)
  private Button reloadButton;

  @Locate(xpath = "//button[contains(text(), 'Remove')]", on = Platform.WEB)
  private Button removeButton;

  @Locate(css = ".my-cards__actions a[href*='register']", on = Platform.WEB)
  private Button registerNewPeetsCardButton;

  @Locate(css = ".my-cards__actions a[href*='gift']", on = Platform.WEB)
  private Button buyNewPeetsCardButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(baseElement).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("My cards page URL: " + getDriver().getCurrentUrl());
  }

  public String getCardNumberByIndex(int index) {
    logger.info("Card number: {}", peetsCardNumber.getText());
    String cardNumber =
        peetsCardTable.stream().collect(Collectors.toList()).get(index).getCardNumber();
    return cardNumber;
  }

  public String getCardNumber() {
    logger.info("Card number: {}", peetsCardNumber.getText());
    return peetsCardNumber.getText();
  }

  public String getCardBalance() {
    logger.info("Card number: {}", peetsCardBalance.getText());
    return peetsCardBalance.getText();
  }

  public Boolean isReloadButtonDisplayed() {
    return reloadButton.isDisplayed();
  }

  public Boolean isRemoveButtonDisplayed() {
    return removeButton.isDisplayed();
  }

  public ReloadComponent clickReloadButton() {
    logger.info("Clicking reload button");
    WebHelper.scrollToElement(reloadButton);
    reloadButton.click();
    return SdkHelper.create(ReloadComponent.class);
  }

  public RegisterPeetCardComponent clickRegisterPeetsCard() {
    logger.info("Clicking 'Register Peet's Card' button");
    registerNewPeetsCardButton.click();
    return SdkHelper.create(RegisterPeetCardComponent.class);
  }

  public GiftCardsPage clickBuyPeetsCard() {
    logger.info("Clicking 'Buy Peet's Card' button");
    buyNewPeetsCardButton.click();
    return SdkHelper.create(GiftCardsPage.class);
  }

  @Step("Verify REGISTER NEW PEET'S CARDS Button is displayed")
  public Boolean isRegisterNewPeetsCardsDisplayed() {
    return WebHelper.isDisplayed(registerNewPeetsCardButton);
  }

  @Step("Verify Buy a Peet's Card Button is displayed")
  public Boolean isBuyPeetsCardDisplayed() {
    return WebHelper.isDisplayed(buyNewPeetsCardButton);
  }
}
