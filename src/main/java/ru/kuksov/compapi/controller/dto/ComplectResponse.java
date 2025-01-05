package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Ответ на запрос данных комплекта")
public record ComplectResponse(

        @Schema(name = "Идентификатор комплекта", example = "25")
        int id,

        @Schema(name = "Описание комплекта", example = "Склад")
        String name,

        @Schema(name = "Список идентификаторов устройств, находящихся в комплекте", example = "['25m', '45k']")
        List<String> devicesId
) {
}
