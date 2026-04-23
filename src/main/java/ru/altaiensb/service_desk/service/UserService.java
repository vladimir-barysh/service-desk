package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

import ru.altaiensb.service_desk.dto.UserDTO.UserResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.model.Group;
import ru.altaiensb.service_desk.model.Authority;
import ru.altaiensb.service_desk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Преобразование сущности в DTO
    private UserResponseDTO toResponse(User user) {
        Set<Integer> groupIds = user.getGroups().stream()
                .map(Group::getIdGroup)
                .collect(Collectors.toSet());
        Set<Integer> authorityIds = user.getAuthorities().stream()
                .map(Authority::getIdAuthority)
                .collect(Collectors.toSet());

        return new UserResponseDTO(
                user.getIdItUser(),
                user.getLoginAd(),
                user.getEmailAd(),
                user.getTelAd(),
                user.getFio1c(),
                user.getPodr() != null ? user.getPodr().getIdPodr() : null,
                user.getDolzh1c(),
                user.getTabNum1c(),
                user.getDateCreate(),
                user.getDateModern(),
                user.getIsUser(),
                user.getDatePrin(),
                user.getDateUvol(),
                user.getAgreementType(),
                user.getFizLico(),
                user.getState1c(),
                user.getInterAd(),
                user.getGrade(),
                groupIds,
                authorityIds
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toResponse(user);
    }
}