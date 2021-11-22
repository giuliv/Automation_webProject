package com.applause.auto.common.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class MyOrderDto {
  private String name;
  private String info;
  private int quantity;
  private String price;
}
