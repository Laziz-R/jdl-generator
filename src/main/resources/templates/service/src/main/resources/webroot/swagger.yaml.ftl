<#assign schemaPascal = schema.pascalCase/>
<#assign schemaCamel = schema.camelCase/>
<#assign schemaKebab = schema.kebabCase/>
openapi: '3.0.2'
info:
  title: ${schema.pascalCase} APIs
  version: '1.0'
servers:
  - url: http://localhost:8080

paths:
<#list entities as entity>
<#assign tableKebab = entity.name.kebabCase/>
<#assign tableCamel = entity.name.camelCase/>
<#assign tablePascal = entity.name.pascalCase/>

  /${schemaKebab}/${tableKebab}/add: 
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}Add
      requestBody:
        $ref: "#/components/requestBodies/RequestBody${tablePascal}"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"
  
  /${schemaKebab}/${tableKebab}/update:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}Update
      requestBody:
        $ref: "#/components/requestBodies/RequestBody${tablePascal}"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

  /${schemaKebab}/${tableKebab}/delete:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}Delete
      requestBody:
        $ref: "#/components/requestBodies/Request${tablePascal}Id"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

  /${schemaKebab}/${tableKebab}/get:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}Get
      requestBody:
        $ref: "#/components/requestBodies/Request${tablePascal}Id"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

  /${schemaKebab}/${tableKebab}/get-list:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}GetList
      requestBody:
        $ref: "#/components/requestBodies/RequestListParams"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

  /${schemaKebab}/${tableKebab}/get-all:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}GetAll
      requestBody:
        $ref: "#/components/requestBodies/RequestListParams"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

  /${schemaKebab}/${tableKebab}/get-summary-list:
    post:
      tags:
        - "${schemaPascal} ${tablePascal}"
      operationId:  ${schemaCamel}${tablePascal}GetSummaryList
      requestBody:
        $ref: "#/components/requestBodies/RequestListParams"
      responses:
        200:
          $ref: "#/components/responses/Success"
        404:
          $ref: "#/components/responses/Error"

</#list>

components:
  schemas:
<#list entities as entity>
<#assign tableCamel = entity.name.camelCase/>
<#assign tablePascal = entity.name.pascalCase/>
<#assign tableSnake = entity.name.snakeCase/>

    ${tableCamel}Id:
      type: object
      properties:
        ${tableSnake}_id:
          type: integer
          nullable: true
          format: int64
      required:
        - ${tableSnake}_id

    ${tablePascal}:
      allOf:
        - $ref: "#/components/schemas/${tableCamel}Id"
        - type: object
          properties:
          <#list entity.fields as field>
            ${field.name.snakeCase}:
              type: ${field.type.swName}
              nullable: true
          </#list>
</#list>
    Request:
      required:
        - jsonrpc
        - id
        - params
      properties:
        jsonrpc:
          type: string
          enum:
            - "2.0"
          example: "2.0"
        id:
          type: integer
          format: int64
          example: 10
        params:
          type: object
    Response:
      properties:
        id:
          type: integer
          format: int64
        result:
          type: object
          nullable: true
          example: null
        error:
          type: object
          nullable: true
          example: null
    ResponseSuccess:
      allOf:
        - $ref: "#/components/schemas/Response"
        - type: object
          properties:
            result:
              type: object
              nullable: false
              example: {}
    ResponseError:
      allOf:
        - $ref: "#/components/schemas/Response"
        - type: object
          properties:
            error:
              type: object
              nullable: false
              properties:
                code:
                  type: integer
                  format: int64
                  nullable: false
                  example: 1000
                message:
                  type: string
                  nullable: false
                  example: "any error response text message"
              example: { code: 1000, message: "any error response text message"}
    listParams:
      type: object
      properties:
        skip_count:
          type: integer
          nullable: true
          example: 0
        page_size:
          type: integer
          nullable: true
          example: 20

  requestBodies:
    RequestListParams:
      description: "Standard get list"
      required: true
      content:
        application/json:
          schema:
            allOf:
              - $ref: '#/components/schemas/Request'
              - type: object
                properties:
                  params:
                    type: object
                    allOf:
                      - $ref: '#/components/schemas/listParams'
<#list entities as entity>
<#assign tableCamel = entity.name.camelCase/>
<#assign tablePascal = entity.name.pascalCase/>
<#assign tableSnake = entity.name.snakeCase/>
    Request${tablePascal}IdListParams:
      description: "Standard get list by ${tablePascal} Request"
      required: true
      content:
        application/json:
          schema:
            allOf:
              - $ref: '#/components/schemas/Request'
              - type: object
                properties:
                  params:
                    type: object
                    allOf:
                      - $ref: '#/components/schemas/${tableCamel}Id'
                      - $ref: '#/components/schemas/listParams'
    Request${tablePascal}Id:
      description: "Standard ${tablePascal} Request"
      required: true
      content:
          application/json:
            schema:
              allOf:
                - $ref: '#/components/schemas/Request'
                - type: object
                  properties:
                    params:
                      $ref: '#/components/schemas/${tableCamel}Id'
    RequestBody${tablePascal}:
      description: "Add ${tablePascal} Request"
      required: true
      content:
        application/json:
          schema:
            allOf:
              - $ref: '#/components/schemas/Request'
              - type: object
                properties:
                  params:
                    type: object
                    properties:
                      ${tableSnake}:
                        $ref: '#/components/schemas/${tablePascal}'
</#list>
  responses:
    Success:
      description: "Success Response"
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/ResponseSuccess"
    Error:
      description: "Error response"
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/ResponseError"