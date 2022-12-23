package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = AccountActivityView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountActivityView.class, on = Platform.MOBILE_IOS)
public class AccountActivityView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"ACCOUNT ACTIVITY\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='ACCOUNT ACTIVITY']",
      on = Platform.MOBILE_ANDROID)
  protected Text getSignatureText;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeNavigationBar[`name == 'ACCOUNT ACTIVITY'`]/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@content-desc, 'Navigate up')]", on = Platform.MOBILE_ANDROID)
  protected Button backButton;

  /* -------- Actions -------- */

  @Step("Verify Back button is displayed")
  public boolean isBackButtonDisplayed() {
    return MobileHelper.isDisplayed(backButton);
  }

  @Step("Verify 'ACCOUNT ACTIVITY' header is displayed")
  public boolean isHeaderDisplayed() {
    return MobileHelper.isElementDisplayed(getSignatureText, 15);
  }
}
