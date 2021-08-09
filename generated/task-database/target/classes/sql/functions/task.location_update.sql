DROP FUNCTION IF EXISTS task.location_update;
CREATE FUNCTION task.location_update(
    in_login_id BIGINT,
    in_location_id BIGINT,
    in_street_address TEXT,
    in_postal_code TEXT,
    in_city TEXT,
    in_state_province TEXT,
    in_country_id BIGINT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.location_update.sql
**		Name: task.location_update
**		Desc: update location's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: location id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.location_update';
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
    IF NOT EXISTS(SELECT * FROM task.location WHERE location.location_id = in_location_id AND NOT deleted) THEN
        RAISE EXCEPTION 'location % not exists --> % step %', in_location_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your location ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.location
    SET
        street_address = in_street_address,
        postal_code = in_postal_code,
        city = in_city,
        state_province = in_state_province,
        country_id = in_country_id,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        location.location_id = in_location_id
        AND NOT location.deleted
  RETURNING location_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
