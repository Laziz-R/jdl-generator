DROP FUNCTION IF EXISTS task.job_history_add;
CREATE FUNCTION task.job_history_add(
  in_login_id BIGINT,
  in_start_date DATE,
  in_end_date DATE,
  in_language TEXT,
  in_job_id BIGINT,
  in_department_id BIGINT,
  in_employee_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.job_history_add.sql
**        Name: task.job_history_add
**        Desc: add job_history's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-09
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-09        laziz            Created
*******************************************************************************
**        Return values: job_history id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_history_add';
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

  INSERT INTO task.job_history(
    start_date,
    end_date,
    language,
    job_id,
    department_id,
    employee_id,
    created_by,
    deleted
    )
  VALUES (
    in_start_date,
    in_end_date,
    in_language,
    in_job_id,
    in_department_id,
    in_employee_id,
    in_login_id,
    FALSE
  )
  RETURNING job_history_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

