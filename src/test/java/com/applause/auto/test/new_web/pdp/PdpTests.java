package com.applause.auto.test.new_web.pdp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.PDPTestData;
import com.applause.auto.common.data.dto.MyReviewDto;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.common.data.enums.ShipEveryDropdown;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.MyReviewModalComponent;
import com.applause.auto.new_web.components.YourReviewWasSubmittedComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CoffeeFinderPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PdpTests extends BaseTest {

  @Test(
      groups = {
        Constants.TestNGGroups.WEB_REGRESSION,
        Constants.TestNGGroups.PDP,
        Constants.TestNGGroups.SMOKE
      },
      description = "11102944")
  public void PDPElementsTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();

    logger.info("2. Select an item");
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(2);
    String name = productOnPosition.getProductName();
    String price = productOnPosition.getProductPrice();
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();

    logger.info("Verify that Product name, and price should match.");
    softAssert.assertEquals(
        productDetailsPage.getProductName().toLowerCase(),
        name.toLowerCase(),
        "Product name didn't match");
    softAssert.assertEquals(
        productDetailsPage.getProductPrice(), price, "Product name didn't match");

    logger.info(
        "Verify that Grind, quantity, subscribe, one time purchase, add to cart and recommended for you should display.");
    softAssert.assertTrue(productDetailsPage.isGrindDisplayed(), "Grind isn't displayed");
    softAssert.assertTrue(
        productDetailsPage.isProductQuantityDisplayed(), "Product Quantity isn't displayed");
    softAssert.assertTrue(
        productDetailsPage.isSubscribeTypeDisplayed(), "Subscribe Type isn't displayed");
    softAssert.assertTrue(
        productDetailsPage.isAddToCartButtonDisplayed(), "Add to cart isn't displayed");
    softAssert.assertTrue(
        productDetailsPage.isRecommendedForYouSectionDisplayed(),
        "Recommended for you section isn't displayed");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11102945")
  public void grindAndQuantitySelectionTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();

    logger.info("2. Select an item");
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(2);
    String name = productOnPosition.getProductName();
    String price = productOnPosition.getProductPrice();
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();

    logger.info("3. Select Drip");
    productDetailsPage = productDetailsPage.selectGrind(GrindDropdown.DRIP);

    logger.info("Verify that Grind was selected");
    softAssert.assertEquals(
        productDetailsPage.getGrindSelected().toLowerCase(),
        GrindDropdown.DRIP.getValue().toLowerCase(),
        "Grind wasn't selected");

    logger.info("4. Click on (+) in Quantity");
    int productQuantitySelected = productDetailsPage.getProductQuantitySelected();
    productDetailsPage = productDetailsPage.incrementProductQuantity(1);

    logger.info("Verify that quantity should increase by 1.");
    softAssert.assertEquals(
        productDetailsPage.getProductQuantitySelected(),
        productQuantitySelected + 1,
        "Quantity wasn't increased by 1");

    logger.info("5. Click on (-) in Quantity");
    productQuantitySelected = productDetailsPage.getProductQuantitySelected();
    productDetailsPage = productDetailsPage.decrementProductQuantity(1);

    logger.info("Verify that quantity should decremented by 1.");
    softAssert.assertEquals(
        productDetailsPage.getProductQuantitySelected(),
        productQuantitySelected - 1,
        "Quantity wasn't decrement by 1");

    logger.info("6. Select One time purchase.");
    productDetailsPage.selectSubscribeType();

    logger.info("7. Click on Add to cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info(
        "Verify that item should be added to cart and minicart should match product name and price.");
    softAssert.assertEquals(
        miniCart.getProductNameByIndex(0).toLowerCase(),
        name.toLowerCase(),
        "Product name didn't match");
    softAssert.assertEquals(miniCart.getPriceByIndex(0), price, "Product price didn't match");
    softAssert.assertEquals(
        miniCart.getGrindByIndex(0).toLowerCase(),
        GrindDropdown.DRIP.getValue().toLowerCase(),
        "Product grind didn't match");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11102946")
  public void subscribeTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();

    logger.info("2. Select an item");
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(2);
    String name = productOnPosition.getProductName();
    String price = productOnPosition.getProductPrice();
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();
    String grind = productDetailsPage.getGrindSelected();

    logger.info("3. Hover subscribe info icon");
    String tooltip = productDetailsPage.hoverOverSubscribeInfoIcon();

    logger.info("Verify that tooltip should display.");
    softAssert.assertTrue(
        tooltip.contains(PDPTestData.SUBSCRIBE_INFO_TOOLTIP), "Tooltip didn't appear");

    logger.info("4. Select Subscribe & get free shipping.");
    productDetailsPage.selectSubscribeType();

    logger.info("5. Select Ship every 2 weeks");
    productDetailsPage = productDetailsPage.selectShipEvery(ShipEveryDropdown.TWO_WEEKS);

    logger.info("Verify that 2 weeks should display as selected.");
    softAssert.assertEquals(
        productDetailsPage.getSelectedShipEvery(),
        ShipEveryDropdown.TWO_WEEKS.getValue(),
        "2 weeks wasn't display as selected.");

    logger.info("6. Click on Add to cart");
    MiniCart miniCart = productDetailsPage.clickAddToMiniCart();

    logger.info(
        "Verify that Item should be added to cart and minicart should match product name, image, price and shipping option.");
    softAssert.assertEquals(
        miniCart.getProductNameByIndex(0).toLowerCase(),
        name.toLowerCase(),
        "Product name didn't match");
    softAssert.assertEquals(miniCart.getPriceByIndex(0), price, "Product price didn't match");
    softAssert.assertEquals(
        miniCart.getGrindByIndex(0).toLowerCase(),
        grind.toLowerCase(),
        "Product grind didn't match");
    softAssert.assertTrue(miniCart.isSubscribeButtonEnabled(), "Subscribe Button isn't enabled");

    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11102947")
  public void writeReviewTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();

    logger.info("2. Select an item");
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(2);
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();

    logger.info("3. Click on stars");
    int pageCoordinatesBefore = WebHelper.getPagePositionY();
    productDetailsPage.clickOnStars();

    logger.info("Verify that page scrolls down to write a review.");
    softAssert.assertTrue(
        WebHelper.getPagePositionY() > pageCoordinatesBefore, "The page wasn't scrolled down");

    logger.info("4. Click on write a review");
    MyReviewModalComponent myReviewModalComponent = productDetailsPage.clickWriteReview();

    logger.info("Verify that My review modal should display.");
    softAssert.assertTrue(
        myReviewModalComponent.isDisplayed(), "'My review' modal isn't displayed");

    logger.info("5. Close modal");
    productDetailsPage = myReviewModalComponent.close(ProductDetailsPage.class);

    logger.info("Verify that My review modal isn't display.");
    softAssert.assertFalse(
        myReviewModalComponent.isDisplayed(), "'My review' modal is still displayed");

    logger.info("6. Click on write a review again");
    myReviewModalComponent = productDetailsPage.clickWriteReview();

    logger.info("7. Fill up mandatory fields");
    YourReviewWasSubmittedComponent yourReviewWasSubmittedComponent =
        myReviewModalComponent.postReview(MyReviewDto.builder().build());

    logger.info("Verify that review should be posted.");
    softAssert.assertTrue(yourReviewWasSubmittedComponent.isDisplayed(), "Review wasn't posted.");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11102948")
  public void takeTheCoffeeQuizTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();

    logger.info("2. Select an item");
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(2);
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();

    logger.info("3. Scroll down and click on take the coffee quiz");
    CoffeeFinderPage coffeeFinderPage = productDetailsPage.clickOnTakeTheCoffeeQuiz();

    logger.info("4. Verify that coffee finder page should display.");
    Assert.assertTrue(coffeeFinderPage.isDisplayed(), "Coffee finder page didn't appear");
  }
}
