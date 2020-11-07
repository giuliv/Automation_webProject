package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.ReportAProblemPopupChunk;
import com.applause.auto.mobile.components.TryMobileOrderAheadPopupChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

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

  //  @Locate(id = "Sign In", on = Platform.MOBILE_IOS)
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
    return ComponentFactory.create(ReportAProblemPopupChunk.class);
  }

  /**
   * Chunck for try mobile order ahead popup
   *
   * @return TryMobileOrderAheadPopupChunk
   */
  public TryMobileOrderAheadPopupChunk getTryMobileOrderAheadPopupChunk() {
    return ComponentFactory.create(TryMobileOrderAheadPopupChunk.class);
  }

  @Override
  public void afterInit() {
    super.afterInit();
    SyncHelper.wait(Until.uiElement(getHeadingText).visible().setTimeout(Duration.ofSeconds(240)));
    getReportAProblemPopupChunk().waitForPopUpToDisappear();
  }

  /**
   * Swipe left on tutorial view and expect to arrive at next view
   *
   * @return
   */
  public ExploreOffersView swipeLeftOnScreen() {
    logger.info("Swiping left to get to next tutorial view");
    DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
    return ComponentFactory.create(ExploreOffersView.class);
  }

  /**
   * Create account create account view.
   *
   * @return the create account view
   */
  public CreateAccountView createAccount() {
    logger.info("Tap on create account button");
    //    SyncHelper.sleep(10000);
    SyncHelper.wait(
        Until.uiElement(getCreateAccountButton).clickable().setTimeout(Duration.ofSeconds(20)));
    getCreateAccountButton.click();
    return ComponentFactory.create(CreateAccountView.class);
  }

  /**
   * Sign in sign in view.
   *
   * @return the sign in view
   */
  public SignInView signIn() {
    logger.info("Click on Sign In button");
    SyncHelper.sleep(5000);
    getSignInButton.initialize();
    getSignInButton.click();
    SyncHelper.sleep(1000);
    return ComponentFactory.create(SignInView.class);
  }

  /** Skip onboarding. */
  public void skipOnboarding() {
    logger.info("Skipping Onboarding");
    // this try catch is needed for iOS, since sometimes iOS test is starting on sign in/sign up
    // view
    try {
      SyncHelper.wait(
          Until.uiElement(getSkipButton).clickable().setTimeout(Duration.ofSeconds(20)));
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
    logger.info(">>>>>>>>." + DriverManager.getDriver().getPageSource());
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
    logger.info("Android Skipping Onboarding");
    MobileHelper.activateApp();
    try {

      SyncHelper.wait(Until.uiElement(getSkipButton).present().setTimeout(Duration.ofSeconds(20)));

      for (int i = 0; i < 3; i++) {
        DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
        SyncHelper.sleep(2000);
      }

      SyncHelper.wait(
          Until.uiElement(getStartedButton).clickable().setTimeout(Duration.ofSeconds(20)));
      getStartedButton.click();
    } catch (Exception e) {
      throw new RuntimeException("Error while skipping the Landing View");
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

  // implemented this method for Android, but it looks like it is redundant
  // didn't remove it, maybe it could be used somewhere in the future
  /*@Override
  public ExploreOffersView swipeLeftOnScreen() {
    logger.info("Swiping left to get to next tutorial view");
    Point viewPagerCenter = getViewPager.getMobileElement().getCenter();
    Dimension viewPagerSize = getViewPager.getMobileElement().getSize();
    int offset = (int) (viewPagerSize.getWidth() / 2.5);

    logger.debug(String.format("View page center: %s", viewPagerCenter.toString()));
    logger.debug(String.format("View page size: %s", viewPagerSize.toString()));
    logger.debug(String.format("Swiping offset: %d", offset));

    DeviceControl.swipeAcrossScreenCoordinates(
        viewPagerCenter.getX() + offset,
        viewPagerCenter.getY(),
        viewPagerCenter.getX() - offset,
        viewPagerCenter.getY(),
        1000);
    return ComponentFactory.create(ExploreOffersView.class);
  }*/
}
