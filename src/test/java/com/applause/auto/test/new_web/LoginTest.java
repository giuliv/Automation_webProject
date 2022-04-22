package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.CheckoutUserTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.CreateAccountPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.new_web.views.SignInPage;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.LOGIN},
      description = "")
  public void loginExistingAccountCheckoutPageWithStandardItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Go to Shopify stage --> Add Standard item to cart");
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info("3. Go to Cart page --> Click on 'Checkout'");
    CheckOutPage checkoutPage = productDetailsPage.clickAddToCartPage().clickContinueToCheckOut();

    logger.info("4. Validate the user is directed to checkout");
    Assert.assertTrue(checkoutPage.isDisplayed(), "The user isn't directed to checkout");
    Assert.assertTrue(
        checkoutPage.isContactInformationSectionDisplayed(),
        "Contact information section isn't displayed");

    logger.info("5. At Contact information section --> click on Already have an account?Log in");
    List<String> productsNames = checkoutPage.getProductsNames();
    SignInPage signInPage = checkoutPage.clickLogin();

    logger.info("6. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_LOGIN_PAGE_URL), "Wrong URL is displayed");

    logger.info("7. Enter Email & Password and click on 'Sign in'");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);
    checkoutPage = signInPage.clickOnSignInButton(CheckOutPage.class);

    logger.info("8. Validate the user logged in and items are still in cart");
    Assert.assertTrue(checkoutPage.isUserLoggedIn(), "The user isn't logged in");
    Assert.assertEquals(
        checkoutPage.getProductsNames(),
        productsNames,
        "Product items has benn changed after logged in");
  }

  @Test(
      groups = {TestNGGroups.LOGIN},
      description = "")
  public void loginExistingAccountCheckoutPageWithSubscriptionItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Go to Shopify stage --> Add Subscription item to cart");
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);
    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Validate the item is added to cart");
    softAssert.assertTrue(
        miniCart.isSubscribeButtonEnabled(),
        "Subscribe is not the default selection, and it was expected to be.");

    logger.info("3. Go to Cart page --> Click on 'Checkout'");
    CheckOutPage checkOutPage = miniCart.clickViewCartButton().clickContinueToCheckOut();

    logger.info("4. Validate the user is directed to Checkout page");
    Assert.assertNotNull(checkOutPage, "The user isn't directed to Checkout page");

    logger.info("5. At Contact information section --> click on Already have an account?Log in");
    List<String> productsNames = checkOutPage.getProductsNames();
    SignInPage signInPage = checkOutPage.clickLogin();

    logger.info("6. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_LOGIN_PAGE_URL), "Wrong URL is displayed");

    logger.info("7. Enter Email & Password and click on 'Sign in'");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);
    checkOutPage = signInPage.clickOnSignInButton(CheckOutPage.class);

    logger.info("8. Validate the user logged in and items are still in cart");
    Assert.assertTrue(checkOutPage.isUserLoggedIn(), "The user isn't logged in");
    Assert.assertEquals(
        checkOutPage.getProductsNames(),
        productsNames,
        "Product items has benn changed after logged in");
  }

  @Test(
      groups = {TestNGGroups.LOGIN},
      description = "")
  public void loginCreateNewAccountCheckoutPageWithStandardItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Go to Shopify stage --> Add Standard item to cart");
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info("3. Go to Cart page --> Click on 'Checkout'");
    CheckOutPage checkoutPage = productDetailsPage.clickAddToCartPage().clickContinueToCheckOut();

    logger.info("4. Validate the user is directed to checkout");
    Assert.assertTrue(checkoutPage.isDisplayed(), "The user isn't directed to checkout");
    Assert.assertTrue(
        checkoutPage.isContactInformationSectionDisplayed(),
        "Contact information section isn't displayed");

    logger.info("5. At Contact information section --> click on Already have an account?Log in");
    List<String> productsNames = checkoutPage.getProductsNames();
    SignInPage signInPage = checkoutPage.clickLogin();

    logger.info("6. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_LOGIN_PAGE_URL), "Wrong URL is displayed");

    logger.info("7. At Login Page page --> Click on 'Create Account'");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();

    logger.info("8. Validate the user is directed to https://account-qa.peets.com/Registration");
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_REGISTRATION_PAGE_URL),
        "Wrong Registration URL is displayed");

    logger.info("9. At Create Account Page --> Key in fields with valid data and Click on Create");
    checkoutPage =
        createAccountPage.createAccount(
            WebTestData.FIRST_NAME,
            WebTestData.LAST_NAME,
            WebHelper.getRandomMail(),
            TestData.WEB_PASSWORD,
            TestData.WEB_PASSWORD,
            CheckOutPage.class);

    logger.info("10. Validate the user logged in and items are still in cart");
    Assert.assertTrue(checkoutPage.isUserLoggedIn(), "The user isn't logged in");
    Assert.assertEquals(
        checkoutPage.getProductsNames(),
        productsNames,
        "Product items has benn changed after logged in");
  }

  @Test(
      groups = {TestNGGroups.LOGIN},
      description = "")
  public void loginCreateNewAccountCheckoutPageWithSubscriptionItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Go to Shopify stage --> Add Subscription item to cart");
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);
    productDetailsPage.selectSubscribeType();
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info("3. Validate the item is added to cart");
    softAssert.assertTrue(
        miniCart.isSubscribeButtonEnabled(),
        "Subscribe is not the default selection, and it was expected to be.");

    logger.info("4. Go to Cart page --> Click on 'Checkout'");
    CheckOutPage checkOutPage = miniCart.clickViewCartButton().clickContinueToCheckOut();

    logger.info("4. Validate the user is directed to checkout");
    Assert.assertTrue(checkOutPage.isDisplayed(), "The user isn't directed to checkout");
    Assert.assertTrue(
        checkOutPage.isContactInformationSectionDisplayed(),
        "Contact information section isn't displayed");

    logger.info("5. At Contact information section --> click on Already have an account?Log in");
    List<String> productsNames = checkOutPage.getProductsNames();
    SignInPage signInPage = checkOutPage.clickLogin();

    logger.info("6. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_LOGIN_PAGE_URL), "Wrong URL is displayed");

    logger.info("7. At Login Page page --> Click on 'Create Account'");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();

    logger.info("8. Validate the user is directed to https://account-qa.peets.com/Registration");
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_REGISTRATION_PAGE_URL),
        "Wrong Registration URL is displayed");

    logger.info("9. At Create Account Page --> Key in fields with valid data and Click on Create");
    checkOutPage =
        createAccountPage.createAccount(
            WebTestData.FIRST_NAME,
            WebTestData.LAST_NAME,
            WebHelper.getRandomMail(),
            TestData.WEB_PASSWORD,
            TestData.WEB_PASSWORD,
            CheckOutPage.class);

    logger.info("10. Validate the user logged in and items are still in cart");
    Assert.assertTrue(checkOutPage.isUserLoggedIn(), "The user isn't logged in");
    Assert.assertEquals(
        checkOutPage.getProductsNames(),
        productsNames,
        "Product items has benn changed after logged in");
  }

  @Test(
      groups = {TestNGGroups.LOGIN},
      description = "")
  public void logoutFromMyCheckoutContactInformationSectionTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");

    logger.info("2. Validate the user is shown with Person ICON at Right corner");
    Header header = homePage.getHeader();
    Assert.assertTrue(header.isPersonIconDisplayed(), "The person icon isn't displayed");

    logger.info("3. Click on 'Person' Icon");
    SignInPage signInPage = header.clickAccountButton();

    logger.info("4. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.QA_LOGIN_PAGE_URL), "Wrong URL is displayed");

    logger.info("5. At Login Page page -->Key in Valid Email & Password and click on 'Sign in'");
    signInPage.enterEmail(CheckoutUserTestData.USERNAME);
    signInPage.enterPassword(CheckoutUserTestData.PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton(MyAccountPage.class);

    logger.info("6. Validate the user is directed to User Dashboard successfully");
    Assert.assertNotNull(myAccountPage, "User Dashboard page isn't displayed");

    logger.info("7. Add item to cart and click on Checkout");
    SearchResultsPage searchResultsPage =
        myAccountPage
            .getHeader()
            .getSearchComponent()
            .search(Constants.WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);
    CheckOutPage checkoutPage = productDetailsPage.clickAddToCartPage().clickContinueToCheckOut();

    logger.info(
        "8. Validate the user is directed to Contact information section with customer email and logout link");
    Assert.assertTrue(
        checkoutPage.isContactInformationSectionDisplayed(),
        "Contact information section isn't displayed");
    Assert.assertTrue(
        checkoutPage.isCustomerEmailAddressDisplayed(CheckoutUserTestData.USERNAME),
        "Customer email address isn't displayed");
    Assert.assertTrue(checkoutPage.isUserLoggedIn(), "Logout link isn't displayed");

    logger.info("9. Click on 'Logout' link");
    homePage = checkoutPage.clickLogout();

    logger.info("10. Validate the user is logged out successfully");
    Assert.assertTrue(homePage.getHeader().isUserLoggedOut(), "User isn't logged out");
  }
}
