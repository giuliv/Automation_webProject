package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;

@Implementation(is = CheckOutPage.class, on = Platform.WEB)
@Implementation(is = CheckOutPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckOutPage extends Base {

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("CheckOut Page URL: " + SdkHelper.getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

}
