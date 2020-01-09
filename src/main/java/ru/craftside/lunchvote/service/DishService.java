package ru.craftside.lunchvote.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.craftside.lunchvote.model.Dish;
import ru.craftside.lunchvote.repository.dish.DishRepository;
import ru.craftside.lunchvote.util.ValidationUtil;

import java.util.List;

import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Service
public class DishService {
    private DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getDishByIdAndRestaurantIdAndMenuId(int id, int restaurantId, int menuId) {
        return checkNotFoundWithId(dishRepository.findDishByIdAndRestaurantIdAndMenuId(id, restaurantId, menuId).orElse(null), id);
    }

    public Dish create(Dish dish, int restaurantId, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish, restaurantId, menuId);
    }

    public Dish update(Dish dish, int id, int restaurantId, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        ValidationUtil.assureIdConsistent(dish, id);
        return dishRepository.save(dish, restaurantId, menuId);
    }

    public void deleteById(int id, int restaurantId, int menuId) {
        checkNotFoundWithId(dishRepository.deleteDishById(id, restaurantId, menuId), id);
    }
}
