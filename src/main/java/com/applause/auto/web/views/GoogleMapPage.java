package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import io.qameta.allure.Step;

@Implementation(is = GoogleMapPage.class, on = Platform.WEB)
public class GoogleMapPage extends BaseComponent {

  @Step("Check if Maps displayed")
  public boolean isDisplayed() {
    String currentUrl = WebHelper.getCurrentUrl();
    logger.info("Current URL is - [{}]", currentUrl);
    return currentUrl.contains("maps");
  }
}
