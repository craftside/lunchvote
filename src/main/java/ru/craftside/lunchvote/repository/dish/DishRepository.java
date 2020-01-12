package ru.craftside.lunchvote.repository.dish;

import ru.craftside.lunchvote.model.Dish;

import java.util.List;
import java.util.Optional;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
public interface DishRepository {

    Dish save(Dish dish, int restaurantId, int menuId);

    List<Dish> findAll();

    boolean deleteDishById(int id, int restaurantId, int menuId);

    Optional<Dish> findDishByIdAndRestaurantIdAndMenuId(int id, int restaurantId, int menuId);

    List<Dish> findAllByRestaurantIdAndMenuId(int restaurantId, int menuId);
}
