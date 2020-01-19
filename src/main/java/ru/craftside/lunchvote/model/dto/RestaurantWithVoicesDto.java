package ru.craftside.lunchvote.model.dto;

import java.time.LocalDate;

/**
 * Created at 11.01.2020
 *
 * @author Pavel Tolstenkov
 */
public class RestaurantWithVoicesDto {

    private Integer restaurantId;

    private String restaurantName;

    private LocalDate date;

    private int votes;

    public RestaurantWithVoicesDto(Integer restaurantId, String restaurantName, LocalDate date, int votes) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.date = date;
        this.votes = votes;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "RestaurantWithVoicesDto{" +
                "restaurantId=" + restaurantId +
                ", name='" + restaurantName + '\'' +
                ", date=" + date +
                ", votes=" + votes +
                '}';
    }
}
