package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.ReportAProblemPopupChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.util.RetryTestException;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = AndroidLandingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = LandingView.class, on = Platform.MOBILE_IOS)
public class LandingView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"LET’S MAKE IT OFFICIAL\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/authentication_title_text\")",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(id = "Skip", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/skipTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSkipButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getStartedButton;

  @Locate(iOSNsPredicate = "name='Create Account'", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/signup_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getCreateAccountButton;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name= 'Sign In'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/login_button\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getSignInButton;

  /* -------- Actions -------- */

  /**
   * Chunk for report a problem pop up
   *
   * @return
   */
  public ReportAProblemPopupChunk getReportAProblemPopupChunk() {
    return SdkHelper.create(ReportAProblemPopupChunk.class);
  }

  @Override
  public void afterInit() {
    logger.info("Landing View init");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSignInButton).visible().setTimeout(Duration.ofSeconds(40)));
    //    getReportAProblemPopupChunk().waitForPopUpToDisappear(); [20.01.23:Seems not needed]
  }

  /**
   * Create account SdkHelper.create account view.
   *
   * @return the SdkHelper.create account view
   */
  public CreateAccountView createAccount() {
    logger.info("Tap on SdkHelper.create account button");
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(getCreateAccountButton).clickable().setTimeout(Duration.ofSeconds(20)));
    SdkHelper.getSyncHelper().sleep(3000);
    MobileHelper.tapOnElementCenter(getCreateAccountButton);
    return SdkHelper.create(CreateAccountView.class);
  }

  /**
   * Sign in sign in view.
   *
   * @return the sign in view
   */
  public SignInView signIn() {
    logger.info("Click on Sign In button");
    getSignInButton.initialize();
    getSignInButton.click();
    return SdkHelper.create(SignInView.class);
  }

  /**
   * Get the text value of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    logger.info("Waiting for header text");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(60)));

    getHeadingText.initialize();
    logger.info("Heading text: " + getHeadingText.getText());
    return getHeadingText.getText();
  }

  public void createAccountNavigation() {
    logger.info("Skipping SdkHelper.create account navigation Steps");
  }

  @Step("Check if 'Sign In' Button is Displayed")
  public boolean isSignInButtonDisplayed() {
    return MobileHelper.isDisplayed(getSignInButton);
  }

  @Step("Check if 'Create Account' Button is Displayed")
  public boolean isCreateAccountButtonDisplayed() {
    return MobileHelper.isDisplayed(getCreateAccountButton);
  }
}

class AndroidLandingView extends LandingView {

  /* -------- Actions -------- */

  public void skipOnboarding() {
    logger.info("Android Skipping OnBoarding");

    try {
      logger.info("Clicking on Skip button, if its found");
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getSkipButton).clickable().setTimeout(Duration.ofSeconds(20)));
      getSkipButton.click();
    } catch (Exception e) {
      logger.info("Skip button was not found!");
    }

    try {

      if (!getCreateAccountButton.exists()) {
        logger.info("Swiping left until start button its displayed");
        for (int i = 0; i < 4; i++) {
          SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
          SdkHelper.getSyncHelper().sleep(10000);
        }
        SdkHelper.getSyncHelper()
            .wait(Until.uiElement(getStartedButton).clickable().setTimeout(Duration.ofSeconds(20)));
        getStartedButton.click();
      }

    } catch (Exception e) {
      throw new RetryTestException("Error while skipping the Landing View");
    }
  }

  @Override
  public void createAccountNavigation() {
    try {
      skipOnboarding();
      logger.info("Tap Create Account");
      createAccount();
    } catch (Exception e) {
      logger.info("Skipping Onboarding was not successful. Probably skip is not present.");
    }
  }

  @Override
  public void afterInit() {
    logger.info("Landing View init");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSignInButton).visible().setTimeout(Duration.ofSeconds(20)));
  }
}
