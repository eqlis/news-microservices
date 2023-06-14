CREATE TABLE users (
  id BIGSERIAL,
  username VARCHAR(50) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT username_key UNIQUE (username)
);

CREATE TABLE likes (
  id BIGSERIAL,
  value BOOLEAN NOT NULL,
  user_id BIGINT,
  post_id BIGINT,
  CONSTRAINT likes_pk PRIMARY KEY (id),
  CONSTRAINT likes_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT likes_posts_fk FOREIGN KEY (post_id) REFERENCES posts(id),
  CONSTRAINT likes_user_id_post_id_key UNIQUE (user_id, post_id)
);

CREATE TABLE comments (
  id BIGSERIAL,
  text VARCHAR(255) NOT NULL,
  user_id BIGINT,
  post_id BIGINT,
  CONSTRAINT comments_pk PRIMARY KEY (id),
  CONSTRAINT comments_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT comments_posts_fk FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE categories (
  id BIGSERIAL,
  title VARCHAR(50) NOT NULL,
  user_id BIGINT,
  post_id BIGINT,
  CONSTRAINT categories_pk PRIMARY KEY (id),
  CONSTRAINT categories_users_fk FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT categories_posts_fk FOREIGN KEY (post_id) REFERENCES posts(id)
);