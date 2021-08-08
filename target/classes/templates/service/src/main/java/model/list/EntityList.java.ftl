<#assign tablePascal = entity.name.pascalCase/>
<#assign tableCamel = entity.name.camelCase/>
package ${package}.model.list;

import ${package}.model.${tablePascal};

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;

public class ${tablePascal}List extends ArrayList<${tablePascal}> {

    private static final long serialVersionUID = 1L;
    private final String label = "${tableCamel}List";

    public String getLabel() {
        return label;
    }

    public JsonArray toJsonArray(){
        JsonArray array = new JsonArray();
        this.forEach(e -> array.add(JsonObject.mapFrom(e)));
        return array;
    }

}