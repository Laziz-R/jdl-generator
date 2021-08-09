DROP FUNCTION IF EXISTS task.job_delete;
CREATE FUNCTION task.job_delete(
  in_login_id BIGINT,
  in_job_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.job_delete.sql
**		Name: task.job_delete
**		Desc: delete job
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
**		Return values: job id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.job_delete';
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
        RAISE EXCEPTION 'Cannot delete job % not exists --> % step %', in_job_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your job ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE task.job
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        job.job_id = in_job_id
        AND NOT deleted
  RETURNING job_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
