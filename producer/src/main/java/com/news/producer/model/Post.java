package com.news.producer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public record Post(
  String title
  , String summary
  , @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime date
  , String author
  , String authorRole
  , String imageSrc
) {
}
