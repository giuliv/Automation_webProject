package com.applause.auto.new_web.components.pdp;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.common.data.enums.SortOption;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.ReviewItemComponent;
import com.applause.auto.new_web.components.WriteReviewComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.google.common.collect.Ordering;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsCustomerReviewsComponent extends BaseComponent {

  @Locate(id = "productReviews", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "div.bv-control-bar-sort select", on = Platform.WEB)
  private SelectList sortByDropdown;

  @Locate(css = "li.bv-content-item", on = Platform.WEB)
  private List<ReviewItemComponent> reviews;

  @Locate(css = ".bv-inline-histogram-ratings button span", on = Platform.WEB)
  private List<Text> averageCustomerRatings;

  @Locate(css = "p.pv-reviews__total", on = Platform.WEB)
  private Text reviewsTotalNumber;

  @Locate(css = "span.bv-secondary-rating-summary-rating", on = Platform.WEB)
  private Text averageCustomerRating;

  @Locate(
      css = "div[data-bv-histogram-rating-value='%s'] span.bv-content-secondary-ratings-value",
      on = Platform.WEB)
  private ContainerElement starRatingBar;

  @Locate(
      xpath =
          "//div[@class='bv-inline-histogram-ratings-star' and contains(text(),'%s')]/../div[@class='bv-inline-histogram-ratings-score']/span",
      on = Platform.WEB)
  private Text ratingScoreForStar;

  @Locate(css = ".bv-inline-histogram-ratings button[id*='%s']", on = Platform.WEB)
  private Button reviewsStarButton;

  @Locate(css = "button.bv-active-filter-button-clear", on = Platform.WEB)
  private Button clearAllButton;

  @Locate(css = "button.bv-write-review", on = Platform.WEB)
  private Button writeReviewButton;

  @Locate(css = "button.pv-reviews__expand", on = Platform.WEB)
  private Button seeMoreReviews;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
    WebHelper.scrollToElement(mainContainer);
  }

  @Step("Get selected sort by option")
  public String getSelectedSortByOption() {
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(sortByDropdown);
    String selectedOption = sortByDropdown.getSelectedOption().getText().trim();
    logger.info("Selected sort option: {}", selectedOption);
    return selectedOption;
  }

  @Step("Verify sort by dropdown contains all expected options")
  public boolean areSortByOptionsDisplayed(List<String> expectedOptions) {
    logger.info("Checking Sort by dropdown contains all expected options: {}", expectedOptions);
    WebHelper.scrollToElement(sortByDropdown);
    List<String> options =
        sortByDropdown.getOptions().stream()
            .map(ContainerElement::getText)
            .collect(Collectors.toList());
    logger.info("Current options: {}", options);
    return options.containsAll(expectedOptions);
  }

  @Step("Select Sort by option")
  public void selectSortByOption(SortOption option) {
    logger.info("Selecting Sort by: {}", option);
    switch (option) {
      case LOWEST_TO_HIGHEST_RATING:
        ContainerElement element =
            sortByDropdown.getOptions().stream()
                .filter(
                    opt ->
                        opt.getAttributeValue(Attribute.VALUE.getValue()).equals(option.getValue()))
                .findFirst()
                .get();
        WebHelper.jsClick(element.getWebElement());
        break;
      default:
        throw new RuntimeException(
            String.format("The method hasn't implemented for '{}' yet", option));
    }
    SdkHelper.getSyncHelper().sleep(2000); // wait for sorting process complete
  }

  @Step("Verify list of reviews is sorted properly")
  public boolean areReviewsSortedProperly(SortOption sortByOption) {
    logger.info("Checking reviews are sorted by: {}", sortByOption.getName());
    switch (sortByOption) {
      case LOWEST_TO_HIGHEST_RATING:
        return Ordering.natural().isOrdered(getReviewsRatings());
      default:
        throw new RuntimeException(
            String.format(
                "Method isn't implemented for Sort by: '%s' yet", sortByOption.getName()));
    }
  }

  public List<ReviewItemComponent> getReviewItemComponent() {
    ((LazyList<?>) reviews).initialize();
    return reviews;
  }

  @Step("Get reviews ratings")
  public List<Integer> getReviewsRatings() {
    return getReviewItemComponent().stream()
        .map(ReviewItemComponent::getRating)
        .collect(Collectors.toList());
  }

  @Step("Verify Average Customer Ratings calculated properly")
  public boolean isAverageRatingCalculatedProperly() {
    logger.info("Checking Average Customer Ratings calculated properly");
    int summaryRatings = 0;
    for (Text rating : averageCustomerRatings) {
      int star =
          Integer.parseInt(rating.getText().split("with")[1].replaceAll("[^\\d]", "").trim());
      int reviews = Integer.parseInt(rating.getText().split("review")[0].trim());
      summaryRatings += star * reviews;
    }

    logger.info("Summary ratings: {}", summaryRatings);
    logger.info("Summary reviews: {}", getReviewsTotalNumber());
    double value =
        Double.parseDouble(String.valueOf(summaryRatings))
            / Double.parseDouble(String.valueOf(getReviewsTotalNumber()));
    return getAverageCustomerRating() == WebHelper.roundDouble(value, 1);
  }

  @Step("Summary reviews number")
  public int getReviewsTotalNumber() {
    int reviews = Integer.parseInt(reviewsTotalNumber.getText().replaceAll("[^\\d]", "").trim());
    logger.info("Reviews total number: {}", reviews);
    return reviews;
  }

  @Step("Average Customer Rating")
  public double getAverageCustomerRating() {
    double rating = Double.parseDouble(averageCustomerRating.getText().trim());
    logger.info("Average customer rating: {}", rating);
    return rating;
  }

  @Step("Verify reviews divided according to the stars")
  public boolean areReviewsDividedAccordingToStars() {
    logger.info("Checking reviews divided according to the stars");
    for (int i = 1; i <= 5; i++) {
      starRatingBar.format(i).initialize();
      int currentBarNumber =
          Integer.parseInt(
              starRatingBar
                  .getAttributeValue(Attribute.CLASS.getValue())
                  .replaceAll("[^\\d]", "")
                  .trim());
      int expectedBarNumber =
          (int)
              Math.round(
                  (100 / Double.parseDouble(String.valueOf(getReviewsTotalNumber())))
                      * Double.parseDouble(String.valueOf(getRatingScoreForStar(i))));

      if (currentBarNumber != expectedBarNumber) {
        logger.info("Reviews isn't divided according to the star for: {}", i);
        logger.info("Expected reviews bar value: {}", expectedBarNumber);
        logger.info("Current reviews bar value: {}", currentBarNumber);
        return false;
      }
    }
    return true;
  }

  @Step("Get rating score for star")
  public int getRatingScoreForStar(int star) {
    ratingScoreForStar.format(star).initialize();
    int score = Integer.parseInt(ratingScoreForStar.getText().trim());
    logger.info("Rating score for {} star: {}", star, score);
    return score;
  }

  @Step("Verify reviews total number is displayed")
  public boolean isReviewsTotalNumberDisplayed() {
    logger.info("Checking reviews total number is displayed");
    return WebHelper.isDisplayed(reviewsTotalNumber);
  }

  @Step("Click on the review star")
  public void clickOnReviewStar(int star) {
    logger.info("Clicking on the review star: {}", star);
    reviewsStarButton.format(star).initialize();
    WebHelper.scrollToElement(reviewsStarButton);
    WebHelper.jsClick(reviewsStarButton.getWebElement());
    SdkHelper.getSyncHelper().wait(Until.uiElement(clearAllButton).present());
  }

  @Step("Click on the 'Clear All' button")
  public void clearAllFilters() {
    logger.info("Clicking on the 'Clear All' button");
    WebHelper.jsClick(clearAllButton.getWebElement());
    SdkHelper.getSyncHelper().wait(Until.uiElement(clearAllButton).notVisible());
  }

  @Step("Verify Clear All button is displayed")
  public boolean isClearAllFilterButtonDisplayed() {
    return WebHelper.isDisplayed(clearAllButton);
  }

  @Step("Click on the 'Write a review' button")
  public WriteReviewComponent clickWriteReview() {
    logger.info("Clicking on the 'Write a review' button");
    writeReviewButton.click();
    return SdkHelper.create(WriteReviewComponent.class);
  }

  @Step("Click on the 'See more reviews'")
  public void seeMoreReviews() {
    logger.info("Clicking on the 'See more reviews'");
    WebHelper.scrollToElement(seeMoreReviews);
    seeMoreReviews.click();
    SdkHelper.getSyncHelper().sleep(2000); // wait for action to complete
  }
}
