package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import io.qameta.allure.Step;

@Implementation(is = GoogleMapPage.class, on = Platform.WEB)
public class GoogleMapPage extends BaseComponent {

  @Step("Check if Maps displayed")
  public boolean isDisplayed() {
    String currentUrl = SdkHelper.getDriver().getCurrentUrl();
    logger.info("Current URL is - [{}]", currentUrl);
    return currentUrl.contains("maps");
  }
}
