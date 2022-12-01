package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.HomepageCoffeeBar;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.Touts;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.StoreDetailsDto;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.components.NeverMissOfferComponent;
import com.applause.auto.new_web.components.OneAppManyBenefitsComponent;
import com.applause.auto.new_web.components.PeetnikRewardsComponent;
import com.applause.auto.new_web.components.StockResultFilterComponent;
import com.applause.auto.new_web.components.StockResultItemComponent;
import com.applause.auto.new_web.components.ToutsSectionComponent;
import com.applause.auto.new_web.components.pdp.CoffeeBarCarouselComponent;
import com.applause.auto.new_web.components.pdp.CoffeeBarItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.GoogleMapPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SignUpPage;
import com.applause.auto.new_web.views.StoreDetailsPage;
import com.applause.auto.new_web.views.StoreLocatorPage;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FindStoreTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SMOKE},
      description = "11102898")
  public void validateResultsListUiAndLoadMore() {
    logger.info("1. Navigate to Store Locator page");
    StoreLocatorPage storeLocatorPage = navigateToStoreLocatorPage();

    logger.info("Validate next to Peets Logo there is a Person's Icon");
    Header header = storeLocatorPage.getHeader();
    Assert.assertTrue(header.isLogoDisplayed(), "Peet's Coffee Logo isn't displayed");
    Assert.assertTrue(header.isUserLoggedOut(), "Person's Icon isn't displayed");

    logger.info("2. At Store Locator Header --> Click on Peets Logo");
    HomePage homePage = header.clickLogoButton();

    logger.info(
        "Validate we also have Person Icon, Search logo, Location Icon and Cart logo are displayed");
    header = homePage.getHeader();
    Assert.assertTrue(header.isLogoDisplayed(), "Peet's Coffee Logo isn't displayed on Home page");
    Assert.assertTrue(header.isUserLoggedOut(), "Person's Icon isn't displayed on Home page");
    Assert.assertTrue(
        header.isLocationIconDisplayed(), "Location Icon isn't displayed on Home page");
    Assert.assertTrue(header.isCartIconDisplayed(), "Cart Icon isn't displayed on Home page");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.FIND_STORE},
      description = "11102899")
  public void validateFilterTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on filter by amenities.");
    List<String> stockResultListBefore = storeLocatorPage.getListOfStoreNames();
    StockResultFilterComponent stockResultFilterComponent = storeLocatorPage.openFilters();

    logger.info("5. Select any filter.");
    storeLocatorPage = stockResultFilterComponent.selectFiltersOnPosition(1, 2).close();

    logger.info("Verify that new results appear");
    Assert.assertNotEquals(
        storeLocatorPage.getListOfStoreNames(),
        stockResultListBefore,
        "New results didn't appear after filtering");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE, TestNGGroups.SANITY},
      description = "11102900")
  public void validateStoreDetails() {
    logger.info("1. Navigate to Store Locator page");
    StoreLocatorPage storeLocatorPage = navigateToStoreLocatorPage();

    logger.info("3. Enter correct Zipcode anc click on 'SEARCH'");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info(
        "Validate the user is shown with Results with all the 5 stores near to this Zipcode");
    List<StockResultItemComponent> stockResultItems = storeLocatorPage.getStockResultList();
    Assert.assertEquals(
        stockResultItems.size(), 5, "Not 5 stores near to this Zipcode are displayed");

    logger.info("Validate Results (25) - 25 is number of stores shown in search");
    Assert.assertEquals(
        storeLocatorPage.getCountOfItemsFromResultTitle(), 25, "25 items are not displayed");

    logger.info("Validate FILTER BY AMENITIES - user can filter by Amenities");
    Assert.assertTrue(
        storeLocatorPage.isAmenitiesFilterDisplayed(), "FILTER BY AMENITIES is not displayed");

    logger.info("Validate For each store, user is shown with all expected info");
    Assert.assertTrue(
        storeLocatorPage.checkIfAllStoresFullyDisplayed(),
        "Not all stores are displayed with expected info");

    logger.info("Validate user is shown with all expected Amenities options");
    Assert.assertEquals(
        storeLocatorPage.getAmenitiesOptions(),
        WebTestData.AMENITIES_LIST,
        "Amenities options are not displayed properly");

    logger.info("4. Select any filter option");
    List<String> itemsBeforeSelectFilter = storeLocatorPage.getListOfStoreNames();
    storeLocatorPage = storeLocatorPage.selectAmenities("Contactless Payments");

    logger.info("Validate results are shown according to selected filter");
    Assert.assertNotEquals(
        storeLocatorPage.getListOfStoreNames(),
        itemsBeforeSelectFilter,
        "Result list is not changed");

    logger.info("6. Click on Start Order");
    storeLocatorPage.getStockResultList().get(0).startOrder();

    logger.info("Validate user is directed to 'https://order.peets.com/'");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains("https://order.peets.com/"),
        "User is not navigated to https://order.peets.com/");

    logger.info("Navigate back to Store locator page");
    storeLocatorPage = navigateToStoreLocatorPage().searchByZipCode("95032");

    logger.info("5. Click on a store");
    StockResultItemComponent stockResultItemComponent =
        storeLocatorPage.getStockResultList().get(0);
    StoreDetailsDto storeDetailsDto = stockResultItemComponent.getStoreDetailsDto();
    StoreDetailsPage storeDetailsPage = stockResultItemComponent.click();

    logger.info("Verify that store type, name and address should match.");
    Assert.assertEquals(
        storeDetailsPage.getType(), storeDetailsDto.getType(), "Store type isn't correct");
    Assert.assertEquals(
        storeDetailsPage.getName(), storeDetailsDto.getName(), "Store Name isn't correct");
    Assert.assertTrue(
        StringUtils.containsIgnoreCase(storeDetailsPage.getAddress(), storeDetailsDto.getAddress()),
        String.format(
            "Store Address isn't. Expected [%s] but found [%s]",
            storeDetailsDto.getAddress(), storeDetailsPage.getAddress()));

    logger.info(
        "Verify that Back to results, start order and get directions buttons should display.");
    Assert.assertTrue(storeDetailsPage.isBackButtonDisplayed(), "'Back' button isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isStartOrderButtonDisplayed(), "'Start Order' button isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isGetDirectionsButtonDisplayed(),
        "'Get Directions' button isn't displayed");

    logger.info("Verify that Schedule and amenities should display.");
    Assert.assertEquals(
        storeDetailsPage.getSchedule().replaceAll(":00", "").toLowerCase(),
        storeDetailsDto.getSchedule().toLowerCase(),
        "Store Schedule isn't correct");
    Assert.assertTrue(storeDetailsPage.isAmenitiesButtonDisplayed(), "'Amenities' isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.FIND_STORE},
      description = "11102901")
  public void validateGetDirections() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info("5. Click on Get directions.");
    GoogleMapPage googleMapPage = storeDetailsPage.clickOnGetDirectionsButton();

    logger.info("Verify that map should display.");
    Assert.assertTrue(googleMapPage.isDisplayed(), "Map isn't display");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102902")
  public void validateStartOrder() {
    logger.info("1. Navigate to Store Locator page");
    StoreLocatorPage storeLocatorPage = navigateToStoreLocatorPage();

    logger.info("2. Enter correct Zipcode anc click on 'SEARCH'");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("3. Click on any store name");
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info(
        "Verify that Stores should display with no., name, store, address schedule and start order button.");
    Assert.assertTrue(
        storeDetailsPage.areStoreDetailsDisplayed(), "Not all Stores are fully displayed");

    logger.info("Validate user is shown with description and Download APP Button");
    OneAppManyBenefitsComponent oneAppManyBenefitsComponent =
        storeDetailsPage.getOneAppManyBenefitsComponent();
    Assert.assertTrue(
        oneAppManyBenefitsComponent.isDisplayedProperly(),
        "One app, many benefits section isn't displayed properly");

    if (!WebHelper.isMobile()) {
      logger.info("5. Click on Download APP Button");
      oneAppManyBenefitsComponent.clickDownloadApp();

      logger.info("Verify App store page is displayed");
      String currentUrl = WebHelper.getCurrentUrl();
      Assert.assertTrue(currentUrl.contains("apps.apple.com"), "App store page isn't displayed");
      Assert.assertTrue(currentUrl.contains("Peets"), "Peet's App store page isn't displayed");

      logger.info("Verify user is shown with correct touts titles and descriptions");
      storeDetailsPage =
          navigateToStoreLocatorPage().searchByZipCode("95032").getStockResultList().get(0).click();
    }

    ToutsSectionComponent toutsSectionComponent = storeDetailsPage.getToutsSectionComponent();
    Assert.assertTrue(
        toutsSectionComponent.isToutDisplayed(Touts.START_AN_ORDER),
        "Touts isn't displayed properly");
    Assert.assertTrue(
        toutsSectionComponent.isToutDisplayed(Touts.PLACE_ORDER_AND_PAY),
        "Touts isn't displayed properly");
    Assert.assertTrue(
        toutsSectionComponent.isToutDisplayed(Touts.PICK_UP_IN_STORE),
        "Touts isn't displayed properly");

    logger.info("Validate the user is shown with banner, description and Learn more button");
    PeetnikRewardsComponent peetnikRewardsComponent = storeDetailsPage.getPeetnikRewardsComponent();
    Assert.assertTrue(
        peetnikRewardsComponent.isDisplayed(), "Peetnik Rewards section isn't displayed properly");

    logger.info("6. Click on 'Learn more'");
    peetnikRewardsComponent.clickLearnMore();

    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.TestData.REWARDS_URL),
        "Not on the correct URL");

    if (WebHelper.isMobile()) {
      /*
       When we click on Download APP Button for android phone, we switch to App store native view
       and when we try to navigate back(with Android back button) the Chrome browser and native
       App store view close, so it makes tests not stable. Moved this step to the end for mobile.
      */
      logger.info("5. Click on Download APP Button");
      if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
        WebHelper.navigateBack(StoreDetailsPage.class);
      } else {
        WebHelper.clickAndroidBackButton();
      }
      SdkHelper.create(StoreDetailsPage.class).getOneAppManyBenefitsComponent().clickDownloadApp();

      logger.info("Verify App store page is displayed");
      Assert.assertTrue(WebHelper.nativeContextIsPresent(), "App store page isn't displayed");
    }
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.FIND_STORE},
      description = "11102903")
  public void backToResults() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    List<String> listOfStoreNames = storeLocatorPage.getListOfStoreNames();
    StoreDetailsPage storeDetailsPage = storeLocatorPage.getStockResultList().get(0).click();

    logger.info("5. Click on Back to results.");
    storeLocatorPage = storeDetailsPage.backToResults();

    logger.info("Verify that results list should display.");
    Assert.assertEquals(
        storeLocatorPage.getListOfStoreNames(), listOfStoreNames, "Results list isn't the same");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11102904")
  public void byCurrentLocation() {
    logger.info("1. Navigate to landing page");
    StoreLocatorPage storeLocatorPage = navigateToStoreLocatorPage();

    logger.info("Validate FIND A PEET’S NEAR YOU title is displayed");
    Assert.assertTrue(
        storeLocatorPage.isPageTitleDisplayed(), "FIND A PEET’S NEAR YOU title isn't displayed");

    logger.info("Validate the user is shown with description");
    Assert.assertTrue(
        storeLocatorPage.isPageDescriptionDisplayed(),
        "FIND A PEET’S NEAR YOU description isn't displayed");

    logger.info("2. Click on 'Use My Current Location'");
    storeLocatorPage = storeLocatorPage.clickUseMyCurrentLocation();

    logger.info("Verify that closest store should display.");
    Assert.assertTrue(storeLocatorPage.getListOfStoreNames().size() > 0, "Stores didn't appear");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.FIND_STORE},
      description = "11102905")
  public void zoomMapTest() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Zoom any part on the map");
    storeLocatorPage = storeLocatorPage.clickOnZoomInButton(2);

    logger.info("Verify that list of stores within the map should display.");
    Assert.assertTrue(storeLocatorPage.getListOfStoreNames().size() > 0, "Stores didn't appear");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.FIND_STORE},
      description = "11102906")
  public void thereAreNoStoresNearby() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any country without stores");
    storeLocatorPage = storeLocatorPage.searchByZipCode("France");

    logger.info("4. Zoom any part on the map");
    storeLocatorPage = storeLocatorPage.clickOnZoomInButton(3);

    logger.info("Verify that correct message and link is displayed");
    Assert.assertEquals(
        storeLocatorPage.getCountOfItemsFromResultTitle(),
        0,
        "Count of results from the title is wrong");
    Assert.assertEquals(
        storeLocatorPage.getResultMessage(),
        WebTestData.STORES_NO_RESULTS_MESSAGE,
        "'No results' message is wrong");
    Assert.assertTrue(storeLocatorPage.isShopOnlineDisplayed(), "'Shop Online' isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11107478")
  public void storeDetailsPageFromCoffeeBar() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    StockResultItemComponent stockResultItemComponent =
        storeLocatorPage.getStockResultList().get(0);
    StoreDetailsPage storeDetailsPage = stockResultItemComponent.click();

    logger.info("5. Validate the image, styling and content of this section is correct");
    CoffeeBarCarouselComponent carouselComponent = storeDetailsPage.getCoffeeBarCarouselComponent();
    Assert.assertTrue(carouselComponent.isHeaderIsDisplayed(), "Header isn't displayed");
    Assert.assertTrue(carouselComponent.isSubHeaderIsDisplayed(), "Sub-header isn't displayed");
    Assert.assertTrue(carouselComponent.isImageIsDisplayed(), "Image isn't displayed");
    Assert.assertTrue(carouselComponent.isDescriptionIsDisplayed(), "Description isn't displayed");

    logger.info("6. Verify four items are displayed");
    int visibleItemsNumber = WebHelper.isDesktop() ? 4 : 1;
    Assert.assertEquals(
        carouselComponent.getVisibleCoffeeBarItemNames().size(),
        visibleItemsNumber,
        String.format("Not %s items are displayed", visibleItemsNumber));

    logger.info(
        "7. Verify item image, name, description, price per LB and reviews are shown correctly");
    // TODO Description and price per LB are not displayed
    Assert.assertTrue(
        carouselComponent.areItemsDisplayedProperly(),
        "Not all items are displayed with name and image");

    logger.info("8. Validate the user is shown with different set of four items");
    Assert.assertTrue(
        carouselComponent.areUniqueItemsAreDisplayed(), "Not all items are different");

    logger.info(
        "9. Verify left and right arrow scrolls the bar, and that you can get to initial position after scroll");
    List<String> initialPositions = carouselComponent.getVisibleCoffeeBarItemNames();
    carouselComponent.clickCoffeeBarNext();
    List<String> nextPositions = carouselComponent.getVisibleCoffeeBarItemNames();

    softAssert.assertNotEquals(
        initialPositions.get(0),
        nextPositions.get(0),
        "The coffee bar items did not change when the next arrow was clicked.");

    carouselComponent.clickCoffeeBarPrevious();
    nextPositions = carouselComponent.getVisibleCoffeeBarItemNames();

    softAssert.assertEquals(
        initialPositions.get(0),
        nextPositions.get(0),
        "The coffee bar did not go back to the original list with previous clicked.");

    logger.info("10. Hover any Coffee item");
    CoffeeBarItemComponent itemComponent = carouselComponent.getCoffeeBarItemComponent(0);
    itemComponent.hoverOver();

    logger.info("11. Validate the user is shown with 'Order Now' button immediately");
    softAssert.assertTrue(itemComponent.isOrderNowDisplayed(), "Order Now is not displayed");

    logger.info("12. Click on 'Order Now'");
    itemComponent.clickOrderNowButton();

    logger.info("13. Validate the correct URL is shown");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.ORDER_PEETS_URL), "Wrong URL is shown");

    logger.info("14. Click on 'View all Seasonal Beverages' Button");
    WebHelper.closeOtherTabs();
    storeDetailsPage =
        navigateToStoreLocatorPage().searchByZipCode("95032").getStockResultList().get(0).click();
    storeDetailsPage.getCoffeeBarCarouselComponent().clickSeeAllSeasonalBeveragesButton();

    logger.info(
        "15. Validate the user is directed to \"https://peets-coffee-staging.myshopify.com/collections/seasonal\" page");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(HomepageCoffeeBar.SEASONAL_URL_PART),
        "Seasonal page is opened with wrong URL");
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11107479")
  public void storeDetailsCoffeeSubscriptionsSection() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Type any zip code (95032)");
    storeLocatorPage = storeLocatorPage.searchByZipCode("95032");

    logger.info("4. Click on a store.");
    StockResultItemComponent stockResultItemComponent =
        storeLocatorPage.getStockResultList().get(0);
    StoreDetailsPage storeDetailsPage = stockResultItemComponent.click();

    logger.info("5. Validate the image, styling and content of this section is correct");
    Assert.assertTrue(
        storeDetailsPage.isCoffeeSubscriptionHeaderDisplayed(),
        "Coffee subscription header isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isCoffeeSubscriptionDescriptionDisplayed(),
        "Coffee subscription description isn't displayed");
    Assert.assertTrue(
        storeDetailsPage.isCoffeeSubscriptionSeeOptionsButtonDisplayed(),
        "Coffee subscription 'See Subscription options' isn't displayed");

    logger.info("6. Click on the 'See Subscription options'");
    storeDetailsPage.clickCoffeeSubscriptionSeeOptionsButton();

    logger.info("7. Validate the user is redirected to the Subscription page");
    Assert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.SUBSCRIPTIONS_URL),
        String.format(
            "Subscription page is displayed. Expected URL: %s", TestData.SUBSCRIPTIONS_URL));
  }

  @Test(
      groups = {TestNGGroups.FRONT_END_REGRESSION, TestNGGroups.FIND_STORE},
      description = "11107480")
  public void storeDetailsNeverMissOfferSection() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on location from header.");
    StoreLocatorPage storeLocatorPage = productListPage.getHeader().clickOnStoreLocator();

    logger.info("3. Verifying offer heading");
    NeverMissOfferComponent neverMissOfferComponent = storeLocatorPage.getNeverMissOfferComponent();
    softAssert.assertEquals(
        Constants.HomepageNeverMissOffer.OFFER_TITLE.toLowerCase(),
        neverMissOfferComponent.getNeverMissOfferTitle().toLowerCase(),
        "Never Miss an offer section titles did not match; got: "
            + neverMissOfferComponent.getNeverMissOfferTitle().toLowerCase()
            + " expected: "
            + Constants.HomepageNeverMissOffer.OFFER_TITLE.toLowerCase());

    logger.info("4. Verifying offer description");
    softAssert.assertEquals(
        Constants.HomepageNeverMissOffer.OFFER_DESCRIPTION.toLowerCase(),
        neverMissOfferComponent.getNeverMissOfferDescription().toLowerCase(),
        "Never Miss an offer section descriptions did not match; got: "
            + neverMissOfferComponent.getNeverMissOfferDescription().toLowerCase()
            + " expected: "
            + Constants.HomepageNeverMissOffer.OFFER_DESCRIPTION.toLowerCase());

    logger.info("5. Verifying sign up button");
    SignUpPage signUpPage = neverMissOfferComponent.clickNeverMissOfferSignup();
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(Constants.HomepageNeverMissOffer.OFFER_URL_PART),
        "URL did not match;  expected "
            + Constants.HomepageNeverMissOffer.OFFER_URL_PART
            + " to be a part of"
            + signUpPage.getDriver().getCurrentUrl());

    logger.info("6. Validate upgrade to 10% section is displayed");
    softAssert.assertTrue(
        signUpPage.isTenPercentSectionDisplayed(), "First upgrade to 10% section is not displayed");

    logger.info("7. Enter valid Email id and click on 'Continue'");
    signUpPage = signUpPage.enterEmailAndClickContinue(WebHelper.getRandomMail());

    logger.info("8. Validate upgrade to 15% section is displayed");
    softAssert.assertTrue(
        signUpPage.isFifteenPercentSectionDisplayed(),
        "First upgrade to 15% section is not displayed");

    logger.info("9. Enter valid Mobile Number and click on 'Get 15% off'");
    signUpPage = signUpPage.enterMobileNumberAndSubmit(TestData.MOBILE_NUMBER);

    logger.info("10. Validate the user is shown with 'Check your Texts' message");
    softAssert.assertTrue(signUpPage.isSuccessPageHeaderDisplayed(), "Message isn't displayed");

    softAssert.assertAll();
  }
}
