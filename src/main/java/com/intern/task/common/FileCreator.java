package com.intern.task.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class FileCreator {
    Vertx vertx;
    TemplateEngine engine;

    public FileCreator(Vertx vertx) {
        this.vertx = vertx;
        this.engine = FreeMarkerTemplateEngine.create(vertx);
    }

    public Future<Void> create(File tempFile, String newFileName, JsonObject context) {
        Promise<Void> promise = Promise.promise();
        File newFile = new File(newFileName);

        if (!newFile.exists()) {
            try {
                Files.createDirectories(Paths.get(newFile.getParent()));
            } catch (IOException e) {
                promise.fail(e);
            }
        }
        if (tempFile.getName().endsWith(".ftl")) {
            engine.render(context, tempFile.getPath())
                    .onSuccess(buffer ->
                            vertx.fileSystem()
                                    .writeFile(newFileName, buffer)
                                    .onSuccess(ar -> promise.complete())
                                    .onFailure(promise::fail)
                    )
                    .onFailure(promise::fail);
        } else {
            vertx.fileSystem().copy(tempFile.getPath(), newFileName)
                    .onSuccess(ar -> promise.complete())
                    .onFailure(promise::fail);
        }
        return promise.future();
    }
}
