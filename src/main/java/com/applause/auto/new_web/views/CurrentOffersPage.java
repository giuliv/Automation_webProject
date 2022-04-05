package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = CurrentOffersPage.class, on = Platform.WEB)
public class CurrentOffersPage extends Base {

  @Locate(
      xpath = "//h1[@class='simple-hero__heading' and contains(., 'Current Offers')]",
      on = Platform.WEB)
  protected Text pageHeading;

  public boolean isPageHeadingDisplayed() {
    return pageHeading.isDisplayed();
  }
}
