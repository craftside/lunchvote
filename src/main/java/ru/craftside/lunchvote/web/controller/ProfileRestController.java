package ru.craftside.lunchvote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.craftside.lunchvote.model.Menu;
import ru.craftside.lunchvote.model.dto.RestaurantWithVoicesDto;
import ru.craftside.lunchvote.service.ProfileService;
import ru.craftside.lunchvote.web.security.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

/**
 * Created at 09.01.2020
 *
 * @author Pavel Tolstenkov
 */
@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/profile";

    private ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/voices")
    public List<RestaurantWithVoicesDto> getVotes() {
        log.info("get all votes by the users on today");
        return profileService.getVotes();
    }

    @GetMapping("/myvote")
    public ResponseEntity<Menu> getVoicesByUserAndDate() {
        int authUserId = SecurityUtil.authUserId();

        LocalDate date = LocalDate.now();
        log.info("voices by user: {} on date {}", authUserId, date);
        return profileService.getVoicesByUserAndDate(authUserId, date)
                .map(vote -> new ResponseEntity<>(vote.getMenu(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("restaurants/{id}/vote")
    public ResponseEntity<Menu> vote(@PathVariable("id") int restaurantId) {
        int authUserId = SecurityUtil.authUserId();
        log.info("vote for the restaurant={} by user with id={}", restaurantId, authUserId);

        ProfileService.VoteWithStatus voteWithStatus = profileService.vote(restaurantId, authUserId);
        return new ResponseEntity<>(voteWithStatus.getVote().getMenu(),
                voteWithStatus.isCreated() ? HttpStatus.CREATED : (voteWithStatus.isExpired() ? HttpStatus.CONFLICT : HttpStatus.OK));

    }

}
