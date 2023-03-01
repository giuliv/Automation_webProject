package com.applause.auto.test.mobile;

import static com.applause.auto.test.mobile.helpers.TestHelper.navigateToLandingView;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.helpers.SwipeDirectionCloseToMiddle;
import com.applause.auto.mobile.views.CartView;
import com.applause.auto.mobile.views.FindACoffeeBarView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.MoreOptionsView;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.PeetnikRewardsHelpView;
import com.applause.auto.mobile.views.RewardsStoreView;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RewardsBannerTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.REWARDS_BANNER},
      description = "11131880")
  public void Rewards97PointsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.login(
            navigateToLandingView(),
            Constants.MyAccountTestData.EMAIL,
            Constants.MyAccountTestData.PASSWORD);

    logger.info("Close initial onboarding views");
    homeView.getCheckInTooltipComponent().closeAnyTooltipIfDisplayed(3);

    logger.info("VERIFY Rewards Points View is NOT displayed in the home page");
    Assert.assertFalse(
        homeView.getCheckInTooltipComponent().isViewRewardsBannerDisplayed(),
        "Rewards Points view should not be displayed");
  }

  @Test(
      groups = {TestNGGroups.REWARDS_BANNER},
      description = "11131881")
  public void Rewards197PointsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.login(
            navigateToLandingView(),
            MyAccountTestData.EMAIL_REWARDS_197_POINTS,
            Constants.MyAccountTestData.PASSWORD);

    logger.info("Close initial onboarding views");
    homeView.getCheckInTooltipComponent().closeAnyTooltipIfDisplayed(3);

    logger.info("VERIFY Rewards Points View is displayed in the home page");
    Assert.assertTrue(
        homeView.getCheckInTooltipComponent().isViewRewardsBannerDisplayed(),
        "Rewards Points view is not displayed");
  }

  @Test(
      groups = {TestNGGroups.REWARDS_BANNER},
      description = "11131882")
  public void Rewards47PointsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.login(
            navigateToLandingView(),
            MyAccountTestData.EMAIL_REWARDS_47_POINTS,
            Constants.MyAccountTestData.PASSWORD);

    logger.info("Close initial onboarding views");
    homeView.getCheckInTooltipComponent().closeAnyTooltipIfDisplayed(3);

    logger.info("VERIFY Rewards Points View is NOT displayed in the home page");
    Assert.assertFalse(
        homeView.getCheckInTooltipComponent().isViewRewardsBannerDisplayed(),
        "Rewards Points view should not be displayed");
  }

  @Test(
      groups = {TestNGGroups.REWARDS_BANNER},
      description = "11131883")
  public void Rewards288PointsTest() {
    logger.info("STEP #1 - Launch the app and arrive at the first on boarding screen view");
    HomeView homeView =
        testHelper.login(
            navigateToLandingView(),
            MyAccountTestData.EMAIL_REWARDS_288_POINTS,
            Constants.MyAccountTestData.PASSWORD);

    logger.info("Close initial onboarding views");
    homeView.getCheckInTooltipComponent().closeAnyTooltipIfDisplayed(3);

    logger.info("VERIFY Rewards Points View is NOT displayed in the home page");
    Assert.assertFalse(
        homeView.getCheckInTooltipComponent().isViewRewardsBannerDisplayed(),
        "Rewards Points view should not be displayed");
  }
}
