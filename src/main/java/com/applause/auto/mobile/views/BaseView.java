package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

import java.time.Duration;

public class BaseView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name == 'Back'`]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button backButton;

  /* -------- Actions -------- */

  @Step("Navigate back")
  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Tapping back button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(backButton).present().setTimeout(Duration.ofSeconds(15)));
    backButton.click();
    return SdkHelper.create(clazz);
  }

  @Step("Click back button")
  public void navigateBack() {
    try {
      if (backButton.isDisplayed()) logger.info("Click on back button");
      backButton.click();
    } catch (Exception e) {
      logger.info("No back button is displayed");
    }
  }
}
