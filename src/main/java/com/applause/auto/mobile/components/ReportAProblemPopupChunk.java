package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import java.time.Duration;
import org.openqa.selenium.WebDriverException;

/** The report problem popup chunk. */
@Implementation(is = ReportAProblemPopupChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = ReportAProblemPopupChunkiOS.class, on = Platform.MOBILE_IOS)
public class ReportAProblemPopupChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSNsPredicate =
          "type == 'XCUIElementTypeStaticText' AND value == 'We are happy to hear your thoughts'",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/ib_core_lyt_onboarding_pager_fragment",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement reportAProblemAdv;

  /* -------- Actions -------- */

  /** Wait for pop up to disappear */
  public void waitForPopUpToDisappear() {
    if (isReportAProblemPopUpDisplayed()) {
      logger.info("Report a problem pop up is present, waiting until it will disappear");
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(reportAProblemAdv).notPresent().setTimeout(Duration.ofSeconds(12)));
      SdkHelper.getSyncHelper().sleep(2000);
    }
  }

  /** * Check whether pop up is displayed */
  public boolean isReportAProblemPopUpDisplayed() {
    logger.info("Waiting for report a problem pop up to appear");
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(reportAProblemAdv).present().setTimeout(Duration.ofSeconds(12)));
      return true;
    } catch (WebDriverException e) {
      logger.error("Report a problem pop up didn't appear");
      return false;
    }
  }
}

class ReportAProblemPopupChunkiOS extends ReportAProblemPopupChunk {

  @Override
  public void waitForPopUpToDisappear() {
    if (isReportAProblemPopUpDisplayed()) {
      if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
        // for some reasons the pop up leave in iOS layout forever
        SdkHelper.getSyncHelper().sleep(12000);
      }
    }
  }
}
