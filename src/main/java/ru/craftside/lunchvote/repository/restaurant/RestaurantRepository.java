package ru.craftside.lunchvote.repository.restaurant;

import ru.craftside.lunchvote.model.Restaurant;

import java.util.List;
import java.util.Optional;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
public interface RestaurantRepository {

    Restaurant findByName(String name);

    List<Restaurant> findAll();

    Optional<Restaurant> findById(int id);

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);
}
