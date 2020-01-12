package ru.craftside.lunchvote.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created at 08.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    List<Menu> findAllByDate(LocalDate date);

    List<Menu> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Menu> findAllByRestaurantId(int restaurantId);

    Menu findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    List<Menu> findAllByRestaurantIdAndDateBetween(int restaurantId, LocalDate startDate, LocalDate endDate);

    Optional<Menu> findMenuByRestaurantIdAndId(int restaurantId, int id);

}
