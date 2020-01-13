package ru.craftside.lunchvote.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Vote;
import ru.craftside.lunchvote.web.dto.RestaurantWithVoicesDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Transactional(readOnly = true)
public interface CrudProfileRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Optional<Vote> getByUserAndDate(@Param("userId") int userId, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.menu.restaurant.id=:restaurantId AND v.date=:date")
    int countByRestaurantId(@Param("restaurantId") Integer restaurantId, @Param("date") LocalDate date);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(rvm.restaurantId) as votes, rvm.restaurantId, rvm.restaurantName, rvm.date FROM (SELECT vm.menu_restaurantId as restaurantId, r.NAME as restaurantName, vm.menu_date as date FROM RESTAURANTS r INNER JOIN (SELECT m.RESTAURANT_ID as menu_restaurantId, m.DATE as menu_date FROM VOTES v INNER JOIN MENU M on v.MENU_ID = M.ID) as vm ON r.ID = vm.menu_restaurantId WHERE vm.menu_date=:date) as rvm GROUP BY rvm.restaurantId, rvm.restaurantName, rvm.date ORDER BY COUNT(rvm.restaurantId) DESC")
    List<RestaurantWithVoicesDto> countByRestaurantsAndDate(@Param("date") LocalDate date);
}
