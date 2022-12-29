package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;

@Implementation(is = CookieComponent.class, on = Platform.WEB)
@Implementation(is = CookieComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class CookieComponent extends BaseComponent {

  @Locate(css = ".cc-allow", on = Platform.WEB)
  private Button acceptCookiesButton;

  @Step("Accept Cookies")
  public void acceptCookies() {
    if (MobileHelper.isElementDisplayed(acceptCookiesButton, 5)) {
      acceptCookiesButton.click();
    } else {
      logger.info("No cookies popup overlay found");
    }
  }
}
