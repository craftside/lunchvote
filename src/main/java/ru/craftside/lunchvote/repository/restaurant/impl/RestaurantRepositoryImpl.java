package ru.craftside.lunchvote.repository.restaurant.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.craftside.lunchvote.model.Restaurant;
import ru.craftside.lunchvote.repository.restaurant.CrudRestaurantRepository;
import ru.craftside.lunchvote.repository.restaurant.RestaurantRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created at 08.01.2020
 *
 * @author Pavel Tolstenkov
 */
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantRepositoryImpl(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant findByName(String name) {
        return crudRestaurantRepository.findByName(name);
    }

    @Override
    public List<Restaurant> findAll() {
        return crudRestaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(int id) {
        return crudRestaurantRepository.findById(id);
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && findById(restaurant.getId()) == null) {
            return null;
        }
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }
}
