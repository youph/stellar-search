--
-- A table for token authorisation credentials.
--
CREATE TABLE user (
  username VARCHAR(255) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL
);

INSERT INTO user (
  username,
  password
) VALUES (
  'test-user',
  '$2a$10$fw3qEqwqUetIhMNtUGc6W.44GTBe8YNvZSQb2DSj1AyBx3PNWMDDK'
);
