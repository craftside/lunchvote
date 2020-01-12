package ru.craftside.lunchvote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.craftside.lunchvote.model.Dish;
import ru.craftside.lunchvote.service.DishService;
import ru.craftside.lunchvote.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menu/{menuId}/dishes";

    private final DishService dishService;

    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "rest/admin/restaurants/menu/dishes/")
    public List<Dish> getAll() {
        log.info("getAll");
        return dishService.getAll();
    }

    @GetMapping(value = REST_URL)
    public List<Dish> getDishesByRestaurantIdAndMenuId(@PathVariable("restaurantId") int restaurantId,
                                                       @PathVariable("menuId") int menuId) {
        log.info("get dish list for restaurant with id={} and menu with id={}", restaurantId, menuId);
        return dishService.getDishesByRestaurantIdAndMenuId(restaurantId, menuId);
    }

    @GetMapping(value = REST_URL + "/{id}")
    public Dish get(@PathVariable("restaurantId") int restaurantId,
                    @PathVariable("menuId") int menuId,
                    @PathVariable("id") int id) {
        log.info("get dish with id={} for restaurant with id={} and menu with id={}", id, restaurantId, menuId);
        return dishService.getDishByIdAndRestaurantIdAndMenuId(id, restaurantId, menuId);
    }

    @PostMapping(value = REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@PathVariable("restaurantId") int restaurantId,
                                                   @PathVariable("menuId") int menuId,
                                                   @Valid @RequestBody Dish dish) {
        log.info("create dish {} for restaurant with id={} and menu with id={}", dish, restaurantId, menuId);
        ValidationUtil.checkNew(dish);

        Dish created = dishService.create(dish, restaurantId, menuId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, menuId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId,
                       @PathVariable("menuId") int menuId,
                       @PathVariable int id) {
        log.info("delete dish with id={} for restaurant with id={} and menu with id={}", id, restaurantId, menuId);
        dishService.deleteById(id, restaurantId, menuId);
    }

    @PutMapping(value = REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> update(@Valid @RequestBody Dish dish,
                                       @PathVariable int id,
                                       @PathVariable("restaurantId") int restaurantId,
                                       @PathVariable("menuId") int menuId) {
        log.info("update dish ={} for restaurant with id={} and menu with id={}", dish, restaurantId, menuId);
        Dish updated = dishService.update(dish, id, restaurantId, menuId);
        return ResponseEntity.ok(updated);
    }
}
