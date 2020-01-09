package ru.craftside.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "menu_date_restaurant_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
//    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Menu menu) {
        this.id = menu.id;
        this.date = menu.date;
        this.restaurant = menu.restaurant;
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "date=" + date +
                ", restaurant=" + restaurant +
                ", id=" + id +
                '}';
    }
}
