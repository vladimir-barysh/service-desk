package ru.altaiensb.service_desk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.OrderBinding;

@Repository
public interface OrderBindingRepository extends JpaRepository<OrderBinding, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */
    List<OrderBinding> findByOrder_IdOrder(Integer orderId);
}
