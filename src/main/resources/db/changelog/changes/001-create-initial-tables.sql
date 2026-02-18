
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL
);


CREATE TABLE categories (
                            id UUID PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE tags (
                      id UUID PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE,
                      author_id UUID NOT NULL,
                      CONSTRAINT fk_tags_author FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE posts (
                       id UUID PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       author_id UUID NOT NULL,
                       category_id UUID NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id),
                       CONSTRAINT fk_posts_category FOREIGN KEY (category_id) REFERENCES categories(id)
);


CREATE TABLE post_tags (
                           post_id UUID NOT NULL,
                           tags_id UUID NOT NULL,
                           PRIMARY KEY (post_id, tags_id),
                           CONSTRAINT fk_post_tags_post FOREIGN KEY (post_id) REFERENCES posts(id),
                           CONSTRAINT fk_post_tags_tag FOREIGN KEY (tags_id) REFERENCES tags(id)
);