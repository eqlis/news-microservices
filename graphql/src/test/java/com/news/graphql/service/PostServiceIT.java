package com.news.graphql.service;

import com.news.graphql.config.CacheConfig;
import com.news.graphql.model.Post;
import com.news.graphql.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({
  PostService.class,
  CacheConfig.class
})
@ImportAutoConfiguration(classes = {
  CacheAutoConfiguration.class,
  RedisAutoConfiguration.class
})
class PostServiceIT {
  @MockBean
  private PostRepository repository;
  @Autowired
  private PostService service;
  @Autowired
  private CacheManager cacheManager;

  @BeforeEach
  void clearCache() {
    cacheManager.getCache("postsCache").clear();
  }

  @Test
  void getPostsIsCacheable() {
    List<Post> posts = List.of(Post.builder().id(-1L).build(), Post.builder().id(-2L).build());
    when(repository.findAll()).thenReturn(posts);

    List<Post> firstResult = service.getPosts();
    List<Post> secondResult = service.getPosts();

    Assertions.assertThat(firstResult).usingRecursiveFieldByFieldElementComparator().isEqualTo(posts);
    Assertions.assertThat(secondResult).usingRecursiveFieldByFieldElementComparator().isEqualTo(posts);
    verify(repository, times(1)).findAll();
  }
}
