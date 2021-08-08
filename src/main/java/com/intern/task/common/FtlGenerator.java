package com.intern.task.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.intern.task.model.jdl.Entity;
import com.intern.task.model.jdl.Enum;
import com.intern.task.model.jdl.Name;
import com.intern.task.util.CaseUtil;

import org.apache.log4j.Logger;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

public class FtlGenerator implements Structure {
    private static Logger LOGGER = Logger.getLogger(FtlGenerator.class);

    private JdlCode jdlCode;
    private FileCreator fileCreator;
    private JsonObject contextJson = new JsonObject();

    private Name schema;
    private String extendedJavaPath;

    private List<Future> writtenFileNames = new ArrayList<>();

    public FtlGenerator(RoutingContext ctx) {
        Vertx vertx = ctx.vertx();
        MultiMap params = ctx.request().params();

        this.fileCreator = new FileCreator(vertx);

        params.forEach(p -> {
            if(p.getKey().equals("schema")){
                schema = new Name(p.getValue(), CaseUtil.SNAKE_CASE);
                this.contextJson.put("schema", schema);
            } else {
                this.contextJson.put(p.getKey(), p.getValue());
            }
        });
        this.contextJson.put("package", contextJson.getString("groupId") + "." + schema.getSnakeCase());

        FileUpload fUp = ctx.fileUploads().iterator().next();
        String source = vertx.fileSystem().readFileBlocking(fUp.uploadedFileName()).toString();
        this.jdlCode = new JdlCode(source);
    }

    public Future<JsonObject> generate() {
        Promise<JsonObject> promise = Promise.promise();

        deleteDirectory(new File(GENERATE_PATH));
        extendedJavaPath = "java/" + contextJson.getString("package").replaceAll("\\.", "/");

        jdlCode.define();
        contextJson.put("entities", jdlCode.getEntities());

        generateFile(new File(TEMPLATE_PATH));
        CompositeFuture.all(writtenFileNames)
            .onSuccess(ar -> {
                promise.complete(
                    new JsonObject()
                        .put(GENERATE_PATH, getTree(new File(GENERATE_PATH)))
                );
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    private void generateFile(File file) {
        if(!file.exists()) return;
        if (file.isDirectory()) {
            for (File subFile : file.listFiles())
                generateFile(subFile);
        } else if (file.isFile()) {
            try {
                //  Form generating path of the file
                String newFileName = file.getPath()
                    .replace(TEMPLATE_PATH + "\\", GENERATE_PATH + "\\" + schema.getKebabCase() + "-")
                    .replaceFirst("java", extendedJavaPath)
                    .replace(".ftl", "");

                //  Generating template files
                switch(file.getName()){
                    case "Entity.java.ftl":
                    case "EntityCommand.java.ftl":
                    case "EntityList.java.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("Entity", e.getName().getPascalCase()),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "Enum.java.ftl":
                        for (Enum e : jdlCode.getEnums()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("Enum", e.getName().getPascalCase()),
                                    contextJson.put("enum", e)
                                )
                            );
                        }
                        break;
                    case "SchemaController.java.ftl":
                    case "SchemaService.java.ftl":
                    case "SchemaRouter.java.ftl":
                        writtenFileNames.add(
                            fileCreator.create(
                                file,
                                newFileName.replace("Schema", schema.getPascalCase()),
                                contextJson
                            )
                        );
                        break;
                    case "create-table.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-table", schema.getSnakeCase() + "." + e.getName().getSnakeCase()),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "create-type.sql.ftl":
                        for (Enum e : jdlCode.getEnums()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-type", schema.getSnakeCase() + "." + e.getName().getSnakeCase()),
                                    contextJson.put("enum", e)
                                )
                            );
                        }
                        break;
                    case "create-add-function.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-add-function", schema.getSnakeCase() + "." + e.getName().getSnakeCase() + "_" + "add"),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "create-delete-function.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-delete-function", schema.getSnakeCase() + "." + e.getName().getSnakeCase() + "_" + "delete"),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "create-get-function.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-get-function", schema.getSnakeCase() + "." + e.getName().getSnakeCase() + "_" + "get"),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "create-get-list-function.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-get-list-function", schema.getSnakeCase() + "." + e.getName().getSnakeCase() + "_" + "get_list"),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                    case "create-update-function.sql.ftl":
                        for (Entity e : jdlCode.getEntities()) {
                            writtenFileNames.add(
                                fileCreator.create(
                                    file,
                                    newFileName.replace("create-update-function", schema.getSnakeCase() + "." + e.getName().getSnakeCase() + "_" + "update"),
                                    contextJson.put("entity", e)
                                )
                            );
                        }
                        break;
                        
                    case "config.properties.ftl":
                        String currentPath = (System.getProperty("user.dir") + "/" + newFileName.split("src")[0]).replace("\\", "/");
                        writtenFileNames.add(fileCreator.create(file, newFileName, contextJson.put("currentPath", currentPath)));
                        break;

                    default:
                        writtenFileNames.add(fileCreator.create(file, newFileName, contextJson));
                }
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
    }

    private boolean deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                if (deleteDirectory(subFile))
                    LOGGER.info("File deleted: " + subFile.getName());
                else
                    LOGGER.info("File didn't delete: " + subFile.getName());
            }
        }
        return file.delete();
    }

    private JsonArray getTree(File file){
        JsonArray items = new JsonArray();
        if(file.isFile()){
            return items.add(file.getName());
        } 
        if(file.isDirectory()){
            for(File subFile: file.listFiles())
                if(subFile.isDirectory())
                    items.add(new JsonObject().put(subFile.getName(), getTree(subFile)));
            for(File subFile: file.listFiles())
                if(subFile.isFile())
                    items.add(subFile.getName());
        }
        return items;
    }
}