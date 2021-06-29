package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = SearchComponent.class, on = Platform.WEB)
@Implementation(is = SearchComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class SearchComponent extends BaseComponent {

  @Locate(id = "searchMenu", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "searchInput", on = Platform.WEB)
  private TextBox searchBox;

  @Locate(id = "searchMenuBtn", on = Platform.WEB)
  private Button searchButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */
  public SearchResultsPage search(String product) {
    logger.info("Searching for: " + product);
    searchBox.sendKeys(product);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action

    searchButton.click();
    return SdkHelper.create(SearchResultsPage.class);
  }
}
