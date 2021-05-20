package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

@Implementation(is = HomePage.class, on = Platform.WEB)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "button[aria-label='allow cookies']", on = Platform.WEB)
  private Button allowCookies;

  @Override
  public void afterInit() {
    getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Peet's Home URL: " + getDriver().getCurrentUrl());

    if (allowCookies.exists()) {
      logger.info("Accept Cookies");
      allowCookies.click();
    }
  }

  /* -------- Actions -------- */

}
