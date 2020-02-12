package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = SearchResultsPage.class, on = Platform.WEB)
public class SearchResultsPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = ".shown-results", on = Platform.WEB)
  private Text getResultsText;

  @Locate(
      css = "#product_addtocart_form10995 > div.image-holder > div > a > picture > img",
      on = Platform.WEB)
  private Button getFirstProduct;

  @Locate(
      css =
          "#product_addtocart_form10008 > div.image-holder > div.product-image.americas > a > picture > img",
      on = Platform.WEB)
  private Button getKonaProduct;

  /* -------- Actions -------- */

  /**
   * Click First Product
   *
   * @return CoffeeProductDescriptionPage
   */
  public CoffeeProductDescriptionPage clickFirstProduct() {
    logger.info("Clicking First Product");
    getFirstProduct.click();
    return ComponentFactory.create(CoffeeProductDescriptionPage.class);
  }

  /**
   * Click Kona Product
   *
   * @return CoffeeProductDescriptionPage
   */
  public CoffeeProductDescriptionPage clickKona() {
    logger.info("Clicking First Product");
    getKonaProduct.click();
    return ComponentFactory.create(CoffeeProductDescriptionPage.class);
  }
}
