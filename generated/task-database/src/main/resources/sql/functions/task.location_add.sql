DROP FUNCTION IF EXISTS task.location_add;
CREATE FUNCTION task.location_add(
  in_login_id BIGINT,
  in_street_address TEXT,
  in_postal_code TEXT,
  in_city TEXT,
  in_state_province TEXT,
  in_country_id BIGINT  )
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: task.location_add.sql
**        Name: task.location_add
**        Desc: add location's data
*******************************************************************************
**        Auth: laziz
**        Date: 2021-08-09
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        2021-08-09        laziz            Created
*******************************************************************************
**        Return values: location id infor.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := 'task.location_add';
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

  INSERT INTO task.location(
    street_address,
    postal_code,
    city,
    state_province,
    country_id,
    created_by,
    deleted
    )
  VALUES (
    in_street_address,
    in_postal_code,
    in_city,
    in_state_province,
    in_country_id,
    in_login_id,
    FALSE
  )
  RETURNING location_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

