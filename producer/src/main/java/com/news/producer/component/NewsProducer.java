package com.news.producer.component;

import com.news.producer.config.RabbitConfig;
import com.news.producer.model.Post;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@EnableScheduling
@Component
public class NewsProducer {

  private static final Logger LOGGER = Logger.getLogger(NewsProducer.class.getName());

  private final RabbitConfig rabbitConfig;
  private final RabbitTemplate rabbitTemplate;

  @Scheduled(fixedRateString = "${scheduler.interval}")
  public void fetchAndSendNews() {
    WebDriver driver = new FirefoxDriver();
    try {
      driver.get("https://www.bbc.com/news/world");
      List<WebElement> elements = driver.findElements(By.className("lx-stream__post-container"));
      for (WebElement el : elements) {
        String title = getElementText(el, By.className("lx-stream-post__header-text"));
        String date = getElementText(el, By.className("qa-post-auto-meta"));
        String summary = getElementText(el, By.className("lx-stream-related-story--summary"));
        String mediaSummary = getElementText(el, By.className("lx-media-asset-summary"));
        String author = getElementText(el, By.className("qa-contributor-name"));
        String authorRole = getElementText(el, By.className("qa-contributor-role"));
        String imageSrc = getElementSrc(el, By.className("qa-srcset-image"));
        Post post = new Post(title, summary != null ? summary : mediaSummary, parseDate(date), author, authorRole, imageSrc);
        LOGGER.info("Sending: " + post);
        rabbitTemplate.convertAndSend(rabbitConfig.getTopicExchangeName(), rabbitConfig.getRoutingKey(), post);
      }
    } finally {
      driver.close();
    }
  }

  private String getElementText(WebElement parent, By by) {
    WebElement element = getElementSafe(parent, by);
    if (element != null) {
      return element.getText();
    }
    return null;
  }

  private String getElementSrc(WebElement parent, By by) {
    WebElement element = getElementSafe(parent, by);
    if (element != null) {
      return element.getAttribute("src");
    }
    return null;
  }

  private WebElement getElementSafe(WebElement parent, By by) {
    try {
      return parent.findElement(by);
    } catch (NoSuchElementException ignored) {
      return null;
    }
  }

  private LocalDateTime parseDate(String date) {
    LocalDate datePart = LocalDate.now();
    if (date.length() <= 5) {
      LocalTime timePart = LocalTime.parse(date, DateTimeFormatter.ofPattern("k:mm"));
      return LocalDateTime.of(datePart, timePart);
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("k:mm dd MMM yyyy");
    return LocalDateTime.parse(date + " " + datePart.getYear(), formatter);
  }
}
