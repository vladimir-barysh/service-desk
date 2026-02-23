package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.OrderBindingDTO;
import ru.altaiensb.service_desk.model.OrderBinding;
import ru.altaiensb.service_desk.repository.OrderBindingRepository;
import ru.altaiensb.service_desk.service.OrderBindingService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/orderbinding")
@RequiredArgsConstructor
public class OrderBindingController {
    private final OrderBindingService service;
    private final OrderBindingRepository repo;

    @GetMapping
    public List<OrderBinding> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBinding> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {

        OrderBinding binding = service.getById(id);

        Path filePath = Paths.get(binding.getPath());
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Файл не найден на диске");
        }

        String contentType;
        try {
            contentType = java.nio.file.Files.probeContentType(filePath);
        } catch (Exception e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        contentType != null ? contentType : "application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + binding.getName() + "\"")
                .body(resource);
    }

    @GetMapping("/orders/{orderId}/files")
    public List<OrderBindingDTO> getFiles(@PathVariable Integer orderId) {
        return repo.findByOrder_IdOrder(orderId)
                .stream()
                .map(b -> new OrderBindingDTO(
                        b.getIdOrderBinding(),
                        b.getName(),
                        b.getDateCreated()))
                .toList();
    }
}
