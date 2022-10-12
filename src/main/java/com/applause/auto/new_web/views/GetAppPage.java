package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = GetAppPage.class, on = Platform.WEB)
@Implementation(is = GetAppPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class GetAppPage extends Base {

  @Locate(id = "peetnik-rewards", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
  }

  @Step("verify Get App page is displayed")
  public boolean isGetAppPageDisplayed() {
    logger.info("Checking Get App page is displayed");
    return SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.REWARDS_URL);
  }
}

class GetAppPageMobile extends GetAppPage {

  @Locate(xpath = "//android.view.View[contains(@content-desc,'Google Play')]", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    WebHelper.waitForPageLoadingToComplete();
  }

  @Override
  public boolean isGetAppPageDisplayed() {
    logger.info("Checking Get App page is displayed");
    if (((SupportsContextSwitching) SdkHelper.getDriver()).getContextHandles().size() > 1) {
      WebHelper.switchToNativeContext();
      return WebHelper.isDisplayed(mainContainer, 30);
    } else {
      return super.isGetAppPageDisplayed();
    }
  }
}
