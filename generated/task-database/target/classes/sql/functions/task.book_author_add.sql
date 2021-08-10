DROP FUNCTION IF EXISTS task.book_author_add;
CREATE FUNCTION task.book_author_add(
  in_login_id BIGINT,
  in_book_id BIGINT,
  in_author_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.book_author_add.sql
**        Name: task.book_author_add
**        Desc: add book_author's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-10
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-10        laziz            Created
*******************************************************************************
**        Return values: book_author id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.book_author_add';
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


---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  INSERT INTO task.book_author(
    book_id,
    author_id,
    created_by,
    deleted
    )
  VALUES (
    in_book_id,
    in_author_id,
    in_login_id,
    FALSE
  )
  RETURNING book_author_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

