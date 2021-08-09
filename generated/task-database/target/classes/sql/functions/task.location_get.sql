DROP FUNCTION IF EXISTS task.location_get;
CREATE FUNCTION task.location_get(
    in_login_id BIGINT,
    in_location_id BIGINT
    )
RETURNS TABLE (
    location_id BIGINT,
    street_address TEXT,
    postal_code TEXT,
    city TEXT,
    state_province TEXT,
    country_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.location_get.sql
**		Name: task.location_get
**		Desc: Get location data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: location infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.location_get';
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

    IF in_location_id IS NULL OR in_location_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_location_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your location ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        location.location_id,
        location.street_address,
        location.postal_code,
        location.city,
        location.state_province,
        location.country_id    FROM task.location
    WHERE
        location.location_id = in_location_id
        AND NOT location.deleted;
END;
$$ LANGUAGE plpgsql;