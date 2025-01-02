package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.kuksov.compapi.controller.dto.TypeRequest;
import ru.kuksov.compapi.controller.dto.TypeResponse;
import ru.kuksov.compapi.model.Type;
import ru.kuksov.compapi.service.TypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compapi/v1/types")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Типы устройств")
public class TypeController {

    private final TypeService typeService;

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех типов устройств")
    @GetMapping
    public Iterable<TypeResponse> getAllTypes() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<Type> types = this.typeService.getAllTypes();
        log.info(marker, "All types successfully found");

        return types.stream().map(type -> new TypeResponse(
                type.getId(),
                type.getName()
        )).toList();
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск типа устройства по id")
    @GetMapping("/{id:\\d+}")
    public TypeResponse getTypeById(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get type id = " + id);
        Optional<Type> type = this.typeService.findTypeById(id);
        log.info(marker, "Search type id {} finished", id);

        return type.map(value -> new TypeResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new TypeResponse(-1, "Unknown"));
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск типа устройства по наименованию")
    @GetMapping("/name/{name}")
    public TypeResponse getTypeByName(@PathVariable String name) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get type " + name);
        Optional<Type> type = this.typeService.findTypeByName(name);
        log.info(marker, "Search type {} finished", name);

        return type.map(value -> new TypeResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new TypeResponse(-1, "Unknown"));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление нового типа устройств")
    @PostMapping
    public TypeResponse addType(@RequestBody @Validated TypeRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "add");
        Type newType = this.typeService.addType(request.name());
        log.info(marker, "Type %s successfully adding".formatted(newType.getName()));
        return new TypeResponse(
                newType.getId(),
                newType.getName()
        );
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование типа устройств")
    @PatchMapping("/{id}")
    public void editType(@PathVariable int id,
                          @RequestBody @Validated TypeRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        this.typeService.updateType(id, request.name());
        log.info(marker, "Type %s successfully edit".formatted(request.name()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление типа устройств")
    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
        this.typeService.deleteType(id);
        log.info(marker, "Type %d successfully delete".formatted(id));
    }
}
