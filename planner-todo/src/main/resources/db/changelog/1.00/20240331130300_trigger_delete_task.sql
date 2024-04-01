-- liquibase formatted sql

-- changeset msuzko:trigger_delete_task
CREATE OR REPLACE FUNCTION delete_task()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS '
BEGIN
	IF (OLD.category_id IS NOT NULL) THEN
		IF (OLD.completed = 1) THEN
			UPDATE category
			SET completed_count = completed_count - 1
			WHERE user_id = OLD.user_id
				AND id = OLD.category_id;
		ELSE
			UPDATE category
			SET uncompleted_count = uncompleted_count - 1
			WHERE user_id = OLD.user_id
				AND id = OLD.category_id;
		END IF;
	END IF;

	IF (OLD.completed = 1) THEN
		UPDATE statistics
		SET completed_total = completed_total - 1
		WHERE user_id = OLD.user_id;
	ELSE
		UPDATE statistics
		SET uncompleted_total = uncompleted_total - 1
		WHERE user_id = OLD.user_id;
	END IF;

	RETURN OLD;
END
';

CREATE TRIGGER delete_task
    AFTER DELETE
    ON task
    FOR EACH ROW
    EXECUTE FUNCTION delete_task();

-- rollback DROP FUNCTION delete_task() cascade;
