DROP FUNCTION IF EXISTS task.employee_get;
CREATE FUNCTION task.employee_get(
    in_login_id BIGINT,
    in_employee_id BIGINT
    )
RETURNS TABLE (
    employee_id BIGINT,
    first_name TEXT,
    last_name TEXT,
    email TEXT,
    phone_number TEXT,
    hire_date DATE,
    salary BIGINT,
    commission_pct BIGINT,
    department_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.employee_get.sql
**		Name: task.employee_get
**		Desc: Get employee data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: employee infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.employee_get';
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

    IF in_employee_id IS NULL OR in_employee_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_employee_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your employee ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        employee.employee_id,
        employee.first_name,
        employee.last_name,
        employee.email,
        employee.phone_number,
        employee.hire_date,
        employee.salary,
        employee.commission_pct,
        employee.department_id    FROM task.employee
    WHERE
        employee.employee_id = in_employee_id
        AND NOT employee.deleted;
END;
$$ LANGUAGE plpgsql;