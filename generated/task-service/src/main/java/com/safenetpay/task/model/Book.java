package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import java.time.LocalDate;

/**
* The type Book
*
* @author laziz
* @since 2021-08-10
*/
public class Book extends BaseDataContract {

    public static final String BOOK = "book";

    public static final String BOOK_ID = "book_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(BOOK_ID)
    private Long bookId;

    public static final String NAME = "name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(NAME)
    private String name;

    public static final String LANGUAGE = "language";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(LANGUAGE)
    private Language language;

    public static final String PUBLISHED_DATE = "published_date";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(PUBLISHED_DATE)
    private LocalDate publishedDate;

    /**
    * bookId getter.
    *
    * @return the bookId
    */
    public Long getBookId() {
        return this.bookId;
    }

    /**
    * * bookId setter.
    *
    * @param bookId the bookId to set
    * @return this
    */
    public Book setBookId(Long bookId) {
        this.bookId = bookId;
        return this;
    }

    /**
    * name getter.
    *
    * @return the name
    */
    public String getName() {
        return this.name;
    }

    /**
    * name setter.
    *
    * @param name the name to set
    * @return this
    */
    public Book setName(String name) {
        this.name = name;
        return this;
    }

    /**
    * language getter.
    *
    * @return the language
    */
    public Language getLanguage() {
        return this.language;
    }

    /**
    * language setter.
    *
    * @param language the language to set
    * @return this
    */
    public Book setLanguage(Language language) {
        this.language = language;
        return this;
    }

    /**
    * publishedDate getter.
    *
    * @return the publishedDate
    */
    public LocalDate getPublishedDate() {
        return this.publishedDate;
    }

    /**
    * publishedDate setter.
    *
    * @param publishedDate the publishedDate to set
    * @return this
    */
    public Book setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(name, language, publishedDate,  bookId);
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
        Book other = (Book) obj;
        return
            Objects.equals(name, other.name) &&
            Objects.equals(language, other.language) &&
            Objects.equals(publishedDate, other.publishedDate) &&
            Objects.equals(bookId, other.bookId);
    }
}