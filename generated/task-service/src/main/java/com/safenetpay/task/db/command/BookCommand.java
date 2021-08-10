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

    public BookCommand(PgPool client) {
        this.client = client;
    }

    public Future<Long> bookAddCommand(Long loginId, Book book) {
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
                res.forEach(row -> promise.complete(row.getLong("book_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> bookUpdateCommand(Long loginId, Book book) {
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
                res.forEach(row -> promise.complete(row.getLong("book_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Long> bookDeleteCommand(Long loginId, Long bookId) {
        Promise<Long> promise = Promise.promise();
        client
            .preparedQuery("SELECT task.book_delete($1, $2) \"book_id\";")
            .execute(Tuple.of(loginId, bookId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(row.getLong("book_id")));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<Book> bookGetCommand(Long loginId, Long bookId) {
        Promise<Book> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get($1, $2);")
            .execute(Tuple.of(loginId, bookId))
            .onSuccess(res -> {
                res.forEach(row -> promise.complete(createBook(row)));
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
        return promise.future();
    }

    public Future<BookList> bookGetListCommand(Long loginId, Long skip, Long pageSize) {
        Promise<BookList> promise = Promise.promise();
        client
            .preparedQuery("SELECT * FROM task.book_get_list($1, $2, $3);")
            .execute(Tuple.of(loginId, skip, pageSize))
            .onSuccess(res -> {
                BookList bookList = new BookList();
                res.forEach(row -> bookList.add(createBook(row)));
                promise.complete(bookList);
                if(promise.tryComplete()){
                    LOGGER.info("No data!");
                }
            })
            .onFailure(promise::fail);
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