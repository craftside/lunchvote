package ru.craftside.lunchvote.model;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    Restaurant restaurant;
}
