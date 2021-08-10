DROP FUNCTION IF EXISTS task.book_update;
CREATE FUNCTION task.book_update(
    in_login_id BIGINT,
    in_book_id BIGINT,
    in_name TEXT,
    in_language TEXT,
    in_published_date DATE)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.book_update.sql
**		Name: task.book_update
**		Desc: update book's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-10
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-10		laziz     	Created
*******************************************************************************
**		Return values: book id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.book_update';
  STEP_INDEX INTEGER;
  STEP_DESC VARCHAR(500);
  result BIGINT;
BEGIN

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 0;
  STEP_DESC := FN_NAME || ': Initialized ...';
  RAISE NOTICE '%', STEP_DESC;
----------------------------------------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 10;
  STEP_DESC := FN_NAME || ': Validation check permission';
  RAISE NOTICE '%', STEP_DESC;
----------------------------------------------------------------------------------------------------------------------------------------------------
-- to do need to complete this step
    IF NOT EXISTS(SELECT * FROM task.book WHERE book.book_id = in_book_id AND NOT deleted) THEN
        RAISE EXCEPTION 'book % not exists --> % step %', in_book_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your book ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.book
    SET
        name = in_name,
        language = in_language,
        published_date = in_published_date,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        book.book_id = in_book_id
        AND NOT book.deleted
  RETURNING book_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
