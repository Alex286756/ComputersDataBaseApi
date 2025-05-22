package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Запрос марки (фирмы-производителя)")
@Getter
@Setter
@AllArgsConstructor
public class BrandRequest {

        @Schema(name = "Название марки (фирмы-производителя)", example = "Genius")
        @NotBlank(message = "Название фирмы-производителя не может быть пустым.")
        String name;
}
