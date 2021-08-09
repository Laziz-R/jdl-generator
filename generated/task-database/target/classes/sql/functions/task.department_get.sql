DROP FUNCTION IF EXISTS task.department_get;
CREATE FUNCTION task.department_get(
    in_login_id BIGINT,
    in_department_id BIGINT
    )
RETURNS TABLE (
    department_id BIGINT,
    department_name TEXT,
    location_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.department_get.sql
**		Name: task.department_get
**		Desc: Get department data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: department infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.department_get';
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

    IF in_department_id IS NULL OR in_department_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_department_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your department ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        department.department_id,
        department.department_name,
        department.location_id    FROM task.department
    WHERE
        department.department_id = in_department_id
        AND NOT department.deleted;
END;
$$ LANGUAGE plpgsql;