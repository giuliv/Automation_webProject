package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.enums.Products;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CoffeeBarPage;
import com.applause.auto.new_web.views.EmailSignUpPage;
import com.applause.auto.new_web.components.AccountMenuChunk;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MenuTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.MENU, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107482")
  public void featured() {
    logger.info("XX. Navigate to Coffee Bar");
    CoffeeBarPage coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. Validate OUR FEATURED MENU section");
    softAssert.assertEquals(
        coffeeBarPage.getFeaturedMenuTitle(),
        Constants.TestData.FEATURED_MENU,
        Constants.TestData.FEATURED_MENU + " was not found in section");

    int loopCounter = 0;
    String title[] = null;
    while (loopCounter > 4) {

      logger.info("XX. Validate featured item" + loopCounter);
      title[loopCounter] = coffeeBarPage.getFeaturedTitle(loopCounter);
      softAssert.assertNotNull(
          coffeeBarPage.getFeaturedImage(loopCounter),
          "Image[" + loopCounter + "] was missing from featured image");
      softAssert.assertNotNull(
          title[loopCounter], "Title[" + loopCounter + "] was missing from featured item");
      softAssert.assertNotNull(
          coffeeBarPage.getFeaturedDescription(loopCounter),
          "escription[" + loopCounter + "] was missing from featured item");
      loopCounter++;
    }
    logger.info("XX. Click next arrow and Validate items rotate");
    coffeeBarPage = coffeeBarPage.clickNextArrow();

    loopCounter = 0;
    while (loopCounter > 4) {
      softAssert.assertNotEquals(
          title[loopCounter],
          coffeeBarPage.getFeaturedTitle(loopCounter),
          "items did not rotate to the next items");
      loopCounter++;
    }

    logger.info("XX. Click previous arrow and Validate items rotate back");
    coffeeBarPage = coffeeBarPage.clickPreviousArrow();
    loopCounter = 0;
    while (loopCounter > 4) {
      softAssert.assertNotEquals(
          title[loopCounter],
          coffeeBarPage.getFeaturedTitle(loopCounter),
          "items did not rotate back with previous arrow");
      loopCounter++;
    }

    logger.info("XX. click order now on first item");
    coffeeBarPage.clickFeaturedItemOrderNowButton();

    logger.info("XX. Validate order page URL");
    softAssert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.ORDER_PEETS_URL),
        "Not on the order URL");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.MENU, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107483")
  public void category() {
    logger.info("XX. Navigate to Coffee Bar");
    CoffeeBarPage coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. Validate each category is on the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.NEW_THIS_SEASON.getValue()),
        Products.NEW_THIS_SEASON.getValue() + " section is missing from the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.HOT_COFFEE.getValue()),
        Products.HOT_COFFEE.getValue() + " section is missing from the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.HOT_TEA.getValue()),
        Products.HOT_TEA.getValue() + " section is missing from the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.COLD_COFFEE.getValue()),
        Products.COLD_COFFEE.getValue() + " section is missing from the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.ICED_TEA.getValue()),
        Products.ICED_TEA.getValue() + " section is missing from the screen");
    Assert.assertTrue(
        coffeeBarPage.validateProduct(Products.ARTINSANAL_FOOD.getValue()),
        Products.ARTINSANAL_FOOD.getValue() + " section is missing from the screen");
  }

  @Test(
      groups = {Constants.TestNGGroups.MENU, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107484")
  public void banners() {
    logger.info("XX. Navigate to Coffee Bar");
    CoffeeBarPage coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. open find a coffee");
    coffeeBarPage.openCoffeeBanner();
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.STORE_LOCATOR_URL),
        SdkHelper.getDriver().getCurrentUrl()
            + " did not contain "
            + Constants.TestData.STORE_LOCATOR_URL);

    logger.info("XX. Navigate to landing page");
    coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. open get the app page");
    coffeeBarPage.openAppBanner();
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.REWARDS_URL),
        "Not on the correct URL");
  }

  @Test(
      groups = {Constants.TestNGGroups.MENU, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107485")
  public void newsletter() {
    logger.info("XX. Navigate to Coffee Bar");
    CoffeeBarPage coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. Navigate to Email sign up page");
    EmailSignUpPage emailSignUpPage = coffeeBarPage.openSignUp();

    Assert.assertTrue(
        emailSignUpPage.validateEmailInput(), "Email Input text box was not on the screen");
    Assert.assertTrue(
        emailSignUpPage.validateContinueButton(), "Continue button was not on the screen");
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.EMAIL_SIGNUP_URL),
        SdkHelper.getDriver().getCurrentUrl()
            + " did not contain "
            + Constants.TestData.EMAIL_SIGNUP_URL);
  }

  @Test(
      groups = {Constants.TestNGGroups.MENU, Constants.TestNGGroups.FRONT_END_REGRESSION},
      description = "11107481")
  public void viewMenuAndOptions() {
    logger.info("XX. Navigate to Coffee Bar");
    CoffeeBarPage coffeeBarPage = navigateToCoffeeBarMenuPage();

    logger.info("XX. Validate the user is directed to CoffeeBar Menu");
    Assert.assertEquals(
        coffeeBarPage.getCoffeeBarTitle(),
        Constants.TestData.COFFEEBAR_MENU,
        Constants.TestData.COFFEEBAR_MENU + "is not on the page");

    logger.info("XX. Validate the Description of coffee bar menu");
    Assert.assertFalse(
        coffeeBarPage.getCoffeeBarDescription().isEmpty(),
        "Description of coffee bar menu isn't displayed");

    logger.info("XX. Validate Order Now button");
    Assert.assertTrue(coffeeBarPage.getOrderNowButton(), "Order Now Button is not on the screen");

    logger.info("XX. Click Order Now and switch to new tab");
    coffeeBarPage.clickOrderNow();

    logger.info("XX. Validate Order Now URL and switch back to main page");
    WebHelper.switchToNewTabThatContainsUrl(Constants.TestData.ORDER_PEETS_URL);
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains(Constants.TestData.ORDER_PEETS_URL),
        "Not on the correct URL");

    SdkHelper.getDriver().switchTo().window(WebHelper.previousTab);

    AccountMenuChunk accountMenu = coffeeBarPage.getAccountMenu();

    logger.info("XX. Validate Account Icon");
    Assert.assertTrue(
        accountMenu.getGetAccountButton().exists(), "Account Button is not on the page");

    logger.info("XX. Validate Search logo");
    Assert.assertTrue(
        accountMenu.getGetSearchButton().exists(), "Search Button is not on the page");

    logger.info("XX. Validate Location Icon");
    Assert.assertTrue(
        accountMenu.getGetStoreLocatorButton().exists(), "Location Button is not on the page");

    logger.info("XX. Validate Cart Logo");
    Assert.assertTrue(accountMenu.getGetCartButton().exists(), "Cart logo is not on the page");
  }
}
