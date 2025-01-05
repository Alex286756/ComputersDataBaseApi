package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Запрос модели оборудования")
public record ModelRequest(

        @Schema(name = "Название модели оборудования", example = "NetScroll+")
        @NotBlank(message = "Название модели оборудования не может быть пустым.")
        String name
) {
}
