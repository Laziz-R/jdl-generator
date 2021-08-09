DROP FUNCTION IF EXISTS task.country_update;
CREATE FUNCTION task.country_update(
    in_login_id BIGINT,
    in_country_id BIGINT,
    in_country_name TEXT,
    in_region_id BIGINT)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: task.country_update.sql
**		Name: task.country_update
**		Desc: update country's data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: country id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.country_update';
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
    IF NOT EXISTS(SELECT * FROM task.country WHERE country.country_id = in_country_id AND NOT deleted) THEN
        RAISE EXCEPTION 'country % not exists --> % step %', in_country_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your country ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
 STEP_INDEX := 20;
 RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

 UPDATE task.country
    SET
        country_name = in_country_name,
        region_id = in_region_id,
        modified_on = now(),
        modified_by = in_login_id
    WHERE
        country.country_id = in_country_id
        AND NOT country.deleted
  RETURNING country_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
