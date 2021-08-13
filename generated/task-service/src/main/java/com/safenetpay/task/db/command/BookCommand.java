package com.safenetpay.task.db.command;

import com.safenetpay.task.model.Book;
import com.safenetpay.task.model.list.BookList;
import com.safenetpay.task.model.Language;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import org.apache.log4j.Logger;

public class BookCommand {
    private static Logger LOGGER = Logger.getLogger(BookCommand.class);
    private PgPool client;

    /**
    * Instantiates a new BookCommand.
    *
    * @param client the client
    */
    public BookCommand(PgPool client) {
        super();
        LOGGER.info("init: Creating BookCommand - start");
        this.client = client;
        LOGGER.info("init: Creating BookCommand - completed");
    }

    /**
    * Add book command future.
    *
    * @param loginId the login id
    * @param book the book
    * @return the future
    */
    public Future<Long> bookAddCommand(Long loginId, Book book) {
        LOGGER.info("info: bookAddCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_add($1, $2, $3, $4) \"book_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    book.getName(),
                    book.getLanguage(),
                    book.getPublishedDate()
                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookAddCommand result - ok");
                  promise.complete(row.getLong("book_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookAddCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookAddCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Update book command future.
    *
    * @param loginId the login id
    * @param book the book
    * @return the future
    */
    public Future<Long> bookUpdateCommand(Long loginId, Book book) {
        LOGGER.info("info: bookUpdateCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_update($1, $2, $3, $4, $5) \"book_id\";")
            .execute(
                Tuple.of(
                    loginId,
                    book.getBookId(),
                    book.getName(),
                    book.getLanguage(),
                    book.getPublishedDate()
                )
            )
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookUpdateCommand result - ok");
                  promise.complete(row.getLong("book_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookUpdateCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookUpdateCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Delete book command future.
    *
    * @param loginId the login id
    * @param bookId the book id
    * @return the future
    */
    public Future<Long> bookDeleteCommand(Long loginId, Long bookId) {
        LOGGER.info("info: bookDeleteCommand - start");
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_delete($1, $2) \"book_id\";")
            .execute(Tuple.of(loginId, bookId))
            .onSuccess(res -> {
                System.out.println("Got " + res.size() + " rows ");
                for (Row row : res) {
                  LOGGER.info("info: handle query bookDeleteCommand result - ok");
                  promise.complete(row.getLong("book_id"));
                }

                if (promise.tryComplete()) {
                  LOGGER.info("info: handle query bookDeleteCommand result - no result");
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookDeleteCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * Get book command future.
    *
    * @param loginId the login id
    * @param bookId the book id
    * @return the book command
    */
    public Future<Book> bookGetCommand(Long loginId, Long bookId) {
        LOGGER.info("info: bookGetCommand - start");
        Promise<Book> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get($1, $2);")
            .execute(Tuple.of(loginId, bookId))
            .onSuccess(res -> {
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Book book = createBook(row);
                        LOGGER.info("info: handle query bookGetCommand result - ok");
                        promise.complete(book);
                    }

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookGetCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookGetCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookGetCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetList book command future.
    *
    * @param loginId the login id
    * @param skip the skip
    * @param pageSize the page size
    * @return the book command
    */
    public Future<BookList> bookGetListCommand(Long loginId, Long skip, Long pageSize) {
        LOGGER.info("info: bookGetListCommand - start");
        Promise<BookList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                BookList bookList = new BookList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Book book = createBook(row);
                        LOGGER.info("info: handle query bookGetListCommand result - ok");
                        bookList.add(book);
                    }

                    promise.complete(bookList);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookGetListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookGetListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookGetListCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetAll book command future.
    *
    * @param loginId the login id
    * @return the book command
    */
    public Future<BookList> bookGetAllCommand(Long loginId) {
        LOGGER.info("info: bookGetAllCommand - start");
        Promise<BookList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get_all($1);")
            .execute(Tuple.of(loginId))
            .onSuccess(res -> {
                BookList bookList = new BookList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Book book = createBook(row);
                        LOGGER.info("info: handle query bookGetAllCommand result - ok");
                        bookList.add(book);
                    }

                    promise.complete(bookList);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookGetAllCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookGetAllCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookGetListCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    /**
    * GetSummaryList book command future.
    *
    * @param loginId the login id
    * @param sortExpression the sort expression
    * @param filterCondition the filter condition
    * @param skip the skip
    * @param pageSize the page size
    * @return the book command
    */
    public Future<BookList> bookGetSummaryListCommand(Long loginId, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        LOGGER.info("info: bookGetSummaryListCommand - start");
        Promise<BookList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get_summary_list($1, $2, $3, $4, $5);")
            .execute(Tuple.of(loginId, sortExpression, filterCondition, skip, pageSize))
            .onSuccess(res -> {
                BookList bookList = new BookList();
                try{
                    System.out.println("Got " + res.size() + " rows ");
                    for (Row row : res) {
                        Book book = createBook(row);
                        LOGGER.info("info: handle query bookGetSummaryListCommand result - ok");
                        bookList.add(book);
                    }

                    promise.complete(bookList);

                    if (promise.tryComplete()) {
                      LOGGER.info("info: handle query bookGetSummaryListCommand result - no result");
                    }
                } catch(Exception e){
                    LOGGER.info("info: handle query bookGetSummaryListCommand result - no result");
                    promise.fail(e);
                }
            })
            .onFailure(ar -> {
                LOGGER.error("info: handle query bookGetSummaryListCommand result - failed");
                promise.fail(ar);
            });
        return promise.future();
    }

    private Book createBook(Row row){
        return new Book()
            .setBookId(row.getLong("book_id"))
            .setName(row.getString("name"))
            .setLanguage(row.getString("language") == null
                ? null
                : Language.valueOf(row.getString("language")))
            .setPublishedDate(row.getLocalDate("published_date"));
    }
}