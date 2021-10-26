package com.applause.auto.common.data.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class CreditCardDto {
  private String cardNumber;
  private String nameOnCard;
  private String expirationMonth;
  private String expirationYear;
  private String securityCode;
}
