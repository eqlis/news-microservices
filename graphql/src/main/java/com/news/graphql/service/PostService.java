package com.news.graphql.service;

import com.news.graphql.model.Post;
import com.news.graphql.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
  private final PostRepository repository;

  public List<Post> getPosts() {
    return repository.findAll();
  }

  public Post getPost(long id) {
    return getPosts().stream().filter(post -> post.getId() == id).findFirst().orElse(null);
  }
}
