package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.kuksov.compapi.model.Role;

@Schema(name = "Ответ на запрос данных пользователей")
public record UserResponse(

        @Schema(name = "Идентификатор пользователя", example = "22")
        int id,

        @Schema(name = "Имя пользователя", example = "ИвановИИ")
        String name,

        @Schema(name = "Роль пользователя", example = "ROLE_ADMIN")
        Role role
) {
}
