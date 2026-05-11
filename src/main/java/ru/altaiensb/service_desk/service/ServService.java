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
    private final ServRepository ServRepo;

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
            serv.getServiceType() != null ? serv.getServiceType().getIdServiceType() : null,
            serv.getServiceState() != null ? serv.getServiceState().getIdServiceState() : null,
            serv.getExpType() != null ? serv.getExpType().getIdExpType() : null,
            serv.getServiceParent() != null ? serv.getServiceParent().getIdService() : null,
            serv.getIsNeedApproval(),
            serv.getIsService(),
            serv.getBusinessCritical(),
            serv.getBasisS()
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<ServResponseDTO> getAll() {
        return ServRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServResponseDTO getById(Integer id) {
        Serv serv = ServRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serv", id));
        return toResponse(serv);
    }
}