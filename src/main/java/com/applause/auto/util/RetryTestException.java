package com.applause.auto.util;

public class RetryTestException extends RuntimeException {

  public RetryTestException(String message) {
    super("Retry requested: " + message);
  }
}
