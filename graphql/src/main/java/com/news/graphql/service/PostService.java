package com.news.graphql.service;

import com.news.graphql.model.Category;
import com.news.graphql.model.Comment;
import com.news.graphql.model.Like;
import com.news.graphql.model.Post;
import com.news.graphql.model.User;
import com.news.graphql.repository.CategoryRepository;
import com.news.graphql.repository.CommentRepository;
import com.news.graphql.repository.LikeRepository;
import com.news.graphql.repository.PostRepository;
import com.news.graphql.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final CommentRepository commentRepository;
  private final LikeRepository likeRepository;

  @Cacheable("postsCache")
  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  public Post getPost(long id) {
    return getPosts().stream().filter(post -> post.getId() == id).findFirst().orElse(null);
  }

  @Transactional
  public Post addCategory(long id, String title) {
    Post post = findById(id);
    Category category = Category.builder().title(title).build();

    category.setUser(getCurrentUser());
    category.setPost(post);
    post.addCategory(category);

    categoryRepository.save(category);
    return postRepository.save(post);
  }

  @Transactional
  public Post addComment(long id, String text) {
    Post post = findById(id);
    Comment comment = Comment.builder().text(text).build();

    comment.setUser(getCurrentUser());
    comment.setPost(post);
    post.addComment(comment);

    commentRepository.save(comment);
    return postRepository.save(post);
  }

  @Transactional
  public Post like(long id, boolean value) {
    Post post = findById(id);
    Like like = Like.builder().value(value).build();

    like.setUser(getCurrentUser());
    like.setPost(post);
    if (value) {
      post.setLikes(post.getLikes() + 1);
    } else {
      post.setDislikes(post.getDislikes() + 1);
    }

    likeRepository.save(like);
    return postRepository.save(post);
  }

  private Post findById(long id) {
    return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
  }

  private User getCurrentUser() {
    Principal principal = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername(principal.getName()).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }
}
