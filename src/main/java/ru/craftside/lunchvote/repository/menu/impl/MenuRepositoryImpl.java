package ru.craftside.lunchvote.repository.menu.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Menu;
import ru.craftside.lunchvote.repository.menu.CrudMenuRepository;
import ru.craftside.lunchvote.repository.menu.MenuRepository;
import ru.craftside.lunchvote.repository.restaurant.CrudRestaurantRepository;
import ru.craftside.lunchvote.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created at 08.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
@Transactional(readOnly = true)
public class MenuRepositoryImpl implements MenuRepository {

    private CrudMenuRepository crudMenuRepository;

    private CrudRestaurantRepository crudRestaurantRepository;

    public MenuRepositoryImpl(CrudMenuRepository crudMenuRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMenuRepository = crudMenuRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public List<Menu> findAllByDate(LocalDate date) {
        return crudMenuRepository.findAllByDate(date);
    }

    @Override
    public List<Menu> findAllByDateBetween(LocalDate startDate, LocalDate endDate) {
        return crudMenuRepository.findAllByDateBetween(startDate, endDate);
    }

    @Override
    public List<Menu> findAllByRestaurantId(int restaurantId) {
        return crudMenuRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public Menu findAllByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return crudMenuRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    @Override
    public List<Menu> findAllByRestaurantIdAndDateBetween(int restaurantId, LocalDate startDate, LocalDate endDate) {
        return crudMenuRepository.findAllByRestaurantIdAndDateBetween(restaurantId, startDate, endDate);
    }

    @Override
    public Menu findMenuByRestaurantIdAndId(int restaurantId, int id) {
        return crudMenuRepository.findMenuByRestaurantIdAndId(restaurantId, id).orElse(null);
    }

    @Override
    @Transactional
    public Menu save(Menu menu, int restaurantId) {
        if (!menu.isNew() && findMenuByRestaurantIdAndId(restaurantId, menu.getId()) == null) {
            return null;
        }
        menu.setRestaurant(checkNotFoundWithId(crudRestaurantRepository.findById(restaurantId).orElseThrow(
                ValidationUtil.notFoundWithId("not found restaurant with id={}", restaurantId)), restaurantId));
        return crudMenuRepository.save(menu);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudMenuRepository.delete(id, restaurantId) != 0;
    }


}
