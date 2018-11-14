INSERT INTO user (email, username, password, enabled) VALUES
  ('test@test.be', 'tom', '$2a$10$Fg.pwGKNEk8TtRq3C86DEeIo6CnUI05umcVQuvRh2DdwEKJUPtsJK', 1);

INSERT INTO role (email, role) VALUES
  ('test@test.be', 'admin'),
  ('test@test.be', 'user');