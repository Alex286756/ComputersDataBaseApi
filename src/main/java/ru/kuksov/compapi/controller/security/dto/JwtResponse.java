package ru.kuksov.compapi.controller.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import ru.kuksov.compapi.model.Role;

@Data
@Builder
@Tag(name = "Ответ с токеном доступа")
public class JwtResponse {

    @Schema(description = "Уровень доступа пользователя", example = "ROLE_USER")
    private Role role;

    @Schema(description = "Токен доступа", example = "dfvSDFVBSFGBDfgbndfBNDFnDGHND")
    private String accessToken;

    @Schema(description = "Refresh доступа", example = "dfvSDFVBSFGBDfgbndfBNDFnDGHND")
    private String refreshToken;
}
