package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;

import java.util.List;

@Implementation(is = ProductListPage.class, on = Platform.WEB)
@Implementation(is = ProductListPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductListPage extends Base {

  @Locate(className = "collection__app", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".collection__grid article", on = Platform.WEB)
  private List<ContainerElement> productList;

  //  @Locate(
  //      css = ".collection__grid article, .collection__grid li img",
  //      on = Platform.WEB) // Both [shopify/peets]
  //  @Locate(css = ".collection__grid li img", on = Platform.WEB) //out of stock issue
  @Locate(
      css = ".collection__grid li a.pi__link > div:first-child:not(.pi__badge)",
      on = Platform.WEB)
  private List<Image> productsImageList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Products List Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));

    SdkHelper.getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));

    productsImageList.get(index).click();
    return SdkHelper.create(ProductDetailsPage.class);
  }
}
