package ru.altaiensb.service_desk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.OrderPriority;

@Repository
public interface OrderPriorityRepository extends JpaRepository<OrderPriority, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */
    Optional<OrderPriority> findByName(String name);
}
