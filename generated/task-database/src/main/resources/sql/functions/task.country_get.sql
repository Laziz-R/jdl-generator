DROP FUNCTION IF EXISTS task.country_get;
CREATE FUNCTION task.country_get(
    in_login_id BIGINT,
    in_country_id BIGINT
    )
RETURNS TABLE (
    country_id BIGINT,
    country_name TEXT,
    region_id BIGINT    )
AS $$
/******************************************************************************
**		File: task.country_get.sql
**		Name: task.country_get
**		Desc: Get country data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-09
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-09		laziz     	Created
*******************************************************************************
**		Return values: country infor.
*******************************************************************************
**/

    DECLARE FN_NAME CONSTANT TEXT := 'task.country_get';
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

    IF in_country_id IS NULL OR in_country_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_country_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your country ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        country.country_id,
        country.country_name,
        country.region_id    FROM task.country
    WHERE
        country.country_id = in_country_id
        AND NOT country.deleted;
END;
$$ LANGUAGE plpgsql;