package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.ApproveUser;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.ApproveUserRepository;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class ApproveUserService {
    private final ApproveUserRepository approveUserRepo;
	private final ApproveService approveService;
	private final OrderStateRepository orderStateRepo;

    // ---------------------------- Respons ----------------------------
    private ApproveUserResponseDTO toResponse(ApproveUser entity) {
        return new ApproveUserResponseDTO(
                entity.getIdApproveUser(),
                entity.getApprove() != null ? entity.getApprove().getIdApprove() : null,
                entity.getUser() != null ? entity.getUser().getIdItUser() : null,
                entity.getUser() != null ? entity.getUser().getFio1c() : null,
                entity.getUserRole() != null ? entity.getUserRole().getIdUserRole() : null,
                entity.getUserRole() != null ? entity.getUserRole().getName() : null,
                entity.getApproveUserState() != null ? entity.getApproveUserState().getIdOrderState() : null,
				entity.getFlagIgnored(),
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
    public List<ApproveUserResponseDTO> getByOrderId(Integer orderId) {
        List<ApproveUser> approveUsers = approveUserRepo.findByOrderId(orderId);
        return approveUsers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApproveUserResponseDTO getById(Integer id) {
        ApproveUser entity = approveUserRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ApproveUser", id));
        return toResponse(entity);
    }

    // ---------------------------- UPDATE ----------------------------
    @Transactional
	public ApproveUserResponseDTO updateSelf(Integer approveId, Integer newState, String resultText) {
		// TODO: взять согласованта из контекста
		ApproveUser approveUser = approveUserRepo.findByApprove_IdApproveAndUser_IdItUser(approveId, 1)
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("ApproveUser not found for approveId=%d and userId=%d", approveId, 1)));

		OrderState approveUserState = orderStateRepo.findById(newState)
			.orElseThrow(() -> new ResourceNotFoundException("OrderState", newState));

		// Обновляем данные участника
		approveUser.setApproveUserState(approveUserState);
		approveUser.setResultText(resultText);
		approveUser.setDateFact(Instant.now());
		ApproveUser saved = approveUserRepo.save(approveUser);

		// Пересчитываем общий статус согласования
    	approveService.recalculateStatus(approveId);
		
		return toResponse(saved);
	}
}