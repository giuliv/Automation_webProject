package com.applause.auto.common.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
public class BillingAddressDto {
  private String firstName;
  private String lastName;
  private String company;
  private String streetAddress;
  private String apartmentSuiteEtc;
  private String city;
  private String phoneNumber;
  private String country;
  private String state;
  private String zipCode;
}
