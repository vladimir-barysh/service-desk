package ru.altaiensb.service_desk.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.core.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    //Сюда писать дополнительные методы 
    //Готовые методы:
    //findAll(), findById(), save(), deleteById(), count() и т.д.
}
