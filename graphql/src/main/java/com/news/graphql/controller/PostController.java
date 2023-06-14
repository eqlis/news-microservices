package com.news.graphql.controller;

import com.news.graphql.model.Post;
import com.news.graphql.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@AllArgsConstructor
@Controller
public class PostController {
  private final PostService service;

  @QueryMapping
  public List<Post> getPosts() {
    return service.getPosts();
  }

  @QueryMapping
  public Post getPost(@Argument long id) {
    return service.getPost(id);
  }

  @MutationMapping
  public Post addCategory(@Argument long id, @Argument String title) {
    return service.addCategory(id, title);
  }

  @MutationMapping
  public Post addComment(@Argument long id, @Argument String text) {
    return service.addComment(id, text);
  }

  @MutationMapping
  public Post like(@Argument long id, @Argument boolean value) {
    return service.like(id, value);
  }
}
