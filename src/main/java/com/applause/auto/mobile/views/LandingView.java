package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.ReportAProblemPopupChunk;
import com.applause.auto.mobile.components.TryMobileOrderAheadPopupChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.util.RetryTestException;
import java.time.Duration;
import com.applause.auto.framework.SdkHelper;
import org.openqa.selenium.NoSuchElementException;

@Implementation(is = AndroidLandingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = LandingView.class, on = Platform.MOBILE_IOS)
public class LandingView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Earn Rewards.", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/headingText", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//XCUIElementTypeButton[contains(@label,'Allow')]", on = Platform.MOBILE_IOS)
  protected Text allowButton;

  @Locate(id = "Skip", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/skipTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSkipButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getStartedButton;

  @Locate(iOSNsPredicate = "name='Create Account'", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/signUp", on = Platform.MOBILE_ANDROID)
  protected Button getCreateAccountButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Sign In\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/logIn", on = Platform.MOBILE_ANDROID)
  protected Button getSignInButton;

  @Locate(
      xpath =
          "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/onBoardingViewPager",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getViewPager;

  /* -------- Actions -------- */

  /**
   * Chunck for report a problem pop up
   *
   * @return
   */
  public ReportAProblemPopupChunk getReportAProblemPopupChunk() {
    return SdkHelper.create(ReportAProblemPopupChunk.class);
  }

  /**
   * Chunck for try mobile order ahead popup
   *
   * @return TryMobileOrderAheadPopupChunk
   */
  public TryMobileOrderAheadPopupChunk getTryMobileOrderAheadPopupChunk() {
    return SdkHelper.create(TryMobileOrderAheadPopupChunk.class);
  }

  @Override
  public void afterInit() {
    logger.info("Landing View init");
    try {
      allowButton.click();
    } catch (NoSuchElementException nse) {
      logger.info("Popup 'Allow tracking' does not found");
    }
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).visible().setTimeout(Duration.ofSeconds(240)));
    getReportAProblemPopupChunk().waitForPopUpToDisappear();
  }

  /**
   * Swipe left on tutorial view and expect to arrive at next view
   *
   * @return
   */
  public ExploreOffersView swipeLeftOnScreen() {
    logger.info("Swiping left to get to next tutorial view");
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
    return SdkHelper.create(ExploreOffersView.class);
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
    getCreateAccountButton.click();
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

  /** Skip onboarding. */
  public void skipOnboarding() {
    logger.info("Skipping Onboarding");
    // this try catch is needed for iOS, since sometimes iOS test is starting on sign in/sign up
    // view
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getSkipButton).clickable().setTimeout(Duration.ofSeconds(20)));
      getSkipButton.click();
      logger.info("Skip button was clicked correctly");
    } catch (Exception e) {
      logger.error("Error while skipping the Landing View");
    }
  }

  /** Dismiss Try Mobile Order Ahead */
  public void dismissTryMobileOrderAhead() {
    logger.info("Looking for Try Mobile Order Ahead popup");
    if (getTryMobileOrderAheadPopupChunk().isDismissButtonDisplayed()) {
      logger.info("Popup found. Clicking on dismiss button");
      getTryMobileOrderAheadPopupChunk().clickDismissButton();
    } else {
      logger.info("Popup not found. Moving on");
    }
  }

  /**
   * Get the text value of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    logger.info(">>>>>>>>." + SdkHelper.getDriver().getPageSource());
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
        .wait(Until.uiElement(getHeadingText).visible().setTimeout(Duration.ofSeconds(240)));
  }
}
