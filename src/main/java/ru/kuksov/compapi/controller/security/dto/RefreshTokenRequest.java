package ru.kuksov.compapi.controller.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Обновление токена доступа")
public class RefreshTokenRequest {

    @Schema(description = "RefreshToken")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String refreshToken;
}
