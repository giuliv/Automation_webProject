package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.StoreLocatorPage;
import io.qameta.allure.Step;
import java.util.List;

@Implementation(is = StockResultFilterComponent.class, on = Platform.WEB)
@Implementation(is = StockResultFilterComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class StockResultFilterComponent extends BaseComponent {

  @Locate(xpath = "//label[contains(@class, 'filters-item')]", on = Platform.WEB)
  private List<Button> filterList;

  @Locate(xpath = "//button[contains(@class, 'filters-button')]", on = Platform.WEB)
  protected Button filterByButton;

  @Locate(xpath = "//div[contains(@class, 'loading')]", on = Platform.WEB)
  protected Button loadingAnimation;

  @Step("Select Filters on specific Position")
  public StockResultFilterComponent selectFiltersOnPosition(int... filterPosition) {
    ((LazyList<?>) filterList).initialize();
    for (int position : filterPosition) {
      Button button = filterList.get(position - 1);
      logger.info("Clicking on [{}] filter with [{}] name", position, button.getText());
      button.click();
    }
    return this;
  }

  @Step("Close filter menu")
  public StoreLocatorPage close() {
    logger.info("Clicking amenities to close filters");
    filterByButton.click();
    WebHelper.waitForElementToDisappear(loadingAnimation, 5);
    return SdkHelper.create(StoreLocatorPage.class);
  }
}
