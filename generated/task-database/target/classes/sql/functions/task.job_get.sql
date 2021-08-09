DROP FUNCTION IF EXISTS task.job_get;
CREATE FUNCTION task.job_get(
    in_login_id BIGINT,
    in_job_id BIGINT
    )
RETURNS TABLE (
    job_id BIGINT,
    job_title TEXT,
    min_salary BIGINT,
    max_salary BIGINT,
    employee_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.job_get.sql
**		Name: task.job_get
**		Desc: Get job data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: job infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.job_get';
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

    IF in_job_id IS NULL OR in_job_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_job_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        job.job_id,
        job.job_title,
        job.min_salary,
        job.max_salary,
        job.employee_id    FROM task.job
    WHERE
        job.job_id = in_job_id
        AND NOT job.deleted;
END;
$$ LANGUAGE plpgsql;