package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ на запрос данных устройства")
public record DeviceResponse(

        @Schema(name = "Идентификатор устройства", example = "2s")
        String id,

        @Schema(name = "Тип устройства", example = "")
        int typeId,

        @Schema(name = "", example = "")
        int brandId,

        @Schema(name = "", example = "")
        int modelId,

        @Schema(name = "Серийный (заводской) номер устройства", example = "WCCFG50067WS")
        String serialNumber,

        @Schema(name = "Год выпуска (производства) устройства", example = "2019")
        int year,

        @Schema(name = "Номер комплекта, в состав которого входит устройство", example = "25")
        int complectId
) {
}
