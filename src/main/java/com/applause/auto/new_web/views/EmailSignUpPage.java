package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.time.Duration;

@Implementation(is = EmailSignUpPage.class, on = Platform.WEB)
public class EmailSignUpPage extends Base {

  @Locate(id = "main", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "input0input", on = Platform.WEB)
  private TextBox emailInput;

  @Locate(css = ".email-iframe", on = Platform.WEB)
  private ContainerElement emailFrame;

  @Locate(css = "#ctabutton1", on = Platform.WEB)
  private Button continueButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
  }

  /* -------- Actions -------- */

  /**
   * switch frame and find button
   *
   * @return a Boolean
   */
  public Boolean validateEmailInput() {
    logger.info("switch frame and find button");
    WebHelper.switchToIFrame(emailFrame);
    Boolean exists = emailInput.exists();
    SdkHelper.getDriver().switchTo().defaultContent();
    return exists;
  }

  /**
   * switch frame and find button
   *
   * @return a Boolean
   */
  public Boolean validateContinueButton() {
    logger.info("switch frame and find button");
    WebHelper.switchToIFrame(emailFrame);
    Boolean exists = continueButton.exists();
    SdkHelper.getDriver().switchTo().defaultContent();
    return exists;
  }
}
