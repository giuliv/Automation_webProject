package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.*;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.new_web.components.MyAccountLeftMenu;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CreateAccountPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.MyAccountPage;
import com.applause.auto.new_web.views.PasswordRecoveryPage;
import com.applause.auto.new_web.views.ResetPasswordPage;
import com.applause.auto.new_web.views.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MyAccountTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102911")
  public void loginWithInvalidAccountTest() {

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter invalid credentials.");
    signInPage.enterEmail(WebTestData.NOT_EXIST_EMAIL);
    signInPage.enterPassword(WebTestData.PASSWORD);
    signInPage = signInPage.clickOnSignInButton(SignInPage.class);
    Assert.assertTrue(signInPage.errorMessageIsDisplayed(), "Error message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102912")
  public void forgotPasswordTest() {

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Forgot password?");
    ResetPasswordPage resetPasswordPage = signInPage.clickForgotPasswordLink();
    Assert.assertNotNull(resetPasswordPage, "Failed to navigate to the Reset password page.");

    logger.info("3. Enter email.");
    resetPasswordPage.enterEmail(Constants.Mail.Mail6.getValue());

    logger.info("4. Click on Submit");
    PasswordRecoveryPage passwordRecoveryPage = resetPasswordPage.clickSubmitButton();
    Assert.assertTrue(
        passwordRecoveryPage.isSuccessfulMessageDisplayed(Constants.Mail.Mail6.getValue()),
        "Successful message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102913")
  public void loginWithValidAccountTest() {

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102914")
  public void loginValidMenuTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102915")
  public void logoutTest() {

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102916")
  public void invalidSignUpTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
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
      groups = {TestNGGroups.NEW_WEB_CASES, TestNGGroups.MY_ACCOUNT},
      description = "11102917")
  public void validSignUpTest() {

    logger.info("1. Navigate to Sign in page");
    navigateToHome();
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
}
