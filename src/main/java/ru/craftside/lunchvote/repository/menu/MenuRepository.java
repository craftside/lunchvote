package ru.craftside.lunchvote.repository.menu;

import ru.craftside.lunchvote.model.Menu;

import java.time.LocalDate;
import java.util.List;

/**
 * Created at 07.01.2020
 *
 * @author Pavel Tolstenkov
 */
public interface MenuRepository {

    List<Menu> findAllByDate(LocalDate date);

    List<Menu> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Menu> findAllByRestaurantId(int restaurantId);

    Menu findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Menu> findAllByRestaurantIdAndDateBetween(int restaurantId, LocalDate startDate, LocalDate endDate);

    Menu findMenuByRestaurantIdAndId(int restaurantId, int id);

    Menu save(Menu menu, int restaurantId);

    boolean delete(int id, int restaurantId);
}
