package com.applause.auto.test.new_web.plp;

import com.applause.auto.common.data.Constants;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.views.ProductListPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class quickViewTest extends BaseTest {

  @Test(
      groups = {Constants.TestNGGroups.PLP},
      description = "TBD")
  public void reviewQuickViewUITest() {

    logger.info("1. Navigate to landing page");
    ProductListPage productListPage = navigateToPLP();
    Assert.assertNotNull(productListPage, "Failed to navigate to Product Listing Page");

    logger.info("2. Validate UI Elements of QuickView Modal");
    QuickViewComponent quickViewComponent = productListPage.clickOverFirstQuickViewButton();
    quickViewComponent.validateMainUIElements().assertAll();

    logger.info("FINISH");
  }
}
