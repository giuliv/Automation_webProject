package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = PasswordRecoveryPage.class, on = Platform.WEB)
public class PasswordRecoveryPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".col-main", on = Platform.WEB)
  private Text getMessageText;

  /* -------- Actions -------- */

  @Step("Verify successful message is displayed")
  public boolean isSuccessfulMessageDisplayed(String email) {
    logger.info("Checking successful message is displayed");
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getMessageText).visible().setTimeout(Duration.ofSeconds(20)));
    } catch (Exception e) {
      logger.error("Successful message isn't displayed");
      return false;
    }

    String currentMessageText = getMessageText.getText();
    if (!currentMessageText.contains(email)) {
      logger.error(
          "The message [{}] doesn't contain expected email [{}]", currentMessageText, email);
      return false;
    }

    return true;
  }
}
