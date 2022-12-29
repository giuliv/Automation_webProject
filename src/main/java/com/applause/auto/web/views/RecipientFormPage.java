package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = RecipientFormPage.class, on = Platform.WEB)
public class RecipientFormPage extends Base {

  @Locate(css = "main.app-wrapper", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "a.header__img > img[src]", on = Platform.WEB)
  private Image peetsCoffeeImage;

  @Locate(css = "button[data-cashbot-id=recipient-button-submit]", on = Platform.WEB)
  private Button sendDigitalCardButton;

  @Locate(css = ".tab-container > div:nth-child(1)", on = Platform.WEB)
  private Button friendButton;

  @Locate(css = ".tab-container > div:nth-child(2)", on = Platform.WEB)
  private Button forMeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
  }

  /* -------- Actions -------- */

  @Step("Verify Peet's coffee image is displayed")
  public boolean isPeetsCoffeeImageDisplayed() {
    return peetsCoffeeImage.isDisplayed();
  }

  @Step("Verify 'Send A Digital Card' button is displayed")
  public boolean isSendDigitalCardButtonDisplayed() {
    return sendDigitalCardButton.isDisplayed();
  }

  @Step("Verify 'A Friend' button is displayed")
  public boolean isFriendButtonDisplayed() {
    return friendButton.isDisplayed();
  }

  @Step("Verify 'For me' button is displayed")
  public boolean isForMeButtonDisplayed() {
    return forMeButton.isDisplayed();
  }
}
