package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = HomePage.class, on = Platform.WEB)
@Implementation(is = HomePageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".cookieconsent-wrapper .cc-allow", on = Platform.WEB)
  private Button allowCookies;

  @Locate(css = "button.launch-modal__close, #closeIconContainer", on = Platform.WEB)
  private Button closeModal;

  @Locate(css = "#header a[href*='/login']", on = Platform.WEB)
  @Locate(css = "#navMobileMain a[href*='/login']", on = Platform.WEB_MOBILE_PHONE)
  protected Button signInButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Peet's Home URL: " + SdkHelper.getDriver().getCurrentUrl());

    if (!WebHelper.getTestExecution().equals("local")) {
      if (closeModal.exists()) {
        logger.info("Close peets.com Modal");
        SdkHelper.getSyncHelper().wait(Until.uiElement(closeModal).clickable());
        closeModal.click();
      }

      if (!WebHelper.isDesktop() && allowCookies.exists()) {
        logger.info("Accept Cookies");
        WebHelper.jsClick(allowCookies.getWebElement());
      }
    }
  }

  /* -------- Actions -------- */

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }
}

class HomePageMobile extends HomePage {

  /**
   * Taps the sign in button.
   *
   * @return a Login
   */
  public SignInPage clickSignInButton() {
    getHeader().openHamburgerMenu();
    logger.info("Tap on SignIn Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(signInButton).clickable()).click();

    return SdkHelper.create(SignInPage.class);
  }
}
