package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = ShopEquipmentPage.class, on = Platform.WEB)
public class ShopEquipmentPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//div[contains(@class,'top-banner')]//div[@class='text-content']/h1[contains(.,'Equipment')]",
      on = Platform.WEB)
  private Text getViewSignature;

  @Locate(xpath = "//a[@class='product-link' and contains(.,'%s')]", on = Platform.WEB)
  private Button productNameButton;

  /* -------- Actions -------- */

  /**
   * Clicks a product name under the Tea Page
   *
   * @return a Tea Product Page
   */
  public EquipmentProductPage clickProductName(String productName) {
    logger.info(String.format("Tap on Product Name: %s", productName));
    productNameButton.initializeWithFormat(productName);
    productNameButton.click();
    return ComponentFactory.create(EquipmentProductPage.class);
  }
}
