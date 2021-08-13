<#assign aDate = .now>
<#assign schema = schema.snakeCase/>
<#assign table = entity.name.snakeCase/>
<#assign functionName = "${schema}.${table}_add"/>
DROP FUNCTION IF EXISTS ${functionName};
CREATE FUNCTION ${functionName}(
  in_login_id BIGINT,
  <#list entity.fields as field>
  in_${field.name.snakeCase} ${field.type.pgName}<#sep>,
  </#list>
)
RETURNS BIGINT
AS $$
/******************************************************************************
**        File: ${functionName}.sql
**        Name: ${functionName}
**        Desc: add ${table}'s data
*******************************************************************************
**        Auth: ${author}
**        Date: ${aDate?date?iso_utc}
*******************************************************************************
**        Change History
**        Date:                        Author:              Description:
*******************************************************************************
**        ${aDate?date?iso_utc}        ${author}            Created
*******************************************************************************
**        Return values: ${table} id infor.
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


---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------

  INSERT INTO ${schema}.${table}(
<#list entity.fields as field>
    ${field.name.snakeCase},
</#list>
    created_by,
    deleted
    )
  VALUES (
<#list entity.fields as field>
    in_${field.name.snakeCase},
</#list>
    in_login_id,
    FALSE
  )
  RETURNING ${table}_id INTO result;

  RETURN result;
END;
$$ LANGUAGE plpgsql;

