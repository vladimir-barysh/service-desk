package ru.altaiensb.service_desk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import ru.altaiensb.service_desk.model.ApproveUser;

@Repository
public interface ApproveUserRepository extends JpaRepository<ApproveUser, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */

    List<ApproveUser> findByApprove_IdApprove(Integer idApprove);
    
    // Возвращает всех участников согласований, связанных с указанным заказом
    @Query("SELECT au FROM ApproveUser au JOIN au.approve a WHERE a.order.idOrder = :orderId")
    List<ApproveUser> findByOrderId(@Param("orderId") Integer orderId);
}
