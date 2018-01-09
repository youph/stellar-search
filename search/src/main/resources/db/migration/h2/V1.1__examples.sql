--
-- Purely for the example controller
--
CREATE TABLE example_task (
  id BIGINT NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  date_time_instant TIMESTAMP WITH TIME ZONE NOT NULL,
  date_time_zone_id VARCHAR(255) NOT NULL,
  status VARCHAR(255) NOT NULL,
  principle_name VARCHAR(255) NOT NULL
);
CREATE SEQUENCE example_task_id_seq;
