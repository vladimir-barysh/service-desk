package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.MailingType;
import ru.altaiensb.service_desk.repository.reference.MailingTypeRepository;

@Service
@RequiredArgsConstructor
public class MailingTypeService {

    private final MailingTypeRepository repo;

    public List<MailingType> getAll() {
        return repo.findAll();
    }

    public MailingType getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("MailingType not found with id=" + id));
    }
}
