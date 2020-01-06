package ru.craftside.lunchvote.model;

import org.springframework.util.Assert;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
