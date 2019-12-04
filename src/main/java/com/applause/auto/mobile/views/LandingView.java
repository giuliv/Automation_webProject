package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.ReportAProblemPopupChunk;
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

  @Locate(id = "//XCUIElementTypeButton[@name='Create Account']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/signUp", on = Platform.MOBILE_ANDROID)
  protected Button getCreateAccountButton;

  @Locate(id = "Sign In", on = Platform.MOBILE_IOS)
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

  @Override
  public void afterInit() {
    super.afterInit();
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
    getSignInButton.click();
    return ComponentFactory.create(SignInView.class);
  }

  /** Skip onboarding. */
  public void skipOnboarding() {
    logger.info("Skipping Onboarding");
    // this try catch is needed for iOS, since sometimes iOS test is starting on sign in/sign up
    // view
    try {
      getSkipButton.click();
    } catch (Exception e) {
      logger.error("Error while skipping the Landing View");
    }
  }

  /**
   * Get the text value of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    SyncHelper.sleep(3000);
    DriverManager.getDriver().getPageSource();
    return getHeadingText.getText();
  }
}

class AndroidLandingView extends LandingView {

  /* -------- Actions -------- */

  public void skipOnboarding() {
    logger.info("Skipping Onboarding");
    getSkipButton.click();
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
