package ru.kuksov.compapi.useCases.auth.signUp;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.User;
import ru.kuksov.compapi.repository.UserRepository;
import ru.kuksov.compapi.service.JwtService;

@RequiredArgsConstructor
@Service("SignUp")
public class Handler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Response handle(Command command) {
        String passwordHash = passwordEncoder.encode(command.getPassword());
        User user = User.builder()
                .username(command.getUsername())
                .password(passwordHash)
                .role(command.getRole())
                .build();

        userRepository.save(user);
        String accessToken = jwtService.generateToken(user);

        return new Response(accessToken, "");
    }
}
