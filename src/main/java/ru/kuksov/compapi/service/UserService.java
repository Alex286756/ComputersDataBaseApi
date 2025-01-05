package ru.kuksov.compapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kuksov.compapi.model.Role;
import ru.kuksov.compapi.model.User;
import ru.kuksov.compapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User addUser(String name,
                        String password,
                        Role role) {
        return this.userRepository.save(
                User.builder()
                        .username(name)
                        .password(password)
                        .role(role)
                        .build()
        );
    }

    public void updateUser(int id,
                           String name,
                           String password,
                           Role role) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (name != null)
                user.setUsername(name);
            if (password != null)
                user.setPassword(password);
            if (role != null)
                user.setRole(role);
            this.userRepository.save(user);
        }
    }

    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
