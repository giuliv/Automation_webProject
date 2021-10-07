package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = HomePage.class, on = Platform.WEB)
@Implementation(is = HomePage.class, on = Platform.WEB_MOBILE_PHONE)
public class HomePage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".cookieconsent-wrapper .cc-allow", on = Platform.WEB)
  private Button allowCookies;

  @Locate(css = "button.launch-modal__close, #closeIconContainer", on = Platform.WEB)
  private Button closeModal;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Peet's Home URL: " + SdkHelper.getDriver().getCurrentUrl());

    if (!WebHelper.getTestExecution().equals("local")) {
      if (closeModal.exists()) {
        logger.info("Close peets.com Modal");
        SdkHelper.getSyncHelper().wait(Until.uiElement(closeModal).clickable());
        closeModal.click();
      }

      if (!WebHelper.isDesktop() && allowCookies.exists()) {
        logger.info("Accept Cookies");
        WebHelper.jsClick(allowCookies.getWebElement());
      }
    }
  }

  /* -------- Actions -------- */

}
