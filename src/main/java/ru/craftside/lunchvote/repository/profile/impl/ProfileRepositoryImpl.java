package ru.craftside.lunchvote.repository.profile.impl;

import org.springframework.stereotype.Repository;
import ru.craftside.lunchvote.model.Vote;
import ru.craftside.lunchvote.repository.profile.CrudProfileRepository;
import ru.craftside.lunchvote.repository.profile.ProfileRepository;
import ru.craftside.lunchvote.web.dto.RestaurantWithVoicesDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private CrudProfileRepository crudProfileRepository;

    public ProfileRepositoryImpl(CrudProfileRepository crudProfileRepository) {
        this.crudProfileRepository = crudProfileRepository;
    }

    @Override
    public Optional<Vote> getVoicesByUserAndDate(int authorizedUser, LocalDate date) {
        return crudProfileRepository.getByUserAndDate(authorizedUser, date);
    }

    @Override
    public void vote(int restaurantId, int authorizedUser) {

    }

    @Override
    public Vote getOne(int userId) {
        return crudProfileRepository.getOne(userId);
    }

    @Override
    public Vote save(Vote vote) {
        return crudProfileRepository.save(vote);
    }

    @Override
    public int countByRestaurantIdAndDate(Integer restaurantId, LocalDate date) {
        return crudProfileRepository.countByRestaurantId(restaurantId, date);
    }

    @Override
    public List<RestaurantWithVoicesDto> countByRestaurantsAndDate(LocalDate date) {
        return crudProfileRepository.countByRestaurantsAndDate(date);
    }
}
