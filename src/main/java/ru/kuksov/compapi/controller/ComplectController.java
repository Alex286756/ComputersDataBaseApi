package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.dto.ComplectRequest;
import ru.kuksov.compapi.controller.dto.ComplectResponse;
import ru.kuksov.compapi.model.Complect;
import ru.kuksov.compapi.model.Device;
import ru.kuksov.compapi.service.ComplectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compapi/v1/complects")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Комплекты устройств")
public class ComplectController {

    private final ComplectService complectService;

    @PostConstruct
    private void init() {
        if (this.complectService.findComplectById(0).isEmpty()) {
            this.complectService.addComplect("Склад", List.of());
        }
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех комплектов")
    @GetMapping
    public Iterable<ComplectResponse> getAllComplects() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<Complect> complects = this.complectService.getAllComplects();
        log.info(marker, "All complects successfully found");
        return complects.stream().map(complect -> new ComplectResponse(
                complect.getId(),
                complect.getName(),
                complect.getDevices().stream().map(Device::getId).toList()
        )).toList();
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение комплекта по id")
    @GetMapping("/{id:\\d+}")
    public ComplectResponse getComplectById(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get complect id = " + id);
        Optional<Complect> complects = this.complectService.findComplectById(id);
        log.info(marker, "Search complect id {} finished", id);
        return complects.map(value -> new ComplectResponse(
                value.getId(),
                value.getName(),
                value.getDevices().stream().map(Device::getId).toList()))
                .orElseGet(() -> new ComplectResponse(-1, "Unknown", List.of()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление нового комплекта")
    @PostMapping
    public ComplectResponse addComplect(@RequestBody @Validated ComplectRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "add");
        Complect newComplect = this.complectService.addComplect(request.name(), request.devicesId());
        log.info(marker, "Complect %s successfully adding".formatted(newComplect.getId()));
        return new ComplectResponse(
                newComplect.getId(),
                newComplect.getName(),
                newComplect.getDevices() != null ?
                        newComplect.getDevices().stream().map(Device::getId).toList() :
                        List.of()
        );
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование имеющегося комплекта")
    @PatchMapping("/{id}")
    public void editComplect(@PathVariable int id,
                          @RequestBody @Validated ComplectRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        this.complectService.updateComplect(id, request.name());
        log.info(marker, "Complect %s successfully edit".formatted(request.name()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление комплекта")
    @DeleteMapping("/{id}")
    public void deleteComplect(@PathVariable int id) {
        if (id > 0) {
            LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
            this.complectService.deleteComplect(id);
            log.info(marker, "Complect %d successfully delete".formatted(id));
        }
    }
}
