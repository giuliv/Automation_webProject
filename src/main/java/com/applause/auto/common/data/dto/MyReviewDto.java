package com.applause.auto.common.data.dto;

import static com.applause.auto.new_web.helpers.WebHelper.getRandomMail;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Builder
@Getter
@EqualsAndHashCode
public class MyReviewDto {
  @Default private int rating = 5;
  @Default private String reviewTitle = "Applause Automation Title";

  @Default
  private String review =
      "This Applause Automation review. It was created during the Automation test run and can be skipped";

  @Default private String nickName = RandomStringUtils.random(10, true, true);
  @Default private String email = getRandomMail();
  @Default private boolean iAgreeTermsAndConditions = true;
}
