package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = BrewGuidesPage.class, on = Platform.WEB)
@Implementation(is = BrewGuidesPage.class, on = Platform.WEB_MOBILE_PHONE)
public class BrewGuidesPage extends Base {

  @Locate(xpath = "//section[@class='page-hero']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Current URL - [{}]", WebHelper.getCurrentUrl());
  }

  @Step("Check if Brew Guides Page displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer);
  }
}
