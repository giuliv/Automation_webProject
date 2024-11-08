package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.ReportAProblemPopupChunk;
import com.applause.auto.mobile.components.TrackingPopup;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.time.Duration;
import lombok.Getter;

@Implementation(is = OnboardingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OnboardingViewIos.class, on = Platform.MOBILE_IOS)
public class OnboardingView extends BaseComponent {

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`label == \"SKIP\"`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/skipTextView\")",
      on = Platform.MOBILE_ANDROID)
  protected Button skipButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeScrollView/**/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/onBoardingViewPager\").childSelector(new UiSelector().resourceIdMatches(\".*id/textView24\"))",
      on = Platform.MOBILE_ANDROID)
  protected Text title;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeScrollView/**/XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'id/onBoardingViewPager')]//android.widget.TextView[last()]",
      on = Platform.MOBILE_ANDROID)
  protected Text description;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`name  == 'swipe left to continue'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/helpfulMessageTextView\")",
      on = Platform.MOBILE_ANDROID)
  protected Text helpfulMessage;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"SKIP\"]/preceding-sibling::XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/linePageIndicator\")",
      on = Platform.MOBILE_ANDROID)
  protected Button pageIndicator;

  @Locate(id = "swiping", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*swiping\")",
      on = Platform.MOBILE_ANDROID)
  protected Button animation;

  @Getter @Locate ReportAProblemPopupChunk getReportAProblemPopupChunk;
  @Getter @Locate TrackingPopup getTrackingPopup;

  @Override
  public void afterInit() {
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(skipButton).visible().setTimeout(Duration.ofSeconds(40)));
    } catch (Exception e) {
      logger.info("Skip button didn't appear");
    }
  }

  @Step("Get Onboarding Title")
  public String getTitle() {
    title.initialize();
    String titleText = title.getText().replaceAll("\n", " ");
    logger.info("Title - [{}]", WebHelper.cleanString(titleText));
    return WebHelper.cleanString(titleText);
  }

  @Step("Check if 'Skip' Button is Displayed")
  public boolean isSkipButtonDisplayed() {
    //    return MobileHelper.isDisplayed(skipButton);
    Boolean skipButtonDisplayed;
    if (SdkHelper.getEnvironmentHelper().isAndroid()) {
      skipButtonDisplayed = skipButton.getAttributeValue("enabled").equalsIgnoreCase("true");
    } else {
      skipButtonDisplayed = skipButton.getAttributeValue("accessible").equalsIgnoreCase("true");
    }
    return skipButtonDisplayed;
  }

  @Step("Get Onboarding description")
  public String getDescription() {
    String descriptionText = description.getText().replaceAll("\n", " ").replaceAll("  ", " ");
    logger.info("Description - [{}]", descriptionText);
    return WebHelper.cleanString(descriptionText);
  }

  @Step("Get helpful Message Text")
  public String getHelpfulMessage() {
    String helpfulMessageText = helpfulMessage.getText();
    logger.info("Helpful Message Text - [{}]", helpfulMessageText);
    return WebHelper.cleanString(helpfulMessageText);
  }

  @Step("Check if 'Helpful Message' is Displayed")
  public boolean isHelpfulMessageDisplayed() {
    return MobileHelper.isDisplayed(helpfulMessage);
  }

  @Step("Check if 'Page Indicator' is Displayed")
  public boolean isPageIndicatorDisplayed() {
    return pageIndicator.isEnabled();
  }

  @Step("Swipe to get to next tutorial view")
  public <T extends BaseComponent> T swipeOnScreen(SwipeDirection direction, Class<T> clazz) {
    logger.info("Swiping [{}] to get to next tutorial view", direction.name());
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(direction);
    MobileHelper.waitUntilElementDisappears(animation, 5);
    return SdkHelper.create(clazz);
  }

  @Step("Tap on 'Skip' button")
  public LandingView skipOnboarding() {
    logger.info("Tapping on 'Skip' button");
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(skipButton).present().setTimeout(Duration.ofSeconds(40)));

      if (skipButton.isClickable()) {
        MobileHelper.tapOnElementCenter(skipButton);

      } else {
        logger.info("For some reason, skip button seems not clickable");
        SdkHelper.getDeviceControl().tapElementCenter(skipButton);
      }
    } catch (Exception e) {
      logger.info("Skip button didn't appear");
    }

    return SdkHelper.create(LandingView.class);
  }
}

class OnboardingViewIos extends OnboardingView {

  private boolean isTrackingPopupAccepted = false;

  @Override
  public void afterInit() {
    if (!isTrackingPopupAccepted) {
      getGetTrackingPopup().allowTrackingIfDisplayed();
      isTrackingPopupAccepted = true;
    } else {
      logger.info("Tracking Popup was already Accepted");
    }
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(skipButton).present().setTimeout(Duration.ofSeconds(40)));
    } catch (Exception e) {
      logger.info("Skip button didn't appear");
    }
  }
}
