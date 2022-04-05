package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = PeetnikRewardsPage.class, on = Platform.WEB)
public class PeetnikRewardsPage extends Base {

  @Locate(
      xpath = "//h1[@class='page-hero-full__heading' and contains(., 'Peetnik Rewards')]",
      on = Platform.WEB)
  protected Text pageHeading;

  public boolean isPageHeadingDisplayed() {
    return pageHeading.isDisplayed();
  }
}
