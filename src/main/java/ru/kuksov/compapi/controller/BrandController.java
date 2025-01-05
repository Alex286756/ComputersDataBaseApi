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
import ru.kuksov.compapi.controller.dto.BrandRequest;
import ru.kuksov.compapi.controller.dto.BrandResponse;
import ru.kuksov.compapi.model.Brand;
import ru.kuksov.compapi.service.BrandService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compapi/v1/brands")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Марки (производители оборудования)")
public class BrandController {

    private final BrandService brandService;

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Получение списка всех производителей")
    @GetMapping
    public Iterable<BrandResponse> getBrands() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<Brand> brands = this.brandService.getAllBrands();
        log.info(marker, "All brands successfully found");

        return brands.stream().map(brand -> new BrandResponse(
                brand.getId(),
                brand.getName()
        )).toList();
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск производителя по id")
    @GetMapping("/{id:\\d+}")
    public BrandResponse getBrandById(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get brand id = " + id);
        Optional<Brand> brand = this.brandService.findTypeById(id);
        log.info(marker, "Search brand id {} finished", id);

        return brand.map(value -> new BrandResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new BrandResponse(-1, "Unknown"));
    }

    @Secured({"ROLE_OPERATOR", "ROLE_USER"})
    @Operation(summary = "Поиск производителя по наименованию")
    @GetMapping("/name/{name}")
    public BrandResponse getBrandByName(@PathVariable String name) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get brand " + name);
        Optional<Brand> brand = this.brandService.findTypeByName(name);
        log.info(marker, "Search brand {} finished", name);

        return brand.map(value -> new BrandResponse(
                        value.getId(),
                        value.getName()))
                .orElseGet(() -> new BrandResponse(-1, "Unknown"));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Добавление новой марки")
    @PostMapping
    public BrandResponse addBrand(@RequestBody @Validated BrandRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "add");
        Brand newBrand = this.brandService.addBrand(request.name());
        log.info(marker, "Brand %s successfully adding".formatted(newBrand.getName()));
        return new BrandResponse(
                newBrand.getId(),
                newBrand.getName()
        );
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Редактирование марки")
    @PatchMapping("/{id}")
    public void editBrand(@PathVariable int id,
                          @RequestBody @Validated BrandRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        this.brandService.updateBrand(id, request.name());
        log.info(marker, "Brand %s successfully edit".formatted(request.name()));
    }

    @Secured("ROLE_OPERATOR")
    @Operation(summary = "Удаление марки")
    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
        this.brandService.deleteBrand(id);
        log.info(marker, "Brand %d successfully delete".formatted(id));
    }
}
