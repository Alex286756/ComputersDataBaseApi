package ru.kuksov.compapi.useCases.auth.signIn;

import lombok.Builder;
import lombok.Data;
import ru.kuksov.compapi.model.Role;

@Data
@Builder
public class Response {

    private Role role;

    private String accessToken;

    private String refreshToken;
}
