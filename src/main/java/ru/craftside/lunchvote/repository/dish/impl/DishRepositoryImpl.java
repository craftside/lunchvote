package ru.craftside.lunchvote.repository.dish.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Dish;
import ru.craftside.lunchvote.model.Restaurant;
import ru.craftside.lunchvote.repository.dish.CrudDishRepository;
import ru.craftside.lunchvote.repository.dish.DishRepository;
import ru.craftside.lunchvote.repository.menu.CrudMenuRepository;
import ru.craftside.lunchvote.repository.restaurant.CrudRestaurantRepository;
import ru.craftside.lunchvote.util.ValidationUtil;

import java.util.List;
import java.util.Optional;

import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;
import static ru.craftside.lunchvote.util.ValidationUtil.notFoundWithId;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
public class DishRepositoryImpl implements DishRepository {

    private CrudDishRepository crudDishRepository;

    private CrudMenuRepository crudMenuRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    public DishRepositoryImpl(CrudDishRepository crudDishRepository, CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId, int menuId) {
        if (!dish.isNew() && findDishByIdAndRestaurantIdAndMenuId(dish.getId(), restaurantId, menuId) == null) {
            return null;
        }

        Restaurant restaurant = checkNotFoundWithId(crudRestaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        dish.setMenu(checkNotFoundWithId(crudMenuRepository.findMenuByRestaurantIdAndId(restaurantId, menuId).orElseThrow(
                ValidationUtil.notFoundWithId("not found menu with id={}", restaurantId)), restaurantId));
        dish.getMenu().setRestaurant(restaurant);
        return crudDishRepository.save(dish);
    }

    @Override
    public List<Dish> findAll() {
        return crudDishRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteDishById(int dishId, int restaurantId, int menuId) {
        crudMenuRepository.findMenuByRestaurantIdAndId(restaurantId, menuId).orElseThrow(
                notFoundWithId("not found the combination restaurant with id={} and menu with id={}", restaurantId, menuId));
        return crudDishRepository.delete(dishId) != 0;
    }

    @Override
    @Transactional
    public Optional<Dish> findDishByIdAndRestaurantIdAndMenuId(int id, int restaurantId, int menuId) {
        crudMenuRepository.findMenuByRestaurantIdAndId(restaurantId, menuId).orElseThrow(
                notFoundWithId("not found the combination restaurant with id={} and menu with id={}", restaurantId, menuId));
        return crudDishRepository.findById(id);
    }

}

