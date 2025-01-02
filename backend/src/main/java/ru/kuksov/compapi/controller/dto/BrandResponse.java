package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Ответ на запрос марки (фирмы-производителя)")
public record BrandResponse(

        @Schema(name = "Идентификатор марки производителя", example = "23")
        int id,

        @Schema(name = "Название марки (фирмы-производителя)", example = "Genius")
        @NotBlank(message = "Название фирмы-производителя не может быть пустым.")
        String name
) {
}
