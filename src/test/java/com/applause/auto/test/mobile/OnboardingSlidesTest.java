package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.mobile.views.*;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.SyncHelper;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * This is a sample test that verifies the project is setup correctly and can execute a simple test.
 */
public class OnboardingSlidesTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ONBOARDING},
      description = "625878")
  public void onboardingSlidesTest() {

    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = ComponentFactory.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    logger.info("Swipe left and verify Explore Offers screen has correct title");
    SyncHelper.sleep(5000);
    ExploreOffersView exploreOffersView = landingView.swipeLeftOnScreen();
    Assert.assertEquals(
        exploreOffersView.getHeadingTextValue(),
        "Explore Offers.",
        "First screen text value is not correct");

    logger.info("Swipe left and verify Pay Faster screen has correct title");
    PayFasterView payFasterView = exploreOffersView.swipeLeftOnScreen();
    Assert.assertEquals(
        payFasterView.getHeadingTextValue(),
        "Pay Faster.",
        "First screen text value is not correct");

    logger.info("Swipe left and verify Order Ahead screen has correct title");
    OrderAheadView orderAheadView = payFasterView.swipeLeftOnScreen();
    Assert.assertEquals(
        orderAheadView.getHeadingTextValue(),
        "Order Ahead.",
        "First screen text value is not correct");

    logger.info("Press Get Started button and verify Peetnik Rewards title");
    AuthenticationView authenticationView = orderAheadView.clickGetStartedButton();
    Assert.assertEquals(
        authenticationView.getRewardTitleTextValue(),
        "Peetnik Rewards",
        "Reward title text value is not correct");
  }
}
