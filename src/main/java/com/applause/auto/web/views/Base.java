package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.components.FooterComponent;
import com.applause.auto.web.components.Header;
import com.applause.auto.web.components.MyAccountLeftMenu;
import com.applause.auto.web.components.NeverMissOfferComponent;
import com.applause.auto.web.components.pdp.CoffeeBarCarouselComponent;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import io.qameta.allure.Step;

@Implementation(is = Base.class, on = Platform.WEB)
@Implementation(is = Base.class, on = Platform.WEB_MOBILE_PHONE)
public class Base extends BaseComponent {
  @Locate(id = "dismissbutton2header1", on = Platform.WEB)
  protected Button dismissBanner;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"close\"]", on = Platform.WEB)
  protected Button dismissBannerIOS;

  @Locate(css = "#attentive_creative", on = Platform.WEB)
  protected ContainerElement newBannerIFrame;

  @Step("Get header")
  public Header getHeader() {
    logger.info("Getting the header");
    return SdkHelper.create(Header.class);
  }

  @Step("Get Left menu")
  public MyAccountLeftMenu getLeftMenu() {
    logger.info("Getting the left menu");
    return SdkHelper.create(MyAccountLeftMenu.class);
  }

  @Step("Get Footer component")
  public FooterComponent getFooterComponent() {
    return SdkHelper.create(FooterComponent.class);
  }

  @Step("Get Coffee Bar Carousel component")
  public CoffeeBarCarouselComponent getCoffeeBarCarouselComponent() {
    return SdkHelper.create(CoffeeBarCarouselComponent.class);
  }

  @Step("Get Never Miss Offer component")
  public NeverMissOfferComponent getNeverMissOfferComponent() {
    return SdkHelper.create(NeverMissOfferComponent.class);
  }
}
