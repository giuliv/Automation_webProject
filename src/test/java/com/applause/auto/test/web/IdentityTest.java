package com.applause.auto.test.web;

import static com.applause.auto.common.data.Constants.TestData.PASSWORD;
import static com.applause.auto.common.data.Constants.TestData.PASSWORD_BAD_FORMAT;
import static com.applause.auto.common.data.Constants.TestData.PEETS_FORGOT_PASSWORD_USERNAME;
import static com.applause.auto.common.data.Constants.TestData.UNRECOGNIZED_USERNAME_AND_PASSWORD_MESSAGE;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.CheckoutUserTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.Header;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.CreateAccountPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.PasswordRecoveryPage;
import com.applause.auto.web.views.PasswordRecoveryResetPage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ResetPasswordPage;
import com.applause.auto.web.views.SearchResultsPage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class IdentityTest extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11102911")
  public void loginWithInvalidAccountTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter invalid credentials.");
    signInPage.enterEmail(WebTestData.NOT_EXIST_EMAIL);
    signInPage.enterPassword(WebTestData.PASSWORD);
    signInPage = signInPage.clickOnSignInButton(SignInPage.class);
    Assert.assertTrue(signInPage.errorMessageIsDisplayed(), "Error message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11102912")
  public void forgotPasswordTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Forgot password?");
    ResetPasswordPage resetPasswordPage = signInPage.clickForgotPasswordLink();
    Assert.assertNotNull(resetPasswordPage, "Failed to navigate to the Reset password page.");

    logger.info("3. Enter email.");
    resetPasswordPage.enterEmail(WebTestData.RECOVER_EMAIL);

    logger.info("4. Click on Submit");
    PasswordRecoveryPage passwordRecoveryPage = resetPasswordPage.clickSubmitButton();
    Assert.assertTrue(
        passwordRecoveryPage.isSuccessfulMessageDisplayed(WebTestData.RECOVER_EMAIL),
        "Successful message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY, TestNGGroups.SMOKE},
      description = "11102913")
  public void loginWithValidAccountTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();
    Assert.assertNotNull(myAccountPage, "My account page isn't displayed");
    Assert.assertTrue(
        myAccountPage.getWelcomeMessage().contains("welcome"),
        "User is not signed in or welcome name is wrong");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11102915")
  public void logoutTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on LogOut");
    HomePage homePage = myAccountPage.getLeftMenu().clickLogoutButton();
    Assert.assertTrue(homePage.getHeader().isSignInButtonDisplayed(), "User isn't logged out");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11102916")
  public void invalidSignUpTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Create an account");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");

    logger.info("3. Click on Create");
    createAccountPage = createAccountPage.clickCreateAccountButton(CreateAccountPage.class);
    softAssert.assertTrue(
        createAccountPage.isFirstNameFieldErrorMessageDisplayed(),
        "Error message isn't displayed under First name field");
    softAssert.assertTrue(
        createAccountPage.isLastNameFieldErrorMessageDisplayed(),
        "Error message isn't displayed under Last name field");
    softAssert.assertTrue(
        createAccountPage.isEmailFieldErrorMessageDisplayed(),
        "Error message isn't displayed under Email field");
    softAssert.assertTrue(
        createAccountPage.isPasswordFieldErrorMessageDisplayed(),
        "Error message isn't displayed under Password field");
    softAssert.assertTrue(
        createAccountPage.isPasswordConfirmationFieldErrorMessageDisplayed(),
        "Error message isn't displayed under Password confirmation field");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY, TestNGGroups.SMOKE},
      description = "11102917")
  public void validSignUpTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Create an account");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");

    logger.info("3. Click on Create");
    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            WebTestData.FIRST_NAME,
            WebTestData.LAST_NAME,
            WebHelper.getRandomMail(),
            TestData.WEB_PASSWORD,
            TestData.WEB_PASSWORD);

    Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("welcome"), "User is not created");
  }

  @Test(
      groups = {TestNGGroups.IDENTITY, TestNGGroups.FRONT_END_REGRESSION},
      description = "11108422")
  public void loginExistingAccountCheckoutPageWithStandardItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

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
        WebHelper.doesPageUrlContainExpectedParameter(TestData.LOGIN_PAGE_URL),
        "Wrong URL is displayed");

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
      groups = {TestNGGroups.IDENTITY, TestNGGroups.FRONT_END_REGRESSION},
      description = "11107410")
  public void loginExistingAccountCheckoutPageWithSubscriptionItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

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
        WebHelper.doesPageUrlContainExpectedParameter(TestData.LOGIN_PAGE_URL),
        "Wrong URL is displayed");

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
      groups = {TestNGGroups.IDENTITY, TestNGGroups.FRONT_END_REGRESSION},
      description = "11108423")
  public void loginCreateNewAccountCheckoutPageWithStandardItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

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
        WebHelper.doesPageUrlContainExpectedParameter(TestData.LOGIN_PAGE_URL),
        "Wrong URL is displayed");

    logger.info("7. At Login Page page --> Click on 'Create Account'");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();

    logger.info("8. Validate the user is directed to https://account-qa.peets.com/Registration");
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");
    Assert.assertTrue(
        WebHelper.doesPageUrlContainExpectedParameter(TestData.REGISTRATION_PAGE_URL),
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
      groups = {TestNGGroups.IDENTITY, TestNGGroups.FRONT_END_REGRESSION},
      description = "11108424")
  public void loginCreateNewAccountCheckoutPageWithSubscriptionItemTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

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
        WebHelper.doesPageUrlContainExpectedParameter(TestData.LOGIN_PAGE_URL),
        "Wrong URL is displayed");

    logger.info("7. At Login Page page --> Click on 'Create Account'");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();

    logger.info("8. Validate the user is directed to https://account-qa.peets.com/Registration");
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");
    Assert.assertTrue(
        WebHelper.doesPageUrlContainExpectedParameter(TestData.REGISTRATION_PAGE_URL),
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
      groups = {TestNGGroups.IDENTITY, TestNGGroups.FRONT_END_REGRESSION},
      description = "11107413")
  public void logoutFromMyCheckoutContactInformationSectionTest() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Validate the user is shown with Person ICON at Right corner");
    Header header = homePage.getHeader();
    Assert.assertTrue(header.isPersonIconDisplayed(), "The person icon isn't displayed");

    logger.info("3. Click on 'Person' Icon");
    SignInPage signInPage = header.clickAccountButton();

    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.getSyncHelper().sleep(5000); // Extra wait needed on iOS
    }

    logger.info("4. Validate the user is directed to https://account-qa.peets.com/login");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.LOGIN_PAGE_URL), "Wrong URL is displayed");

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

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11107414")
  public void myAccountNonExistingEmailTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter invalid credentials.");
    signInPage.enterEmail(WebTestData.NOT_EXIST_EMAIL);
    signInPage.enterPassword(WebTestData.PASSWORD);
    signInPage = signInPage.clickOnSignInButton(SignInPage.class);
    Assert.assertEquals(
        signInPage.getErrorMessage(),
        UNRECOGNIZED_USERNAME_AND_PASSWORD_MESSAGE,
        "Wrong message displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11107416")
  public void myAccountIncorrectPasswordTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter invalid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword("Incorrectpassword9");
    signInPage = signInPage.clickOnSignInButton(SignInPage.class);
    Assert.assertEquals(
        signInPage.getErrorMessage(),
        UNRECOGNIZED_USERNAME_AND_PASSWORD_MESSAGE,
        "Wrong message displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.IDENTITY},
      description = "11107417")
  public void myAccountInvalidForgotPasswordTest() {
    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Forgot password");
    ResetPasswordPage resetPasswordPage = signInPage.clickForgotPasswordLink();
    Assert.assertNotNull(resetPasswordPage, "Failed to navigate to the Reset password page.");

    logger.info("3. Enter email.");
    resetPasswordPage.enterEmail(PEETS_FORGOT_PASSWORD_USERNAME);

    logger.info("4. Click on Submit");
    PasswordRecoveryPage passwordRecoveryPage = resetPasswordPage.clickSubmitButton();
    Assert.assertTrue(
        passwordRecoveryPage.isSuccessfulMessageDisplayed(PEETS_FORGOT_PASSWORD_USERNAME));

    logger.info("5. Navigate to th Url received in email");
    PasswordRecoveryResetPage recoveryResetPage = passwordRecoveryPage.navigateRecoveryUrl();

    logger.info(
        "6. Key in new and repeat password mismatch fields and click on \"Reset Password\" Validate user is shown with password mismatch error");
    recoveryResetPage =
        recoveryResetPage
            .setPassword(PASSWORD)
            .setConfirmPassword(PASSWORD + PASSWORD)
            .submit(PasswordRecoveryResetPage.class);

    Assert.assertTrue(
        recoveryResetPage.isPasswordMismatchErrorDisplayed(),
        "Password mismatch error does not displayed");

    logger.info(
        "7. Key in new and repeat password incorrect password format in fields and click on \"Reset Password\"");
    recoveryResetPage =
        recoveryResetPage
            .setPassword(PASSWORD_BAD_FORMAT)
            .setConfirmPassword(PASSWORD_BAD_FORMAT)
            .submit(PasswordRecoveryResetPage.class);
    Assert.assertTrue(
        recoveryResetPage.isPasswordBadFormatErrorDisplayed(),
        "Password bad format error does not displayed");
  }
}
