package com.applause.auto.new_web.components.pdp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

public class ProductDetailsViewImageComponent extends BaseComponent {

  @Locate(css = "img.fs-detail-image", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }
}
