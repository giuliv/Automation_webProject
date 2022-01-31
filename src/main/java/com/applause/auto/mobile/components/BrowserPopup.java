package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = BrowserPopup.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = BrowserPopup.class, on = Platform.MOBILE_IOS)
public class BrowserPopup extends BaseComponent {

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/button_once\")",
      on = Platform.MOBILE_ANDROID)
  protected Text chromeBrowserOptionButton;

  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/positive_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser;

  @Locate(
      xpath = "//android.widget.Button[@text='Allow only while using the app']",
      on = Platform.MOBILE_ANDROID)
  protected Text allowLocationToBrowser2;

  @Locate(xpath = "//android.widget.Button[@text='Accept']", on = Platform.MOBILE_ANDROID)
  protected Button acceptCookiesButton;

  @Step("Close Browser Option Popup")
  public void closeBrowserOptionPopup() {
    if (MobileHelper.isElementDisplayed(chromeBrowserOptionButton, 5)) {
      chromeBrowserOptionButton.click();
    } else {
      logger.info("No browser popup overlay found");
    }
  }

  @Step("Close location Popup")
  public void closeLocationPopup() {
    if (MobileHelper.isElementDisplayed(allowLocationToBrowser, 5)) {
      allowLocationToBrowser.click();
    } else {
      logger.info("No location popup overlay didn't found");
    }

    if (MobileHelper.isElementDisplayed(allowLocationToBrowser2, 5)) {
      allowLocationToBrowser.click();
    } else {
      logger.info("No location popup 2 overlay didn't  found");
    }
  }

  @Step("Accept CookiesButton Popup")
  public void acceptCookies() {
    if (MobileHelper.isElementDisplayed(acceptCookiesButton, 5)) {
      acceptCookiesButton.click();
    } else {
      logger.info("No cookies popup overlay found");
    }
  }
}
