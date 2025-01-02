package ru.kuksov.compapi.controller.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.security.dto.JwtResponse;
import ru.kuksov.compapi.controller.security.dto.RefreshTokenRequest;
import ru.kuksov.compapi.controller.security.dto.SignInRequest;
import ru.kuksov.compapi.controller.security.dto.SignUpRequest;

@RestController
@RequestMapping("/compapi/v1/login")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Аутентификация")
public class AuthController {

    private final ru.kuksov.compapi.useCases.auth.signUp.Handler signUpHandler;
    private final ru.kuksov.compapi.useCases.auth.signIn.Handler signInHandler;
    private final ru.kuksov.compapi.useCases.auth.refreshToken.Handler refreshTokenHandler;

    @Operation(description = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        var command = ru.kuksov.compapi.useCases.auth.signUp.Command.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .role(signUpRequest.getRole())
                .build();

        ru.kuksov.compapi.useCases.auth.signUp.Response jwtPair = signUpHandler.handle(command);

        return JwtResponse.builder()
                .accessToken(jwtPair.getAccessToken())
                .refreshToken(jwtPair.getRefreshToken())
                .build();
    }

    @Operation(description = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        var command = ru.kuksov.compapi.useCases.auth.signIn.Command.builder()
                .username(signInRequest.getUsername())
                .password(signInRequest.getPassword())
                .build();

        ru.kuksov.compapi.useCases.auth.signIn.Response jwt = signInHandler.handle(command);

        return JwtResponse.builder()
                .role(jwt.getRole())
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .build();
    }

    @Operation(description = "Обновление токена пользователя")
    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        var command = ru.kuksov.compapi.useCases.auth.refreshToken.Command.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .build();

        ru.kuksov.compapi.useCases.auth.refreshToken.Response jwtPair = refreshTokenHandler.handle(command);

        return JwtResponse.builder()
                .accessToken(jwtPair.getAccessToken())
                .refreshToken(jwtPair.getRefreshToken())
                .build();
    }

}
