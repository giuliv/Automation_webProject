package com.applause.auto.test.new_web.products;

import com.applause.auto.common.data.Constants.SortType;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.enums.Filters;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.SignUpPage;
import com.applause.auto.test.new_web.BaseTest;
import java.util.List;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class ProductsTestHelper extends BaseTest {

  public static void checkPlp(ProductListPage productListPage, SoftAssert softAssert) {
    String currentUrl = WebHelper.getCurrentUrl();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("1. Validate PLP Logo is displayed");
    Header header = productListPage.getHeader();
    softAssert.assertTrue(header.isLogoDisplayed(), "PLP Logo isn't displayed");

    logger.info("2. At PLP page --> Click on Peets Logo");
    HomePage homePage = header.clickLogoButton();

    logger.info("3. Validate the user is directed to Homepage");
    softAssert.assertNotNull(homePage, "The user isn't directed to Homepage");

    logger.info("4. Navigate back to PLP page");
    productListPage = WebHelper.navigateToUrl(currentUrl, ProductListPage.class);

    logger.info("5. Verify person icon");
    header = productListPage.getHeader();
    softAssert.assertTrue(header.isPersonIconDisplayed(), "Person Icon did not display.");

    logger.info("6. Verify search icon");
    softAssert.assertTrue(header.isSearchIconDisplayed(), "Search icon did not display.");

    logger.info("7. Verify shopping cart");
    softAssert.assertTrue(header.isCartIconDisplayed(), "Shopping Cart icon did not display");

    logger.info("8. Verify location icon");
    softAssert.assertTrue(header.isLocationIconDisplayed(), "Location icon did not display.");
    softAssert.assertAll();
  }

  public static void checkBestSellersBanner(
      ProductListPage productListPage, String expectedPageHeader, SoftAssert softAssert) {
    logger.info("1. Navigate to Product list page");
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate banner image and content is displayed");
    softAssert.assertTrue(
        productListPage.getPageHeader().equalsIgnoreCase(expectedPageHeader),
        "Wrong page header is displayed");
    softAssert.assertTrue(
        productListPage.isBannerContentDisplayed(), "Banner content isn't displayed");
    softAssert.assertAll();
  }

  public static void checkFilters(
      ProductListPage productListPage, List<Filters> filters, SoftAssert softAssert) {
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("1. Validate below Coffee Path --> User is shown with Different Coffee Filters");
    softAssert.assertTrue(
        productListPage.areFilterDisplayed(Filters.values()), "Not all filters are displayed");

    logger.info("2. Validate the user is shown with expected options");
    int totalResultsBeforeTest = productListPage.getTotalResults();
    for (Filters filter : filters) {
      selectMultipleOptions(productListPage, filter, softAssert);
    }

    logger.info("3. At All Coffee --> Filters --> Remove Filters individually --> Click on 'tags'");
    int numberOfTags = productListPage.getNumberOfSelectedFilters();
    productListPage.removeSelectedFilterByIndex(1);

    logger.info("4. Validate the selected options are removed and the tags can be cleared");
    softAssert.assertTrue(
        productListPage.getNumberOfSelectedFilters() < numberOfTags,
        "Selected filter wasn't removed");

    logger.info("5. At All Coffee --> Filters -->Click on Clear All Filters individually");
    productListPage.clearAllFilters();

    logger.info("6. Validate all the filters are cleared and all items are shown");
    softAssert.assertEquals(
        productListPage.getNumberOfSelectedFilters(), 0, "Not all filters were removed");
    softAssert.assertEquals(
        productListPage.getTotalResults(),
        totalResultsBeforeTest,
        "Total results has been changed");
    softAssert.assertAll();
  }

  public static void checkSortingOptions(ProductListPage productListPage, SoftAssert softAssert) {
    logger.info("1. Navigate to Product list page");
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate the user is shown with items Sort by recommended on default");
    softAssert.assertTrue(
        productListPage
            .getSelectedSortingType()
            .equalsIgnoreCase(SortType.RECOMMENDED.getOptionName()),
        "Recommended sorting type isn't selected");

    //    selectSortingTypeAndCheckResultList(productListPage, SortType.PRICE_LOW_TO_HIGH,
    // softAssert);
    //    selectSortingTypeAndCheckResultList(productListPage, SortType.PRICE_HIGH_TO_LOW,
    // softAssert);
    //    selectSortingTypeAndCheckResultList(productListPage, SortType.NEWEST, softAssert);
    selectSortingTypeAndCheckResultList(productListPage, SortType.NAME_A_Z, softAssert);
    selectSortingTypeAndCheckResultList(productListPage, SortType.NAME_Z_A, softAssert);

    softAssert.assertAll();
  }

  public static void checkNewSortingOptions(
      ProductListPage productListPage, SoftAssert softAssert) {
    logger.info("1. Navigate to Product list page");
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate the user is shown with items Sort by recommended on default");
    softAssert.assertTrue(
        productListPage
            .getSelectedSortingType()
            .equalsIgnoreCase(SortType.RECOMMENDED.getOptionName()),
        "Recommended sorting type isn't selected");

    selectSortingTypeAndCheckResultList(productListPage, SortType.PRICE_LOW_TO_HIGH, softAssert);
    selectSortingTypeAndCheckResultList(productListPage, SortType.NEWEST, softAssert);
    selectSortingTypeAndCheckResultList(productListPage, SortType.NAME_A_Z, softAssert);
    selectSortingTypeAndCheckResultList(productListPage, SortType.NAME_Z_A, softAssert);

    softAssert.assertAll();
  }

  public static void checkNewsletterSignUpNeverMissOffer(
      ProductListPage productListPage, SoftAssert softAssert) {
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("1. Validate the styling, content, button to Sign up of this section is correct");
    softAssert.assertTrue(
        productListPage.isNeverMissOfferSignUpButtonDisplayed(), "Sign Up button isn't displayed");
    softAssert.assertTrue(
        productListPage.isNeverMissOfferTitleDisplayed(), "Title isn't displayed");
    softAssert.assertTrue(
        productListPage.isNeverMissOfferContentDisplayed(), "Content button isn't displayed");

    logger.info("2. Never Miss an Offer section -> click on 'Sign up'");
    SignUpPage signUpPage = productListPage.clickNeverMissOfferSignUpButton();

    logger.info("3. Validate the user is directed to https://www.peets.com/pages/email-signup");
    softAssert.assertNotNull(signUpPage, "Sign up page isn't displayed");
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.EMAIL_SIGNUP_URL), "Wrong URL is displayed");
    softAssert.assertAll();
  }

  public static void selectMultipleOptions(
      ProductListPage productListPage, Filters filter, SoftAssert softAssert) {
    logger.info("1. Validate the user is shown with expected options below: {}", filter.getName());
    int numberOfSelectedOptions = productListPage.getNumberOfSelectedFilters();
    softAssert.assertTrue(
        productListPage.areFilterOptionsDisplayed(filter),
        "Not all filter options are displayed below: " + filter.getName());

    if (!filter.getOptions().isEmpty()) {
      logger.info("2. Select one of the options");
      int originResults = productListPage.getTotalResults();
      String firstOption = filter.getOptions().get(0);
      productListPage.applyFilterByName(filter, firstOption);

      logger.info("3. Validate the user is now shown with items with that particular option");
      softAssert.assertEquals(
          firstOption,
          productListPage.getFilterAppliedPillByIndex(numberOfSelectedOptions),
          "Correct filter was not applied");
      int newResults = productListPage.getTotalResults();
      softAssert.assertTrue(
          originResults > newResults, "Total results are not correct after filter");

      logger.info("4. Select two of the options");
      String secondOption = filter.getOptions().get(1);
      productListPage.applyFilterByName(filter, secondOption);

      logger.info("5. Validate the user can select multiple options at the same time");
      softAssert.assertEquals(
          secondOption,
          productListPage.getFilterAppliedPillByIndex(numberOfSelectedOptions + 1),
          "Correct second filter was not applied");
      softAssert.assertTrue(
          originResults > productListPage.getTotalResults(),
          "Total results are not correct after second filter");
    }
  }

  public static void selectSortingTypeAndCheckResultList(
      ProductListPage productListPage, SortType sortType, SoftAssert softAssert) {
    logger.info("1. At All Coffee --> Sort --> click on: {}", sortType.getName());
    productListPage = productListPage.selectSortingByType(sortType);

    logger.info("2. Validate the items are sorted by: {}", sortType.getName());
    softAssert.assertTrue(
        productListPage.validateSortingOptionResults(sortType),
        String.format("The items are sorted by: %s", sortType.getName()));
  }
}
