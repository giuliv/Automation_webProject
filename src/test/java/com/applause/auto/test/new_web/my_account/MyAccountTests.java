package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.MyAccountLeftMenu;
import com.applause.auto.new_web.components.RegisterPeetCardComponent;
import com.applause.auto.new_web.components.ReloadComponent;
import com.applause.auto.new_web.components.my_account.MyOrderItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.ContactUsPage;
import com.applause.auto.new_web.views.CreateAccountPage;
import com.applause.auto.new_web.views.GiftCardsPage;
import com.applause.auto.new_web.views.PasswordRecoveryPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.ResetPasswordPage;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.new_web.views.my_account.MyAccountEmailPreferencesPage;
import com.applause.auto.new_web.views.my_account.MyAccountOrderHistoryPage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.new_web.views.my_account.MyAccountPeetnikRewardsPage;
import com.applause.auto.new_web.views.my_account.MyAccountSettingsPage;
import com.applause.auto.new_web.views.my_account.MyCardsPage;
import com.applause.auto.new_web.views.my_account.OrderHistoryPage;
import com.applause.auto.new_web.views.my_account.ReferralsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MyAccountTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT, TestNGGroups.SANITY},
      description = "11102914")
  public void loginValidMenuTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountLeftMenu leftMenu = signInPage.clickOnSignInButton().getLeftMenu();
    softAssert.assertTrue(leftMenu.isLogoutButtonDisplayed(), "Logout button isn't displayed");
    softAssert.assertTrue(
        leftMenu.menuDisplaysAllOptions(), "Not all left menu options are displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, Constants.TestNGGroups.MY_ACCOUNT},
      description = "11102938")
  public void myAccountSettingsElementsTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("Verify Account information title is displayed");
    softAssert.assertTrue(
        settingsPage.isAccountInformationTitleDisplayed(),
        "Account information title isn't displayed");

    logger.info("Verify name and email is matched with user");
    softAssert.assertEquals(
        settingsPage.getName(), TestData.ANDREW_TESTER, "Name isn't matched with user name");
    softAssert.assertEquals(
        settingsPage.getEmail(), TestData.PEETS_USERNAME, "Email isn't matched with user email");

    logger.info("Verify phone number and password should display.");
    softAssert.assertTrue(settingsPage.isPhoneNumberDisplayed(), "Phone number isn't displayed");
    softAssert.assertTrue(settingsPage.isPasswordDisplayed(), "Password isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT, TestNGGroups.SANITY},
      description = "11102939")
  public void myAccountSettingsEditPasswordTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Create an account");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");

    logger.info("3. Click on Create");
    String email = WebHelper.getRandomMail();
    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            WebTestData.FIRST_NAME,
            WebTestData.LAST_NAME,
            email,
            TestData.WEB_PASSWORD,
            TestData.WEB_PASSWORD);

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("5. Click on Edit password");
    ResetPasswordPage resetPasswordPage = settingsPage.clickEditPasswordLink();
    Assert.assertNotNull(resetPasswordPage, "Failed to navigate to the Reset password page.");

    logger.info("6. Enter email.");
    resetPasswordPage.enterEmail(email);

    logger.info("7. Click on Submit");
    PasswordRecoveryPage passwordRecoveryPage = resetPasswordPage.clickSubmitButton();
    Assert.assertTrue(
        passwordRecoveryPage.isSuccessfulMessageDisplayed(email),
        "Successful message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT},
      description = "11102940")
  public void myAccountSettingsEmailPreferencesTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("5. Click on Edit email preferences");
    MyAccountEmailPreferencesPage emailPreferencesPage =
        settingsPage.clickEditEmailPreferencesLink();
    Assert.assertNotNull(emailPreferencesPage, "Failed to navigate to the Email preferences page.");

    logger.info("6. Select a few checkboxes");
    emailPreferencesPage.selectRandomCheckboxes(3);

    logger.info("7. Click on Submit");
    emailPreferencesPage = emailPreferencesPage.clickSubmitButton();

    logger.info("Verify Success! Email settings updated. displays.");
    Assert.assertTrue(
        emailPreferencesPage.messageIsDisplayed("Success! Email settings updated."),
        "Message isn't displayed");

    logger.info("8. Click on Unsubscribe");
    emailPreferencesPage = emailPreferencesPage.clickUnsubscribeButton();

    logger.info("Verify Your email settings have been updated displays.");
    Assert.assertTrue(
        emailPreferencesPage.messageIsDisplayed("Your email settings have been updated."),
        "Message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT, TestNGGroups.SANITY},
      description = "11102924")
  public void myAccountPeetnikRewardsElementsTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in Peetnik rewards");
    MyAccountPeetnikRewardsPage peetnikRewardsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.PEETNIK_REWARDS);

    logger.info("Verify Peetnik rewards title and Rewards settings subtitle are displayed");
    softAssert.assertTrue(peetnikRewardsPage.isTitleDisplayed(), "Title isn't displayed");
    softAssert.assertTrue(peetnikRewardsPage.isSubTitleDisplayed(), "Subtitle isn't displayed");
    String expectedUserName =
        Constants.WebTestData.FIRST_NAME + " " + Constants.WebTestData.LAST_NAME;
    softAssert.assertEquals(
        peetnikRewardsPage.getUserName(), expectedUserName, "User name isn't displayed properly");
    softAssert.assertEquals(
        peetnikRewardsPage.getEmail(),
        TestData.USER_EMAIL_WITH_SUBSCRIPTIONS,
        "Email isn't displayed properly");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT},
      description = "11102925")
  public void myAccountPeetnikRewardsCustomerExperienceTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in Peetnik rewards");
    MyAccountPeetnikRewardsPage peetnikRewardsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.PEETNIK_REWARDS);

    logger.info("5. Click on Customer experience team");
    ContactUsPage contactUsPage = peetnikRewardsPage.clickCustomerExperienceTeamLink();

    logger.info("Verify Contact us page is display");
    Assert.assertNotNull(contactUsPage, "Contact us page is not display");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT},
      description = "11102926")
  public void myAccountMySubscriptionElementsTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in My subscription");
    int pageCoordinatesBefore = WebHelper.getPagePositionY();
    myAccountPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.MY_SUBSCRIPTIONS);

    logger.info("Verify page is scrolled to My subscriptions in Dashboard");
    Assert.assertTrue(
        pageCoordinatesBefore < WebHelper.getPagePositionY(),
        "Page is not scrolled to My subscriptions in Dashboard");

    logger.info(
        "Verify list of subscriptions should display with product name, image and price, quantity and frequency");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionImageDisplayed(), "Subscription image isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionNameDisplayed(), "Subscription name isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionPriceDisplayed(), "Subscription price isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionQuantityDisplayed(), "Subscription quantity isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionFrequencyDisplayed(), "Subscription frequency isn't displayed");

    logger.info(
        "Verify list of subscriptions should display with Schedule Date, Send Now, Change Date, Skip Order");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionScheduleDateDisplayed(),
        "Subscription Schedule Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSendNowButtonDisplayed(),
        "Subscription Send Now isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionChangeDateDisplayed(),
        "Subscription Change Date isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSkipOrderDisplayed(),
        "Subscription Skip Order isn't displayed");

    logger.info(
        "Verify list of subscriptions should display with Billing, Shipping, Total and Update Payment sections");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionBillingSectionDisplayed(),
        "Subscription Billing section isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionShippingSectionDisplayed(),
        "Subscription Shipping section isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionTotalSectionDisplayed(),
        "Subscription Total section isn't displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionUpdatePaymentSectionDisplayed(),
        "Subscription Update Payment section isn't displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT},
      description = "11102930")
  public void myAccountPeetsCardTest() {
    // Todo: Missing validations, needs to be able to add peets cards
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in Peet's Card");
    MyCardsPage myCardsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.PEETS_CARDS);

    logger.info("5. Validate Peet's Card section");
    Assert.assertFalse(myCardsPage.getCardNumber().isEmpty(), "Card number is not displayed");
    Assert.assertFalse(myCardsPage.getCardBalance().isEmpty(), "Card balance is not displayed");
    Assert.assertTrue(myCardsPage.isReloadButtonDisplayed(), "Reload button is not displayed");
    Assert.assertTrue(myCardsPage.isRemoveButtonDisplayed(), "Remove button is not displayed");

    logger.info("5. Click in Register Peet's Card");
    RegisterPeetCardComponent registerPeetCardComponent = myCardsPage.clickRegisterPeetsCard();
    Assert.assertNotNull(registerPeetCardComponent, "Register New Card View is not displayed");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.REGISTER_CARD_URL),
        "Register New Card URL is not correct");

    logger.info("7. Press Browser back button");
    WebHelper.navigateBack(MyAccountPage.class);

    logger.info("8. Click in Buy Peet's Card");
    GiftCardsPage giftCardsPage = myCardsPage.clickBuyPeetsCard();
    Assert.assertNotNull(giftCardsPage, "Buy New Card View is not displayed");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.BUY_CARD_URL),
        "Buy New Card URL is not correct");

    logger.info("9. Press Browser back button");
    if (WebHelper.isDesktop()) {
      WebHelper.navigateBack(MyCardsPage.class);
    } else {
      SdkHelper.getDriver().navigate().back();
      giftCardsPage.closeBannerButton(MyAccountPage.class);
    }

    logger.info("10. Open reload view");
    ReloadComponent reloadComponent = myCardsPage.clickReloadButton();
    Assert.assertNotNull(reloadComponent, "Reload modal is not displayed");

    logger.info("11. Close Modal");
    reloadComponent.closeReloadModal();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT},
      description = "11102927")
  public void myAccountMySubscriptionAddItemTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click on Add item");
    ProductListPage productListPage = myAccountPage.clickAddItem();

    logger.info("Verify All coffee page is displayed");
    Assert.assertNotNull(productListPage, "Failed to navigate to All Coffee Page");
    Assert.assertEquals(
        productListPage.getPageHeader(),
        MyAccountTestData.ALL_COFFEE_HEADER,
        "All Coffee header isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT, TestNGGroups.SMOKE},
      description = "11102928")
  public void myAccountMySubscriptionUpdatePaymentTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in My subscription.");
    myAccountPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.MY_SUBSCRIPTIONS);

    logger.info("5. Click on Update payment information and check it is updated");
    myAccountPage.clickUpdatePaymentInformation().updatePaymentData();
    MyAccountTestsHelper.isPaymentInformationUpdated();
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.MY_ACCOUNT},
      description = "11102929")
  public void myAccountOrderHistoryElementsTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in Order history");
    MyAccountOrderHistoryPage orderHistoryPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.ORDER_HISTORY);

    logger.info("Verify list of orders is displayed");
    Assert.assertTrue(
        orderHistoryPage.orderHistoryItemIsDisplayed(), "The list of orders isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION},
      description = "11107447")
  public void dashboardDifferentSectionsTest() {
    // Todo: Missing sections [Peets card/Peetnik Rewards]
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(mail);
    signInPage.enterPassword(Constants.TestData.WEB_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("Validating Dashboard sections");
    Assert.assertEquals(
        myAccountPage.getRecentOrdersTitle(),
        Constants.DashboardTestData.RECENT_ORDERS_HEADER.toLowerCase(),
        "Recent Orders section is not displayed");

    Assert.assertEquals(
        myAccountPage.getMySubscriptionTitle(),
        Constants.DashboardTestData.MY_SUBSCRIPTION_HEADER.toLowerCase(),
        "My Subscriptions section is not displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED},
      description = "11102918")
  public void dashboardUIelementsTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(Constants.CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(Constants.CheckoutUserTestData.PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();
    softAssert.assertNotNull(myAccountPage, "My account page isn't displayed");
    softAssert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("Verify table title");
    softAssert.assertEquals(
        myAccountPage.getRecentOrdersTitle(),
        Constants.DashboardTestData.RECENT_ORDERS_HEADER.toLowerCase(),
        "Table title isn't correct");

    softAssert.assertEquals(
        myAccountPage.getMyOrderItemList().size(), 3, "Total recent orders does not match");

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();

    softAssert.assertTrue(
        myOrderItemComponent.isOrderNumberDisplayed(), "Order number isn't displayed");
    softAssert.assertTrue(
        myOrderItemComponent.isOrderPriceDisplayed(), "Order Price isn't displayed");

    logger.info("5. Click in view all orders");
    OrderHistoryPage orderHistoryPage = myAccountPage.viewAllOrders();

    logger.info("Verify that order history should display");
    softAssert.assertTrue(orderHistoryPage.isDisplayed(), "Order history page didn't appear");

    logger.info("Verify View all orders URL");
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.RECENT_ORDERS_URL),
        "URL does not matches");

    logger.info("Verify that Order #, Date order, Order status and Total should display");
    if (WebHelper.isDesktop()) {
      softAssert.assertEquals(
          orderHistoryPage.getTableTitleList(),
          Constants.DashboardTestData.MY_ORDERS_TABLE_TITLES,
          "Table titles are not correct");
    }

    logger.info("Verify that each of orders are fully displayed");
    softAssert.assertTrue(
        orderHistoryPage.getMyOrderItemList().size() > 0, "My orders table is empty");
    softAssert.assertTrue(
        orderHistoryPage.isMyOrdersTableFullyDisplayed(), "My orders table is n't fully displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION},
      description = "11118664")
  public void dashboardRecentOrdersTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(Constants.TestData.USER_EMAIL_WITH_SUBSCRIPTIONS);
    signInPage.enterPassword(TestData.WEB_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();
    softAssert.assertNotNull(myAccountPage, "My account page isn't displayed");
    softAssert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");

    logger.info("Verify table title");
    softAssert.assertEquals(
        myAccountPage.getRecentOrdersTitle(),
        Constants.DashboardTestData.RECENT_ORDERS_HEADER.toLowerCase(),
        "Table title isn't correct");

    softAssert.assertEquals(
        myAccountPage.getMyOrderItemList().size(), 3, "Total recent orders does not match");

    logger.info("4. Click on Details");
    MyOrderItemComponent myOrderItemComponent = myAccountPage.getMyOrderItemList().get(0);
    myOrderItemComponent = myOrderItemComponent.clickOnDetails();

    softAssert.assertTrue(
        myOrderItemComponent.isOrderNumberDisplayed(), "Order number isn't displayed");
    softAssert.assertTrue(
        myOrderItemComponent.isOrderPriceDisplayed(), "Order Price isn't displayed");

    logger.info("5. Click in view all orders");
    OrderHistoryPage orderHistoryPage = myAccountPage.viewAllOrders();

    logger.info("Verify that order history should display");
    softAssert.assertTrue(orderHistoryPage.isDisplayed(), "Order history page didn't appear");

    logger.info("Verify View all orders URL");
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.RECENT_ORDERS_URL),
        "URL does not matches");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT},
      description = "11102933")
  public void referralsElementsTest() {
    if (WebHelper.isDesktop()) {
      MyAccountPage myAccountPage =
          MyAccountTestsHelper.navigateToMyAccountPage(
              navigateToSignInPage(),
              TestData.USER_EMAIL_WITH_SUBSCRIPTIONS,
              TestData.WEB_PASSWORD);

      logger.info("4. Click on Referrals");
      ReferralsPage referralsPage =
          myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.REFERRALS);

      logger.info(
          "Verify Referrals title, image, links, Your pin..., Times shared, Friends referrals, Possible rewards and Rewards earned text should display.\n"
              + " email and link icons should display.");
      softAssert.assertEquals(
          referralsPage.getTitle().toLowerCase(),
          MyAccountTestData.REFERRALS_TITLE_HEADER.toLowerCase(),
          "Title isn't correct");
      softAssert.assertTrue(referralsPage.isEmailButtonDisplayed(), "Email button isn't displayed");
      softAssert.assertTrue(referralsPage.isCopyButtonDisplayed(), "Copy button isn't displayed");

      logger.info("5. Validate stats");
      softAssert.assertEquals(
          referralsPage.getListOfStats(),
          MyAccountTestData.REFERRALS_STATS,
          "Not all stats are displayed");

      referralsPage
          .getListOfStatsValues()
          .forEach(x -> softAssert.assertFalse(x.isEmpty(), "Stats value is empty in index: " + x));

      softAssert.assertTrue(referralsPage.isTrackTableDisplayed(), "Track table is not displayed");

      softAssert.assertTrue(
          referralsPage.isStartSharingButtonDisplayed(), "Start sharing button is not displayed");

      softAssert.assertAll();
    }
  }
}
