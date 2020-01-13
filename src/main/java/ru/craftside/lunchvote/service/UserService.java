package ru.craftside.lunchvote.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.craftside.lunchvote.model.User;
import ru.craftside.lunchvote.repository.UserRepository;
import ru.craftside.lunchvote.web.security.AuthorizedUser;

import java.util.List;
import java.util.Optional;

import static ru.craftside.lunchvote.util.UserUtil.prepareToSave;
import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFound;
import static ru.craftside.lunchvote.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created at 06.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
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
        return prepareAndSave(user);
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
        return prepareAndSave(user);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.findByEmail(email).orElse(null), "email=" + email);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email.trim().toLowerCase());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }

        return new AuthorizedUser(user.get());
    }
}
