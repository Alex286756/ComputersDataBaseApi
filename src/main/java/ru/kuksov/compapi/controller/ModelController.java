package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.StructuredMetadataMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.dto.ModelRequest;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.service.ModelService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/compapi/v2/models")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Модели устройств")
public class ModelController {

    private final ModelService modelService;

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех моделей устройств")
    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get all");
        List<Model> models = this.modelService.findAllModels();
        log.info(marker, "All models successfully found");

        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(models);
    }

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск модели устройства по id")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Model> getModelById(@PathVariable int id) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get model id = " + id);
        Optional<Model> model = this.modelService.findModelById(id);
        log.info(marker, "Search model id {} finished", id);

        if (model.isPresent())
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body(model.get());

        log.info(marker, "Model with id {} absent", id);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .build();
    }

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск модель устройства по наименованию")
    @GetMapping("/name/{name}")
    public ResponseEntity<Model> getModelByName(@PathVariable String name) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get model " + name);
        Optional<Model> model = this.modelService.findModelByName(name);
        log.info(marker, "Search model {} finished", name);

        if (model.isPresent())
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body(model.get());

        log.info(marker, "Model with name {} absent", name);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(APPLICATION_JSON)
                .build();
    }

//    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление новой модели")
    @PostMapping
    public ResponseEntity<Model> addNewModel(@RequestBody @Validated ModelRequest request) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "add");
        if (request.name().isEmpty()) {
            log.info(marker, "Model with empty name don't to be adding");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        Model newModel = this.modelService.addModel(request.name());
        log.info(marker, "Model %s successfully adding".formatted(newModel.getName()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(APPLICATION_JSON)
                .body(newModel);
    }

//    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование модели")
    @PatchMapping("/{id}")
    public ResponseEntity<Model> editModel(@PathVariable int id,
                                           @RequestBody @Valid ModelRequest request) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "edit");
        if (request.name().isEmpty()) {
            log.info(marker, "Model cann't have empty name");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        Model model = this.modelService.updateModel(id, request.name());
        if (model == null) {
            log.info(marker, "Model %s not found, cann't edit".formatted(request.name()));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        log.info(marker, "Model %s successfully edit".formatted(request.name()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(APPLICATION_JSON)
                .body(model);
    }

    //    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление всех моделей")
    @DeleteMapping
    public ResponseEntity<String> deleteAllModels() {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "deleteAll");
        if (this.modelService.deleteAllModels()) {
            log.info(marker, "All models successfully delete");
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body("Удалены все модели");
        }
        log.info(marker, "I have some problems with deleting all models");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body("Ошибка при удалении всех моделей");
    }

    //    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление модели устройств")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModelById(@PathVariable int id) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "delete");
        if (this.modelService.deleteModel(id)) {
            log.info(marker, "Model %d successfully delete".formatted(id));
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body("Модель %d удалена из БД".formatted(id));
        }
        log.info(marker, "Model %d can't delete".formatted(id));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body("Проблемы при удалении модели № %d".formatted(id));
    }
}
