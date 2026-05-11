package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.GroupDTO.GroupResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Group;
import ru.altaiensb.service_desk.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repo;

    private GroupResponseDTO toResponse(Group group) {
        return new GroupResponseDTO(
                group.getIdGroup(),
                group.getName(),
                group.getName1cDoc(),
                group.getDescription(),
                group.getResponsibleUser() != null ? group.getResponsibleUser().getIdItUser() : null
        );
    }

    @Transactional(readOnly = true)
    public List<GroupResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponseDTO getById(Integer id) {
        Group group = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id));
        return toResponse(group);
    }
}
