package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = CurrentOffersPage.class, on = Platform.WEB)
public class CurrentOffersPage extends Base {

  @Locate(
      xpath = "//h1[@class='simple-hero__heading' and contains(., 'Current Offers')]",
      on = Platform.WEB)
  @Locate(
      xpath = "//h2[@class='nav-mobile-header__text' and contains(., 'Offers')]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Text pageHeading;

  public boolean isPageHeadingDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).visible());
    return pageHeading.isDisplayed();
  }
}
