package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SubscriptionType {
  SIGNATURE_BLEND("Signature Blend"),
  SINGLE_ORIGIN("Single Origin"),
  SMALL_BATCHES("Small Batches");

  @Getter private String value;
}
