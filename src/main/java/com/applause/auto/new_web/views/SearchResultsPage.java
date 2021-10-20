package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import io.qameta.allure.Step;

import java.util.List;

@Implementation(is = SearchResultsPage.class, on = Platform.WEB)
@Implementation(is = SearchResultsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchResultsPage extends Base {

  @Locate(id = "searchOverlay", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".collection__grid li a.pi__link", on = Platform.WEB)
  private List<Image> productsImageList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    logger.info("Search Results List Page URL: " + getDriver().getCurrentUrl());
  }

  @Step("Click on product")
  public ProductDetailsPage clickOverProductByIndex(int index) {
    logger.info("Click over product with index: " + index);
    WebHelper.scrollToElement(productsImageList.get(index));
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll

    SdkHelper.getSyncHelper().wait(Until.uiElement(productsImageList.get(index)).visible());
    WebHelper.scrollToElement(productsImageList.get(index));

    productsImageList.get(index).click();
    return SdkHelper.create(ProductDetailsPage.class);
  }
}
