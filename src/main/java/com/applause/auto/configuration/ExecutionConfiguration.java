package com.applause.auto.configuration;

import org.aeonbits.owner.Config;

public interface ExecutionConfiguration extends Config {

  @Key("env.type")
  @DefaultValue("webappgsd-sys")
  // webappgsd-test01
  String envType();

  @Key("main.url")
  String mainUrl();

  @Key("retry.on.failure")
  @DefaultValue("false")
  boolean retryOnFailure();
}
