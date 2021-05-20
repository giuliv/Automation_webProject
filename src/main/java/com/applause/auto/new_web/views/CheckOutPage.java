package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

@Implementation(is = CheckOutPage.class, on = Platform.WEB)
@Implementation(is = CheckOutPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckOutPage extends Base {

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("CheckOut Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

}
