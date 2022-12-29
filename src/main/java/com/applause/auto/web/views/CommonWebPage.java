package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.FooterComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import io.qameta.allure.Step;

@Implementation(is = CommonWebPage.class, on = Platform.WEB)
@Implementation(is = CommonWebPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CommonWebPage extends Base {

  @Step("Get Footer component")
  public FooterComponent getFooterComponent() {
    return SdkHelper.create(FooterComponent.class);
  }

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.waitForPageLoadingToComplete();
    // This is a common page for all footer options. We can't use element to wait, so added this
    // static wait
    SdkHelper.getSyncHelper().sleep(2000);
  }
}
