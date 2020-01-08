package ru.craftside.lunchvote.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.craftside.lunchvote.model.Role;
import ru.craftside.lunchvote.model.User;
import ru.craftside.lunchvote.service.UserService;
import ru.craftside.lunchvote.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/users";

    private final UserService userService;

    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get {}", id);
        return userService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singletonList(Role.ROLE_USER));
        }

        User created = userService.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        ValidationUtil.assureIdConsistent(user, id);

        User updated = userService.update(user, id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return userService.getByEmail(email);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        userService.enable(id, enabled);
    }
}
