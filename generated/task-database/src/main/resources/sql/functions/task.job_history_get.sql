DROP FUNCTION IF EXISTS task.job_history_get;
CREATE FUNCTION task.job_history_get(
    in_login_id BIGINT,
    in_job_history_id BIGINT
    )
RETURNS TABLE (
    job_history_id BIGINT,
    start_date DATE,
    end_date DATE,
    language TEXT,
    job_id BIGINT,
    department_id BIGINT,
    employee_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.job_history_get.sql
**		Name: task.job_history_get
**		Desc: Get job_history data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: job_history infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.job_history_get';
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

    IF in_job_history_id IS NULL OR in_job_history_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_job_history_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job_history ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        job_history.job_history_id,
        job_history.start_date,
        job_history.end_date,
        job_history.language,
        job_history.job_id,
        job_history.department_id,
        job_history.employee_id    FROM task.job_history
    WHERE
        job_history.job_history_id = in_job_history_id
        AND NOT job_history.deleted;
END;
$$ LANGUAGE plpgsql;