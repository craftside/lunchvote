package ru.craftside.lunchvote.repository.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Vote;

import java.time.LocalDate;
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
}
