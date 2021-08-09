DROP FUNCTION IF EXISTS task.job_add;
CREATE FUNCTION task.job_add(
  in_login_id BIGINT,
  in_job_title TEXT,
  in_min_salary BIGINT,
  in_max_salary BIGINT,
  in_employee_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.job_add.sql
**        Name: task.job_add
**        Desc: add job's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-09
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-09        laziz            Created
*******************************************************************************
**        Return values: job id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_add';
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

  INSERT INTO task.job(
    job_title,
    min_salary,
    max_salary,
    employee_id,
    created_by,
    deleted
    )
  VALUES (
    in_job_title,
    in_min_salary,
    in_max_salary,
    in_employee_id,
    in_login_id,
    FALSE
  )
  RETURNING job_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

