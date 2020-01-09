package ru.craftside.lunchvote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.craftside.lunchvote.model.Menu;
import ru.craftside.lunchvote.service.MenuService;
import ru.craftside.lunchvote.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/restaurants";

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping(value = "/{restaurantId}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@PathVariable("restaurantId") int restaurantId,
                                                   @Valid @RequestBody Menu menu) {
        log.info("create menu {} for restaurant with id={}", menu, restaurantId);
        ValidationUtil.checkNew(menu);

        Menu created = menuService.create(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/menu/{id}")
                .buildAndExpand(created.getId(), restaurantId).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}/menu/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> update(@Valid @RequestBody Menu menu,
                                       @PathVariable int id,
                                       @PathVariable("restaurantId") int restaurantId) {
        log.info("update menu {} for restaurant with id={}", menu, restaurantId);
        ValidationUtil.assureIdConsistent(menu, id);

        Menu updated = menuService.update(menu, restaurantId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{restaurantId}/menu/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id,
                       @PathVariable("restaurantId") int restaurantId) {
        log.info("delete menu with id = {} for restaurant with id={}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    @GetMapping("/menu/at")
    public List<Menu> get(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu list for all restaurants on date={}", date);
        return menuService.getAllMenuByDate(date);
    }

    @GetMapping("/menu/between")
    public List<Menu> get(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("get menu list for all restaurants on dates between startDate={} and endDate={}", startDate, endDate);
        return menuService.getAllMenuByDateBetween(startDate, endDate);
    }

    @GetMapping("/{restaurantId}/menu")
    public List<Menu> get(@PathVariable("restaurantId") int restaurantId) {
        log.info("get menu list for restaurant with id={}", restaurantId);
        return menuService.getAllMenuByRestaurantId(restaurantId);
    }

    @GetMapping("/{restaurantId}/menu/at")
    public List<Menu> get(@PathVariable("restaurantId") int restaurantId,
                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menu list for restaurant with id={} on date={}", restaurantId, date);
        return menuService.getAllMenuByRestaurantIdAndDate(restaurantId, date);
    }

    @GetMapping("/{restaurantId}/menu/between")
    public List<Menu> get(@PathVariable("restaurantId") int restaurantId,
                          @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("get menu list for restaurant with id={} on dates between startDate={} and endDate={}", restaurantId, startDate, endDate);
        return menuService.getAllMenuByRestaurantIdAndDateBetween(restaurantId, startDate, endDate);
    }

    @GetMapping("/{restaurantId}/menu/{menuId}")
    public Menu get(@PathVariable("restaurantId") int restaurantId,
                    @PathVariable int menuId) {
        log.info("get menu with id={} for restaurant with id={}", menuId, restaurantId);
        return menuService.getMenuByRestaurantIdAndMenuId(restaurantId, menuId);
    }

}
