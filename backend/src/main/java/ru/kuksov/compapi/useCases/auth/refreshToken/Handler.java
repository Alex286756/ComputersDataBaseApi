package ru.kuksov.compapi.useCases.auth.refreshToken;

import org.springframework.stereotype.Service;

@Service("RefreshToken")
public class Handler {

    public Response handle(Command command) {
        return Response.builder().build();
    }
}
