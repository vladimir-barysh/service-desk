package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.model.ApproveUser;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.model.UserRole;
import ru.altaiensb.service_desk.repository.ApproveRepository;
import ru.altaiensb.service_desk.repository.ApproveUserRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
import ru.altaiensb.service_desk.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class ApproveUserService {

    private final ApproveUserRepository approveUserRepo;
    private final ApproveRepository approveRepo;
    private final UserRepository userRepo;
    private final UserRoleRepository userRoleRepo;

    // ---------------------------- Respons ----------------------------
    private ApproveUserResponseDTO toResponse(ApproveUser entity) {
        return new ApproveUserResponseDTO(
                entity.getIdApproveUser(),
                entity.getApprove() != null ? entity.getApprove().getIdApprove() : null,
                entity.getUser() != null ? entity.getUser().getIdItUser() : null,
                entity.getUserRole() != null ? entity.getUserRole().getIdUserRole() : null,
                entity.getState(),
                entity.getDatePlan(),
                entity.getResultText(),
                entity.getApproveUserParent() != null ? entity.getApproveUserParent().getIdApproveUser() : null,
                entity.getDateFact(),
                entity.getTaskText()
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<ApproveUserResponseDTO> getByApproveId(Integer approveId) {
        List<ApproveUser> approveUser = approveUserRepo.findByApprove_IdApprove(approveId);
        return approveUser.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApproveUserResponseDTO getById(Integer id) {
        ApproveUser entity = approveUserRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ApproveUser", id));
        return toResponse(entity);
    }

    // ---------------------------- CREATE ----------------------------
    @Transactional
    public ApproveUserResponseDTO create(ApproveUserCreateRequestDTO dto) {
        // Обязательные связанные сущности
        Approve approve = approveRepo.findById(dto.idApprove())
                .orElseThrow(() -> new ResourceNotFoundException("Approve", dto.idApprove()));

        User user = userRepo.findById(dto.idUser())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.idUser()));

        // НЕ обязательные связанные сущности
        UserRole userRole = null;
        if (dto.idUserRole() != null) {
            userRole = userRoleRepo.findById(dto.idUserRole())
                    .orElseThrow(() -> new ResourceNotFoundException("UserRole", dto.idUserRole()));
        }

        ApproveUser parent = null;
        if (dto.idApproveUserParent() != null) {
            parent = approveUserRepo.findById(dto.idApproveUserParent())
                    .orElseThrow(() -> new ResourceNotFoundException("ApproveUser parent", dto.idApproveUserParent()));
        }

        // Определяем state, если не передан, то 0
        Short state = (dto.state() != null) ? dto.state() : 0;

        // Постройка сущности
        ApproveUser entity = ApproveUser.builder()
                .approve(approve)
                .user(user)
                .userRole(userRole)
                .state(state)
                .approveUserParent(parent)
                .datePlan(dto.datePlan())
                .taskText(dto.taskText())
                .build();

        // Сохранение
        ApproveUser saved = approveUserRepo.save(entity);
        return toResponse(saved);
    }
}