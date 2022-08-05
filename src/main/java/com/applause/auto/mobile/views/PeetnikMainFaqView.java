package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Link;

@Implementation(is = AndroidPeetnikMainFaqView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetnikMainFaqView.class, on = Platform.MOBILE_IOS)
public class PeetnikMainFaqView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeLink[@name=\"Peetnik Rewards & Order Ahead\"]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//a[./h4[text()='Peetnik Rewards & Order Ahead']]", on = Platform.MOBILE_ANDROID)
  protected Link peetnikRewardsAndOrderAhead;

  /* -------- Actions -------- */

  /**
   * Tap on “Peetnik Rewards & Order Ahead” section
   *
   * @return PeetnikRewardsLandingView
   */
  public PeetnikRewardsLandingView tapPeetnikRewardsAndOrderAhead() {
    logger.info("Clicking on 'Peetnik Rewards & Order Ahead' section");
    MobileHelper.scrollDownToElementCloseToMiddle(peetnikRewardsAndOrderAhead, 2);
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetnikRewardsAndOrderAhead).present());
    MobileHelper.tapByCoordinatesOnElementCenter(peetnikRewardsAndOrderAhead);
    return SdkHelper.create(PeetnikRewardsLandingView.class);
  }
}

class AndroidPeetnikMainFaqView extends PeetnikMainFaqView {

  @Override
  public PeetnikRewardsLandingView tapPeetnikRewardsAndOrderAhead() {
    logger.info("Clicking on 'Peetnik Rewards & Order Ahead' section");
    MobileHelper.switchToChromeWebView();
    SdkHelper.getSyncHelper().wait(Until.uiElement(peetnikRewardsAndOrderAhead).present());
    peetnikRewardsAndOrderAhead.click();
    MobileHelper.switchToNativeContext();
    return SdkHelper.create(PeetnikRewardsLandingView.class);
  }
}
