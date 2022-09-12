package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = FindACoffeeBarPage.class, on = Platform.WEB)
public class FindACoffeeBarPage extends Base {

  @Locate(id = "storeLocator", on = Platform.WEB)
  protected Text pageHeading;

  @Override
  public void afterInit() {
    super.afterInit();
    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).present());
  }
}
