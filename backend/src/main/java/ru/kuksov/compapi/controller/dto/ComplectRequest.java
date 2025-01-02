package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Запрос данных комплекта")
public record ComplectRequest(

        @Schema(name = "Описание комплекта", example = "У директора")
        String name,

        @Schema(name = "Список идентификаторов устройств, находящихся в комплекте", example = "['2s', '5p']")
        List<String> devicesId
) {
}
