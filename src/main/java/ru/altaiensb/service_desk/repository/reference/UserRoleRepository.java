package ru.altaiensb.service_desk.repository.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.reference.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
    /*
    Автоматически реализуются следующие методы:
        save(entity)
        findById(id)
        findAll()
        deleteById(id)
        existsById(id)
        count()
    */
}
