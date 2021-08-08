CREATE TYPE ${schema.snakeCase}.${enum.name.snakeCase} AS ENUM (
    <#list enum.fields as field>
    field<#sep>,
    </#list>
);