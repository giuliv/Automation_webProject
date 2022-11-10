package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;

@Implementation(is = ReloadComponent.class, on = Platform.WEB)
@Implementation(is = ReloadComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class ReloadComponent extends BaseComponent {

  @Locate(css = ".reload-card", on = Platform.WEB)
  private ContainerElement reloadContainer;

  @Locate(
      css = "#acDashboard  button[class=\"btn-icon my-cards__modal--close\"]",
      on = Platform.WEB)
  private Button closeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(reloadContainer).visible());
  }

  @Step("Accept Cookies")
  public void closeReloadModal() {
    logger.info("Closing reload modal");
    closeButton.click();
  }
}
