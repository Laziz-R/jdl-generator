DROP FUNCTION IF EXISTS task.employee_update;
CREATE FUNCTION task.employee_update(
    in_login_id BIGINT,
    in_employee_id BIGINT,
    in_first_name TEXT,
    in_last_name TEXT,
    in_email TEXT,
    in_phone_number TEXT,
    in_hire_date DATE,
    in_salary BIGINT,
    in_commission_pct BIGINT,
    in_department_id BIGINT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.employee_update.sql
**		Name: task.employee_update
**		Desc: update employee's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: employee id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.employee_update';
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
        RAISE EXCEPTION 'employee % not exists --> % step %', in_employee_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your employee ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.employee
    SET
        first_name = in_first_name,
        last_name = in_last_name,
        email = in_email,
        phone_number = in_phone_number,
        hire_date = in_hire_date,
        salary = in_salary,
        commission_pct = in_commission_pct,
        department_id = in_department_id,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        employee.employee_id = in_employee_id
        AND NOT employee.deleted
  RETURNING employee_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
