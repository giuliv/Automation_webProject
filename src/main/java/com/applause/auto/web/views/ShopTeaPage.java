package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = ShopTeaPage.class, on = Platform.WEB)
public class ShopTeaPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//div[@class='top-banner hero']//div[@class='text-content']/h1[contains(.,'Tea')]",
      on = Platform.WEB)
  private Text getViewSignature;

  @Locate(xpath = "//a[@class='product-link' and contains(.,'%s')]", on = Platform.WEB)
  private Button productNameButton;

  @Locate(css = "#modal-mighty-leaf-2018 > div > div > div > div.btn-holder > a", on = Platform.WEB)
  private Button closePopupButton;

  /* -------- Actions -------- */

  /**
   * Clicks a product name under the Tea Page
   *
   * @return a Tea Product Page
   */
  public TeaProductPage clickProductName(String productName) {
    logger.info(String.format("Tap on Product Name: %s", productName));
    try {
      closePopupButton.click();
    } catch (Exception ex) {
      logger.info("No popup displayed");
    }
    productNameButton.initializeWithFormat(productName);
    productNameButton.scrollToElement();
    productNameButton.click();
    return SdkHelper.create(TeaProductPage.class);
  }
}
