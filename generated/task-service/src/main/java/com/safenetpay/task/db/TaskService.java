package com.safenetpay.task.db;

import com.safenetpay.task.db.command.BookCommand;
import com.safenetpay.task.db.command.AuthorCommand;
import com.safenetpay.task.db.command.BookAuthorCommand;
import com.safenetpay.task.model.Book;
import com.safenetpay.task.model.Author;
import com.safenetpay.task.model.BookAuthor;
import com.safenetpay.task.model.list.BookList;
import com.safenetpay.task.model.list.AuthorList;
import com.safenetpay.task.model.list.BookAuthorList;
import com.safenetpay.task.need.UserCredentials;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import org.apache.log4j.Logger;

public class TaskService {
    private static Logger LOGGER = Logger.getLogger(TaskService.class);

    private BookCommand bookCommand; 
    private AuthorCommand authorCommand; 
    private BookAuthorCommand bookAuthorCommand; 
    private Vertx vertx;

    /**
    * Constructor.
    *
    * @param vertx - main vertex
    * @param config - json configuration
    */
    public TaskService (Vertx vertx){
        LOGGER.info("init: Creating TaskService - start");
        this.vertx = vertx;
        JsonObject dbConfig = vertx.getOrCreateContext().config().getJsonObject("db");
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(dbConfig.getInteger("port"))
            .setHost(dbConfig.getString("host"))
            .setDatabase(dbConfig.getString("db_name"))
            .setUser(dbConfig.getString("user"))
            .setPassword(dbConfig.getString("password"));
            
        PoolOptions poolOptions = new PoolOptions().setMaxSize(dbConfig.getInteger("max_pool_size"));
        PgPool client = PgPool.pool(vertx, connectOptions, poolOptions);
        this.bookCommand = new BookCommand(client); 
        this.authorCommand = new AuthorCommand(client); 
        this.bookAuthorCommand = new BookAuthorCommand(client); 

        LOGGER.info("init: Creating TaskService - completed");
    }


    //region Book

    public Future<Long> bookAdd(UserCredentials uc, Book book) {
        return this.bookCommand
            .bookAddCommand(uc.getLoginId(), book);
    }
    
    public Future<Long> bookUpdate(UserCredentials uc, Book book) {
        return this.bookCommand
            .bookUpdateCommand(uc.getLoginId(), book);
    }

    public Future<Long> bookDelete(UserCredentials uc, Long bookId) {
        return this.bookCommand
            .bookDeleteCommand(uc.getLoginId(), bookId);
    }

    public Future<Book> bookGet(UserCredentials uc, Long bookId) {
        return this.bookCommand
            .bookGetCommand(uc.getLoginId(), bookId);
    }

    public Future<BookList> bookGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.bookCommand
            .bookGetListCommand(uc.getLoginId(), skip, pageSize);
    }

    public Future<BookList> bookGetAll(UserCredentials uc, Long skip) {
        return this.bookCommand
            .bookGetAllCommand(uc.getLoginId());
    }

    public Future<BookList> bookGetSummaryList(UserCredentials uc, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        return this.bookCommand
            .bookGetSummaryListCommand(uc.getLoginId(), sortExpression, filterCondition, skip, pageSize);
    }
    //endregion
    

    //region Author

    public Future<Long> authorAdd(UserCredentials uc, Author author) {
        return this.authorCommand
            .authorAddCommand(uc.getLoginId(), author);
    }
    
    public Future<Long> authorUpdate(UserCredentials uc, Author author) {
        return this.authorCommand
            .authorUpdateCommand(uc.getLoginId(), author);
    }

    public Future<Long> authorDelete(UserCredentials uc, Long authorId) {
        return this.authorCommand
            .authorDeleteCommand(uc.getLoginId(), authorId);
    }

    public Future<Author> authorGet(UserCredentials uc, Long authorId) {
        return this.authorCommand
            .authorGetCommand(uc.getLoginId(), authorId);
    }

    public Future<AuthorList> authorGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.authorCommand
            .authorGetListCommand(uc.getLoginId(), skip, pageSize);
    }

    public Future<AuthorList> authorGetAll(UserCredentials uc, Long skip) {
        return this.authorCommand
            .authorGetAllCommand(uc.getLoginId());
    }

    public Future<AuthorList> authorGetSummaryList(UserCredentials uc, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        return this.authorCommand
            .authorGetSummaryListCommand(uc.getLoginId(), sortExpression, filterCondition, skip, pageSize);
    }
    //endregion
    

    //region BookAuthor

    public Future<Long> bookAuthorAdd(UserCredentials uc, BookAuthor bookAuthor) {
        return this.bookAuthorCommand
            .bookAuthorAddCommand(uc.getLoginId(), bookAuthor);
    }
    
    public Future<Long> bookAuthorUpdate(UserCredentials uc, BookAuthor bookAuthor) {
        return this.bookAuthorCommand
            .bookAuthorUpdateCommand(uc.getLoginId(), bookAuthor);
    }

    public Future<Long> bookAuthorDelete(UserCredentials uc, Long bookAuthorId) {
        return this.bookAuthorCommand
            .bookAuthorDeleteCommand(uc.getLoginId(), bookAuthorId);
    }

    public Future<BookAuthor> bookAuthorGet(UserCredentials uc, Long bookAuthorId) {
        return this.bookAuthorCommand
            .bookAuthorGetCommand(uc.getLoginId(), bookAuthorId);
    }

    public Future<BookAuthorList> bookAuthorGetList(UserCredentials uc, Long skip, Long pageSize) {
        return this.bookAuthorCommand
            .bookAuthorGetListCommand(uc.getLoginId(), skip, pageSize);
    }

    public Future<BookAuthorList> bookAuthorGetAll(UserCredentials uc, Long skip) {
        return this.bookAuthorCommand
            .bookAuthorGetAllCommand(uc.getLoginId());
    }

    public Future<BookAuthorList> bookAuthorGetSummaryList(UserCredentials uc, String sortExpression, String filterCondition, Long skip, Long pageSize) {
        return this.bookAuthorCommand
            .bookAuthorGetSummaryListCommand(uc.getLoginId(), sortExpression, filterCondition, skip, pageSize);
    }
    //endregion
    
}