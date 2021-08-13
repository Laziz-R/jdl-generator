DROP FUNCTION IF EXISTS task.book_author_delete;
CREATE FUNCTION task.book_author_delete(
  in_login_id BIGINT,
  in_book_author_id BIGINT
)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.book_author_delete.sql
**		Name: task.book_author_delete
**		Desc: delete book_author
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-13
*******************************************************************************
**		Change History
**		Date: 			  Author:				Description:
*******************************************************************************
**		2021-08-13		laziz   Created
**
*******************************************************************************
**		Return values: book_author id.
*******************************************************************************
**/

DECLARE
  FN_NAME CONSTANT TEXT := 'task.book_author_delete';
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
    IF NOT EXISTS(SELECT * FROM task.book_author WHERE book_author.book_author_id = in_book_author_id AND NOT deleted) THEN
        RAISE EXCEPTION 'Cannot delete book_author % not exists --> % step %', in_book_author_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your book_author ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.book_author
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        book_author.book_author_id = in_book_author_id
        AND NOT deleted
  RETURNING book_author_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
