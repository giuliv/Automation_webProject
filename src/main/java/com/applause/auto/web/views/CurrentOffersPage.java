package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

@Implementation(is = CurrentOffersPage.class, on = Platform.WEB)
public class CurrentOffersPage extends Base {

  @Locate(css = "#currentOffersGrid", on = Platform.WEB)
  protected ContainerElement offerSection;

  public boolean areMainOffersDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(offerSection).visible());
    return offerSection.isDisplayed();
  }
}
