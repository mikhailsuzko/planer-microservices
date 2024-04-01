-- liquibase formatted sql

-- changeset msuzko:trigger_update_task
CREATE OR REPLACE FUNCTION update_task()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS '
BEGIN
	IF (COALESCE(NEW.category_id, 0) = COALESCE(OLD.category_id, 0)) THEN
		IF (NEW.completed = 1 AND OLD.completed = 0) THEN
			UPDATE category
			SET completed_count = completed_count + 1,
				uncompleted_count = uncompleted_count - 1
			WHERE id = NEW.category_id and NEW.user_id = user_id;

			UPDATE stat
 			SET completed_total = completed_total + 1,
				uncompleted_total = uncompleted_total - 1
			WHERE OLD.user_id = user_id;
		ELSIF (NEW.completed = 0 AND OLD.completed = 1) THEN
			UPDATE category
			SET completed_count = completed_count - 1,
				uncompleted_count = uncompleted_count + 1
			WHERE id = NEW.category_id and NEW.user_id = user_id;

			UPDATE stat
 			SET completed_total = completed_total - 1,
				uncompleted_total = uncompleted_total + 1
			WHERE OLD.user_id = user_id;
		END IF;
	ELSE
		IF (NEW.completed = OLD.completed) THEN
			IF (NEW.completed = 0) THEN
				UPDATE category
				SET uncompleted_count = uncompleted_count + 1
				WHERE id = NEW.category_id and NEW.user_id = user_id;

				UPDATE category
				SET uncompleted_count = uncompleted_count - 1
				WHERE id = OLD.category_id and OLD.user_id = user_id;
			ELSE
				UPDATE category
				SET completed_count = completed_count + 1
				WHERE id = NEW.category_id and NEW.user_id = user_id;

				UPDATE category
				SET completed_count = completed_count - 1
				WHERE id = OLD.category_id and OLD.user_id = user_id;
			END IF;
		ELSIF (NEW.completed = 0) THEN
			UPDATE category
			SET completed_count = completed_count - 1
			WHERE id = OLD.category_id and OLD.user_id = user_id;

			UPDATE category
			SET uncompleted_count = uncompleted_count + 1
			WHERE id = NEW.category_id and NEW.user_id = user_id;

			UPDATE stat
			SET completed_total = completed_total - 1,
				uncompleted_total = uncompleted_total + 1
			WHERE NEW.user_id = user_id;
		ELSE
			UPDATE category
			SET uncompleted_count = uncompleted_count - 1
			WHERE id = OLD.category_id and OLD.user_id = user_id;

			UPDATE category
			SET completed_count = completed_count + 1
			WHERE id = NEW.category_id and NEW.user_id = user_id;

			UPDATE stat
			SET completed_total = completed_total + 1,
				uncompleted_total = uncompleted_total - 1
			WHERE NEW.user_id = user_id;
		END IF;
	END IF;
	RETURN NEW;
END
';

CREATE TRIGGER update_task
    AFTER UPDATE
    ON task
    FOR EACH ROW
    EXECUTE FUNCTION update_task();

-- rollback DROP FUNCTION update_task() cascade;
