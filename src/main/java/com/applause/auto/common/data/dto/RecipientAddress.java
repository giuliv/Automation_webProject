package com.applause.auto.common.data.dto;

import com.applause.auto.new_web.helpers.WebHelper;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class RecipientAddress {

  @Default private String country = "United States";
  @Default private String firstName = "FirstName" + WebHelper.getRandomValueWithinRange(5, 10);
  @Default private String lastName = "LastName" + WebHelper.getRandomValueWithinRange(5, 10);
  @Default private String company = "Company" + WebHelper.getRandomValueWithinRange(5, 10);
  @Default private String address = "999 9th Street Northwest";
  @Default private String city = "Washington";
  @Default private String state = "Washington DC";
  @Default private String zipCode = "20001";
  @Default private String phoneNumber = "(423) 423-4343";
}
