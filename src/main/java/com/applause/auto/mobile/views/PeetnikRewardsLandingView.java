package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.List;

import io.appium.java_client.AppiumDriver;

@Implementation(is = AndroidPeetnikRewardsLandingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = PeetnikRewardsLandingView.class, on = Platform.MOBILE_IOS)
public class PeetnikRewardsLandingView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeOther[@name=\"Close\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.webkit.WebView//android.widget.Button[@text='Close'])[1] | //*[@resource-id='svg-close-button'] | //span[@class='close-button']",
      on = Platform.MOBILE_ANDROID)
  protected Button closeAdvPopUpButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Close banner\"]", on = Platform.MOBILE_IOS)
  protected Button closeDownloadPopUpButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"GET ANSWERS\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[text()='Get answers']", on = Platform.MOBILE_ANDROID)
  protected Button getAnswersButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Peetnik Rewards & Order Ahead\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.view.View[contains(@text,'Peetnik Rewards')]",
      on = Platform.MOBILE_ANDROID)
  protected Button getPeetnikRewardsAndOrderAheadButton;

  @Locate(
      xpath = "//*[@name='About Peetnik Rewards']/following-sibling::*[1]/*",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//*[@text='Peetnik Rewards & Order Ahead'])[last()]/parent::android.view.View/following-sibling::android.view.View[1]//android.widget.ListView/*",
      on = Platform.MOBILE_ANDROID)
  protected List<Button> getQuestions;

  @Locate(xpath = "//XCUIElementTypeOther[@name=\"article\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//*[contains(@resource-id, 'article-container')]", on = Platform.MOBILE_ANDROID)
  protected Text getQuestionAnswerText;

  /* -------- Actions -------- */

  /**
   * Click 'Get Answers'
   *
   * @return PeetnikRewardsLandingView
   */
  public PeetnikRewardsLandingView clickGetAnswers() {
    logger.info("Click 'Get Answers'");
    // this method is not swiping to the required locator
    //    MobileHelper.scrollUntilElementSectionWillBeAvailableOnTheScreenInWebView(
    //        getAnswersButton, "'Get Answers'", 25);
    // so used the basic swipe method with count
    // MobileHelper.swipeWithCount(SwipeDirection.UP, 8);
    getAnswersButton.click();
    getSyncHelper().sleep(10000);
    // return this
    return this.create(PeetnikRewardsLandingView.class);
  }

  /**
   * Click 'Peetnik Rewards & Order Ahead'
   *
   * @return PeetnikRewardsLandingView
   */
  public PeetnikRewardsLandingView clickGetPeetnikRewardsAndOrderAheadQuestion() {
    logger.info("Click 'Peetnik Rewards & Order Ahead'");
    if (!(getPeetnikRewardsAndOrderAheadButton.exists()
        && getPeetnikRewardsAndOrderAheadButton.isDisplayed())) {
      getPeetnikRewardsAndOrderAheadButton.click();
      MobileHelper.scrollDownToElementCloseToMiddle(getPeetnikRewardsAndOrderAheadButton, 2);
    }
    MobileHelper.tapByCoordinatesOnElementCenter(getPeetnikRewardsAndOrderAheadButton);
    getSyncHelper().sleep(10000);
    logger.info("Checking list of questions is loaded'");
    getSyncHelper().waitUntil(condition -> !getQuestions.isEmpty());
    // for ios simple click doesn't work on getQuestions's item link
    MobileHelper.tapByCoordinatesOnElementCenter(getQuestions.get(0));
    getSyncHelper().sleep(10000);
    // return this
    return this.create(PeetnikRewardsLandingView.class);
  }

  /**
   * Check question answer is displayed
   *
   * @return condition whether question answer is displayed
   */
  public boolean isQuestionAnswerDisplayed() {
    return getQuestionAnswerText.isEnabled();
  }

  /** Close adv. pop up is present if present */
  public PeetnikRewardsLandingView closeReportAProblemPopUpDisplayed() {
    logger.info("Waiting for adv. pop up");
    getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    try {
      logger.info("Contexts: " + ((AppiumDriver) getDriver()).getContextHandles());
      if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
        logger.info("Switching to WebContext");
        ((AppiumDriver) getDriver()).context("WEBVIEW_chrome");
      }
      logger.info("Xml: " + getDriver().getPageSource());
      getSyncHelper()
          .wait(Until.uiElement(closeAdvPopUpButton).present().setTimeout(Duration.ofSeconds(30)));
      if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
        closeAdvPopUpButton.click();
        ((AppiumDriver) getDriver()).context("NATIVE_APP");
      } else {
        // for ios simple click doesn't work on [X] button
        logger.info("Close Download popup");
        //        closeDownloadPopUpButton.initialize();
        //        closeDownloadPopUpButton.click();
        //        logger.info(">>>" + getDriver().getPageSource());
        closeAdvPopUpButton.initialize();
        MobileHelper.tapByCoordinatesOnElementCenter(closeAdvPopUpButton);
      }
    } catch (WebDriverException e) {
      logger.info("Sign Up adv. pop up is not present");
    }
    return this.create(PeetnikRewardsLandingView.class);
  }
}

class AndroidPeetnikRewardsLandingView extends PeetnikRewardsLandingView {
  public PeetnikRewardsLandingView clickGetPeetnikRewardsAndOrderAheadQuestion() {
    ((AppiumDriver) getDriver()).context("NATIVE_APP");
    logger.info("Click 'Peetnik Rewards & Order Ahead'" + getDriver().getPageSource());
    //    try {
    //      getPeetnikRewardsAndOrderAheadButton.click();
    //    } catch (Throwable th) {
    //      MobileHelper.scrollDownToElementCloseToMiddle(getPeetnikRewardsAndOrderAheadButton, 2);
    //      getPeetnikRewardsAndOrderAheadButton.click();
    //    }
    getSyncHelper().sleep(10000);
    logger.info("Checking list of questions is loaded'");
    getSyncHelper().waitUntil(condition -> !getQuestions.isEmpty());
    logger.info("Click first available question");
    getQuestions.get(0).click();
    getSyncHelper().sleep(10000);
    // return this
    return this.create(PeetnikRewardsLandingView.class);
  }
}
