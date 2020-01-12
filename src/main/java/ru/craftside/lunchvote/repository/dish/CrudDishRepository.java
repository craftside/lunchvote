package ru.craftside.lunchvote.repository.dish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Dish;

import java.util.List;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query(nativeQuery = true,
            value = "SELECT d.ID as id, d.NAME as name, d.PRICE as price, d.MENU_ID as menu FROM DISHES d INNER JOIN (SELECT m.id as menu_id, m.date as menu_date, m.restaurant_id as menu_restaurantId FROM restaurants r INNER JOIN MENU M on r.ID = M.RESTAURANT_ID WHERE r.ID=:restaurantId) as rm on d.MENU_ID=rm.menu_id WHERE rm.menu_id=:menuId")
    List<Dish> findAllByRestaurantIdAndMenuId(@Param("restaurantId") int restaurantId, @Param("menuId") int menuId);

}