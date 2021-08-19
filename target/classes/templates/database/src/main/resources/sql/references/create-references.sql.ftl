<#assign schema = schema.snakeCase/>
<#list foreignKeys as fk>
<#assign fromTable = fk.fromEntityName.snakeCase/>
<#assign toTable = fk.toEntityName.snakeCase/>
<#assign fromColumn = fk.fromField.name.snakeCase/>
<#assign toColumn = fk.toField.name.snakeCase/>
ALTER TABLE ONLY ${schema}.${fromTable}
    ADD CONSTRAINT ${fromTable}_${fromColumn}_fk FOREIGN KEY (${fromColumn})
        REFERENCES ${schema}.${toTable}(${toColumn}) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
</#list>