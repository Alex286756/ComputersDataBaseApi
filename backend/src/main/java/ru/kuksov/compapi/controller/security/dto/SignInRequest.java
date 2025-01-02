package ru.kuksov.compapi.controller.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на авторизацию")
public class SignInRequest {

    @Schema(description = "Имя пользователя", example = "John")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Пароль", example = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}
