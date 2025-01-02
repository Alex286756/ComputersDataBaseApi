package ru.kuksov.compapi.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.compapi.model.Role;

import java.util.Arrays;

@RestController
@RequestMapping("/compapi/v1/roles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Роли пользователей")
public class RoleController {

    @Operation(summary = "Получение списка всех возможных ролей")
    @GetMapping
    public Iterable<Role> getAllRoles() {
        LabelMarker marker = LabelMarker.of("ComputersDB", () -> "Get all");
        log.info(marker, "All roles successfully found");

        return Arrays.stream(Role.values()).toList();
    }
}
