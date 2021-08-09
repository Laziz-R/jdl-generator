DROP FUNCTION IF EXISTS task.job_history_update;
CREATE FUNCTION task.job_history_update(
    in_login_id BIGINT,
    in_job_history_id BIGINT,
    in_start_date DATE,
    in_end_date DATE,
    in_language TEXT,
    in_job_id BIGINT,
    in_department_id BIGINT,
    in_employee_id BIGINT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.job_history_update.sql
**		Name: task.job_history_update
**		Desc: update job_history's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: job_history id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_history_update';
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
    IF NOT EXISTS(SELECT * FROM task.job_history WHERE job_history.job_history_id = in_job_history_id AND NOT deleted) THEN
        RAISE EXCEPTION 'job_history % not exists --> % step %', in_job_history_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job_history ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.job_history
    SET
        start_date = in_start_date,
        end_date = in_end_date,
        language = in_language,
        job_id = in_job_id,
        department_id = in_department_id,
        employee_id = in_employee_id,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        job_history.job_history_id = in_job_history_id
        AND NOT job_history.deleted
  RETURNING job_history_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
