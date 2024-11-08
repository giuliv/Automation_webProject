package com.applause.auto.web.helpers;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.RegisterPeetCardComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.views.CreateAccountPage;
import com.applause.auto.web.views.HomePage;
import com.applause.auto.web.views.ProductDetailsPage;
import com.applause.auto.web.views.ProductListPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class TestHelper {
  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  public static MyAccountPage createAccount(HomePage homePage, String mail) {
    CreateAccountPage createAccountPage =
        homePage.getHeader().clickAccountButton().clickOnCreateAccountButton();
    Assert.assertTrue(
        SdkHelper.getDriver().getCurrentUrl().contains("/Registration"),
        "Registration URL is not correct!");

    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            Constants.WebTestData.FIRST_NAME,
            Constants.WebTestData.LAST_NAME,
            mail,
            Constants.TestData.WEB_PASSWORD,
            Constants.TestData.WEB_PASSWORD);

    Assert.assertTrue(myAccountPage.getWelcomeMessage().contains("welcome"), "User is not created");
    return myAccountPage;
  }

  public int findInStockItemWithSpecificGrindPosition(
      ProductListPage productListPage, GrindDropdown grind) {
    int itemAt = 0;
    boolean proceedWithTest = false;
    while (!proceedWithTest) {
      itemAt += 1;
      logger.info("Checking item at [{}] to determine if we have a grind and are in stock", itemAt);
      PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
      ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();
      if (productDetailsPage.isGrindDisplayed()) {
        productDetailsPage.selectGrind(grind);
      }
      proceedWithTest =
          (!productDetailsPage.isItemAvailable() && productDetailsPage.isGrindDisplayed())
              ? true
              : false;
      logger.info("It is [{}] that we should proceed using this item.", proceedWithTest);
      productListPage = WebHelper.navigateBack(ProductListPage.class);
    }
    return itemAt;
  }

  public int findInStockItemWithGrindPosition(ProductListPage productListPage) {
    int itemAt = 0;
    boolean proceedWithTest = false;
    while (!proceedWithTest) {
      itemAt += 1;
      logger.info("Checking item at [{}] to determine if we have a grind and are in stock", itemAt);
      PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
      ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();
      proceedWithTest =
          (!productDetailsPage.isItemAvailable() && productDetailsPage.isGrindDisplayed())
              ? true
              : false;
      logger.info("It is [{}] that we should proceed using this item.", proceedWithTest);
      productListPage = WebHelper.navigateBack(ProductListPage.class);
    }
    return itemAt;
  }

  public int findInStockItemPosition(ProductListPage productListPage) {
    int itemAt = 0;
    boolean proceedWithTest = false;
    while (!proceedWithTest) {
      itemAt += 1;
      logger.info("Checking item at [{}] to determine if we have a grind and are in stock", itemAt);
      PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
      ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();
      proceedWithTest = (!productDetailsPage.isItemAvailable()) ? true : false;
      logger.info("It is [{}] that we should proceed using this item.", proceedWithTest);
      productListPage = WebHelper.navigateBack(ProductListPage.class);
    }
    return itemAt;
  }

  public int findInStockSamplerItemPosition(ProductListPage productListPage) {
    int itemAt = 0;
    boolean proceedWithTest = false;
    while (!proceedWithTest) {
      itemAt += 1;
      logger.info("Checking item at [{}] to determine if we are in stock", itemAt);
      PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
      ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();
      proceedWithTest =
          (!productDetailsPage.isItemAvailable() && productDetailsPage.itemIsSampler())
              ? true
              : false;
      logger.info("It is [{}] that we should proceed using this item.", proceedWithTest);
      productListPage = WebHelper.navigateBack(ProductListPage.class);
    }
    return itemAt;
  }

  public static RegisterPeetCardComponent registerPeetsCard(String cardNumber, String pinNumber) {
    RegisterPeetCardComponent registerPeetCardComponent =
        SdkHelper.create(RegisterPeetCardComponent.class);
    registerPeetCardComponent.registerCard(cardNumber, pinNumber);
    return registerPeetCardComponent;
  }
}
