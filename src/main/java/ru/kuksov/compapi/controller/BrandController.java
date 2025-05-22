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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.dto.BrandRequest;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.service.BrandService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/compapi/v2/brands")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Бренды (производители оборудования)")
public class BrandController {

    private final BrandService brandService;

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех брендов")
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get all");
        List<Brand> brands = this.brandService.findAllBrands();
        log.info(marker, "All brands successfully found");

        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(brands);
    }

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск бренда по id")
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Brand> getBrandById(@PathVariable int id) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get brand id = " + id);
        Brand brand = this.brandService.findBrandById(id);
        log.info(marker, "Search brand id {} finished", id);

        if (brand == null) {
            log.info(marker, "Brand with id {} absent", id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(brand);
    }

//    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск бренда по наименованию")
    @GetMapping("/name/{name}")
    public ResponseEntity<Brand> getBrandByName(@PathVariable String name) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "Get brand " + name);
        Brand brand = this.brandService.findBrandByName(name);
        log.info(marker, "Search brand {} finished", name);

        if (brand == null) {
            log.info(marker, "Brand with name {} absent", name);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        return ResponseEntity
                .ok()
                .contentType(APPLICATION_JSON)
                .body(brand);
    }

//    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление нового бренда")
    @PostMapping
    public ResponseEntity<Brand> addNewBrand(@RequestBody @Validated BrandRequest request) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "add");
        if (request.getName().isEmpty()) {
            log.info(marker, "Brand with empty name don't to be adding");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        Brand newBrand = this.brandService.addBrand(request.getName());
        log.info(marker, "Brand %s successfully adding".formatted(newBrand.getName()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(APPLICATION_JSON)
                .body(newBrand);
    }

//    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование бренда")
    @PatchMapping("/{id}")
    public ResponseEntity<Brand> editBrand(@PathVariable int id,
                                           @Valid @RequestBody BrandRequest request) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "edit");
        if (request.getName().isEmpty()) {
            log.info(marker, "Brand cann't have empty name");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        Brand brand = this.brandService.updateBrand(id, request.getName());
        if (brand == null) {
            log.info(marker, "Brand %s not found, cann't edit".formatted(request.getName()));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(APPLICATION_JSON)
                    .build();
        }
        log.info(marker, "Brand %s successfully edit".formatted(request.getName()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(APPLICATION_JSON)
                .body(brand);
    }

    //    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление всех брендов")
    @DeleteMapping
    public ResponseEntity<String> deleteAllBrands() {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "deleteAll");
        if (this.brandService.deleteAllBrands()) {
            log.info(marker, "All brands successfully delete");
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body("Удалены все бренды");
        }
        log.info(marker, "I have some problems with deleting all brands");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body("Ошибка при удалении всех брендов");
    }

//    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление бренда")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrandById(@PathVariable int id) {
        var marker = StructuredMetadataMarker.of("ComputersDB", () -> "delete");
        if (this.brandService.deleteBrand(id)) {
            log.info(marker, "Brand %d successfully delete".formatted(id));
            return ResponseEntity
                    .ok()
                    .contentType(APPLICATION_JSON)
                    .body("Бренд %d удален из БД".formatted(id));
        }
        log.info(marker, "Brand %d can't delete".formatted(id));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body("Проблемы при удалении бренда № %d".formatted(id));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

//        return ResponseEntity.badRequest().body(errors);

//        log.info(marker, "Brand cann't have empty name");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(errors);
//                .build();

    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
//        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .toList();
//
////        return ResponseEntity.badRequest().body(errors);
//
////        log.info(marker, "Brand cann't have empty name");
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .contentType(APPLICATION_JSON)
//                .body(errors);
////                .build();
//
//    }

}
