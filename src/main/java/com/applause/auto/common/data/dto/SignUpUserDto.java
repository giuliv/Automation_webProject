package com.applause.auto.common.data.dto;

import static com.applause.auto.web.helpers.WebHelper.getRandomMail;

import com.applause.auto.common.data.TestDataUtils;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class SignUpUserDto {
  @Default private String firstName = "Firstname";
  @Default private String lastName = "Lastname";
  @Default private String zipCode = "11214";
  @Default private String dobDay = "27";
  @Default private String dobMonth = "May";
  @Default private String dobYear = "2000";
  @Default private String phoneNumber = TestDataUtils.PhoneNumberDataUtils.getRandomPhoneNumber();
  @Default private String email = getRandomMail();
  @Default private String password = "Password1";
  @Default private boolean isAgreePrivacyPolicyAndTermsAndConditions = true;
}
