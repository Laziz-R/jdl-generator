<#assign aDate = .now>
<#assign schema = schema.snakeCase/>
<#assign table = entity.name.snakeCase/>
<#assign functionName = "${schema}.${table}_update"/>
DROP FUNCTION IF EXISTS ${functionName};
CREATE FUNCTION ${functionName}(
  in_login_id BIGINT,
  in_${table}_id BIGINT,
<#list entity.fields as field>
  in_${field.name.snakeCase} ${field.type.pgName}<#sep>,
</#list>
)
RETURNS BIGINT
AS $$
/******************************************************************************
**		File: ${functionName}.sql
**		Name: ${functionName}
**		Desc: update ${table}'s data
*******************************************************************************
**		Auth: ${author}
**		Date: ${aDate?date?iso_utc}
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		${aDate?date?iso_utc}		${author}     	Created
*******************************************************************************
**		Return values: ${table} id.
*******************************************************************************
**/

DECLARE
  FN_NAME CONSTANT TEXT := '${functionName}';
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
      RAISE EXCEPTION '${table} % not exists --> % step %', in_${table}_id, STEP_INDEX, STEP_DESC
      USING HINT = 'Please check your ${table} ID';
  END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  UPDATE ${schema}.${table}
    SET
<#list entity.fields as field>
      ${field.name.snakeCase} = in_${field.name.snakeCase},
</#list>
      modified_on = now(),
      modified_by = in_login_id
    WHERE
      ${table}.${table}_id = in_${table}_id
      AND NOT ${table}.deleted
    RETURNING ${table}_id INTO result;

RETURN result;
END;
$$ LANGUAGE plpgsql;
