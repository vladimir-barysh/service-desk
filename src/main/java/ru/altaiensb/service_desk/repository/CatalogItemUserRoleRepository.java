package ru.altaiensb.service_desk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import ru.altaiensb.service_desk.model.CatalogItemUserRole;

@Repository
public interface CatalogItemUserRoleRepository extends JpaRepository<CatalogItemUserRole, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */

    List<CatalogItemUserRole> findByService_IdService(Integer serviceId);
    Optional<CatalogItemUserRole> findByService_IdServiceAndUser_IdItUser(Integer serviceId, Integer userId);
}
