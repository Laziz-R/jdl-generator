<#assign table = entity.name.snakeCase/>
<#assign schema = schema.snakeCase/>
<#assign tableUri = "${schema}.${table}">
-- Table: ${tableUri};

-- DROP TABLE ${tableUri};

CREATE TABLE IF NOT EXISTS ${tableUri} (
    ${table}_id BIGSERIAL NOT NULL,
    CONSTRAINT pk_${schema}_${table}_${table}_id PRIMARY KEY (${table}_id)
);

ALTER TABLE ${tableUri}
<#list entity.fields as field>
<#assign fieldName = field.name.snakeCase/>
<#assign type = field.type.pgName/>
ADD COLUMN IF NOT EXISTS ${fieldName} ${type}<#if !field.validation??>,
<#else>
<#assign valid = field.validation/><#if valid.required> NOT NULL</#if><#if valid.unique> UNIQUE</#if><#if !valid.min?? && !valid.max??>,</#if>
<#if type=='TEXT'><#assign obj = "char_length(${fieldName})"/><#else><#assign obj = fieldName/></#if>
<#if valid.min?? && valid.max??>
    CONSTRAINT ${schema}_${table}_${fieldName}_min_max_check CHECK (${valid.min} <= ${obj} AND ${obj} <= ${valid.max}),
<#elseif valid.min??>
    CONSTRAINT ${schema}_${table}_${fieldName}_min_check CHECK (${valid.min} <= ${obj}),
<#elseif valid.max??>
    CONSTRAINT ${schema}_${table}_${fieldName}_max_check CHECK (${obj} <= ${valid.max}),
</#if>
</#if>
</#list>

ADD COLUMN IF NOT EXISTS created_on TIMESTAMP WITH TIME ZONE DEFAULT now(),
ADD COLUMN IF NOT EXISTS created_by BIGINT NOT NULL,
ADD COLUMN IF NOT EXISTS modified_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS modified_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS deleted_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE;