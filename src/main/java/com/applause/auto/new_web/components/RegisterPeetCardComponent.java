package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = RegisterPeetCardComponent.class, on = Platform.WEB)
@Implementation(is = RegisterPeetCardComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class RegisterPeetCardComponent extends BaseComponent {

  @Locate(css = ".register-card__fields", on = Platform.WEB)
  private ContainerElement getBaseComponent;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getBaseComponent).visible());
  }
}
