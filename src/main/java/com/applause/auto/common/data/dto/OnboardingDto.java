package com.applause.auto.common.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class OnboardingDto {
  private int position;
  private String title;
  private String description;
  private String helpfulMessage;
  private boolean isPageIndicatorDisplayed;
  private boolean isSkipButtonDisplayed;
}
