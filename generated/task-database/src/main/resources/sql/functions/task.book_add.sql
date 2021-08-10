DROP FUNCTION IF EXISTS task.book_add;
CREATE FUNCTION task.book_add(
  in_login_id BIGINT,
  in_name TEXT,
  in_language TEXT,
  in_published_date DATE  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.book_add.sql
**        Name: task.book_add
**        Desc: add book's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-10
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-10        laziz            Created
*******************************************************************************
**        Return values: book id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.book_add';
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

  INSERT INTO task.book(
    name,
    language,
    published_date,
    created_by,
    deleted
    )
  VALUES (
    in_name,
    in_language,
    in_published_date,
    in_login_id,
    FALSE
  )
  RETURNING book_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

