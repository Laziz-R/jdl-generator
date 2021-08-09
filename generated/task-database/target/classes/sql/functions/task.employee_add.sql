DROP FUNCTION IF EXISTS task.employee_add;
CREATE FUNCTION task.employee_add(
  in_login_id BIGINT,
  in_first_name TEXT,
  in_last_name TEXT,
  in_email TEXT,
  in_phone_number TEXT,
  in_hire_date DATE,
  in_salary BIGINT,
  in_commission_pct BIGINT,
  in_department_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.employee_add.sql
**        Name: task.employee_add
**        Desc: add employee's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-09
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-09        laziz            Created
*******************************************************************************
**        Return values: employee id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.employee_add';
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

  INSERT INTO task.employee(
    first_name,
    last_name,
    email,
    phone_number,
    hire_date,
    salary,
    commission_pct,
    department_id,
    created_by,
    deleted
    )
  VALUES (
    in_first_name,
    in_last_name,
    in_email,
    in_phone_number,
    in_hire_date,
    in_salary,
    in_commission_pct,
    in_department_id,
    in_login_id,
    FALSE
  )
  RETURNING employee_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

