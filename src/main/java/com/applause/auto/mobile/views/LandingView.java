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
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.applause.auto.util.RetryTestException;
import java.time.Duration;

@Implementation(is = AndroidLandingView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = LandingView.class, on = Platform.MOBILE_IOS)
public class LandingView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Earn Rewards.", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/headingText", on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

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
    return this.create(ReportAProblemPopupChunk.class);
  }

  /**
   * Chunck for try mobile order ahead popup
   *
   * @return TryMobileOrderAheadPopupChunk
   */
  public TryMobileOrderAheadPopupChunk getTryMobileOrderAheadPopupChunk() {
    return this.create(TryMobileOrderAheadPopupChunk.class);
  }

  @Override
  public void afterInit() {
    getSyncHelper()
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
    getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
    return this.create(ExploreOffersView.class);
  }

  /**
   * Create account create account view.
   *
   * @return the create account view
   */
  public CreateAccountView createAccount() {
    logger.info("Tap on create account button");
    getSyncHelper()
        .wait(
            Until.uiElement(getCreateAccountButton).clickable().setTimeout(Duration.ofSeconds(20)));
    getSyncHelper().sleep(3000);
    getCreateAccountButton.click();
    return this.create(CreateAccountView.class);
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
    return this.create(SignInView.class);
  }

  /** Skip onboarding. */
  public void skipOnboarding() {
    logger.info("Skipping Onboarding");
    // this try catch is needed for iOS, since sometimes iOS test is starting on sign in/sign up
    // view
    try {
      getSyncHelper()
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
    logger.info(">>>>>>>>." + getDriver().getPageSource());
    getHeadingText.initialize();
    return getHeadingText.getText();
  }

  public void createAccountNavigation() {
    logger.info("Skipping create account navigation Steps");
  }
}

class AndroidLandingView extends LandingView {

  /* -------- Actions -------- */

  public void skipOnboarding() {
    logger.info("Android Skipping OnBoarding");
    try {
      getSyncHelper()
          .wait(Until.uiElement(getSkipButton).clickable().setTimeout(Duration.ofSeconds(20)));
      getSkipButton.click();

      if (!getCreateAccountButton.exists()) {
        for (int i = 0; i < 4; i++) {
          getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
          getSyncHelper().sleep(10000);
        }
        getSyncHelper()
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
    getSyncHelper()
        .wait(Until.uiElement(getHeadingText).visible().setTimeout(Duration.ofSeconds(240)));
  }
}
