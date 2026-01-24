package ru.altaiensb.service_desk.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.core.Approve;

@Repository
public interface ApproveRepository extends JpaRepository<Approve, Integer>{
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
