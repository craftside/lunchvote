package ru.craftside.lunchvote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.craftside.lunchvote.model.Menu;
import ru.craftside.lunchvote.repository.menu.MenuRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Service
public class MenuService {

    private MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return menuRepository.save(menu, restaurantId);
    }

    public Menu update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        return checkNotFoundWithId(menuRepository.save(menu, restaurantId), menu.getId());
    }

    public void delete(int id, int restaurantId) {
        // throws exception if either restarantId or menuId is invalid
        checkNotFoundWithId(menuRepository.delete(id, restaurantId), id);
    }

    public List<Menu> getAllMenuByDate(LocalDate date) {
        return menuRepository.findAllByDate(date);
    }

    public List<Menu> getAllMenuByDateBetween(LocalDate startDate, LocalDate endDate) {
        return menuRepository.findAllByDateBetween(startDate, endDate);
    }

    public List<Menu> getAllMenuByRestaurantId(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public List<Menu> getAllMenuByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return menuRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    public List<Menu> getAllMenuByRestaurantIdAndDateBetween(int restaurantId, LocalDate startDate, LocalDate endDate) {
        return menuRepository.findAllByRestaurantIdAndDateBetween(restaurantId, startDate, endDate);
    }

    public Menu getMenuByRestaurantIdAndMenuId(int restaurantId, int menuId) {
        return menuRepository.findMenuByRestaurantIdAndId(restaurantId, menuId);
    }
}
