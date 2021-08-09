DROP FUNCTION IF EXISTS task.region_get;
CREATE FUNCTION task.region_get(
    in_login_id BIGINT,
    in_region_id BIGINT
    )
RETURNS TABLE (
    region_id BIGINT,
    region_name TEXT    )
AS $$
/******************************************************************************
**		File: task.region_get.sql
**		Name: task.region_get
**		Desc: Get region data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: region infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.region_get';
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

    IF in_region_id IS NULL OR in_region_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_region_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your region ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        region.region_id,
        region.region_name    FROM task.region
    WHERE
        region.region_id = in_region_id
        AND NOT region.deleted;
END;
$$ LANGUAGE plpgsql;