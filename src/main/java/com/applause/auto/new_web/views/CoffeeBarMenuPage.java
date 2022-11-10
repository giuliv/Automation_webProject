package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = CoffeeBarMenuPage.class, on = Platform.WEB)
public class CoffeeBarMenuPage extends Base {

  @Locate(
      xpath = "//h1[@class='page-hero__heading' and normalize-space()='From The Coffeebar']",
      on = Platform.WEB)
  protected Text pageHeading;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).present());
  }
}
