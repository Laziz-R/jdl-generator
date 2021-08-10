DROP FUNCTION IF EXISTS task.author_delete;
CREATE FUNCTION task.author_delete(
  in_login_id BIGINT,
  in_author_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.author_delete.sql
**		Name: task.author_delete
**		Desc: delete author
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-10
*******************************************************************************
**		Change History
**		Date: 			  Author:				Description:
*******************************************************************************
**		2021-08-10		laziz   Created
**
*******************************************************************************
**		Return values: author id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.author_delete';
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
    IF NOT EXISTS(SELECT * FROM task.author WHERE author.author_id = in_author_id AND NOT deleted) THEN
        RAISE EXCEPTION 'Cannot delete author % not exists --> % step %', in_author_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your author ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.author
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        author.author_id = in_author_id
        AND NOT deleted
  RETURNING author_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
