<#assign aDate= .now>
<#assign tablePascal = entity.name.pascalCase/>
<#assign tableCamel = entity.name.camelCase/>
<#assign tableSnake = entity.name.snakeCase/>
<#assign tableUpper = entity.name.upperCase/>
package ${package}.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;
<#list entity.libraries as lib>
import ${lib};
</#list>

/**
* The type ${tablePascal}
*
* @author ${(author)!""}
* @since ${aDate?date?iso_utc}
*/
public class ${tablePascal} extends BaseDataContract {

    public static final String ${tableUpper} = "${tableSnake}";

    public static final String ${tableUpper}_ID = "${tableSnake}_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(${tableUpper}_ID)
    private Long ${tableCamel}Id;
<#list entity.fields as field>
<#assign fieldCamel = field.name.camelCase/>
<#assign fieldSnake = field.name.snakeCase/>
<#assign fieldUpper = field.name.upperCase/>

    public static final String ${fieldUpper} = "${fieldSnake}";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(${fieldUpper})
    private ${field.type.jvName} ${fieldCamel};
</#list>

    /**
    * ${tableCamel}Id getter.
    *
    * @return the ${tableCamel}Id
    */
    public Long get${tablePascal}Id() {
        return this.${tableCamel}Id;
    }

    /**
    * * ${tableCamel}Id setter.
    *
    * @param ${tableCamel}Id the ${tableCamel}Id to set
    * @return this
    */
    public ${tablePascal} set${tablePascal}Id(Long ${tableCamel}Id) {
        this.${tableCamel}Id = ${tableCamel}Id;
        return this;
    }
<#list entity.fields as field>
<#assign fieldCamel = field.name.camelCase/>
<#assign fieldPascal = field.name.pascalCase/>

    /**
    * ${fieldCamel} getter.
    *
    * @return the ${fieldCamel}
    */
    public ${field.type.jvName} get${fieldPascal}() {
        return this.${fieldCamel};
    }

    /**
    * ${fieldCamel} setter.
    *
    * @param ${fieldCamel} the ${fieldCamel} to set
    * @return this
    */
    public ${tablePascal} set${fieldPascal}(${field.type.jvName} ${fieldCamel}) {
        this.${fieldCamel} = ${fieldCamel};
        return this;
    }
</#list>


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(<#list entity.fields as field>${field.name.camelCase}, </#list> ${tableCamel}Id);
    }

    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#equals(java.lang.Object)
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ${tablePascal} other = (${tablePascal}) obj;
        return
        <#list entity.fields as field>
            Objects.equals(${field.name.camelCase}, other.${field.name.camelCase}) &&
        </#list>
            Objects.equals(${tableCamel}Id, other.${tableCamel}Id);
    }
}