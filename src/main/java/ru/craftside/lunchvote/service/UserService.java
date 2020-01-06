package ru.craftside.lunchvote.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.craftside.lunchvote.model.User;
import ru.craftside.lunchvote.repository.UserRepository;

import java.util.List;

import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFound;
import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created at 06.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(userRepository.existsById(id), id);
        userRepository.deleteById(id);
    }

    @Transactional
    public User update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.existsById(id), id);
        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.findByEmail(email), "email=" + email);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        userRepository.save(user);
    }
}
