package ru.altaiensb.service_desk.repository.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.reference.ExpType;

@Repository
public interface ExpTypeRepository extends JpaRepository<ExpType, Integer>{
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
