package ru.craftside.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.User;

import java.util.Optional;

/**
 * Created at 04.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
