<#assign aDate = .now>
<#assign schema = schema.snakeCase/>
<#assign table = entity.name.snakeCase/>
<#assign functionName = "${schema}.${table}_get_summary_list"/>
DROP FUNCTION IF EXISTS ${functionName};
CREATE FUNCTION ${functionName}(
    in_login_id BIGINT,
    in_sort_expression TEXT,
	in_filter_condition TEXT,
    in_skip BIGINT,
    in_page_size BIGINT
)
RETURNS TABLE (
    hidden_row_count BIGINT,
    hidden_is_empty BOOLEAN,
    ${table}_id BIGINT,
<#list entity.fields as field>
    ${field.name.snakeCase} ${field.type.pgName}<#sep>,
</#list>
)
AS $$
/******************************************************************************
**		File: ${functionName}.sql
**		Name: ${functionName}
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

DECLARE
    FN_NAME CONSTANT TEXT := '${functionName}';
    STEP_INDEX INTEGER;
    STEP_DESC VARCHAR(500);
    DATA_QUERY TEXT;
BEGIN

---------------------------------------------------------------------------------------------------------------------------------------------------
    STEP_INDEX := 0;
    STEP_DESC := FN_NAME || ': Initialized ...';
    RAISE NOTICE '%', STEP_DESC;
----------------------------------------------------------------------------------------------------------------------------------------------------
    -- default is empty true
    -- no output row here
    hidden_is_empty := TRUE;

    -- update sort expression for next usage
    IF in_sort_expression <> '' AND in_sort_expression IS NOT NULL THEN 
	    in_sort_expression := ' ORDER BY ' || in_sort_expression;
    ELSE 
        in_sort_expression := '';
    END IF;
    
    -- update filter condition for next usage
    IF in_filter_condition <> '' AND in_filter_condition IS NOT NULL THEN
      in_filter_condition := ' WHERE ' || in_filter_condition;
    ELSE 
      in_filter_condition := '';
    END IF;

    DATA_QUERY := '
      WITH data AS (
        SELECT
        ${table}.${table}_id,
<#list entity.fields as field>
        ${table}.${field.name.snakeCase}<#sep>,
</#list>
        FROM ${schema}.${table}
        WHERE 
            NOT ${table}.deleted
      )';

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
    RAISE NOTICE '%', FN_NAME || ': Calculate requested data total row count';
----------------------------------------------------------------------------------------------------------------------------------------------------
    EXECUTE
      data_query || '
        SELECT
          count(*)
        FROM data '
        ||  in_filter_condition
    INTO hidden_row_count;
	
---------------------------------------------------------------------------------------------------------------------------------------------------
    STEP_INDEX := 30;
    RAISE NOTICE '%', FN_NAME || ': Select Data';
----------------------------------------------------------------------------------------------------------------------------------------------------
    -- put all output columns here
    FOR
    ${table}_id,
<#list entity.fields as field>
    ${field.name.snakeCase}<#sep>,
</#list>
    IN
    EXECUTE 
      data_query || '
        SELECT
          *
        FROM data'|| 
        in_filter_condition  || 
        in_sort_expression || 
        ' OFFSET $1 LIMIT $2' 
      USING in_skip, in_page_size	
    LOOP
      -- update to false since we have output data here
      hidden_is_empty = false;
      RETURN NEXT;
    END LOOP;
    
    -- checking if data were returned overwise put empty row with total count only 
    IF hidden_is_empty THEN
      RETURN NEXT;
    END IF;
END;
$$ LANGUAGE plpgsql;