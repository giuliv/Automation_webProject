package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = MenuPage.class, on = Platform.WEB)
@Implementation(is = MenuPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MenuPage extends BaseComponent {

  @Locate(xpath = "//div[contains(@class, 'location-menu')]", on = Platform.WEB)
  private Text baseElement;

  @Step("Check if 'Menu Page' is Displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }
}
