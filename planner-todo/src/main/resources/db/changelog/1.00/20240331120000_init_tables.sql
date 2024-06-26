-- liquibase formatted sql

-- changeset msuzko:init_category
CREATE TABLE IF NOT EXISTS category
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    completed_count BIGINT NOT NULL DEFAULT 0,
    uncompleted_count BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS priority_id_idx
    ON category(user_id);

-- rollback DROP TABLE IF EXISTS category;

-- changeset msuzko:init_priority
CREATE TABLE IF NOT EXISTS priority
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    color VARCHAR(10) NOT NULL,
    user_id VARCHAR(36) NOT NULL
);
-- rollback DROP TABLE IF EXISTS priority;

-- changeset msuzko:init_statistics
CREATE TABLE IF NOT EXISTS stat
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    completed_total BIGINT NOT NULL DEFAULT 0,
    uncompleted_total BIGINT NOT NULL DEFAULT 0,
    user_id VARCHAR(36) UNIQUE NOT NULL
);
-- rollback DROP TABLE IF EXISTS stat;

-- changeset msuzko:init_task
CREATE TABLE IF NOT EXISTS task
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    completed SMALLINT NOT NULL DEFAULT 0,
    task_date TIMESTAMP WITHOUT TIME ZONE,
    category_id BIGINT,
    priority_id BIGINT,
    user_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
        ON DELETE SET NULL,
    FOREIGN KEY (priority_id) REFERENCES priority (id)
        ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS title_idx
    ON task(title);

CREATE INDEX IF NOT EXISTS category_id_idx
    ON task(category_id);

CREATE INDEX IF NOT EXISTS priority_id_idx
    ON task(priority_id);
-- rollback DROP TABLE IF EXISTS title_idx;
-- rollback DROP INDEX IF EXISTS category_id_idx;
-- rollback DROP INDEX IF EXISTS priority_id_idx;
-- rollback DROP INDEX IF EXISTS task;
