package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = FAQPage.class, on = Platform.WEB)
public class FAQPage extends BaseComponent {

  @Locate(css = ".article-sidebar", on = Platform.WEB)
  private Text viewSignature;

  @Locate(xpath = "//h1", on = Platform.WEB)
  private Text title;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(viewSignature).visible());
  }

  @Step("Get Page Title")
  public String getTitle() {
    String pageTitle = WebHelper.cleanString(title.getText());
    logger.info("Page Title - [{}]", pageTitle);
    return pageTitle;
  }
}
