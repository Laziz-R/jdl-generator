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
ADD COLUMN IF NOT EXISTS ${field.name.snakeCase} ${field.type.pgName} <#if field.required>NOT NULL</#if>,
</#list>

ADD COLUMN IF NOT EXISTS created_on TIMESTAMP WITH TIME ZONE DEFAULT now(),
ADD COLUMN IF NOT EXISTS created_by BIGINT NOT NULL,
ADD COLUMN IF NOT EXISTS modified_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS modified_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted_on TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS deleted_by BIGINT,
ADD COLUMN IF NOT EXISTS deleted BOOLEAN NOT NULL DEFAULT FALSE;