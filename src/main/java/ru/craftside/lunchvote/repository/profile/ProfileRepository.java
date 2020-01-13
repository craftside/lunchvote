package ru.craftside.lunchvote.repository.profile;

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
public interface ProfileRepository {

    Optional<Vote> getVoicesByUserAndDate(int authorizedUser, LocalDate date);

    void vote(int restaurantId, int authorizedUser);

    Vote getOne(int userId);

    Vote save(Vote vote);

    int countByRestaurantIdAndDate(Integer id, LocalDate date);

    List<RestaurantWithVoicesDto> countByRestaurantsAndDate(LocalDate date);
}
