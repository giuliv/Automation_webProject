package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = OrderDeliveryAddressPage.class, on = Platform.WEB)
public class OrderDeliveryAddressPage extends BaseComponent {

  @Locate(xpath = "//ion-router-outlet", on = Platform.WEB)
  protected ContainerElement baseElement;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(baseElement).present());
  }
}
