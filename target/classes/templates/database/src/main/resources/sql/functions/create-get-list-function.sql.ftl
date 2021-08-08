<#assign aDate = .now>
<#assign schema = schema.snakeCase/>
<#assign table = entity.name.snakeCase/>
<#assign function_name = "${schema}.${table}_get_list"/>
DROP FUNCTION IF EXISTS ${function_name};
CREATE FUNCTION ${function_name}(
    in_login_id BIGINT,
    in_skip BIGINT,
    in_page_size BIGINT
    )
RETURNS TABLE (
    ${table}_id BIGINT,
<#list entity.fields as field>
    ${field.name.snakeCase} ${field.type.pgName}<#sep>,
</#list>
)
AS $$
/******************************************************************************
**		File: ${function_name}.sql
**		Name: ${function_name}
**		Desc: Get ${table}s data
*******************************************************************************
**		Auth: ${author}
**		Date: ${aDate?date?iso_utc}
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		${aDate?date?iso_utc}		${author}     	Created
**
*******************************************************************************
**		Return values: ${table} infor.
*******************************************************************************
**/

  DECLARE FN_NAME CONSTANT TEXT := '${function_name}';
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

    IF in_login_id IS NULL OR in_login_id < 0 THEN
        RAISE EXCEPTION 'Nonexistent ID --> % step % %', in_login_id, STEP_INDEX, STEP_DESC
        USING HINT = 'Please check your login';
    END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------
  STEP_INDEX := 20;
  RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
  RETURN QUERY
    SELECT
        ${table}.${table}_id,
<#list entity.fields as field>
        ${table}.${field.name.snakeCase}<#sep>,
</#list>
    FROM ${schema}.${table}
    WHERE
        NOT ${table}.deleted
    OFFSET in_skip
    LIMIT in_page_size;
END;
$$ LANGUAGE plpgsql;