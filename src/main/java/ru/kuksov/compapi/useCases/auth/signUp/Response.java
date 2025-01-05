package ru.kuksov.compapi.useCases.auth.signUp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    private String accessToken;

    private String refreshToken;
}
