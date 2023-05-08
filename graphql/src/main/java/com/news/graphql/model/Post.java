package com.news.graphql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post implements Serializable {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private String title;
  private String summary;
  private LocalDateTime date;
  private String author;
  private String authorRole;
  private String imageSrc;
}
