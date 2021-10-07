package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;

@Implementation(is = MyAccountPage.class, on = Platform.WEB)
public class MyAccountPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = "#acDashboard h1", on = Platform.WEB)
  @Locate(css = ".ac-section-header", on = Platform.WEB_MOBILE_PHONE)
  private Text getViewSignature;

  @Locate(className = "dashboard-rewards__modal--close", on = Platform.WEB)
  private Button closeBanner;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
    if (closeBanner.exists()) {
      closeBanner.click();
    }
    logger.info("My Account Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  /**
   * Clicks on LoginMenu
   *
   * @return LoginPage
   */
  public String getWelcomeMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(getViewSignature).visible());
    logger.info("Welcome message: " + getViewSignature.getText().toLowerCase());
    return getViewSignature.getText().toLowerCase();
  }
}
