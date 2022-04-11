package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = FindACoffeeBarPage.class, on = Platform.WEB)
public class FindACoffeeBarPage extends BaseComponent {

  @Locate(id = "storeLocator", on = Platform.WEB)
  protected Text pageHeading;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).present());
  }
}
