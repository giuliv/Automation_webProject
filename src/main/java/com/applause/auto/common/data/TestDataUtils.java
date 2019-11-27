package com.applause.auto.common.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataUtils {
  public static class PhoneNumberDataUtils {
    public static String getRandomPhoneNumber() {
      return "2" + RandomStringUtils.randomNumeric(9);
    }

    public static String getOnlyDigitsFromPhoneNumber(String phoneNumber) {
      String updatedNumber =
          phoneNumber.replace("(", "").replace(")", "").replace("-", "").replace(" ", "");
      return updatedNumber;
    }
  }

  public static class BillingUtils {
    public static int parseBalance(String balance) throws ParseException {
      return NumberFormat.getNumberInstance(Locale.US).parse(balance).intValue();
    }

    public static String getFormatCurrentTransactionDate() {
      return new SimpleDateFormat("MMM d, yyyy").format(new Date());
    }

    public static String getFormatTransactionAmount(int cardAmount) {
      return "+$" + new DecimalFormat("0.00").format(cardAmount);
    }
  }
}
