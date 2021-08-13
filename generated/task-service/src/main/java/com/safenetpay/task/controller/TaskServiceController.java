package com.safenetpay.task.controller;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

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

public class TaskServiceController extends BaseController{
    private static Logger LOGGER = Logger.getLogger(TaskServiceController.class);

    private Vertx vertx;
    private TaskService taskService;

    static final String HASH_SAULT = "put here any string you need";
    private WebClient authClient;
    private String serviceToken;

    /**
    * Constructor of taskServiceController.
    *
    * @param vertx - default mounting vertex
    */
    public TaskServiceController(Vertx vertx) {
        super();
        LOGGER.info("init: TaskServiceController - start");
        this.vertx = vertx;

        JsonObject config = Vertx.currentContext().config();

        this.taskService = new TaskService(vertx);
        this.serviceToken = config.getJsonObject("TaskService").getString("token");

        WebClientOptions webClientOptions = new WebClientOptions()
            .setDefaultHost(config.getJsonObject("AuthService").getJsonObject("http").getString("host"))
            .setDefaultPort(config.getJsonObject("AuthService").getJsonObject("http").getInteger("port"));
        this.authClient = WebClient.create(vertx, webClientOptions);

        LOGGER.info("init: TaskServiceController - completed");
    }


    //  #region  Common handlers

    /**
    * Default TaskServiceController fail handler.
    *
    * @param context - context
    */
    public void defaultFailureHandler(RoutingContext context) {
        try {
            if (context.failure() instanceof ApplicationRuntimeException) {
                ApplicationRuntimeException ex = (ApplicationRuntimeException) context.failure();
                LOGGER.error("info: TaskServiceController default failure handler");
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
                LOGGER.error("info: TaskServiceController default failure handler");
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

    /**
    * Default TaskServiceController fail handler.
    *
    * @param context - context
    */
    public void handlerNotFound(RoutingContext context) {
        LOGGER.info("info: TaskServiceController default handler Not Found");
        HttpMethod method = context.request().method();
        this.respondJsonResult(
            context,
            404,
            10L,
            null,
            new JsonObject()
                .put("code", -1)
                .put("message",
                    String.format("unknown error: Method %s for path %s",
                    method, context.request().absoluteURI()))
        );
    }

    /**
    * Security handler.
    *
    * @param context - context
    */
    public void handleApiKeyAuth(RoutingContext context) {
        LOGGER.info("call: TaskServiceController ApiKeyAuth Handler");
        String tokenParam = context.request().headers().get("token");

        JsonObject jsonBody = new JsonObject()
            .put("jsonrpc", "2.0")
            .put("id", 1)
            .put("params", new JsonObject().put("user_id", 2).put("token", this.serviceToken));

        this.authClient
            .post("/auth/authenticate")
            .putHeader("token", tokenParam)
            .sendJsonObject(jsonBody)
            .onSuccess(response -> {
                JsonObject authResult = response.bodyAsJsonObject();
                if (authResult.getJsonObject("result") != null) {
                    context.put("auth", authResult.getJsonObject("result"));
                    context.next();
                } else {
                    throw new ApplicationRuntimeException(
                    authResult.getJsonObject("error").getString("message"), Error.AUTH);
                }
            })
            .onFailure(context::fail);
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
            LOGGER.info("call: TaskServiceController /task/book/add Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book/delete Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
                        LOGGER.error(e.getMessage());
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book/update Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));
            
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book/get Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book/get-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * BookGetAll handler.
    *
    * @param context - routing context
    */
    public void handleBookGetAll(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/book/get-all Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            Future<BookList> futureBookList = taskService.bookGetAll(credentials);
            futureBookList
                .onSuccess(res -> {
                    BookList bookList = res;
                    if (bookList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookGetAll failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * BookGetSummaryList handler.
    *
    * @param context - routing context
    */
    public void handleBookGetSummaryList(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/book/get-summary-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            String sortExpression = params.getString("sort_expression");
            String filterCondition = params.getString("filter_condition");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<BookList> futureBookList = taskService.bookGetSummaryList(credentials, sortExpression, filterCondition, skip, pageSize);
            futureBookList
                .onSuccess(res -> {
                    BookList bookList = res;
                    if (bookList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookGetSummaryList failed.",
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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/author/add Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/author/delete Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
                        LOGGER.error(e.getMessage());
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/author/update Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));
            
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/author/get Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/author/get-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * AuthorGetAll handler.
    *
    * @param context - routing context
    */
    public void handleAuthorGetAll(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/author/get-all Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            Future<AuthorList> futureAuthorList = taskService.authorGetAll(credentials);
            futureAuthorList
                .onSuccess(res -> {
                    AuthorList authorList = res;
                    if (authorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task AuthorGetAll failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * AuthorGetSummaryList handler.
    *
    * @param context - routing context
    */
    public void handleAuthorGetSummaryList(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/author/get-summary-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            String sortExpression = params.getString("sort_expression");
            String filterCondition = params.getString("filter_condition");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<AuthorList> futureAuthorList = taskService.authorGetSummaryList(credentials, sortExpression, filterCondition, skip, pageSize);
            futureAuthorList
                .onSuccess(res -> {
                    AuthorList authorList = res;
                    if (authorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task AuthorGetSummaryList failed.",
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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book-author/add Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book-author/delete Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
                        LOGGER.error(e.getMessage());
                        context.fail(e);
                    }
                })
                .onFailure(ar -> {
                    LOGGER.error(ar.getMessage());
                    context.fail(ar);
                });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book-author/update Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));
            
            JsonObject params = this.getBodyJsonObjectParam(context, "params");
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
                    LOGGER.error(e.getMessage());
                    context.fail(e);
                }
            } else {
                throw new ApplicationRuntimeException("param value error", Error.APPLICATION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book-author/get Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
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
            LOGGER.info("call: TaskServiceController /task/book-author/get-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * BookAuthorGetAll handler.
    *
    * @param context - routing context
    */
    public void handleBookAuthorGetAll(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/book-author/get-all Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            Future<BookAuthorList> futureBookAuthorList = taskService.bookAuthorGetAll(credentials);
            futureBookAuthorList
                .onSuccess(res -> {
                    BookAuthorList bookAuthorList = res;
                    if (bookAuthorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookAuthorGetAll failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }

    /**
    * BookAuthorGetSummaryList handler.
    *
    * @param context - routing context
    */
    public void handleBookAuthorGetSummaryList(RoutingContext context) {
        try {
            LOGGER.info("call: TaskServiceController /task/book-author/get-summary-list Handler");
            JsonObject auth = context.get("auth");
            UserCredentials credentials = new UserCredentials().setLoginId(auth.getLong("user_id"));

            JsonObject params = this.getBodyJsonObjectParam(context, "params");
            String sortExpression = params.getString("sort_expression");
            String filterCondition = params.getString("filter_condition");
            Long pageSize = params.getLong("page_size");
            Long skip = params.getLong("skip_count");

            Future<BookAuthorList> futureBookAuthorList = taskService.bookAuthorGetSummaryList(credentials, sortExpression, filterCondition, skip, pageSize);
            futureBookAuthorList
                .onSuccess(res -> {
                    BookAuthorList bookAuthorList = res;
                    if (bookAuthorList == null) {
                        throw new ApplicationRuntimeException(
                            "Task BookAuthorGetSummaryList failed.",
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
            LOGGER.error(e.getMessage());
            context.fail(e);
        }
    }
    // endregion


}