package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = SocialResponsibilityPage.class, on = Platform.WEB)
public class SocialResponsibilityPage extends BaseComponent {

  @Locate(css = ".footer__newsletter", on = Platform.WEB)
  protected ContainerElement baseElement;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(baseElement).present());
  }
}
