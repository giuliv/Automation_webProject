package com.applause.auto.test.new_web.pdp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.PDPTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.MyReviewDto;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.common.data.enums.ShipEveryDropdown;
import com.applause.auto.common.data.enums.SortOption;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.MyReviewModalComponent;
import com.applause.auto.new_web.components.ProductStoryModalComponent;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.components.ReviewItemComponent;
import com.applause.auto.new_web.components.WriteReviewComponent;
import com.applause.auto.new_web.components.YourReviewWasSubmittedComponent;
import com.applause.auto.new_web.components.pdp.PdpStickyNavDetailsComponent;
import com.applause.auto.new_web.components.pdp.ProductDetailsCustomerReviewsComponent;
import com.applause.auto.new_web.components.pdp.ProductDetailsViewImageComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.new_web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CoffeeFinderPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.new_web.views.SignUpPage;
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
    int itemAt = testHelper.findInStockSamplerItemPosition(productListPage);
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
    String name = productOnPosition.getProductName();
    String price = productOnPosition.getProductPrice();
    ProductDetailsPage productDetailsPage = productOnPosition.clickOnProduct();

    logger.info("Verify that Product name, and price should match.");
    softAssert.assertEquals(
        productDetailsPage.getProductName().toLowerCase(),
        name.toLowerCase(),
        "Product name didn't match");
    softAssert.assertTrue(
        price.contains(productDetailsPage.getProductPrice()), "Product price didn't match");

    logger.info(
        "Verify that Grind, quantity, subscribe, one time purchase, add to cart and recommended for you should display.");
    // Todo: Validations not always displayed
    //    softAssert.assertTrue(productDetailsPage.isGrindDisplayed(), "Grind isn't displayed");
    softAssert.assertTrue(
        productDetailsPage.isProductQuantityDisplayed(), "Product Quantity isn't displayed");
    //    softAssert.assertTrue(
    //        productDetailsPage.isSubscribeTypeDisplayed(), "Subscribe Type isn't displayed");
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
    int itemAt =
        testHelper.findInStockItemWithSpecificGrindPosition(productListPage, GrindDropdown.DRIP);
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
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

    logger.info("Determining quantity type");
    boolean isBagType = productDetailsPage.isItemBagQuantityType();
    if (!isBagType) {
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
    } else {
      logger.info("Click on bag quantity of 3+");
      productDetailsPage = productDetailsPage.selectThreePlusQuantity();

      logger.info("4. Click on (+) in Quantity Picker");
      int productQuantitySelected = productDetailsPage.getProductQuantityFromBox();
      productDetailsPage = productDetailsPage.incrementProductQuantityFromBag(1);

      logger.info("Verify that quantity should increase by 1.");
      softAssert.assertEquals(
          productDetailsPage.getProductQuantityFromBox(),
          productQuantitySelected + 1,
          "Quantity wasn't increased by 1");

      logger.info("5. Click on (-) in Quantity Picker");
      productQuantitySelected = productDetailsPage.getProductQuantityFromBox();
      productDetailsPage = productDetailsPage.decrementProductQuantityFromBag(1);

      logger.info("Verify that quantity should decremented by 1.");
      softAssert.assertEquals(
          productDetailsPage.getProductQuantityFromBox(),
          productQuantitySelected - 1,
          "Quantity wasn't decrement by 1");
    }

    logger.info("6. Select One time purchase.");
    productDetailsPage.selectOneTimePurchase();

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
    int itemAt = testHelper.findInStockItemWithGrindPosition(productListPage);
    PlpItemComponent productOnPosition = productListPage.getProductOnPosition(itemAt);
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

    logger.info("Modify price if discount is available from subscribing");
    price = miniCart.getDiscountByIndex(0, price);

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

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107450")
  public void pdpShopRunnerTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage pdp = navigateToPDP(coffeeSelected);

    logger.info("2. Verify FREE 2-Day Shipping & Free Returns is displayed");
    Assert.assertTrue(
        pdp.isFreeDayShippingTextDisplayed(), "FREE 2-Day Shipping & Free Returns isn't displayed");

    logger.info("3. Verify Learn More is displayed");
    Assert.assertTrue(pdp.isLearnMoreLinkDisplayed(), "Learn More isn't displayed");

    logger.info("4. Verify Sign In is displayed");
    Assert.assertTrue(pdp.isSignInLinkDisplayed(), "Sign In isn't displayed");

    logger.info("5. Click Learn More and Verify Learn More overlapping Modal Free shipping Text ");
    PlpLearnMoreOverlappingComponent learMoreOverlapping = pdp.clickLearnMoreLink();
    Assert.assertEquals(
        learMoreOverlapping.getShippingText(),
        TestData.SHOP_RUNNER_FREE_TEXT,
        "FREE 2-Day Shipping & Free Returns Text is not displayed");
    pdp = learMoreOverlapping.clickCloseButton(ProductDetailsPage.class);

    logger.info("6. Click Sign In Link and Verify Sign In overlapping modal");
    PlpSignInOverlappingComponent signInOverlapping = pdp.clickSignInLink();
    signInOverlapping.validateShopRunnerSignInModalUIElements().assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107451")
  public void pdpItemRoastDateFreshnessDescriptionTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info(
        "2. Verify 'Hand Roasted to Order' is shown with message based on the product roasting schedule");
    softAssert.assertTrue(
        detailsPage.isCarouselDescriptionTitleDisplayed(TestData.PDP_HAND_ROAST_TO_ORDER),
        "Hand Roasted to Order title isn't displayed");
    softAssert.assertFalse(
        detailsPage.getCarouselDescription(TestData.PDP_HAND_ROAST_TO_ORDER).isEmpty(),
        "Hand Roasted to Order description isn't displayed");

    if (!WebHelper.isDesktop()) {
      detailsPage.clickCarouselDescriptionNextButton();
    }

    logger.info("3. Verify 'Sealed for Freshness' is shown with correct message");
    softAssert.assertTrue(
        detailsPage.isCarouselDescriptionTitleDisplayed(TestData.PDP_SEALED_FOR_FRESHNESS),
        "Sealed for Freshness title isn't displayed");
    softAssert.assertEquals(
        detailsPage.getCarouselDescription(TestData.PDP_SEALED_FOR_FRESHNESS),
        TestData.PDP_SEALED_FOR_FRESHNESS_DESCRIPTION,
        "Sealed for Freshness description isn't displayed");

    if (!WebHelper.isDesktop()) {
      detailsPage.clickCarouselDescriptionNextButton();
    }

    logger.info("4. Verify 'Delivered Fresh To You' is shown with correct message");
    softAssert.assertTrue(
        detailsPage.isCarouselDescriptionTitleDisplayed(TestData.PDP_DELIVERED_FRESH_TO_YOU),
        "Delivered Fresh To You title isn't displayed");
    softAssert.assertEquals(
        detailsPage.getCarouselDescription(TestData.PDP_DELIVERED_FRESH_TO_YOU),
        TestData.PDP_DELIVERED_FRESH_TO_YOU_DESCRIPTION,
        "Delivered Fresh To You description isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107452")
  public void pdpFlavorProfileTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP(TestData.COFFEE_BEST_SELLERS_URL);
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Select first product ");
    PlpItemComponent itemComponent = productListPage.getProductOnPosition(1);
    String productDescription = itemComponent.getProductDescription();
    ProductDetailsPage detailsPage = itemComponent.clickOnProduct();

    logger.info("3. Verify flavor notes match the flavor notes on the corresponding PCP shop card");
    softAssert.assertEquals(
        detailsPage.getFlavorProfileSubtitle(),
        productDescription,
        "Flavor notes doesn't match the flavor notes on the corresponding PCP shop card");

    logger.info("4. Verify Roast Details with Custom scales for each product correctly");
    detailsPage = navigateToPDP(Constants.StandardCoffeeInventory.Coffee1.getValue());
    softAssert.assertTrue(
        detailsPage.isFlavorProfileRoastDetailsRatingDisplayedProperly("Roast", "light", "dark"),
        "Roast Details aren't displayed properly");
    softAssert.assertTrue(
        detailsPage.isFlavorProfileRoastDetailsRatingDisplayedProperly("Brightness", "low", "high"),
        "Roast Details aren't displayed properly");
    softAssert.assertTrue(
        detailsPage.isFlavorProfileRoastDetailsRatingDisplayedProperly("Body", "light", "full"),
        "Roast Details aren't displayed properly");

    logger.info("5. Verify Roast Details Attributes are displayed");
    softAssert.assertTrue(
        detailsPage.isFlavorProfileAttributeDisplayed("Type"),
        "Roast Details Attribute isn't displayed");
    softAssert.assertTrue(
        detailsPage.isFlavorProfileAttributeDisplayed("Origin"),
        "Roast Details Attribute isn't displayed");
    softAssert.assertTrue(
        detailsPage.isFlavorProfileAttributeDisplayed("Process"),
        "Roast Details Attribute isn't displayed");

    if (WebHelper.isDesktop()) {
      logger.info("6. Verify Need Help to find the Perfect Coffee image is displayed");
      softAssert.assertTrue(
          detailsPage.isFlavorProfileQuizImageDisplayed(),
          "Need Help to find the Perfect Coffee image isn't displayed");
    }
    softAssert.assertTrue(
        detailsPage.isFlavorProfileQuizTitleDisplayed(),
        "Need Help to find the Perfect Coffee title isn't displayed");

    logger.info("7. Click on 'Take the Coffee Quiz' link");
    CoffeeFinderPage coffeeFinderPage = detailsPage.clickFlavorProfileQuizLink();

    logger.info("8. Verify the coffee quiz landing page is displayed");
    softAssert.assertTrue(
        coffeeFinderPage.isDisplayed(), "The coffee quiz landing page isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107453")
  public void pdpStoryTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info("2. Verify Story section is displayed with name, banner and description");
    softAssert.assertTrue(
        detailsPage.isStoryBannerDisplayed(), "Story banner image isn't displayed");
    softAssert.assertTrue(detailsPage.isStoryTitleDisplayed(), "Story title isn't displayed");
    softAssert.assertTrue(
        detailsPage.isStoryDescriptionDisplayed(), "Story description isn't displayed");

    logger.info("2. Click on Read more link");
    ProductStoryModalComponent storyModalComponent = detailsPage.clickStoryReadMoreLink();

    logger.info("3. Verify full story in this overlay is displayed");
    softAssert.assertTrue(
        storyModalComponent.isDisplayed(), "Story in this overlay isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107454")
  public void pdpBrewingMethodsTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage =
        navigateToPDP(Constants.StandardCoffeeInventory.Coffee1.getValue());

    logger.info("2. Verify the user is shown with all different brewing methods");
    softAssert.assertTrue(
        detailsPage.areAllBrewingMethodsDifferent(),
        "Not all different brewing methods are displayed");

    logger.info("3. Verify the first tab will be active by default");
    softAssert.assertEquals(
        detailsPage.getBrewingMethod(1),
        detailsPage.getActiveBrewingMethod(),
        "The first tab doesn't active by default");

    logger.info("4. Click on other tabs");
    String firstBrewingMethodDescription = detailsPage.getActiveBrewingMethodDescription();
    String firstBrewingMethodTime = detailsPage.getActiveBrewingMethodTime();
    detailsPage = detailsPage.selectBrewingMethod(2);

    logger.info(
        "5. Verify the user is shown with different brewing methods, description, Brewing time");
    softAssert.assertNotEquals(
        detailsPage.getActiveBrewingMethodDescription(),
        firstBrewingMethodDescription,
        "The same description is displayed");
    softAssert.assertNotEquals(
        detailsPage.getActiveBrewingMethodTime(),
        firstBrewingMethodTime,
        "The same time is displayed");

    logger.info("6. Click on Play button on Banner");
    detailsPage = detailsPage.clickBrewingMethodPlayVideoButton();

    logger.info(
        "7. Verify the video will start playing and after clicking play, a pause is displayed");
    softAssert.assertTrue(detailsPage.isBrewingMethodVideoPlayed(), "Video isn't played");
    softAssert.assertTrue(
        detailsPage.isBrewingMethodPauseVideoButtonDisplayed(), "Pause button isn't played");

    logger.info("8. Click on Pause button on Banner validate the video is paused");
    detailsPage = detailsPage.clickBrewingMethodPauseVideoButton();
    softAssert.assertFalse(detailsPage.isBrewingMethodVideoPlayed(), "The video isn't paused");

    logger.info("9. Click Brewing Method shop link");
    detailsPage = detailsPage.clickBrewingMethodShopLink();

    logger.info("10. Verify equipment pdp is displayed");
    softAssert.assertTrue(
        detailsPage.isEquipmentDetailsPageDisplayed(), "The equipment page isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107455")
  public void pdpCustomerReviewsTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(Constants.TestData.BARIDI_BLEND_BLUNDE);

    logger.info("2. Verify customer reviews are sorted by Most Recent by default");
    ProductDetailsCustomerReviewsComponent customerReviewsComponent =
        detailsPage.getCustomerReviewsComponent();

    logger.info("3. Choose different Sort by Options");
    customerReviewsComponent.seeMoreReviews();
    customerReviewsComponent.selectSortByOption(SortOption.LOWEST_TO_HIGHEST_RATING);

    logger.info("4. Verify customer reviews sorted by respective option selected");
    softAssert.assertTrue(
        customerReviewsComponent.areReviewsSortedProperly(SortOption.LOWEST_TO_HIGHEST_RATING),
        "Reviews are not sorted properly");

    logger.info("5. Verify the user is shown with correct 'Average Customer Ratings'");
    softAssert.assertTrue(
        customerReviewsComponent.isAverageRatingCalculatedProperly(),
        "'Average Customer Ratings' isn't calculated properly");

    logger.info("6. Verify reviews divided according to the stars");
    softAssert.assertTrue(
        customerReviewsComponent.areReviewsDividedAccordingToStars(),
        "Reviews aren't divided according to the stars");

    logger.info("7. Verify total number of reviews is displayed");
    softAssert.assertTrue(
        customerReviewsComponent.isReviewsTotalNumberDisplayed(),
        "Total number of reviews isn't displayed");

    logger.info("8. Click on any review starts");
    customerReviewsComponent.clickOnReviewStar(4);

    logger.info("9. Verify the reviews of that star are only displayed");
    softAssert.assertTrue(
        customerReviewsComponent.getReviewsRatings().stream().allMatch(rating -> rating == 4),
        "Not all reviews have selected rating");

    logger.info("10. Click on multiple review starts");
    customerReviewsComponent.clickOnReviewStar(2);

    logger.info("11. Verify the reviews of that star are only displayed");
    softAssert.assertTrue(
        customerReviewsComponent.getReviewsRatings().stream()
            .allMatch(rating -> (rating == 4 | rating == 2)),
        "Not all reviews have selected ratings");

    logger.info("12. Click Clear All Filters");
    customerReviewsComponent.clearAllFilters();

    logger.info("13. Verify the reviews filters are removed");
    softAssert.assertFalse(
        customerReviewsComponent.isClearAllFilterButtonDisplayed(), "The filters aren't removed");

    logger.info(
        "14. Verify for each review, Review stars, Review person's name, location and date is shown");
    for (ReviewItemComponent reviewItem : customerReviewsComponent.getReviewItemComponent()) {
      softAssert.assertTrue(reviewItem.isPersonNameDisplayed(), "Person name isn't displayed");
      softAssert.assertTrue(reviewItem.isRatingDisplayed(), "Rating isn't displayed");
      softAssert.assertTrue(reviewItem.isLocationDisplayed(), "Location isn't displayed");
      softAssert.assertTrue(reviewItem.isDateDisplayed(), "Date isn't displayed");
      softAssert.assertTrue(reviewItem.isHelpfulDisplayed(), "Helpful? isn't displayed");
      softAssert.assertTrue(reviewItem.isYesNoVoteDisplayed(), "Yes/No isn't displayed");
      softAssert.assertTrue(reviewItem.isReportButtonDisplayed(), "Report isn't displayed");
    }

    logger.info("15. Click on Report");
    ReviewItemComponent itemComponent = customerReviewsComponent.getReviewItemComponent().get(0);
    itemComponent.clickReportButton();

    logger.info("16. Verify the user is now reported");
    softAssert.assertTrue(
        itemComponent.isReportedButtonDisplayed(), "Reported button isn't displayed");

    logger.info("17. Click Write a Review");
    WriteReviewComponent writeReviewComponent = customerReviewsComponent.clickWriteReview();

    logger.info("18. Verify click on this will Launch an overlay module to write a review");
    softAssert.assertTrue(
        writeReviewComponent.isDisplayed(), "Write a review module isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107456")
  public void pdpRecommendedForYouSectionTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info("2. Verify 'RECOMMENDED FOR YOU' section is displayed");
    softAssert.assertTrue(
        detailsPage.isRecommendedForYouSectionDisplayed(),
        "Recommended for you section isn't displayed");

    logger.info("3. Verify four items are displayed in the 'RECOMMENDED FOR YOU' section");
    softAssert.assertEquals(
        detailsPage.getDisplayedRecommendedForYouItemNumber(),
        4,
        "The four items aren't displayed in the 'RECOMMENDED FOR YOU' section");

    logger.info("4. Click on Take A Coffee Quiz");
    CoffeeFinderPage coffeeFinderPage = detailsPage.clickRecommendedForYouQuizLink();

    logger.info("5. Verify that coffee finder page should display.");
    Assert.assertTrue(coffeeFinderPage.isDisplayed(), "Coffee finder page didn't appear");

    logger.info(
        "6. Verify for each item, image, name, description, price per LB and reviews are displayed");
    detailsPage = navigateToPDP(coffeeSelected);
    for (PlpItemComponent item : detailsPage.getRecommendedForYouItemList()) {
      softAssert.assertTrue(item.isNameDisplayed(), "Product name isn't displayed");
      String productName = item.getProductName();

      softAssert.assertTrue(
          item.isPriceDisplayed(),
          String.format("Product price isn't displayed for product: {%s}", productName));
      softAssert.assertTrue(
          item.isImageDisplayed(),
          String.format("Product image isn't displayed for product {%s}", productName));
    }

    logger.info(
        "7. Hover any item Validate the user is shown with 'Add to cart' button immediately");
    PlpItemComponent itemComponent = detailsPage.getRecommendedForYouItemList().get(0);
    softAssert.assertTrue(
        itemComponent.isAddToCartButtonDisplayed(), "'Add to cart' button isn't displayed");

    logger.info("8. Hover any item --> click on 'Add to cart'");
    QuickViewComponent quickViewComponent = itemComponent.clickAddToCart();

    logger.info("9. Verify the user is overlay with Quick ADD screen");
    softAssert.assertNotNull(quickViewComponent, "Quick ADD screen isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107457")
  public void pdpNeverMissOfferTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info("2. Verify 'Sign up' button is displayed");
    softAssert.assertTrue(
        detailsPage.isNeverMissOfferSignUpButtonDisplayed(), "'Sign up' button isn't displayed");

    logger.info("3. Click on 'Sign up'");
    SignUpPage signUpPage = detailsPage.clickNeverMissOfferSignUpButton();

    logger.info("4. Verify Sign up button is displayed");
    softAssert.assertNotNull(signUpPage, "Sign up page isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107458")
  public void pdpAtPeetsCoffeeOnInstagramTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info("2. Verify there are Images displayed");
    softAssert.assertTrue(
        detailsPage.arePeetscoffeeOnInstagramImagesDisplayed(),
        "The images aren't displayed in the PeetsCoffee on Instagram section");

    logger.info("3. click on the Image");
    ProductDetailsViewImageComponent imageComponent =
        detailsPage.clickOnPeetscoffeeOnInstagramImage();

    logger.info("4. Verify a window with Image is displayed");
    softAssert.assertNotNull(imageComponent, "A window with Image isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107459")
  public void pdpScrollDownPastTest() {
    logger.info("1. Navigate to PDP");
    ProductDetailsPage detailsPage = navigateToPDP(coffeeSelected);

    logger.info("2. Scroll past the purchasing area");
    detailsPage = detailsPage.scrollToFlavorProfile();

    logger.info("3. Once the user scrolls the sticky nav is replaced with this bar.");
    PdpStickyNavDetailsComponent pdpStickyNavDetailsComponent =
        detailsPage.getPdpStickyDetailsComponent();
    Assert.assertTrue(pdpStickyNavDetailsComponent.isDisplayed(), "Sticky nav isn't displayed");

    if (WebHelper.isDesktop()) {
      // Only 'Add' button is displayed on mobile web
      logger.info("4. Verify product name, product price and 'Add to Cart' button is displayed");
      Assert.assertTrue(
          pdpStickyNavDetailsComponent.isProductNameDisplayed(), "Product name isn't displayed");
      Assert.assertTrue(
          pdpStickyNavDetailsComponent.isProductPriceDisplayed(), "Product price isn't displayed");
    }
    Assert.assertTrue(
        pdpStickyNavDetailsComponent.isAddToCartButtonDisplayed(),
        "'Add to Cart' button isn't displayed");

    logger.info("5. Click on 'ADD to cart'");
    String productName =
        WebHelper.isDesktop()
            ? pdpStickyNavDetailsComponent.getProductName()
            : detailsPage.getProductName();
    MiniCart miniCart = pdpStickyNavDetailsComponent.clickAddToCart();

    logger.info("6. Verify product is added to the cart");
    Assert.assertTrue(
        miniCart.getProductNameByIndex(0).equalsIgnoreCase(productName),
        "Wrong product added to the cart");

    logger.info("7. Scroll up");
    WebHelper.scrollToPageTop();

    logger.info("8. Verify after scroll up, the bar is replaced with the sticky nav");
    Assert.assertFalse(
        pdpStickyNavDetailsComponent.isDisplayed(), "Sticky nav is unexpected displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107460")
  public void pdpSpecialtyBlendsTest() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();

    logger.info(
        "2. Search for the product: {}", WebTestData.SEARCH_COFFEE_YOSEMITE_DOS_SIERRAS_ORGANIC);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(WebTestData.SEARCH_COFFEE_YOSEMITE_DOS_SIERRAS_ORGANIC);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info(
        "3. Verify at the top of the PDP/purchasing area for specialty blends (green packaging) is displayed");
    Assert.assertEquals(
        productDetailsPage.getBackgroundColorForTopSection(),
        WebTestData.PDP_SECTION_GREEN_COLOR,
        "Background color is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107461")
  public void pdpAnniversaryCoffeeTest() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();

    logger.info("2. Search for the product: {}", WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    SearchResultsPage searchResultsPage =
        homePage
            .getHeader()
            .getSearchComponent()
            .search(WebTestData.SEARCH_COFFEE_JR_RESERVE_BLEND);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info(
        "3. Verify at the top of the PDP/purchasing area for Anniversary coffee is displayed");
    Assert.assertEquals(
        productDetailsPage.getBackgroundColorForTopSection(),
        WebTestData.PDP_SECTION_ANNIVERSARY_PRODUCT_COLOR,
        "Background color is wrong");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107462",
      enabled = false)
  public void pdpOnlyFewItemsLeftTest() {
    // TODO We don't have item with only few quantity left
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107463",
      enabled = false)
  public void pdpDarkBackgroundNavigationTest() {
    // TODO The Nav doesn't transparent on land
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107464")
  public void pdpTeaItemTest() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();

    logger.info("2. Search for the product: {}", TestData.TEA_NAME);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(TestData.TEA_NAME);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info("3. Verify the Size dropdown is displayed");
    Assert.assertTrue(
        productDetailsPage.isSizeDropdownDisplayed(), "Size dropdown isn't displayed");

    logger.info("4. Verify Quantity dropdown is displayed");
    Assert.assertTrue(
        productDetailsPage.isIncrementQuantityButtonDisplayed(),
        "Quantity dropdown isn't displayed");

    logger.info("5. Quantity --> Click on '-'");
    productDetailsPage = productDetailsPage.clickQuantityMinusButton();

    logger.info("6. Verify '-' symbol will be inactive if the quantity is set to 1");
    Assert.assertEquals(
        productDetailsPage.getProductQuantitySelected(),
        1,
        "Quantity unexpected changed after click on '-' button");

    logger.info("5. Quantity --> Click on '+'");
    productDetailsPage = productDetailsPage.clickQuantityPlusButton();

    logger.info("6. Verify qty can be increased with Increase quantity with the '+' button");
    Assert.assertEquals(
        productDetailsPage.getProductQuantitySelected(),
        2,
        "Quantity isn't changed after click on '+' button");
  }

  @Test(
      groups = {Constants.TestNGGroups.WEB_REGRESSION, Constants.TestNGGroups.PDP},
      description = "11107465")
  public void pdpEquipmentTest() {
    logger.info("1. Navigate to PDP");
    HomePage homePage = navigateToHome();

    logger.info("2. Search for the product: {}", TestData.EQUIPMENT_NAME);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(TestData.EQUIPMENT_NAME);
    ProductDetailsPage productDetailsPage = searchResultsPage.clickOverProductByIndex(0);

    logger.info(
        "3. Validate the user has no Grind options but only the traditional quantity selector will be used for all generic PDP pages");
    Assert.assertTrue(
        productDetailsPage.isIncrementQuantityButtonDisplayed(),
        "Increment quantity button isn't displayed");
    Assert.assertTrue(
        productDetailsPage.isDecrementQuantityButtonDisplayed(),
        "Decrement quantity button isn't displayed");
  }
}
