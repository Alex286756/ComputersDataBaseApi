package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ на запрос типа оборудования")
public record TypeResponse(

        @Schema(name = "Идентификатор типа устройства", example = "2")
        int id,

        @Schema(name = "Название типа оборудования", example = "Мышь")
        String name
) {
}
