package ru.kuksov.compapi.useCases.auth.signIn;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.User;
import ru.kuksov.compapi.repository.UserRepository;
import ru.kuksov.compapi.service.JwtService;

import java.util.Optional;

@Service("SignIn")
@RequiredArgsConstructor
public class Handler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Response handle(Command command) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.getUsername(),
                        command.getPassword()
                )
        );

        Optional<User> user = userRepository.findByUsername(command.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        String accessToken = jwtService.generateToken(user.get());

        return new Response(user.get().getRole(), accessToken, "");
    }
}
