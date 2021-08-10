DROP FUNCTION IF EXISTS task.author_update;
CREATE FUNCTION task.author_update(
    in_login_id BIGINT,
    in_author_id BIGINT,
    in_full_name TEXT,
    in_bio TEXT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.author_update.sql
**		Name: task.author_update
**		Desc: update author's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-10
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-10		laziz     	Created
*******************************************************************************
**		Return values: author id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.author_update';
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
        RAISE EXCEPTION 'author % not exists --> % step %', in_author_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your author ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.author
    SET
        full_name = in_full_name,
        bio = in_bio,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        author.author_id = in_author_id
        AND NOT author.deleted
  RETURNING author_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
