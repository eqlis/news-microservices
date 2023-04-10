package com.news.consumer.component;

import com.news.consumer.model.dto.PostDTO;
import com.news.consumer.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@AllArgsConstructor
@EnableRabbit
@Component
public class NewsConsumer {
  private static final Logger LOGGER = Logger.getLogger(NewsConsumer.class.getName());

  private final PostService postService;

  @RabbitListener(queues = "${rabbit.news.queue}")
  public void receiveMessage(PostDTO postDTO) {
    LOGGER.info("Received: " + postDTO);
    postService.save(postDTO);
  }
}
