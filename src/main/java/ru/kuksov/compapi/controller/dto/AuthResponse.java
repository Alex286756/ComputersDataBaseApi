package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.kuksov.compapi.model.Role;

@Schema(name = "Ответ на проверку логина и пароля пользователя")
public record AuthResponse(

        @Schema(name = "Роль пользователя", example = "ROLE_ADMIN")
        Role role
) {
}
