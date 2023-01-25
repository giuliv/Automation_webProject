package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;

@Implementation(is = FindACoffeeBarPage.class, on = Platform.WEB)
public class FindACoffeeBarPage extends Base {

  @Locate(id = "storeLocator", on = Platform.WEB)
  protected Text pageHeading;

  @Locate(css = ".dwmodal-box .dwmodal-close-button", on = Platform.WEB)
  protected Button closePopUp;

  @Override
  public void afterInit() {
    super.afterInit();
    if (WebHelper.exists(closePopUp, 5)) {
      if (closePopUp.isDisplayed()) {
        closePopUp.click();
      }
    }

    SdkHelper.getSyncHelper().wait(Until.uiElement(pageHeading).present());
  }
}
