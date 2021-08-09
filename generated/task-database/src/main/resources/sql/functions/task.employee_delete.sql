DROP FUNCTION IF EXISTS task.employee_delete;
CREATE FUNCTION task.employee_delete(
  in_login_id BIGINT,
  in_employee_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.employee_delete.sql
**		Name: task.employee_delete
**		Desc: delete employee
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
**		Return values: employee id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.employee_delete';
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
    IF NOT EXISTS(SELECT * FROM task.employee WHERE employee.employee_id = in_employee_id AND NOT deleted) THEN
        RAISE EXCEPTION 'Cannot delete employee % not exists --> % step %', in_employee_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your employee ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.employee
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        employee.employee_id = in_employee_id
        AND NOT deleted
  RETURNING employee_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
