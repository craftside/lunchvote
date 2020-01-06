package ru.craftside.lunchvote.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_user_date_idx")})
public class Vote extends AbstractNamedEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getName(), vote.getDate(), vote.getUser(), vote.getRestaurant());
    }

    public Vote(Integer id, String name, LocalDate date, User user, Restaurant restaurant) {
        super(id, name);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", user=" + user +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
