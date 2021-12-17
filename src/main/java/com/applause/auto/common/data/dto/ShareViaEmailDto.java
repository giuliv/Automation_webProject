package com.applause.auto.common.data.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ShareViaEmailDto {
  private String email;
  private String subject;
  private String note;
}
