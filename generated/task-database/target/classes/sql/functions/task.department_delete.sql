DROP FUNCTION IF EXISTS task.department_delete;
CREATE FUNCTION task.department_delete(
  in_login_id BIGINT,
  in_department_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.department_delete.sql
**		Name: task.department_delete
**		Desc: delete department
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			  Author:				Description:
*******************************************************************************
**		2021-08-09		laziz   Created
**
*******************************************************************************
**		Return values: department id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.department_delete';
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
    IF NOT EXISTS(SELECT * FROM task.department WHERE department.department_id = in_department_id AND NOT deleted) THEN
        RAISE EXCEPTION 'Cannot delete department % not exists --> % step %', in_department_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your department ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.department
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        department.department_id = in_department_id
        AND NOT deleted
  RETURNING department_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
