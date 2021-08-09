DROP FUNCTION IF EXISTS task.department_add;
CREATE FUNCTION task.department_add(
  in_login_id BIGINT,
  in_department_name TEXT,
  in_location_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.department_add.sql
**        Name: task.department_add
**        Desc: add department's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-09
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-09        laziz            Created
*******************************************************************************
**        Return values: department id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.department_add';
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

  INSERT INTO task.department(
    department_name,
    location_id,
    created_by,
    deleted
    )
  VALUES (
    in_department_name,
    in_location_id,
    in_login_id,
    FALSE
  )
  RETURNING department_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

