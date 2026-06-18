package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.CatalogItemDTO.CatalogItemResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.CatalogItem;
import ru.altaiensb.service_desk.repository.CatalogItemRepository;

@Service
@RequiredArgsConstructor
public class CatalogItemService {
    private final CatalogItemRepository сatalogItemRepo;

    // ---------------------------- Respons ----------------------------
    private CatalogItemResponseDTO toResponse(CatalogItem item) {
        return new CatalogItemResponseDTO(
            item.getIdCatitem(),
            item.getNomer(),
            item.getName(),
            item.getDescription(),
            item.getInfo(),
            item.getExpBasis(),
            item.getExpDate(),
            item.getExpOutBasis(),
            item.getExpOutDate(),
            item.getCatitemParent() != null ? item.getCatitemParent().getIdCatitem() : null,
            item.getExpType() != null ? item.getExpType().getIdExpType() : null,
            item.getEffect() != null ? item.getEffect().getIdEffect() : null,
            item.getScale() != null ? item.getScale().getIdScale() : null,
            item.getCatitemState() != null ? item.getCatitemState().getIdCatitemState() : null
        );
    }   

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<CatalogItemResponseDTO> getAll() {
        return сatalogItemRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CatalogItemResponseDTO getById(Integer id) {
        CatalogItem item = сatalogItemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CatalogItem", id));
        return toResponse(item);
    }
}
