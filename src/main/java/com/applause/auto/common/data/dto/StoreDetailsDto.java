package com.applause.auto.common.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class StoreDetailsDto {
  private String type;
  private String name;
  private String address;
  private String direction;
  private String schedule;
  private boolean isStartOrder;
}
