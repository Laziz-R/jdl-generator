DROP FUNCTION IF EXISTS task.job_history_delete;
CREATE FUNCTION task.job_history_delete(
  in_login_id BIGINT,
  in_job_history_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.job_history_delete.sql
**		Name: task.job_history_delete
**		Desc: delete job_history
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			  Author:				Description:
*******************************************************************************
**		2021-08-09		laziz   Created
**
*******************************************************************************
**		Return values: job_history id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_history_delete';
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
        RAISE EXCEPTION 'Cannot delete job_history % not exists --> % step %', in_job_history_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job_history ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.job_history
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        job_history.job_history_id = in_job_history_id
        AND NOT deleted
  RETURNING job_history_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
