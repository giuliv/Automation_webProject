package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.CoffeeFinderData;
import com.applause.auto.common.data.Constants.MenuSubCategories;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.CoffeeFinderAnswers;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.BrewGuidesPage;
import com.applause.auto.new_web.views.CoffeeFinderPage;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.new_web.views.WeHaveFoundYourCoffeePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoffeeFinderTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.COFFEE_FINDER, TestNGGroups.SMOKE},
      description = "11102941")
  public void coffeeFinderElements() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on Coffee in header and then Coffee finder.");
    Header header = productListPage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    CoffeeFinderPage coffeeFinderPage =
        header.clickOverSubCategoryFromMenu(
            CoffeeFinderPage.class, MenuSubCategories.COFFEE_FINDER);

    logger.info(
        "Verify that How do you prepare your coffee? title, Did you know? text and tabs with type of coffee displays.");
    Assert.assertEquals(
        coffeeFinderPage.getTitle().toLowerCase(),
        CoffeeFinderData.COFFEE_FINDER_TITLE.toLowerCase(),
        "Page title isn't correct");
    Assert.assertTrue(
        coffeeFinderPage.isDidYouKnowButtonDisplayed(), "'Did you know?' button isn't displayed");
    Assert.assertTrue(
        coffeeFinderPage.getTypeOfCoffeeList().size() > 0,
        "Tabs with type of coffee didn't appear");

    logger.info("3. Click on Did you know?");
    coffeeFinderPage = coffeeFinderPage.clickOnDidYouKnowButton();

    logger.info("Verify that Text displays.");
    Assert.assertTrue(
        coffeeFinderPage.isDidYouKnowTextDisplayed(), "'Did You Know' Text isn't Displayed");

    logger.info("4. Click on 'Did you know?' again");
    coffeeFinderPage = coffeeFinderPage.clickOnDidYouKnowButton();

    logger.info("Verify that Text isn't displays.");
    Assert.assertFalse(
        coffeeFinderPage.isDidYouKnowTextDisplayed(), "'Did You Know' Text is still Displayed");

    logger.info("5. Click on Did you know?");
    coffeeFinderPage = coffeeFinderPage.clickOnDidYouKnowButton();

    logger.info("6. Click on Brew guides");
    BrewGuidesPage brewGuidesPage = coffeeFinderPage.clickOnBrewGuidesButton();

    logger.info("Verify that another page with Brew guidelines open.");
    Assert.assertTrue(brewGuidesPage.isDisplayed(), "Brew guidelines page didn't appear");
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.COFFEE_FINDER},
      description = "11102942",
      enabled = false)
  // Todo: We need to figure out the issue on mobile view[20/04/2022]
  public void selectDeselectFlavors() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on Coffee in header and then Coffee finder.");
    Header header = productListPage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    CoffeeFinderPage coffeeFinderPage =
        header.clickOverSubCategoryFromMenu(
            CoffeeFinderPage.class, MenuSubCategories.COFFEE_FINDER);

    logger.info("3. Click on Drip.");
    coffeeFinderPage =
        coffeeFinderPage.selectAnswer(CoffeeFinderAnswers.DRIP, CoffeeFinderPage.class);

    logger.info("4. Click on 'I just know I like it'.");
    coffeeFinderPage =
        coffeeFinderPage.selectAnswer(
            CoffeeFinderAnswers.I_JUST_KNOW_I_LIKE_IT, CoffeeFinderPage.class);

    logger.info("5. Select two flavors");
    coffeeFinderPage = coffeeFinderPage.selectFlavor(1).selectFlavor(2);

    logger.info(
        "Verify that image should display on the center of the wheel, flavors should highlight and pills matching flavor name should display.");
    Assert.assertEquals(
        coffeeFinderPage.getCountOfSelectedFlavorsInTheCircle(),
        2,
        "Flavors were not selected in the circle");
    Assert.assertEquals(
        coffeeFinderPage.getSelectedFlavorsList().size(), 2, "Flavors were not selected");

    if (!WebHelper.isDesktop()) {
      logger.info("6. Click on a pill");
      coffeeFinderPage.getSelectedFlavorsList().get(1).click();

      logger.info("Verify that flavor should get deselected.");
      Assert.assertEquals(
          coffeeFinderPage.getCountOfSelectedFlavorsInTheCircle(),
          1,
          "Flavors were not deselected in the circle");
      Assert.assertEquals(
          coffeeFinderPage.getSelectedFlavorsList().size(), 1, "Flavors were not de selected");
    }
  }

  @Test(
      groups = {TestNGGroups.TO_BE_RENAMED, TestNGGroups.COFFEE_FINDER},
      description = "11102943",
      enabled = false)
  // Todo: Shilpa market as not shown in staging[20/04/2022]
  public void successfulCoffeeSelection() {
    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLPFromHome();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Click on Coffee in header and then Coffee finder.");
    Header header = productListPage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    CoffeeFinderPage coffeeFinderPage =
        header.clickOverSubCategoryFromMenu(
            CoffeeFinderPage.class, MenuSubCategories.COFFEE_FINDER);

    logger.info("3. Click on Pour over.");
    coffeeFinderPage =
        coffeeFinderPage.selectAnswer(CoffeeFinderAnswers.POUR_OVER, CoffeeFinderPage.class);

    logger.info("4. Click on 'I just know I like it'.");
    coffeeFinderPage =
        coffeeFinderPage.selectAnswer(
            CoffeeFinderAnswers.I_JUST_KNOW_I_LIKE_IT, CoffeeFinderPage.class);

    logger.info("5. Select a flavor");
    coffeeFinderPage = coffeeFinderPage.selectFlavor(1);

    logger.info("6. Click on Next question.");
    coffeeFinderPage = coffeeFinderPage.clickOnNextQuestionButton();

    logger.info("7. Click on Full caffeine.");
    coffeeFinderPage =
        coffeeFinderPage.selectAnswer(CoffeeFinderAnswers.FULL_CAFFEINE, CoffeeFinderPage.class);

    logger.info("8. Click on a New everyday coffee");
    WeHaveFoundYourCoffeePage weHaveFoundYourCoffeePage =
        coffeeFinderPage.selectAnswer(
            CoffeeFinderAnswers.A_NEW_EVERYDAY_COFFEE, WeHaveFoundYourCoffeePage.class);

    // skip email subscribe
    logger.info("Click on 'Just take me to my coffee'.");
    coffeeFinderPage = coffeeFinderPage.clickOnTakeMeToMyResults();

    logger.info(
        "Verify that We've found your coffee! success message displays with product image, name and price, add to cart, product details and shop all coffee links.");
    softAssert.assertEquals(
        weHaveFoundYourCoffeePage.getTitle().toLowerCase(),
        CoffeeFinderData.WE_HAVE_FOUND_YOUR_COFFEE.toLowerCase(),
        "Page title isn't correct");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isProductImageDisplayed(), "Product image isn't displayed");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isProductNameDisplayed(), "Product name isn't displayed");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isProductPriceDisplayed(), "Product price isn't displayed");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isAddToCartDisplayed(), "'Add to cart' isn't displayed");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isProductDetailsDisplayed(), "'Product Details' isn't displayed");
    softAssert.assertTrue(
        weHaveFoundYourCoffeePage.isShopAllCoffeeDisplayed(), "'Shop all Coffee' isn't displayed");

    softAssert.assertAll();
  }
}
