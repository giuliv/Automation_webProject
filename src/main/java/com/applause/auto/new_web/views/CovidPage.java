package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;

import java.time.Duration;

@Implementation(is = CovidPage.class, on = Platform.WEB)
public class CovidPage extends BaseComponent {

  @Locate(css = "#page_covid_19", on = Platform.WEB)
  private ContainerElement covidPageHeader;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(covidPageHeader).visible().setTimeout(Duration.ofSeconds(40)));
  }
}
