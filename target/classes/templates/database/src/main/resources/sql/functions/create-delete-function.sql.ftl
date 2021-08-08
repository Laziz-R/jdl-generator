<#assign aDate = .now>
<#assign schema = schema.snakeCase/>
<#assign table = entity.name.snakeCase/>
<#assign function_name = "${schema}.${table}_delete"/>
DROP FUNCTION IF EXISTS ${function_name};
CREATE FUNCTION ${function_name}(
  in_login_id BIGINT,
  in_${table}_id BIGINT
  )
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: ${function_name}.sql
**		Name: ${function_name}
**		Desc: delete ${table}
*******************************************************************************
**		Auth: ${author}
**		Date: ${aDate?date?iso_utc}
*******************************************************************************
**		Change History
**		Date: 			  Author:				Description:
*******************************************************************************
**		${aDate?date?iso_utc}		${author}   Created
**
*******************************************************************************
**		Return values: ${table} id.
*******************************************************************************
**/
  DECLARE FN_NAME CONSTANT TEXT := '${function_name}';
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
    IF NOT EXISTS(SELECT * FROM ${schema}.${table} WHERE ${table}.${table}_id = in_${table}_id AND NOT deleted) THEN
        RAISE EXCEPTION 'Cannot delete ${table} % not exists --> % step %', in_${table}_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your ${table} ID';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE ${schema}.${table}
    SET
        deleted_on = now(),
        deleted_by = in_login_id,
        deleted = TRUE
    WHERE
        ${table}.${table}_id = in_${table}_id
        AND NOT deleted
  RETURNING ${table}_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;
