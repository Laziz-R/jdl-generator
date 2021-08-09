DROP FUNCTION IF EXISTS task.job_task_get;
CREATE FUNCTION task.job_task_get(
    in_login_id BIGINT,
    in_job_task_id BIGINT
    )
RETURNS TABLE (
    job_task_id BIGINT,
    job_title TEXT,
    title TEXT    )
AS $$
/******************************************************************************
**		File: task.job_task_get.sql
**		Name: task.job_task_get
**		Desc: Get job_task data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: job_task infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.job_task_get';
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

    IF in_job_task_id IS NULL OR in_job_task_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_job_task_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job_task ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        job_task.job_task_id,
        job_task.job_title,
        job_task.title    FROM task.job_task
    WHERE
        job_task.job_task_id = in_job_task_id
        AND NOT job_task.deleted;
END;
$$ LANGUAGE plpgsql;