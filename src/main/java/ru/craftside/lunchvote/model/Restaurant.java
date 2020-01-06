package ru.craftside.lunchvote.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name=" + name +
                '}';
    }
}
