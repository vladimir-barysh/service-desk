package ru.altaiensb.service_desk.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.OrderState;

@Repository
public interface OrderStateRepository extends JpaRepository<OrderState, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */

    @Cacheable(value = "orderStates", key = "#name")
    Optional<OrderState> findByName(String name);
}
