package com.news.consumer.service;

import com.news.consumer.model.entity.Post;
import com.news.consumer.model.dto.PostDTO;
import com.news.consumer.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PostService {
  private final PostRepository repository;

  public void save(PostDTO postDTO) {
    if (repository.findByTitle(postDTO.getTitle()).isEmpty()) {
      Post post = postDTO.toPost();
      repository.save(post);
    }
  }
}
