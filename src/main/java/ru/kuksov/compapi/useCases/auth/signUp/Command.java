package ru.kuksov.compapi.useCases.auth.signUp;

import lombok.Builder;
import lombok.Data;
import ru.kuksov.compapi.model.Role;

@Data
@Builder
public class Command {

    private String username;

    private String password;

    private Role role;
}
