package com.safenetpay.task.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
* The type Author
*
* @author laziz
* @since 2021-08-13
*/
public class Author extends BaseDataContract {

    public static final String AUTHOR = "author";

    public static final String AUTHOR_ID = "author_id";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(AUTHOR_ID)
    private Long authorId;

    public static final String FULL_NAME = "full_name";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(FULL_NAME)
    private String fullName;

    public static final String BIO = "bio";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(BIO)
    private String bio;

    /**
    * authorId getter.
    *
    * @return the authorId
    */
    public Long getAuthorId() {
        return this.authorId;
    }

    /**
    * * authorId setter.
    *
    * @param authorId the authorId to set
    * @return this
    */
    public Author setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    /**
    * fullName getter.
    *
    * @return the fullName
    */
    public String getFullName() {
        return this.fullName;
    }

    /**
    * fullName setter.
    *
    * @param fullName the fullName to set
    * @return this
    */
    public Author setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    /**
    * bio getter.
    *
    * @return the bio
    */
    public String getBio() {
        return this.bio;
    }

    /**
    * bio setter.
    *
    * @param bio the bio to set
    * @return this
    */
    public Author setBio(String bio) {
        this.bio = bio;
        return this;
    }


    /*
    * (non-Javadoc)
    *
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
        return Objects.hash(fullName, bio,  authorId);
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
        Author other = (Author) obj;
        return
            Objects.equals(fullName, other.fullName) &&
            Objects.equals(bio, other.bio) &&
            Objects.equals(authorId, other.authorId);
    }
}