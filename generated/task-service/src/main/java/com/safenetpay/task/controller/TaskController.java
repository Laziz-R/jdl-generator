package com.safenetpay.task.controller;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import com.safenetpay.task.db.TaskService;
import com.safenetpay.task.model.Book;
import com.safenetpay.task.model.list.BookList;
import com.safenetpay.task.model.Author;
import com.safenetpay.task.model.list.AuthorList;
import com.safenetpay.task.model.BookAuthor;
import com.safenetpay.task.model.list.BookAuthorList;
import com.safenetpay.task.need.ApplicationRuntimeException;
import com.safenetpay.task.need.Error;
import com.safenetpay.task.need.UserCredentials;

import org.apache.log4j.Logger;


public class TaskController extends BaseController{
    private static Logger LOGGER = Logger.getLogger(TaskController.class);

    private Vertx vertx;
    private TaskService taskService;

    public TaskController(Vertx vertx) {
        this.vertx = vertx;
        this.taskService = new TaskService(vertx);
    }


    //  #region  Common handlers

    /**
    * Default SomeSchemaServiceController fail handler.
    *
    * @param context - context
    */
    public void defaultFailureHandler(RoutingContext context) {
        try {
            if (context.failure() instanceof ApplicationRuntimeException) {
                ApplicationRuntimeException ex = (ApplicationRuntimeException) context.failure();
                LOGGER.error("info: SomeSchemaServiceController default failure handler");
                this.respondJsonResult(
                    context,
                    200,
                    this.getId(context),
                    null,
                    new JsonObject()
                        .put("code", ex.getError().getCode())
                        .put("message", ex.toString())
                );
            } else {
                LOGGER.error("info: SomeSchemaServiceController default failure handler");
                this.respondJsonResult(
                    context,
                    200,
                    this.getId(context),
                    null,
                    new JsonObject()
                        .put("code", -1)
                        .put("message", context.failure().getMessage())
                );
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            this.respondJsonResult(
                context,
                200,
                this.getId(context),
                null,
                new JsonObject()
                    .put("code", -1)
                    .put("message",
                        String.format("unknown error: Method %s for path %s with message %s",
                            context.request().method(),
                            context.request().absoluteURI(),
                            e.getMessage())
                    )
            );
        }
    }

    //  #endregion


    
    // region Book handler
    
    /**
    * BookAdd handler.
    *
    * @param context - context
    */
    public void handleBookAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("book") != null) {
                try{                    
                    Book book = Book.fromJsonObject(params.getJsonObject("book").encodePrettily(), Book.class);
                    Future<Long> futureBook = taskService.bookAdd(credentials, book);
                    futureBook
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task BookAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookDelete handler.
    *
    * @param context - context
    */
    public void handleBookDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long bookId = params.getLong("book_id");
            Future<Long> futureBook = taskService.bookDelete(credentials, bookId);
            futureBook
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task BookDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookUpdate handler.
    *
    * @param context - context
    */
    public void handleBookUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("book") != null) {
                try{                    
                    Book book = Book.fromJsonObject(params.getJsonObject("book").encodePrettily(), Book.class);
                    Future<Long> futureBook = taskService.bookUpdate(credentials, book);
                    futureBook
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task BookUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookGet handler.
    *
    * @param context - routing context
    */
    public void handleBookGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long bookId = params.getLong("book_id");
            Future<Book> futureBook = taskService.bookGet(credentials, bookId);
            futureBook
                .onSuccess(res -> {
                    Book book = res;
                    if (book == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("book", book.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookGetList handler.
    *
    * @param context - routing context
    */
    public void handleBookGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<BookList> futureBookList = taskService.bookGetList(credentials, skip, pageSize);
            futureBookList
                .onSuccess(res -> {
                    BookList bookList = res;
                    if (bookList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(bookList.getLabel(), bookList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region Author handler
    
    /**
    * AuthorAdd handler.
    *
    * @param context - context
    */
    public void handleAuthorAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("author") != null) {
                try{                    
                    Author author = Author.fromJsonObject(params.getJsonObject("author").encodePrettily(), Author.class);
                    Future<Long> futureAuthor = taskService.authorAdd(credentials, author);
                    futureAuthor
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("author_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task AuthorAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * AuthorDelete handler.
    *
    * @param context - context
    */
    public void handleAuthorDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long authorId = params.getLong("author_id");
            Future<Long> futureAuthor = taskService.authorDelete(credentials, authorId);
            futureAuthor
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("author_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task AuthorDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * AuthorUpdate handler.
    *
    * @param context - context
    */
    public void handleAuthorUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("author") != null) {
                try{                    
                    Author author = Author.fromJsonObject(params.getJsonObject("author").encodePrettily(), Author.class);
                    Future<Long> futureAuthor = taskService.authorUpdate(credentials, author);
                    futureAuthor
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("author_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task AuthorUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * AuthorGet handler.
    *
    * @param context - routing context
    */
    public void handleAuthorGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long authorId = params.getLong("author_id");
            Future<Author> futureAuthor = taskService.authorGet(credentials, authorId);
            futureAuthor
                .onSuccess(res -> {
                    Author author = res;
                    if (author == null) {
                        throw new ApplicationRuntimeException(
                            "Task AuthorGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("author", author.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * AuthorGetList handler.
    *
    * @param context - routing context
    */
    public void handleAuthorGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<AuthorList> futureAuthorList = taskService.authorGetList(credentials, skip, pageSize);
            futureAuthorList
                .onSuccess(res -> {
                    AuthorList authorList = res;
                    if (authorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task AuthorGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(authorList.getLabel(), authorList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion

    
    // region BookAuthor handler
    
    /**
    * BookAuthorAdd handler.
    *
    * @param context - context
    */
    public void handleBookAuthorAdd(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("book_author") != null) {
                try{                    
                    BookAuthor bookAuthor = BookAuthor.fromJsonObject(params.getJsonObject("book_author").encodePrettily(), BookAuthor.class);
                    Future<Long> futureBookAuthor = taskService.bookAuthorAdd(credentials, bookAuthor);
                    futureBookAuthor
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_author_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task BookAuthorAdd failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookAuthorDelete handler.
    *
    * @param context - context
    */
    public void handleBookAuthorDelete(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long bookAuthorId = params.getLong("book_author_id");
            Future<Long> futureBookAuthor = taskService.bookAuthorDelete(credentials, bookAuthorId);
            futureBookAuthor
                .onSuccess(res -> {
                    try{
                        if (res > 0) {
                            this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_author_id", res), null);
                        } else {
                            throw new ApplicationRuntimeException(
                                "Task BookAuthorDelete failed.",
                                Error.DATABASE
                            );
                        }
                    } catch (Exception e) {
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookAuthorUpdate handler.
    *
    * @param context - context
    */
    public void handleBookAuthorUpdate(RoutingContext context) {
        try {
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            if (params.getJsonObject("book_author") != null) {
                try{                    
                    BookAuthor bookAuthor = BookAuthor.fromJsonObject(params.getJsonObject("book_author").encodePrettily(), BookAuthor.class);
                    Future<Long> futureBookAuthor = taskService.bookAuthorUpdate(credentials, bookAuthor);
                    futureBookAuthor
                        .onSuccess(res -> {
                            if (res > 0) {
                                this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_author_id", res), null);
                            } else {
                                throw new ApplicationRuntimeException(
                                    "Task BookAuthorUpdate failed.",
                                    Error.DATABASE
                                );
                            }
                        })
                        .onFailure(ar -> {
                            LOGGER.error(ar.getMessage());
                            context.fail(ar);
                        });
                } catch (Exception e) {
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookAuthorGet handler.
    *
    * @param context - routing context
    */
    public void handleBookAuthorGet(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long bookAuthorId = params.getLong("book_author_id");
            Future<BookAuthor> futureBookAuthor = taskService.bookAuthorGet(credentials, bookAuthorId);
            futureBookAuthor
                .onSuccess(res -> {
                    BookAuthor bookAuthor = res;
                    if (bookAuthor == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookAuthorGet failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put("book_author", bookAuthor.toJsonObject()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }


    /**
    * BookAuthorGetList handler.
    *
    * @param context - routing context
    */
    public void handleBookAuthorGetList(RoutingContext context) {
        try {
            UserCredentials credentials = new UserCredentials().setLoginId(6L);
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<BookAuthorList> futureBookAuthorList = taskService.bookAuthorGetList(credentials, skip, pageSize);
            futureBookAuthorList
                .onSuccess(res -> {
                    BookAuthorList bookAuthorList = res;
                    if (bookAuthorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookAuthorGetList failed.",
                            Error.DATABASE
                        );
                    }
                    this.respondJsonResult(context, 200, 1L, new JsonObject().put(bookAuthorList.getLabel(), bookAuthorList.toJsonArray()), null);
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            context.fail(e);
        }
    }

    // endregion


}