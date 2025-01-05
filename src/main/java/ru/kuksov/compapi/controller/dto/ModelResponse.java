package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ на запрос модели оборудования")
public record ModelResponse(

        @Schema(name = "Идентификатор модели устройства", example = "43")
        int id,

        @Schema(name = "Название модели оборудования", example = "NetScroll+")
        String name
) {
}
