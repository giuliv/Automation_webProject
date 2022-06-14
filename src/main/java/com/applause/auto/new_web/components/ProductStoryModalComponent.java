package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

public class ProductStoryModalComponent extends BaseComponent {

  @Locate(id = "productStoryModal", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "div.modal__inner p", on = Platform.WEB)
  private Text modalContent;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Verify modal is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(modalContent);
  }
}
