package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import java.time.Duration;

@Implementation(is = OrderPage.class, on = Platform.WEB)
public class OrderPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "locationFinderPage", on = Platform.WEB)
  private ContainerElement mainContainer;

  /* -------- Actions -------- */
  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
  }
}
