package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = OnboardingLastView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OnboardingLastView.class, on = Platform.MOBILE_IOS)
public class OnboardingLastView extends BaseComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name='Create Account'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/createAccountButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button createAccountButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name='Sign In'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/signInButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button signInButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeScrollView/**/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/textView24\")",
      on = Platform.MOBILE_ANDROID)
  protected Text title;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(signInButton).visible().setTimeout(Duration.ofSeconds(20)));
  }

  @Step("Check if 'Sign In' Button is Displayed")
  public boolean isSignInButtonDisplayed() {
    return MobileHelper.isDisplayed(signInButton);
  }

  @Step("Check if 'Create Account' Button is Displayed")
  public boolean isCreateAccountButtonDisplayed() {
    return MobileHelper.isDisplayed(createAccountButton);
  }

  @Step("Swipe right on the screen")
  public OnboardingView swipeRightOnScreen() {
    logger.info("Swiping right on screen");
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.RIGHT);
    return SdkHelper.create(OnboardingView.class);
  }

  @Step("Get Title")
  public String getTitle() {
    title.initialize();
    String titleText = title.getText();
    logger.info("Heading text: [{}]", titleText);
    return WebHelper.cleanString(titleText);
  }
}
