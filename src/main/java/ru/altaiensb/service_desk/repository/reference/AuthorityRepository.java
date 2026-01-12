package ru.altaiensb.service_desk.repository.reference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.reference.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
    //Сюда писать дополнительные методы 
    //Готовые методы:
    //findAll(), findById(), save(), deleteById(), count() и т.д.
}
