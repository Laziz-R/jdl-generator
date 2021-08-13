DROP FUNCTION IF EXISTS task.book_author_get_summary_list;
CREATE FUNCTION task.book_author_get_summary_list(
    in_login_id BIGINT,
    in_sort_expression TEXT,
	in_filter_condition TEXT,
    in_skip BIGINT,
    in_page_size BIGINT
)
RETURNS TABLE (
    hidden_row_count BIGINT,
    hidden_is_empty BOOLEAN,
    book_author_id BIGINT,
    book_id BIGINT,
    author_id BIGINT)
AS $$
/******************************************************************************
**		File: task.book_author_get_summary_list.sql
**		Name: task.book_author_get_summary_list
**		Desc: Get book_authors data
*******************************************************************************
**		Auth: laziz
**		Date: 2021-08-13
*******************************************************************************
**		Change History
**		Date: 			Author:				Description:
*******************************************************************************
**		2021-08-13		laziz     	Created
**
*******************************************************************************
**		Return values: book_author infor.
*******************************************************************************
**/

DECLARE
    FN_NAME CONSTANT TEXT := 'task.book_author_get_summary_list';
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
        book_author.book_author_id,
        book_author.book_id,
        book_author.author_id        FROM task.book_author
        WHERE 
            NOT book_author.deleted
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
    book_author_id,
    book_id,
    author_id    IN
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