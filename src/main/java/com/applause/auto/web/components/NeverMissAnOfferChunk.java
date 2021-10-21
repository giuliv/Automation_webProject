package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = NeverMissAnOfferChunk.class, on = Platform.WEB)
public class NeverMissAnOfferChunk extends BaseComponent {

  @Locate(css = "#newsletterEmail", on = Platform.WEB)
  private TextBox enterEmailAddressField;

  @Locate(
      xpath = "//li[contains(@class, 'footer') and contains(@class, 'newsletter')]/button",
      on = Platform.WEB)
  private Button signUpButton;

  @Locate(css = "#formMessages", on = Platform.WEB)
  private Text message;

  /**
   * Click on type an emain in Never miss an offer.
   *
   * @return NeverMissAnOfferChunk
   */
  public NeverMissAnOfferChunk signUp(String email) {
    logger.info("typing email address [{}]", email);
    enterEmailAddressField.clearText();
    enterEmailAddressField.sendKeys(email);

    logger.info("Click on Sign Up button");
    signUpButton.click();
    return this;
  }

  /**
   * Get Message
   *
   * @return String
   */
  public String getMessage() {
    String messageString = message.getText();
    logger.info("Form message  - [{}]", messageString);
    return WebHelper.cleanString(messageString);
  }
}
