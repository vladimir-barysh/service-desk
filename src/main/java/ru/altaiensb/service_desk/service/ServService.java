package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import ru.altaiensb.service_desk.dto.ServDTO.ServResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Serv;
import ru.altaiensb.service_desk.repository.ServRepository;

@Service
@RequiredArgsConstructor
public class ServService {
    private final ServRepository repo;

    // ---------------------------- Respons ----------------------------
    private ServResponseDTO toResponse(Serv serv) {
        return new ServResponseDTO(
            serv.getIdService(),
            serv.getFullname(),
            serv.getSname(),
            serv.getDescription(),
            serv.getDeveloper(),
            serv.getDateS(),
            serv.getDateF(),
            serv.getPriznakIs(),
            serv.getIsNeedApproval(),
            serv.getIsService(),
            serv.getBusinessCritical(),
            serv.getBasisS(),
            serv.getCatalogItem() != null ? serv.getCatalogItem().getIdCatitem() : null,
            serv.getCatalogItem() != null ? serv.getCatalogItem().getName() : null,
            serv.getServiceType() != null ? serv.getServiceType().getIdServiceType() : null,
            serv.getServiceType() != null ? serv.getServiceType().getFullname() : null,
            serv.getServiceState() != null ? serv.getServiceState().getIdServiceState() : null,
            serv.getServiceState() != null ? serv.getServiceState().getName() : null,
            serv.getExpType() != null ? serv.getExpType().getIdExpType() : null,
            serv.getServiceParent() != null ? serv.getServiceParent().getIdService() : null
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<ServResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServResponseDTO getById(Integer id) {
        Serv serv = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serv", id));
        return toResponse(serv);
    }
}