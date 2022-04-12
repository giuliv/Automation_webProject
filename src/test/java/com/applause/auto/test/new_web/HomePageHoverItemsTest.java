package com.applause.auto.test.new_web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.enums.CoffeeSubMenu;
import com.applause.auto.common.data.enums.TeaSubMenu;
import com.applause.auto.new_web.components.FooterComponent;
import com.applause.auto.new_web.components.Header;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageHoverItemsTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.HOME_PAGE},
      description = "11107439")
  public void homepageEndofPageDescriptionTest() {
    logger.info("1. Navigate to Home page");
    HomePage homePage = navigateToHome();
    softAssert.assertNotNull(homePage, "Failed to navigate to the Home page.");

    logger.info("Verify End of the page Description");
    FooterComponent footer = homePage.getFooterComponent();

    Assert.assertEquals(
        footer.getEndOfThePageDescription(),
        Constants.TestData.END_OF_PAGE_DESCRIPTION,
        "End of the page Description Mismatches");
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION},
      description = "11107440")
  public void hoverCoffeOptions() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    Header header = homePage.getHeader();

    logger.info("2. Hover and Verify Coffee Menu and All SubMenu Items");
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    for (CoffeeSubMenu menuItem : CoffeeSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }

    // Todo: Online Exclusives is not displayed on the
    logger.info(
        "3. Hover and Click Coffee Hover Each SubMenu Item and Verify it is redirected to the Corresponding Item page.");
    for (CoffeeSubMenu menuItem : CoffeeSubMenu.values()) {
      logger.info("Click MenuItem: " + menuItem.getText());
      if (menuItem.getText().contains(TestData.ALL_COFFEE_HOVER)) {
        header.clickSubMenuItemAllLinks(menuItem.getLink());
      } else {
        header.clickSubMenuItem(menuItem.getLink());
      }
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(menuItem.getLink()),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    }

    logger.info(
        "4. Hover and Click Coffee Hover anniversary-blend and Verify it is redirected to the Corresponding Item page.");
    header.clickCoffeeSubMenuAnnaversiryAndSumatraLinks(TestData.COFFEE_HOVER_BLEND);
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.COFFEE_HOVER_BLEND),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());

    logger.info(
        "5. Hover and Click Coffee Hover sumatra-batak and Verify it is redirected to the Corresponding Item page.");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
    header.clickCoffeeSubMenuAnnaversiryAndSumatraLinks(TestData.COFFEE_HOVER_SUMATRA);
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.COFFEE_HOVER_SUMATRA),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());
    // Todo: Find your Match is displaying for only in Desktop
    if (WebHelper.isDesktop()) {
      logger.info(
          "6. Hover and Click Coffee Hover Find Your Match and Verify it is redirected to the Corresponding Item page.");
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.COFFEE);
      header.clickCoffeeFindYourMatch(TestData.COFFEE_HOVER_FINDER);
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(TestData.COFFEE_HOVER_FINDER),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
    }
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.WEB_REGRESSION},
      description = "11107441")
  public void hoverTeaOptions() {
    logger.info("1. Navigate to landing page");
    HomePage homePage = navigateToHome();
    Assert.assertNotNull(homePage, "Failed to navigate to the landing page.");
    Header header = homePage.getHeader();

    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    for (TeaSubMenu menuItem : TeaSubMenu.values()) {
      logger.info("Verifying: " + menuItem.getText());
      softAssert.assertTrue(
          header.isSubMenuItemDisplayed(menuItem.getLink()),
          menuItem.getText() + " did not display.");
    }
    // Todo: Gift Sets,Gifts,Equipment,Online Exclusives are not displayed on the
    logger.info(
        "3. Hover and Click Tead Menu Each SubMenu Items and Verify it is redirected to the Corresponding Item page.");
    for (TeaSubMenu menuItem : TeaSubMenu.values()) {
      logger.info("Click MenuItem: " + menuItem.getText());
      if (menuItem.getText().contains(TestData.ALL_TEA_HOVER)) {
        header.clickSubMenuItemAllLinks(menuItem.getLink());
      } else {
        header.clickSubMenuItem(menuItem.getLink());
      }
      softAssert.assertTrue(
          WebHelper.getCurrentUrl().contains(menuItem.getLink()),
          "New Page is not redirected: " + WebHelper.getCurrentUrl());
      homePage = navigateToHome();
      header = homePage.getHeader();
      header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    }

    logger.info(
        "4. Hover and Click Tea Hover Find Your Tea Match and Verify it is redirected to the Corresponding Item page.");
    header.clickTeaSubMenuTeaMatchAndMightyLeafLinks(TestData.TEA_HOVER_FINDER);
    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.TEA_HOVER_FINDER),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());

    logger.info(
        "5. Hover and Click Tea Hover Mighty Leaf and Verify it is redirected to the Corresponding Item page.");
    homePage = navigateToHome();
    header = homePage.getHeader();
    header.hoverCategoryFromMenu(Constants.MenuOptions.TEA);
    header.clickTeaSubMenuTeaMatchAndMightyLeafLinks(TestData.TEA_HOVER_MIGHTY_LEAF);

    softAssert.assertTrue(
        WebHelper.getCurrentUrl().contains(TestData.TEA_HOVER_MIGHTY_LEAF),
        "New Page is not redirected: " + WebHelper.getCurrentUrl());

    softAssert.assertAll();
  }
}
