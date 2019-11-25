package com.applause.auto.common.data;

import org.apache.commons.lang3.RandomStringUtils;

public class PhoneNumberDataUtils {

  public static String getRandomPhoneNumber() {
    return "2" + RandomStringUtils.randomNumeric(9);
  }

  public static String getOnlyDigitsFromPhoneNumber(String phoneNumber) {
    String updatedNumber =
        phoneNumber.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
    return updatedNumber;
  }
}
