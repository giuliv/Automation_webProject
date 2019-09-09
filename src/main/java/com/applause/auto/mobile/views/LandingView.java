package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
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

  @Locate(id = "Create Account", on = Platform.MOBILE_IOS)
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

  /** Skip offer. */
  public void skipOffer() {
    logger.info("Swipe left and verify Explore Offers screen has correct title");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 3);
    ExploreOffersView exploreOffersView = swipeLeftOnScreen();
    PayFasterView payFasterView = exploreOffersView.swipeLeftOnScreen();
    OrderAheadView orderAheadView = payFasterView.swipeLeftOnScreen();
    AuthenticationView authenticationView = orderAheadView.clickGetStartedButton();
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
    SyncHelper.sleep(20000);
    getSkipButton.click();
    SyncHelper.sleep(10000);
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
    SyncHelper.sleep(10000);
  }
}
