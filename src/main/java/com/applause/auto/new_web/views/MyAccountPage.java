package com.applause.auto.new_web.views;

import java.time.Duration;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = MyAccountPage.class, on = Platform.WEB)
public class MyAccountPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = "#acDashboard h1", on = Platform.WEB)
  @Locate(css = ".ac-section-header", on = Platform.WEB_MOBILE_PHONE)
  private Text getViewSignature;

  @Override
  public void afterInit() {
    logger.info("Refresh site");
    SdkHelper.getDriver().navigate().refresh();
    SdkHelper.getSyncHelper().sleep(5000);

    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
  }

  /* -------- Actions -------- */

  /**
   * Clicks on LoginMenu
   *
   * @return LoginPage
   */
  public String getWelcomeMessage() {
    logger.info("Welcome message: " + getViewSignature.getText().toLowerCase());
    return getViewSignature.getText().toLowerCase();
  }
}
