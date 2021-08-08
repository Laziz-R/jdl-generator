package ${package}.model;
<#if (enum.javadoc)??>

/**
* The type ${enum.name.pascalCase}
*
* @author ${(author)!""}
* @since ${aDate?date?iso_utc}
*/
public enum ${enum.name.pascalCase} {
<#list enum.fields as field>
    ${field}<#sep>,
</#list>;
}