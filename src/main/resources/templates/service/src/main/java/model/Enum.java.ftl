package ${package}.model;

public enum ${enum.name.pascalCase} {
<#list enum.fields as field>
    ${field}<#sep>,
</#list>;
}