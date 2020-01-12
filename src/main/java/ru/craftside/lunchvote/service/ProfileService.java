package ru.craftside.lunchvote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Menu;
import ru.craftside.lunchvote.model.Restaurant;
import ru.craftside.lunchvote.model.Vote;
import ru.craftside.lunchvote.repository.UserRepository;
import ru.craftside.lunchvote.repository.menu.MenuRepository;
import ru.craftside.lunchvote.repository.profile.ProfileRepository;
import ru.craftside.lunchvote.repository.restaurant.RestaurantRepository;
import ru.craftside.lunchvote.util.ValidationUtil;
import ru.craftside.lunchvote.web.dto.RestaurantWithVoicesDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Service
public class ProfileService {

    private static final LocalTime EXPIRED = LocalTime.parse("11:00");

    private ProfileRepository profileRepository;

    private RestaurantRepository restaurantRepository;

    private MenuRepository menuRepository;

    private UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    public Optional<Vote> getVoicesByUserAndDate(int authorizedUser, LocalDate date) {
        return profileRepository.getVoicesByUserAndDate(authorizedUser, date);
    }

    @Transactional
    public VoteWithStatus vote(int restaurantId, int authorizedUser) {
        ValidationUtil.checkNotFoundWithId(restaurantRepository.existById(restaurantId), restaurantId);

        LocalDate today = LocalDate.now();
        Menu menu = menuRepository.findAllByRestaurantIdAndDate(restaurantId, today);
        if ((menu == null) || !menu.getDate().equals(today)) {
            ValidationUtil.notFoundWithId("Not found today's menu!");
        }

        boolean expired = LocalTime.now().isAfter(EXPIRED);

        return expired ? saveIfAbsent(authorizedUser, menu, expired) : save(authorizedUser, menu, expired);
    }

    @Transactional
    public VoteWithStatus save(int userId, final Menu menu, final boolean expired) {
        LocalDate date = menu.getDate();
        VoteWithStatus voteWithStatus = profileRepository.getVoicesByUserAndDate(userId, date)
                .map(v -> {
                    v.setMenu(menu);
                    return new VoteWithStatus(v, false, expired);
                })
                .orElseGet(() -> new VoteWithStatus(
                        new Vote(date, userRepository.getOne(userId), menu), true, expired));

        profileRepository.save(voteWithStatus.getVote());
        return voteWithStatus;
    }

    @Transactional
    public VoteWithStatus saveIfAbsent(int userId, final Menu menu, final boolean expired) {
        LocalDate date = menu.getDate();
        return profileRepository.getVoicesByUserAndDate(userId, date)
                .map(v -> new VoteWithStatus(v, false, expired))
                .orElseGet(() -> new VoteWithStatus(profileRepository.save(new Vote(date, userRepository.getOne(userId), menu)), true, expired));
    }

    @Transactional
    public List<RestaurantWithVoicesDto> getVotes() {

        LocalDate date = LocalDate.now();
        List<RestaurantWithVoicesDto> restaurantWithVoicesDto = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant r : restaurants) {
            restaurantWithVoicesDto.add(new RestaurantWithVoicesDto(r.id(), r.getName(), date,
                    profileRepository.countByRestaurantIdAndDate(r.getId(), date)));
        }
        return restaurantWithVoicesDto;
    }

    public static class VoteWithStatus {
        private final Vote vote;
        private final boolean created;
        private final boolean expired;

        public VoteWithStatus(Vote vote, boolean updated, boolean expired) {
            this.vote = vote;
            this.created = updated;
            this.expired = expired;
        }

        public Vote getVote() {
            return vote;
        }

        public boolean isCreated() {
            return created;
        }

        public boolean isExpired() {
            return expired;
        }
    }
}
