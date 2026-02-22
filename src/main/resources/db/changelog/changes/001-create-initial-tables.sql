
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL
);


CREATE TABLE categories (
                            id UUID PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE,
                            author_id UUID NOT NULL,
                            CONSTRAINT fk_category_author
                                FOREIGN KEY (author_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE
);

CREATE INDEX idx_category_author ON categories(author_id);

CREATE TABLE tags (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE,
                      author_id UUID NOT NULL,
                      CONSTRAINT fk_tag_author
                          FOREIGN KEY (author_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE
);

CREATE INDEX idx_tag_author ON tags(author_id);


CREATE TABLE posts (
                       id UUID PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       author_id UUID NOT NULL,
                       category_id UUID NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       CONSTRAINT fk_post_author
                           FOREIGN KEY (author_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE,
                       CONSTRAINT fk_post_category
                           FOREIGN KEY (category_id)
                               REFERENCES categories(id)
                               ON DELETE CASCADE
);

CREATE INDEX idx_post_author ON posts(author_id);
CREATE INDEX idx_post_category ON posts(category_id);


CREATE TABLE post_tag (
                          post_id UUID NOT NULL,
                          tag_id UUID NOT NULL,
                          PRIMARY KEY (post_id, tag_id),
                          CONSTRAINT fk_post_tag_post
                              FOREIGN KEY (post_id)
                                  REFERENCES posts(id)
                                  ON DELETE CASCADE,
                          CONSTRAINT fk_post_tag_tag
                              FOREIGN KEY (tag_id)
                                  REFERENCES tags(id)
                                  ON DELETE CASCADE
);

CREATE INDEX idx_post_tag_post ON post_tag(post_id);
CREATE INDEX idx_post_tag_tag ON post_tag(tag_id);