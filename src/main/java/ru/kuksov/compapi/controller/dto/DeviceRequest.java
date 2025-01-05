package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

@Schema(name = "Запрос данных по устройству")
public record DeviceRequest(

        @Schema(name = "Идентификатор устройства", example = "56m")
        String id,

        @Schema(name = "", example = "")
        int typeId,

        @Schema(name = "", example = "")
        int brandId,

        @Schema(name = "", example = "")
        int modelId,

        @Schema(name = "Серийный (заводской) номер устройства", example = "WCCFG50067WS")
        String serialNumber,

        @Schema(name = "Год выпуска (производства) устройства", example = "2024")
        int year,

        @Schema(name = "Номер комплекта, в состав которого входит устройство (0 - лежит на складе)", example = "25")
        int complectId
) {
}
