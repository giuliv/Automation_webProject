package com.applause.auto.new_web.views;

import java.time.Duration;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.EmailHelper;
import com.applause.auto.helpers.email.Inbox;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

import io.qameta.allure.Step;

@Implementation(is = PasswordRecoveryPage.class, on = Platform.WEB)
public class PasswordRecoveryPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".col-main", on = Platform.WEB)
  private Text getMessageText;

  /* -------- Actions -------- */

	/**
	 * Is successful message displayed boolean.
	 *
	 * @param email
	 *            the email
	 * @return the boolean
	 */
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

	/**
	 * Navigate recovery url password recovery reset page.
	 *
	 * @return the password recovery reset page
	 */
	public PasswordRecoveryResetPage navigateRecoveryUrl() {
		logger.info("Navigate Url from email");
		String recoveryUrl = getRecoverUrl();
		logger.info("Recovery Url: " + recoveryUrl);
		SdkHelper.getDriver().navigate().to(recoveryUrl);
		return SdkHelper.create(PasswordRecoveryResetPage.class);
	}

	private String getRecoverUrl() {
		Inbox inbox = EmailHelper.getInbox("peetfp01");
		String emailBody = inbox.waitForEmail().getBody();
		return emailBody.replaceAll("(?s).*(https://.*/reset-password/.+?(?=\")).*", "$1");
	}
}
