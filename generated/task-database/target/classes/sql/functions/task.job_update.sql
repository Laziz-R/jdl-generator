DROP FUNCTION IF EXISTS task.job_update;
CREATE FUNCTION task.job_update(
    in_login_id BIGINT,
    in_job_id BIGINT,
    in_job_title TEXT,
    in_min_salary BIGINT,
    in_max_salary BIGINT,
    in_employee_id BIGINT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.job_update.sql
**		Name: task.job_update
**		Desc: update job's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: job id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_update';
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
    IF NOT EXISTS(SELECT * FROM task.job WHERE job.job_id = in_job_id AND NOT deleted) THEN
        RAISE EXCEPTION 'job % not exists --> % step %', in_job_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.job
    SET
        job_title = in_job_title,
        min_salary = in_min_salary,
        max_salary = in_max_salary,
        employee_id = in_employee_id,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        job.job_id = in_job_id
        AND NOT job.deleted
  RETURNING job_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
