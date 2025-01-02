package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ru.kuksov.compapi.model.Role;

@Schema(name = "Запрос данных пользователей")
public record UserRequest(

        @Schema(name = "Имя пользователя", example = "ИвановИИ")
        @NotBlank(message = "Имя пользователя не может быть пустым.")
        String name,

        @Schema(name = "Пароль", example = "password")
        @NotBlank(message = "Пароль не может быть пустым.")
        String password,

        @Schema(name = "Роль пользователя", example = "ROLE_USER")
        @NotBlank(message = "Роль пользователя должна быть определена.")
        Role role
) {
}
