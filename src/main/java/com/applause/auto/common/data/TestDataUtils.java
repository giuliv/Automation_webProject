package com.applause.auto.common.data;

import com.applause.auto.framework.SdkHelper;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TestDataUtils {
  public static class PhoneNumberDataUtils {
    /**
     * Gets random phone number.
     *
     * @return the random phone number
     */
    public static String getRandomPhoneNumber() {
      String current = String.valueOf(System.currentTimeMillis());
      return "2" + current.substring(current.length() - 9);
    }

    /**
     * Gets only digits from phone number.
     *
     * @param phoneNumber the phone number
     * @return the only digits from phone number
     */
    public static String getOnlyDigitsFromPhoneNumber(String phoneNumber) {
      String updatedNumber =
          phoneNumber
              .replace("(", "")
              .replace(")", "")
              .replace("-", "")
              .replace(" ", "")
              .replace("PhoneNumberOptional", "");
      return updatedNumber;
    }
  }

  public static class BillingUtils {
    /**
     * Parse balance int.
     *
     * @param balance the balance
     * @return the int
     * @throws ParseException the parse exception
     */
    public static int parseBalance(String balance) throws ParseException {
      return NumberFormat.getNumberInstance(Locale.US).parse(balance).intValue();
    }

    /**
     * Gets format current transaction date.
     *
     * @return the format current transaction date
     */
    public static String getFormatCurrentTransactionDate() {
      SimpleDateFormat transactionDateFormat;
      if (SdkHelper.getEnvironmentHelper().isMobileAndroid()) {
        transactionDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
      } else {
        transactionDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
      }
      transactionDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
      return transactionDateFormat.format(new Date());
    }

    /**
     * Gets format transaction amount.
     *
     * @param cardAmount the card amount
     * @return the format transaction amount
     */
    public static String getFormatTransactionAmount(int cardAmount) {
      return "+$" + new DecimalFormat("0.00").format(cardAmount);
    }
  }
}
