-- liquibase formatted sql

-- changeset msuzko:trigger_add_task
CREATE OR REPLACE FUNCTION add_task()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS '
BEGIN
	IF (NEW.category_id IS NOT NULL) THEN
		IF (NEW.completed = 1) THEN
			UPDATE category
			SET completed_count = completed_count + 1
			WHERE user_id = NEW.user_id
				AND id = NEW.category_id;
		ELSE
			UPDATE category
			SET uncompleted_count = uncompleted_count + 1
			WHERE user_id = NEW.user_id
				AND id = NEW.category_id;
		END IF;
	END IF;

	IF (NEW.completed = 1) THEN
		UPDATE statistics
		SET completed_total = completed_total + 1
		WHERE user_id = NEW.user_id;
	ELSE
		UPDATE statistics
		SET uncompleted_total = uncompleted_total + 1
		WHERE user_id = NEW.user_id;
	END IF;
	RETURN NEW;
END
';

CREATE TRIGGER new_task
    AFTER INSERT
    ON task
    FOR EACH ROW
    EXECUTE FUNCTION add_task();

-- rollback DROP FUNCTION add_task() cascade;
