package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = CoffeeRevolutionPage.class, on = Platform.WEB)
public class CoffeeRevolutionPage extends BaseComponent {

  @Locate(
      xpath = "//h1[@class='simple-hero__heading' and (contains(text(),'Our Coffee'))]",
      on = Platform.WEB)
  protected Text pageHeading;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).present());
  }
}
