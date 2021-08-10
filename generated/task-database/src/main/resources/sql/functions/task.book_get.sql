DROP FUNCTION IF EXISTS task.book_get;
CREATE FUNCTION task.book_get(
    in_login_id BIGINT,
    in_book_id BIGINT
    )
RETURNS TABLE (
    book_id BIGINT,
    name TEXT,
    language TEXT,
    published_date DATE    )
AS $$
/******************************************************************************
**		File: task.book_get.sql
**		Name: task.book_get
**		Desc: Get book data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-10
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-10		laziz     	Created
*******************************************************************************
**		Return values: book infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.book_get';
    STEP_INDEX INTEGER;
    STEP_DESC VARCHAR(500);
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

    IF in_book_id IS NULL OR in_book_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_book_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your book ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        book.book_id,
        book.name,
        book.language,
        book.published_date    FROM task.book
    WHERE
        book.book_id = in_book_id
        AND NOT book.deleted;
END;
$$ LANGUAGE plpgsql;