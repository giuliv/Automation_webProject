package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = ShopRunnerChunk.class, on = Platform.WEB)
public class ShopRunnerChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "input#sr_sign_in_button", on = Platform.WEB)
  protected Button getSignInButton;

  @Locate(css = "#sr_close", on = Platform.WEB)
  protected Button getContinueShoppingButton;

  @Locate(css = "#sr_signin_email", on = Platform.WEB)
  protected TextBox getEmailTextBox;

  @Locate(css = "#sr_signin_password", on = Platform.WEB)
  protected TextBox getPasswordTextBox;

  @Locate(css = "#sr_modal_inner", on = Platform.WEB)
  protected ContainerElement getSignature;

  /* -------- Actions -------- */

  /**
   * Sign in.
   *
   * @param email the email
   * @param password the password
   */
  public void signIn(String email, String password) {
    logger.info("Clicking Sign In");
    getEmailTextBox.sendKeys(email);
    getPasswordTextBox.sendKeys(password);
    getSignInButton.click();
  }

  /**
   * Continue shopping t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T continueShopping(Class<T> clazz) {
    logger.info("Clicking Continue Shopping button");
    SdkHelper.getSyncHelper().sleep(5000);
    getContinueShoppingButton.click();
    SdkHelper.getSyncHelper().sleep(6000);
    return SdkHelper.create(clazz);
  }
}
