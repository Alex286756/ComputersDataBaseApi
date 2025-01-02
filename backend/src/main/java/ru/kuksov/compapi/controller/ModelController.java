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
import ru.kuksov.compapi.controller.dto.ModelRequest;
import ru.kuksov.compapi.controller.dto.ModelResponse;
import ru.kuksov.compapi.model.Model;
import ru.kuksov.compapi.service.ModelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compapi/v1/models")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Модели устройств")
public class ModelController {

    private final ModelService modelService;

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех моделей устройств")
    @GetMapping
    public Iterable<ModelResponse> getAllModels() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<Model> models = this.modelService.getAllModels();
        log.info(marker, "All models successfully found");

        return models.stream().map(model -> new ModelResponse(
                model.getId(),
                model.getName()
        )).toList();
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск типа устройства по id")
    @GetMapping("/{id:\\d+}")
    public ModelResponse getModelById(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get model id = " + id);
        Optional<Model> model = this.modelService.findTypeById(id);
        log.info(marker, "Search model id {} finished", id);

        return model.map(value -> new ModelResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new ModelResponse(-1, "Unknown"));
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск типа устройства по наименованию")
    @GetMapping("/name/{name}")
    public ModelResponse getModelByName(@PathVariable String name) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get model " + name);
        Optional<Model> model = this.modelService.findTypeByName(name);
        log.info(marker, "Search model {} finished", name);

        return model.map(value -> new ModelResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new ModelResponse(-1, "Unknown"));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление новой модели")
    @PostMapping
    public ModelResponse addModel(@RequestBody @Validated ModelRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "add");
        Model newModel = this.modelService.addModel(request.name());
        log.info(marker, "Model %s successfully adding".formatted(newModel.getName()));
        return new ModelResponse(
                newModel.getId(),
                newModel.getName()
        );
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование модели")
    @PatchMapping("/{id}")
    public void editModel(@PathVariable int id,
                          @RequestBody @Validated ModelRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        this.modelService.updateModel(id, request.name());
        log.info(marker, "Model %s successfully edit".formatted(request.name()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление модели устройств")
    @DeleteMapping("/{id}")
    public void deleteModel(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
        this.modelService.deleteModel(id);
        log.info(marker, "Model %d successfully delete".formatted(id));
    }
}
