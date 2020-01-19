package ru.craftside.lunchvote.repository.profile.impl;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.craftside.lunchvote.model.Vote;
import ru.craftside.lunchvote.model.dto.RestaurantWithVoicesDto;
import ru.craftside.lunchvote.repository.profile.CrudProfileRepository;
import ru.craftside.lunchvote.repository.profile.ProfileRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CrudProfileRepository crudProfileRepository;

    public ProfileRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, CrudProfileRepository crudProfileRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
    public List<RestaurantWithVoicesDto> getVotesByDate(LocalDate date) {

        String sql = "SELECT COUNT(rvm.restaurantId) as votes, restaurantId, restaurantName, date " +
                "FROM (SELECT vm.menu_restaurantId as restaurantId, r.NAME as restaurantName, vm.menu_date as date " +
                "FROM RESTAURANTS r INNER JOIN (SELECT m.RESTAURANT_ID as menu_restaurantId, m.DATE as menu_date " +
                "FROM VOTES v INNER JOIN MENU M on v.MENU_ID = M.ID) as vm ON r.ID = vm.menu_restaurantId " +
                "WHERE vm.menu_date=:date) as rvm GROUP BY rvm.restaurantId, rvm.restaurantName, rvm.date " +
                "ORDER BY COUNT(rvm.restaurantId) DESC";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", date);

        return namedParameterJdbcTemplate.query(sql, params, rs -> {
            List<RestaurantWithVoicesDto> restaurantWithVoicesDto = new ArrayList<>();

            while (rs.next()) {
                int restaurantId = rs.getInt("restaurantId");
                int votes = rs.getInt("votes");
                String restaurantName = rs.getString("restaurantName");
                LocalDate localDate = LocalDate.parse(rs.getString("date"));

                restaurantWithVoicesDto.add(new RestaurantWithVoicesDto(restaurantId, restaurantName, localDate, votes));

            }

            return restaurantWithVoicesDto;
        });

    }
}
