package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.integrations.helpers.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

import java.util.List;

@Implementation(is = ProductListPage.class, on = Platform.WEB)
@Implementation(is = ProductListPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductListPage extends Base {

  @Locate(className = "collection__app", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".collection__grid article", on = Platform.WEB)
  private List<ContainerElement> productList;

  @Locate(css = ".collection__grid article, .collection__grid li img", on = Platform.WEB)
  private List<Image> productsImageList;

  @Override
  public void afterInit() {
    getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Products List Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));

    getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));

    productsImageList.get(index).click();
    return SdkHelper.create(ProductDetailsPage.class);
  }
}
