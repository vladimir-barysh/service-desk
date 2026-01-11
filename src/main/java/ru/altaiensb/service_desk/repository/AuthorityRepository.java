package ru.altaiensb.service_desk.repository;

import ru.altaiensb.service_desk.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
    //Сюда писать дополнительные методы 
    //Готовые методы:
    //findAll(), findById(), save(), deleteById(), count() и т.д.
}
