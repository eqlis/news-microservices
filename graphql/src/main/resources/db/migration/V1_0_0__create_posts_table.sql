CREATE TABLE posts (
  id BIGSERIAL
  , title VARCHAR(255)
  , summary VARCHAR(255)
  , date TIMESTAMP
  , author VARCHAR(255)
  , author_role VARCHAR(255)
  , image_src VARCHAR(255)
);