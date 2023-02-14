package com.applause.auto.test.web.my_account;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.CheckoutUserTestData;
import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.MyOrderDto;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.test.web.BaseTest;
import com.applause.auto.web.components.MyAccountLeftMenu;
import com.applause.auto.web.components.QuickViewComponent;
import com.applause.auto.web.components.RegisterPeetCardComponent;
import com.applause.auto.web.components.ReloadComponent;
import com.applause.auto.web.components.UpdateSubscriptionPaymentChunk;
import com.applause.auto.web.components.WelcomeBackDialogComponent;
import com.applause.auto.web.components.WelcomeBackDialogRecommendedForYouComponent;
import com.applause.auto.web.components.my_account.MyOrderItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CartPage;
import com.applause.auto.web.views.ContactUsPage;
import com.applause.auto.web.views.CreateAccountPage;
import com.applause.auto.web.views.GiftCardsPage;
import com.applause.auto.web.views.PasswordRecoveryPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.web.views.ResetPasswordPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.my_account.MyAccountEmailPreferencesPage;
import com.applause.auto.web.views.my_account.MyAccountOrderHistoryPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import com.applause.auto.web.views.my_account.MyAccountPeetnikRewardsPage;
import com.applause.auto.web.views.my_account.MyAccountSettingsPage;
import com.applause.auto.web.views.my_account.MyCardsPage;
import com.applause.auto.web.views.my_account.OrderHistoryPage;
import com.applause.auto.web.views.my_account.ReferralsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MyAccountTests extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.SANITY,
        TestNGGroups.FE_PROD_REGRESSION
      },
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

    logger.info("Verify Cancel Subscription button is displayed");
    softAssert.assertTrue(
        myAccountPage.isCancelSubscriptionDisplayed(),
        "Cancel Subscription button is not displayed");

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
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11102930")
  public void myAccountPeetsCardTest() {
    // Todo: Missing validations, needs to be able to add peets cards
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);

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
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FE_PROD_REGRESSION},
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
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
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

      logger.info("Verify the user is directed to Referrals page");
      softAssert.assertTrue(
          referralsPage.isDisplayed(), "The user is not directed to Referrals page");

      logger.info(
          "Verify below information is shown for the customer Refer a Friend Banner and Email, Copy links");
      softAssert.assertTrue(
          referralsPage.isReferFriendBannerDisplayed(),
          "The customer Refer a Friend Banner is not displayed");
      softAssert.assertTrue(referralsPage.isEmailButtonDisplayed(), "Email button isn't displayed");
      softAssert.assertTrue(referralsPage.isCopyButtonDisplayed(), "Copy button isn't displayed");

      logger.info(
          "Verify Times Shared info, Possible Rewards info, Friends Referred info, Rewards Earned info stats are displayed");
      softAssert.assertEquals(
          referralsPage.getListOfStats(),
          MyAccountTestData.REFERRALS_STATS,
          "Not all stats are displayed");

      referralsPage
          .getListOfStatsValues()
          .forEach(x -> softAssert.assertFalse(x.isEmpty(), "Stats value is empty in index: " + x));

      logger.info("Verify Keep track of your shares & rewards is displayed");
      softAssert.assertTrue(referralsPage.isTrackTableDisplayed(), "Track table is not displayed");

      logger.info("Verify Starting Sharing button is displayed");
      softAssert.assertTrue(
          referralsPage.isStartSharingButtonDisplayed(), "Start sharing button is not displayed");

      softAssert.assertAll();
    }
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11107448")
  public void myAccountDashboardPeetsCardTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), MyAccountTestData.EDIT_EMAIL, MyAccountTestData.EDIT_EMAIL_PWD);

    logger.info("Validate Peets Gift card balance is displayed");
    softAssert.assertTrue(
        myAccountPage.isPeetsGiftCardBalanceDisplayed(),
        "Peets Gift card balance is not displayed");

    logger.info("Validate Auto Reload ON/OFF is displayed");
    softAssert.assertTrue(
        myAccountPage.isAutoReloadOnOffDisplayed(), "Auto Reload ON/OFF is not displayed");

    logger.info("Validate Reload card button is displayed");
    softAssert.assertTrue(
        myAccountPage.isReloadButtonDisplayed(), "Reload card button is not displayed");

    logger.info("4. At My Account page --> Peets Card section --> Click on Reload Card");
    ReloadComponent reloadComponent = myAccountPage.clickReloadButton();
    Assert.assertNotNull(reloadComponent, "Reload modal is not displayed");

    logger.info("5. At My Account page --> Recent Orders  section --> Click on 'View all orders'");
    reloadComponent.closeReloadModal();
    OrderHistoryPage orderHistoryPage = myAccountPage.clickViewAllOrders();

    logger.info("Validate the user is directed to My Orders section of the user 'account#/orders'");
    softAssert.assertTrue(
        orderHistoryPage.isDisplayed(), "The user is not directed to My Orders section");
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.RECENT_ORDERS_URL),
        "My Orders URL does not matches");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127066")
  public void myAccountWelcomeBackWindowAddButtonTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), MyAccountTestData.EDIT_EMAIL, MyAccountTestData.EDIT_EMAIL_PWD);

    logger.info("Validate a Welcome back window is shown just below the Navigation");
    WelcomeBackDialogComponent welcomeBackDialogComponent =
        myAccountPage.getWelcomeBackDialogComponent();
    softAssert.assertTrue(
        welcomeBackDialogComponent.isDisplayed(), "Welcome back window is not shown");

    logger.info("Validate 'Welcome Back, 'Username' is shown correctly");
    softAssert.assertEquals(
        welcomeBackDialogComponent.getTitle().toUpperCase(),
        "WELCOME BACK,APPLAUSE.",
        " 'Welcome Back, 'Username' is not shown correctly");

    logger.info(
        "Validate At Welcome back Window -> Two Recommended for you sections are displayed");
    softAssert.assertTrue(
        welcomeBackDialogComponent.areTwoRecommendedForYouSectionsDisplayed(),
        "Two Recommended for you sections are not displayed");

    logger.info("Validate both section shows any item with Image, name, Price and ADD Button");
    WelcomeBackDialogRecommendedForYouComponent firstSection =
        welcomeBackDialogComponent.getFirstRecommendedForYouSection();
    softAssert.assertTrue(
        firstSection.isSectionContainsAllDetails(),
        "Not all expected details are displayed for the first section");
    softAssert.assertTrue(
        welcomeBackDialogComponent
            .getSecondRecommendedForYouSection()
            .isSectionContainsAllDetails(),
        "Not all expected details are displayed for the second section");

    logger.info("4. Click on 'ADD' on one section");
    String firstName = firstSection.getName().toLowerCase();
    QuickViewComponent quickViewComponent = firstSection.clickAdd();

    logger.info("Validate this item is added to cart correctly");
    softAssert.assertEquals(
        quickViewComponent.getProductName().toLowerCase(),
        firstName,
        "Wrong product name is displayed");

    logger.info("5. Close Quick view popup");
    quickViewComponent.closeQuickView();

    logger.info("6. Click on 'ADD' on another section ");
    WelcomeBackDialogRecommendedForYouComponent secondSection =
        welcomeBackDialogComponent.getSecondRecommendedForYouSection();
    String secondName = secondSection.getName().toLowerCase();
    quickViewComponent = secondSection.clickAdd();

    logger.info("Validate this item is added to cart correctly");
    softAssert.assertEquals(
        quickViewComponent.getProductName().toLowerCase(),
        secondName,
        "Wrong second product name is displayed");

    logger.info("5. Close Quick view popup");
    quickViewComponent.closeQuickView();

    logger.info("6. At Welcome back Window -> Click on 'X'");
    welcomeBackDialogComponent.close();

    logger.info("Validate the Window is closed");
    softAssert.assertFalse(welcomeBackDialogComponent.isDisplayed(), "Window is not closed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT},
      description = "11127065")
  public void myAccountWelcomeBackWindowReorderButtonTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), CheckoutUserTestData.USERNAME, CheckoutUserTestData.PASSWORD);

    logger.info(
        "Validate this section shows Fulfilled (Invoiced) order item is shown with Image, name, Price and  Reorder Button");
    MyOrderItemComponent myOrderItemComponent =
        myAccountPage.viewAllOrders().getMyOrderItemList().get(0);
    softAssert.assertTrue(
        myOrderItemComponent.isDisplayedCorrectly(), "The order item is not shown correctly");

    logger.info("5. At Welcome back Window -> Your Past Order Section -> Click on 'Reorder'");
    String accountUrl = WebHelper.getCurrentUrl();
    MyOrderDto orderDTO = myOrderItemComponent.clickOnDetails().getOrderDTO();
    CartPage cartPage = myOrderItemComponent.clickOnReorderButton();

    logger.info("Validate this item is added to cart correctly");
    softAssert.assertEquals(
        cartPage.getProductNameByIndex(1).toLowerCase(),
        orderDTO.getName().toLowerCase(),
        "Product name didn't match on Cart page");
    softAssert.assertEquals(
        cartPage.getGrindSelectedByIndex(1).toLowerCase(),
        orderDTO.getInfo().toLowerCase(),
        "Product Grind didn't match on Cart page");
    softAssert.assertEquals(
        cartPage.getProductPriceByIndex(1),
        orderDTO.getPrice(),
        "Product Price didn't match on Cart page");

    logger.info("Validate this section shows any item with Image, name, Price and ADD Button");
    myAccountPage = WebHelper.navigateToUrl(accountUrl, MyAccountPage.class);
    WelcomeBackDialogComponent welcomeBackDialogComponent =
        myAccountPage.getWelcomeBackDialogComponent();
    WelcomeBackDialogRecommendedForYouComponent firstSection =
        myAccountPage.getWelcomeBackDialogComponent().getFirstRecommendedForYouSection();
    softAssert.assertTrue(
        firstSection.isSectionContainsAllDetails(),
        "Not all expected details are displayed for the first section");
    softAssert.assertTrue(
        welcomeBackDialogComponent
            .getSecondRecommendedForYouSection()
            .isSectionContainsAllDetails(),
        "Not all expected details are displayed for the second section");

    logger.info("4. Click on 'ADD' on one section");
    String firstName = firstSection.getName().toLowerCase();
    QuickViewComponent quickViewComponent = firstSection.clickAdd();

    logger.info("Validate this item is added to cart correctly");
    softAssert.assertEquals(
        quickViewComponent.getProductName().toLowerCase(),
        firstName,
        "Wrong product name is displayed");

    logger.info("5. Close Quick view popup");
    quickViewComponent.closeQuickView();

    logger.info("6. Click on 'ADD' on another section ");
    WelcomeBackDialogRecommendedForYouComponent secondSection =
        welcomeBackDialogComponent.getSecondRecommendedForYouSection();
    String secondName = secondSection.getName().toLowerCase();
    quickViewComponent = secondSection.clickAdd();

    logger.info("Validate this item is added to cart correctly");
    softAssert.assertEquals(
        quickViewComponent.getProductName().toLowerCase(),
        secondName,
        "Wrong second product name is displayed");

    logger.info("5. Close Quick view popup");
    quickViewComponent.closeQuickView();

    logger.info("6. At Welcome back Window -> Click on 'X'");
    welcomeBackDialogComponent.close();

    logger.info("Validate the Window is closed");
    softAssert.assertFalse(welcomeBackDialogComponent.isDisplayed(), "Window is not closed");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127067")
  public void myAccountDashboardPeetnikRewardsSection() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(),
            MyAccountTestData.USER_WITH_PEETNIK_REWARDS,
            MyAccountTestData.PASSWORD_FOR_USER__WITH_PEETNIK_REWARDS);

    logger.info("4. At My Account page --> Dashboard -> click on Peetnik Rewards");
    MyAccountPeetnikRewardsPage peetnikRewardsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.PEETNIK_REWARDS);

    logger.info("Validate the user is directed to Peetnik rewards page");
    softAssert.assertTrue(peetnikRewardsPage.isTitleDisplayed(), "Title isn't displayed");

    logger.info("Validate there are two sections -> Peetnik reward banner and Peets card section");
    softAssert.assertTrue(
        peetnikRewardsPage.isPeetnikRewardsBannerDisplayed(),
        "Peetnik reward banner is not displayed");
    softAssert.assertTrue(
        peetnikRewardsPage.isPeetsCardsSectionDisplayed(), "Peets card section is not displayed");

    logger.info("Validate this section should show: Peets Gift card balance");
    softAssert.assertTrue(
        peetnikRewardsPage.isPeetsCardsBalanceDisplayed(),
        "Peets Gift card balance is not displayed");

    logger.info("Validate this section should show: Auto Reload ON/OFF");
    softAssert.assertTrue(
        peetnikRewardsPage.isPeetsCardsAutoReloadDisplayed(),
        "Auto Reload ON/OFF is not displayed");

    logger.info("Validate this section should show: Reload card button");
    softAssert.assertTrue(
        peetnikRewardsPage.isPeetsCardsReloadButtonDisplayed(),
        "Reload card button is not displayed");

    logger.info("5. At Peetnik Rewards page -> At Peets card section --> Click on Reload Card");
    ReloadComponent reloadComponent = peetnikRewardsPage.clickPeetsCardsReloadButton();
    Assert.assertNotNull(reloadComponent, "Reload modal is not displayed");

    logger.info("6. Close Modal");
    reloadComponent.closeReloadModal();

    logger.info("7. At Peetnik Rewards page -> At Peets card section  --> Click on 'Manage'");
    MyCardsPage myCardsPage = peetnikRewardsPage.clickPeetsCardsManageButton();

    logger.info("Validate the user is directed to Peets card page of the user");
    softAssert.assertNotNull(myCardsPage, "Peets card page is not displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.MY_ACCOUNT},
      description = "11127068")
  public void myAccountMySubscriptionValidationTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.USER_EMAIL_WITH_SUBSCRIPTIONS, TestData.WEB_PASSWORD);

    logger.info("4. Click in My subscription");
    myAccountPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.MY_SUBSCRIPTIONS);

    logger.info("Validate Ship date is mentioned on the subscription");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionScheduleDateDisplayed(),
        "Ship date is not mentioned on the subscription");

    logger.info("Validate subscription 'Send now' button is displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSendNowButtonDisplayed(),
        "Subscription Send Now isn't displayed");

    logger.info("Validate subscription Skip Order is displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionSkipOrderDisplayed(),
        "Subscription Skip Order isn't displayed");

    logger.info("Validate subscription Change Date is displayed");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionChangeDateDisplayed(),
        "Subscription Change Date isn't displayed");

    logger.info("Validate Product name is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionNameDisplayed(), "Subscription name isn't displayed");

    logger.info("Validate Grind is shown correctly");
    softAssert.assertEquals(
        myAccountPage.getMySubscriptionGrind(),
        GrindDropdown.WHOLE_BEAN.getValue(),
        "Grind value is wrong");

    logger.info("Validate the item total is shown correctly");
    softAssert.assertFalse(
        myAccountPage.expandDetailsSectionOnMobile().getMySubscriptionTotalAmount().isEmpty(),
        "The item total is not shown correctly");

    logger.info(
        "Validate User can click on Cancel subscription -> once clicked -> user can cancel the subscription successfully");
    softAssert.assertTrue(
        myAccountPage.isCancelSubscriptionDisplayed(),
        "Cancel subscription button is not displayed");

    logger.info(
        "Validate Sending dropdown shows qty values from 1-20 and able to update to desired qty");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionQuantityDisplayed(),
        "Subscription quantity dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.areSendingQuantityDropdownValuesDisplayed(),
        "Sending dropdown shows wrong quantity values");

    logger.info(
        "Validate Every dropdown shows 1-8 weeks and able to update to desired no of weeks");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionFrequencyDisplayed(),
        "Subscription Every dropdown isn't displayed");
    softAssert.assertFalse(
        myAccountPage.areSubscriptionFrequencyDropdownValuesDisplayed(),
        "Subscription Every dropdown doesn't show 1-8 weeks");

    logger.info("Validate Billing is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionBillingSectionDisplayed(),
        "Subscription Billing section isn't displayed");

    logger.info(
        "Validate on the banner, there is ADD ITEM CTA which direct user to https://peets-coffee-staging.myshopify.com/collections/all");
    softAssert.assertTrue(myAccountPage.isAddItemButtonDisplayed(), "Add Item isn't displayed");

    logger.info("Validate Shipping is shown correctly");
    softAssert.assertTrue(
        myAccountPage.isSubscriptionShippingSectionDisplayed(),
        "Subscription Shipping section isn't displayed");

    logger.info("Validate click on 'EDIT', Shows Change shipping address window");
    softAssert.assertTrue(
        myAccountPage.isEditShippingAddressModalDisplayed(), "Edit Shipping modal isn't displayed");
    myAccountPage.closeEditShippingAddressModal();

    logger.info("5. Click Update Payment information");
    UpdateSubscriptionPaymentChunk updateSubscriptionPaymentChunk =
        myAccountPage.clickUpdatePaymentInformation();

    logger.info("Validate user is directed to update payment information section");
    Assert.assertNotNull(
        updateSubscriptionPaymentChunk, "Update payment information section is not displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.MY_ACCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127069")
  public void myAccountSettingsTest() {
    MyAccountPage myAccountPage =
        MyAccountTestsHelper.navigateToMyAccountPage(
            navigateToSignInPage(), TestData.PEETS_USERNAME, TestData.PEETS_PASSWORD);

    logger.info("4. At Dashboard -> Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("Validate the user is directed to Settings section");
    softAssert.assertTrue(
        settingsPage.isAccountInformationTitleDisplayed(),
        "Account information title isn't displayed");

    logger.info("Validate name and email is matched with user");
    softAssert.assertEquals(
        settingsPage.getName(), TestData.ANDREW_TESTER, "Name isn't matched with user name");
    softAssert.assertEquals(
        settingsPage.getEmail(), TestData.PEETS_USERNAME, "Email isn't matched with user email");

    logger.info("Validate phone number and password should display.");
    softAssert.assertTrue(settingsPage.isPhoneNumberDisplayed(), "Phone number isn't displayed");
    softAssert.assertTrue(settingsPage.isPasswordDisplayed(), "Password isn't displayed");

    logger.info("Validate Edit Password | Edit Email Preferences");
    softAssert.assertTrue(
        settingsPage.isEditPasswordLinkDisplayed(), "Edit Password link isn't displayed");
    softAssert.assertTrue(
        settingsPage.isEditEmailPreferencesLinkDisplayed(),
        "Edit Email Preferences link isn't displayed");

    logger.info("Validate UPDATE SUBSCRIPTION SHIPPING ADDRESS");
    softAssert.assertTrue(
        settingsPage.isUpdateSubscriptionShippingAddressLinkDisplayed(),
        "UPDATE SUBSCRIPTION SHIPPING ADDRESS link isn't displayed");

    logger.info("Validate EDIT STANDARD ORDER ADDRESS BOOK");
    softAssert.assertTrue(
        settingsPage.isUpdateSubscriptionShippingAddressLinkDisplayed(),
        "EDIT STANDARD ORDER ADDRESS BOOK link isn't displayed");

    softAssert.assertAll();
  }
}
