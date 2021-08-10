package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type BookAuthor
*
* @author laziz
* @since 2021-08-10
*/
public class BookAuthor extends BaseDataContract {

    public static final String BOOK_AUTHOR = "book_author";

    public static final String BOOK_AUTHOR_ID = "book_author_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(BOOK_AUTHOR_ID)
    private Long bookAuthorId;

    public static final String BOOK_ID = "book_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(BOOK_ID)
    private Long bookId;

    public static final String AUTHOR_ID = "author_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(AUTHOR_ID)
    private Long authorId;

    /**
    * bookAuthorId getter.
    *
    * @return the bookAuthorId
    */
    public Long getBookAuthorId() {
        return this.bookAuthorId;
    }

    /**
    * * bookAuthorId setter.
    *
    * @param bookAuthorId the bookAuthorId to set
    * @return this
    */
    public BookAuthor setBookAuthorId(Long bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
        return this;
    }

    /**
    * bookId getter.
    *
    * @return the bookId
    */
    public Long getBookId() {
        return this.bookId;
    }

    /**
    * bookId setter.
    *
    * @param bookId the bookId to set
    * @return this
    */
    public BookAuthor setBookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    /**
    * authorId getter.
    *
    * @return the authorId
    */
    public Long getAuthorId() {
        return this.authorId;
    }

    /**
    * authorId setter.
    *
    * @param authorId the authorId to set
    * @return this
    */
    public BookAuthor setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(bookId, authorId,  bookAuthorId);
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
        BookAuthor other = (BookAuthor) obj;
        return
            Objects.equals(bookId, other.bookId) &&
            Objects.equals(authorId, other.authorId) &&
            Objects.equals(bookAuthorId, other.bookAuthorId);
    }
}