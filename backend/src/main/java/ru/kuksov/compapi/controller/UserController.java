package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.controller.dto.UserRequest;
import ru.kuksov.compapi.controller.dto.UserResponse;
import ru.kuksov.compapi.model.Role;
import ru.kuksov.compapi.model.User;
import ru.kuksov.compapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/compapi/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Пользователи")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        if (this.userService.getAllUsers().isEmpty()) {
            this.userService.addUser("admin", passwordEncoder.encode("admin"), Role.ROLE_ADMIN);
            this.userService.addUser("operator", passwordEncoder.encode("operator"), Role.ROLE_OPERATOR);
            this.userService.addUser("user", passwordEncoder.encode("user"), Role.ROLE_USER);
        }
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping
    public Iterable<UserResponse> getAllUsers() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        List<User> users = this.userService.getAllUsers();
        log.info(marker, "All users successfully found");

        return users.stream().map(user -> new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        )).toList();
    }

    @Operation(summary = "Добавление нового пользователя")
    @PostMapping
    public UserResponse addUser(@RequestBody UserRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "add");
        String passwordHash = passwordEncoder.encode(request.password());
        User newUser = this.userService.addUser(request.name(), passwordHash, request.role());
        log.info(marker, "User %s successfully adding".formatted(newUser.getUsername()));
        return new UserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getRole()
        );
    }

    @Operation(summary = "Редактирование пользователя")
    @PatchMapping("/{id}")
    public void editUser(@PathVariable int id,
                          @RequestBody UserRequest request) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "edit");
        String passwordHash = request.password() == null ? null : passwordEncoder.encode(request.password());
        this.userService.updateUser(id, request.name(), passwordHash, request.role());
        log.info(marker, "User %s successfully edit".formatted(request.name()));
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "delete");
        this.userService.deleteUser(id);
        log.info(marker, "User %d successfully delete".formatted(id));
    }
}
