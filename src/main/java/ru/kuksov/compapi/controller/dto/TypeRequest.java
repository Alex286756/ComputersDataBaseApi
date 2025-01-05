package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Запрос типа оборудования")
public record TypeRequest(

        @Schema(name = "Название типа оборудования", example = "Мышь")
        @NotBlank(message = "Название типа оборудования не может быть пустым.")
        String name
) {
}
