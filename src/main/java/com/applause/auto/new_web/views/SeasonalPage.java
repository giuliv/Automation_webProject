package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import java.time.Duration;

@Implementation(is = SeasonalPage.class, on = Platform.WEB)
public class SeasonalPage extends BaseComponent {

  @Locate(css = "section.page-hero", on = Platform.WEB)
  private ContainerElement topSection;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(topSection).visible().setTimeout(Duration.ofSeconds(40)));
  }
}
