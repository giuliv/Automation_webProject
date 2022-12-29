package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = FreeHomeDeliveryPage.class, on = Platform.WEB)
public class FreeHomeDeliveryPage extends Base {

  @Locate(
      xpath = "//h1[@class='page-hero-full__heading' and contains(., 'Free Home Delivery')]",
      on = Platform.WEB)
  protected Text pageHeading;

  public boolean isPageHeadingDisplayed() {
    return pageHeading.isDisplayed();
  }
}
