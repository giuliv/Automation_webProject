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

  @Locate(
      css = ".collection__grid li a.pi__link > div:first-child:not(.pi__badge)",
      on = Platform.WEB)
  private List<Image> productsImageList;

  @Locate(
      css = ".collection__grid li a.pi__link[href*='reserve'] > div:first-child:not(.pi__badge)",
      on = Platform.WEB)
  private List<Image> reserveProductsImageList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
    logger.info("Products List Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    if (WebHelper.isSafari() && WebHelper.isDesktop()) {
      WebHelper.jsClick(productsImageList.get(index).getWebElement());
    } else {
      productsImageList.get(index).click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }

  public ProductDetailsPage clickOverReserveProductByIndex(int index) {
    logger.info("Click over reserve product with index: " + index);
    WebHelper.scrollToElement(reserveProductsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(reserveProductsImageList.get(index)).visible());
    WebHelper.scrollToElement(reserveProductsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    if (WebHelper.isSafari() && WebHelper.isDesktop()) {
      WebHelper.jsClick(reserveProductsImageList.get(index).getWebElement());
    } else {
      reserveProductsImageList.get(index).click();
    }

    return SdkHelper.create(ProductDetailsPage.class);
  }
}
