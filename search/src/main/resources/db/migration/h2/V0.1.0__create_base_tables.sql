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
  '$2a$10$auov.G1Hak6c4QAuXzJIiemDokcfsLZJ/e9mbVtkbN9YVeipZH.om'
);

--
-- EPG Meta info table
--
CREATE TABLE epg_meta (
  name VARCHAR(255) NOT NULL PRIMARY KEY,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  status VARCHAR(255) NOT NULL,
  created_by_username VARCHAR(255) NOT NULL,
  graphs VARCHAR NOT NULL,
  vertices VARCHAR NOT NULL,
  edges VARCHAR NOT NULL
);
