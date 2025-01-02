package ru.kuksov.compapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Запрос данных пользователей для проверки")
public record AuthRequest(

        @Schema(name = "Имя пользователя", example = "ИвановИИ")
        @NotBlank(message = "Имя пользователя не может быть пустым.")
        String username,

        @Schema(name = "Пароль", example = "password")
        @NotBlank(message = "Пароль не может быть пустым.")
        String password
) {
}
